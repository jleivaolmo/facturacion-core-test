package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.ep.entry.EntryMetadata;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntity;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPALink;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;

@Slf4j
public class CustomJPAEntity extends JPAEntity {

	private Object jpaEntity = null;
	private JPAEntity parentJPAEntity = null;
	private EdmEntityType oDataEntityType = null;
	private EdmEntitySet oDataEntitySet = null;
	private HashMap<String, Method> accessModifiersWrite = null;
	private JPAEntityParser jpaEntityParser = null;
	private ODataJPAContext oDataJPAContext;
	private List<String> relatedJPAEntityLink = new ArrayList<String>();
	private HashMap<String, List<Object>> relatedJPAEntityMap = null;

	public CustomJPAEntity(EdmEntityType oDataEntityType, EdmEntitySet oDataEntitySet, ODataJPAContext context) {
		super(oDataEntityType, oDataEntitySet, context);
		this.oDataEntityType = oDataEntityType;
		this.oDataEntitySet = oDataEntitySet;
		oDataJPAContext = context;
		try {
			JPAEdmMapping mapping = (JPAEdmMapping) oDataEntityType.getMapping();
			mapping.getJPAType();
		} catch (EdmException e) {
			return;
		}
		jpaEntityParser = new JPAEntityParser();
	}

	@Override
	public void setParentJPAEntity(final JPAEntity jpaEntity) {
		parentJPAEntity = jpaEntity;
	}

	@Override
	public JPAEntity getParentJPAEntity() {
		return parentJPAEntity;
	}

	@Override
	public Object getJPAEntity() {
		return jpaEntity;
	}

	@Override
	public void setJPAEntity(Object jpaEntity) {
		super.setJPAEntity(jpaEntity);
		this.jpaEntity = jpaEntity;
	}

	@Override
	public void update(final ODataEntry oDataEntry) throws ODataJPARuntimeException {
		if (oDataEntry == null) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL, null);
		}
		Map<String, Object> oDataEntryProperties = oDataEntry.getProperties();
		if (oDataEntry.containsInlineEntry()) {
			normalizeRelatedEntities(oDataEntryProperties);
			normalizeInlineEntries(oDataEntryProperties);
		}
		write(oDataEntryProperties, false);
		JPALink link = new JPALink(oDataJPAContext);
		link.setSourceJPAEntity(jpaEntity);
		try {
			link.create(oDataEntitySet, oDataEntry, oDataEntityType.getNavigationPropertyNames());
		} catch (EdmException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		} catch (ODataJPAModelException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		}
	}

	@Override
	public void update(final Map<String, Object> oDataEntryProperties) throws ODataJPARuntimeException {
		normalizeRelatedEntities(oDataEntryProperties);
		normalizeInlineEntries(oDataEntryProperties);
		write(oDataEntryProperties, false);
	}

	@Override
	public void create(ODataEntry oDataEntry) throws ODataJPARuntimeException {

		if (oDataEntry == null) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL, null);
		}
		try {
			EntryMetadata entryMetadata = oDataEntry.getMetadata();
			Map<String, Object> oDataEntryProperties = oDataEntry.getProperties();
			if (oDataEntry.containsInlineEntry()) {
				normalizeRelatedEntities(oDataEntryProperties);
				normalizeInlineEntries(oDataEntryProperties);
			}

			if (oDataEntry.getProperties().size() > 0) {

				write(oDataEntryProperties, true);

				for (String navigationPropertyName : oDataEntityType.getNavigationPropertyNames()) {
					EdmNavigationProperty navProperty = (EdmNavigationProperty) oDataEntityType
							.getProperty(navigationPropertyName);
					if (relatedJPAEntityMap != null && relatedJPAEntityMap.containsKey(navigationPropertyName)) {
						oDataEntry.getProperties().get(navigationPropertyName);
						JPALink.linkJPAEntities(relatedJPAEntityMap.get(navigationPropertyName), jpaEntity,
								navProperty);
						continue;
					}
					// The second condition is required to ensure that there is an explicit request
					// to link
					// two entities. Else the third condition will always be true for cases where
					// two navigations
					// point to same entity types.
					if (parentJPAEntity != null
							&& navProperty.getRelationship().equals(getViaNavigationProperty().getRelationship())) {
						List<Object> targetJPAEntities = new ArrayList<Object>();
						targetJPAEntities.add(parentJPAEntity.getJPAEntity());
						JPALink.linkJPAEntities(targetJPAEntities, jpaEntity, navProperty);
					} else if (!entryMetadata.getAssociationUris(navigationPropertyName).isEmpty()) {
						if (!relatedJPAEntityLink.contains(navigationPropertyName)) {
							relatedJPAEntityLink.add(navigationPropertyName);
						}
					}
				}
			}
			if (!relatedJPAEntityLink.isEmpty()) {
				JPALink link = new JPALink(oDataJPAContext);
				link.setSourceJPAEntity(jpaEntity);
				link.create(oDataEntitySet, oDataEntry, relatedJPAEntityLink);
			}
		} catch (EdmException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		} catch (ODataJPAModelException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		}
	}

	@Override
	public void create(final Map<String, Object> oDataEntryProperties) throws ODataJPARuntimeException {
		normalizeRelatedEntities(oDataEntryProperties);
		normalizeInlineEntries(oDataEntryProperties);
		write(oDataEntryProperties, true);
	}

	@SuppressWarnings("unchecked")
	private void write(final Map<String, Object> oDataEntryProperties, final boolean isCreate)
			throws ODataJPARuntimeException {
		try {

			EdmStructuralType structuralType = null;
			final List<String> keyNames = oDataEntityType.getKeyPropertyNames();

			if (isCreate) {
				jpaEntity = instantiateJPAEntity();
			} else if (jpaEntity == null) {
				throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.RESOURCE_NOT_FOUND, null);
			}

			if (accessModifiersWrite == null) {
				accessModifiersWrite = jpaEntityParser.getAccessModifiers(jpaEntity, oDataEntityType,
						JPAEntityParser.ACCESS_MODIFIER_SET);
			}

			if (oDataEntityType == null || oDataEntryProperties == null) {
				throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL, null);
			}

			final HashMap<String, String> embeddableKeys = jpaEntityParser
					.getJPAEmbeddableKeyMap(jpaEntity.getClass().getName());
			Set<String> propertyNames = null;
			if (embeddableKeys != null) {
				setEmbeddableKeyProperty(embeddableKeys, oDataEntityType.getKeyProperties(), oDataEntryProperties,
						jpaEntity);

				propertyNames = new HashSet<String>();
				propertyNames.addAll(oDataEntryProperties.keySet());
				for (String key : embeddableKeys.keySet()) {
					propertyNames.remove(key);
				}
			} else {
				propertyNames = oDataEntryProperties.keySet();
			}

			boolean isVirtual = false;
			for (String propertyName : propertyNames) {
				EdmTyped edmTyped = (EdmTyped) oDataEntityType.getProperty(propertyName);
				if (edmTyped instanceof EdmProperty) {
					isVirtual = ((JPAEdmMappingImpl) ((EdmProperty) edmTyped).getMapping()).isVirtualAccess();
				} else {
					isVirtual = false;
				}
				Method accessModifier = null;

				switch (edmTyped.getType().getKind()) {
				case SIMPLE:
					if (isCreate == false) {
						if (keyNames.contains(edmTyped.getName())) {
							continue;
						}
					}
					accessModifier = accessModifiersWrite.get(propertyName);
					EdmProperty edmProperty = (EdmProperty) oDataEntityType.getProperty(propertyName);
					boolean isNullable = edmProperty.getFacets() == null
							? (keyNames.contains(propertyName) ? false : true)
							: edmProperty.getFacets().isNullable() == null ? true
									: edmProperty.getFacets().isNullable();
					if (isVirtual) {
						setProperty(accessModifier, jpaEntity, oDataEntryProperties.get(propertyName),
								(EdmSimpleType) edmTyped.getType(), isNullable);
					} else {
						setProperty(accessModifier, jpaEntity, oDataEntryProperties.get(propertyName),
								(EdmSimpleType) edmTyped.getType(), isNullable);
					}
					break;
				case COMPLEX:
					structuralType = (EdmStructuralType) edmTyped.getType();
					accessModifier = accessModifiersWrite.get(propertyName);
					if (isVirtual) {
						setComplexProperty(accessModifier, jpaEntity, structuralType,
								(HashMap<String, Object>) oDataEntryProperties.get(propertyName), propertyName);
					} else {
						setComplexProperty(accessModifier, jpaEntity, structuralType,
								(HashMap<String, Object>) oDataEntryProperties.get(propertyName));
					}
					break;
				case NAVIGATION:
				case ENTITY:
					if (isCreate) {
						structuralType = (EdmStructuralType) edmTyped.getType();
						EdmNavigationProperty navProperty = (EdmNavigationProperty) edmTyped;
						EdmEntitySet edmRelatedEntitySet = oDataEntitySet.getRelatedEntitySet(navProperty);
						getObjects(oDataEntryProperties, propertyName, navProperty, structuralType,
								edmRelatedEntitySet);
					}else{
						structuralType = (EdmStructuralType) edmTyped.getType();
						EdmNavigationProperty navProperty = (EdmNavigationProperty) edmTyped;
						EdmEntitySet edmRelatedEntitySet = oDataEntitySet.getRelatedEntitySet(navProperty);
						getObjects(oDataEntryProperties, propertyName, navProperty, structuralType,
								edmRelatedEntitySet);
					}
				default:
					continue;
				}
			}
		} catch (Exception e) {
			if (e instanceof ODataJPARuntimeException) {
				throw (ODataJPARuntimeException) e;
			}
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		}
	}

	@SuppressWarnings("unchecked")
	private void getObjects(Map<String, Object> oDataEntryProperties, String propertyName,
			EdmNavigationProperty navProperty, EdmStructuralType structuralType, EdmEntitySet edmRelatedEntitySet)
			throws ODataJPARuntimeException, EdmException {
		List<Object> relatedEntries = (List<Object>) oDataEntryProperties.get(propertyName);
		if (relatedEntries != null && relatedEntries instanceof List && relatedEntries.size() > 0) {
			if (relatedEntries.get(0) instanceof BasicEntity) {
				processEntities((List<BasicEntity>) oDataEntryProperties.get(propertyName), navProperty, structuralType,
						edmRelatedEntitySet);
			} else if (relatedEntries.get(0) instanceof ODataEntry) {
				oDataEntryProperties.entrySet().stream()
						.forEach((entry) -> log.info("Key " + entry.getKey() + " Value " + entry.getValue()));
				log.info(propertyName);
				log.info(navProperty.toString());
				log.info(structuralType.toString());
				log.info(edmRelatedEntitySet.toString());
				processEntry((List<ODataEntry>) oDataEntryProperties.get(propertyName), navProperty, structuralType,
						edmRelatedEntitySet);
			}
		}
	}

	protected void processEntities(List<BasicEntity> relatedEntries, EdmNavigationProperty navProperty,
			EdmStructuralType structuralType, EdmEntitySet edmRelatedEntitySet)
			throws ODataJPARuntimeException, EdmException {

		if (relatedJPAEntityMap == null) {
			relatedJPAEntityMap = new HashMap<String, List<Object>>();
		}
		List<Object> relatedJPAEntities = new ArrayList<Object>();
		for (BasicEntity basicEntity : relatedEntries) {
			relatedJPAEntities.add(basicEntity);
		}
		JPALink.linkJPAEntities(relatedJPAEntities, jpaEntity, navProperty);
	}

	protected void processEntry(List<ODataEntry> relatedEntries, EdmNavigationProperty navProperty,
			EdmStructuralType structuralType, EdmEntitySet edmRelatedEntitySet)
			throws ODataJPARuntimeException, EdmException {

		if (relatedJPAEntityMap == null) {
			relatedJPAEntityMap = new HashMap<String, List<Object>>();
		}
		List<Object> relatedJPAEntities = new ArrayList<Object>();
		for (ODataEntry oDataEntry : relatedEntries) {
			JPAEntity relatedEntity = new JPAEntity((EdmEntityType) structuralType, edmRelatedEntitySet,
					oDataJPAContext);
			relatedEntity.setParentJPAEntity(this);
			relatedEntity.setViaNavigationProperty(navProperty);
			log.info("Creating ***** " + oDataEntry);
			relatedEntity.create(oDataEntry);
			log.info("Created ***** " + oDataEntry);
			log.info("\n\n\n\n\n");
			if (oDataEntry.getProperties().size() == 0) {
				if (!oDataEntry.getMetadata().getUri().isEmpty()
						&& !relatedJPAEntityLink.contains(navProperty.getName())) {
					relatedJPAEntityLink.add(navProperty.getName());
				}
			} else {
				relatedJPAEntities.add(relatedEntity.getJPAEntity());
			}
		}
		if (!relatedJPAEntities.isEmpty()) {
			relatedJPAEntityMap.put(navProperty.getName(), relatedJPAEntities);
		}
	}

	/**
	 * 
	 * @param oDataEntryProperties
	 * @throws ODataJPARuntimeException
	 */
	private void normalizeRelatedEntities(final Map<String, Object> oDataEntryProperties)
			throws ODataJPARuntimeException {
		List<BasicEntity> entries = null;
		try {
			for (String navigationPropertyName : oDataEntityType.getNavigationPropertyNames()) {
				Object inline = oDataEntryProperties.get(navigationPropertyName);
				if (inline instanceof BasicEntity) {
					entries = new ArrayList<BasicEntity>();
					entries.add((BasicEntity) inline);
				}
				if (entries != null) {
					oDataEntryProperties.put(navigationPropertyName, entries);
					entries = null;
				}
			}
		} catch (Exception e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		}
	}

	/**
	 * 
	 * @param oDataEntryProperties
	 * @throws ODataJPARuntimeException
	 */
	private void normalizeInlineEntries(final Map<String, Object> oDataEntryProperties)
			throws ODataJPARuntimeException {
		List<ODataEntry> entries = null;
		try {
			for (String navigationPropertyName : oDataEntityType.getNavigationPropertyNames()) {
				Object inline = oDataEntryProperties.get(navigationPropertyName);
				if (inline instanceof ODataFeed) {
					entries = ((ODataFeed) inline).getEntries();
				} else if (inline instanceof ODataEntry) {
					entries = new ArrayList<ODataEntry>();
					entries.add((ODataEntry) inline);
				}
				if (entries != null) {
					oDataEntryProperties.put(navigationPropertyName, entries);
					entries = null;
				}
			}
		} catch (EdmException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()),
					e);
		}
	}

}

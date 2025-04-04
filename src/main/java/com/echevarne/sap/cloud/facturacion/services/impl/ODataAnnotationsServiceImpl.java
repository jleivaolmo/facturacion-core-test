package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.*;

import javax.annotation.PostConstruct;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.odata.extension.AbstractAnnotationExtension;
import com.echevarne.sap.cloud.facturacion.odata.extension.smart.AbstractAnnotations;
import com.echevarne.sap.cloud.facturacion.odata.extension.smart.AbstractSmartAnnotations;
import com.echevarne.sap.cloud.facturacion.odata.extension.smart.ODataAnnotations;
import com.echevarne.sap.cloud.facturacion.odata.extension.smart.SAPAnnotations;
import com.echevarne.sap.cloud.facturacion.odata.extension.smart.SmartAnnotations;
import com.echevarne.sap.cloud.facturacion.repositories.EntityListNameRep;
import com.echevarne.sap.cloud.facturacion.repositories.dto.NombreEntidadAndFieldNameAndText;
import com.echevarne.sap.cloud.facturacion.services.EntityListNameService;
import com.echevarne.sap.cloud.facturacion.services.ODataAnnotationsService;

import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("oDataAnnotationsSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ODataAnnotationsServiceImpl extends AbstractAnnotationExtension implements ODataAnnotationsService {

	private final EntityListNameService entityListNameSrv;

	@Autowired(required = false)
	private SAPAnnotations sap;
	@Autowired(required = false)
	private SmartAnnotations smart;
	@Autowired(required = false)
	private ODataAnnotations odata;

	@Autowired
	public ODataAnnotationsServiceImpl(final EntityListNameService entityListNameSrv) {
		super();

		this.entityListNameSrv = entityListNameSrv;
	}

	@PostConstruct
	public void postConstruct(){
		this.sapAnnotations = sap;
		this.smartAnnotations = smart;
		this.odataAnnotations = odata;
	}

	@Override
	public void addEntitiesAndFieldsAnnotations(Schema edmSchema) {
		/* Add SAP Annotations */
		if(sapAnnotations != null || odataAnnotations != null) {
			List<NombreEntidadAndFieldNameAndText> nombreEntidadAndFieldNameAndTexts = this.entityListNameSrv.findAllNombreEntidadAndFieldNameAndText(
					ConstFacturacion.IDIOMA_DEFAULT.toLowerCase()
			);
			Map<String, Map<String, String>> textByFieldNameByLcNombreEntidad = mapByLcNombreEntitadAndByFieldName(nombreEntidadAndFieldNameAndTexts);

			if(sapAnnotations != null) {
				sapAnnotations.addAnnotations(edmSchema, textByFieldNameByLcNombreEntidad);
			}

			/* Add OData Annotations */
			if(odataAnnotations != null) {
				odataAnnotations.addAnnotations(edmSchema, textByFieldNameByLcNombreEntidad);
			}
		}

	}

	private Map<String, Map<String, String>> mapByLcNombreEntitadAndByFieldName(List<NombreEntidadAndFieldNameAndText> nombreEntidadAndFieldNameAndTexts) {
		Map<String, Map<String, String>> textByFieldNameByLcNombreEntidad = new HashMap<>();
		for(NombreEntidadAndFieldNameAndText nombreEntidadAndFieldNameAndText: nombreEntidadAndFieldNameAndTexts){
			final String nombreEntidad = nombreEntidadAndFieldNameAndText.getNombreEntidad();
			final String lcNombreEntidad = nombreEntidad.toLowerCase();

			Map<String, String> textByFieldName = textByFieldNameByLcNombreEntidad.computeIfAbsent(lcNombreEntidad, k -> new HashMap<>());

			String fieldName = nombreEntidadAndFieldNameAndText.getNombreCampo();
			String text = nombreEntidadAndFieldNameAndText.getTextoCampo();
			textByFieldName.put(fieldName, text);
		}

		return textByFieldNameByLcNombreEntidad;
	}

	@Override
	public void addSchemaAnnotations(Schema edmSchema) {

		List<AnnotationElement> schemaAnnotations = new ArrayList<>();

		/* Add Smart Annotations */
		if(smartAnnotations != null) {
			smartAnnotations.addAnnotations(edmSchema, schemaAnnotations);
		}

		/* Set Annotations to Schema */
		edmSchema.setAnnotationElements(schemaAnnotations);

		/* Set complex types */
		edmSchema.setComplexTypes(getComplexTypes(edmSchema));

	}

	@Override
	public List<ComplexType> getComplexTypes(Schema edmSchema) {
		return super.mapComplexTypes(edmSchema);
	}

	@Override
	public AbstractSmartAnnotations getSmartAnnotations() {
		return this.smartAnnotations;
	}

	@Override
	public AbstractAnnotations getSapAnnotations() {
		return this.sapAnnotations;
	}

}

package com.echevarne.sap.cloud.facturacion.odata.extension.smart;

public interface AnnotationCommons {
	
	public final static String XML_MS_NAMESPACE = "http://docs.oasis-open.org/odata/ns/edm";
	
	public final static String XML_DOT = ".";
	public final static String XML_SLASH = "/";
	public final static String XML_HASH = "#";
	public final static String XML_REFERENCE = "@";
	
	
	/**
	 * 
	 * ELEMENTS
	 * 
	 */
	public final static String XML_ELEM_ANNOTATIONS = "Annotations";
	public final static String XML_ELEM_ANNOTATION = "Annotation";
	
	public final static String XML_ELEM_COLLECTION = "Collection";
	public final static String XML_ELEM_RECORD = "Record";
	public final static String XML_ELEM_PROP_VALUE = "PropertyValue";
	public final static String XML_ELEM_PROP_PATH = "PropertyPath";
	
	/**
	 * 
	 * ATTRIBUTES
	 * 
	 */
	public final static String XML_ATTR_TERM = "Term";
	public final static String XML_ATTR_ENUMMEMBER = "EnumMember";
	public final static String XML_ATTR_PROPERTY = "Property";
	public final static String XML_ATTR_ANNOTATION_PATH = "AnnotationPath";
	public final static String XML_ATTR_PATH = "Path";
	public final static String XML_ATTR_PROP_PATH = "PropertyPath";
	public final static String XML_ATTR_STRING = "String";
	public final static String XML_ATTR_BOOL = "Bool";
	public final static String XML_ATTR_TARGET = "Target";
	public final static String XML_ATTR_RECORD_TYPE = "Type";
	public final static String XML_ATTR_QUALIFIER = "Qualifier";
	
	/**
	 * 
	 * TERMS
	 * 
	 */
	public final static String XML_TERM_COMMON = "com.sap.vocabularies.Common.v1";
	public final static String XML_TERM_UI = "com.sap.vocabularies.UI.v1";
	
	public final static String XML_TERM_VALUE_LIST = XML_TERM_COMMON + XML_DOT + "ValueList";
	public final static String XML_TERM_VL_IN = XML_TERM_COMMON + XML_DOT + "ValueListParameterIn";
	public final static String XML_TERM_VL_OUT = XML_TERM_COMMON + XML_DOT + "ValueListParameterOut";
	public final static String XML_TERM_VL_IN_OUT = XML_TERM_COMMON + XML_DOT + "ValueListParameterInOut";
	public final static String XML_TERM_VL_DISPLAY_ONLY = XML_TERM_COMMON + XML_DOT + "ValueListParameterDisplayOnly";
	
	public final static String XML_TERM_LINEITEM = XML_TERM_UI + XML_DOT + "LineItem";
	public final static String XML_TERM_IDENTIFICATION = XML_TERM_UI + XML_DOT + "Identification";
	public final static String XML_TERM_DATAPOINT = XML_TERM_UI + XML_DOT + "DataPoint";
	public final static String XML_TERM_SELECTION_FIELDS = XML_TERM_UI + XML_DOT + "SelectionFields";
	public final static String XML_TERM_CRITICALLY = XML_TERM_UI + XML_DOT + "Criticality";
	public final static String XML_TERM_FACETS = XML_TERM_UI + XML_DOT + "Facets";
	public final static String XML_TERM_HEADER_INFO = XML_TERM_UI + XML_DOT + "HeaderInfo";
	public final static String XML_TERM_HEADER_FACETS = XML_TERM_UI + XML_DOT + "HeaderFacets";
	public final static String XML_TERM_REFERENCE_FACET = XML_TERM_UI + XML_DOT + "ReferenceFacet";
	
	
	/**
	 * 
	 * VALUES
	 * 
	 */
	public final static String XML_VALU_ID = "ID";
	public final static String XML_VALU_LABEL = "Label";
	public final static String XML_VALU_COLLECTION_PATH = "CollectionPath";
	public final static String XML_VALU_COLLECTION_ROOT = "CollectionRoot";
	public final static String XML_VALU_SEARCH_SUPPORTED = "SearchSupported";
	public final static String XML_VALU_PARAMETERS = "Parameters";
	public final static String XML_VALU_VALUE = "Value";
	public final static String XML_VALU_TITLE = "Title";
	public final static String XML_VALU_TARGET = "Target";
	
	public final static String XML_VALU_TYPE_NAME = "TypeName";
	public final static String XML_VALU_TYPE_NAME_PLURAL = "TypeNamePlural";
	public final static String XML_VALU_TYPE_IMAGE_URL = "TypeImageUrl";
	
	public final static String XML_VALU_PROP_VALLIST = "ValueListProperty";
	public final static String XML_VALU_PROP_LOCAL = "LocalDataProperty";
	
	public final static String XML_VALU_RECORD_TYPE_DATAFIELD = XML_TERM_UI + XML_DOT + "DataField";
	public final static String XML_VALU_RECORD_TYPE_DATAPOINTTYPE = XML_TERM_UI + XML_DOT + "DataPointType";
	
	
	/**
	 * 
	 * TERMS
	 * 
	 */
	public static final String SAP_NAMESPACE = "http://www.sap.com/Protocols/SAPData";
	public static final String SAP_PREFIX = "sap";
	public static final String LABEL = "label";
	public static final String TEXT = "text";
	public static final String ALL_ENTITIES = "AllEntities";
	public static final String SEPARATOR = ".";
	public static final String DISPLAY_FORMAT = "display-format";
	public static final String DATE_FORMAT = "Date";	
	
}

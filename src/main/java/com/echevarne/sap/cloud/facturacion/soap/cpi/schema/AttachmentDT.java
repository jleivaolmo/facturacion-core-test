package com.echevarne.sap.cloud.facturacion.soap.cpi.schema;

import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.ATTACHMENT_DT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.CONTENT_TYPE;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.CONTENT_TYPE_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.FILENAME_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.FILE_ELEMENT_TAG;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ATTACHMENT_DT_TAG, propOrder = { FILENAME_ELEMENT_TAG, FILE_ELEMENT_TAG, CONTENT_TYPE_ELEMENT_TAG })
public class AttachmentDT {

	@XmlElement(required = true)
	protected String filename;
	@XmlElement(required = true)
	protected String file;
	@XmlElement(name = CONTENT_TYPE, required = true)
	protected String contentType;

}

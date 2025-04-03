package com.echevarne.sap.cloud.facturacion.soap.cpi.schema;

import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.ATTACHMENTS_DT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.ATTACHMENT_ELEMENT_TAG;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ATTACHMENTS_DT_TAG, propOrder = { ATTACHMENT_ELEMENT_TAG })
public class AttachmentsDT {

	protected List<AttachmentDT> attachment;

	public List<AttachmentDT> getAttachment() {
		if (attachment == null) {
			attachment = new ArrayList<>();
		}
		return this.attachment;
	}

}

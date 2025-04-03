package com.echevarne.sap.cloud.facturacion.soap.cpi.schema;

import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.MAIL_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.NAMESPACE;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _MailMT_QNAME = new QName(NAMESPACE, MAIL_ELEMENT_TAG);

	public ObjectFactory() {
	}

	public MailDT createMailDT() {
		return new MailDT();
	}

	public AttachmentsDT createAttachmentsDT() {
		return new AttachmentsDT();
	}

	public AttachmentDT createAttachmentDT() {
		return new AttachmentDT();
	}

	@XmlElementDecl(namespace = NAMESPACE, name = MAIL_ELEMENT_TAG)
	public JAXBElement<MailDT> createMailMT(MailDT value) {
		return new JAXBElement<MailDT>(_MailMT_QNAME, MailDT.class, null, value);
	}

}

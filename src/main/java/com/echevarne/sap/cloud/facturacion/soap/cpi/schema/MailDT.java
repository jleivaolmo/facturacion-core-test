package com.echevarne.sap.cloud.facturacion.soap.cpi.schema;

import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.ATTACHMENTS_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.DELIVERY_METHOD_PDFWD;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.DELIVERY_METHOD_PDFWD_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.DELIVERY_METHOD_REG_ACTV;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.DELIVERY_METHOD_REG_ACTV_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.FECHA_FACTURA;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.FECHA_FACTURA_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.FLAG_LIQUIS;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.FLAG_LIQUIS_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.LOCAL_PATH_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.MAIL_DT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.MAIL_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.MAIL_TO_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.NAMESPACE;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.NUMERO_CLIENTE;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.NUMERO_CLIENTE_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.NUMERO_FACTURA;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.NUMERO_FACTURA_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.PASSWORD;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.PASSWORD_ELEMENT_TAG;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.SOCIEDAD;
import static com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants.SOCIEDAD_ELEMENT_TAG;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.echevarne.sap.cloud.facturacion.soap.cpi.constants.SchemaConstants;

import lombok.Data;

@Data
@XmlRootElement(name = MAIL_ELEMENT_TAG, namespace = NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = MAIL_DT_TAG, propOrder = { MAIL_TO_ELEMENT_TAG, NUMERO_FACTURA, NUMERO_CLIENTE, FECHA_FACTURA, SOCIEDAD, FLAG_LIQUIS, LOCAL_PATH_ELEMENT_TAG,
		DELIVERY_METHOD_PDFWD, DELIVERY_METHOD_REG_ACTV, ATTACHMENTS_ELEMENT_TAG, PASSWORD, SchemaConstants.FLAG_PREFAC, SchemaConstants.FLAG_RECTIS })
public class MailDT {

	protected String mailTo;
	@XmlElement(name = NUMERO_FACTURA_ELEMENT_TAG)
	protected String numeroFactura;
	@XmlElement(name = NUMERO_CLIENTE_ELEMENT_TAG)
	protected String numeroCliente;
	@XmlElement(name = FECHA_FACTURA_ELEMENT_TAG)
	protected Date fechaFactura;
	@XmlElement(name = SOCIEDAD_ELEMENT_TAG)
	protected String sociedad;
	@XmlElement(name = FLAG_LIQUIS_ELEMENT_TAG)
	protected String flagLiquis;
	protected String localPath;
	@XmlElement(name = DELIVERY_METHOD_PDFWD_ELEMENT_TAG)
	protected String deliveryMethodPDFWD;
	@XmlElement(name = DELIVERY_METHOD_REG_ACTV_ELEMENT_TAG)
	protected String deliveryMethodRegActv;
	@XmlElement(required = true)
	protected AttachmentsDT attachments;
	@XmlElement(name = PASSWORD_ELEMENT_TAG)
	protected String password;
	@XmlElement(name = SchemaConstants.FLAG_PREFAC_ELEMENT_TAG)
	protected String flagPrefac;
	@XmlElement(name = SchemaConstants.FLAG_RECTIS_ELEMENT_TAG)
	protected String flagRectis;
}

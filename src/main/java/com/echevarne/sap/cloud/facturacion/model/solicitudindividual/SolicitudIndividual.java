package com.echevarne.sap.cloud.facturacion.model.solicitudindividual;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Borrada;
import com.echevarne.sap.cloud.facturacion.gestionestados.CreadaRecibida;
import com.echevarne.sap.cloud.facturacion.gestionestados.Erronea;
import com.echevarne.sap.cloud.facturacion.gestionestados.Excluida;
import com.echevarne.sap.cloud.facturacion.gestionestados.Facturable;
import com.echevarne.sap.cloud.facturacion.gestionestados.ParcialmenteProcesada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Procesable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.ReglaPrefactura;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Class for the Entity {@link SolicitudIndividual}.
 *
 * <p>
 * The persistent class. . .T_SolicitudIndividual
 * </p>
 * <p>
 * This is a simple description of the class. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(
	name = "T_SolicitudIndividual",
	indexes = {
		@Index(name = "SolicitudIndividual_byPurchaseOrderByCustomer", columnList = "purchaseOrderByCustomer", unique = false),
		@Index(name = "SolicitudIndividual_bySoldToParty", columnList = "soldToParty", unique = false),
	}
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudIndividual extends BasicEntity implements Transicionable<TrazabilidadSolicitud> {

	private static final long serialVersionUID = -1752659581847824503L;

	@Basic
	private String salesOrderInd;
	@Basic
	private String salesOrderType;
	@Basic
	private String salesOrganization;
	@Basic
	private String distributionChannel;
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "codigoSector"),
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	private String organizationDivision;
	@Basic
	private String salesGroup;
	@Basic
	private String salesOffice;
	@Basic
	private String salesDistrict;
	@Basic
	private String soldToParty;

	//@Column(unique = true)
	@Basic
	private String purchaseOrderByCustomer;
	@Basic
	private String customerPurchaseOrderType;
	@Basic
	private Timestamp customerPurchaseOrderDate;
	@Basic
	private Timestamp salesOrderDate;
	@Basic
	private String transactionCurrency;
	@Basic
	private String sdDocumentReason;
	@Basic
	private Timestamp pricingDate;
	@Basic
	private Timestamp requestedDeliveryDate;
	@Basic
	private String shippingCondition;
	@Basic
	@ColumnDefault("false")
	private boolean completeDeliveryIsDefined;
	@Basic
	private String shippingType;
	@Basic
	private String headerBillingBlockReason;
	@Basic
	private String deliveryBlockReason;
	@Basic
	private String incotermsClassification;
	@Basic
	private String incotermsTransferLocation;
	@Basic
	private String incotermsLocation1;
	@Basic
	private String incotermsLocation2;
	@Basic
	private String incotermsVersion;
	@Basic
	private String customerPaymentTerms;
	@Basic
	private String paymentMethod;
	@Basic
	private String assignmentReference;
	@Basic
	private String referenceSDDocument;
	@Basic
	private String customerTaxClassification1;
	@Basic
	private String taxDepartureCountry;
	@Basic
	private String vatRegistrationCountry;
	@Basic
	private String tarifa;
	@Basic
	private String grupoPrecioCliente;
	@Basic
	private String tipoCotizacion;
	@Basic
	private String codigoPoliza;
	@Basic
	private String documentoUnico;
	@Basic
	private String servicioConcertado;
	@Basic
	private String codigoOperacion;
	@Basic
	private String provinciaDelegacion;
	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private int tipoPeticion;
	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean tratablePrivados;
	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean bloqueoAutomatico;

	@Column(precision = 16, scale = 3)
	private BigDecimal totalNetAmount;

	@Column(precision = 16, scale = 3)
	private BigDecimal taxAmount;

	@Column(precision = 16, scale = 3)
	private BigDecimal totalAmount;

	@Column(precision = 16, scale = 2)
	private BigDecimal totalPoints;

	@OneToMany(cascade = ALL, mappedBy = "solicitudInd")
	@JsonManagedReference
	private Set<SolIndPartner> partners = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "solicitudInd")
	@JsonManagedReference
	private Set<SolIndPricing> prices = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "solicitudInd")
	@JsonManagedReference
	private Set<SolIndTexts> texts = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "solicitudInd")
	@JsonManagedReference
	@OrderBy("priceReferenceMaterial")
	private Set<SolIndItems> items = new HashSet<>();

	@OneToOne(mappedBy = "solicitudInd", cascade = { PERSIST, MERGE, DETACH, REFRESH }, fetch = FetchType.LAZY) // not REMOVE
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadSolicitud trazabilidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo", nullable = true)
	private ContratoCapitativo contrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ReglaPrefactura", nullable = true)
	private ReglaPrefactura reglaPrefactura;

	/**
	 * @return the salesOrderInd
	 */
	public String getSalesOrderInd() {
		return this.salesOrderInd;
	}

	/**
	 * @param salesOrderInd the salesOrderInd to set
	 */
	public void setSalesOrderInd(String salesOrderInd) {
		this.salesOrderInd = salesOrderInd;
	}

	/**
	 * @return the salesOrderType
	 */
	public String getSalesOrderType() {
		return this.salesOrderType;
	}

	/**
	 * @param salesOrderType the salesOrderType to set
	 */
	public void setSalesOrderType(String salesOrderType) {
		this.salesOrderType = salesOrderType;
	}

	/**
	 * @return the salesOrganization
	 */
	public String getSalesOrganization() {
		return this.salesOrganization;
	}

	/**
	 * @param salesOrganization the salesOrganization to set
	 */
	public void setSalesOrganization(String salesOrganization) {
		this.salesOrganization = salesOrganization;
	}

	/**
	 * @return the distributionChannel
	 */
	public String getDistributionChannel() {
		return this.distributionChannel;
	}

	/**
	 * @param distributionChannel the distributionChannel to set
	 */
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	/**
	 * @return the organizationDivision
	 */
	public String getOrganizationDivision() {
		return this.organizationDivision;
	}

	/**
	 * @param organizationDivision the organizationDivision to set
	 */
	public void setOrganizationDivision(String organizationDivision) {
		this.organizationDivision = organizationDivision;
	}

	/**
	 * @return the salesGroup
	 */
	public String getSalesGroup() {
		return this.salesGroup;
	}

	/**
	 * @param salesGroup the salesGroup to set
	 */
	public void setSalesGroup(String salesGroup) {
		this.salesGroup = salesGroup;
	}

	/**
	 * @return the salesOffice
	 */
	public String getSalesOffice() {
		return this.salesOffice;
	}

	/**
	 * @param salesOffice the salesOffice to set
	 */
	public void setSalesOffice(String salesOffice) {
		this.salesOffice = salesOffice;
	}

	/**
	 * @return the salesDistrict
	 */
	public String getSalesDistrict() {
		return this.salesDistrict;
	}

	/**
	 * @param salesDistrict the salesDistrict to set
	 */
	public void setSalesDistrict(String salesDistrict) {
		this.salesDistrict = salesDistrict;
	}

	/**
	 * @return the soldToParty
	 */
	public String getSoldToParty() {
		return this.soldToParty;
	}

	/**
	 * @param soldToParty the soldToParty to set
	 */
	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	/**
	 * @return the purchaseOrderByCustomer
	 */
	public String getPurchaseOrderByCustomer() {
		return this.purchaseOrderByCustomer;
	}

	/**
	 * @param purchaseOrderByCustomer the purchaseOrderByCustomer to set
	 */
	public void setPurchaseOrderByCustomer(String purchaseOrderByCustomer) {
		this.purchaseOrderByCustomer = purchaseOrderByCustomer;
	}

	/**
	 * @return the customerPurchaseOrderType
	 */
	public String getCustomerPurchaseOrderType() {
		return this.customerPurchaseOrderType;
	}

	/**
	 * @param customerPurchaseOrderType the customerPurchaseOrderType to set
	 */
	public void setCustomerPurchaseOrderType(String customerPurchaseOrderType) {
		this.customerPurchaseOrderType = customerPurchaseOrderType;
	}

	/**
	 * @return the customerPurchaseOrderDate
	 */
	public Timestamp getCustomerPurchaseOrderDate() {
		return this.customerPurchaseOrderDate;
	}

	/**
	 * @param customerPurchaseOrderDate the customerPurchaseOrderDate to set
	 */
	public void setCustomerPurchaseOrderDate(Timestamp customerPurchaseOrderDate) {
		this.customerPurchaseOrderDate = customerPurchaseOrderDate;
	}

	/**
	 * @return the salesOrderDate
	 */
	public Timestamp getSalesOrderDate() {
		return this.salesOrderDate;
	}

	/**
	 * @param salesOrderDate the salesOrderDate to set
	 */
	public void setSalesOrderDate(Timestamp salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return this.transactionCurrency;
	}

	/**
	 * @param transactionCurrency the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the sdDocumentReason
	 */
	public String getSdDocumentReason() {
		return this.sdDocumentReason;
	}

	/**
	 * @param sdDocumentReason the sdDocumentReason to set
	 */
	public void setSdDocumentReason(String sdDocumentReason) {
		this.sdDocumentReason = sdDocumentReason;
	}

	/**
	 * @return the pricingDate
	 */
	public Timestamp getPricingDate() {
		return this.pricingDate;
	}

	/**
	 * @param pricingDate the pricingDate to set
	 */
	public void setPricingDate(Timestamp pricingDate) {
		this.pricingDate = pricingDate;
	}

	/**
	 * @return the requestedDeliveryDate
	 */
	public Timestamp getRequestedDeliveryDate() {
		return this.requestedDeliveryDate;
	}

	/**
	 * @param requestedDeliveryDate the requestedDeliveryDate to set
	 */
	public void setRequestedDeliveryDate(Timestamp requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	/**
	 * @return the shippingCondition
	 */
	public String getShippingCondition() {
		return this.shippingCondition;
	}

	/**
	 * @param shippingCondition the shippingCondition to set
	 */
	public void setShippingCondition(String shippingCondition) {
		this.shippingCondition = shippingCondition;
	}

	/**
	 * @return the completeDeliveryIsDefined
	 */
	public boolean getCompleteDeliveryIsDefined() {
		return this.completeDeliveryIsDefined;
	}

	/**
	 * @param completeDeliveryIsDefined the completeDeliveryIsDefined to set
	 */
	public void setCompleteDeliveryIsDefined(boolean completeDeliveryIsDefined) {
		this.completeDeliveryIsDefined = completeDeliveryIsDefined;
	}

	/**
	 * @return the shippingType
	 */
	public String getShippingType() {
		return this.shippingType;
	}

	/**
	 * @param shippingType the shippingType to set
	 */
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	/**
	 * @return the headerBillingBlockReason
	 */
	public String getHeaderBillingBlockReason() {
		return this.headerBillingBlockReason;
	}

	/**
	 * @param headerBillingBlockReason the headerBillingBlockReason to set
	 */
	public void setHeaderBillingBlockReason(String headerBillingBlockReason) {
		this.headerBillingBlockReason = headerBillingBlockReason;
	}

	/**
	 * @return the deliveryBlockReason
	 */
	public String getDeliveryBlockReason() {
		return this.deliveryBlockReason;
	}

	/**
	 * @param deliveryBlockReason the deliveryBlockReason to set
	 */
	public void setDeliveryBlockReason(String deliveryBlockReason) {
		this.deliveryBlockReason = deliveryBlockReason;
	}

	/**
	 * @return the incotermsClassification
	 */
	public String getIncotermsClassification() {
		return this.incotermsClassification;
	}

	/**
	 * @param incotermsClassification the incotermsClassification to set
	 */
	public void setIncotermsClassification(String incotermsClassification) {
		this.incotermsClassification = incotermsClassification;
	}

	/**
	 * @return the incotermsTransferLocation
	 */
	public String getIncotermsTransferLocation() {
		return this.incotermsTransferLocation;
	}

	/**
	 * @param incotermsTransferLocation the incotermsTransferLocation to set
	 */
	public void setIncotermsTransferLocation(String incotermsTransferLocation) {
		this.incotermsTransferLocation = incotermsTransferLocation;
	}

	/**
	 * @return the incotermsLocation1
	 */
	public String getIncotermsLocation1() {
		return this.incotermsLocation1;
	}

	/**
	 * @param incotermsLocation1 the incotermsLocation1 to set
	 */
	public void setIncotermsLocation1(String incotermsLocation1) {
		this.incotermsLocation1 = incotermsLocation1;
	}

	/**
	 * @return the incotermsLocation2
	 */
	public String getIncotermsLocation2() {
		return this.incotermsLocation2;
	}

	/**
	 * @param incotermsLocation2 the incotermsLocation2 to set
	 */
	public void setIncotermsLocation2(String incotermsLocation2) {
		this.incotermsLocation2 = incotermsLocation2;
	}

	/**
	 * @return the incotermsVersion
	 */
	public String getIncotermsVersion() {
		return this.incotermsVersion;
	}

	/**
	 * @param incotermsVersion the incotermsVersion to set
	 */
	public void setIncotermsVersion(String incotermsVersion) {
		this.incotermsVersion = incotermsVersion;
	}

	/**
	 * @return the customerPaymentTerms
	 */
	public String getCustomerPaymentTerms() {
		return this.customerPaymentTerms;
	}

	/**
	 * @param customerPaymentTerms the customerPaymentTerms to set
	 */
	public void setCustomerPaymentTerms(String customerPaymentTerms) {
		this.customerPaymentTerms = customerPaymentTerms;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the assignmentReference
	 */
	public String getAssignmentReference() {
		return this.assignmentReference;
	}

	/**
	 * @param assignmentReference the assignmentReference to set
	 */
	public void setAssignmentReference(String assignmentReference) {
		this.assignmentReference = assignmentReference;
	}

	/**
	 * @return the referenceSDDocument
	 */
	public String getReferenceSDDocument() {
		return this.referenceSDDocument;
	}

	/**
	 * @param referenceSDDocument the referenceSDDocument to set
	 */
	public void setReferenceSDDocument(String referenceSDDocument) {
		this.referenceSDDocument = referenceSDDocument;
	}

	/**
	 * @return the customerTaxClassification1
	 */
	public String getCustomerTaxClassification1() {
		return this.customerTaxClassification1;
	}

	/**
	 * @param customerTaxClassification1 the customerTaxClassification1 to set
	 */
	public void setCustomerTaxClassification1(String customerTaxClassification1) {
		this.customerTaxClassification1 = customerTaxClassification1;
	}

	/**
	 * @return the taxDepartureCountry
	 */
	public String getTaxDepartureCountry() {
		return this.taxDepartureCountry;
	}

	/**
	 * @param taxDepartureCountry the taxDepartureCountry to set
	 */
	public void setTaxDepartureCountry(String taxDepartureCountry) {
		this.taxDepartureCountry = taxDepartureCountry;
	}

	/**
	 * @return the vatRegistrationCountry
	 */
	public String getVatRegistrationCountry() {
		return this.vatRegistrationCountry;
	}

	/**
	 * @param vatRegistrationCountry the vatRegistrationCountry to set
	 */
	public void setVatRegistrationCountry(String vatRegistrationCountry) {
		this.vatRegistrationCountry = vatRegistrationCountry;
	}

	/**
	 * CustomField {@code VBKD-PLTYP}
	 *
	 * @return tarifa
	 */
	public String getTarifa() {
		return this.tarifa;
	}

	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * CustomField {@code VBKD-KONDA}
	 *
	 * @return grupoPrecioCliente
	 */
	public String getGrupoPrecioCliente() {
		return this.grupoPrecioCliente;
	}

	public void setGrupoPrecioCliente(String grupoPrecioCliente) {
		this.grupoPrecioCliente = grupoPrecioCliente;
	}

	/**
	 * CustomField {@code VBKD-KURST}
	 *
	 * @return tipoCotizacion
	 */
	public String getTipoCotizacion() {
		return this.tipoCotizacion;
	}

	public void setTipoCotizacion(String tipoCotizacion) {
		this.tipoCotizacion = tipoCotizacion;
	}

	/**
	 * @return the totalNetAmount
	 */
	public BigDecimal getTotalNetAmount() {
		return totalNetAmount;
	}

	/**
	 * @param totalNetAmount the totalNetAmount to set
	 */
	public void setTotalNetAmount(BigDecimal totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}

	/**
	 *
	 * @return
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 *
	 * @param taxAmount
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 *
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 *
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(BigDecimal totalPoints) {
		this.totalPoints = totalPoints;
	}

	/**
	 * @return the provinciaDelegacion
	 */
	public String getProvinciaDelegacion() {
		return provinciaDelegacion;
	}

	/**
	 * @param provinciaDelegacion the provinciaDelegacion to set
	 */
	public void setProvinciaDelegacion(String provinciaDelegacion) {
		this.provinciaDelegacion = provinciaDelegacion;
	}

	/**
	 * @return the partners
	 */
	public Set<SolIndPartner> getPartners() {
		return partners;
	}

	/**
	 * @param partners the partners to set
	 */
	public void setPartners(Set<SolIndPartner> partners) {
		this.partners = partners;
	}

	public void addPartner(SolIndPartner partner) {
		partners.add(partner);
	}

	public Optional<SolIndPartner> getInterlocutorByRol(String rol) {
		return this.getPartners().stream().filter(partn -> partn.getPartnerFunction().equals(rol)).findFirst();
	}

	public Optional<SolIndPartner> getInterlocutorEmpresa() {
		return getInterlocutorByRol("ZE");
	}

	public Optional<SolIndPartner> getInterlocutorCompania() {
		return getInterlocutorByRol("ZC");
	}

	public Optional<SolIndPartner> getInterlocutorRemitente() {
		return getInterlocutorByRol("ZR");
	}

	public Optional<SolIndPartner> getInterlocutorPaciente() {
		return getInterlocutorByRol("ZH");
	}

	public Optional<SolIndPartner> getInterlocutorProfesional() {
		return getInterlocutorByRol("ZP");
	}

	/**
	 * @return the prices
	 */
	public Set<SolIndPricing> getPrices() {
		return this.prices;
	}

	/**
	 * @param prices the prices to set
	 */
	public void setPrices(Set<SolIndPricing> prices) {
		this.prices = prices;
	}

	public void addPrice(SolIndPricing price) {
		prices.add(price);
	}

	/**
	 * @return the texts
	 */
	public Set<SolIndTexts> getTexts() {
		return this.texts;
	}

	/**
	 * @param texts the texts to set
	 */
	public void setTexts(Set<SolIndTexts> texts) {
		this.texts = texts;
	}

	/**
	 * @return the items
	 */
	public Set<SolIndItems> getItems() {
		return this.items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Set<SolIndItems> items) {
		this.items = items;
	}

	/**
	 * @return the trazabilidad
	 */
	public TrazabilidadSolicitud getTrazabilidad() {
		return trazabilidad;
	}

	/**
	 * @param trazabilidad the trazabilidad to set
	 */
	public void setTrazabilidad(TrazabilidadSolicitud trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	/**
	 * @return the contrato
	 */
	public ContratoCapitativo getContrato() {
		return contrato;
	}

	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(ContratoCapitativo contrato) {
		this.contrato = contrato;
	}

	public ReglaPrefactura getReglaPrefactura() {
		return reglaPrefactura;
	}

	public void setReglaPrefactura(ReglaPrefactura reglaPrefactura) {
		this.reglaPrefactura = reglaPrefactura;
	}

	public String getCodigoPoliza() {
		return codigoPoliza;
	}

	public void setCodigoPoliza(String codigoPoliza) {
		this.codigoPoliza = codigoPoliza;
	}

	public String getDocumentoUnico() {
		return documentoUnico;
	}

	public void setDocumentoUnico(String documentoUnico) {
		this.documentoUnico = documentoUnico;
	}

	public String getServicioConcertado() {
		return servicioConcertado;
	}

	public void setServicioConcertado(String servicioConcertado) {
		this.servicioConcertado = servicioConcertado;
	}

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public int getTipoPeticion() {
		return tipoPeticion;
	}

	public void setTipoPeticion(int tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}

	public boolean isTratablePrivados() {
		return this.tratablePrivados;
	}

	public boolean getTratablePrivados() {
		return this.tratablePrivados;
	}

	public void setTratablePrivados(boolean tratablePrivados) {
		this.tratablePrivados = tratablePrivados;
	}

	public boolean isBloqueoAutomatico() {
		return this.bloqueoAutomatico;
	}

	public boolean getBloqueoAutomatico() {
		return this.bloqueoAutomatico;
	}

	public void setBloqueoAutomatico(boolean bloqueoAutomatico) {
		this.bloqueoAutomatico = bloqueoAutomatico;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		SolicitudIndividual other = (SolicitudIndividual) obj;
		return Objects.equals(salesOrderInd, other.salesOrderInd)
				&& Objects.equals(assignmentReference, other.assignmentReference)
				&& completeDeliveryIsDefined == other.completeDeliveryIsDefined
				&& Objects.equals(customerPaymentTerms, other.customerPaymentTerms)
				&& Objects.equals(customerPurchaseOrderDate, other.customerPurchaseOrderDate)
				&& Objects.equals(customerPurchaseOrderType, other.customerPurchaseOrderType)
				&& Objects.equals(customerTaxClassification1, other.customerTaxClassification1)
				&& Objects.equals(deliveryBlockReason, other.deliveryBlockReason)
				&& Objects.equals(distributionChannel, other.distributionChannel)
				&& Objects.equals(headerBillingBlockReason, other.headerBillingBlockReason)
				&& Objects.equals(incotermsClassification, other.incotermsClassification)
				&& Objects.equals(incotermsLocation1, other.incotermsLocation1)
				&& Objects.equals(incotermsLocation2, other.incotermsLocation2)
				&& Objects.equals(incotermsTransferLocation, other.incotermsTransferLocation)
				&& Objects.equals(incotermsVersion, other.incotermsVersion) && Objects.equals(items, other.items)
				&& Objects.equals(organizationDivision, other.organizationDivision)
				&& Objects.equals(partners, other.partners) && Objects.equals(paymentMethod, other.paymentMethod)
				&& Objects.equals(prices, other.prices) && Objects.equals(pricingDate, other.pricingDate)
				&& Objects.equals(purchaseOrderByCustomer, other.purchaseOrderByCustomer)
				&& Objects.equals(referenceSDDocument, other.referenceSDDocument)
				&& Objects.equals(requestedDeliveryDate, other.requestedDeliveryDate)
				&& Objects.equals(salesDistrict, other.salesDistrict) && Objects.equals(salesGroup, other.salesGroup)
				&& Objects.equals(salesOffice, other.salesOffice)
				&& Objects.equals(salesOrderDate, other.salesOrderDate)
				&& Objects.equals(salesOrderType, other.salesOrderType)
				&& Objects.equals(salesOrganization, other.salesOrganization)
				&& Objects.equals(sdDocumentReason, other.sdDocumentReason)
				&& Objects.equals(shippingCondition, other.shippingCondition)
				&& Objects.equals(codigoPoliza, other.codigoPoliza)
				&& Objects.equals(documentoUnico, other.documentoUnico)
				&& Objects.equals(servicioConcertado, other.servicioConcertado)
				&& Objects.equals(codigoOperacion, other.codigoOperacion)
				&& Objects.equals(shippingType, other.shippingType) && Objects.equals(soldToParty, other.soldToParty)
				&& Objects.equals(totalAmount, other.totalAmount) && Objects.equals(totalPoints, other.totalPoints)
				&& Objects.equals(totalNetAmount, other.totalNetAmount)
				&& Objects.equals(provinciaDelegacion, other.provinciaDelegacion)
				&& Objects.equals(taxAmount, other.taxAmount)
				&& Objects.equals(transactionCurrency, other.transactionCurrency)
				&& Objects.equals(taxDepartureCountry, other.taxDepartureCountry) && Objects.equals(texts, other.texts)
				&& Objects.equals(tipoPeticion, other.tipoPeticion)
				&& Objects.equals(vatRegistrationCountry, other.vatRegistrationCountry)
				&& Objects.equals(tratablePrivados, other.tratablePrivados)
				&& Objects.equals(bloqueoAutomatico, other.bloqueoAutomatico)
				&& Objects.equals(reglaPrefactura, other.reglaPrefactura);
	}

	@Override
	public boolean transicionar(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicion(this, estadoOrigen, manual);
	}
	
	@Override
	public boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicionV2(this, estadoOrigen, manual);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> obtieneMotivo(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.getMotivo(this, estadoOrigen, manual);
	}

	@Override
	public TrazabilidadSolicitud obtieneTrazabilidad() {
		return this.getTrazabilidad();
	}

	@Override
	public Optional<List<String>> obtieneDestinos() {
		List<String> postSimulacion = new ArrayList<String>();
		postSimulacion.add(Bloqueada.CODIGO);
		postSimulacion.add(Borrada.CODIGO);
		postSimulacion.add(Erronea.CODIGO);
		postSimulacion.add(Facturable.CODIGO);
		postSimulacion.add(ParcialmenteProcesada.CODIGO);
		postSimulacion.add(CreadaRecibida.CODIGO);
		postSimulacion.add(Excluida.CODIGO);
		return Optional.of(postSimulacion);
	}

	@Override
	public Set<String> obtieneAlertas() {
		return this.getTrazabilidad().getPeticionRec().obtieneAlertas();
	}

	@Override
	public SolicitudIndividual obtienePadre() {
		return null;
	}

	@Override
	public String obtieneNivelEntity() {
		return EstadosUtils.NIVEL_CABECERA;
	}

	@Override
	public boolean contieneValidada() {
		return this.obtieneHijos().stream().anyMatch(x -> x.contieneValidada());
	}

	@Override
	public List<SolIndItems> obtieneHijos() {
		return this.getItems().stream().sorted(Comparator.comparingInt(SolIndItems::getSalesOrderIndItem).reversed())
				.collect(Collectors.toList());
	}

}

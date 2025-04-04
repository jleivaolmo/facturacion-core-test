package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.gestionestados.Facturable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Facturada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Procesable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturados;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupada;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolicitudesAgrupadas}.
 *
 * <p>The persistent class. . .T_SolicitudesAgrupadas</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(
	name = "T_SolicitudesAgrupadas",
	indexes = {
		@Index(name = "IDX_bySalesOrderAgr", columnList = "salesOrderAgr", unique = false),
		@Index(name = "IDX_byBillingDocument", columnList = "billingDocument", unique = false),
	}
)
@NoArgsConstructor
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolicitudesAgrupadas extends BasicEntity implements Transicionable<TrazabilidadSolicitudAgrupada> {

	private static final long serialVersionUID = 182288843696415801L;

	@Basic
	private String salesOrderAgr;

	@Basic
	private String billingDocument;

	@Basic
	private String salesOrderType;

	@Basic
	private String salesOrganization;

	@Basic
	private String distributionChannel;

	@Basic
	private String organizationDivision;

	@Basic
	private String salesGroup;

	@Basic
	private String salesOffice;

	@Basic
	private String salesDistrict;

	@Basic
	private String soldToParty;

	@Basic
	private String purchaseOrderByCustomer;

	@Basic
	private String customerPurchaseOrderType;

	@Basic
	private Date customerPurchaseOrderDate;

	@Basic
	private Date salesOrderDate;

	@Basic
	private String transactionCurrency;

	@Basic
	private String sdDocumentReason;

	@Basic
	private Date pricingAgrDate;

	@Basic
	private String requestedDeliveryDate;

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
	private String listaPrecio;

	@Basic
	private String tipoContrato;

	@Basic
	private String cargoPeticion;

	@Basic
	private Date fechaPeticion;

	@Basic
	private String cotizacion;

	@Basic
	private String grupoPrecio;

	@Basic
	@ColumnDefault("false")
	private int tipoPeticion;

	@Basic
	private String textoFactura;

	@Basic
	@Getter
	@Setter
	private String agrupacionKey;

	@Basic
	@Getter
	@Setter
	private String divisorKey;

	@Basic
	@Getter
	@Setter
	private Long basedInObject;

	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="periodosFacturadosId")
	@JsonIdentityReference(alwaysAsId=true)
	@JoinColumn(name = "periodos_facturados")
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private PeriodosFacturados periodosFacturados;

	@Column(name="periodos_facturados_id")
	private Integer periodosFacturadosId;

	@Basic
	@Getter
	@Setter
	private Integer estadoProceso;

	@Column(precision = 16, scale = 3)
	private BigDecimal totalNetAmount;

	@Column(precision = 16, scale = 3)
	private BigDecimal taxAmount;

	@Column(precision = 16, scale = 3)
	private BigDecimal totalAmount;

	@Column(precision = 16, scale = 3)
	private BigDecimal totalPoints;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="solicitudAgr")
	@JsonManagedReference
	private Set<SolAgrPartner> partners = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy="solicitudAgr")
	@JsonManagedReference
	private Set<SolAgrPricing> prices = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy="solicitudAgr")
	@JsonManagedReference
	private Set<SolAgrTexts> texts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy="solicitudAgr")
	@JsonManagedReference
	private Set<SolAgrItems> items = new HashSet<>();

	@OneToMany(mappedBy="solicitudAgr", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<TrazabilidadSolicitudAgrupado> trazabilidad = new HashSet<>();

	@OneToOne(mappedBy = "solicitudesAgrupadas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadSolicitudAgrupada trazabilidadSolicitudAgrupada;

	public int getPeriodosFacturadosId() {
		return periodosFacturadosId;
	}

	public void setPeriodosFacturadosId(Integer periodosFacturadosId) {
		this.periodosFacturadosId = periodosFacturadosId;
	}

	public PeriodosFacturados getPeriodosFacturados() {
		return this.periodosFacturados;
	}

	public void setPeriodosFacturados(PeriodosFacturados periodosFacturados) {
		this.periodosFacturados = periodosFacturados;
	}

	/**
	 * @return the salesOrderAgr
	 */
	public String getSalesOrderAgr() {
		return this.salesOrderAgr;
	}

	/**
	 * @param salesOrderAgr
	 *            the salesOrderAgr to set
	 */
	public void setSalesOrderAgr(String salesOrderAgr) {
		this.salesOrderAgr = salesOrderAgr;
	}

	/**
	 * @return the billingDocument
	 */
	public String getBillingDocument() {
		return this.billingDocument;
	}

	/**
	 * @param billingDocument
	 *            the billingDocument to set
	 */
	public void setBillingDocument(String billingDocument) {
		this.billingDocument = billingDocument;
	}

	/**
	 * @return the salesOrderType
	 */
	public String getSalesOrderType() {
		return this.salesOrderType;
	}

	/**
	 * @param salesOrderType
	 *            the salesOrderType to set
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
	 * @param salesOrganization
	 *            the salesOrganization to set
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
	 * @param distributionChannel
	 *            the distributionChannel to set
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
	 * @param organizationDivision
	 *            the organizationDivision to set
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
	 * @param salesGroup
	 *            the salesGroup to set
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
	 * @param salesOffice
	 *            the salesOffice to set
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
	 * @param salesDistrict
	 *            the salesDistrict to set
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
	 * @param soldToParty
	 *            the soldToParty to set
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
	 * @param purchaseOrderByCustomer
	 *            the purchaseOrderByCustomer to set
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
	 * @param customerPurchaseOrderType
	 *            the customerPurchaseOrderType to set
	 */
	public void setCustomerPurchaseOrderType(String customerPurchaseOrderType) {
		this.customerPurchaseOrderType = customerPurchaseOrderType;
	}

	/**
	 * @return the customerPurchaseOrderDate
	 */
	public Date getCustomerPurchaseOrderDate() {
		return this.customerPurchaseOrderDate;
	}

	/**
	 * @param customerPurchaseOrderDate
	 *            the customerPurchaseOrderDate to set
	 */
	public void setCustomerPurchaseOrderDate(Date customerPurchaseOrderDate) {
		this.customerPurchaseOrderDate = customerPurchaseOrderDate;
	}

	/**
	 * @return the salesOrderDate
	 */
	public Date getSalesOrderDate() {
		return this.salesOrderDate;
	}

	/**
	 * @param salesOrderDate
	 *            the salesOrderDate to set
	 */
	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return this.transactionCurrency;
	}

	/**
	 * @param transactionCurrency
	 *            the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the sDDocumentReason
	 */
	public String getSdDocumentReason() {
		return this.sdDocumentReason;
	}

	/**
	 * @param sDDocumentReason
	 *            the sDDocumentReason to set
	 */
	public void setSdDocumentReason(String sDDocumentReason) {
		sdDocumentReason = sDDocumentReason;
	}

	/**
	 * @return the pricingDate
	 */
	public Date getPricingAgrDate() {
		return this.pricingAgrDate;
	}

	/**
	 * @param pricingDate
	 *            the pricingDate to set
	 */
	public void setPricingAgrDate(Date pricingAgrDate) {
		this.pricingAgrDate = pricingAgrDate;
	}

	/**
	 * @return the requestedDeliveryDate
	 */
	public String getRequestedDeliveryDate() {
		return this.requestedDeliveryDate;
	}

	/**
	 * @param requestedDeliveryDate
	 *            the requestedDeliveryDate to set
	 */
	public void setRequestedDeliveryDate(String requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	/**
	 * @return the shippingCondition
	 */
	public String getShippingCondition() {
		return this.shippingCondition;
	}

	/**
	 * @param shippingCondition
	 *            the shippingCondition to set
	 */
	public void setShippingCondition(String shippingCondition) {
		this.shippingCondition = shippingCondition;
	}

	/**
	 * @return the completeDeliveryIsDefined
	 */
	public boolean isCompleteDeliveryIsDefined() {
		return this.completeDeliveryIsDefined;
	}

	/**
	 * @param completeDeliveryIsDefined
	 *            the completeDeliveryIsDefined to set
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
	 * @param shippingType
	 *            the shippingType to set
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
	 * @param headerBillingBlockReason
	 *            the headerBillingBlockReason to set
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
	 * @param deliveryBlockReason
	 *            the deliveryBlockReason to set
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
	 * @param incotermsClassification
	 *            the incotermsClassification to set
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
	 * @param incotermsTransferLocation
	 *            the incotermsTransferLocation to set
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
	 * @param incotermsLocation1
	 *            the incotermsLocation1 to set
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
	 * @param incotermsLocation2
	 *            the incotermsLocation2 to set
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
	 * @param incotermsVersion
	 *            the incotermsVersion to set
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
	 * @param customerPaymentTerms
	 *            the customerPaymentTerms to set
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
	 * @param paymentMethod
	 *            the paymentMethod to set
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
	 * @param assignmentReference
	 *            the assignmentReference to set
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
	 * @param referenceSDDocument
	 *            the referenceSDDocument to set
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
	 * @param customerTaxClassification1
	 *            the customerTaxClassification1 to set
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
	 * @param taxDepartureCountry
	 *            the taxDepartureCountry to set
	 */
	public void setTaxDepartureCountry(String taxDepartureCountry) {
		this.taxDepartureCountry = taxDepartureCountry;
	}

	/**
	 * @return the vATRegistrationCountry
	 */
	public String getVatRegistrationCountry() {
		return this.vatRegistrationCountry;
	}

	/**
	 * @param vATRegistrationCountry
	 *            the vATRegistrationCountry to set
	 */
	public void setVatRegistrationCountry(String vATRegistrationCountry) {
		vatRegistrationCountry = vATRegistrationCountry;
	}

	/**
	 *
	 * @return
	 */
	public String getTextoFactura() {
		return textoFactura;
	}

	/**
	 *
	 * @param textoFactura
	 */
	public void setTextoFactura(String textoFactura) {
		this.textoFactura = textoFactura;
	}

	/**
	 * @return the partners
	 */
	public Set<SolAgrPartner> getPartners() {
		return this.partners;
	}

	public void addPartner(SolAgrPartner partner) {
		this.partners.add(partner);
	}

	/**
	 * @param partners
	 *            the partners to set
	 */
	public void setPartners(Set<SolAgrPartner> partners) {
		this.partners = partners;
	}

	/**
	 * @return the prices
	 */
	public Set<SolAgrPricing> getPrices() {
		return this.prices;
	}

	public void addPrice(SolAgrPricing price) {
		getPrices().add(price);
	}

	/**
	 * @param prices
	 *            the prices to set
	 */
	public void setPrices(Set<SolAgrPricing> prices) {
		this.prices = prices;
	}

	/**
	 * @return the texts
	 */
	public Set<SolAgrTexts> getTexts() {
		return this.texts;
	}

	/**
	 * @param texts
	 *            the texts to set
	 */
	public void setTexts(Set<SolAgrTexts> texts) {
		this.texts = texts;
	}

	/**
	 * @return the items
	 */
	public Set<SolAgrItems> getItems() {
		return this.items;
	}

	public void addItem(SolAgrItems item) {
		this.items.add(item);
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Set<SolAgrItems> items) {
		this.items = items;
	}

	public String getListaPrecio() {
		return listaPrecio;
	}

	public void setListaPrecio(String listaPrecio) {
		this.listaPrecio = listaPrecio;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getCargoPeticion() {
		return cargoPeticion;
	}

	public void setCargoPeticion(String cargoPeticion) {
		this.cargoPeticion = cargoPeticion;
	}

	public Date getFechaPeticion() {
		return fechaPeticion;
	}

	public void setFechaPeticion(Date fechaPeticion) {
		this.fechaPeticion = fechaPeticion;
	}

	public String getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getGrupoPrecio() {
		return grupoPrecio;
	}

	public void setGrupoPrecio(String grupoPrecio) {
		this.grupoPrecio = grupoPrecio;
	}

	public int getTipoPeticion() {
		return tipoPeticion;
	}

	public void setTipoPeticion(int tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}

	public BigDecimal getTotalNetAmount() {
		return totalNetAmount;
	}

	public void setTotalNetAmount(BigDecimal totalNetAmount) {

		this.totalNetAmount = totalNetAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(BigDecimal totalPoints) {
		this.totalPoints = totalPoints;
	}

	public TrazabilidadSolicitudAgrupada getTrazabilidadSolicitudAgrupada() {
		return this.trazabilidadSolicitudAgrupada;
	}

	public void setTrazabilidadSolicitudAgrupada(TrazabilidadSolicitudAgrupada trz) {
		this.trazabilidadSolicitudAgrupada = trz;
	}

	/**
	 * @return the trazabilidad
	 */
	public Set<TrazabilidadSolicitudAgrupado> getTrazabilidad() {
		return trazabilidad;
	}

	/**
	 * @param trazabilidad the trazabilidad to set
	 */
	public void setTrazabilidad(Set<TrazabilidadSolicitudAgrupado> trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public void addTrazabilidad(TrazabilidadSolicitudAgrupado trazabilidad) {
		this.trazabilidad.add(trazabilidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SolicitudesAgrupadas other = (SolicitudesAgrupadas) obj;
		return 	Objects.equals(assignmentReference, other.assignmentReference) &&
				completeDeliveryIsDefined == other.completeDeliveryIsDefined &&
				Objects.equals(customerPaymentTerms, other.customerPaymentTerms) &&
				Objects.equals(customerPurchaseOrderDate, other.customerPurchaseOrderDate) &&
				Objects.equals(customerPurchaseOrderType, other.customerPurchaseOrderType) &&
				Objects.equals(customerTaxClassification1, other.customerTaxClassification1) &&
				Objects.equals(deliveryBlockReason, other.deliveryBlockReason) &&
				Objects.equals(distributionChannel, other.distributionChannel) &&
				Objects.equals(headerBillingBlockReason, other.headerBillingBlockReason) &&
				Objects.equals(incotermsClassification, other.incotermsClassification) &&
				Objects.equals(incotermsLocation1, other.incotermsLocation1) &&
				Objects.equals(incotermsLocation2, other.incotermsLocation2) &&
				Objects.equals(incotermsTransferLocation, other.incotermsTransferLocation) &&
				Objects.equals(incotermsVersion, other.incotermsVersion) &&
				Objects.equals(organizationDivision, other.organizationDivision) &&
				Objects.equals(paymentMethod, other.paymentMethod) &&
				Objects.equals(pricingAgrDate, other.pricingAgrDate) &&
				Objects.equals(purchaseOrderByCustomer, other.purchaseOrderByCustomer) &&
				Objects.equals(referenceSDDocument, other.referenceSDDocument) &&
				Objects.equals(requestedDeliveryDate, other.requestedDeliveryDate) &&
				Objects.equals(sdDocumentReason, other.sdDocumentReason) &&
				Objects.equals(salesDistrict, other.salesDistrict) &&
				Objects.equals(salesGroup, other.salesGroup) &&
				Objects.equals(salesOffice, other.salesOffice) &&
				Objects.equals(salesOrderAgr, other.salesOrderAgr) &&
				Objects.equals(billingDocument, other.billingDocument) &&
				Objects.equals(salesOrderDate, other.salesOrderDate) &&
				Objects.equals(salesOrderType, other.salesOrderType) &&
				Objects.equals(salesOrganization, other.salesOrganization) &&
				Objects.equals(shippingCondition, other.shippingCondition) &&
				Objects.equals(shippingType, other.shippingType) &&
				Objects.equals(soldToParty, other.soldToParty) &&
				Objects.equals(taxDepartureCountry, other.taxDepartureCountry) &&
				Objects.equals(transactionCurrency, other.transactionCurrency) &&
				Objects.equals(vatRegistrationCountry, other.vatRegistrationCountry) &&
				Objects.equals(listaPrecio, other.listaPrecio) &&
				Objects.equals(tipoContrato, other.tipoContrato) &&
				Objects.equals(cargoPeticion, other.cargoPeticion) &&
				Objects.equals(fechaPeticion, other.fechaPeticion) &&
				Objects.equals(cotizacion, other.cotizacion) &&
				Objects.equals(grupoPrecio, other.grupoPrecio) &&
				Objects.equals(tipoPeticion, other.tipoPeticion) &&
				Objects.equals(totalAmount, other.totalAmount) &&
				Objects.equals(taxAmount, other.taxAmount) &&
				Objects.equals(totalNetAmount, other.totalNetAmount) &&
				Objects.equals(totalPoints, other.totalPoints);
	}

	@Override
	public boolean transicionar(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicion(this, estadoOrigen, manual);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> obtieneMotivo(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.getMotivo(this, estadoOrigen);
	}

	@Override
	public TrazabilidadSolicitudAgrupada obtieneTrazabilidad() {
		return getTrazabilidadSolicitudAgrupada();
	}

	@Override
	public Optional<List<String>> obtieneDestinos() {
		List<String> destinos = new ArrayList<>();
		destinos.add(Facturable.CODIGO);
		destinos.add(Facturada.CODIGO);
		return Optional.of(destinos);
	}

	@Override
	public Set<String> obtieneAlertas() {
		return null;
	}

	@Override
	public List<SolAgrItems> obtieneHijos() {
		return this.getItems().stream().sorted(Comparator.comparingInt(SolAgrItems::getSalesOrderAgrItem).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public SolicitudesAgrupadas obtienePadre() {
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
	public BasicEntity copyWithoutId() {
		final SolicitudesAgrupadas target = (SolicitudesAgrupadas) super.copyWithoutId();

		copyFieldAndSetWithoutId(this::getTrazabilidadSolicitudAgrupada, target::setTrazabilidadSolicitudAgrupada);
		copyAndSetWithoutId(this::getPartners, target::setPartners);
		copyAndSetWithoutId(this::getPrices, target::setPrices);
		copyAndSetWithoutId(this::getTexts, target::setTexts);
		copyAndSetWithoutId(this::getItems, target::setItems);
		copyAndSetWithoutId(this::getTrazabilidad, target::setTrazabilidad);

		return target;
	}

	@Override
	protected Set<String> getCopyWithoutIdBlacklistFields() {
		final Set<String> blacklistFields = super.getCopyWithoutIdBlacklistFields();
		blacklistFields.add("trazabilidadSolicitudAgrupada");
		blacklistFields.add("partners");
		blacklistFields.add("prices");
		blacklistFields.add("texts");
		blacklistFields.add("items");
		blacklistFields.add("trazabilidad");

		return blacklistFields;
	}

	@Override
	public boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicionV2(this, estadoOrigen, manual);
	}
}

package com.echevarne.sap.cloud.facturacion.model.solicitudindividual;

import static javax.persistence.CascadeType.PERSIST;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.*;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolIndItems}.
 *
 * <p>
 * The persistent class. . .T_SolIndItems
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
		name = "T_SolIndItems",
		indexes = {
			@Index(name = "SolIndItems_bySalesOrderIndItem", columnList = "salesOrderIndItem", unique = false),
		}
)
public class SolIndItems extends BasicEntity implements Transicionable<Trazabilidad> {

	/**
	 * Universal serial ID for serialization
	 */
	private static final long serialVersionUID = 1696772613309234697L;


	@Basic
	private int salesOrderIndItem;
	@Basic
	@ColumnDefault("false")
	private int higherLevelltem;
	@Basic
	private String salesOrderItemCategory;
	@Basic
	private String salesOrderItemText;
	@Basic
	private String purchaseOrderByCustomer;
	@Basic
	private String material;
	@Basic
	private String materialByCustomer;
	@Basic
	private Timestamp pricingDate;

	@Basic
	@Column(precision = 16, scale = 3)
	private BigDecimal requestedQuantity;

	@Basic
	private String requestedQuantityUnit;
	@Basic
	private String materialGroup;
	@Basic
	private String materialPricingGroup;
	@Basic
	private String batch;
	@Basic
	private String productionPlant;
	@Basic
	private String storageLocation;
	@Basic
	private String deliveryGroup;
	@Basic
	private String shippingPoint;
	@Basic
	private String shippingType;
	@Basic
	private String deliveryPriority;
	@Basic
	private String incotermsClassification;
	@Basic
	private String incotermsTransferLocation;
	@Basic
	private String incotermsLocation1;
	@Basic
	private String incotermsLocation2;
	@Basic
	private String customerPaymentTerms;
	@Basic
	private String salesDocumentRjcnReason;
	@Basic
	private String itemBilingBlockReason;
	@Basic
	private String wbsElement;
	@Basic
	private String profitCenter;
	@Basic
	private String referenceSDDocument;
	@Basic
	private String referenceSDDocumentItem;
	@Basic
	private String transactionCurrency;
	@Column(precision = 16, scale = 3)
	private BigDecimal netAmount;
	@Column(precision = 16, scale = 2)
	private BigDecimal pointAmount;
	@Column(precision = 16, scale = 3)
	private BigDecimal taxAmount;
	@Basic
	private String delProductiva;
	@Basic
	private String priceReferenceMaterial;
	@Basic
	private String unidadProductiva;
	@Basic
	private Date fechaValidacion;
	@Basic
	private String organizationDivision;
	@Basic
	private String grupoSector;
	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean esPrecioEspecial;
	@Basic
	private String codigoBaremo;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudIndividual", nullable = false)
	@JsonBackReference
	private SolicitudIndividual solicitudInd;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "posicion")
	@JsonManagedReference
	private Set<SolIndItemPartner> partners = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "posicion")
	@JsonManagedReference
	private Set<SolIndItemPricing> prices = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "posicion")
	@JsonManagedReference
	private Set<SolIndItemTexts> texts = new HashSet<>();

	@OneToOne(mappedBy = "itemInd", cascade = { PERSIST }, fetch = FetchType.LAZY) // not REMOVE
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private Trazabilidad trazabilidad;

	/**
	 * @return the salesOrderItem
	 */
	public int getSalesOrderIndItem() {
		return this.salesOrderIndItem;
	}

	/**
	 * @param salesOrderItem the salesOrderItem to set
	 */
	public void setSalesOrderIndItem(int salesOrderItem) {
		this.salesOrderIndItem = salesOrderItem;
	}

	/**
	 * @return the higherLevelltem
	 */
	public int getHigherLevelltem() {
		return this.higherLevelltem;
	}

	/**
	 * @param higherLevelltem the higherLevelltem to set
	 */
	public void setHigherLevelltem(int higherLevelltem) {
		this.higherLevelltem = higherLevelltem;
	}

	/**
	 * @return the salesOrderItemCategory
	 */
	public String getSalesOrderItemCategory() {
		return this.salesOrderItemCategory;
	}

	/**
	 * @param salesOrderItemCategory the salesOrderItemCategory to set
	 */
	public void setSalesOrderItemCategory(String salesOrderItemCategory) {
		this.salesOrderItemCategory = salesOrderItemCategory;
	}

	/**
	 * @return the salesOrderItemText
	 */
	public String getSalesOrderItemText() {
		return this.salesOrderItemText;
	}

	/**
	 * @param salesOrderItemText the salesOrderItemText to set
	 */
	public void setSalesOrderItemText(String salesOrderItemText) {
		this.salesOrderItemText = salesOrderItemText;
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
	 * @return the material
	 */
	public String getMaterial() {
		return this.material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * @return the materialByCustomer
	 */
	public String getMaterialByCustomer() {
		return this.materialByCustomer;
	}

	/**
	 * @param materialByCustomer the materialByCustomer to set
	 */
	public void setMaterialByCustomer(String materialByCustomer) {
		this.materialByCustomer = materialByCustomer;
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
	 * @return the requestedQuantity
	 */
	public BigDecimal getRequestedQuantity() {
		return this.requestedQuantity;
	}

	/**
	 * @param requestedQuantity the requestedQuantity to set
	 */
	public void setRequestedQuantity(BigDecimal requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	/**
	 * @return the requestedQuantityUnit
	 */
	public String getRequestedQuantityUnit() {
		return this.requestedQuantityUnit;
	}

	/**
	 * @param requestedQuantityUnit the requestedQuantityUnit to set
	 */
	public void setRequestedQuantityUnit(String requestedQuantityUnit) {
		this.requestedQuantityUnit = requestedQuantityUnit;
	}

	/**
	 * @return the materialGroup
	 */
	public String getMaterialGroup() {
		return this.materialGroup;
	}

	/**
	 * @param materialGroup the materialGroup to set
	 */
	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	/**
	 * @return the materialPricingGroup
	 */
	public String getMaterialPricingGroup() {
		return this.materialPricingGroup;
	}

	/**
	 * @param materialPricingGroup the materialPricingGroup to set
	 */
	public void setMaterialPricingGroup(String materialPricingGroup) {
		this.materialPricingGroup = materialPricingGroup;
	}

	/**
	 * @return the batch
	 */
	public String getBatch() {
		return this.batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}

	/**
	 * @return the productionPlant
	 */
	public String getProductionPlant() {
		return this.productionPlant;
	}

	/**
	 * @param productionPlant the productionPlant to set
	 */
	public void setProductionPlant(String productionPlant) {
		this.productionPlant = productionPlant;
	}

	/**
	 * @return the storageLocation
	 */
	public String getStorageLocation() {
		return this.storageLocation;
	}

	/**
	 * @param storageLocation the storageLocation to set
	 */
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	/**
	 * @return the deliveryGroup
	 */
	public String getDeliveryGroup() {
		return this.deliveryGroup;
	}

	/**
	 * @param deliveryGroup the deliveryGroup to set
	 */
	public void setDeliveryGroup(String deliveryGroup) {
		this.deliveryGroup = deliveryGroup;
	}

	/**
	 * @return the shippingPoint
	 */
	public String getShippingPoint() {
		return this.shippingPoint;
	}

	/**
	 * @param shippingPoint the shippingPoint to set
	 */
	public void setShippingPoint(String shippingPoint) {
		this.shippingPoint = shippingPoint;
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
	 * @return the deliveryPriority
	 */
	public String getDeliveryPriority() {
		return this.deliveryPriority;
	}

	/**
	 * @param deliveryPriority the deliveryPriority to set
	 */
	public void setDeliveryPriority(String deliveryPriority) {
		this.deliveryPriority = deliveryPriority;
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
	 * @return the salesDocumentRjcnReason
	 */
	public String getSalesDocumentRjcnReason() {
		return this.salesDocumentRjcnReason;
	}

	/**
	 * @param salesDocumentRjcnReason the salesDocumentRjcnReason to set
	 */
	public void setSalesDocumentRjcnReason(String salesDocumentRjcnReason) {
		this.salesDocumentRjcnReason = salesDocumentRjcnReason;
	}

	/**
	 * @return the itemBilingBlockReason
	 */
	public String getItemBilingBlockReason() {
		return this.itemBilingBlockReason;
	}

	/**
	 * @param itemBilingBlockReason the itemBilingBlockReason to set
	 */
	public void setItemBilingBlockReason(String itemBilingBlockReason) {
		this.itemBilingBlockReason = itemBilingBlockReason;
	}

	/**
	 * @return the wbsElement
	 */
	public String getWbsElement() {
		return this.wbsElement;
	}

	/**
	 * @param wbsElement the wbsElement to set
	 */
	public void setWbsElement(String wbsElement) {
		this.wbsElement = wbsElement;
	}

	/**
	 * @return the profitCenter
	 */
	public String getProfitCenter() {
		return this.profitCenter;
	}

	/**
	 * @param profitCenter the profitCenter to set
	 */
	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
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
	 * @return the referenceSDDocumentItem
	 */
	public String getReferenceSDDocumentItem() {
		return this.referenceSDDocumentItem;
	}

	/**
	 * @param referenceSDDocumentItem the referenceSDDocumentItem to set
	 */
	public void setReferenceSDDocumentItem(String referenceSDDocumentItem) {
		this.referenceSDDocumentItem = referenceSDDocumentItem;
	}

	/**
	 * @return the partners
	 */
	public Set<SolIndItemPartner> getPartners() {
		return this.partners;
	}

	/**
	 * @param partners the partners to set
	 */
	public void setPartners(Set<SolIndItemPartner> partners) {
		this.partners = partners;
	}

	/**
	 * @return the prices
	 */
	public Set<SolIndItemPricing> getPrices() {
		return this.prices;
	}

	/**
	 * @param prices the prices to set
	 */
	public void setPrices(Set<SolIndItemPricing> prices) {
		this.prices = prices;
	}

	/**
	 * @return the texts
	 */
	public Set<SolIndItemTexts> getTexts() {
		return this.texts;
	}

	/**
	 * @param texts the texts to set
	 */
	public void setTexts(Set<SolIndItemTexts> texts) {
		this.texts = texts;
	}

	/**
	 * @return the solicitudInd
	 */
	public SolicitudIndividual getSolicitudInd() {
		return solicitudInd;
	}

	/**
	 * @param solicitudInd the solicitudInd to set
	 */
	public void setSolicitudInd(SolicitudIndividual solicitudInd) {
		this.solicitudInd = solicitudInd;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * @param transactionCurrency the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the netAmount
	 */
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}

	/**
	 * @return the taxAmount
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the trazabilidad
	 */
	public Trazabilidad getTrazabilidad() {
		return trazabilidad;
	}

	/**
	 * @param trazabilidad the trazabilidad to set
	 */
	public void setTrazabilidad(Trazabilidad trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	/**
	 * @return the delProductiva
	 */
	public String getDelProductiva() {
		return delProductiva;
	}

	/**
	 * @param delProductiva the delProductiva to set
	 */
	public void setDelProductiva(String delProductiva) {
		this.delProductiva = delProductiva;
	}

	/**
	 * @return the priceReferenceMaterial
	 */
	public String getPriceReferenceMaterial() {
		return priceReferenceMaterial;
	}

	/**
	 * @param priceReferenceMaterial the priceReferenceMaterial to set
	 */
	public void setPriceReferenceMaterial(String priceReferenceMaterial) {
		this.priceReferenceMaterial = priceReferenceMaterial;
	}

	public String getUnidadProductiva() {
		return unidadProductiva;
	}

	public void setUnidadProductiva(String unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
	}

	/**
	 * @return the fechaValidacion
	 */
	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	/**
	 * @param fechaValidacion the fechaValidacion to set
	 */
	public void setFechaValidacion(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	/**
	 * @return the organizationDivision
	 */
	public String getOrganizationDivision() {
		return organizationDivision;
	}

	/**
	 * @param organizationDivision the organizationDivision to set
	 */
	public void setOrganizationDivision(String organizationDivision) {
		this.organizationDivision = organizationDivision;
	}

	public String getGrupoSector() {
		return grupoSector;
	}

	public void setGrupoSector(String grupoSector) {
		this.grupoSector = grupoSector;
	}

	public boolean isEsPrecioEspecial() {
		return esPrecioEspecial;
	}

	public void setEsPrecioEspecial(boolean esPrecioEspecial) {
		this.esPrecioEspecial = esPrecioEspecial;
	}

	public String getCodigoBaremo() {
		return codigoBaremo;
	}

	public void setCodigoBaremo(String codigoBaremo) {
		this.codigoBaremo = codigoBaremo;
	}
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		SolIndItems other = (SolIndItems) obj;
		if (batch == null) {
			if (other.batch != null)
				return false;
		} else if (!batch.equals(other.batch))
			return false;
		if (customerPaymentTerms == null) {
			if (other.customerPaymentTerms != null)
				return false;
		} else if (!customerPaymentTerms.equals(other.customerPaymentTerms))
			return false;
		if (deliveryGroup == null) {
			if (other.deliveryGroup != null)
				return false;
		} else if (!deliveryGroup.equals(other.deliveryGroup))
			return false;
		if (deliveryPriority == null) {
			if (other.deliveryPriority != null)
				return false;
		} else if (!deliveryPriority.equals(other.deliveryPriority))
			return false;
		if (higherLevelltem != other.higherLevelltem)
			return false;
		if (incotermsClassification == null) {
			if (other.incotermsClassification != null)
				return false;
		} else if (!incotermsClassification.equals(other.incotermsClassification))
			return false;
		if (incotermsLocation1 == null) {
			if (other.incotermsLocation1 != null)
				return false;
		} else if (!incotermsLocation1.equals(other.incotermsLocation1))
			return false;
		if (incotermsLocation2 == null) {
			if (other.incotermsLocation2 != null)
				return false;
		} else if (!incotermsLocation2.equals(other.incotermsLocation2))
			return false;
		if (incotermsTransferLocation == null) {
			if (other.incotermsTransferLocation != null)
				return false;
		} else if (!incotermsTransferLocation.equals(other.incotermsTransferLocation))
			return false;
		if (itemBilingBlockReason == null) {
			if (other.itemBilingBlockReason != null)
				return false;
		} else if (!itemBilingBlockReason.equals(other.itemBilingBlockReason))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (materialByCustomer == null) {
			if (other.materialByCustomer != null)
				return false;
		} else if (!materialByCustomer.equals(other.materialByCustomer))
			return false;
		if (materialGroup == null) {
			if (other.materialGroup != null)
				return false;
		} else if (!materialGroup.equals(other.materialGroup))
			return false;
		if (materialPricingGroup == null) {
			if (other.materialPricingGroup != null)
				return false;
		} else if (!materialPricingGroup.equals(other.materialPricingGroup))
			return false;
		if (netAmount == null) {
			if (other.netAmount != null)
				return false;
		} else if (!netAmount.equals(other.netAmount))
			return false;
		if (pointAmount == null) {
			if (other.pointAmount != null)
				return false;
		} else if (!pointAmount.equals(other.pointAmount))
			return false;
		if (pricingDate == null) {
			if (other.pricingDate != null)
				return false;
		} else if (!pricingDate.equals(other.pricingDate))
			return false;
		if (productionPlant == null) {
			if (other.productionPlant != null)
				return false;
		} else if (!productionPlant.equals(other.productionPlant))
			return false;
		if (profitCenter == null) {
			if (other.profitCenter != null)
				return false;
		} else if (!profitCenter.equals(other.profitCenter))
			return false;
		if (purchaseOrderByCustomer == null) {
			if (other.purchaseOrderByCustomer != null)
				return false;
		} else if (!purchaseOrderByCustomer.equals(other.purchaseOrderByCustomer))
			return false;
		if (referenceSDDocument == null) {
			if (other.referenceSDDocument != null)
				return false;
		} else if (!referenceSDDocument.equals(other.referenceSDDocument))
			return false;
		if (referenceSDDocumentItem == null) {
			if (other.referenceSDDocumentItem != null)
				return false;
		} else if (!referenceSDDocumentItem.equals(other.referenceSDDocumentItem))
			return false;
		if (requestedQuantity == null) {
			if (other.requestedQuantity != null)
				return false;
		} else if (!requestedQuantity.equals(other.requestedQuantity))
			return false;
		if (requestedQuantityUnit == null) {
			if (other.requestedQuantityUnit != null)
				return false;
		} else if (!requestedQuantityUnit.equals(other.requestedQuantityUnit))
			return false;
		if (salesDocumentRjcnReason == null) {
			if (other.salesDocumentRjcnReason != null)
				return false;
		} else if (!salesDocumentRjcnReason.equals(other.salesDocumentRjcnReason))
			return false;
		if (salesOrderIndItem != other.salesOrderIndItem)
			return false;
		if (salesOrderItemCategory == null) {
			if (other.salesOrderItemCategory != null)
				return false;
		} else if (!salesOrderItemCategory.equals(other.salesOrderItemCategory))
			return false;
		if (fechaValidacion == null) {
			if (other.fechaValidacion != null)
				return false;
		} else if (!fechaValidacion.equals(other.fechaValidacion))
			return false;
		if (salesOrderItemText == null) {
			if (other.salesOrderItemText != null)
				return false;
		} else if (!salesOrderItemText.equals(other.salesOrderItemText))
			return false;
		if (shippingPoint == null) {
			if (other.shippingPoint != null)
				return false;
		} else if (!shippingPoint.equals(other.shippingPoint))
			return false;
		if (shippingType == null) {
			if (other.shippingType != null)
				return false;
		} else if (!shippingType.equals(other.shippingType))
			return false;
		if (storageLocation == null) {
			if (other.storageLocation != null)
				return false;
		} else if (!storageLocation.equals(other.storageLocation))
			return false;
		if (taxAmount == null) {
			if (other.taxAmount != null)
				return false;
		} else if (!taxAmount.equals(other.taxAmount))
			return false;
		if (transactionCurrency == null) {
			if (other.transactionCurrency != null)
				return false;
		} else if (!transactionCurrency.equals(other.transactionCurrency))
			return false;
		if (wbsElement == null) {
			if (other.wbsElement != null)
				return false;
		} else if (!wbsElement.equals(other.wbsElement))
			return false;
		if (delProductiva == null) {
			if (other.delProductiva != null)
				return false;
		} else if (!delProductiva.equals(other.delProductiva))
			return false;
		if (priceReferenceMaterial == null) {
			if (other.priceReferenceMaterial != null)
				return false;
		} else if (!priceReferenceMaterial.equals(other.priceReferenceMaterial))
			return false;
		if (unidadProductiva == null) {
			if (other.unidadProductiva != null)
				return false;
		} else if (!unidadProductiva.equals(other.unidadProductiva))
			return false;
		if (organizationDivision == null) {
			if (other.organizationDivision != null)
				return false;
		} else if (!organizationDivision.equals(other.organizationDivision))
			return false;
		return true;
	}

	@Override
	public boolean transicionar(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicion(this, estadoOrigen, manual);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> obtieneMotivo(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.getMotivo(this, estadoOrigen, manual);
	}

	@Override
	public Trazabilidad obtieneTrazabilidad() {
		return this.getTrazabilidad();
	}

	@Override
	public Optional<List<String>> obtieneDestinos() {
		List<String> postSimulacion = new ArrayList<String>();
		postSimulacion.add(Facturable.CODIGO);
		postSimulacion.add(Erronea.CODIGO);
		postSimulacion.add(Excluida.CODIGO);
		postSimulacion.add(CreadaRecibida.CODIGO);
		return Optional.of(postSimulacion);
	}

	@Override
	public Set<String> obtieneAlertas() {
		return Collections.emptySet();
	}

	@Override
	public SolicitudIndividual obtienePadre() {
		return this.getSolicitudInd();
	}

	@Override
	public String obtieneNivelEntity() {
		return EstadosUtils.NIVEL_POSICION;
	}

	@Override
	public boolean contieneValidada() {
		return this.getTrazabilidad().getEstados().stream().anyMatch(x -> ValidadaAutorizada.CODIGO.equals(x.getEstado().getCodeEstado()))
				|| this.getTrazabilidad().getItemRec().contieneValidada();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolIndItems> obtieneHijos() {
		return Collections.emptyList();
	}

	public Optional<SolIndItemPricing> obtenerPriceBrutoOModificado() {
		return prices.stream().filter(pr -> pr.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_BRUTO) ||
				pr.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_MODIFICADO)).findAny();
	}

	public Optional<SolIndItemPricing> obtenerImpuestos() {
		return prices.stream().filter(pr -> pr.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_IVA)).findAny();
	}

	public boolean estaTotalmenteFacturadaONoEsFacturable() {
		return checkEstado(Arrays.asList(Facturada.CODIGO, Excluida.CODIGO, Borrada.CODIGO));
	}

	public boolean estaFacturada() {
		return checkEstado(Arrays.asList(Facturada.CODIGO, PedidoCreado.CODIGO, Prefacturada.CODIGO, CreandoPedido.CODIGO));
	}
	
    public boolean esFacturable() {
		return checkEstado(Facturable.CODIGO);
    }

    public boolean esErronea() {
		return checkEstado(Erronea.CODIGO);
	}

	private boolean checkEstado(String codeEstado) {
		return checkEstado(Arrays.asList(codeEstado));
	}

	private boolean checkEstado(List<String> codesEstado) {
		if (codesEstado ==null || this.getTrazabilidad()==null || this.getTrazabilidad().getUltimoEstado()==null) return false;
		return codesEstado.stream().anyMatch(x -> x.equals(this.getTrazabilidad().getUltimoEstado()));
	}
	
	@Override
	public boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicionV2(this, estadoOrigen, manual);
	}


}

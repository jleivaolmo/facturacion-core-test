package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.gestionestados.Facturable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Facturada;
import com.echevarne.sap.cloud.facturacion.gestionestados.PedidoCreado;
import com.echevarne.sap.cloud.facturacion.gestionestados.Procesable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItems;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolAgrItems}.
 *
 * <p>The persistent class. . .T_SolAgrItems</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 */
@SqlResultSetMapping(
	    name = "GroupedSolIndItemsMapping",
	    classes = @ConstructorResult(
	        targetClass = GroupedSolIndItems.class,
	        columns = {
	            @ColumnResult(name = "count"),
	            @ColumnResult(name = "TIPOPOSICION"),
	            @ColumnResult(name = "MATERIAL"),
	            @ColumnResult(name = "PRICEREFERENCEMATERIAL"),
	            @ColumnResult(name = "PRODUCTIONPLANT"),
	            @ColumnResult(name = "PROFITCENTER"),
	            @ColumnResult(name = "DELPRODUCTIVA"),
	            @ColumnResult(name = "CODIGODELEGACION"),
	            @ColumnResult(name = "GRUPOPRECIO"),
	            @ColumnResult(name = "LISTAPRECIO"),
	            @ColumnResult(name = "TIPOPETICION"),
	            @ColumnResult(name = "ORGANIZATIONDIVISION")
	        }
	    )
	)

@Entity
@Table(name = "T_SolAgrItems")
@NoArgsConstructor
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrItems extends BasicEntity implements Transicionable<TrazabilidadSolAgrItems> {

	private static final long serialVersionUID = 3551253797925855985L;

	@Basic
	@ColumnDefault("false")
	private int salesOrderAgrItem;
	@Basic
	@ColumnDefault("false")
	private int higherLevelltem;
	@Basic
	private String salesOrderItemCategory;
	@Basic
	private String purchaseOrderByCustomer;
	@Basic
	private String material;
	@Basic
	private String pricingDate;
	@Basic
	private BigDecimal requestedQuantity;
	@Basic
	private String productionPlant;
	@Basic
	private String profitCenter;
	@Basic
	@Column(precision = 16, scale = 3)
	private BigDecimal netAmount;
	@Basic
	@Column(precision = 16, scale = 3)
	private BigDecimal taxAmount;
	@Column(precision = 16, scale = 2)
	private BigDecimal pointAmount;
	@Basic
	private String delProductiva;
	@Basic
	private String priceReferenceMaterial;
	@Basic
	private String referenceSDDocument;
	@Basic
	private String referenceSDDocumentItem;
	@Basic
	private String transactionCurrency;
	@Basic
	private String unidadProductiva;
	@Basic
	private Date fechaValidacion;
	@Basic
	private String oficinaVentas;
	@Basic
	@ColumnDefault("false")
	private int tipoPeticion;
	@Basic
	private String grupoPrecio;
	@Basic
	private String listaPrecio;
	@Basic
	private String servicioConcertado;
	@Basic
	private String documentoUnico;
	@Basic
	private String codigoPoliza;
	@Basic
	private String codigoOperacion;
	@Basic
	private String operacionSeoga;
	@Basic
	private String codigoBloqueo;
	@Basic
	private String organizationDivision;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	@JoinColumn(name = "fk_SolicitudAgrupada", nullable = false)
	private SolicitudesAgrupadas solicitudAgr;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "posicion")
	@JsonManagedReference
	private Set<SolAgrItemPartner> partners = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "posicion", orphanRemoval = true)
	@JsonManagedReference
	private Set<SolAgrItemPricing> prices = new HashSet<>();

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "itemAgr")
	@JsonIgnore
	private Set<Trazabilidad> trazabilidades = new HashSet<>();

	@OneToOne(mappedBy = "solAgrItems", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadSolAgrItems trazabilidad;

	public SolAgrItems(SolAgrItemsKey key, SolicitudesAgrupadas solicitudAgr) {
		this.solicitudAgr = solicitudAgr;
		material = key.getMaterial();
		priceReferenceMaterial = key.getPriceReferenceMaterial();
		productionPlant = key.getProductionPlant();
		profitCenter = key.getProfitCenter();
		delProductiva = key.getDelProductiva();
		grupoPrecio = key.getGrupoPrecio();
		listaPrecio = key.getListaPrecio();
		tipoPeticion = key.getTipoPeticion();
		salesOrderItemCategory = key.getTipoPosicion();
		requestedQuantity = new BigDecimal(0);
		netAmount = new BigDecimal(0);
		taxAmount = new BigDecimal(0);
		pointAmount = new BigDecimal(0);
		trazabilidades = new HashSet<>();
		partners = new HashSet<>();
		prices = new HashSet<>();
		trazabilidad = new TrazabilidadSolAgrItems();
		trazabilidad.setSolAgrItems(this);
		organizationDivision = key.getOrganizationDivision();
	}


	public TrazabilidadSolAgrItems getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(TrazabilidadSolAgrItems trazabilidad) {
		this.trazabilidad = trazabilidad;
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

	public void addRequestedQuantity(BigDecimal requestedQuantity) {
		setRequestedQuantity(this.requestedQuantity.add(requestedQuantity));
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
	 * @return the partners
	 */
	public Set<SolAgrItemPartner> getPartners() {
		return this.partners;
	}

	public void addPartner(SolAgrItemPartner partner) {
		this.partners.add(partner);
	}

	/**
	 * @param partners the partners to set
	 */
	public void setPartners(Set<SolAgrItemPartner> partners) {
		this.partners = partners;
	}

	/**
	 * @return the prices
	 */
	public Set<SolAgrItemPricing> getPrices() {
		return this.prices;
	}

	public void addPrice(SolAgrItemPricing price) {
		this.prices.add(price);
	}

	/**
	 * @param prices the prices to set
	 */
	public void setPrices(Set<SolAgrItemPricing> prices) {
		this.prices = prices;
	}

	/**
	 * @return the solicitudAgr
	 */
	public SolicitudesAgrupadas getSolicitudAgr() {
		return solicitudAgr;
	}

	/**
	 * @param solicitudAgr the solicitudAgr to set
	 */
	public void setSolicitudAgr(SolicitudesAgrupadas solicitudAgr) {
		this.solicitudAgr = solicitudAgr;
	}

	/**
	 * @return the trazabilidad
	 */
	public Set<Trazabilidad> getTrazabilidades() {
		return trazabilidades;
	}

	public void addTrazabilidad(Trazabilidad trazabilidad) {
		trazabilidades.add(trazabilidad);
	}

	/**
	 * @param trazabilidades the trazabilidad to set
	 */
	public void setTrazabilidades(Set<Trazabilidad> trazabilidades) {
		this.trazabilidades = trazabilidades;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public void addNetAmount(BigDecimal netAmount) {
		setNetAmount(this.netAmount.add(netAmount));
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public void addTaxAmount(BigDecimal taxAmount) {
		setTaxAmount(this.taxAmount.add(taxAmount));
	}

	public BigDecimal getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}

	public void addPointAmount(BigDecimal pointAmount) {
		if (pointAmount != null) {
			setPointAmount(this.pointAmount.add(pointAmount));
		}
	}

	public String getDelProductiva() {
		return delProductiva;
	}

	public void setDelProductiva(String delProductiva) {
		this.delProductiva = delProductiva;
	}

	public String getPriceReferenceMaterial() {
		return priceReferenceMaterial;
	}

	public void setPriceReferenceMaterial(String priceReferenceMaterial) {
		this.priceReferenceMaterial = priceReferenceMaterial;
	}


	public int getSalesOrderAgrItem() {
		return salesOrderAgrItem;
	}

	public void setSalesOrderAgrItem(int salesOrderAgrItem) {
		this.salesOrderAgrItem = salesOrderAgrItem;
	}

	public int getHigherLevelltem() {
		return higherLevelltem;
	}

	public void setHigherLevelltem(int higherLevelltem) {
		this.higherLevelltem = higherLevelltem;
	}

	public String getSalesOrderItemCategory() {
		return salesOrderItemCategory;
	}

	public void setSalesOrderItemCategory(String salesOrderItemCategory) {
		this.salesOrderItemCategory = salesOrderItemCategory;
	}

	public String getPurchaseOrderByCustomer() {
		return purchaseOrderByCustomer;
	}

	public void setPurchaseOrderByCustomer(String purchaseOrderByCustomer) {
		this.purchaseOrderByCustomer = purchaseOrderByCustomer;
	}

	public String getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(String pricingDate) {
		this.pricingDate = pricingDate;
	}

	public String getReferenceSDDocument() {
		return referenceSDDocument;
	}

	public void setReferenceSDDocument(String referenceSDDocument) {
		this.referenceSDDocument = referenceSDDocument;
	}

	public String getReferenceSDDocumentItem() {
		return referenceSDDocumentItem;
	}

	public void setReferenceSDDocumentItem(String referenceSDDocumentItem) {
		this.referenceSDDocumentItem = referenceSDDocumentItem;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public String getUnidadProductiva() {
		return unidadProductiva;
	}

	public void setUnidadProductiva(String unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
	}

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getOficinaVentas() {
		return oficinaVentas;
	}

	public void setOficinaVentas(String oficinaVentas) {
		this.oficinaVentas = oficinaVentas;
	}

	public int getTipoPeticion() {
		return tipoPeticion;
	}

	public void setTipoPeticion(int tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}

	public String getGrupoPrecio() {
		return grupoPrecio;
	}

	public void setGrupoPrecio(String grupoPrecio) {
		this.grupoPrecio = grupoPrecio;
	}

	public String getListaPrecio() {
		return listaPrecio;
	}

	public void setListaPrecio(String listaPrecio) {
		this.listaPrecio = listaPrecio;
	}

	public String getServicioConcertado() {
		return servicioConcertado;
	}

	public void setServicioConcertado(String servicioConcertado) {
		this.servicioConcertado = servicioConcertado;
	}

	public String getDocumentoUnico() {
		return documentoUnico;
	}

	public void setDocumentoUnico(String documentoUnico) {
		this.documentoUnico = documentoUnico;
	}

	public String getCodigoPoliza() {
		return codigoPoliza;
	}

	public void setCodigoPoliza(String codigoPoliza) {
		this.codigoPoliza = codigoPoliza;
	}

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public String getOperacionSeoga() {
		return operacionSeoga;
	}

	public void setOperacionSeoga(String operacionSeoga) {
		this.operacionSeoga = operacionSeoga;
	}

	public String getCodigoBloqueo() {
		return codigoBloqueo;
	}

	public void setCodigoBloqueo(String codigoBloqueo) {
		this.codigoBloqueo = codigoBloqueo;
	}

	public String getOrganizationDivision() {
		return organizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		this.organizationDivision = organizationDivision;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		SolAgrItems other = (SolAgrItems) obj;
		return Objects.equals(material, other.material) &&
				Objects.equals(priceReferenceMaterial, other.priceReferenceMaterial) &&
				Objects.equals(productionPlant, other.productionPlant) &&
				Objects.equals(profitCenter, other.profitCenter) &&
				Objects.equals(requestedQuantity, other.requestedQuantity) &&
				Objects.equals(netAmount, other.netAmount) &&
				Objects.equals(taxAmount, other.taxAmount) &&
				Objects.equals(delProductiva, other.delProductiva) &&
				Objects.equals(pricingDate, other.pricingDate) &&
				Objects.equals(purchaseOrderByCustomer, other.purchaseOrderByCustomer) &&
				Objects.equals(referenceSDDocument, other.referenceSDDocument) &&
				Objects.equals(referenceSDDocumentItem, other.referenceSDDocumentItem) &&
				Objects.equals(salesOrderAgrItem, other.salesOrderAgrItem) &&
				Objects.equals(fechaValidacion, other.fechaValidacion) &&
				Objects.equals(oficinaVentas, other.oficinaVentas) &&
				Objects.equals(tipoPeticion, other.tipoPeticion) &&
				Objects.equals(listaPrecio, other.listaPrecio) &&
				Objects.equals(grupoPrecio, other.grupoPrecio) &&
				Objects.equals(higherLevelltem, other.higherLevelltem) &&
				Objects.equals(servicioConcertado, other.servicioConcertado) &&
				Objects.equals(documentoUnico, other.documentoUnico) &&
				Objects.equals(codigoPoliza, other.codigoPoliza) &&
				Objects.equals(codigoOperacion, other.codigoOperacion) &&
				Objects.equals(operacionSeoga, other.operacionSeoga) &&
				Objects.equals(codigoBloqueo, other.codigoBloqueo) &&
				Objects.equals(organizationDivision, other.organizationDivision);
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
	public TrazabilidadSolAgrItems obtieneTrazabilidad() {
		return this.trazabilidad;
	}

	@Override
	public Optional<List<String>> obtieneDestinos() {
		List<String> destinos = new ArrayList<>();
		destinos.add(Facturable.CODIGO);
		destinos.add(PedidoCreado.CODIGO);
		destinos.add(Facturada.CODIGO);
		return Optional.of(destinos);
	}

	@Override
	public Set<String> obtieneAlertas() {
		return new HashSet<>();
	}

	@Override
	public <T> List<T> obtieneHijos() {
		return Collections.emptyList();
	}

	@Override
	public SolicitudesAgrupadas obtienePadre() {
		return this.solicitudAgr;
	}

	@Override
	public String obtieneNivelEntity() {
		return EstadosUtils.NIVEL_POSICION;
	}

	@Override
	public boolean contieneValidada() {
		return false;
	}

	public boolean estaFacturada() {
		if (this.getTrazabilidad()==null || this.getTrazabilidad().getUltimoEstado()==null) return false;
		return this.getTrazabilidad().getUltimoEstado().equals(Facturada.CODIGO);
	}

	@Override
	public BasicEntity copyWithoutId() {
		final SolAgrItems target = (SolAgrItems) super.copyWithoutId();

		copyAndSetWithoutId(this::getPartners, target::setPartners);
		copyAndSetWithoutId(this::getPrices, target::setPrices);
		copyFieldAndSetWithoutId(this::getTrazabilidad, target::setTrazabilidad);

		return target;
	}

	@Override
	protected Set<String> getCopyWithoutIdBlacklistFields() {
		final Set<String> fields = super.getCopyWithoutIdBlacklistFields();
		fields.add("partners");
		fields.add("prices");
		fields.add("trazabilidad");

		return fields;
	}


	@Override
	public boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicionV2(this, estadoOrigen, manual);
	}
}

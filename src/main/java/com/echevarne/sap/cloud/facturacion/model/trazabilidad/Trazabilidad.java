package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import static java.util.Comparator.comparing;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Mutable;
import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros.SolAgrPruebaInformadaItems;
import com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros.SolAgrPruebaItemBaseEntity;
import com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros.SolAgrPruebaTercerosItems;
import com.echevarne.sap.cloud.facturacion.model.privados.CondicionPrecioAdicionalItem;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Esta clase permite conectar los items de cada tipo de solicitud: para
 * comprender la trazabiidad desde {@link PeticionMuestreoItems} hacia
 * {@link SolIndItems} hasta {@link SolAgrItems}
 *
 * @author Hernan Girardi
 * @since 30/04/2020
 */
@Entity
@Table(
	name = "T_Trazabilidad",
	indexes = {
		@Index(name = "Trazabilidad_bySalesOrder", columnList = "salesOrder", unique = false),
		@Index(name = "Trazabilidad_byBillingDocument", columnList = "billingDocument", unique = false),
	}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class Trazabilidad extends BasicEntity implements Mutable<TrazabilidadEstHistory> {

	private static final long serialVersionUID = 6842134752127969526L;

	/*
	 * Objetos relacionados
	 *
	 ********************************************/
	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreoItems", nullable = false)
	@JsonIgnore
	private PeticionMuestreoItems itemRec;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudIndividualItem")
	@JsonIgnore
	private SolIndItems itemInd;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudAgrupadaItem")
	@JsonIgnore
	private SolAgrItems itemAgr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_SolAgrPruebaInformadaItems")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private SolAgrPruebaInformadaItems itemPruebaInformada;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolAgrPruebaTercerosItems")
	@JsonIgnore
	private SolAgrPruebaTercerosItems itemPruebaTerceros;

	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@BatchSize(size = 50)
	@JsonIgnore
	private Set<TrazabilidadDocumentosSAP> trzDocumentosSAP = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy="trazabilidadPrueba", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadClasificacion trzClasificacion;
	
	/*
	 * Associations
	 *
	 ********************************************/
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidad")
	@BatchSize(size = 50)
	@JsonIgnore
	private Set<TrazabilidadEstHistory> estados = new HashSet<>();

	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<CondicionPrecioAdicionalItem> condicionPrecioAdicional = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ReglaFacturacion")
	private ReglasFacturacion reglaFacturacion;

	/*
	 * Trazabilidad de objetos externos
	 *
	 ********************************************/
	@Basic
	private String salesOrder;

	@Basic
	private String billingDocument;

	@Basic
	private String salesOrderItem;

	@Basic
	private String billingDocumentItem;

	@Basic
	private String accountingDocument;

	@Basic
	private String splitterKey;

	@Basic
	private Date fechaFactura;
	
	@Basic
	@ColumnDefault("false")
	@Column(name = "cobro_asignado")
	private boolean cobroAsignado;
	
	@Basic
	@ColumnDefault("false")
	private boolean reorganizado;
	
	/*
	 * Custom Methods
	 *
	 ********************************************/
	@JsonGetter(value = "ultimoEstado")
	public String getUltimoEstado() {
		Optional<TrazabilidadEstHistory> last = getLastEstado();
		if (last.isPresent()) {
			return getLastEstado().get().getEstado().getCodeEstado();
		}
		return "";
	}

	public MasDataMotivosEstado getUltimoMotivo() {
		Optional<TrazabilidadEstHistory> last = getLastEstado();
		if (last.isPresent()) {
			MasDataMotivosEstado motivo = getLastEstado().get().getMotivo();
			if (motivo != null) {
				return motivo;
			}
		}
		return null;
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadEstHistory> getLastEstado() {
		return this.estados.stream().max(comparing(TrazabilidadEstHistory::getSequenceOrder));
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadEstHistory> getAnteUltimoEstado() {
		return this.estados.stream().sorted(comparing(TrazabilidadEstHistory::getSequenceOrder).reversed()).skip(1)
				.findFirst();
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadEstHistory> getEstadoAnterior() {
		if (getLastEstado().isPresent()) {
			List<TrazabilidadEstHistory> lista = this.estados.stream()
					.sorted(comparing(TrazabilidadEstHistory::getSequenceOrder).reversed())
					.collect(Collectors.toList());
			return lista.stream().filter(trzHist -> !trzHist.getEstado().equals(getLastEstado().get().getEstado()))
					.findFirst();
		}
		return Optional.empty();
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadEstHistory> getLastEstado(MasDataEstado estadoTofind) {
		return this.estados.stream().sorted(comparing(TrazabilidadEstHistory::getSequenceOrder).reversed())
				.filter(trzHist -> trzHist.getEstado().equals(estadoTofind)).findFirst();
	}

	/*
	 * Entity Methods
	 *
	 ********************************************/
	public PeticionMuestreoItems getItemRec() {
		return itemRec;
	}

	public void setItemRec(PeticionMuestreoItems itemRec) {
		this.itemRec = itemRec;
	}

	public SolIndItems getItemInd() {
		return itemInd;
	}

	public void setItemInd(SolIndItems itemInd) {
		this.itemInd = itemInd;
	}

	public SolAgrItems getItemAgr() {
		return itemAgr;
	}

	public void setItemAgr(SolAgrItems itemAgr) {
		this.itemAgr = itemAgr;
	}

	/**
	 * @return the salesOrder
	 */
	public String getSalesOrder() {
		return salesOrder;
	}

	/**
	 * @param salesOrder the salesOrder to set
	 */
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	/**
	 * @return the billingDocument
	 */
	public String getBillingDocument() {
		return billingDocument;
	}

	/**
	 * @param billingDocument the billingDocument to set
	 */
	public void setBillingDocument(String billingDocument) {
		this.billingDocument = billingDocument;
	}

	public String getSalesOrderItem() {
		return salesOrderItem;
	}

	public void setSalesOrderItem(String salesOrderItem) {
		this.salesOrderItem = salesOrderItem;
	}

	public String getBillingDocumentItem() {
		return billingDocumentItem;
	}

	public void setBillingDocumentItem(String billingDocumentItem) {
		this.billingDocumentItem = billingDocumentItem;
	}

	public String getAccountingDocument() {
		return accountingDocument;
	}

	public void setAccountingDocument(String accountingDocument) {
		this.accountingDocument = accountingDocument;
	}

	public TrazabilidadClasificacion getTrzClasificacion() {
		return trzClasificacion;
	}

	public void setTrzClasificacion(TrazabilidadClasificacion trzClasificacion) {
		this.trzClasificacion = trzClasificacion;
	}
	
	public Set<TrazabilidadEstHistory> getEstados() {
		return estados;
	}

	public void setEstados(Set<TrazabilidadEstHistory> estados) {
		this.estados = estados;
	}

	public ReglasFacturacion getReglaFacturacion() {
		return reglaFacturacion;
	}

	public void setReglaFacturacion(ReglasFacturacion reglaFacturacion) {
		this.reglaFacturacion = reglaFacturacion;
	}

	public String getSplitterKey() {
		return splitterKey;
	}

	public void setSplitterKey(String splitterKey) {
		this.splitterKey = splitterKey;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public boolean isCobroAsignado() {
		return cobroAsignado;
	}

	public void setCobroAsignado(boolean cobroAsignado) {
		this.cobroAsignado = cobroAsignado;
	}

	public boolean isReorganizado() {
		return reorganizado;
	}

	public void setReorganizado(boolean reorganizado) {
		this.reorganizado = reorganizado;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Trazabilidad other = (Trazabilidad) obj;
		return Objects.equals(billingDocumentItem, other.billingDocumentItem)
				&& Objects.equals(billingDocument, other.billingDocument)
				&& Objects.equals(salesOrder, other.salesOrder) && Objects.equals(salesOrderItem, other.salesOrderItem)
				&& Objects.equals(accountingDocument, other.accountingDocument)
				&& Objects.equals(splitterKey, other.splitterKey)
				&& Objects.equals(fechaFactura, other.fechaFactura)
				&& Objects.equals(cobroAsignado, other.cobroAsignado)
				&& Objects.equals(reorganizado, other.reorganizado);
	}

	public void addEstado(TrazabilidadEstHistory trzEstHist) {
		estados.add(trzEstHist);
	}

	public Set<CondicionPrecioAdicionalItem> getCondicionPrecioAdicional() {
		return condicionPrecioAdicional;
	}

	public void setCondicionPrecioAdicional(Set<CondicionPrecioAdicionalItem> condicionPrecioAdicional) {
		this.condicionPrecioAdicional = condicionPrecioAdicional;
	}

	@Transient
	public Optional<CondicionPrecioAdicionalItem> getDescuentoActivo() {
		if (condicionPrecioAdicional == null || condicionPrecioAdicional.isEmpty()) {
			return Optional.empty();
		}
		return condicionPrecioAdicional.stream()
				.filter(cond -> !cond.isInactive() && !(cond.getConditionType() != null && cond.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_MODIFICADO)))
				.findFirst();
	}

	public SolAgrPruebaInformadaItems getItemPruebaInformada() {
		return itemPruebaInformada;
	}

	public void setItemPruebaInformada(SolAgrPruebaInformadaItems itemPruebaInformada) {
		this.itemPruebaInformada = itemPruebaInformada;
	}

	public SolAgrPruebaTercerosItems getItemPruebaTerceros() {
		return itemPruebaTerceros;
	}

	public void setItemPruebaTerceros(SolAgrPruebaTercerosItems itemPruebaTerceros) {
		this.itemPruebaTerceros = itemPruebaTerceros;
	}

	public void setItemPrueba(SolAgrPruebaItemBaseEntity itemPrueba) {
		if (itemPrueba instanceof SolAgrPruebaInformadaItems) {
			setItemPruebaInformada((SolAgrPruebaInformadaItems) itemPrueba);
		} else if (itemPrueba instanceof SolAgrPruebaTercerosItems) {
			setItemPruebaTerceros((SolAgrPruebaTercerosItems) itemPrueba);
		}
	}

	public void setItemPruebaNull(SolAgrPruebaItemBaseEntity itemPrueba) {
		if (itemPrueba instanceof SolAgrPruebaInformadaItems) {
			setItemPruebaInformada(null);
		} else if (itemPrueba instanceof SolAgrPruebaTercerosItems) {
			setItemPruebaTerceros(null);
		}
	}

	/**
	 *
	 * Añadimos el orden correspondiente.
	 *
	 * @return int
	 */
	@Transient
	public int getOrder() {
		Optional<TrazabilidadEstHistory> lastEstado = this.getLastEstado();
		if (lastEstado.isPresent()) {
			return lastEstado.get().getSequenceOrder() + 1;
		} else {
			return 1;
		}
	}

	@Override
	@Transient
	public TrazabilidadEstHistory createHistory() {
		return new TrazabilidadEstHistory();
	}

	@Override
	@Transient
	public Date getFechaEstado(String codeEstado) {
		return null;
	}

	public Set<TrazabilidadDocumentosSAP> getTrzDocumentosSAP() {
		return trzDocumentosSAP;
	}

	public void setTrzDocumentosSAP(Set<TrazabilidadDocumentosSAP> trzDocumentosSAP) {
		this.trzDocumentosSAP = trzDocumentosSAP;
	}
	
	@Override
	public List<MutableHistory> obtieneEstados() {
		return this.getEstados().stream().filter(x -> x.isActive()).collect(Collectors.toList());
	}

	@Override
	public Optional<TrazabilidadEstHistory> newInstance(MasDataEstado estado, MasDataMotivosEstado motivo,
			boolean automatico, boolean afectaImporte) {
		TrazabilidadEstHistory history = new TrazabilidadEstHistory();
		history.setSequenceOrder(getOrder());
		history.setMotivo(motivo);
		history.setAutomatico(automatico);
		history.setTrazabilidad(this);
		history.setEstado(estado);
		return Optional.of(history);
	}

	@Override
	public Optional<TrazabilidadEstHistory> newInstanceV2(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte,
			Integer sequenceOrder) {
		TrazabilidadEstHistory history = new TrazabilidadEstHistory();
		history.setSequenceOrder(sequenceOrder);
		history.setMotivo(motivo);
		history.setAutomatico(automatico);
		history.setTrazabilidad(this);
		history.setEstado(estado);
		return Optional.of(history);
	}

	@Override
	public void addHistory(TrazabilidadEstHistory history) {
		addEstado(history);
	}

	@Override
	public String getMessageArgs() {
		if(getItemRec() != null)
			return getItemRec().getCodigoMaterial();

		return null;
	}

	@Override
	@Transient
	public String getMessageId() {
		return "0107";
	}

	public void addTrzDocumentosSAP(TrazabilidadDocumentosSAP trzDocumentoSAP) {
		this.trzDocumentosSAP.add(trzDocumentoSAP);
	}
}

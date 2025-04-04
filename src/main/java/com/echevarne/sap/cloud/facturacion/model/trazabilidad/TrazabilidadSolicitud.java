package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import static java.util.Comparator.comparing;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import com.echevarne.sap.cloud.facturacion.gestionestados.Mutable;
import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.AgrupacionLiquidacionDetalle;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.EstadoLiquidacionPeticion;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.PropuestaLiquidacionPeticiones;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.privados.CondicionPrecioAdicionalSolicitud;
import com.echevarne.sap.cloud.facturacion.model.privados.DatosPagador;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link TrazabilidadSolicitud}.
 *
 * <p>
 * The persistent class. . .T_TrazabilidadSolicitud
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
@Setter
@Getter
@Table(name = "T_TrazabilidadSolicitud")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadSolicitud extends BasicEntity implements Mutable<TrazabilidadSolicitudEstHistory> {

	private static final long serialVersionUID = -3370081450009111394L;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo")
	@JsonIgnore
	private PeticionMuestreo peticionRec;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudIndividual")
	@JsonIgnore
	private SolicitudIndividual solicitudInd;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidad")
	@JsonIgnore
	private Set<TrazabilidadSolicitudAgrupado> agrupados = new HashSet<TrazabilidadSolicitudAgrupado>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidadSolicitud")
	@JsonIgnore
	private Set<PropuestaLiquidacionPeticiones> propLiqPeticiones = new HashSet<PropuestaLiquidacionPeticiones>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidad")
	@JsonIgnore
	private Set<TrazabilidadSolicitudEstHistory> estados = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidad")
	@JsonIgnore
	private Set<TrazabilidadSolicitudDocContable> docContables = new HashSet<>();

	@OneToOne(mappedBy = "trzSolicitud", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private DatosPagador datosPagador;

	@OneToMany(mappedBy = "trazabilidadSolicitud")
	@JsonIgnore
	private Set<CondicionPrecioAdicionalSolicitud> condicionPrecioAdicional = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trazabilidad")
	@JsonIgnore
	private Set<TrazabilidadSolicitudProcessStatus> estadosProceso = new HashSet<>();
	
	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@BatchSize(size = 50)
	@JsonIgnore
	private Set<EstadoLiquidacionPeticion> estadosLiquidacionPeticion = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_AgrupacionLiquidacion")
	@JsonIgnore
	private AgrupacionLiquidacionDetalle agrupLiquidacionDetalle;

	/*
	 * Trazabilidad de objetos externos
	 *
	 ********************************************/
	@Basic
	private String groupKey;
	
	@Basic
	@ColumnDefault("false")
	@Column(name = "cobro_asignado")
	private boolean cobroAsignado;
	
	@Basic
	@ColumnDefault("false")
	private boolean reorganizado;
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		TrazabilidadSolicitud other = (TrazabilidadSolicitud) obj;
		return Objects.equals(peticionRec, other.peticionRec) && Objects.equals(solicitudInd, other.solicitudInd)
				&& Objects.equals(agrupados, other.agrupados)
				&& Objects.equals(cobroAsignado, other.cobroAsignado)
				&& Objects.equals(reorganizado, other.reorganizado);
	}

	/**
	 * Metodos adicionales
	 */
	@Transient
	@JsonIgnore
	public Set<TrazabilidadSolicitudAgrupado> getAgrupados() {
		return agrupados;
	}

	public void setAgrupados(Set<TrazabilidadSolicitudAgrupado> agrupados) {
		this.agrupados = agrupados;
	}

	public void addAgrupados(TrazabilidadSolicitudAgrupado agrupado) {
		this.agrupados.add(agrupado);
	}

	public void removeAgrupados(TrazabilidadSolicitudAgrupado agrupado) {
		this.agrupados.remove(agrupado);
	}

	public void addEstado(TrazabilidadSolicitudEstHistory trzEstHist) {
		estados.add(trzEstHist);
	}

	public void addEstadosProceso(TrazabilidadSolicitudProcessStatus statusProceso) {
		estadosProceso.add(statusProceso);
	}
	
	public AgrupacionLiquidacionDetalle getAgrupLiquidacionDetalle() {
		return agrupLiquidacionDetalle;
	}

	public void setAgrupLiquidacionDetalle(AgrupacionLiquidacionDetalle agrupLiquidacionDetalle) {
		this.agrupLiquidacionDetalle = agrupLiquidacionDetalle;
	}

	/*****************
	 * Custom Methods
	 *
	********************************************/
	@JsonGetter(value = "ultimoEstado")
	public String getUltimoEstado() {
		Optional<TrazabilidadSolicitudEstHistory> last = getLastEstado();
		if (last.isPresent()) {
			return getLastEstado().get().getEstado().getCodeEstado();
		}
		return "";
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadSolicitudEstHistory> getLastEstado() {
		Set<TrazabilidadSolicitudEstHistory> estados = getEstados();
		if (estados != null)
			return estados.stream()
					.filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
					.max(comparing(TrazabilidadSolicitudEstHistory::getSequenceOrder));
		else
			return Optional.empty();
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadSolicitudEstHistory> getAnteUltimoEstado() {
		return this.estados.stream()
				.filter(trzHist -> trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
				.sorted(comparing(TrazabilidadSolicitudEstHistory::getSequenceOrder).reversed()).skip(1).findFirst();
	}

	@Transient
	@JsonIgnore
	public Optional<TrazabilidadSolicitudEstHistory> getLastEstado(MasDataEstado estadoTofind) {
		if (this.estados != null)
			return this.estados.stream()
					.filter(trzHist -> trzHist.getEstado().equals(estadoTofind)
							&& trzHist.getEstado().getTipoEstado() == MasDataEstado.ESTADO_PRINCIPAL)
					.max(comparing(TrazabilidadSolicitudEstHistory::getSequenceOrder));
		else
			return Optional.empty();
	}

	@Transient
	@JsonIgnore
	public Optional<CondicionPrecioAdicionalSolicitud> getDescuentoActivo() {
		if (condicionPrecioAdicional == null || condicionPrecioAdicional.isEmpty()) {
			return Optional.empty();
		}
		return condicionPrecioAdicional.stream().filter(cond -> (cond.isInactive() != true) && cond.getDescuento() != null).findFirst();
	}

	/**
	 *
	 * Añadimos el orden del estado del proceso
	 * @return
	 */
	@Transient
	public int getOrderProceso() {
		Optional<TrazabilidadSolicitudProcessStatus> lastProceso = this.getLastProceso();
		if (lastProceso.isPresent()) {
			return lastProceso.get().getSequenceOrder() + 1;
		} else {
			return 1;
		}
	}

	private Optional<TrazabilidadSolicitudProcessStatus> getLastProceso() {
		if (this.estadosProceso != null)
			return this.estadosProceso.stream()
					.max(comparing(TrazabilidadSolicitudProcessStatus::getSequenceOrder));
		else
			return Optional.empty();
	}

	/**
	 *
	 * Añadimos el orden correspondiente
	 * @return
	 */
	@Transient
	public int getOrder() {
		Optional<TrazabilidadSolicitudEstHistory> lastEstado = this.getLastEstado();
		if (lastEstado.isPresent()) {
			return lastEstado.get().getSequenceOrder() + 1;
		} else {
			return 1;
		}
	}

	@Override
	public TrazabilidadSolicitudEstHistory createHistory() {
		return new TrazabilidadSolicitudEstHistory();
	}

	@Override
	public Date getFechaEstado(String codeEstado) {
		return null;
	}

	@Override
	@Transient
	public List<MutableHistory> obtieneEstados() {
		return this.getEstados().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<TrazabilidadSolicitudEstHistory> newInstance(MasDataEstado estado, MasDataMotivosEstado motivo,
			boolean automatico, boolean afectaImporte) {
		TrazabilidadSolicitudEstHistory history = new TrazabilidadSolicitudEstHistory();
		history.setSequenceOrder(getOrder());
		history.setMotivo(motivo);
		history.setAutomatico(automatico);
		history.setTrazabilidad(this);
		history.setEstado(estado);
		history.setAfectaImporte(afectaImporte);
		return Optional.of(history);
	}

	@Override
	public void addHistory(TrazabilidadSolicitudEstHistory history) {
		addEstado(history);
	}

	@Override
	@Transient
	public String getMessageArgs() {
		if(getSolicitudInd() != null)
			return getSolicitudInd().getPurchaseOrderByCustomer();
		else if (getPeticionRec()!=null) {
			return getPeticionRec().getCodigoPeticion();
		}
		return null;
	}

	@Override
	@Transient
	public String getMessageId() {
		return "0106";
	}

	@Override
	public Optional<TrazabilidadSolicitudEstHistory> newInstanceV2(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte,
			Integer sequenceOrder) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}

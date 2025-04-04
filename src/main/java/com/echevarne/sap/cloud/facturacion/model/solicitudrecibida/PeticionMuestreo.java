package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NaturalId;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.gestionestados.Procesable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Class for the Entity {@link PeticionMuestreo}.
 *
 * <p>
 * The persistent class. . .T_PeticionMuestreo
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
@Table(name = ConstEntities.ENTIDAD_PETICIONMUESTREO, indexes = {
		@Index(name = "PeticionMuestreo_byCodigoPeticion", columnList = "fk_SolicitudMuestreo,codigoPeticion", unique = true) })
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeticionMuestreo extends BasicEntity implements Transicionable<TrazabilidadSolicitud>, SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 3666052170055128174L;

	@Column(unique = true)
	@NaturalId(mutable = true)
	@Priorizable
	private String codigoPeticion;

	@Basic
	@Priorizable
	@ColumnDefault("false")
	private int tipoPeticion;

	@Basic
	private String categoriaPeticion;

	@Basic
	private String codigoDelegacion;

	@Basic
	private String codigoSector;

	@Basic
	private String codigoCliente;

	@Basic
	private String nifCliente;

	@Basic
	private String cargoPeticion;

	@Basic
	@ColumnDefault("false")
	private boolean esMuestraRemitida;

	@Basic
	@ColumnDefault("false")
	private boolean esInmediato;

	@Basic
	@ColumnDefault("false")
	private boolean esPrivado;

	@Basic
	private String personaRemitente;

	@Basic
	private String codigoReferenciaCliente;

	@Basic
	private String codigoReferenciaExterno;

	@Basic
	private String codigoCertificado;

	@Basic
	private String bloqueoFactura;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean esPresupuesto;

	@Basic
	private String codigoIdioma;

	@Basic
	private String hojaPeticionServicio;

	@Basic
	private String codigoPaisCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudMuestreo", nullable = false)
	@JsonBackReference
	private SolicitudMuestreo solicitud;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "peticion")
	@JsonManagedReference
	private Set<PeticionMuestreoItems> pruebas = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "peticion")
	@JsonManagedReference
	private Set<PetMuesInterlocutores> interlocutores = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "peticion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PetMuesOperacion operacion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "peticion")
	@JsonManagedReference
	private Set<PetMuesAlerta> alertas = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "peticion")
	@JsonManagedReference
	private Set<PetMuesEstado> estados = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "peticion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PetMuesFechas fechas;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "peticion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PetMuesPrecio precio;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "peticion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PetMuesPaciente paciente;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "peticion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PetMuesMedico medico;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "peticion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PetMuesClinicos clinicos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "peticion")
	@JsonManagedReference
	private Set<PetMuesContenedor> contenedor = new HashSet<>();

	@OneToOne(mappedBy = "peticionRec", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadSolicitud trazabilidad;

	public String getCodigoPeticion() {
		return codigoPeticion;
	}

	public void setCodigoPeticion(String codigoPeticion) {
		this.codigoPeticion = codigoPeticion;
	}

	public int getTipoPeticion() {
		return tipoPeticion;
	}

	public void setTipoPeticion(int tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}

	public String getCategoriaPeticion() {
		return categoriaPeticion;
	}

	public void setCategoriaPeticion(String categoriaPeticion) {
		this.categoriaPeticion = categoriaPeticion;
	}

	public String getCodigoDelegacion() {
		return codigoDelegacion;
	}

	public void setCodigoDelegacion(String codigoDelegacion) {
		this.codigoDelegacion = codigoDelegacion;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNifCliente() {
		return nifCliente;
	}

	public void setNifCliente(String nifCliente) {
		this.nifCliente = nifCliente;
	}

	public String getCargoPeticion() {
		return cargoPeticion;
	}

	public void setCargoPeticion(String cargoPeticion) {
		this.cargoPeticion = cargoPeticion;
	}

	public boolean isEsMuestraRemitida() {
		return esMuestraRemitida;
	}

	public void setEsMuestraRemitida(boolean esMuestraRemitida) {
		this.esMuestraRemitida = esMuestraRemitida;
	}

	public boolean isEsInmediato() {
		return esInmediato;
	}

	public void setEsInmediato(boolean esInmediato) {
		this.esInmediato = esInmediato;
	}

	public boolean isEsPrivado() {
		return esPrivado;
	}

	public void setEsPrivado(boolean esPrivado) {
		this.esPrivado = esPrivado;
	}

	public String getPersonaRemitente() {
		return personaRemitente;
	}

	public void setPersonaRemitente(String personaRemitente) {
		this.personaRemitente = personaRemitente;
	}

	public String getCodigoReferenciaCliente() {
		return codigoReferenciaCliente;
	}

	public void setCodigoReferenciaCliente(String codigoReferenciaCliente) {
		this.codigoReferenciaCliente = codigoReferenciaCliente;
	}

	public String getCodigoReferenciaExterno() {
		return codigoReferenciaExterno;
	}

	public void setCodigoReferenciaExterno(String codigoReferenciaExterno) {
		this.codigoReferenciaExterno = codigoReferenciaExterno;
	}

	public String getCodigoCertificado() {
		return codigoCertificado;
	}

	public void setCodigoCertificado(String codigoCertificado) {
		this.codigoCertificado = codigoCertificado;
	}

	public String getBloqueoFactura() {
		return bloqueoFactura;
	}

	public void setBloqueoFactura(String bloqueoFactura) {
		this.bloqueoFactura = bloqueoFactura;
	}

	public boolean isEsPresupuesto() {
		return esPresupuesto;
	}

	public void setEsPresupuesto(boolean esPresupuesto) {
		this.esPresupuesto = esPresupuesto;
	}

	public String getCodigoIdioma() {
		return codigoIdioma;
	}

	public void setCodigoIdioma(String codigoIdioma) {
		this.codigoIdioma = codigoIdioma;
	}

	public String getHojaPeticionServicio() {
		return hojaPeticionServicio;
	}

	public void setHojaPeticionServicio(String hojaPeticionServicio) {
		this.hojaPeticionServicio = hojaPeticionServicio;
	}

	public String getCodigoPaisCliente() {
		return codigoPaisCliente;
	}

	public void setCodigoPaisCliente(String codigoPaisCliente) {
		this.codigoPaisCliente = codigoPaisCliente;
	}

	/**
	 * @return the pruebas
	 */
	public Set<PeticionMuestreoItems> getPruebas() {
		return this.pruebas;
	}

	/**
	 * @param pruebas the pruebas to set
	 */
	public void setPruebas(Set<PeticionMuestreoItems> pruebas) {
		this.pruebas = pruebas;
	}

	/**
	 * @param prueba the Items to add
	 */
	public void addItems(PeticionMuestreoItems prueba) {
		this.pruebas.add(prueba);
	}

	/**
	 * @param prueba the Items to remove
	 */
	public void removeItems(PeticionMuestreoItems prueba) {
		this.pruebas.remove(prueba);
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
	 * @return the interlocutores
	 */
	public Set<PetMuesInterlocutores> getInterlocutores() {
		return this.interlocutores;
	}

	public Optional<PetMuesInterlocutores> interlocutorWithPrefix(String prefix) {
		return interlocutores==null? Optional.empty() :
			interlocutores.stream().filter(inter -> inter.getCodigoInterlocutor().startsWith(prefix)).findFirst();
	}

	public Optional<PetMuesInterlocutores> interlocutorByRol(String rol) {
		return interlocutores==null? Optional.empty() :
			interlocutores.stream().filter(inter -> inter.getRolInterlocutor().equals(rol)).findFirst();
	}

	public Optional<PetMuesInterlocutores> interlocutorEmpresa() {
		return interlocutorByRol("ZE");
	}

	public Optional<PetMuesInterlocutores> interlocutorCompania() {
		return interlocutorByRol("ZC");
	}

	public Optional<PetMuesInterlocutores> interlocutorRemitente() {
		return interlocutorByRol("ZR");
	}

	/**
	 * @param interlocutores the interlocutores to set
	 */
	public void setInterlocutores(Set<PetMuesInterlocutores> interlocutores) {
		this.interlocutores = interlocutores;
	}

	/**
	 * @param interlocutores the Interlocutores to add
	 */
	public void addInterlocutores(PetMuesInterlocutores interlocutores) {
		this.interlocutores.add(interlocutores);
		interlocutores.setPeticion(this);
	}

	/**
	 * @param interlocutores the interlocutores to remove
	 */
	public void removeEstados(PetMuesInterlocutores interlocutores) {
		this.interlocutores.remove(interlocutores);
		interlocutores.setPeticion(null);
	}

	/**
	 * @return the alertas
	 */
	public Set<PetMuesAlerta> getAlertas() {
		return this.alertas;
	}

	/**
	 * @param alertas the alertas to set
	 */
	public void setAlertas(Set<PetMuesAlerta> alertas) {
		this.alertas = alertas != null ? alertas : new HashSet<>();
	}

	/**
	 * @param alertas the alertas to add
	 */
	public void addAlertas(PetMuesAlerta alertas) {
		this.alertas.add(alertas);
		alertas.setPeticion(this);
	}

	/**
	 * @param alertas the alertas to remove
	 */
	public void removeAlertas(PetMuesAlerta alertas) {
		this.alertas.remove(alertas);
		alertas.setPeticion(null);
	}

	/**
	 * @return the estados
	 */
	public Set<PetMuesEstado> getEstados() {
		return this.estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(Set<PetMuesEstado> estados) {
		this.estados = estados;
	}

	/**
	 * /**
	 *
	 * @param estado the estados to add
	 */
	public void addEstados(PetMuesEstado estado) {
		this.estados.add(estado);
		estado.setPeticion(this);
	}

	/**
	 * @param estado the estados to remove
	 */
	public void removeEstados(PetMuesEstado estado) {
		this.estados.remove(estado);
		estado.setPeticion(null);
	}

	/**
	 * @return the fechas
	 */
	public PetMuesFechas getFechas() {
		return this.fechas;
	}

	/**
	 * @param fechas the fechas to set
	 */
	public void setFechas(PetMuesFechas fechas) {
		this.fechas = fechas;
	}

	/**
	 * @return the precio
	 */
	public PetMuesPrecio getPrecio() {
		return this.precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(PetMuesPrecio precio) {
		this.precio = precio;
	}

	/**
	 * @return the operacion
	 */
	public PetMuesOperacion getOperacion() {
		return this.operacion;
	}

	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(PetMuesOperacion operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return the paciente
	 */
	public PetMuesPaciente getPaciente() {
		return this.paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(PetMuesPaciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the medico
	 */
	public PetMuesMedico getMedico() {
		return this.medico;
	}

	/**
	 * @param medico the medico to set
	 */
	public void setMedico(PetMuesMedico medico) {
		this.medico = medico;
	}

	/**
	 * @return the clinicos
	 */
	public PetMuesClinicos getClinicos() {
		return this.clinicos;
	}

	/**
	 * @param clinicos the clinicos to set
	 */
	public void setClinicos(PetMuesClinicos clinicos) {
		this.clinicos = clinicos;
	}

	/**
	 * @return the contenedor
	 */
	public Set<PetMuesContenedor> getContenedor() {
		return this.contenedor;
	}

	/**
	 * @param contenedor the contenedor to set
	 */
	public void setContenedor(Set<PetMuesContenedor> contenedor) {
		this.contenedor = contenedor;
	}

	/**
	 * /**
	 *
	 * @param contenedor the contenedor to add
	 */
	public void addContenedor(PetMuesContenedor contenedor) {
		this.contenedor.add(contenedor);
		contenedor.setPeticion(this);
	}

	/**
	 * @param contenedor the contenedor to remove
	 */
	public void removeContenedor(PetMuesContenedor contenedor) {
		this.contenedor.remove(contenedor);
		contenedor.setPeticion(null);
	}

	public SolicitudMuestreo getSolicitud() {
		return this.solicitud;
	}

	public void setSolicitud(SolicitudMuestreo solicitud) {
		this.solicitud = solicitud;
	}

	public String getCodigoInterlocutorByRol(String rol) {
		return interlocutorByRol(rol).map(x -> x.getCodigoInterlocutor()).orElse(null);
	}

	public List<String> getCodigosAlertas() {
		if(alertas == null) return Collections.emptyList();
		return new ArrayList<>(obtieneAlertas());
	}

	/**
	 * Transicionable
	 */
	@Override
	public boolean transicionar(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicion(this, estadoOrigen, manual);
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
	public SolicitudMuestreo obtienePadre() {
		return this.getSolicitud();
	}

	@Override
	public List<PeticionMuestreoItems> obtieneHijos() {
		return this.pruebas.stream().sorted(Comparator.comparingInt(PeticionMuestreoItems::getIdItem).reversed())
		.collect(Collectors.toList());
	}

	@Override
	public Optional<List<String>> obtieneDestinos() {
		// Obtiene los estados que vienen en la peticion
		if (this.estados == null)
			return Optional.empty();
		if (this.estados.size() > 0)
			return Optional.of(this.estados.stream().filter(x -> !x.isInactive()).map(PetMuesEstado::getCodigoEstado)
					.collect(Collectors.toList()));
		else
			return Optional.empty();
	}

	@Override
	public Set<String> obtieneAlertas() {
		return this.alertas.stream().filter(x -> !x.isInactive()).map(PetMuesAlerta::getCodigoAlerta)
				.collect(Collectors.toSet());
	}

	@Override
	public String obtieneNivelEntity() {
		return EstadosUtils.NIVEL_CABECERA;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean contieneValidada() {
		return this.getPruebas().stream().anyMatch(x -> x.contieneValidada());
	}

	@Transient
	@JsonIgnore
	public boolean validarEsPrivado() {
		return pruebas.stream().anyMatch(PeticionMuestreoItems::getEsPrivado);
	}
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		PeticionMuestreo other = (PeticionMuestreo) obj;
		return Objects.equals(this.bloqueoFactura, other.bloqueoFactura)
				&& Objects.equals(this.cargoPeticion, other.cargoPeticion)
				&& Objects.equals(this.categoriaPeticion, other.categoriaPeticion)
				&& Objects.equals(this.codigoCertificado, other.codigoCertificado)
				&& Objects.equals(this.codigoCliente, other.codigoCliente)
				&& Objects.equals(this.codigoDelegacion, other.codigoDelegacion)
				&& Objects.equals(this.codigoIdioma, other.codigoIdioma)
				&& Objects.equals(this.codigoPaisCliente, other.codigoPaisCliente)
				&& Objects.equals(this.codigoPeticion, other.codigoPeticion)
				&& Objects.equals(this.codigoReferenciaCliente, other.codigoReferenciaCliente)
				&& Objects.equals(this.codigoReferenciaExterno, other.codigoReferenciaExterno)
				&& Objects.equals(this.codigoSector, other.codigoSector) && esInmediato == other.esInmediato
				&& esMuestraRemitida == other.esMuestraRemitida && esPresupuesto == other.esPresupuesto
				&& esPrivado == other.esPrivado && Objects.equals(this.hojaPeticionServicio, other.hojaPeticionServicio)
				&& Objects.equals(this.interlocutores, other.interlocutores)
				&& Objects.equals(this.nifCliente, other.nifCliente)
				&& Objects.equals(this.personaRemitente, other.personaRemitente)
				&& Objects.equals(this.tipoPeticion, other.tipoPeticion)
				&& Objects.equals(this.alertas, other.alertas)
				&& Objects.equals(this.paciente, other.paciente)
				&& Objects.equals(this.medico, other.medico)
				&& Objects.equals(this.operacion, other.operacion)
				&& Objects.equals(this.precio, other.precio)
				&& Objects.equals(this.pruebas, other.pruebas);
	}

	@Override
	public BasicEntity copyWithoutId() {
		final PeticionMuestreo target = (PeticionMuestreo)super.copyWithoutId();
		copyFieldAndSetWithoutId(this::getFechas, target::setFechas);
		copyFieldAndSetWithoutId(this::getPrecio, target::setPrecio);
		copyFieldAndSetWithoutId(this::getPaciente, target::setPaciente);
		copyFieldAndSetWithoutId(this::getMedico, target::setMedico);
		copyFieldAndSetWithoutId(this::getClinicos, target::setClinicos);
		copyFieldAndSetWithoutId(this::getOperacion, target::setOperacion);
		copyAndSetWithoutId(this::getEstados, target::setEstados);
		copyAndSetWithoutId(this::getInterlocutores, target::setInterlocutores);
		copyAndSetWithoutId(this::getContenedor, target::setContenedor);
		// Alertas and Pruebas must not be copied.

		return target;
	}

	@Override
	protected Set<String> getCopyWithoutIdBlacklistFields() {
		final Set<String> fields = super.getCopyWithoutIdBlacklistFields();
		fields.add("fechas");
		fields.add("precio");
		fields.add("paciente");
		fields.add("medico");
		fields.add("clinicos");
		fields.add("operacion");
		fields.add("estados");
		fields.add("interlocutores");
		fields.add("contenedor");
		fields.add("alertas");
		fields.add("pruebas");

		return fields;
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setSolicitud((SolicitudMuestreo) cabecera);
	}

	@Override
	public boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicionV2(this, estadoOrigen, manual);
	}

}

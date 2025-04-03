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
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class PeticionMuestreo extends BasicEntity implements SetComponent {

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

	public SolicitudMuestreo getSolicitud() {
		return this.solicitud;
	}

	public void setSolicitud(SolicitudMuestreo solicitud) {
		this.solicitud = solicitud;
	}

	public String getCodigoInterlocutorByRol(String rol) {
		return interlocutorByRol(rol).map(x -> x.getCodigoInterlocutor()).orElse(null);
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
				&& Objects.equals(this.pruebas, other.pruebas);
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

	

	

}

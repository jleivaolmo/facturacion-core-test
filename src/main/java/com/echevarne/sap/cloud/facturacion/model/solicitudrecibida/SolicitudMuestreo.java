package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.gestionestados.Procesable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadAlbaran;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;

/**
 * Class for the Entity {@link SolicitudMuestreo}.
 *
 * <p>
 * The persistent class. . .T_SolicitudMuestreo
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
@Table(name = ConstEntities.ENTIDAD_SOLICITUDMUESTREO, indexes = {
		@Index(name = "IDX_SMbyCodigoPeticion", columnList = "codigoPeticion", unique = true) })
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudMuestreo extends BasicEntity implements Transicionable<TrazabilidadAlbaran> {

	/**
	 *
	 */
	private static final long serialVersionUID = 5287899000987861231L;

	@Basic
	private String codigoOficinaVentas;
	@Basic
	private String numeroReferenciaCliente;
	@Basic
	private String numeroReferenciaExterno;
	@Basic
	private String codigoUsuarioExterno;
	@Basic
	private Long codigoSistema;
	@Column(unique = true)
	@NaturalId(mutable = true)
	private String codigoPeticion;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean esMixta;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean esEshop;
	@Basic
	private String codigoPeticionLims;

	@OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<PeticionMuestreo> peticion = new HashSet<>();

	@OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<ProductosAdicionales> adicionales = new HashSet<>();

	@OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<SolMuesEstado> estados = new HashSet<>();

	@OneToOne(mappedBy = "solicitudRec", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadAlbaran trazabilidad;

	public String getCodigoOficinaVentas() {
		return codigoOficinaVentas;
	}

	public void setCodigoOficinaVentas(String codigoOficinaVentas) {
		this.codigoOficinaVentas = codigoOficinaVentas;
	}

	public String getNumeroReferenciaCliente() {
		return numeroReferenciaCliente;
	}

	public void setNumeroReferenciaCliente(String numeroReferenciaCliente) {
		this.numeroReferenciaCliente = numeroReferenciaCliente;
	}

	public String getNumeroReferenciaExterno() {
		return numeroReferenciaExterno;
	}

	public void setNumeroReferenciaExterno(String numeroReferenciaExterno) {
		this.numeroReferenciaExterno = numeroReferenciaExterno;
	}

	public String getCodigoUsuarioExterno() {
		return codigoUsuarioExterno;
	}

	public void setCodigoUsuarioExterno(String codigoUsuarioExterno) {
		this.codigoUsuarioExterno = codigoUsuarioExterno;
	}

	public String getCodigoPeticion() {
		return codigoPeticion;
	}

	public void setCodigoPeticion(String codigoPeticion) {
		this.codigoPeticion = codigoPeticion;
	}

	public String getCodigoPeticionLims() {
		return codigoPeticionLims;
	}

	public void setCodigoPeticionLims(String codigoPeticionLims) {
		this.codigoPeticionLims = codigoPeticionLims;
	}

	/**
	 * @return the peticion
	 */
	public Set<PeticionMuestreo> getPeticion() {
		return peticion;
	}

	@Transient
	@JsonIgnore
	public List<PeticionMuestreo> getPeticionesActivas() {
		return peticion.stream().filter(x -> !x.isInactive()).collect(Collectors.toList());
	}

	@Transient
	@JsonIgnore
	public List<PeticionMuestreo> getPeticionesMutua() {
		return getPeticionesActivasDeTipoMutuaOPrivado(false);
	}

	@Transient
	@JsonIgnore
	public List<PeticionMuestreo> getPeticionesPrivado() {
		return getPeticionesActivasDeTipoMutuaOPrivado(true);
	}

	@Transient
	@JsonIgnore
	private List<PeticionMuestreo> getPeticionesActivasDeTipoMutuaOPrivado(boolean esPrivado) {
		if (esPrivado)
			return peticion.stream().filter(x -> !x.isInactive() && x.isEsPrivado()).collect(Collectors.toList());
		else
			return peticion.stream().filter(x -> !x.isInactive() && !x.isEsPrivado()).collect(Collectors.toList());
	}

	@Transient
	@JsonIgnore
	public boolean validarEsPrivado() {
		return peticion.stream().anyMatch(PeticionMuestreo::validarEsPrivado) || peticion.stream().allMatch(PeticionMuestreo::isEsPrivado);
	}

	/**
	 * @param peticion the peticion to set
	 */
	public void setPeticion(Set<PeticionMuestreo> peticion) {
		this.peticion = peticion;
	}

	/**
	 * @return the estados
	 */
	public Set<SolMuesEstado> getEstados() {
		return estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(Set<SolMuesEstado> estados) {
		this.estados = estados;
	}

	/**
	 * @param estado the estados to add
	 */
	public void addEstados(SolMuesEstado estado) {
		this.estados.add(estado);
		estado.setSolicitud(this);
	}

	/**
	 * @param estado the estados to remove
	 */
	public void removeEstados(SolMuesEstado estado) {
		this.estados.remove(estado);
		estado.setSolicitud(null);
	}

	public boolean isEsMixta() {
		return esMixta;
	}

	public void setEsMixta(boolean esMixta) {
		this.esMixta = esMixta;
	}

	public boolean isEsEshop() {
		return esEshop;
	}

	public void setEsEshop(boolean esEshop) {
		this.esEshop = esEshop;
	}

	public Set<ProductosAdicionales> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(Set<ProductosAdicionales> adicionales) {
		this.adicionales = adicionales;
	}

	/**
	 * @return the trazabilidad
	 */
	public TrazabilidadAlbaran getTrazabilidad() {
		return trazabilidad;
	}

	/**
	 * @param trazabilidad the trazabilidad to set
	 */
	public void setTrazabilidad(TrazabilidadAlbaran trazabilidad) {
		this.trazabilidad = trazabilidad;
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
		SolicitudMuestreo other = (SolicitudMuestreo) obj;
		return Objects.equals(codigoOficinaVentas, other.codigoOficinaVentas)
				&& Objects.equals(codigoUsuarioExterno, other.codigoUsuarioExterno)
				&& Objects.equals(estados, other.estados)
				&& Objects.equals(numeroReferenciaCliente, other.numeroReferenciaCliente)
				&& Objects.equals(numeroReferenciaExterno, other.numeroReferenciaExterno)
				&& Objects.equals(peticion, other.peticion) && Objects.equals(esMixta, other.esMixta)
				&& Objects.equals(adicionales, other.adicionales) && Objects.equals(codigoSistema, other.codigoSistema);
	}

	@JsonIgnore
	public boolean isEmpty() {
		return (peticion == null);
	}

	/**
	 * @return the codigoSistema
	 */
	public Long getCodigoSistema() {
		return codigoSistema;
	}

	/**
	 * @param codigoSistema the codigoSistema to set
	 */
	public void setCodigoSistema(Long codigoSistema) {
		this.codigoSistema = codigoSistema;
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
	public TrazabilidadAlbaran obtieneTrazabilidad() {
		return this.trazabilidad;
	}

	@Override
	public Optional<List<String>> obtieneDestinos() {
		// Obtiene los estados que vienen en la peticion
		if(this.estados == null) return Optional.empty();
		if(this.estados.size() > 0)
			return Optional.of(this.estados.stream()
					.map(SolMuesEstado::getCodigoEstado)
					.collect(Collectors.toList()));
		else
			return Optional.empty();
	}

	@Override
	public Set<String> obtieneAlertas() {
		return Collections.emptySet();
	}

	@Override
	public List<PeticionMuestreo> obtieneHijos() {
		return this.getPeticion().stream().collect(Collectors.toList());
	}

	@Override
	public Transicionable<?> obtienePadre() {
		return null;
	}

	@Override
	public String obtieneNivelEntity() {
		return EstadosUtils.NIVEL_ALBARAN;
	}

	@Override
	public boolean contieneValidada() {
		return this.obtieneHijos().stream().anyMatch(PeticionMuestreo::contieneValidada);
	}

	@Transient
	@JsonIgnore
	public Set<PeticionMuestreoItems> getPruebasMutua() {
		return this.getPeticion().stream().filter(x -> !x.isEsPrivado()).flatMap(x -> x.getPruebas().stream()).collect(Collectors.toSet());
	}

	@Override
	public boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual) {
		return procesadorEstado.doTransicionV2(this, estadoOrigen, manual);
	}

}

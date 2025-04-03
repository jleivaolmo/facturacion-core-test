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
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudMuestreo extends BasicEntity  {

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
				
				&& Objects.equals(numeroReferenciaCliente, other.numeroReferenciaCliente)
				&& Objects.equals(numeroReferenciaExterno, other.numeroReferenciaExterno)
				&& Objects.equals(peticion, other.peticion) && Objects.equals(esMixta, other.esMixta)
				&& Objects.equals(codigoSistema, other.codigoSistema);
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

	@Transient
	@JsonIgnore
	public Set<PeticionMuestreoItems> getPruebasMutua() {
		return this.getPeticion().stream().filter(x -> !x.isEsPrivado()).flatMap(x -> x.getPruebas().stream()).collect(Collectors.toSet());
	}

	

}

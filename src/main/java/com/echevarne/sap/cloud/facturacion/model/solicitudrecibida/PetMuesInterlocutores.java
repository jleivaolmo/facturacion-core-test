package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.privados.DatosPagador;
import com.echevarne.sap.cloud.facturacion.model.privados.TipoDocumento;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Class for the Entity {@link PetMuesInterlocutores}.
 *
 * <p>The persistent class. . .T_PetMuesInterlocutores</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESINTERLOCUTORES,
indexes={@Index(name = "IDX_byRolInterlocutor",  columnList="fk_PeticionMuestreo,rolInterlocutor", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesInterlocutores extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 2310051333357812044L;

	@Basic
	@JsonProperty("rolInterlocutor")
	private String rolInterlocutor;

	@Basic
	private String codigoInterlocutor;

	@Basic
	private String nifInterlocutor;

	@Basic
	private String nombreInterlocutor;

	@Basic
	private String primApellidoInterlocutor;

	@Basic
	private String segApellidoInterlocutor;

	@Basic
	private String tratamientoInterlocutor;

	@Basic
	private String direccionInterlocutor;

	@Basic
	private String poblacionInterlocutor;

	@Basic
	private String codigoPostalInterlocutor;

	@Basic
	private String provinciaInterlocutor;

	@Basic
	private String numeroColegiado;

	@Basic
	private String codigoEspecialidad;

	@Basic
	private String codigoReferencia;

	@Basic
	private String telefono1Interlocutor;

	@Basic
	private String telefono2Interlocutor;

	@Basic
	private String emailInterlocutor;

	@Basic
	private String codigoPaisInterlocutor;

	@Basic
	private String inicialesInterlocutor;

	@Basic
	private String tipoIdentificacion;

	@Basic
	private String codigoIdentificacion;

	@Basic
	@JsonAlias({"codigoCip","codigoCIP"})
	private String codigoCIP;

	@Basic
	private String codigoIdioma;

	@Basic
	private String oficinaVentasCopa;


	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the codigoInterlocutor
	 */
	public String getCodigoInterlocutor() {
		return this.codigoInterlocutor;
	}

	/**
	 * @param codigoInterlocutor
	 *            the codigoInterlocutor to set
	 */
	public void setCodigoInterlocutor(String codigoInterlocutor) {
		this.codigoInterlocutor = codigoInterlocutor;
	}

	/**
	 * @return the nifInterlocutor
	 */
	public String getNifInterlocutor() {
		return this.nifInterlocutor;
	}

	/**
	 * @param nifInterlocutor
	 *            the nifInterlocutor to set
	 */
	public void setNifInterlocutor(String nifInterlocutor) {
		this.nifInterlocutor = nifInterlocutor;
	}

	/**
	 * @return the rolInterlocutor
	 */
	public String getRolInterlocutor() {
		return this.rolInterlocutor;
	}

	/**
	 * @param rolInterlocutor
	 *            the rolInterlocutor to set
	 */
	public void setRolInterlocutor(String rolInterlocutor) {
		this.rolInterlocutor = rolInterlocutor;
	}

	/**
	 * @return the peticion
	 */
	public PeticionMuestreo getPeticion() {
		return peticion;
	}

	/**
	 * @param peticion
	 *            the peticion to set
	 */
	public void setPeticion(PeticionMuestreo peticion) {
		this.peticion = peticion;
	}



	public String getNombreInterlocutor() {
		return nombreInterlocutor;
	}

	public void setNombreInterlocutor(String nombreInterlocutor) {
		this.nombreInterlocutor = nombreInterlocutor;
	}

	public String getPrimApellidoInterlocutor() {
		return primApellidoInterlocutor;
	}

	public void setPrimApellidoInterlocutor(String primApellidoInterlocutor) {
		this.primApellidoInterlocutor = primApellidoInterlocutor;
	}

	public String getSegApellidoInterlocutor() {
		return segApellidoInterlocutor;
	}

	public void setSegApellidoInterlocutor(String segApellidoInterlocutor) {
		this.segApellidoInterlocutor = segApellidoInterlocutor;
	}

	public String getTratamientoInterlocutor() {
		return tratamientoInterlocutor;
	}

	public void setTratamientoInterlocutor(String tratamientoInterlocutor) {
		this.tratamientoInterlocutor = tratamientoInterlocutor;
	}

	public String getDireccionInterlocutor() {
		return direccionInterlocutor;
	}

	public void setDireccionInterlocutor(String direccionInterlocutor) {
		this.direccionInterlocutor = direccionInterlocutor;
	}

	public String getPoblacionInterlocutor() {
		return poblacionInterlocutor;
	}

	public void setPoblacionInterlocutor(String poblacionInterlocutor) {
		this.poblacionInterlocutor = poblacionInterlocutor;
	}

	public String getCodigoPostalInterlocutor() {
		return codigoPostalInterlocutor;
	}

	public void setCodigoPostalInterlocutor(String codigoPostalInterlocutor) {
		this.codigoPostalInterlocutor = codigoPostalInterlocutor;
	}

	public String getProvinciaInterlocutor() {
		return provinciaInterlocutor;
	}

	public void setProvinciaInterlocutor(String provinciaInterlocutor) {
		this.provinciaInterlocutor = provinciaInterlocutor;
	}

	public String getNumeroColegiado() {
		return numeroColegiado;
	}

	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}

	public String getCodigoEspecialidad() {
		return codigoEspecialidad;
	}

	public void setCodigoEspecialidad(String codigoEspecialidad) {
		this.codigoEspecialidad = codigoEspecialidad;
	}

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public String getTelefono1Interlocutor() {
		return telefono1Interlocutor;
	}

	public void setTelefono1Interlocutor(String telefono1Interlocutor) {
		this.telefono1Interlocutor = telefono1Interlocutor;
	}

	public String getTelefono2Interlocutor() {
		return telefono2Interlocutor;
	}

	public void setTelefono2Interlocutor(String telefono2Interlocutor) {
		this.telefono2Interlocutor = telefono2Interlocutor;
	}

	public String getEmailInterlocutor() {
		return emailInterlocutor;
	}

	public void setEmailInterlocutor(String emailInterlocutor) {
		this.emailInterlocutor = emailInterlocutor;
	}

	public String getCodigoPaisInterlocutor() {
		return codigoPaisInterlocutor;
	}

	public void setCodigoPaisInterlocutor(String codigoPaisInterlocutor) {
		this.codigoPaisInterlocutor = codigoPaisInterlocutor;
	}

	public String getInicialesInterlocutor() {
		return inicialesInterlocutor;
	}

	public void setInicialesInterlocutor(String inicialesInterlocutor) {
		this.inicialesInterlocutor = inicialesInterlocutor;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getCodigoIdentificacion() {
		return codigoIdentificacion;
	}

	public void setCodigoIdentificacion(String codigoIdentificacion) {
		this.codigoIdentificacion = codigoIdentificacion;
	}

	public String getCodigoCIP() {
		return codigoCIP;
	}

	public void setCodigoCIP(String codigoCIP) {
		this.codigoCIP = codigoCIP;
	}

	public String getCodigoIdioma() {
		return codigoIdioma;
	}

	public void setCodigoIdioma(String codigoIdioma) {
		this.codigoIdioma = codigoIdioma;
	}

	public String getOficinaVentasCopa() {
		return oficinaVentasCopa;
	}

	public void setOficinaVentasCopa(String oficinaVentasCopa) {
		this.oficinaVentasCopa = oficinaVentasCopa;
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
		PetMuesInterlocutores other = (PetMuesInterlocutores) obj;
		return  Objects.equals(this.codigoCIP, other.codigoCIP) &&
				Objects.equals(this.codigoEspecialidad, other.codigoEspecialidad) &&
				Objects.equals(this.codigoIdentificacion, other.codigoIdentificacion) &&
				Objects.equals(this.codigoIdioma, other.codigoIdioma) &&
				Objects.equals(this.codigoInterlocutor, other.codigoInterlocutor) &&
				Objects.equals(this.codigoPaisInterlocutor, other.codigoPaisInterlocutor) &&
				Objects.equals(this.codigoPostalInterlocutor, other.codigoPostalInterlocutor) &&
				Objects.equals(this.codigoReferencia, other.codigoReferencia) &&
				Objects.equals(this.direccionInterlocutor, other.direccionInterlocutor) &&
				Objects.equals(this.emailInterlocutor, other.emailInterlocutor) &&
				Objects.equals(this.inicialesInterlocutor, other.inicialesInterlocutor) &&
				Objects.equals(this.nifInterlocutor, other.nifInterlocutor) &&
				Objects.equals(this.nombreInterlocutor, other.nombreInterlocutor) &&
				Objects.equals(this.numeroColegiado, other.numeroColegiado) &&
				Objects.equals(this.poblacionInterlocutor, other.poblacionInterlocutor) &&
				Objects.equals(this.primApellidoInterlocutor, other.primApellidoInterlocutor) &&
				Objects.equals(this.provinciaInterlocutor, other.provinciaInterlocutor) &&
				Objects.equals(this.rolInterlocutor, other.rolInterlocutor) &&
				Objects.equals(this.segApellidoInterlocutor, other.segApellidoInterlocutor) &&
				Objects.equals(this.telefono1Interlocutor, other.telefono1Interlocutor) &&
				Objects.equals(this.telefono2Interlocutor, other.telefono2Interlocutor) &&
				Objects.equals(this.tipoIdentificacion, other.tipoIdentificacion) &&
				Objects.equals(this.tratamientoInterlocutor, other.tratamientoInterlocutor) &&
				Objects.equals(this.oficinaVentasCopa, other.oficinaVentasCopa);
	}

	public DatosPagador interlocutorAsPagador() {

		int codigoTipoDoc = this.tipoIdentificacion != null ? Integer.parseInt(this.tipoIdentificacion) : 5;

		return DatosPagador.builder()
			.nombrePagador(this.nombreInterlocutor)
			.tipoDocumentoPagador(TipoDocumento.getByValue(codigoTipoDoc).name())
			.numeroDocumentoPagador(this.codigoIdentificacion)
			.primApellidoPagador(this.primApellidoInterlocutor)
			.segApellidoPagador(this.segApellidoInterlocutor)
			.emailPagador(this.emailInterlocutor)
			.telefono1Pagador(this.telefono1Interlocutor)
			.telefono2Pagador(this.telefono2Interlocutor)
			.direccionPagador(this.direccionInterlocutor)
			.ciudadPagador(this.poblacionInterlocutor)
			//TODO obtener nombre provicia by codigo
			.provinciaPagador(this.provinciaInterlocutor)
			.codigoPostalPagador(this.codigoPostalInterlocutor)
			.codigoPaisPagador(this.codigoPaisInterlocutor)
			.codigoIdiomaPagador(this.codigoIdioma)
			//Por defecto se pone a false
			.esEmpresa(false)
			.build();
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setPeticion((PeticionMuestreo) cabecera);
	}

	@Override
	protected Set<String> getCopyWithoutIdBlacklistFields() {
		final Set<String> fields = super.getCopyWithoutIdBlacklistFields();
		fields.add("peticion");

		return fields;
	}

}

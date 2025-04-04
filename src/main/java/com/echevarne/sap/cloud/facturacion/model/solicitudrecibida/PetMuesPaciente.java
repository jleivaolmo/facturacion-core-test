package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Class for the Entity {@link PetMuesPaciente}.
 *
 * <p>The persistent class. . .T_PetMuesPaciente</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESPACIENTE,
		indexes={@Index(name = "PetMuesPaciente_byMuestreo",  columnList="fk_PeticionMuestreo", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesPaciente extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -1170675908612660701L;

	@Basic
	private String numeroPaciente;

	@Basic
	private String tipoDocumento;

	@Basic
	private String codigoDocumento;

	@Basic
	private String codigoPoliza;

	@Basic
	private String cuentaCotizacion;

	@Basic
	private String nombreCuentaCotizacion;

	@Basic
	private String numeroContrato;

	@Basic
	private String puestoTrabajo;

	@Basic
	private String numeroEmpleado;

	@Basic
	private String codigoColectivo;

	@Basic
	private String operarioMuestreado;

	@Basic
	private String codigoPropietario;

	@Basic
	private String nombreAnimal;

	@Basic
	private String codigoDescripcion;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the numeroPaciente
	 */
	public String getNumeroPaciente() {
		return this.numeroPaciente;
	}

	/**
	 * @param numeroPaciente
	 *            the numeroPaciente to set
	 */
	public void setNumeroPaciente(String numeroPaciente) {
		this.numeroPaciente = numeroPaciente;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 *
	 * @return
	 */
	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	/**
	 *
	 * @param codigoDocumento
	 */
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	/**
	 * @return the codigoPoliza
	 */
	public String getCodigoPoliza() {
		return this.codigoPoliza;
	}

	/**
	 * @param codigoPoliza
	 *            the codigoPoliza to set
	 */
	public void setCodigoPoliza(String codigoPoliza) {
		this.codigoPoliza = codigoPoliza;
	}

	/**
	 * @return the cuentaCotizacion
	 */
	public String getCuentaCotizacion() {
		return this.cuentaCotizacion;
	}

	/**
	 * @param cuentaCotizacion
	 *            the cuentaCotizacion to set
	 */
	public void setCuentaCotizacion(String cuentaCotizacion) {
		this.cuentaCotizacion = cuentaCotizacion;
	}

	/**
	 * @return the nombreCuentaCotizacion
	 */
	public String getNombreCuentaCotizacion() {
		return this.nombreCuentaCotizacion;
	}

	/**
	 * @param nombreCuentaCotizacion
	 *            the nombreCuentaCotizacion to set
	 */
	public void setNombreCuentaCotizacion(String nombreCuentaCotizacion) {
		this.nombreCuentaCotizacion = nombreCuentaCotizacion;
	}

	/**
	 * @return the numeroContrato
	 */
	public String getNumeroContrato() {
		return this.numeroContrato;
	}

	/**
	 * @param numeroContrato
	 *            the numeroContrato to set
	 */
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	/**
	 * @return the puestoTrabajo
	 */
	public String getPuestoTrabajo() {
		return this.puestoTrabajo;
	}

	/**
	 * @param puestoTrabajo
	 *            the puestoTrabajo to set
	 */
	public void setPuestoTrabajo(String puestoTrabajo) {
		this.puestoTrabajo = puestoTrabajo;
	}

	/**
	 * @return the numeroEmpleado
	 */
	public String getNumeroEmpleado() {
		return this.numeroEmpleado;
	}

	/**
	 * @param numeroEmpleado
	 *            the numeroEmpleado to set
	 */
	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	/**
	 * @return the codigoColectivo
	 */
	public String getCodigoColectivo() {
		return this.codigoColectivo;
	}

	/**
	 * @param codigoColectivo
	 *            the codigoColectivo to set
	 */
	public void setCodigoColectivo(String codigoColectivo) {
		this.codigoColectivo = codigoColectivo;
	}

	public String getOperarioMuestreado() {
		return operarioMuestreado;
	}

	public void setOperarioMuestreado(String operarioMuestreado) {
		this.operarioMuestreado = operarioMuestreado;
	}

	public String getCodigoPropietario() {
		return codigoPropietario;
	}

	public void setCodigoPropietario(String codigoPropietario) {
		this.codigoPropietario = codigoPropietario;
	}

	public String getNombreAnimal() {
		return nombreAnimal;
	}

	public void setNombreAnimal(String nombreAnimal) {
		this.nombreAnimal = nombreAnimal;
	}

	public String getCodigoDescripcion() {
		return codigoDescripcion;
	}

	public void setCodigoDescripcion(String codigoDescripcion) {
		this.codigoDescripcion = codigoDescripcion;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PetMuesPaciente other = (PetMuesPaciente) obj;
		if (codigoColectivo == null) {
			if (other.codigoColectivo != null)
				return false;
		} else if (!codigoColectivo.equals(other.codigoColectivo))
			return false;
		if (codigoDescripcion == null) {
			if (other.codigoDescripcion != null)
				return false;
		} else if (!codigoDescripcion.equals(other.codigoDescripcion))
			return false;
		if (codigoPoliza == null) {
			if (other.codigoPoliza != null)
				return false;
		} else if (!codigoPoliza.equals(other.codigoPoliza))
			return false;
		if (codigoPropietario == null) {
			if (other.codigoPropietario != null)
				return false;
		} else if (!codigoPropietario.equals(other.codigoPropietario))
			return false;
		if (cuentaCotizacion == null) {
			if (other.cuentaCotizacion != null)
				return false;
		} else if (!cuentaCotizacion.equals(other.cuentaCotizacion))
			return false;
		if (nombreAnimal == null) {
			if (other.nombreAnimal != null)
				return false;
		} else if (!nombreAnimal.equals(other.nombreAnimal))
			return false;
		if (nombreCuentaCotizacion == null) {
			if (other.nombreCuentaCotizacion != null)
				return false;
		} else if (!nombreCuentaCotizacion.equals(other.nombreCuentaCotizacion))
			return false;
		if (numeroContrato == null) {
			if (other.numeroContrato != null)
				return false;
		} else if (!numeroContrato.equals(other.numeroContrato))
			return false;
		if (numeroEmpleado == null) {
			if (other.numeroEmpleado != null)
				return false;
		} else if (!numeroEmpleado.equals(other.numeroEmpleado))
			return false;
		if (numeroPaciente == null) {
			if (other.numeroPaciente != null)
				return false;
		} else if (!numeroPaciente.equals(other.numeroPaciente))
			return false;
		if (operarioMuestreado == null) {
			if (other.operarioMuestreado != null)
				return false;
		} else if (!operarioMuestreado.equals(other.operarioMuestreado))
			return false;
		if (puestoTrabajo == null) {
			if (other.puestoTrabajo != null)
				return false;
		} else if (!puestoTrabajo.equals(other.puestoTrabajo))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
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

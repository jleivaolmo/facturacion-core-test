package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.Date;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolMuesEstado}.
 *
 * <p>The persistent class. . .T_SolMuesEstado</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_SOLMUESESTADO,
indexes={@Index(name = "IDX_T_SolMuesEstado_unique",  columnList="fk_SolicitudMuestreo,codigoEstado", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolMuesEstado extends BasicEntity implements SetComponent {

	private static final long serialVersionUID = 440964193719052993L;

	@Basic
	private String codigoEstado;

	@Basic
	private String codigoEstadoSec;

	@Basic
	private Date fechaEstado;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudMuestreo", nullable = false)
	@JsonBackReference
	private SolicitudMuestreo solicitud;

	/**
	 * @return the codigoEstado
	 */
	public String getCodigoEstado() {
		return codigoEstado;
	}

	/**
	 * @param codigoEstado
	 *            the codigoEstado to set
	 */
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	/**
	 * @return the codigoEstadoSec
	 */
	public String getCodigoEstadoSec() {
		return codigoEstadoSec;
	}

	/**
	 * @param codigoEstadoSec
	 *            the codigoEstadoSec to set
	 */
	public void setCodigoEstadoSec(String codigoEstadoSec) {
		this.codigoEstadoSec = codigoEstadoSec;
	}

	/**
	 * @return the fechaEstado
	 */
	public Date getFechaEstado() {
		return fechaEstado;
	}

	/**
	 * @param fechaEstado
	 *            the fechaEstado to set
	 */
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	/**
	 * @return the estados
	 */
	public SolicitudMuestreo getSolicitud() {
		return this.solicitud;
	}

	/**
	 * @param solicitud
	 *            the estados to set
	 */
	public void setSolicitud(SolicitudMuestreo solicitud) {
		this.solicitud = solicitud;
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
		SolMuesEstado other = (SolMuesEstado) obj;
		if (codigoEstado == null) {
			if (other.codigoEstado != null)
				return false;
		} else if (!codigoEstado.equals(other.codigoEstado))
			return false;
		if (codigoEstadoSec == null) {
			if (other.codigoEstadoSec != null)
				return false;
		} else if (!codigoEstadoSec.equals(other.codigoEstadoSec))
			return false;
		if (fechaEstado == null) {
			if (other.fechaEstado != null)
				return false;
		} else if (!fechaEstado.equals(other.fechaEstado))
			return false;
		return true;
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setSolicitud((SolicitudMuestreo) cabecera);
	}

}

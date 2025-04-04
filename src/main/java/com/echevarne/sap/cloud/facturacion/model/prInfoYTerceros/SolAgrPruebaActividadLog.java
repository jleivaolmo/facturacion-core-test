package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_SolAgrPruebaActividadLog")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrPruebaActividadLog extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 432074441157096919L;

	@Basic
	private String tipoMsg;

	@Lob
	@Column(length=5000)
	private String descripcion;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar cronomarcador;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolAgrPruebaActividad", nullable = false)
	@JsonManagedReference
	private SolAgrPruebaActividad actividad;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		SolAgrPruebaActividadLog other = (SolAgrPruebaActividadLog) obj;
		if (cronomarcador == null) {
			if (other.cronomarcador != null)
				return false;
		} else if (!cronomarcador.equals(other.cronomarcador))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (tipoMsg == null) {
			if (other.tipoMsg != null)
				return false;
		} else if (!tipoMsg.equals(other.tipoMsg))
			return false;
		return true;
	}
}

package com.echevarne.sap.cloud.facturacion.model.logAuditoria;

import java.sql.Time;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_LOGAUDITORIASOLICITUD")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class LogAuditoriaSolicitud extends BasicEntity {

	private static final long serialVersionUID = -6822688000643333043L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "FK_TRAZABILIDADSOLICITUD", nullable = false)
	private TrazabilidadSolicitud trazabilidadSolicitud;

	@Basic
	private String codPeticion;

	@Basic
	private String codUsuario;

	@Basic
	private String nombreUsuario;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fecha;

	@Basic
	private Time hora;

	@Basic
	private String accionRealizada;

	@Basic
	private String factura;

	@Basic
	private String prueba;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogAuditoriaSolicitud other = (LogAuditoriaSolicitud) obj;
		return Objects.equals(accionRealizada, other.accionRealizada) && Objects.equals(codPeticion, other.codPeticion)
				&& Objects.equals(codUsuario, other.codUsuario) && Objects.equals(factura, other.factura) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(hora, other.hora) && Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(prueba, other.prueba)
				&& Objects.equals(trazabilidadSolicitud, other.trazabilidadSolicitud);
	}
}

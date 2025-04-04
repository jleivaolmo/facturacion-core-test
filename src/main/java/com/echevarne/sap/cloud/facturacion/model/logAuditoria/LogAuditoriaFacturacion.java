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
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupada;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;

@Entity
@Table(name = "T_LOGAUDITORIAFACTURACION")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class LogAuditoriaFacturacion extends BasicEntity {

	private static final long serialVersionUID = 2397285096555310L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "FK_TRAZABILIDAD", nullable = false)
	private TrazabilidadSolicitudAgrupada trazabilidad;

	@Basic
	private String codFactura;

	@Basic
	private String salesOrder;

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

	public TrazabilidadSolicitudAgrupada getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(TrazabilidadSolicitudAgrupada trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public String getAccionRealizada() {
		return accionRealizada;
	}

	public void setAccionRealizada(String accionRealizada) {
		this.accionRealizada = accionRealizada;
	}

	public String getCodFactura() {
		return codFactura;
	}

	public void setCodFactura(String codFactura) {
		this.codFactura = codFactura;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		LogAuditoriaFacturacion other = (LogAuditoriaFacturacion) obj;
		return Objects.equals(accionRealizada, other.accionRealizada) && Objects.equals(codFactura, other.codFactura) && Objects.equals(codUsuario, other.codUsuario)
				&& Objects.equals(fecha, other.fecha) && Objects.equals(hora, other.hora) && Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(salesOrder, other.salesOrder)
				&& Objects.equals(trazabilidad, other.trazabilidad);
	}
}

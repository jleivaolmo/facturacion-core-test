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
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;

@Entity
@Table(name = "T_LOGAUDITORIAPRUEBA")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class LogAuditoriaPrueba extends BasicEntity {

	private static final long serialVersionUID = 2397285096555310L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "FK_TRAZABILIDAD", nullable = false)
	private Trazabilidad trazabilidad;

	@Basic
	private String codPeticion;

	@Basic
	private String codPrueba;

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
	private Long prefactura;

	@Basic
	private Integer posicionPrefactura;

	public Trazabilidad getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(Trazabilidad trazabilidad) {
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

	public Long getPrefactura() {
		return prefactura;
	}

	public void setPrefactura(Long prefactura) {
		this.prefactura = prefactura;
	}

	public String getCodPeticion() {
		return codPeticion;
	}

	public void setCodPeticion(String codPeticion) {
		this.codPeticion = codPeticion;
	}

	public String getCodPrueba() {
		return codPrueba;
	}

	public void setCodPrueba(String codPrueba) {
		this.codPrueba = codPrueba;
	}

	public Integer getPosicionPrefactura() {
		return posicionPrefactura;
	}

	public void setPosicionPrefactura(Integer posicionPrefactura) {
		this.posicionPrefactura = posicionPrefactura;
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
		LogAuditoriaPrueba other = (LogAuditoriaPrueba) obj;
		return Objects.equals(accionRealizada, other.accionRealizada) && Objects.equals(codPeticion, other.codPeticion) && Objects.equals(codPrueba, other.codPrueba)
				&& Objects.equals(codUsuario, other.codUsuario) && Objects.equals(fecha, other.fecha) && Objects.equals(hora, other.hora) && Objects.equals(nombreUsuario, other.nombreUsuario)
				&& Objects.equals(posicionPrefactura, other.posicionPrefactura) && Objects.equals(prefactura, other.prefactura) && Objects.equals(trazabilidad, other.trazabilidad);
	}

}

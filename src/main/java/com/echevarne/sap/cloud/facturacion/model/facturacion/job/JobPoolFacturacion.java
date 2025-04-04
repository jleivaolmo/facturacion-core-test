package com.echevarne.sap.cloud.facturacion.model.facturacion.job;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Table(name = "T_JobPoolFacturacion")
@ToString(callSuper = false, includeFieldNames = false)
@Builder(toBuilder = true)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class JobPoolFacturacion extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8869502344872749792L;

	public static final String MODELOREPETICION_DIARIO = "DIARIO";
	public static final String MODELOREPETICION_SEMANAL = "SEMANAL";
	public static final String MODELOREPETICION_MENSUAL = "MENSUAL";

	/* Parametros pool facturacion */

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionClientes> clientes = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionOrgVentas> organizacionesVentas = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionCodDelegacion> codigosDelegacion = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionCodRemitentes> remitentes = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionCompanias> companias = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionTiposPeticion> tiposPeticion = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionPruebas> pruebas = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionSectores> sectores = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionCodigosPeticion> codigosPeticion = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "job")
	@JsonManagedReference
	@Builder.Default
	private Set<JobPoolFacturacionEjecucion> ejecuciones = new HashSet<>();

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaIniFacturacion;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaFinFacturacion;

	/* Parametros especificos job */

	@Basic
	private Integer periodicidad;

	@Basic
	private String modeloRepeticion;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar inicioJob;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar finJob;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar proximaEjecucion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPoolFacturacion other = (JobPoolFacturacion) obj;
		if (finJob == null) {
			if (other.finJob != null)
				return false;
		} else if (!finJob.equals(other.finJob))
			return false;
		if (inicioJob == null) {
			if (other.inicioJob != null)
				return false;
		} else if (!inicioJob.equals(other.inicioJob))
			return false;
		if (periodicidad == null) {
			if (other.periodicidad != null)
				return false;
		} else if (!periodicidad.equals(other.periodicidad))
			return false;
		if (proximaEjecucion == null) {
			if (other.proximaEjecucion != null)
				return false;
		} else if (!proximaEjecucion.equals(other.proximaEjecucion))
			return false;
		return true;
	}
}

package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import lombok.*;
import org.hibernate.annotations.JoinFormula;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.ImpuestosText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Table(name = "T_JobPruebaTerceros")
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class JobPruebaTerceros extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3725665487766264336L;

	public static final String MODELOREPETICION_DIARIO = "DIARIO";
	public static final String MODELOREPETICION_SEMANAL = "SEMANAL";
	public static final String MODELOREPETICION_MENSUAL = "MENSUAL";

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.ProveedoresPrinfoyterceros, Label = "Proveedores", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoProveedor", LocalDataProperty = "proveedor") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreProveedor") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "nifProveedor") })})
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "proveedoresText/nombreProveedor")
	private String proveedor;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String sociedad;

	@Column(length = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Impuestos, Label = "Indicador impuestos", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "indicadorImpuestos") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "impuestosText/nombre")
	private String indicadorImpuestos;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacionDesde;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacionHasta;

	@OneToMany(cascade = ALL, mappedBy = "cabecera")
	@JsonManagedReference
	private Set<JobPruebaTercerosDelProductivas> delegacionesProductivas = new HashSet<>();

	@OneToMany(cascade = ALL, mappedBy = "jobPruebaTerceros")
	@JsonManagedReference
	@ToString.Exclude
	private Set<SolAgrPruebaActividad> ejecuciones = new HashSet<>();

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar inicioJob;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar finJob;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar proximaEjecucion;

	@Basic
	private Integer periodicidad;

	@Basic
	private String modeloRepeticion;

	@Basic
	private String tipoAgrupacion;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sociedad", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="indicadorImpuestos", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ImpuestosText impuestosText;

	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Setter(AccessLevel.PRIVATE)
	@JoinFormula("(SELECT j.codigoProveedor FROM VT_PROVEEDORES_PRINFOYTERCEROS j WHERE j.codigoProveedor = proveedor AND proveedor IS NOT NULL)")
	private ProveedoresPrinfoytercerosText proveedoresText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPruebaTerceros other = (JobPruebaTerceros) obj;
		if (delegacionesProductivas == null) {
			if (other.delegacionesProductivas != null)
				return false;
		} else if (!delegacionesProductivas.equals(other.delegacionesProductivas))
			return false;
		if (fechaValidacionDesde == null) {
			if (other.fechaValidacionDesde != null)
				return false;
		} else if (!fechaValidacionDesde.equals(other.fechaValidacionDesde))
			return false;
		if (fechaValidacionHasta == null) {
			if (other.fechaValidacionHasta != null)
				return false;
		} else if (!fechaValidacionHasta.equals(other.fechaValidacionHasta))
			return false;
		if (indicadorImpuestos == null) {
			if (other.indicadorImpuestos != null)
				return false;
		} else if (!indicadorImpuestos.equals(other.indicadorImpuestos))
			return false;
		if (proveedor == null) {
			if (other.proveedor != null)
				return false;
		} else if (!proveedor.equals(other.proveedor))
			return false;
		if (sociedad == null) {
			if (other.sociedad != null)
				return false;
		} else if (!sociedad.equals(other.sociedad))
			return false;
		if (inicioJob == null) {
			if (other.inicioJob != null)
				return false;
		} else if (!inicioJob.equals(other.inicioJob))
			return false;
		if (finJob == null) {
			if (other.finJob != null)
				return false;
		} else if (!finJob.equals(other.finJob))
			return false;
		if (periodicidad == null) {
			if (other.periodicidad != null)
				return false;
		} else if (!periodicidad.equals(other.periodicidad))
			return false;
		if (modeloRepeticion == null) {
			if (other.modeloRepeticion != null)
				return false;
		} else if (!modeloRepeticion.equals(other.modeloRepeticion))
			return false;
		if (tipoAgrupacion == null) {
			if (other.tipoAgrupacion != null)
				return false;
		} else if (!tipoAgrupacion.equals(other.tipoAgrupacion))
			return false;

		return true;
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public ImpuestosText getImpuestosText() {
		return EntityUtil.getOrNull(() -> this.impuestosText, ImpuestosText::getNombre);
	}

	public ProveedoresPrinfoytercerosText getProveedoresText() {
		return EntityUtil.getOrNull(() -> this.proveedoresText, ProveedoresPrinfoytercerosText::getNombreProveedor);
	}
}

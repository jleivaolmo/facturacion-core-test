package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.DelegacionProductivaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.UnidadesProductivasText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = false, includeFieldNames = false)
public abstract class SolAgrPruebaBaseEntity extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7576869188003032288L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolAgrPruebaActividad", nullable = false)
	@JsonManagedReference
	private SolAgrPruebaActividad actividad;

	@Basic
	private String tipoAgrupacion;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String sociedad;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.DelegacionProductiva, Label = "Delegacion Productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDelegacion", LocalDataProperty = "delegacionProductiva") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDelegacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "delegacionText/nombreDelegacion")
	private String delegacionProductiva;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4UnidadesProductivas, Label = "Unidad productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "unidadProductiva") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombre")}) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="unidadProductivaText/nombre")
	private String unidadProductiva;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.ProveedoresPrinfoyterceros, Label = "Proveedores", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoProveedor", LocalDataProperty = "proveedor") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreProveedor") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "nifProveedor") })})
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "proveedoresText/nombreProveedor")
	private String proveedor;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacionDesde;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacionHasta;

	@Basic
	private String pedidoCompras;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaPedidoCompras;

	@Column(length = 2)
	private String indicadorImpuestos;

	@Basic
	private String mensajeError;
	
	@Basic
	private Long fk_idLog;

	public abstract void addItem(SolAgrPruebaItemBaseEntity item);

	public abstract Set<?> getItems();

	/*
	 * Associations Texts
	 *
	 ********************************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionProductiva", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DelegacionProductivaText delegacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sociedad", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "proveedor", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProveedoresPrinfoytercerosText proveedoresText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "unidadProductiva", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UnidadesProductivasText unidadProductivaText;

	public DelegacionProductivaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, DelegacionProductivaText::getNombreDelegacion);
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public ProveedoresPrinfoytercerosText getProveedoresText() {
		return EntityUtil.getOrNull(() -> this.proveedoresText, ProveedoresPrinfoytercerosText::getNombreProveedor);
	}

	public UnidadesProductivasText getUnidadProductivaText() {
		return EntityUtil.getOrNull(() -> this.unidadProductivaText, UnidadesProductivasText::getNombre);
	}

	@Override
	public boolean onEquals(Object o) {
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;
		SolAgrPruebaBaseEntity other = (SolAgrPruebaBaseEntity) o;
		if (delegacionProductiva == null) {
			if (other.delegacionProductiva != null)
				return false;
		} else if (!delegacionProductiva.equals(other.delegacionProductiva))
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
		if (pedidoCompras == null) {
			if (other.pedidoCompras != null)
				return false;
		} else if (!pedidoCompras.equals(other.pedidoCompras))
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
		if (unidadProductiva == null) {
			if (other.unidadProductiva != null)
				return false;
		} else if (!unidadProductiva.equals(other.unidadProductiva))
			return false;
		if (fechaPedidoCompras == null) {
			if (other.fechaPedidoCompras != null)
				return false;
		} else if (!fechaPedidoCompras.equals(other.fechaPedidoCompras))
			return false;
		if (fk_idLog == null) {
			if (other.fk_idLog != null)
				return false;
		} else if (!fk_idLog.equals(other.fk_idLog))
			return false;
		if (indicadorImpuestos == null) {
			if (other.indicadorImpuestos != null)
				return false;
		} else if (!indicadorImpuestos.equals(other.indicadorImpuestos))
			return false;
		if (mensajeError == null) {
			if (other.mensajeError != null)
				return false;
		} else if (!mensajeError.equals(other.mensajeError))
			return false;
		if (tipoAgrupacion == null) {
			if (other.tipoAgrupacion != null)
				return false;
		} else if (!tipoAgrupacion.equals(other.tipoAgrupacion))
			return false;
		return true;
	}
}

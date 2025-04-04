package com.echevarne.sap.cloud.facturacion.model.privados;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.CondPreciosPrivadosText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.EstadoText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoDescuentoText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sortable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FieldSortEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_MasDataDescuento", indexes = {
		@Index(name = "MasDataDescuento_byCodigo", columnList = "codigo", unique = false)})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class MasDataDescuento extends BasicMasDataEntity {

	private static final long serialVersionUID = 3548752321257389319L;

	@Basic
	@Sap(filterable=true)
    @Sortable(order = FieldSortEnum.Asc, priority = 1)
	private String codigo;

	@Basic
	@Sap(filterable=false)
	private String descripcion;

	@Basic
	@Sap(filterable=false)
	private boolean porcentual;

	@Column(precision = 16, scale = 2)
	@Sap(filterable=false)
	private BigDecimal valor = BigDecimal.ZERO;

	@Column(precision = 16, scale = 2, nullable=false)
	@Sap(unit = "codigoDivisa", filterable = false)
	private BigDecimal brutoMinimo = BigDecimal.ZERO;

	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda del contrato", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"), @ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String codigoDivisa;

	@NonNull
	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.CondPreciosPrivados, Label = "Delegación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "conditionType") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "descripcion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "condPreciosPrivadosText/descripcion")
	private String conditionType;

	@Basic
	@Column(name = "validez_desde", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
    @Sortable(order = FieldSortEnum.Asc, priority = 2)
	private Calendar validezDesde;

	@Basic
	@Column(name = "validez_hasta", nullable = false)
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezHasta;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.EstadoEntidad, Label = "Estado del descuento", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "estado", LocalDataProperty = "statusFiori") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed)
	private String statusFiori;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoDescuento, Label = "Tipo del descuento", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "tipo", LocalDataProperty = "tipoFiori") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed)
	private String tipoFiori;

	public String getStatusFiori() {
		if (this.active) {
			return EstadoText.ESTADO_ACTIVO;
		} else {
			return EstadoText.ESTADO_INACTIVO;
		}
	}

	public String getTipoFiori() {
		if (this.porcentual) {
			return TipoDescuentoText.TIPO_PORCENTUAL;
		} else {
			return TipoDescuentoText.TIPO_FIJO;
		}
	}

	/*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="conditionType", referencedColumnName = "codigo", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CondPreciosPrivadosText condPreciosPrivadosText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataDescuento other = (MasDataDescuento) obj;
		return  Objects.equals(this.codigo, other.codigo) &&
				Objects.equals(this.descripcion, other.descripcion) &&
				this.porcentual == other.porcentual &&
				Objects.equals(this.conditionType, other.conditionType) &&
				Objects.equals(this.valor, other.valor) &&
				Objects.equals(this.brutoMinimo, other.brutoMinimo);
	}

	public CondPreciosPrivadosText getCondPreciosPrivadosText() {
		return EntityUtil.getOrNull(() -> this.condPreciosPrivadosText, CondPreciosPrivadosText::getDescripcion);
	}

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}
}

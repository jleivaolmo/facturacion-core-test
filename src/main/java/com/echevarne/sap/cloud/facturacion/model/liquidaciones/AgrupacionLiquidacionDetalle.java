package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import static javax.persistence.CascadeType.PERSIST;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesProfesionalesText;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.GroupByField;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_AgrupacionLiquidacionDetalle")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class AgrupacionLiquidacionDetalle extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7295697606925254045L;

	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
	@Basic
	private Calendar fechaActividad;
	
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
	@Basic
	private Calendar fechaPeticion;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal importeLiquidacion1;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal importeLiquidacion2;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal importeLiquidacion3;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal importeLiquidacion4;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal importeLiquidacion5;

	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Divisa", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	@GroupByField(fieldName = "codigoDivisa")
	private String codigoDivisa;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.RemitentesProfesionales, Label = "Remitente/Profesional", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoRemPr") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteProfesionalText/nombre")
	private String codigoRemPr;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "compania") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "companiaText/nombreCompania")
	private String compania;

	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "oficinaVentasText/nombreOficina")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "oficinaVentas") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	private String oficinaVentas;

	@Basic
	private String codigoPeticion;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal importeLiqNumPruebas;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_tramoLiqNumPruebas")
	@JsonBackReference
	private TramosLiqNumPruebas tramo;
	
	@Basic
	private Integer numPruebasTramo;
	
	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_agrupacionliquidacion", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private AgrupacionLiquidacion agrupacionLiquidacion;

	@OneToOne(mappedBy = "agrupLiquidacionDetalle", cascade = { PERSIST }, fetch = FetchType.LAZY) // not REMOVE
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnore
	private TrazabilidadSolicitud trazabilidad;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoRemPr", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesProfesionalesText remitenteProfesionalText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "compania", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "oficinaVentas", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText oficinaVentasText;

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}

	public RemitentesProfesionalesText getRemitenteProfesionalText() {
		return EntityUtil.getOrNull(() -> this.remitenteProfesionalText, RemitentesProfesionalesText::getNombre);
	}

	public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}

	public OficinaVentaText getOficinaVentasText() {
		return EntityUtil.getOrNull(() -> this.oficinaVentasText, OficinaVentaText::getNombreOficina);
	}

	@Override
	public boolean onEquals(Object o) {
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;
		AgrupacionLiquidacionDetalle other = (AgrupacionLiquidacionDetalle) o;
		return Objects.equals(codigoDivisa, other.codigoDivisa) && Objects.equals(fechaActividad, other.fechaActividad)
				&& Objects.equals(importeLiquidacion1, other.importeLiquidacion1) && Objects.equals(importeLiquidacion2, other.importeLiquidacion2)
				&& Objects.equals(importeLiquidacion3, other.importeLiquidacion3) && Objects.equals(importeLiquidacion4, other.importeLiquidacion4)
				&& Objects.equals(importeLiquidacion5, other.importeLiquidacion5) && Objects.equals(codigoRemPr, other.codigoRemPr)
				&& Objects.equals(compania, other.compania) && Objects.equals(oficinaVentas, other.oficinaVentas)
				&& Objects.equals(codigoPeticion, other.codigoPeticion);
	}

}

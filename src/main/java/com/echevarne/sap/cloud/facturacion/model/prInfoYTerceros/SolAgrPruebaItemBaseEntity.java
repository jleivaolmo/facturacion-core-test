package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.UnidadesProductivasText;
import com.echevarne.sap.cloud.facturacion.model.texts.UnidadesText;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = false, includeFieldNames = false)
public abstract class SolAgrPruebaItemBaseEntity extends BasicEntity {

	private static final long serialVersionUID = 2101276317853715100L;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegacion Productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionProductiva") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "delegacionText/nombreOficina")
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
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacion;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Perfil / Prueba", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "prueba") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "materialText/nombreMaterial")
	private String prueba;

	@Column(precision = 16, scale = 3)
	private BigDecimal cantidad;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa")
	private BigDecimal importe;

	@Basic
	private Long cantidadBase;

	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String codigoDivisa;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Unidades, Label = "Unidad de medida", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "unidadMedida") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombre")}) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="unidadesText/nombre")
	@ToString.Exclude
	private String unidadMedida;

	@Basic
	private String centroCoste;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "sectorText/nombreSector")
	private String sector;

	@Basic
	private String posicion;

	@Basic
	private String nombreUsuario;

	public abstract Set<Trazabilidad> getTrazabilidad();

	public abstract void addTrazabilidad(Trazabilidad trazabilidad);

	public abstract void setAgrupacion(SolAgrPruebaBaseEntity agrupacion);

	public abstract SolAgrPruebaBaseEntity getAgrupacion();

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "prueba", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MaterialesVentaText materialText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionProductiva", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sector", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "unidadProductiva", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UnidadesProductivasText unidadProductivaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="unidadMedida", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private UnidadesText unidadesText;

	@Override
	public boolean onEquals(Object o) {
		SolAgrPruebaItemBaseEntity other = (SolAgrPruebaItemBaseEntity) o;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		if (cantidadBase == null) {
			if (other.cantidadBase != null)
				return false;
		} else if (!cantidadBase.equals(other.cantidadBase))
			return false;
		if (centroCoste == null) {
			if (other.centroCoste != null)
				return false;
		} else if (!centroCoste.equals(other.centroCoste))
			return false;
		if (delegacionProductiva == null) {
			if (other.delegacionProductiva != null)
				return false;
		} else if (!delegacionProductiva.equals(other.delegacionProductiva))
			return false;
		if (fechaValidacion == null) {
			if (other.fechaValidacion != null)
				return false;
		} else if (!fechaValidacion.equals(other.fechaValidacion))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (prueba == null) {
			if (other.prueba != null)
				return false;
		} else if (!prueba.equals(other.prueba))
			return false;
		if (unidadProductiva == null) {
			if (other.unidadProductiva != null)
				return false;
		} else if (!unidadProductiva.equals(other.unidadProductiva))
			return false;
		return true;
	}

	public MaterialesVentaText getMaterialText() {
		return EntityUtil.getOrNull(() -> this.materialText, MaterialesVentaText::getNombreMaterial);
	}

	public OficinaVentaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, OficinaVentaText::getNombreOficina);
	}

	public SectorVentaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
	}

	public UnidadesProductivasText getUnidadProductivaText() {
		return EntityUtil.getOrNull(() -> this.unidadProductivaText, UnidadesProductivasText::getNombre);
	}

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}

	public UnidadesText getUnidadesText() {
		return EntityUtil.getOrNull(() -> this.unidadesText, UnidadesText::getNombre);
	}
}

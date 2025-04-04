package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.CentroText;
import com.echevarne.sap.cloud.facturacion.model.texts.ConceptosFacturacionText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorPruebaText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPosicionText;
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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_PERF_DETALLE")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@IdClass(PerfilesSubKey.class)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class DetallePerfil implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3037106753494501652L;

	/*
	 * Clave
	 *
	 *******************************************/
	@Id
	@Basic
	private Long uuid;

	@Id
	@Basic
	private Long uuid_parent;

	/*
	 * Campos
	 *
	 *******************************************/

	@Column(length = 4)
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="oficinaText/nombreOficina")
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionProductiva") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	private String delegacionProductiva;

	@Basic
	@Sap(text = "centroText/nombreCentro")
	private String codigoCentro;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Perfil / Prueba", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoPrueba") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="pruebaText/nombreMaterial")
	private String codigoPrueba;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.ConceptosFacturacion, Label = "Concepto de facturación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoConcepto") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="conceptoText/nombreMaterial")
	private String codigoConcepto;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaPrecio;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacion;

	@Basic
	private String motivoBloqueo;

	@Basic
	private String motivoRechazo;

	@Column(precision = 16, scale = 3)
	@Sap(unit="codigoDivisa")
	private BigDecimal valorPosicion;

	@Column(precision = 16, scale = 3)
	@Sap(unit="codigoDivisa")
	private BigDecimal valorImpuestos;

	@Column(precision = 16, scale = 3)
	@Sap(unit="codigoDivisa")
	private BigDecimal valorNeto;

	@Column(precision = 16, scale = 2)
	private BigDecimal valorPuntos;

	@Column(length = 5)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Divisa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String codigoDivisa;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Tipo de posicion", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPosicion", LocalDataProperty = "tipoPosicion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPosicion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="tipoPosicionText/nombreTipoPosicion")
	private String tipoPosicion;

	@Basic
	private String pictureUrl;

	@Basic
	@Sap(filterable = false)
	private String nombreEstado;

	@Basic
	@Sap(filterable = false)
	private String nombreGrupoEstado;

	@Basic
	@Sap(filterable = false)
	private String nombreTipologia;

	@Basic
	@Sap(text = "sectorPruebaText/nombreSector", filterable = true)
	private String sectorPrueba;

	@Basic
	@Column(precision = 34, scale = 3)
	@Sap(unit = "unidad")
	private BigDecimal cantidad;

	@Basic
	private String unidad;

	/*
	 * UI Fields
	 *
	 *******************************************/
	@Basic
	private int critically;

	/*
     * Associations
     *
     ********************************************/
    @OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="uuid_parent", insertable=false, updatable=false)
	private Prueba prueba;

	/*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="delegacionProductiva", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText oficinaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="codigoPrueba", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private MaterialesVentaText pruebaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="codigoConcepto", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private ConceptosFacturacionText conceptoText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoDivisa", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoPosicion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPosicionText tipoPosicionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sectorPrueba", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorPruebaText sectorPruebaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCentro", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CentroText centroText;

	public OficinaVentaText getOficinaText() {
		return EntityUtil.getOrNull(() -> this.oficinaText, OficinaVentaText::getNombreOficina);
	}

	public MaterialesVentaText getPruebaText() {
		return EntityUtil.getOrNull(() -> this.pruebaText, MaterialesVentaText::getNombreMaterial);
	}

	public ConceptosFacturacionText getConceptoText() {
		return EntityUtil.getOrNull(() -> this.conceptoText, ConceptosFacturacionText::getNombreMaterial);
	}

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}

	public TipoPosicionText getTipoPosicionText() {
		return EntityUtil.getOrNull(() -> this.tipoPosicionText, TipoPosicionText::getNombreTipoPosicion);
	}

	public SectorPruebaText getSectorPruebaText() {
		return EntityUtil.getOrNull(() -> this.sectorPruebaText, SectorPruebaText::getNombreSector);
	}

	public CentroText getCentroText() {
		return EntityUtil.getOrNull(() -> this.centroText, CentroText::getNombreCentro);
	}
}

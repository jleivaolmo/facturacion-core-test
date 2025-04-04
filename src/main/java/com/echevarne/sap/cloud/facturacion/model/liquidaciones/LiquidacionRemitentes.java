package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.ImpuestosText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.ProveedoresText;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesText;
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
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.OverrideEntity;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.SumField;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.WhereField;
import com.echevarne.sap.cloud.facturacion.odata.jpa.listeners.CustomQueryListener;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_LIQ_REMITENTES")
@IdClass(LiquidacionKey.class)
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
@EntityListeners(value = CustomQueryListener.class)
@OverrideEntity(entityClass = LiquidacionRemitentes.class)
public class LiquidacionRemitentes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7646833263961678948L;
	
	public LiquidacionRemitentes(String sociedad, String organizacionCompras, String grupoCompras, String proveedor,
			String grupoProveedor, String agrupador, Boolean facturaUnica, BigDecimal totalTipoLiquidacion1,
			BigDecimal totalTipoLiquidacion2, BigDecimal totalTipoLiquidacion3, BigDecimal totalTipoLiquidacion4,
			BigDecimal totalTipoLiquidacion5, String codigoDivisa, String indicadorIVA, String idProveedorLiquidacion,
			BigDecimal importeLiquidadoBruto, BigDecimal importeLiquidadoNeto) {
		this.sociedad = sociedad;
		this.organizacionCompras = organizacionCompras;
		this.grupoCompras = grupoCompras;
		this.proveedor = proveedor;
		this.grupoProveedor = grupoProveedor;
		this.agrupador = agrupador;
		this.facturaUnica = facturaUnica;
		this.totalTipoLiquidacion1 = totalTipoLiquidacion1;
		this.totalTipoLiquidacion2 = totalTipoLiquidacion2;
		this.totalTipoLiquidacion3 = totalTipoLiquidacion3;
		this.totalTipoLiquidacion4 = totalTipoLiquidacion4;
		this.totalTipoLiquidacion5 = totalTipoLiquidacion5;
		this.codigoDivisa = codigoDivisa;
		this.indicadorIVA = indicadorIVA;
		this.idProveedorLiquidacion = idProveedorLiquidacion;
		this.importeLiquidadoBruto = importeLiquidadoBruto;
		this.importeLiquidadoNeto = importeLiquidadoNeto;
	}

	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
	@WhereField(fieldName = "fechaActividad")
	@Basic
	private Calendar fechaActividad;
	
	@Id
	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	@GroupByField(fieldName = "sociedad")
	private String sociedad;
	
	@Id
	@Basic
	@GroupByField(fieldName = "organizacionCompras")
	private String organizacionCompras;
	
	@Id
	@Basic
	@GroupByField(fieldName = "grupoCompras")
	private String grupoCompras;
	
	@Id
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Proveedores, Label = "Proveedor", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoProveedor", LocalDataProperty = "proveedor") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreProveedor") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "proveedorText/nombreProveedor")
	@GroupByField(fieldName = "proveedor")
	private String proveedor;
	
	@Id
	@Basic
	@GroupByField(fieldName = "grupoProveedor")
	private String grupoProveedor;
	
	@Basic(fetch = FetchType.LAZY)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "oficinaVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)
	@WhereField(fieldName = "oficinaVentas")
	private String oficinaVentas;
	
	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Remitente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "codigoRemitente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteText/nombreRemitente", filterable = true)
	@WhereField(fieldName = "codigoRemitente")
	private String codigoRemitente;
	
	@Id
	@Basic
	@GroupByField(fieldName = "agrupador")
	private String agrupador;
	
	@Basic
	@GroupByField(fieldName = "facturaUnica")
	private Boolean facturaUnica;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "totalTipoLiquidacion1")
	private BigDecimal totalTipoLiquidacion1;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "totalTipoLiquidacion2")
	private BigDecimal totalTipoLiquidacion2;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "totalTipoLiquidacion3")
	private BigDecimal totalTipoLiquidacion3;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "totalTipoLiquidacion4")
	private BigDecimal totalTipoLiquidacion4;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "totalTipoLiquidacion5")
	private BigDecimal totalTipoLiquidacion5;
	
	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Divisa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	@GroupByField(fieldName = "codigoDivisa")
	private String codigoDivisa;
	
	@Basic
	@GroupByField(fieldName = "indicadorIVA")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Impuestos, Label = "Indicador IVA", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "indicadorIVA") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "impuestosText/nombre")
	private String indicadorIVA;
	
	@Sap
	@GroupByField(fieldName = "idProveedorLiquidacion")
	@Basic
	private String idProveedorLiquidacion;
	
	@Sap(filterable = false)
	@WhereField(fieldName = "idTrazabilidadSolicitud")
	@Basic
	private Long idTrazabilidadSolicitud;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "importeLiquidadoBruto")
	private BigDecimal importeLiquidadoBruto;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	@SumField(fieldName = "importeLiquidadoNeto")
	private BigDecimal importeLiquidadoNeto;
	
	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sociedad", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "proveedor", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProveedoresText proveedorText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoRemitente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesText remitenteText;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private DivisasText divisasText;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="indicadorIVA", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ImpuestosText impuestosText;

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public ProveedoresText getProveedorText() {
		return EntityUtil.getOrNull(() -> this.proveedorText, ProveedoresText::getNombreProveedor);
	}
	
	public RemitentesText getRemitenteText() {
		return EntityUtil.getOrNull(() -> this.remitenteText, RemitentesText::getNombreRemitente);
	}
	
	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}
	
	public ImpuestosText getImpuestosText() {
		return EntityUtil.getOrNull(() -> this.impuestosText, ImpuestosText::getNombre);
	}
}

package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadoLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.ImpuestosText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.ProveedoresText;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_AgrupacionLiquidacion", indexes = {
		@Index(name = "IDX_AgrupacionLiquidacion", columnList = "tipoLiquidacion, proveedor, agrupador, sociedad, facturaUnica, grupoProveedor, "
				+ "organizacionCompras, fechaInicio, fechaFin", unique = false) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class AgrupacionLiquidacion extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -114387061833896597L;
	
	/* 1: remitente, 2: profesional */
	@Basic
	private int tipoLiquidacion; 

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String sociedad;
	
	@Basic
	private String organizacionCompras;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Proveedores, Label = "Proveedor", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoProveedor", LocalDataProperty = "proveedor") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreProveedor") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "proveedorText/nombreProveedor")
	private String proveedor;
	
	@Basic
	private String grupoProveedor;
	
	@Basic
	private String agrupador;
	
	@Basic
	private Boolean facturaUnica;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal totalTipoLiquidacion1;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal totalTipoLiquidacion2;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal totalTipoLiquidacion3;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal totalTipoLiquidacion4;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
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
	
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
	@Basic
	private Calendar fechaInicio;
	
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
	@Basic
	private Calendar fechaFin;
	
	@Basic
	private String periodoContable;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Impuestos, Label = "Indicador IVA", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "indicadorIVA") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "impuestosText/nombre")
	private String indicadorIVA;
	
	@Basic
	private String purchaseOrder;
	
	@Basic
	private Boolean pdf_necesario;
    
    @Basic
   	private Boolean detalle_necesario;
    
    @Basic
   	private String psw_encriptacion;
    
    @Basic
	private String grupoArticulos;
    
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
	@Basic
	private Calendar fechaCreacion;
    
    @Basic
   	private boolean aplica_liq_numpruebas;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_Estado", nullable = false)
    private MasDataEstadoLiquidacion estado;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "agrupacionLiquidacion", orphanRemoval = true)
   	@JsonManagedReference
	private Set<AgrupacionLiquidacionDetalle> detalle = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "agrupacionLiquidacion", orphanRemoval = true)
   	@JsonManagedReference
	private Set<AgrupacionLiquidacionEmailDestinatario> emailDestinatario = new HashSet<>();
	
	@Basic
	private String nombreEstado;
	
	public String getNombreEstado() {
		return estado.getDescripcion();
	}
	
	@Basic
	private Long idJob;
	
	@Basic
	private Boolean enviada;
	
	@Column(precision = 16, scale = 2)
	@Sap(filterable = false, unit = "codigoDivisa")
	private BigDecimal totalLiqNumPruebas;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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
	
	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}
	
	public ImpuestosText getImpuestosText() {
		return EntityUtil.getOrNull(() -> this.impuestosText, ImpuestosText::getNombre);
	}
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		AgrupacionLiquidacion other = (AgrupacionLiquidacion) obj;
		return Objects.equals(agrupador, other.agrupador) 
				&& Objects.equals(facturaUnica, other.facturaUnica)
				&& Objects.equals(grupoArticulos, other.grupoArticulos)
				&& Objects.equals(grupoProveedor, other.grupoProveedor)
				&& Objects.equals(organizacionCompras, other.organizacionCompras)
				&& Objects.equals(proveedor, other.proveedor) && Objects.equals(sociedad, other.sociedad)
				&& Objects.equals(totalTipoLiquidacion1, other.totalTipoLiquidacion1)
				&& Objects.equals(totalTipoLiquidacion2, other.totalTipoLiquidacion2)
				&& Objects.equals(totalTipoLiquidacion3, other.totalTipoLiquidacion3)
				&& Objects.equals(totalTipoLiquidacion4, other.totalTipoLiquidacion4)
				&& Objects.equals(totalTipoLiquidacion5, other.totalTipoLiquidacion5)
				&& tipoLiquidacion == other.tipoLiquidacion;
	}

	@Override
	public String toString() {
		return id.toString();
	}

}

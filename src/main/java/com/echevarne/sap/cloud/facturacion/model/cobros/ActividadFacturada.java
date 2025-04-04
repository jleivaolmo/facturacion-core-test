package com.echevarne.sap.cloud.facturacion.model.cobros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.SociedadesText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipologiaActividadCobrosText;
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
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_ACTIVIDAD_FACTURADA_COBROS")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class ActividadFacturada implements Serializable {

	private static final long serialVersionUID = 2111827426385838730L;
	
	@Id
	@Basic
	@Sap(visible = false)
	private Long id;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Companies, Label = "Sociedades", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoSociedad", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSociedad") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "sociedadText/nombreSociedad", filterable = true, aggregationRole = "dimension")
	private String sociedad;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "cliente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "z_nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente", filterable = true, aggregationRole = "dimension")
	private String cliente;
	
	@Basic
	@Column(name = "tipo_actividad")
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipologiaActividadCobros, Label = "Tipos de Actividad", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoTipologia", LocalDataProperty = "tipoActividad") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipologia") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text = "tipoActividadText/nombreTipologia", filterable = true)
	private String tipoActividad;
	
	@Basic
	private String autorizacion;
	
	@Basic
	private String baremo;
	
	@Basic
	private String nombre;
	
	@Basic
	private String nif;
	
	@Basic
	@Column(name = "fecha_peticion")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fechaPeticion;
	
	@Basic
	@Column(name = "numero_factura")
	private String numeroFactura;
	
	@Basic
	@Column(name = "fecha_factura")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fechaFactura;
	
	@Basic
	@Column(name = "numero_operacion")
	private String numeroOperacion;
	
	@Basic
	@Column(name = "numero_poliza")
	private String numeroPoliza;
	
	@Basic
	@Column(name = "numero_peticion")
	private String numeroPeticion;
	
	@Basic
	private String volante;
	
	@Basic
	@Column(name = "codigo_prueba")
	private String codigoPrueba;
	
	@Basic
	@Column(name = "descripcion_prueba")
	private String descPrueba;
	
	@Column(name = "importe_facturado", precision = 16, scale = 2)
	private BigDecimal importeFacturado;
	
	@Column(name = "cantidad_facturada", precision = 16, scale = 2)
	private BigDecimal cantidadFacturada;
	
	@Basic
	@Column(name = "cobro_asignado")
	private boolean cobroAsignado;
	
	@Basic
	private boolean reorganizado;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "cliente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sociedad", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SociedadesText sociedadText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "tipo_actividad", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipologiaActividadCobrosText tipoActividadText;
	
	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}
	
	public SociedadesText getSociedadText() {
		return EntityUtil.getOrNull(() -> this.sociedadText, SociedadesText::getNombreSociedad);
	}
	
	public TipologiaActividadCobrosText getTipoActividadText() {
		return EntityUtil.getOrNull(() -> this.tipoActividadText, TipologiaActividadCobrosText::getNombreTipologia);
	}

}

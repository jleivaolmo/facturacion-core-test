package com.echevarne.sap.cloud.facturacion.model.cobros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.SociedadesText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntityType;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsTypeEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.GroupByField;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.SumField;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity 
@Table(name = "V_COBROS_CON_ACT_FACT_AGRUPADA")
@ToString(callSuper = false, includeFieldNames = false)
@Getter
@Setter
@SapEntityType(semantics = SemanticsTypeEnum.AGGREGATE)
public class CobroYActFacturadaAgrupada implements Serializable {

	private static final long serialVersionUID = 5972469038307812603L;

	@EmbeddedId
	private CobroYActFacturadaKey cobroYActFacturadaKey;
	
	@Basic
	@Column(name = "tipo_actividad")
	@GroupByField(fieldName = "tipoActividad")
	@Sap(visible = false)
	private String tipoActividad;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Companies, Label = "Sociedades", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoSociedad", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSociedad") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "sociedadText/nombreSociedad", filterable = true, aggregationRole = "dimension")
	@GroupByField(fieldName = "sociedad")
	private String sociedad;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "cliente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "z_nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente", filterable = true, aggregationRole = "dimension")
	@GroupByField(fieldName = "cliente")
	private String cliente;
	
	@Basic
	@Sap(aggregationRole = "dimension")
	@GroupByField(fieldName = "asignacion")
	private String asignacion;
	
	@Basic
	@Column(name = "fecha_peticion")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	@GroupByField(fieldName = "fechaPeticion")
	private Calendar fechaPeticion;
	
	@Basic
	@Column(name = "fecha_cobro")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	@GroupByField(fieldName = "fechaCobro")
	private Calendar fechaCobro;
	
	@Basic
	@Column(name = "fecha_factura")
	@Sap(displayFormat = DisplayFormatEnum.Date)
	@GroupByField(fieldName = "fechaFactura")
	private Calendar fechaFactura;
	
	@Basic
	@GroupByField(fieldName = "autorizacion")
	private String autorizacion;
	
	@Basic
	@Column(name = "nombre_paciente")
	@GroupByField(fieldName = "nombrePaciente")
	private String nombrePaciente;
	
	@Basic
	@GroupByField(fieldName = "nif")
	private String nif;
	
	@Basic
	@Column(name = "numero_peticion")
	@GroupByField(fieldName = "numeroPeticion")
	private String numeroPeticion;
	
	@Basic
	@Column(name = "numero_operacion")
	@GroupByField(fieldName = "numeroOperacion")
	private String numeroOperacion;
	
	@Basic
	@Column(name = "numero_poliza")
	@GroupByField(fieldName = "numeroPoliza")
	private String numeroPoliza;
	
	@Basic
	@Column(name = "documento_unico")
	@GroupByField(fieldName = "documentoUnico")
	private String documentoUnico;
	
	@Basic
	@Column(name = "numero_factura")
	@GroupByField(fieldName = "numeroFactura")
	private String numeroFactura;
	
	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa", aggregationRole = "measure")
	@SumField(fieldName = "importe")
	private BigDecimal importe;
	
	@Basic
	@GroupByField(fieldName = "asignado")
	@Sap(visible = false)
	private Boolean asignado;
	
	@Basic
	@Column(name = "numero_peticion_btp")
	@GroupByField(fieldName = "numPeticionBTP")
	@Sap(visible = false)
	private String numPeticionBTP;
	
	@Basic
	@Column(name = "numero_factura_sap")
	@GroupByField(fieldName = "numFacturaSAP")
	@Sap(visible = false)
	private String numFacturaSAP;
	
	@Column(length = 5, name = "codigo_divisa")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda del contrato", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE, aggregationRole = "dimension")
	@GroupByField(fieldName = "codigoDivisa")
	private String codigoDivisa;
	
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
	@JoinColumn(name = "codigo_divisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;
	
	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}
	
	public SociedadesText getSociedadText() {
		return EntityUtil.getOrNull(() -> this.sociedadText, SociedadesText::getNombreSociedad);
	}
	
	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}

}

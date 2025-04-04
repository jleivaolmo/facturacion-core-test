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
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.GroupByField;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity 
@Table(name = "V_ACTIVIDAD_FACTURADA_AGRUPADA_PENDIENTE")
@ToString(callSuper = false, includeFieldNames = false)
@Getter
@Setter
@Builder
public class ActFactAgrupadaPendiente implements Serializable {

	private static final long serialVersionUID = 8241823095435075885L;

	@Id
	@Basic
	private Long id;
	
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
	private String autorizacion;
	
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
	
	@Column(name = "importe_facturado", precision = 16, scale = 2)
	private BigDecimal importeFacturado;
	
	@Column(name = "importe_cobro", precision = 16, scale = 2)
	private BigDecimal importeCobrado;
	
	@Column(name = "importe_pendiente", precision = 16, scale = 2)
	private BigDecimal importePendiente;
	
	@Basic
	@Column(name = "estado_cobro")
	private String estado;
	
	@Basic
	private String asignacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "cliente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sociedad", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SociedadesText sociedadText;
	
	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}
	
	public SociedadesText getSociedadText() {
		return EntityUtil.getOrNull(() -> this.sociedadText, SociedadesText::getNombreSociedad);
	}

}

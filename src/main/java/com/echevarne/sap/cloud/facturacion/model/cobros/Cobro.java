package com.echevarne.sap.cloud.facturacion.model.cobros;

import java.io.Serializable;
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
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_COBRO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = true)
public class Cobro extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 1690960733818020657L;
	
	@Basic
	@Column(name = "fecha_carga")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fechaCarga;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Companies, Label = "Sociedades", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoSociedad", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSociedad") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sociedadText/nombreSociedad", filterable = true)
	private String sociedad;
	
	@Basic
	private String asignacion;
	
	@Basic
	@Column(name = "fecha_cobro")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fechaCobro;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "cliente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "z_nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente", filterable = true)
	private String cliente;
	
	@Basic
	@Column(name = "tipo_actividad")
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipologiaActividadCobros, Label = "Tipos de Actividad", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoTipologia", LocalDataProperty = "tipoActividad") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipologia") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text = "tipoActividadText/nombreTipologia", filterable = true)
	private String tipoActividad;
	
	@Basic
	@Column(name = "fecha_peticion")
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fechaPeticion;
	
	@Basic
	private String autorizacion;
	
	@Basic
	private String baremo;
	
	@Basic
	@Column(name = "descripcion_prueba")
	private String descPrueba;
	
	@Basic
	@Column(name = "nombre_paciente")
	private String nombrePaciente;
	
	@Basic
	private String dni;
	
	@Basic
	@Column(name = "numero_peticion")
	private String numPeticion;
	
	@Basic
	@Column(name = "numero_operacion")
	private String numOperacion;
	
	@Column(name = "numero_poliza")
	@Basic
	private String numPoliza;
	
	@Column(name = "documento_unico")
	@Basic
	private String docUnico;
	
	@Column(name = "numero_factura")
	@Basic
	private String numFactura;
	
	@Column(name = "importe_cobro", precision = 16, scale = 2)
	private BigDecimal importeCobro;
	
	@Column(precision = 16, scale = 3)
	private BigDecimal cantidad;
	
	@Column(name = "reg_actividad")
	@Basic
	private String regActividad;
	
	@Column(name = "peticion_btp")
	@Basic
	private String numPeticionBTP;
	
	@Column(name = "cod_prueba_btp")
	@Basic
	private String codPruebaBTP;
	
	@Column(name = "numero_factura_sap")
	@Basic
	private String numFacturaSAP;
	
	@Column(name = "doc_fi_cobro_sap")
	@Basic
	private String docFICobroSAP;
	
	@Column(name = "ej_doc_fi_cobro_sap")
	@Basic
	private String ejDocFICobroSAP;
	
	@Basic
	@ColumnDefault("false")
	private boolean asignado;
	
	@Basic
	@Column(name = "id_af")
	private Long idAf;
	
	
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
	

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Cobro other = (Cobro) obj;
		return Objects.equals(fechaCarga, other.fechaCarga)
				&& Objects.equals(sociedad, other.sociedad) && Objects.equals(asignacion, other.asignacion)
				&& Objects.equals(fechaCobro, other.fechaCobro) && Objects.equals(cliente, other.cliente)
				&& Objects.equals(tipoActividad, other.tipoActividad)
				&& Objects.equals(fechaPeticion, other.fechaPeticion)
				&& Objects.equals(autorizacion, other.autorizacion) && Objects.equals(baremo, other.baremo)
				&& Objects.equals(descPrueba, other.descPrueba) && Objects.equals(nombrePaciente, other.nombrePaciente)
				&& Objects.equals(dni, other.dni) && Objects.equals(numPeticion, other.numPeticion)
				&& Objects.equals(numOperacion, other.numOperacion) && Objects.equals(numPoliza, other.numPoliza)
				&& Objects.equals(docUnico, other.docUnico) && Objects.equals(numFactura, other.numFactura)
				&& Objects.equals(importeCobro, other.importeCobro) && Objects.equals(cantidad, other.cantidad)
				&& Objects.equals(regActividad, other.regActividad)
				&& Objects.equals(numPeticionBTP, other.numPeticionBTP)
				&& Objects.equals(codPruebaBTP, other.codPruebaBTP)
				&& Objects.equals(numFacturaSAP, other.numFacturaSAP)
				&& Objects.equals(docFICobroSAP, other.docFICobroSAP)
				&& Objects.equals(ejDocFICobroSAP, other.ejDocFICobroSAP)
				&& Objects.equals(asignado, other.asignado
				&& Objects.equals(idAf, other.idAf));
	}	

}

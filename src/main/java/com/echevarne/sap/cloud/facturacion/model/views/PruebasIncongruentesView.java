package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_PRUEBAS_INCONGRUENTES_ACT")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class PruebasIncongruentesView implements Serializable {

	private static final long serialVersionUID = 7508786681114326892L;

	@Id
	@Basic
	private Long uuid;

	@Column(length = 18)
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Prueba / Perfil", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "materialProvocante") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="matProvocanteText/nombreMaterial")
	private String materialProvocante;

	@Column(length = 18)
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Prueba / Perfil", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "materialRechazable") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="matRechazableText/nombreMaterial")
	private String materialRechazable;

	@Column(length = 10)
	@Sap(text="codigoCliente")
	private String codigoCliente;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "codigoCompania") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="companiaText/nombreCompania")
	private String codigoCompania;

	@Column(precision = 2)
	private int valorPrioridad;

	@Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaInicio;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaFin;

	/*
     * Associations Texts
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoCliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoCompania", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText companiaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="materialProvocante", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private MaterialesVentaText matProvocanteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="materialRechazable", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private MaterialesVentaText matRechazableText;

	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}

	public ClientesText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, ClientesText::getNombreCliente);
	}

	public MaterialesVentaText getMatProvocanteText() {
		return EntityUtil.getOrNull(() -> this.matProvocanteText, MaterialesVentaText::getNombreMaterial);
	}

	public MaterialesVentaText getMatRechazableText() {
		return EntityUtil.getOrNull(() -> this.matRechazableText, MaterialesVentaText::getNombreMaterial);
	}
}


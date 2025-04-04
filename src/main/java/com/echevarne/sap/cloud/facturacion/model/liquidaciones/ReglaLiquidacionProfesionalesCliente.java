package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

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
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesAllText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_ReglaLiquidacionProfesionalesCliente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ReglaLiquidacionProfesionalesCliente extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -953367920383360599L;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.ClientesAll, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }),
			@ValueListParameter(ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "z_nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente", filterable = true)
	private String codigoCliente;
	
	@Basic
	private Integer secuencia;
	
	public ReglaLiquidacionProfesionalesCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_reglaLiquidacion", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private ReglaLiquidacionProfesionales reglaLiquidacion;
	
	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCliente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesAllText clienteText;
	
	public ClientesAllText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesAllText::getNombreCliente);
	}
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ReglaLiquidacionProfesionalesCliente other = (ReglaLiquidacionProfesionalesCliente) obj;
		return Objects.equals(codigoCliente, other.codigoCliente);
	}
}

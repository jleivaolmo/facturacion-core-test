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
import com.echevarne.sap.cloud.facturacion.model.texts.ProfesionalesText;
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
@Table(name = "T_ReglaLiquidacionProfesionalesCodProfesional")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ReglaLiquidacionProfesionalesCodProfesional extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -953367920383360599L;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Profesionales, Label = "Profesional", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoProfesional") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "profesionalText/nombre", filterable = true)
	private String codigoProfesional;
	
	@Basic
	private Integer secuencia;
	
	public ReglaLiquidacionProfesionalesCodProfesional(String codigoProfesional) {
		this.codigoProfesional = codigoProfesional;
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
	@JoinColumn(name = "codigoProfesional", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProfesionalesText profesionalText;
	
	public ProfesionalesText getProfesionalText() {
		return EntityUtil.getOrNull(() -> this.profesionalText, ProfesionalesText::getNombre);
	}
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ReglaLiquidacionProfesionalesCodProfesional other = (ReglaLiquidacionProfesionalesCodProfesional) obj;
		return Objects.equals(codigoProfesional, other.codigoProfesional);
	}
}

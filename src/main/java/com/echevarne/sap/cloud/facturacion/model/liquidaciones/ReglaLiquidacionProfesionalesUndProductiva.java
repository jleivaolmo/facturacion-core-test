package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.util.Objects;
import javax.persistence.Basic;
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
import com.echevarne.sap.cloud.facturacion.model.texts.UnidadesProductivasText;
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
@Table(name = "T_ReglaLiquidacionProfesionalesUndProductiva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ReglaLiquidacionProfesionalesUndProductiva extends BasicEntity {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4259339992785924919L;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4UnidadesProductivas, Label = "Unidades productivas", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "unidadProductiva") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "unidadesProductivasText/nombre")
	private String unidadProductiva;
	
	@Basic
	private Integer secuencia;
	
	public ReglaLiquidacionProfesionalesUndProductiva(String unidadProductiva) {
		this.unidadProductiva = unidadProductiva;
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
	@JoinColumn(name = "unidadProductiva", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UnidadesProductivasText unidadesProductivasText;
	
	public UnidadesProductivasText getUnidadesProductivasText() {
		return EntityUtil.getOrNull(() -> this.unidadesProductivasText, UnidadesProductivasText::getNombre);
	}
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ReglaLiquidacionProfesionalesUndProductiva other = (ReglaLiquidacionProfesionalesUndProductiva) obj;
		return Objects.equals(unidadProductiva, other.unidadProductiva);
	}
}

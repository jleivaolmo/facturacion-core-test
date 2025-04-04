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
import com.echevarne.sap.cloud.facturacion.model.texts.DelegacionProductivaAllText;
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
@Table(name = "T_ReglaLiquidacionProfesionalesDelProductiva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ReglaLiquidacionProfesionalesDelProductiva extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4259339992785924919L;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.DelegacionProductivaAll, Label = "Delegacion Productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDelegacion", LocalDataProperty = "delegacionProductiva") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDelegacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="delegacionText/nombreDelegacion")
	private String delegacionProductiva;
	
	@Basic
	private Integer secuencia;
	
	public ReglaLiquidacionProfesionalesDelProductiva(String delegacionProductiva) {
		this.delegacionProductiva = delegacionProductiva;
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
	@JoinColumn(name="delegacionProductiva", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DelegacionProductivaAllText delegacionText;
	
	public DelegacionProductivaAllText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, DelegacionProductivaAllText::getNombreDelegacion);
	}
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ReglaLiquidacionProfesionalesDelProductiva other = (ReglaLiquidacionProfesionalesDelProductiva) obj;
		return Objects.equals(delegacionProductiva, other.delegacionProductiva);
	}
}

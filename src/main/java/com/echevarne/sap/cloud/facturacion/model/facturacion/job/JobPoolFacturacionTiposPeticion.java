package com.echevarne.sap.cloud.facturacion.model.facturacion.job;

import java.io.Serializable;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_JobPoolFacturacionTiposPeticion")
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class JobPoolFacturacionTiposPeticion extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3394954792622936287L;

	@Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed,  text="tipoPeticionText/nombreTipoPeticion")
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
    private int tipoPeticion;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPoolFacturacion", nullable = false)
	@JsonBackReference
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private JobPoolFacturacion cabecera;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoPeticion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPoolFacturacionTiposPeticion other = (JobPoolFacturacionTiposPeticion) obj;
		if (tipoPeticion != other.tipoPeticion)
			return false;
		return true;
	}

	public TipoPeticionText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
	}
}

package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.io.Serializable;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
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
@Table(name = "T_JobPruebaTercerosDelProductivas")
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class JobPruebaTercerosDelProductivas extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4820030685185588156L;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.DelegacionProductiva, Label = "Delegacion Productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDelegacion", LocalDataProperty = "delegacionProductiva") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDelegacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "delegacionText/nombreDelegacion")
	private String delegacionProductiva;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPruebaTerceros", nullable = false)
	@JsonBackReference
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private JobPruebaTerceros cabecera;

	/*
	 * Associations Texts
	 *
	 ********************************************/

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionProductiva", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPruebaTercerosDelProductivas other = (JobPruebaTercerosDelProductivas) obj;
		if (delegacionProductiva == null) {
			if (other.delegacionProductiva != null)
				return false;
		} else if (!delegacionProductiva.equals(other.delegacionProductiva))
			return false;
		return true;
	}

	public OficinaVentaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, OficinaVentaText::getNombreOficina);
	}
}

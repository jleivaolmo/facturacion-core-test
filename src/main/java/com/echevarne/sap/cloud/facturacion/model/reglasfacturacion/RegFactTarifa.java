package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
import com.echevarne.sap.cloud.facturacion.model.texts.TarifasText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Germán Laso
 * @since 04/10/2020
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_RegFactTarifa")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
public class RegFactTarifa extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = -655477727989302102L;

	@Matcheable
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Tarifas, Label = "Tarifa", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigoTarifa", LocalDataProperty = "tarifa") },
				ValueListParameterDisplayOnly = {
				@ValueListParameterDisplayOnly(ValueListProperty = "nombreTarifa")
		})
	})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="tarifaText/nombreTarifa")
	private String tarifa;

	/*
	 * Custom Constructor
	 *
	 ********************************************/
	public RegFactTarifa(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
	 * Persistence events
	 *
	 ********************************************/
	@PrePersist
	public void prePersist() {
		if (StringUtils.equalsAnyOrNull(this.tarifa, StringUtils.EMPTY))
			this.tarifa = StringUtils.ANY;
	}

	/*
	 * Associations
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ReglaFacturacion")
	@JsonBackReference
	private ReglasFacturacion regla;

	/*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="tarifa", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private TarifasText tarifaText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactTarifa other = (RegFactTarifa) obj;
		return Objects.equals(tarifa, other.tarifa);
	}

	public TarifasText getTarifaText() {
		return EntityUtil.getOrNull(() -> this.tarifaText, TarifasText::getNombreTarifa);
	}
}

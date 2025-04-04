package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
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
import org.hibernate.annotations.Cache;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_RegFactCompanias")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
@Cacheable(false)
public class RegFactCompanias extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1841604144933351357L;

	@Basic
	@Matcheable
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Interlocutor comercial", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "interlocutorCompania") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="companiaText/nombreCompania")
    private String interlocutorCompania;

	/*
     * Custom Constructor
     *
     ********************************************/
	public RegFactCompanias(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
     * Persistence events
     *
     ********************************************/
	@PrePersist
	public void prePersist() {
	    if(StringUtils.equalsAnyOrNull(this.interlocutorCompania,StringUtils.EMPTY))
	    	this.interlocutorCompania = StringUtils.ANY;
	}

	/*
     * Associations
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="fk_ReglaFacturacion")
	@JsonBackReference
	private ReglasFacturacion regla;

	/*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="interlocutorCompania", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactCompanias other = (RegFactCompanias) obj;
		return Objects.equals(interlocutorCompania, other.interlocutorCompania);
	}

	public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}
}

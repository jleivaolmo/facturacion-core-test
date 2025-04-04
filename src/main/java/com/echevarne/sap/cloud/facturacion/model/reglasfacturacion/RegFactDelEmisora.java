package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
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
@Table(name = "T_RegFactDelEmisora")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
@Cacheable(false)
public class RegFactDelEmisora extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 7681558508012426086L;

	@Matcheable
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación emisora", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionEmisora") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionEmisoraText/nombreOficina")
	private String delegacionEmisora;

	/*
     * CustomConstructor
     *
     ********************************************/
	public RegFactDelEmisora(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
     * Persistence events
     *
     ********************************************/
	@PrePersist
	public void prePersist() {
	    if(StringUtils.equalsAnyOrNull(this.delegacionEmisora,StringUtils.EMPTY))
	    	this.delegacionEmisora = StringUtils.ANY;
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
	@JoinColumn(name = "delegacionEmisora", referencedColumnName = "codigoOficina", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@BatchSize(size=100)
	private OficinaVentaText delegacionEmisoraText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactDelEmisora other = (RegFactDelEmisora) obj;
		return Objects.equals(delegacionEmisora, other.delegacionEmisora);
	}

	public OficinaVentaText getDelegacionEmisoraText() {
		return EntityUtil.getOrNull(() -> this.delegacionEmisoraText, OficinaVentaText::getNombreOficina);
	}
}

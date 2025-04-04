package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesText;
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
@Table(name = "T_RegFactRemitentes")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
public class RegFactRemitentes extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 3862506397624432343L;

	@Matcheable
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Interlocutor comercial", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "interlocutorRemitente") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="remitenteText/nombreRemitente")
    private String interlocutorRemitente;

	/*
     * Custom Constructor
     *
     ********************************************/
	public RegFactRemitentes(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
     * Persistence events
     *
     ********************************************/
	@PrePersist
	public void prePersist() {
	    if(StringUtils.equalsAnyOrNull(this.interlocutorRemitente,StringUtils.EMPTY))
	    	this.interlocutorRemitente = StringUtils.ANY;
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
	@JoinColumn(name="interlocutorRemitente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesText remitenteText;

    @Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactRemitentes other = (RegFactRemitentes) obj;
		return Objects.equals(interlocutorRemitente, other.interlocutorRemitente);
	}

	public RemitentesText getRemitenteText() {
		return EntityUtil.getOrNull(() -> this.remitenteText, RemitentesText::getNombreRemitente);
	}
}

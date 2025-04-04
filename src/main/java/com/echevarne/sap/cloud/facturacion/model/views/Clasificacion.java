package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipologiaFacturacionText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_PRUE_CLASIFICACION")
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class Clasificacion implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6923828693689196163L;

	/*
	 * Clave
	 *
	 *******************************************/
	@Id
	@Basic
	private Long uuid;

	/*
	 * Campos
	 *
	 *******************************************/
	@Basic
	@Sap(text="tipologiaText/nombreTipologia")
	private Long idTipologia;

	@Column(length = 5)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Divisa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "monedaTipologia") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String monedaTipologia;

	/*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="idTipologia", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipologiaFacturacionText tipologiaText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="monedaTipologia", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	public TipologiaFacturacionText getTipologiaText() {
		return EntityUtil.getOrNull(() -> this.tipologiaText, TipologiaFacturacionText::getNombreTipologia);
	}

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}
}

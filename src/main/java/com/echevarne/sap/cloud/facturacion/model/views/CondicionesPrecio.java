package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
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
@Table(name = "V_PRUE_PRECIO")
@IdClass(PruebasSubKey.class)
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class CondicionesPrecio implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2381636142602034866L;

	/*
	 * Clave
	 *
	 *******************************************/
	@Id
	@Basic
	private Long uuid_parent;

	@Id
	@Basic
	private Long uuid;

	/*
	 * Campos
	 *
	 *******************************************/
	@Basic
	private String pricingProcedureStep;

	@Basic
	private String pricingProcedureCounter;

	@Basic
	private String conditionType;

	@Column(precision = 16, scale = 3)
	@Sap(unit="conditionCurrency")
	private BigDecimal conditionRateValue;

	@Column(precision = 16, scale = 3)
	@Sap(unit="transactionCurrency")
	private BigDecimal conditionAmount;

	@Column(length = 5)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Divisa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "conditionCurrency") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="condCurrencyText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String conditionCurrency;

	@Column(precision = 16, scale = 3)
	@Sap(unit="conditionQuantityUnit")
	private BigDecimal conditionQuantity;

	@Basic
	private String conditionQuantityUnit;

	@Column(length = 5)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Divisa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "transactionCurrency") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="tranCurrencyText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String transactionCurrency;

	/*
     * Associations Texts
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="conditionCurrency", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText condCurrencyText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="transactionCurrency", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText tranCurrencyText;


	public DivisasText getCondCurrencyText() {
		return EntityUtil.getOrNull(() -> this.condCurrencyText, DivisasText::getNombreDivisa);
	}

	public DivisasText getTranCurrencyText() {
		return EntityUtil.getOrNull(() -> this.tranCurrencyText, DivisasText::getNombreDivisa);
	}
}

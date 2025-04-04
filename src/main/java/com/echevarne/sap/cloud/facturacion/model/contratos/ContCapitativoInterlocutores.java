package com.echevarne.sap.cloud.facturacion.model.contratos;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.InterlocutoresText;
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
import com.google.common.base.Objects;

/**
 * Class for the Entity {@link ContCapitativoInterlocutores}.
 *
 * @author Germán Laso
 * @since 17/04/2020
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_CONTRATOCAPITATIVOINTERLOCUTORES, indexes = {
	@Index(name = "IDX_byCodigo", columnList = "codigoInterlocutor", unique = false)
})
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ContCapitativoInterlocutores extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1059365905290987719L;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Interlocutores, Label = "Interlocutor comercial", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoInterlocutor")},
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="interlocutoresText/nombreCliente")
	private String codigoInterlocutor;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo")
	@JsonBackReference
	private ContratoCapitativo contrato;

	/*
     * Associations Texts
     *
     ********************************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoInterlocutor", referencedColumnName = "codigoCliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private InterlocutoresText interlocutoresText;

	public String getCodigoInterlocutor() {
		return codigoInterlocutor;
	}

	public void setCodigoInterlocutor(String codigoInterlocutor) {
		this.codigoInterlocutor = codigoInterlocutor;
	}

	public ContratoCapitativo getContrato() {
		return contrato;
	}

	public void setContrato(ContratoCapitativo contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return the interlocutoresText
	 */
	public InterlocutoresText getInterlocutoresText() {
		return EntityUtil.getOrNull(() -> this.interlocutoresText, InterlocutoresText::getNombreCliente);
	}

	/**
	 * @param interlocutoresText the interlocutoresText to set
	 */
	public void setInterlocutoresText(InterlocutoresText interlocutoresText) {
		this.interlocutoresText = interlocutoresText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ContCapitativoInterlocutores other = (ContCapitativoInterlocutores) obj;
		Objects.equal(codigoInterlocutor, other.codigoInterlocutor);
		return true;
	}

}

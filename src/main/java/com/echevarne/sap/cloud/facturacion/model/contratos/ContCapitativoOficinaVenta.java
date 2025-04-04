package com.echevarne.sap.cloud.facturacion.model.contratos;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
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
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.Objects;

@Entity
@Table(name = ConstEntities.ENTIDAD_CONTRATOCAPITATIVOOFICINAVENTA, indexes = {
		@Index(name = "IDX_byCodigoOficinaVenta", columnList = "codigoOficinaVenta", unique = false) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ContCapitativoOficinaVenta extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 126250132816550162L;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "codigoOficinaVenta") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionOrigenText/nombreOficina")
	private String codigoOficinaVenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo")
	@JsonBackReference
	private ContratoCapitativo contrato;

	public String getCodigoOficinaVenta() {
		return codigoOficinaVenta;
	}

	public void setCodigoOficinaVenta(String codigoOficinaVenta) {
		this.codigoOficinaVenta = codigoOficinaVenta;
	}

	public ContratoCapitativo getContrato() {
		return contrato;
	}

	public void setContrato(ContratoCapitativo contrato) {
		this.contrato = contrato;
	}

	/*
	 * Associations Texts
	 *
	 ********************************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoOficinaVenta", referencedColumnName = "codigoOficina", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionOrigenText;

	public OficinaVentaText getDelegacionOrigenText() {
		return EntityUtil.getOrNull(() -> this.delegacionOrigenText, OficinaVentaText::getNombreOficina);
	}

	public void setDelegacionOrigenText(OficinaVentaText oficinaVentaText) {
		this.delegacionOrigenText = oficinaVentaText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ContCapitativoOficinaVenta other = (ContCapitativoOficinaVenta) obj;
		Objects.equal(codigoOficinaVenta, other.codigoOficinaVenta);
		return true;
	}

}

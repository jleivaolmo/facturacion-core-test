package com.echevarne.sap.cloud.facturacion.model.texts;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VT_PETICION")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class CodigoPeticionHelp {

    @Id
    @Basic
	@Sap(filterable = true, filterRestriction = FilterRestrictionsEnum.Multiple)
    private String codigoPeticion;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") }, ValueListParameterDisplayOnly = {
                            @ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text = "tipoPeticionText/nombreTipoPeticion")
    private int tipoPeticion;

    @Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDelegacion", LocalDataProperty = "codigoOficina") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "oficinaText/nombreOficina")
    private String codigoDelegacion;

    /*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDelegacion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText oficinaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "tipoPeticion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

	public OficinaVentaText getOficinaText() {
		return EntityUtil.getOrNull(() -> this.oficinaText, OficinaVentaText::getNombreOficina);
	}

	public TipoPeticionText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
	}
}

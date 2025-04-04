package com.echevarne.sap.cloud.facturacion.model.conversion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.texts.CentroText;
import com.echevarne.sap.cloud.facturacion.model.texts.DelegacionProductivaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sortable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FieldSortEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

@Entity
@Table(name = ConstEntities.ENTIDAD_CONVERSIONCENTRO, indexes={@Index(name = "IDX_DelegProdCentro",  columnList="delegacionProductiva", unique=true)})
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ConversionCentro extends BasicEntity {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 4)
	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.DelegacionProductiva, Label = "Delegacion Productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDelegacion", LocalDataProperty = "delegacionProductiva") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDelegacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="delegacionText/nombreDelegacion")
	@Sortable(order=FieldSortEnum.Asc, priority=1)
	@NaturalId
	private String delegacionProductiva;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Centros, Label = "Nombre del centro", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoCentro", LocalDataProperty = "centro") },
                    ValueListParameterDisplayOnly = {
                    @ValueListParameterDisplayOnly(ValueListProperty = "nombreCentro") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, text="centroText/nombreCentro")
	@Sortable(order=FieldSortEnum.Asc, priority=2)
	private String centro;

	/*
	 * Associations
	 **********************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="delegacionProductiva", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DelegacionProductivaText delegacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="centro", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CentroText centroText;

	public String getDelegacionProductiva() {
		return delegacionProductiva;
	}

	public void setDelegacionProductiva(String delegacionProductiva) {
		this.delegacionProductiva = delegacionProductiva;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public DelegacionProductivaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, DelegacionProductivaText::getNombreDelegacion);
	}

	public void setDelegacionText(DelegacionProductivaText delegacionText) {
		this.delegacionText = delegacionText;
	}

	public CentroText getCentroText() {
		return EntityUtil.getOrNull(() -> centroText, CentroText::getNombreCentro);
	}

	public void setCentroText(CentroText centroText) {
		this.centroText = centroText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ConversionCentro other = (ConversionCentro) obj;
		return 	Objects.equals(centro, other.centro) &&
				Objects.equals(delegacionProductiva, other.delegacionProductiva);
	}

}

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
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionText;
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
@Table(
	name = ConstEntities.ENTIDAD_CONVERSIONSECTOR,
	indexes = {
		@Index(name = "ConversionSector_byTipoPeticion", columnList = "tipoPeticion", unique = true),
	}
)
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ConversionSector extends BasicEntity {

	private static final long serialVersionUID = -6516229310774209978L;

	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, text="tipoPeticionText/nombreTipoPeticion")
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
	@Basic
	@Sortable(order=FieldSortEnum.Asc, priority=1)
	@NaturalId
	private int tipoPeticion;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(
					ValueListParameterInOut = {
						@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector")
					},
					ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector")
					})
			})
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, text="sectorText/nombreSector")
	@Sortable(order=FieldSortEnum.Asc, priority=2)
	private String sector;

	/*
	 * Associations
	 **********************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoPeticion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="sector", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

	public int getTipoPeticion() {
		return tipoPeticion;
	}

	public void setTipoPeticion(int tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public TipoPeticionText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
	}

	public void setPeticionText(TipoPeticionText tipoPeticionText) {
		this.tipoPeticionText = tipoPeticionText;
	}

	public SectorVentaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
	}

	public void setSectorText(SectorVentaText sectorText) {
		this.sectorText = sectorText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)  return true;
		if (getClass() != obj.getClass())  return false;
		ConversionSector other = (ConversionSector) obj;
		return  (tipoPeticion != other.tipoPeticion) &&
				Objects.equals(this.sector, other.sector);
	}

}

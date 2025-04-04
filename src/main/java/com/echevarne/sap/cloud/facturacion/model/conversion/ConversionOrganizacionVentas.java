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
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
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
	name = ConstEntities.ENTIDAD_CONVERSIONORGANIZACIONVENTAS,
	indexes = {
		@Index(name = "IDX_bySector", columnList = "sector", unique = false),
		@Index(name = "IDX_byCodigoDelegacion", columnList = "codigoDelegacion,sector", unique = false),
	}
)
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ConversionOrganizacionVentas extends BasicEntity {

	private static final long serialVersionUID = 1690103256652118046L;

	@Basic
	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "codigoSector"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="sectorText/nombreSector")
	@Sortable(order= FieldSortEnum.Asc, priority=2)
	private String sector;

	@Priorizable
	@Column(length = 4)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "codigoDelegacion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="delegacionText/nombreOficina")
	@Sortable(order=FieldSortEnum.Asc, priority=1)
	private String codigoDelegacion;

	@Basic
	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "codigoOrganizacion"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="organizacionText/nombreOrganizacion")
	@Sortable(order=FieldSortEnum.Asc, priority=3)
	private String organizacionVentas;

	/*
	 * Associations
	 **********************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoDelegacion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="sector", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="organizacionVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getCodigoDelegacion() {
		return codigoDelegacion;
	}

	public void setCodigoDelegacion(String codigoDelegacion) {
		this.codigoDelegacion = codigoDelegacion;
	}

	public String getOrganizacionVentas() {
		return organizacionVentas;
	}

	public void setOrganizacionVentas(String organizacionVentas) {
		this.organizacionVentas = organizacionVentas;
	}

	public OficinaVentaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, OficinaVentaText::getNombreOficina);
	}

	public void setDelegacionText(OficinaVentaText delegacionText) {
		this.delegacionText = delegacionText;
	}

	public SectorVentaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
	}

	public void setSectorText(SectorVentaText sectorText) {
		this.sectorText = sectorText;
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public void setOrganizacionText(OrganizacionVentaText organizacionText) {
		this.organizacionText = organizacionText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)  return true;
		if (getClass() != obj.getClass())  return false;
		ConversionOrganizacionVentas other = (ConversionOrganizacionVentas) obj;
		return  Objects.equals(this.organizacionVentas, other.organizacionVentas) &&
				Objects.equals(this.codigoDelegacion, other.codigoDelegacion) &&
				Objects.equals(this.sector, other.sector);
	}

}

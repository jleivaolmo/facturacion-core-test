package com.echevarne.sap.cloud.facturacion.model.divisores;

import java.util.Objects;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorPruebaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.Getter;
import lombok.Setter;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(
	name = ConstEntities.ENTIDAD_GRUPOSSECTORES,
	indexes = {
		@Index(name = "IDX_bySectorSalesOrg", columnList = "sector,salesOrganization", unique = true),
	}
)
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, deletable = false)
public class GruposSectores extends BasicEntity {

	private static final long serialVersionUID = -1218719891125029390L;

	@Column(columnDefinition="NVARCHAR(10)")
    private String grupo;

	@Column(columnDefinition="NVARCHAR(4)")
	@ValueList(CollectionPath = ValueListEntitiesEnum.SectoresPrueba, Label = "Sector de ventas prueba", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sectorPruebaText/nombreSector", filterable = true)
    private String sector;

	@Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "salesOrganization") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="organizacionText/nombreOrganizacion")
	private String salesOrganization;

	/*
     * Associations Texts
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="salesOrganization", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sector", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorPruebaText sectorPruebaText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		GruposSectores other = (GruposSectores) obj;
		return  Objects.equals(this.grupo, other.grupo) &&
				Objects.equals(this.sector, other.sector) &&
				Objects.equals(this.salesOrganization, other.salesOrganization);
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public SectorPruebaText getSectorPruebaText() {
		return EntityUtil.getOrNull(() -> this.sectorPruebaText, SectorPruebaText::getNombreSector);
	}
}

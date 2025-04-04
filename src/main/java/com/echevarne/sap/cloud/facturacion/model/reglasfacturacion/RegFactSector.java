package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
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
@Table(name = "T_RegFactSector")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
@Cacheable(false)
public class RegFactSector extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = -4167504087161018965L;

	@Matcheable
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4DivisionPrueba, Label = "Sector de ventas de las pruebas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="sectorText/nombreSector")
    private String sectorVentas;

	/*
     * Custom Constructor
     *
     ********************************************/
	public RegFactSector(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
     * Persistence events
     *
     ********************************************/
	@PrePersist
	public void prePersist() {
	    if(StringUtils.equalsAnyOrNull(this.sectorVentas,StringUtils.EMPTY))
	    	this.sectorVentas = StringUtils.ANY;
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
	@JoinColumn(name="sectorVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorPruebaText sectorText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactSector other = (RegFactSector) obj;
		return Objects.equals(sectorVentas, other.sectorVentas);
	}

	public SectorPruebaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorPruebaText::getNombreSector);
	}
}

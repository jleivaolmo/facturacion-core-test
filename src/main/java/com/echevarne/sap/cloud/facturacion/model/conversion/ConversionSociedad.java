package com.echevarne.sap.cloud.facturacion.model.conversion;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

@Entity
@Table(name = ConstEntities.ENTIDAD_CONVERSIONSOCIEDAD)
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ConversionSociedad extends BasicEntity {

	private static final long serialVersionUID = -6183846208158328836L;

	@Basic
	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "cliente") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="clienteText/nombreCliente")
	private String cliente;

	@Basic
	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "codigoDelegacion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="delegacionText/nombreOficina")
	private String codigoDelegacion;

	@Basic
	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Pruebas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "prueba") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="materialText/nombreMaterial")
	private String prueba;

	@Basic
	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="sectorText/nombreSector")
	private String sector;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "sociedad") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="organizacionText/nombreOrganizacion")
	private String sociedad;

	/*
	 * Associations
	 **********************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="cliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

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
	@JoinColumn(name="sociedad", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="prueba", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private MaterialesVentaText materialText;

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCodigoDelegacion() {
		return codigoDelegacion;
	}

	public void setCodigoDelegacion(String codigoDelegacion) {
		this.codigoDelegacion = codigoDelegacion;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}

	public void setClienteText(ClientesText clienteText) {
		this.clienteText = clienteText;
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

	public MaterialesVentaText getMaterialText() {
		return EntityUtil.getOrNull(() -> this.materialText, MaterialesVentaText::getNombreMaterial);
	}

	public void setMaterialText(MaterialesVentaText materialText) {
		this.materialText = materialText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)  return true;
		if (getClass() != obj.getClass())  return false;
		ConversionSociedad other = (ConversionSociedad) obj;
		return  Objects.equals(this.cliente, other.cliente) &&
				Objects.equals(this.codigoDelegacion, other.codigoDelegacion) &&
				Objects.equals(this.prueba, other.prueba) &&
				Objects.equals(this.sector, other.sector) &&
				Objects.equals(this.sociedad, other.sociedad);
	}

}

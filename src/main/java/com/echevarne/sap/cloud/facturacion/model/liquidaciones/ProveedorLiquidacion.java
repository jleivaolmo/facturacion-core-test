package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.ImpuestosText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesCompraText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.ProveedoresText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ProveedorLiquidacion", indexes = {
		@Index(name = "IDX_ProveedorLiquidacion", columnList = "organizacionVentas, delegacion, sector, fechaInicio, fechaFin", unique = true) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ProveedorLiquidacion extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4211736166237348463L;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String organizacionVentas;
	
	@Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacion") }, ValueListParameterDisplayOnly = {
                            @ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "oficinaText/nombreOficina")
    private String delegacion;
	
	@Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector") }, ValueListParameterDisplayOnly = {
                            @ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sectorText/nombreSector")
    private String sector;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Proveedores, Label = "Proveedor", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoProveedor", LocalDataProperty = "proveedor") }, ValueListParameterDisplayOnly = {
                            @ValueListParameterDisplayOnly(ValueListProperty = "nombreProveedor") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "proveedorText/nombreProveedor")
	private String proveedor;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Impuestos, Label = "Indicador IVA", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "indicadorIVA") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "impuestosText/nombre")
	private String indicadorIVA;
	
	@Basic
	private String conceptoGasto;
	
	@Basic
	private boolean facturaUnica;
	
	@Basic
	private String grupoArticulos;
	
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesCompra, Label = "Material", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "materialCompra") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "materialText/nombreMaterial")
	private String materialCompra;
	
	@Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaInicio;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaFin;
    
    @Basic
	private Boolean pdf_necesario;
    
    @Basic
   	private Boolean detalle_necesario;
    
    @Basic
   	private String psw_encriptacion;
    
    @Basic
   	private boolean aplica_liq_numpruebas;
    
    /*
	 * Asociaciones
	 *
	 ********************************************/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedorLiquidacion")
	@JsonManagedReference
	@ToString.Exclude
	private Set<ProvLiqEmailDestinatario> provLiqEmailDestinatario = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedorLiquidacion")
	@JsonManagedReference
	@ToString.Exclude
	private Set<ProvLiqRemitenteProfesional> provLiqRemitenteProfesional = new HashSet<>();
    
	
	/*
     * Associations Texts
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="organizacionVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText oficinaText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sector", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="indicadorIVA", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ImpuestosText impuestosText;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="proveedor", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProveedoresText proveedorText;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="materialCompra", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private MaterialesCompraText materialText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProveedorLiquidacion other = (ProveedorLiquidacion) obj;
		if (organizacionVentas == null) {
			if (other.organizacionVentas != null)
				return false;
		} else if (!organizacionVentas.equals(other.organizacionVentas))
			return false;
		if (delegacion == null) {
			if (other.delegacion != null)
				return false;
		} else if (!delegacion.equals(other.delegacion))
			return false;
		if (facturaUnica != other.facturaUnica)
			return false;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!fechaFin.equals(other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (grupoArticulos == null) {
			if (other.grupoArticulos != null)
				return false;
		} else if (!grupoArticulos.equals(other.grupoArticulos))
			return false;
		if (indicadorIVA == null) {
			if (other.indicadorIVA != null)
				return false;
		} else if (!indicadorIVA.equals(other.indicadorIVA))
			return false;
		if (materialCompra == null) {
			if (other.materialCompra != null)
				return false;
		} else if (!materialCompra.equals(other.materialCompra))
			return false;
		if (proveedor == null) {
			if (other.proveedor != null)
				return false;
		} else if (!proveedor.equals(other.proveedor))
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		if (conceptoGasto == null) {
			if (other.conceptoGasto != null)
				return false;
		} else if (!conceptoGasto.equals(other.conceptoGasto))
			return false;
		return true;
	}
	
	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}
	
	public OficinaVentaText getOficinaText() {
		return EntityUtil.getOrNull(() -> this.oficinaText, OficinaVentaText::getNombreOficina);
	}
	
	public SectorVentaText getSectorText() {
        return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
    }
	
	public ImpuestosText getImpuestosText() {
		return EntityUtil.getOrNull(() -> this.impuestosText, ImpuestosText::getNombre);
	}
	
	public ProveedoresText getProveedoresText() {
		return EntityUtil.getOrNull(() -> this.proveedorText, ProveedoresText::getNombreProveedor);
	}
	
	public MaterialesCompraText getMaterialText() {
		return EntityUtil.getOrNull(() -> this.materialText, MaterialesCompraText::getNombreMaterial);
	}
}

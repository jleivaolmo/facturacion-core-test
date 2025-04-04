package com.echevarne.sap.cloud.facturacion.model;

import java.util.Calendar;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.texts.CanalDistribucionText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@Table(name = "T_AgrParametrosFacturacion")
public class AgrParametrosFacturacion extends BasicEntity {

	private static final long serialVersionUID = 8551866943319330560L;

	@Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaInicio;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaFin;

    @Priorizable
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
    @Column(length = 4)
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="organizacionText/nombreOrganizacion")
    private String organizacionVentas;

    @Column(length = 4)
    private String oficinaVentas;

    @Column(length = 2)
    @Priorizable
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4DistributionChannel, Label = "Canal de distribución", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCanal", LocalDataProperty = "canalDistribucion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCanal") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="canalDistribucionText/nombreCanal")
    private String canalDistribucion;

    @Priorizable
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
    @Column(length = 2)
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="sectorText/nombreSector")
    private String sectorVentas;

    @Column(length = 10)
    private String codigoSolicitante;

    @Column(length = 10)
    private String codigoCompania;

    @Column(length = 10)
    private String codigoRemitente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed,  text="tipoPeticionText/nombreTipoPeticion")
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
    @Priorizable
    private int tipoPeticion;

    @Basic
    private boolean porPedido;

    @Basic
    private boolean facturacionInterna;

    @Basic
    private boolean conBloqueo;

    @Column(length = 5)
    private String claseFactura;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaFactura;

    @Basic
    private boolean incluirCapitativos;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="organizacionVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="canalDistribucion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private CanalDistribucionText canalDistribucionText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="sectorVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoPeticion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgrParametrosFacturacion other = (AgrParametrosFacturacion) obj;
		if (canalDistribucion == null) {
			if (other.canalDistribucion != null)
				return false;
		} else if (!canalDistribucion.equals(other.canalDistribucion))
			return false;
		if (claseFactura == null) {
			if (other.claseFactura != null)
				return false;
		} else if (!claseFactura.equals(other.claseFactura))
			return false;
		if (codigoCompania == null) {
			if (other.codigoCompania != null)
				return false;
		} else if (!codigoCompania.equals(other.codigoCompania))
			return false;
		if (codigoRemitente == null) {
			if (other.codigoRemitente != null)
				return false;
		} else if (!codigoRemitente.equals(other.codigoRemitente))
			return false;
		if (codigoSolicitante == null) {
			if (other.codigoSolicitante != null)
				return false;
		} else if (!codigoSolicitante.equals(other.codigoSolicitante))
			return false;
		if (conBloqueo != other.conBloqueo)
			return false;
		if (facturacionInterna != other.facturacionInterna)
			return false;
		if (fechaFactura == null) {
			if (other.fechaFactura != null)
				return false;
		} else if (!fechaFactura.equals(other.fechaFactura))
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
		if (incluirCapitativos != other.incluirCapitativos)
			return false;
		if (oficinaVentas == null) {
			if (other.oficinaVentas != null)
				return false;
		} else if (!oficinaVentas.equals(other.oficinaVentas))
			return false;
		if (organizacionVentas == null) {
			if (other.organizacionVentas != null)
				return false;
		} else if (!organizacionVentas.equals(other.organizacionVentas))
			return false;
		if (porPedido != other.porPedido)
			return false;
		if (sectorVentas == null) {
			if (other.sectorVentas != null)
				return false;
		} else if (!sectorVentas.equals(other.sectorVentas))
			return false;
		if (tipoPeticion != other.tipoPeticion)
			return false;
		return true;
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public CanalDistribucionText getCanalDistribucionText() {
		return EntityUtil.getOrNull(() -> this.canalDistribucionText, CanalDistribucionText::getNombreCanal);
	}

	public SectorVentaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
	}

	public TipoPeticionText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
	}
}

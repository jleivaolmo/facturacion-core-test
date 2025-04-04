package com.echevarne.sap.cloud.facturacion.model.contratos;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.texts.CanalDistribucionText;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.PaisesText;
import com.echevarne.sap.cloud.facturacion.model.texts.RegionText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.TarifasText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoContratoText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterOut;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_CONTRATOCAPITATIVO_FULL")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class ContratoCapitativoFull {

    @Id
    @Basic
    private Long id;

	@Basic
    private String descripcionContrato;

    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed,  text="tipoContratoText/nombreTipoContrato")
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoContrato, Label = "Tipo de contrato", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoContrato", LocalDataProperty = "tipoContrato") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoContrato") }) })
    @Basic
    private String tipoContrato;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="clienteText/nombreCliente")
    private String codigoCliente;

    @Priorizable(priority = 2)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="organizacionText/nombreOrganizacion")
    private String organizacionVentas;

    @Basic
    @Priorizable(priority = 2)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4DistributionChannel, Label = "Canal de distribución", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCanal", LocalDataProperty = "canalDistribucion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCanal") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="canalDistribucionText/nombreCanal")
    private String canalDistribucion;

    @Priorizable(priority = 2)
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="sectorText/nombreSector")
    private String sectorVentas;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar validoDesde;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar validoHasta;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed,  text="tipoPeticionText/nombreTipoPeticion")
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
    @Priorizable(priority = 2)
    private int tipoPeticion;

    @Priorizable(priority = 2)
    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="delegacionOrigenText/nombreOficina")
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionOrigen") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
    private String delegacionOrigen;

    @Priorizable(priority = 2)
    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionEmisora") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="delegacionEmisoraText/nombreOficina")
    private String delegacionEmisora;

    @Priorizable(priority = 2)
    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Regiones, Label = "Provincia de la delegación", Parameters = {
			@ValueListParameter(
					ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRegion", LocalDataProperty = "provinciaDelegacion")
					},
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreRegion")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="regionDelegacionText/nombreRegion")
    private String provinciaDelegacion;

    @Priorizable(priority = 2)
    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Regiones, Label = "Provincia del remitente", Parameters = {
			@ValueListParameter(
					ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRegion", LocalDataProperty = "provinciaRemitente")
					},
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreRegion")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="regionRemitenteText/nombreRegion")
    private String provinciaRemitente;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Tarifas, Label = "Tarifa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoTarifa", LocalDataProperty = "codigoTarifa") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTarifa") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="tarifasText/nombreTarifa")
    private String codigoTarifa;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Paises, Label = "Pais", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoPais", LocalDataProperty = "codigoPais") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombrePais") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="paisesText/nombrePais")
    private String codigoPais;

    @Basic
    @Priorizable(priority = 2)
    private String codigoReferenciaCliente;

    @Basic
    private boolean envioPrefactura;

    @Column(precision = 16, scale = 2)
    @Sap(unit="monedaContrato")
    private BigDecimal importeContrato;

    @Column(precision = 5, scale = 2)
    @Sap(unit="monedaContrato")
    private BigDecimal importe2Peticion;

    @Column(precision = 5, scale = 2)
    @Sap(unit="monedaContrato")
    private BigDecimal importe3Peticion;

    @Column(precision = 5, scale = 2)
    @Sap(unit="monedaContrato")
    private BigDecimal importe4Peticion;

    @Column(precision = 5, scale = 2)
    @Sap(unit="monedaContrato")
    private BigDecimal importe5Peticion;

    @Basic
    private int dias;
    
    @Basic
    private int prioridad;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda del contrato", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "monedaContrato") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
    private String monedaContrato;

    @Basic
	private String textoFactura;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Interlocutores, Label = "Interlocutor comercial", Parameters = {
        @ValueListParameter(
            ValueListParameterOut = {
                @ValueListParameterOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoInterlocutor") },
            ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente")}
        )
    })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)
    private String codigoInterlocutor;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private String codigoPoliza;

    @Basic
    private int critically;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.EstadoEntidad, Label = "Estado de la regla", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
            @ValueListParameterInOut(ValueListProperty = "estado", LocalDataProperty = "estado") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed)
    private String estado;

    /*
     * Associations Texts
     *
     ********************************************/

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="delegacionOrigen", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionOrigenText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="delegacionEmisora", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionEmisoraText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="sectorVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

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
	@JoinColumn(name="codigoCliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoPeticion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoContrato", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoContratoText tipoContratoText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="provinciaDelegacion", referencedColumnName = "codigoRegion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RegionText regionDelegacionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="provinciaRemitente", referencedColumnName = "codigoRegion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RegionText regionRemitenteText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoTarifa", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TarifasText tarifasText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoPais", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private PaisesText paisesText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="monedaContrato", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

    public OficinaVentaText getDelegacionOrigenText() {
        return EntityUtil.getOrNull(() -> this.delegacionOrigenText, OficinaVentaText::getNombreOficina);
    }

    public OficinaVentaText getDelegacionEmisoraText() {
        return EntityUtil.getOrNull(() -> this.delegacionEmisoraText, OficinaVentaText::getNombreOficina);
    }

    public SectorVentaText getSectorText() {
        return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
    }

    public OrganizacionVentaText getOrganizacionText() {
        return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
    }

    public CanalDistribucionText getCanalDistribucionText() {
        return EntityUtil.getOrNull(() -> this.canalDistribucionText, CanalDistribucionText::getNombreCanal);
    }

    public ClientesText getClienteText() {
        return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
    }

    public TipoPeticionText getTipoPeticionText() {
        return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
    }

    public TipoContratoText getTipoContratoText() {
        return EntityUtil.getOrNull(() -> this.tipoContratoText, TipoContratoText::getNombreTipoContrato);
    }

    public RegionText getRegionDelegacionText() {
        return EntityUtil.getOrNull(() -> this.regionDelegacionText, RegionText::getNombreRegion);
    }

    public RegionText getRegionRemitenteText() {
        return EntityUtil.getOrNull(() -> this.regionRemitenteText, RegionText::getNombreRegion);
    }

    public TarifasText getTarifasText() {
        return EntityUtil.getOrNull(() -> this.tarifasText, TarifasText::getNombreTarifa);
    }

    public PaisesText getPaisesText() {
        return EntityUtil.getOrNull(() -> this.paisesText, PaisesText::getNombrePais);
    }

    public DivisasText getDivisasText() {
        return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
    }
}

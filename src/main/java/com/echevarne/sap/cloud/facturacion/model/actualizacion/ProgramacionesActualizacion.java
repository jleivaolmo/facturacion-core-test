package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.data.util.ProxyUtils;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.Periodicidad;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
import com.echevarne.sap.cloud.facturacion.model.texts.EstadoEjecucionText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesText;
import com.echevarne.sap.cloud.facturacion.model.texts.TarifasText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionStringText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@Table(name = "V_UPDATE_PROGRAMACIONESACTUALIZACION")
@Cacheable(false)
//JL: Esto se está usando para visualizar los jobs en la pantalla principal de la app
public class ProgramacionesActualizacion implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2857040156377085938L;
	/*
	 	Concatena los valores en caso de haber mas de un un valor)
	   	Con … a partir de 3 valores; * cuando no hayan indicado valor
	 */

	@Basic
    private Long IdPlanificacion;

    @Basic
    private Integer IdEjecucion;

    @Basic
    private String nombre;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.EstadoEjecucion, Label = "Estados de la ejecución", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "estado") }, ValueListParameterDisplayOnly = {
                    @ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, text = "estadoEjecucionText/nombreEstado", valueList = PropertyValueListEnum.Fixed)
    private int estado;

    @Column(name = "fecha_inicio_programacion", columnDefinition = "DATE")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar FechaInicioProgramacion;

    @Column(name = "hora_inicio_programacion", columnDefinition = "TIME")
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private java.sql.Time horaInicioProgramacion;

    @Column(name = "fecha_inicio", columnDefinition = "DATE")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar FechaInicio;

    @Column(name = "hora_inicio", columnDefinition = "TIME")
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private java.sql.Time horaInicio;

    @Column(name = "fecha_fin", columnDefinition = "DATE")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
    @Temporal(TemporalType.DATE)
    private Calendar fechaFin;

    @Column(name = "hora_fin", columnDefinition = "TIME")
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private java.sql.Time horaFin;

    @Basic
    @Id
    private Long ejecucion;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.Periodicidad, Label = "Periodicidad", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "idPeriodicidad") },
                ValueListParameterDisplayOnly = {
                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text="nombrePeriodicidad", filterable = true)
    private Long idPeriodicidad;

    @Basic
    private String nombrePeriodicidad;

    @JoinColumn(name = "fk_periodicidad")
    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private Periodicidad periodicidad;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") },
                    ValueListParameterDisplayOnly = {
                            @ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed,  text="organizacionText/nombreOrganizacion", filterable = true)
    private String organizacionVentas;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegaciones", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "oficinaVentas") }, ValueListParameterDisplayOnly = {
                    @ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionText/nombreOficina")
    private String oficinaVentas;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") },
                    ValueListParameterDisplayOnly = {
                            @ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="clienteText/nombreCliente", filterable = true)
    private String codigoCliente;

    @Column(columnDefinition = "DATE")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.None)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar paramFechaPeticionDesde;

    @Column(columnDefinition = "DATE")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.None)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar paramFechaPeticionHasta;

    @Column(columnDefinition = "DATE")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.None)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar paramFechaCreacionDesde;

    @Column(columnDefinition = "DATE")
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.None)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar paramFechaCreacionHasta;

    @OneToMany
    @JoinColumn(referencedColumnName = "ejecucion", name = "fk_ProcesoActualizacionEjecucion", insertable = false, updatable = false)
    @JsonManagedReference
    @Builder.Default
    @ToString.Exclude
    private Set<ProcesoActualizacionLog> logs = new HashSet<>();

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None, valueList = PropertyValueListEnum.None, filterable = true)
    private String codigosPeticion;

    @Basic
    private boolean inicioInmediato;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.Estados, Label = "Estado de la solicitud", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "codigosEstado") }, ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed)
    private String codigosEstado;
    
    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "codigoCompania") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "companiaText/nombreCompania", filterable = true)
    private String codigoCompania;
    
    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Remitente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "codigoRemitente") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteText/nombreRemitente", filterable = true)
	private String codigoRemitente;
    
    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text = "tipoPeticionText/nombreTipoPeticion", filterable = true)
	private String tipoPeticion;
    
    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Tarifas, Label = "Tarifa", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoTarifa", LocalDataProperty = "codigoTarifa") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTarifa") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "tarifasText/nombreTarifa", filterable = true)
	private String codigoTarifa;
    
    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Material", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "material") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text="pruebaText/nombreMaterial")
    private String material;
    

    /*
    *  Association texts
    */

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoCliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="oficinaVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="organizacionVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name="estado", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EstadoEjecucionText estadoEjecucionText;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCompania", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoRemitente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesText remitenteText;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "tipoPeticion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionStringText tipoPeticionText;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoTarifa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TarifasText tarifasText;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
   	@JoinColumn(name="material", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
   	private MaterialesVentaText pruebaText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ProxyUtils.getUserClass(this) != ProxyUtils.getUserClass(o))
            return false;
        ProgramacionesActualizacion that = (ProgramacionesActualizacion) o;
        return IdPlanificacion != null && Objects.equals(IdPlanificacion, that.IdPlanificacion);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public ClientesText getClienteText() {
        return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
    }

    public OficinaVentaText getDelegacionText() {
        return EntityUtil.getOrNull(() -> this.delegacionText, OficinaVentaText::getNombreOficina);
    }

    public OrganizacionVentaText getOrganizacionText() {
        return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
    }

    public EstadoEjecucionText getEstadoEjecucionText() {
        return EntityUtil.getOrNull(() -> this.estadoEjecucionText, EstadoEjecucionText::getNombreEstado);
    }
    
    public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}
    
    public RemitentesText getRemitenteText() {
		return EntityUtil.getOrNull(() -> this.remitenteText, RemitentesText::getNombreRemitente);
	}
    
    public TipoPeticionStringText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionStringText::getNombreTipoPeticion);
	}
    
    public TarifasText getTarifasText() {
		return EntityUtil.getOrNull(() -> this.tarifasText, TarifasText::getNombreTarifa);
	}
    
    public MaterialesVentaText getPruebaText() {
		return EntityUtil.getOrNull(() -> this.pruebaText, MaterialesVentaText::getNombreMaterial);
	}
}

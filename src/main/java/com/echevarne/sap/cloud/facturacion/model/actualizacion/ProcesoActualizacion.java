package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.sql.Time;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
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
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.util.FioriDateDeserializer;
import com.echevarne.sap.cloud.facturacion.util.FioriTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Entity
@AllArgsConstructor
@Table(name = "T_ProcesoActualizacion")
@ToString
@Getter
@Setter
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class ProcesoActualizacion extends BasicEntity {

    private static final long serialVersionUID = -602103245782345569L;

    @Column(name = "fecha_inicio")
    @Sap(displayFormat = DisplayFormatEnum.Date)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaInicio;

    @Column(name = "hora_inicio", columnDefinition = "TIME")
    @JsonDeserialize(converter = FioriTimeDeserializer.class)
    private java.sql.Time horaInicio;

    @ValueList(CollectionPath = ValueListEntitiesEnum.Periodicidad, Label = "Periodicidad", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "idPeriodicidad") },
                ValueListParameterDisplayOnly = {
                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, filterable = true)
    private Long idPeriodicidad;

    @Basic
    private boolean ejecucionInmediata;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
	@JsonManagedReference
	private Set<ProcesoActualizacionClientes> clientes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
	@JsonManagedReference
	private Set<ProcesoActualizacionOrgVentas> orgVentas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionOficinasVentas> oficinasVentas = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodPeticion> codigosPeticion = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodEstado> codigosEstado = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodGrupoEstado> codigosGrupoEstado = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionTiposPeticion> tiposPeticion = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCompanias> companias = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionRemitentes> remitentes = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionConceptosFact> conceptosFact = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodigosCanal> codigosCanal = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodigosSector> codigosSector = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionTiposContrato> tiposContrato = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionNumerosContrato> numerosContrato = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodPeticionLims> codPeticionLims = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodAlerta> codAlerta = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionAreasVentas> areasVentas = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCargosPeticion> cargosPeticion = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionRefCliente> refCliente = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodRefExterno> codRefExterno = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodHistClinica> codHistClinica = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodDivisa> codDivisa = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionMotivosEstado> motivosEstado = new HashSet<>();
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodPaciente> codPaciente = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionProvRemitente> provRemitente = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodTarifa> codTarifa = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionTiposCotizacion> tiposCotizacion = new HashSet<>();
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodUsrExterno> codUsrExterno = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionMaterial> material = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionFacturas> facturas = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionLotes> lotes = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionLugarMuestreo> lugarMuestreo = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionNombreAnimal> nombreAnimal = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionCodigoPropietario> codigoPropietario = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionNombreDescr> nombreDescr = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionPedidoVenta> pedidoVenta = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso", orphanRemoval = true)
   	@JsonManagedReference
   	private Set<ProcesoActualizacionPrefactura> prefactura = new HashSet<>();
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaPeticionDesde;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaPeticionHasta;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaCreacionDesde;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaCreacionHasta;
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaActualizacionDesde;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaActualizacionHasta;
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
	private Calendar fechaRecepcionMuestraIni;
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
	private Calendar fechaRecepcionMuestraFin;
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
	private Calendar fechaTomaMuestraIni;
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
	private Calendar fechaTomaMuestraFin;
    
    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaFacturaDesde;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval, filterable = true)
    @JsonDeserialize(converter = FioriDateDeserializer.class)
    private Calendar fechaFacturaHasta;
    
    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, filterable = true)
    Boolean esMixta;
    
    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, filterable = true)
	Boolean esPrivada;
    
    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, filterable = true)
    Boolean esMuestraRemitida;
   
    @Basic
    private String nombre;

	@OneToMany(mappedBy = "procesoActualizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcesoActualizacionEjecucion> listaEjecuciones = new ArrayList<>();

    public ProcesoActualizacion() {
        super();
    }

    public ProcesoActualizacion addEjecucion(ProcesoActualizacionEjecucion ejecucion) {
        if (this.listaEjecuciones == null )
            this.listaEjecuciones = new ArrayList<>();
        this.listaEjecuciones.add(ejecucion);
        ejecucion.setProcesoActualizacion(this);
        return this;
    }

    public ProcesoActualizacion removeEjecucion(ProcesoActualizacionEjecucion ejecucion) {
        if (this.listaEjecuciones == null )
            this.listaEjecuciones = new ArrayList<>();
        this.listaEjecuciones.remove(ejecucion);
        ejecucion.setProcesoActualizacion(null);
        return this;
    }
    
    /*
     * Métodos para obtener listas de valores
     */
	public Set<String> getListaAreasVentas() {
		Set<String> response = new HashSet<>();
		this.areasVentas.forEach(i -> {
			response.add(i.getAreaVentas());
		});
		return response;
	}
	
	public Set<String> getListaCargosPeticion() {
		Set<String> response = new HashSet<>();
		this.cargosPeticion.forEach(i -> {
			response.add(i.getCargoPeticion());
		});
		return response;
	}
	
	public Set<String> getListaClientes() {
		Set<String> response = new HashSet<>();
		this.clientes.forEach(i -> {
			response.add(i.getCliente());
		});
		return response;
	}
	
	public Set<String> getListaCodsAlerta() {
		Set<String> response = new HashSet<>();
		this.codAlerta.forEach(i -> {
			response.add(i.getCodAlerta());
		});
		return response;
	}
	
	public Set<String> getListaCodsDivisa() {
		Set<String> response = new HashSet<>();
		this.codDivisa.forEach(i -> {
			response.add(i.getCodDivisa());
		});
		return response;
	}
	
	public Set<String> getListaCodsHistClinica() {
		Set<String> response = new HashSet<>();
		this.codHistClinica.forEach(i -> {
			response.add(i.getCodHistClinica());
		});
		return response;
	}
	
	public Set<String> getListaCodigosCanal() {
		Set<String> response = new HashSet<>();
		this.codigosCanal.forEach(i -> {
			response.add(i.getCodigoCanal());
		});
		return response;
	}
	
	public Set<String> getListaCodigosEstado() {
		Set<String> response = new HashSet<>();
		this.codigosEstado.forEach(i -> {
			response.add(i.getCodEstado());
		});
		return response;
	}
	
	public Set<String> getListaCodigosGrupoEstado() {
		Set<String> response = new HashSet<>();
		this.codigosGrupoEstado.forEach(i -> {
			response.add(i.getGrupoEstado());
		});
		return response;
	}
	
	public Set<String> getListaCodigosPeticion() {
		Set<String> response = new HashSet<>();
		this.codigosPeticion.forEach(i -> {
			response.add(i.getCodPeticion());
		});
		return response;
	}
	
	public Set<String> getListaCodigosSector() {
		Set<String> response = new HashSet<>();
		this.codigosSector.forEach(i -> {
			response.add(i.getCodigoSector());
		});
		return response;
	}
	
	public Set<String> getListaCodigosPaciente() {
		Set<String> response = new HashSet<>();
		this.codPaciente.forEach(i -> {
			response.add(i.getCodPaciente());
		});
		return response;
	}
	
	public Set<String> getListaCodPeticionLims() {
		Set<String> response = new HashSet<>();
		this.codPeticionLims.forEach(i -> {
			response.add(i.getCodPeticionLims());
		});
		return response;
	}
	
	public Set<String> getListaCodRefExterno() {
		Set<String> response = new HashSet<>();
		this.codRefExterno.forEach(i -> {
			response.add(i.getCodRefExterno());
		});
		return response;
	}
	
	public Set<String> getListaCodTarifa() {
		Set<String> response = new HashSet<>();
		this.codTarifa.forEach(i -> {
			response.add(i.getCodTarifa());
		});
		return response;
	}
	
	public Set<String> getListaCodUsrExterno() {
		Set<String> response = new HashSet<>();
		this.codUsrExterno.forEach(i -> {
			response.add(i.getCodUsrExterno());
		});
		return response;
	}
	
	public Set<String> getListaCompanias() {
		Set<String> response = new HashSet<>();
		this.companias.forEach(i -> {
			response.add(i.getCompania());
		});
		return response;
	}
	
	public Set<String> getListaConceptosFact() {
		Set<String> response = new HashSet<>();
		this.conceptosFact.forEach(i -> {
			response.add(i.getConceptoFact());
		});
		return response;
	}
	
	public Set<String> getListaOrgVentas() {
		Set<String> response = new HashSet<>();
		this.orgVentas.forEach(i -> {
			response.add(i.getOrgVentas());
		});
		return response;
	}
	
	public Set<String> getListaMotivosEstado() {
		Set<String> response = new HashSet<>();
		this.motivosEstado.forEach(i -> {
			response.add(i.getMotivoEstado());
		});
		return response;
	}
	
	public Set<String> getListaNumerosContrato() {
		Set<String> response = new HashSet<>();
		this.numerosContrato.forEach(i -> {
			response.add(i.getNumContrato());
		});
		return response;
	}
	
	public Set<String> getListaOficinasVentas() {
		Set<String> response = new HashSet<>();
		this.oficinasVentas.forEach(i -> {
			response.add(i.getOficinasVentas());
		});
		return response;
	}
	
	public Set<String> getListaProvRemitente() {
		Set<String> response = new HashSet<>();
		this.provRemitente.forEach(i -> {
			response.add(i.getProvRemitente());
		});
		return response;
	}
	
	public Set<String> getListaRefCliente() {
		Set<String> response = new HashSet<>();
		this.refCliente.forEach(i -> {
			response.add(i.getRefCliente());
		});
		return response;
	}
	
	public Set<String> getListaRemitentes() {
		Set<String> response = new HashSet<>();
		this.remitentes.forEach(i -> {
			response.add(i.getRemitente());
		});
		return response;
	}
	
	public Set<String> getListaTiposContrato() {
		Set<String> response = new HashSet<>();
		this.tiposContrato.forEach(i -> {
			response.add(i.getTipoContrato());
		});
		return response;
	}
	
	public Set<String> getListaTiposCotizacion() {
		Set<String> response = new HashSet<>();
		this.tiposCotizacion.forEach(i -> {
			response.add(i.getTipoCotizacion());
		});
		return response;
	}
	
	public Set<Integer> getListaTiposPeticion() {
		Set<Integer> response = new HashSet<>();
		this.tiposPeticion.forEach(i -> {
			response.add(i.getTipoPeticion());
		});
		return response;
	}
	
	public Set<String> getListaMaterial() {
		Set<String> response = new HashSet<>();
		this.material.forEach(i -> {
			response.add(i.getMaterial());
		});
		return response;
	}
	
	public Set<String> getListaFacturas() {
		Set<String> response = new HashSet<>();
		this.facturas.forEach(i -> {
			response.add(i.getFactura());
		});
		return response;
	}
	
	public Set<String> getListaLotes() {
		Set<String> response = new HashSet<>();
		this.lotes.forEach(i -> {
			response.add(i.getLote());
		});
		return response;
	}
	
	public Set<String> getListaLugarMuestreo() {
		Set<String> response = new HashSet<>();
		this.lugarMuestreo.forEach(i -> {
			response.add(i.getLugarMuestreo());
		});
		return response;
	}
	
	public Set<String> getListaNombreAnimal() {
		Set<String> response = new HashSet<>();
		this.nombreAnimal.forEach(i -> {
			response.add(i.getNombreAnimal());
		});
		return response;
	}
	
	public Set<String> getListaCodigoPropietario() {
		Set<String> response = new HashSet<>();
		this.codigoPropietario.forEach(i -> {
			response.add(i.getCodigoPropietario());
		});
		return response;
	}
	
	public Set<String> getListaNombreDescr() {
		Set<String> response = new HashSet<>();
		this.nombreDescr.forEach(i -> {
			response.add(i.getNombreDescr());
		});
		return response;
	}
	
	public Set<String> getListaPedidoVenta() {
		Set<String> response = new HashSet<>();
		this.pedidoVenta.forEach(i -> {
			response.add(i.getPedidoVenta());
		});
		return response;
	}
	
	public Set<String> getListaPrefactura() {
		Set<String> response = new HashSet<>();
		this.prefactura.forEach(i -> {
			response.add(i.getPrefactura());
		});
		return response;
	}

	/*
	 * 
	 */

    @Transient
    @JsonIgnore
    public Instant getInstantInicioProceso() {
        Time horaInicio = getHoraInicio();
        if (horaInicio == null) {
            this.setHoraInicio(Time.valueOf("00:00:00"));
        }
        this.fechaInicio.set(Calendar.HOUR_OF_DAY, 0);
        var dateAux = this.fechaInicio.toInstant().plusSeconds(getHoraInicio().toLocalTime().toSecondOfDay());
        log.info("Fecha sin convertir: " + dateAux);
        var hourDiff = -DateUtils.getHourDiff();
        var response = dateAux.plus(hourDiff, ChronoUnit.HOURS);
        log.info("HourDiff: " + hourDiff +" Fecha tras conversion: " + response);
        return response;
    }
    
    

	@Transient
	@JsonIgnore
	public boolean necesitaNuevaEjecucion() {
		return !isEjecucionInmediata();
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProcesoActualizacion other = (ProcesoActualizacion) obj;
		return Objects.equals(fechaInicio, other.fechaInicio)
				&& Objects.equals(horaInicio, other.horaInicio)
				&& Objects.equals(nombre, other.nombre);
	}

}

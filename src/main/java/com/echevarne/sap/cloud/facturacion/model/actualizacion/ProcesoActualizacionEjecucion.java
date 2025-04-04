package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.time.Duration;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NaturalId;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.CronExecution;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(
        name = "T_ProcesoActualizacionEjecucion",
        indexes = {
            @Index(name = "IDX_byEstado", columnList = "estado", unique = false),
        }
)
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcesoActualizacionEjecucion extends BasicEntity implements CronExecution {

	private static final long serialVersionUID = 1L;
	public static final Integer ESTADO_PLANIFICADO = 1;
    public static final Integer ESTADO_ENCURSO = 2;
    public static final Integer ESTADO_FINALIZADO = 3;
    public static final Integer ESTADO_ERRONEO = 4;

    @Column(unique = true)
    @NaturalId
    private String processId;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_ProcesoActualizacion", nullable = true)
    @JsonBackReference
    @ToString.Exclude
    ProcesoActualizacion procesoActualizacion;

    @Basic
    private int totalPeticiones;

    @Basic
    private int numProcesadas;

    @Basic
    private int numErrores;

    @Basic
    private Integer numEjecucion;

    @Column(name = "fecha_inicio")
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar fechaInicio;

    @Column(name = "hora_inicio")
    private java.sql.Time horaInicio;
    
    @Column(name = "hora_fin")
    private java.sql.Time horaFin;

    @Column(name = "inicio_proceso", nullable = true)
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar inicioProceso;

    @Column(name = "fin_proceso", nullable = true)
    @Sap(displayFormat = DisplayFormatEnum.Date)
    private Calendar finProceso;

    @Basic
    private Integer estado;
    
    @Basic
	private String uuidInstance;
    
    @OneToMany(
            mappedBy = "procesoActualizacionEjecucion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<ProcesoActualizacionLog> logList = new HashSet<>();

    @Transient
    @JsonIgnore
    private ScheduledFuture<?> cancellator;

    @Transient
    @JsonIgnore
    public void cancel() {
        if (cancellator !=null && !cancellator.isCancelled())
        cancellator.cancel(true);
    }

    public ProcesoActualizacionEjecucion() {
        super();
    }

    public ProcesoActualizacionEjecucion(String processId) {
        super();
        this.processId = processId;
    }

    @Transient
    @JsonProperty("tiempoEjecucion")
    public Long getTiempoejecucion() {
        if (!haTerminado()) return 0L;
        return Duration.between(inicioProceso.toInstant(),finProceso.toInstant()).getSeconds();
    }

    @Transient
    public boolean haTerminado() {
        return this.getFinProceso()!=null;
    }

    public ProcesoActualizacionEjecucion addLog(ProcesoActualizacionLog log) {
        if (this.logList == null)
            this.logList = new HashSet<>();
        logList.add(log);
        log.setProcesoActualizacionEjecucion(this);
        return this;
    }

    public ProcesoActualizacionEjecucion removeLog(ProcesoActualizacionLog log) {
        this.logList.remove(log);
        log.setProcesoActualizacionEjecucion(null);
        return this;
    }

    @Override
    public boolean onEquals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ProcesoActualizacionEjecucion)) return false;
        if (getProcessId() ==null) return
                ((ProcesoActualizacionEjecucion) o).getProcesoActualizacion().equals(this.getProcesoActualizacion()) &&
                        this.numEjecucion ==  ((ProcesoActualizacionEjecucion) o).getNumEjecucion();
        return getProcessId().equals(((ProcesoActualizacionEjecucion) o).getProcessId());
    }

}

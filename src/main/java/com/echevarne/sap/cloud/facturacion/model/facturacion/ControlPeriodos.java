package com.echevarne.sap.cloud.facturacion.model.facturacion;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = ConstEntities.ENTIDAD_FACTLOGCONTROL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class ControlPeriodos extends BasicEntity {

    /**
	 *
	 */
	private static final long serialVersionUID = -868789476213051190L;
	public static final Integer ESTADO_COMENZADO = 0;
    public static final Integer ESTADO_PROCESANDO = 1;
    public static final Integer ESTADO_PEDIDOS_CREADOS = 5;
    public static final Integer ESTADO_ERROR = 6;
    public static final Integer ESTADO_FINALIZADO = 7;
    public static final Integer ESTADO_PREFACTURADO = 8;
    public static final Integer ESTADO_ESPERA_RESPUESTA_S4 = 9;
    public static final Integer ESTADO_PLANIFICADO = 10;

    /*
     * Campos
     *
     ********************************************/
    @Basic
    private int estadoActual = ESTADO_COMENZADO;
    @Basic
    private Timestamp fechaInicio;
    @Basic
    private Timestamp fechaFin;
    @Basic
    private boolean esCancelacion = false;

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JsonBackReference
    @JoinColumn(name = "fk_PeriodoFacturado", nullable = false)
    private PeriodosFacturados periodo;

    @OneToMany(cascade = ALL, mappedBy = "control")
	@JsonManagedReference
	private Set<ControlPeriodosTipologia> controlPeriodosTipologia = new HashSet<>();

    @Override
    public boolean onEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlPeriodos that = (ControlPeriodos) o;
        if (estadoActual != that.estadoActual) return false;
        if (!Objects.equals(fechaInicio, that.fechaInicio)) return false;
        return Objects.equals(fechaFin, that.fechaFin);
    }
}

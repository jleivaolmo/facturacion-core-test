package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.EstadoActualizacionLogText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "T_ProcesoActualizacionLog")
@ToString
@Builder
public class ProcesoActualizacionLog extends BasicEntity implements Serializable {

    private static final long serialVersionUID = -602103245791255567L;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_ProcesoActualizacionEjecucion")
    @JsonBackReference
    @ToString.Exclude
    private ProcesoActualizacionEjecucion procesoActualizacionEjecucion;

    @Basic
    private String codigoPeticion;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.EstadoProcesoActualizacionLog, Label = "Estados de actualización log", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "estadoLog") }, ValueListParameterDisplayOnly = {
                    @ValueListParameterDisplayOnly(ValueListProperty = "descripcion") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text="estadoActualizacionLogText/descripcion", filterable = true)
    private int estadoLog;
    
    @Basic
    private String idProceso;

    @Lob
    @Type(type="org.hibernate.type.MaterializedClobType")    
    private String errorMessage;
    
    /*
     *  Association texts
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="estadoLog", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EstadoActualizacionLogText estadoActualizacionLogText;

    public ProcesoActualizacionLog() {
        super();
    }

    @Override
    public boolean onEquals(Object o) {
        if (o == this) return true;
        if (! (o instanceof ProcesoActualizacionLog)) return false;
        if (((ProcesoActualizacionLog) o).getCodigoPeticion() == null && this.getCodigoPeticion()!=null) return false;
        if (this.getCodigoPeticion() == null) {
            if (this.getProcesoActualizacionEjecucion() == null) {
                return ((ProcesoActualizacionLog) o).getProcesoActualizacionEjecucion() == null;
            } else {
                return this.getProcesoActualizacionEjecucion().equals(((ProcesoActualizacionLog) o).getProcesoActualizacionEjecucion());
            }
        }
        ProcesoActualizacionLog objFmt = (ProcesoActualizacionLog) o;
        return objFmt.getCodigoPeticion().equals(this.getCodigoPeticion()) &&
                objFmt.getProcesoActualizacionEjecucion().getProcessId().equals(this.getProcesoActualizacionEjecucion().getProcessId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    public EstadoActualizacionLogText getEstadoActualizacionLogText() {
		return EntityUtil.getOrNull(() -> this.estadoActualizacionLogText, EstadoActualizacionLogText::getDescripcion);
	}
}


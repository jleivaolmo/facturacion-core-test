package com.echevarne.sap.cloud.facturacion.model.parametrizacion;

import com.echevarne.sap.cloud.facturacion.mappers.EntityIdResolver;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_ParamPeriodicidad")
@Getter
@Setter
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope=Periodicidad.class)
public class Periodicidad extends BasicParamEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1330131753448304744L;
	
	public static final String DIARIA = "Diaria";
    public static final String SEMANAL = "Semanal";
    public static final String MENSUAL = "Mensual";
    public static final String NINGUNA = "Ninguna";

    @Column(unique = true)
    private String nombre;

    @Basic
    private Integer numMinutos;

    @Override
    public boolean onEquals(Object o) {
        if (this==o) return true;
        if (! (o instanceof Periodicidad)) return false;

        return ((Periodicidad) o).getNombre().equals(this.getNombre()) ;
    }
}

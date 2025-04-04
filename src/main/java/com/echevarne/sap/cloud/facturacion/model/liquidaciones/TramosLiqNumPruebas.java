package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_TramosLiqNumPruebas")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class TramosLiqNumPruebas extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1554652311722574015L;

	@Basic
	private String nombre;
	
	@Basic
	private int inicioTramo;
	
	@Basic
	private Integer finTramo;
	
	@Basic
	private int porcentaje;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TramosLiqNumPruebas other = (TramosLiqNumPruebas) obj;
		return finTramo == other.finTramo && inicioTramo == other.inicioTramo && Objects.equals(nombre, other.nombre) && porcentaje == other.porcentaje;
	}
}

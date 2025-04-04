package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_PropuestaLiquidacion", indexes = {
		@Index(name = "IDX_PropuestaLiquidacion", columnList = "tipoLiquidacion, proveedor, grupoProveedor, sociedad, "
				+ "fechaInicioPeriodo, fechaFinPeriodo", unique = false) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class PropuestaLiquidacion extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1834255190868073382L;

	/* 1: remitente, 2: profesional */
	@Basic
	private int tipoLiquidacion;

	@Column(length = 4)
	private String sociedad;

	@Basic
	private String proveedor;

	@Basic
	private String grupoProveedor;

	@Basic
	private Calendar fechaInicioPeriodo;

	@Basic
	private Calendar fechaFinPeriodo;

	@Basic
	private Calendar fechaCreacion;

	@Basic
	private Calendar fechaFinalizacion;

	@Basic
	private String nombreProceso;

	@Basic
	private String codUsuario;

	@Basic
	private String nombreUsuario;

	@Basic
	private String uuidInstance;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "propuestaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<PropuestaLiquidacionPeticiones> peticiones = new HashSet<>();

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PropuestaLiquidacion other = (PropuestaLiquidacion) obj;
		return Objects.equals(fechaInicioPeriodo, other.fechaInicioPeriodo) && Objects.equals(fechaFinPeriodo, other.fechaFinPeriodo)
				&& Objects.equals(fechaCreacion, other.fechaCreacion) && Objects.equals(fechaFinalizacion, other.fechaFinalizacion)
				&& Objects.equals(proveedor, other.proveedor) && Objects.equals(grupoProveedor, other.grupoProveedor)
				&& Objects.equals(sociedad, other.sociedad) && tipoLiquidacion == other.tipoLiquidacion && Objects.equals(nombreProceso, other.nombreProceso)
				&& Objects.equals(codUsuario, other.codUsuario) && Objects.equals(nombreUsuario, other.nombreUsuario)
				&& Objects.equals(uuidInstance, other.uuidInstance);
	}

}

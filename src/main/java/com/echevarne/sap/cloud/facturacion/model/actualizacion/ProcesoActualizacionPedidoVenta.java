package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_ProcesoActualizacionPedidoVenta")
@AllArgsConstructor 
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ProcesoActualizacionPedidoVenta extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2596437995286240058L;
	@Basic
	private String pedidoVenta;
	
	public ProcesoActualizacionPedidoVenta(String pedidoVenta) {
		this.pedidoVenta = pedidoVenta;
	}
	
	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_proceso", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private ProcesoActualizacion proceso;
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProcesoActualizacionPedidoVenta other = (ProcesoActualizacionPedidoVenta) obj;
		return Objects.equals(pedidoVenta, other.pedidoVenta);
	}
}

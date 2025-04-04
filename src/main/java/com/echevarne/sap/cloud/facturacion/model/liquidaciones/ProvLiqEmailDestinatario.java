package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ProvLiqEmailDestinatario")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ProvLiqEmailDestinatario extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 96902196653919611L;

	@Basic
	private String emailDestinatario;
	
	@Basic
	private Integer secuencia;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_proveedorLiquidacion", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private ProveedorLiquidacion proveedorLiquidacion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProvLiqEmailDestinatario other = (ProvLiqEmailDestinatario) obj;
		return Objects.equals(emailDestinatario, other.emailDestinatario);
	}

}

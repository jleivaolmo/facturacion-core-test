package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadoLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_EstadoLiquidacionPeticion")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class EstadoLiquidacionPeticion extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5982297985686082859L;
	
	/* 1: remitente, 2: profesional */
	private int tipoLiquidacion; 
	
	@Column(precision = 16, scale = 2)
	private BigDecimal importeLiquidacion1;
	
	@Column(precision = 16, scale = 2)
	private BigDecimal importeLiquidacion2;
	
	@Column(precision = 16, scale = 2)
	private BigDecimal importeLiquidacion3;
	
	@Column(precision = 16, scale = 2)
	private BigDecimal importeLiquidacion4;
	
	@Column(precision = 16, scale = 2)
	private BigDecimal importeLiquidacion5;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_Estado", nullable = false)
    private MasDataEstadoLiquidacion estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	TrazabilidadSolicitud trazabilidad;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EstadoLiquidacionPeticion other = (EstadoLiquidacionPeticion) obj;
		return Objects.equals(estado, other.estado) && tipoLiquidacion == other.tipoLiquidacion
				&& Objects.equals(trazabilidad, other.trazabilidad);
	}

}

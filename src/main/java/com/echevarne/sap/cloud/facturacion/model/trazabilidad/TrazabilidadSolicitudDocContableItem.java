package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Getter
@Setter
@Table(name="T_TrazabilidadSolicitudDocContableItem")
public class TrazabilidadSolicitudDocContableItem extends BasicEntity {

	private static final long serialVersionUID = 4463199853089802945L;

	@Column(precision = 16, scale = 3, nullable = false)
	private BigDecimal importe;

	@Column(nullable=false)
	private String cuentaDebe;

	@Column(nullable=false)
	private String cuentaHaber;

	@Column
	private Long idMetodoPago;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_docContable", nullable = false)
	@JsonBackReference
	private TrazabilidadSolicitudDocContable docContable;


	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TrazabilidadSolicitudDocContableItem other = (TrazabilidadSolicitudDocContableItem) obj;
		return  Objects.equals(this.importe, other.importe) &&
				Objects.equals(this.idMetodoPago, other.idMetodoPago) &&
				Objects.equals(this.cuentaDebe, other.cuentaDebe) &&
				Objects.equals(this.cuentaHaber, other.cuentaHaber);
	}

}

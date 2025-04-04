package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import static javax.persistence.CascadeType.ALL;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Getter
@Setter
@Table(name="T_TrazabilidadSolicitudDocContable")
public class TrazabilidadSolicitudDocContable extends BasicEntity {

	public enum TipoOperacion {
		COBRO, ANTICIPO, DEPOSITO, ABONO, ABONO_CON_ANT, DEV_ANT, DEV_DEP, COBRO_CON_ANT, FACTURAR, RECTIFICAR
	}

	private static final long serialVersionUID = -7679663596861623947L;

	@Column(nullable=false)
	private TipoOperacion tipoOperacion;

	@Column(nullable=false)
	private String journalEntryID;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	TrazabilidadSolicitud trazabilidad;

	@OneToMany(cascade = ALL, mappedBy = "docContable")
	@JsonManagedReference
	private List<TrazabilidadSolicitudDocContableItem> items;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TrazabilidadSolicitudDocContable other = (TrazabilidadSolicitudDocContable) obj;
		return  Objects.equals(this.tipoOperacion, other.tipoOperacion) &&
				Objects.equals(this.journalEntryID, other.journalEntryID) &&
				Objects.equals(items, other.items);
	}

	public BigDecimal getSumImporte() {
		return this.getItems().stream().map(TrazabilidadSolicitudDocContableItem::getImporte).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
}

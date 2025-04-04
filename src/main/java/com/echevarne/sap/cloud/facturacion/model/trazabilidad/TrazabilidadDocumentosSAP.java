package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoDocumentoSAP;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_TrazabilidadDocumentosSAP")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadDocumentosSAP extends BasicEntity {

	private static final long serialVersionUID = -2125471160669916642L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	private Trazabilidad trazabilidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_tipoDocumento")
	@JsonBackReference
	private MasDataTipoDocumentoSAP tipoDocumento;

	@Basic
	private String valor;

	@Basic
	private String item;

	@Basic
	private String prueba;

	@Basic
	private String cliente;

	@Basic
	private String remitente;

	@Basic
	private String compania;

	@Basic
	private String oficinaVentas;

	@Basic
	private int tipoPeticion;

	@Basic
	private BigDecimal unidades;

	@Basic
	private BigDecimal precio_unitario_bruto;

	@Basic
	private BigDecimal precio_unitario_neto;

	@Basic
	private BigDecimal importe_total_bruto;

	@Basic
	private BigDecimal importe_total_neto;

	@Basic
	private String petLims;

	@Basic
	private String orgVentas;

	@Basic
	private BigDecimal importe_puntos;

	@Basic
	private Timestamp fecha;

	@Basic
	private String canal;

	@Basic
	private BigDecimal conditionRateValue;

	@Basic
	private BigDecimal descuentoZRDT;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(canal, cliente, compania, conditionRateValue, descuentoZRDT, fecha, importe_puntos, importe_total_bruto,
				importe_total_neto, item, oficinaVentas, orgVentas, petLims, precio_unitario_bruto, precio_unitario_neto, prueba, remitente, tipoDocumento,
				tipoPeticion, trazabilidad, unidades, valor);
		return result;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TrazabilidadDocumentosSAP other = (TrazabilidadDocumentosSAP) obj;
		return Objects.equals(canal, other.canal) && Objects.equals(cliente, other.cliente) && Objects.equals(compania, other.compania)
				&& Objects.equals(conditionRateValue, other.conditionRateValue) && Objects.equals(descuentoZRDT, other.descuentoZRDT)
				&& Objects.equals(fecha, other.fecha) && Objects.equals(importe_puntos, other.importe_puntos)
				&& Objects.equals(importe_total_bruto, other.importe_total_bruto) && Objects.equals(importe_total_neto, other.importe_total_neto)
				&& Objects.equals(item, other.item) && Objects.equals(oficinaVentas, other.oficinaVentas) && Objects.equals(orgVentas, other.orgVentas)
				&& Objects.equals(petLims, other.petLims) && Objects.equals(precio_unitario_bruto, other.precio_unitario_bruto)
				&& Objects.equals(precio_unitario_neto, other.precio_unitario_neto) && Objects.equals(prueba, other.prueba)
				&& Objects.equals(remitente, other.remitente) && Objects.equals(tipoDocumento, other.tipoDocumento) && tipoPeticion == other.tipoPeticion
				&& Objects.equals(trazabilidad, other.trazabilidad) && Objects.equals(unidades, other.unidades) && Objects.equals(valor, other.valor);
	}

}

package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_LOGPRUEBASINFORMADAS")
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class LogPruebasInformadas extends BasicEntity {

	private static final long serialVersionUID = 7227950203861581832L;

	@Basic
	private String proveedor;

	@Basic
	private Timestamp fechaDesde;

	@Basic
	private Timestamp fechaHasta;

	@Basic
	private String estado;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogPruebasInformadas other = (LogPruebasInformadas) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fechaDesde, other.fechaDesde) && Objects.equals(fechaHasta, other.fechaHasta)
				&& Objects.equals(proveedor, other.proveedor);
	}
}

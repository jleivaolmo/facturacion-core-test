package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

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
@Table(name = "T_TrazabilidadSolAgrDocumentosSAP")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadSolAgrDocumentosSAP extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 8151973067803505952L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TrazabilidadSolAgr", nullable = false)
	@JsonIgnore
	private TrazabilidadSolicitudAgrupado trazabilidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_tipoDocumento")
	@JsonBackReference
	private MasDataTipoDocumentoSAP tipoDocumento;

	@Basic
	private String valor;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TrazabilidadSolAgrDocumentosSAP other = (TrazabilidadSolAgrDocumentosSAP) obj;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		if (trazabilidad == null) {
			if (other.trazabilidad != null)
				return false;
		} else if (!trazabilidad.equals(other.trazabilidad))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}

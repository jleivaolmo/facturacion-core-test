package com.echevarne.sap.cloud.facturacion.model.contratos;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link ContCapitativoPolizas}.
 *
 * @author Germán Laso
 * @since 17/04/2020
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_CONTRATOCAPITATIVOPOLIZAS, indexes = {
@Index(name = "ContCapitativoPolizas_byCodigo", columnList = "codigoPoliza", unique = false)}
)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ContCapitativoPolizas extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1059365905290987719L;

	@Column(length = 80)
	private String codigoPoliza;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo")
	@JsonBackReference
	private ContratoCapitativo contrato;

	/**
	 * @return the codigoPoliza
	 */
	public String getCodigoPoliza() {
		return codigoPoliza;
	}

	/**
	 * @param codigoPoliza the codigoPoliza to set
	 */
	public void setCodigoPoliza(String codigoPoliza) {
		this.codigoPoliza = codigoPoliza;
	}

	/**
	 * @return the contrato
	 */
	public ContratoCapitativo getContrato() {
		return contrato;
	}

	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(ContratoCapitativo contrato) {
		this.contrato = contrato;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ContCapitativoPolizas other = (ContCapitativoPolizas) obj;
		if (codigoPoliza == null) {
			if (other.codigoPoliza != null)
				return false;
		} else if (!codigoPoliza.equals(other.codigoPoliza))
			return false;
		return true;
	}

}

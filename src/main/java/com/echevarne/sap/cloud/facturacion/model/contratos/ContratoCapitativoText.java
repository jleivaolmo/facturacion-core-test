package com.echevarne.sap.cloud.facturacion.model.contratos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;


/**
 * Class for the Entity {@link ContratoCapitativoText}.
 * @author Germán Laso
 * @since 17/04/2020
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_CONTRATOCAPITATIVOTEXT)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = false)
public class ContratoCapitativoText extends BasicEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 4572179893709867056L;

	@Column(length=100)
	private String textoContrato;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo", nullable = false)
	@JsonBackReference
	private ContratoCapitativo contrato;

	public String getTextoContrato() {
		return textoContrato;
	}

	public void setTextoContrato(String textoContrato) {
		this.textoContrato = textoContrato;
	}

	public ContratoCapitativo getContrato() {
		return contrato;
	}

	public void setContrato(ContratoCapitativo contrato) {
		this.contrato = contrato;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ContratoCapitativoText other = (ContratoCapitativoText) obj;
		if (textoContrato == null) {
			if (other.textoContrato != null)
				return false;
		} else if (!textoContrato.equals(other.textoContrato))
			return false;
		return true;
	}
}

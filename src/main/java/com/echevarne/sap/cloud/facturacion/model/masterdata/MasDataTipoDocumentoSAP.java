package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(
	name="T_MasDataTipoDocumentoSAP",
	indexes = {
		@Index(name = "MasDataTipoDocumentoSAP_byCodigo", columnList = "codigo", unique = false),
	}
)
@SapEntitySet(creatable = false, updatable = false, deletable = false)
public class MasDataTipoDocumentoSAP extends BasicMasDataEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 3082011607991571222L;

	public static final String CREDITMEMOREQUEST = "CREDITMEMOREQUEST";
	public static final String SALESORDER = "SALESORDER";
	public static final String BILLINGDOCUMENT = "BILLINGDOCUMENT";
	public static final String ACCOUNTINGDOCUMENT = "ACCOUNTINGDOCUMENT";
	public static final String CANCELLATIONBILLINGDOCUMENT = "CANCELLATIONBILLINGDOCUMENT";
	public static final String PREFACTURA = "PREFACTURA";

	@Basic
	@NaturalId
	private String codigo;

	@Basic
	private String descripcion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataTipoDocumentoSAP other = (MasDataTipoDocumentoSAP) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

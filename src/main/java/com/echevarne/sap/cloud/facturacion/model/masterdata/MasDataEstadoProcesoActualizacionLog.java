package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "T_MasDataEstadoProcesoActualizacionLog")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = false)
public class MasDataEstadoProcesoActualizacionLog extends BasicMasDataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4928651564818160127L;
	
	@Basic
	@NaturalId
	private Integer codigo;

	@Basic
	private String descripcion;

}

package com.echevarne.sap.cloud.facturacion.model.facturacion;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = ConstEntities.ENTIDAD_FACTLOGPROCESO)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class LogFacturacion extends BasicMessagesEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -3128776990475710320L;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_FacturaGenerada", nullable = false)
	@ToString.Exclude
	@JsonBackReference
	private FacturasGeneradas factura;

}

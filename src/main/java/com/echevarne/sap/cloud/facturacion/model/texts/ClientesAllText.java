package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "VT_CLIENTES")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
@JPAExit(allowAll = true, allowEmpty = false, fieldId = "codigoCliente", fieldDescription = "nombreCliente")
public class ClientesAllText implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1033538473284312048L;

	@Id
	@Sap(filterable = true)
	private String codigoCliente;

	@Basic
	@Sap(filterable = true)
	private String nombreCliente;

	@Basic
	@Sap(filterable = false)
	private String nifCliente;

	@Basic
	@Sap(filterable = true)
	private String z_nifCliente;

	@Basic
	@Sap(filterable = false)
	private String granCliente;
	
	@Basic
	@Sap(filterable = false)
	private String esGranCliente;
}

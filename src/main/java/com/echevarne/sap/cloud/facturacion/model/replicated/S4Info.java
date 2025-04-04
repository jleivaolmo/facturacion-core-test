package com.echevarne.sap.cloud.facturacion.model.replicated;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the S4Info database table.
 */
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name="VR_S4INFO")
public class S4Info {
	
	@Id
	@Basic
	private String GUIDAUX;

	@Basic
	private String KDMAT;
	
	@Basic
	private String MATERIAL;
	
	@Basic
	private String ORG_VENTAS;
	
	@Basic
	private String DELEGACION;
	
	@Basic
	private String CLIENTE;
	
	@Basic
	private String CIANR;

	@Basic
	private String FECHA_INICIO;

	@Basic
	private String FECHA_FIN;

	@Basic
	private String CANTIDAD;

	@Basic
	private String UNIDAD_MEDIDA;

	@Basic
	private String UNIDAD_PROD;

	@Basic
	private String UN_PROD_CLIENTE;

	@Basic
	private String NO_BAREMO;

	@Basic
	private String IMPORTE;

	@Basic
	private String MONEDA;

	@Basic
	private String CANTIDAD_2;
	
	@Basic
	private Integer PRIORIDAD;
}

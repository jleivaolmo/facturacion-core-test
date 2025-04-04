package com.echevarne.sap.cloud.facturacion.model.replicated;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the S4MaterialSales database table.
 */
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@IdClass(S4KeyMaterialSales.class)
@Table(name="VR_S4MATERIALSALES")
public class S4MaterialSales {
	
	@Id
	@Basic
	private String MATNR;
	
	@Id
	@Basic
	private String VKORG;
	
	@Id
	@Basic
	private String VTWEG;
	
	@Basic
	private String MTPOS;
	
	@Basic
	private String SKTOF;
	
	@Basic
	private String MVGR1;
	
	@Basic
	private String MVGR2;
	
	@Basic
	private String MVGR3;
	
	@Basic
	private String MVGR4;
	
	@Basic
	private String MVGR5;
	
}

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
 * The persistent class for the S4Customers database table.
 * 
 */
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name="VR_S4CUSTOMERS")
public class S4Customers {
	
	@Basic
	@Id
	private String KUNNR;
	
	@Basic
	private String LAND1;
	
	@Basic
	private String NAME1;
	
	@Basic
	private String NAME2;
	
	@Basic
	private String ORT01;
	
	@Basic
	private String PSTLZ;
	
	@Basic
	private String REGIO;
	
	@Basic
	private String SORTL;
	
	@Basic
	private String STRAS;
	
	@Basic
	private String TELF1;
	
	@Basic
	private String TELFX;
	
	@Basic
	private String XCPDK;
	
	@Basic
	private String ADRNR;
	
	@Basic
	private String ERDAT;
	
	@Basic
	private String ERNAM;
	
	@Basic
	private String KTOKD;
	
	@Basic
	private String LIFNR;
	
	@Basic
	private String LIFSD;
	
	@Basic
	private String NAME3;
	
	@Basic
	private String NAME4;
	
	@Basic
	private String ORT02;
	
	@Basic
	private String SPRAS;
	
	@Basic
	private String STCD1;
	
	@Basic
	private String STCD2;
	
	@Basic
	private String STCD3;
	
	@Basic
	private String STCD4;
	
	@Basic
	private String STCD5;
	
	@Basic
	private String STCEG;
	
	@Basic
	private String TELBX;
	
	@Basic
	private String TELF2;
	
	@Basic
	private String TELTX;
	
	@Basic
	private String TELX1;
	
}

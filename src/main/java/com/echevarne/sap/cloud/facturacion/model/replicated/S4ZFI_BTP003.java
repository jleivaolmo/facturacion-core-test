package com.echevarne.sap.cloud.facturacion.model.replicated;

import java.io.Serializable;

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

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_S4ZFI_BTP003")
public class S4ZFI_BTP003 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8526790388933420910L;

	@Id
	@Basic
	private String MANDT;

	@Id
	@Basic
	private String EMAIL;

	@Id
	@Basic
	private String DELEG;
	
	@Id
	@Basic
	private String MONITOR;
}

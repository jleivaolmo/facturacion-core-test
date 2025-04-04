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
 * The persistent class for the S4Materials database table.
 */
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name="VR_S4MATERIALSTEXT")
public class S4MaterialTexts {
	
	@Basic
	@Id
	private String MATNR;

	@Basic
	private String SPRAS;
	
	@Basic
	private String MAKTX;
	
	@Basic
	private String MAKTG;
	
}
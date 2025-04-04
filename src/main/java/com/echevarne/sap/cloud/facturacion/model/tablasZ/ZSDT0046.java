package com.echevarne.sap.cloud.facturacion.model.tablasZ;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@Table(name = "T_ZSDT0046")
@IdClass(ZSDT70046Key.class)
public class ZSDT0046 implements Serializable {

	private static final long serialVersionUID = -4785625937046444393L;

	@Basic
	@Id
	private String KUNRG;

	@Basic
	@Id
	private String KUNNR;

	@Basic
	@Id
	private String PMATN;

	@Basic
	private String NETWR;
}

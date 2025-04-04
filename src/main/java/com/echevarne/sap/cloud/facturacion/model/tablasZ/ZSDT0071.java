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
@EqualsAndHashCode
@Table(name = "T_ZSDT0071")
@IdClass(ZSDT70071Key.class)
public class ZSDT0071 implements Serializable {

	private static final long serialVersionUID = 22615141687939285L;
	
	@Basic
	@Id
	private String VKBUR;

	@Basic
	@Id
	private String ZZREMNR;

	@Basic
	@Id
	private String ZZCIANR;

	@Basic
	@Id
	private String ZZCARGO;

	@Basic
	private String ZZACTI;
}

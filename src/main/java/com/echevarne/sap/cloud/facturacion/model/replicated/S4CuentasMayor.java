package com.echevarne.sap.cloud.facturacion.model.replicated;

import javax.persistence.Basic;
import javax.persistence.Column;
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
 * The persistent class for the S4 - ZOTC_SD_DET_CUEN database table.
 */
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@IdClass(S4KeyS4CuentasMayor.class)
@Table(name="VR_ZOTC_SD_DET_CUEN")
public class S4CuentasMayor {
	
	@Id
	@Basic
	@Column(name="MANDT")
	private String mandante;
	
	@Id
	@Basic
	@Column(name="CODOP")
	private String codigoOperacion;

	@Id
	@Basic
	@Column(name="ZLSCH")
	private String idMedioPago;
	
	@Id
	@Basic
	@Column(name="DELEGACION")
	private String codigoDelegacion;
	
	@Id
	@Column(name="HKONT_DEBE",length = 10)
	private String cuentaDebe;

	@Id
	@Column(name="HKONT_HABER", length = 10)
	private String cuentaHaber;	
}
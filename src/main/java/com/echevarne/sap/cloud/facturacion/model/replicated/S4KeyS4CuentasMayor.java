package com.echevarne.sap.cloud.facturacion.model.replicated;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class S4KeyS4CuentasMayor implements Serializable {
	
	private static final long serialVersionUID = 8944460189551537470L;

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

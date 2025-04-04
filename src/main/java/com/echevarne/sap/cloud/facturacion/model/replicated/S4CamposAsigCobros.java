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
@Table(name = "VR_S4CAMPOSASIGCOBROS")
public class S4CamposAsigCobros implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic
	private String identificador;

	@Basic
	private String campo;

	@Basic
	private String descripcion;

}
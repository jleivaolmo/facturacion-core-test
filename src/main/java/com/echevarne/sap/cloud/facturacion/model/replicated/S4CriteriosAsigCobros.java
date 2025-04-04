package com.echevarne.sap.cloud.facturacion.model.replicated;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "VR_S4CRITERIOSASIGCOBROS")
public class S4CriteriosAsigCobros implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private S4CriteriosAsigCobrosKey s4CriteriosAsigCobrosKey;

	@Basic
	private String asignacion;

}
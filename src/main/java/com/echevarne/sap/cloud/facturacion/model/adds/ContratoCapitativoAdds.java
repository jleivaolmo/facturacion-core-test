package com.echevarne.sap.cloud.facturacion.model.adds;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_CONTRATOCAP_ADD")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class ContratoCapitativoAdds implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 8737138314110416811L;

	/*
	 * Clave
	 *
	 *******************************************/
	@Id
	@Basic
	private Long id;

	/*
	 * Campos
	 *
	 *******************************************/

	/*
	 * UI Fields
	 *
	 *******************************************/
	@Basic
	private int critically;

	@Basic
	private String estado;
}

package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Column;
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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "VT_ESTADO")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class EstadoText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3863761738250461194L;
	
	public static final String ESTADO_ACTIVO 	= "Activo";
	public static final String ESTADO_INACTIVO 	= "Inactivo";

	@Id
	//@Column(columnDefinition="VARCHAR(255)")
	private String estado;

}

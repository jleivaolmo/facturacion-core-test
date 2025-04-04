package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
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
@Table(name = "VT_ALMACENES")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@IdClass(value = AlmacenKey.class)
public class AlmacenText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3035330248260819900L;

	@Id
	@Sap(filterable = true)
	private String codigoCentro;
	
	@Id
	@Sap(text="nombreAlmacen", filterable = true)
	private String codigoAlmacen;

	@Basic
	@Sap(filterable = true)
	private String nombreAlmacen;

}

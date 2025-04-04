package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
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
// @IdClass(RegionKey.class)
@Table(name = "VT_REGIONES")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@JPAExit(allowAll = true, allowEmpty = true, fieldId = "codigoRegion", fieldDescription = "nombreRegion")
public class RegionText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -548470872522643172L;

	@Id
	@Sap(text="nombreRegion")
	//@Column(columnDefinition="VARCHAR(255)")
	private String codigoRegion;
	
	@Basic
	@Sap(filterable = true)
	private String codigoPais;
	
	@Basic
	@Sap(filterable = true)
	private String nombreRegion;

}

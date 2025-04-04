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
@Table(name = "VT_ORGANIZACION_VENTAS")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
@JPAExit(allowAll = true, fieldId = "codigoOrganizacion", fieldDescription = "nombreOrganizacion")
public class OrganizacionVentaText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3507249040388779628L;

	@Id
	@Sap(text="nombreOrganizacion")
//	@Column(columnDefinition="VARCHAR(255)")
	@Column(length = 4)
	private String codigoOrganizacion;
	
	@Basic
	@Sap(filterable = true)
	private String nombreOrganizacion;

}

package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

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
@Table(name = "VT_PROVEEDORES_PRINFOYTERCEROS")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@JPAExit(fieldId = "codigoProveedor", fieldDescription = "nombreProveedor")
public class ProveedoresPrinfoytercerosText implements Serializable {

	private static final long serialVersionUID = -4252049708872224535L;
	
	@Id
	@Sap(text="nombreProveedor")
	//@Column(columnDefinition="VARCHAR(255)")
	private String codigoProveedor;
	
	@Basic
	@Sap(filterable = true)
	private String nombreProveedor;
	
	@Basic
	@Sap(filterable = false)
	private String nifProveedor;
	
	@Basic
	@Sap(filterable = true)
	private String z_nifProveedor;

}

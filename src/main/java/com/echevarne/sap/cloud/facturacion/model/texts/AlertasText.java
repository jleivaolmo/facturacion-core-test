package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.Getter;

@Entity
@Getter
@Table(name = "VT_ALERTAS")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class AlertasText implements Serializable {

	@Id
	@Sap(text="nombre")
	private String codigo;

	@Basic
	private String nombre;

}

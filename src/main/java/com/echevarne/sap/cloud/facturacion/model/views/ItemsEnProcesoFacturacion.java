package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_ITEMSENPROCESOFACTURACION")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class ItemsEnProcesoFacturacion implements Serializable {

	private static final long serialVersionUID = -407673514619235185L;

	@Basic
	@Id
	private Long solIndItemInProcess;

	@Basic
	private String material;

	@Basic
	private String codigoPeticion;
}

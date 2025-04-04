package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
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
@Table(name = "VT_REGLAS_FACTURACION")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@JPAExit(fieldId = "id", fieldDescription = "z_descripcionRegla")
public class ReglaFacturacionText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -366096197504663693L;

	@Id
	@Sap(text="z_descripcionRegla")
	private String id;

	@Basic
    @Sap(filterable = true)
	private String z_descripcionRegla;

   
}

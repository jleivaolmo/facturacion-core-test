package com.echevarne.sap.cloud.facturacion.model.replicated;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntityType;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsTypeEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Embeddable
@ToString(callSuper = false, includeFieldNames = false)
@EqualsAndHashCode(callSuper = false)
@SapEntityType(semantics = SemanticsTypeEnum.AGGREGATE)
public class S4CriteriosAsigCobrosKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic
	private String sociedad;

	@Basic
	private String cliente;
	
	@Basic
	@Column(name = "tipo_actividad")
	private String tipoActividad;

}
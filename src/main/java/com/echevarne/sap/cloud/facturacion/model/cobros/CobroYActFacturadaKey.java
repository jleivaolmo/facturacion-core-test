package com.echevarne.sap.cloud.facturacion.model.cobros;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntityType;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsTypeEnum;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.GroupByField;

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
public class CobroYActFacturadaKey implements Serializable {

	private static final long serialVersionUID = 5857189607553170979L;
	
	@Basic
	@Sap(visible = false)
	@GroupByField(fieldName = "id")
	private Long id;
	
	@Basic
	@Sap(filterable = false, visible = false)
	@GroupByField(fieldName = "tipo")
	private String tipo;

}

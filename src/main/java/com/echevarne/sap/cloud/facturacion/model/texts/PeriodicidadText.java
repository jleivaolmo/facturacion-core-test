package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "VT_PERIODICIDAD")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class PeriodicidadText implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2561010396524135163L;

	@Id
    @ValueList(CollectionPath = ValueListEntitiesEnum.Periodicidad, Label = "Periodicidad", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigo") },
                ValueListParameterDisplayOnly = {
                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, filterable = true, text="nombre")
	private Long codigo;
	
	@Basic
	@Sap(filterable = true)
	private String nombre;

}

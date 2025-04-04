package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_ProcesoActualizacionCodGrupoEstado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ProcesoActualizacionCodGrupoEstado extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5895570343631887196L;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.GrupoEstados, Label = "Estado de facturación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "grupoEstado") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, text = "nombreGrupoEstado", valueList = PropertyValueListEnum.Fixed)
	private String grupoEstado;
	
	public ProcesoActualizacionCodGrupoEstado(String grupoEstado) {
		this.grupoEstado = grupoEstado;
	}
	
	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_proceso", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private ProcesoActualizacion proceso;
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProcesoActualizacionCodGrupoEstado other = (ProcesoActualizacionCodGrupoEstado) obj;
		return Objects.equals(grupoEstado, other.grupoEstado);
	}
}

package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesProfesionalesText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ProvLiqRemitenteProfesional")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ProvLiqRemitenteProfesional extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 96902196653919611L;

	@ValueList(CollectionPath = ValueListEntitiesEnum.RemitentesProfesionales, Label = "Remitente/Profesional", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "remitenteProfesional") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteProfesionalText/nombre")
	private String remitenteProfesional;
	
	@Basic
	private Integer secuencia;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_proveedorLiquidacion", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private ProveedorLiquidacion proveedorLiquidacion;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="remitenteProfesional", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesProfesionalesText remitenteProfesionalText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProvLiqRemitenteProfesional other = (ProvLiqRemitenteProfesional) obj;
		return Objects.equals(remitenteProfesional, other.remitenteProfesional);
	}
	
	public RemitentesProfesionalesText getRemitentesProfesionalesText() {
		return EntityUtil.getOrNull(() -> this.remitenteProfesionalText, RemitentesProfesionalesText::getNombre);
	}

}

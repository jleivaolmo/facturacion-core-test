package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import java.util.Objects;
import javax.persistence.Column;
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
import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_ProcesoActualizacionCompanias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(searchable = true)
public class ProcesoActualizacionCompanias extends BasicEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5895570343631887196L;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "compania") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "companiaText/nombreCompania", filterable = true)
	private String compania;
	
	public ProcesoActualizacionCompanias(String compania) {
		this.compania = compania;
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
	
	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "compania", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;
	
	public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ProcesoActualizacionCompanias other = (ProcesoActualizacionCompanias) obj;
		return Objects.equals(compania, other.compania);
	}
}

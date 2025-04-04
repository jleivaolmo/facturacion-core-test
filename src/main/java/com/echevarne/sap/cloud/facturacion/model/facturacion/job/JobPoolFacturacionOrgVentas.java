package com.echevarne.sap.cloud.facturacion.model.facturacion.job;

import java.io.Serializable;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_JobPoolFacturacionOrgVentas")
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class JobPoolFacturacionOrgVentas extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5537276439312409184L;

	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String organizacionVentas;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPoolFacturacion", nullable = false)
	@JsonBackReference
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private JobPoolFacturacion cabecera;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "organizacionVentas", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPoolFacturacionOrgVentas other = (JobPoolFacturacionOrgVentas) obj;
		if (organizacionVentas == null) {
			if (other.organizacionVentas != null)
				return false;
		} else if (!organizacionVentas.equals(other.organizacionVentas))
			return false;
		return true;
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}
}

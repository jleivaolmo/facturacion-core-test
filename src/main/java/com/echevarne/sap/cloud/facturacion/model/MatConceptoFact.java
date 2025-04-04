package com.echevarne.sap.cloud.facturacion.model;

import java.util.Calendar;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.ConceptosFacturacionText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sortable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FieldSortEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor()
@Table(
		name = "T_MatConceptoFact",
		indexes = {
			@Index(name = "IDX_byCodigoMaterial", columnList = "codigoMaterial, conceptoFacturacion, validez_desde", unique = true),
		}
)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class MatConceptoFact extends BasicEntity {

	private static final long serialVersionUID = -804732863737134607L;

	@Column(length = 20)
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Perfil / Prueba", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoMaterial") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "materialText/nombreMaterial")
	@Sortable(order=FieldSortEnum.Asc, priority=1)
	private String codigoMaterial;

	@Column(length = 20)
	@ValueList(CollectionPath = ValueListEntitiesEnum.ConceptosFacturacion, Label = "Concepto facturacion", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "conceptoFacturacion") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "conceptoText/nombreMaterial")
	@Sortable(order=FieldSortEnum.Asc, priority=2)
	private String conceptoFacturacion;

	@Basic
	@Column(name = "validez_desde", nullable = false)
	@Sap(displayFormat = DisplayFormatEnum.Date, filterable = true)
	@Sortable(order=FieldSortEnum.Asc, priority=3)
	private Calendar validezDesde;

	@Basic
	@Column(name = "validez_hasta", nullable = false)
	@Sap(displayFormat = DisplayFormatEnum.Date, filterable = true)
	private Calendar validezHasta;

    /*
     * Associations Texts
     *
     ********************************************/
	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "id", insertable = false, updatable = false, nullable = true)
	private MatConceptoAdicional adicional;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoMaterial", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MaterialesVentaText materialText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "conceptoFacturacion", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConceptosFacturacionText conceptoText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MatConceptoFact other = (MatConceptoFact) obj;
		if (codigoMaterial == null) {
			if (other.codigoMaterial != null)
				return false;
		} else if (!codigoMaterial.equals(other.codigoMaterial))
			return false;
		if (conceptoFacturacion == null) {
			if (other.conceptoFacturacion != null)
				return false;
		} else if (!conceptoFacturacion.equals(other.conceptoFacturacion))
			return false;
		return true;
	}

	public MaterialesVentaText getMaterialText() {
		return EntityUtil.getOrNull(() -> this.materialText, MaterialesVentaText::getNombreMaterial);
	}

	public ConceptosFacturacionText getConceptoText() {
		return EntityUtil.getOrNull(() -> this.conceptoText, ConceptosFacturacionText::getNombreMaterial);
	}
}

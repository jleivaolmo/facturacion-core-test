package com.echevarne.sap.cloud.facturacion.model.contratos;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.RegionText;
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
import com.google.common.base.Objects;

@Entity
@Table(name = ConstEntities.ENTIDAD_CONTRATOCAPITATIVOPROVREMITENTE, indexes = {
		@Index(name = "IDX_byCodigoProvRemitente", columnList = "provinciaRemitente", unique = false) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ContCapitativoProvRemitente extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4360678725286907633L;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Regiones, Label = "Provincia del remitente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRegion", LocalDataProperty = "provinciaRemitente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreRegion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "regionRemitenteText/nombreRegion")
	private String provinciaRemitente;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo")
	@JsonBackReference
	private ContratoCapitativo contrato;

	public String getProvinciaRemitente() {
		return provinciaRemitente;
	}

	public void setProvinciaRemitente(String provinciaRemitente) {
		this.provinciaRemitente = provinciaRemitente;
	}

	public ContratoCapitativo getContrato() {
		return contrato;
	}

	public void setContrato(ContratoCapitativo contrato) {
		this.contrato = contrato;
	}

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "provinciaRemitente", referencedColumnName = "codigoRegion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RegionText regionRemitenteText;
	
	public RegionText getRegionRemitenteText() {
		return EntityUtil.getOrNull(() -> this.regionRemitenteText, RegionText::getNombreRegion);
	}

	public void setRegionRemitenteText(RegionText regionRemitenteText) {
		this.regionRemitenteText = regionRemitenteText;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ContCapitativoProvRemitente other = (ContCapitativoProvRemitente) obj;
		Objects.equal(provinciaRemitente, other.provinciaRemitente);
		return true;
	}

}

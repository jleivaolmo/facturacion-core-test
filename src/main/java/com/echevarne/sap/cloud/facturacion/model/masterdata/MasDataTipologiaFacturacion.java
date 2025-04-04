package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

/**
 * Class for the Entity {@link MasDataTipologiaFacturacion}.
 * @author Hernan Girardi
 * @since 23/04/2020
 */

@Entity
@Table(name = "T_MasDataTipologiaFacturacion", indexes = {
		@Index(name = "IDX_byCodeTipologia", columnList = "codeTipologia", unique = true) })
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MasDataTipologiaFacturacion extends BasicMasDataEntity {

	private static final long serialVersionUID = 2275491684721847930L;

	@Column(length=3,nullable=false)
	@NaturalId
	@Sap(valueList=PropertyValueListEnum.Fixed, text = "descriptionTipologia")
	private String codeTipologia;

	@Basic
	private String descriptionTipologia;

	@OneToMany(mappedBy = "tipologia", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<MasDataSubTipologiaFacturacion> subTipologias = new HashSet<>();

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataTipologiaFacturacion other = (MasDataTipologiaFacturacion) obj;
		if (codeTipologia != other.codeTipologia)
			return false;
		if (descriptionTipologia == null) {
			if (other.descriptionTipologia != null)
				return false;
		} else if (!descriptionTipologia.equals(other.descriptionTipologia))
			return false;
		if (active != other.active)
			return false;
		return true;
	}

}

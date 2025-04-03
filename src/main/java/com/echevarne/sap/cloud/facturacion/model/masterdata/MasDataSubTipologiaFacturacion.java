package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter
@Setter
@Table(
        name = "T_MasDataSubTipologiaFacturacion",
        indexes = {
                @Index(name = "IDX_byCodeSubTipologia", columnList = "codeSubTipologia", unique = true),
        }
)
public class MasDataSubTipologiaFacturacion extends BasicMasDataEntity {

    private static final long serialVersionUID = -3566640465336837746L;

    @Column(length=2, nullable=false)
	@Sap(valueList=PropertyValueListEnum.Fixed, text = "descriptionSubTipologia")
	private String codeSubTipologia;

	@Basic
	private String descriptionSubTipologia;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataTipologiaFacturacion", nullable = false)
	@JsonBackReference
	MasDataTipologiaFacturacion tipologia;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MasDataSubTipologiaFacturacion other = (MasDataSubTipologiaFacturacion) obj;
        if (descriptionSubTipologia == null) {
            if (other.descriptionSubTipologia != null)
                return false;
        } else if (!descriptionSubTipologia.equals(other.descriptionSubTipologia))
            return false;
        if (tipologia == null) {
            if (other.tipologia != null)
                return false;
        } else if (!tipologia.equals(other.tipologia))
            return false;
        if (codeSubTipologia != other.codeSubTipologia)
			return false;
        return true;
    }

}

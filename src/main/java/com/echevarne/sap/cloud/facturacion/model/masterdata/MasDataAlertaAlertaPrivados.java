package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.Objects;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name="T_MasDataAlerta_AlertaPrivados")
public class MasDataAlertaAlertaPrivados extends BasicMasDataEntity {

	private static final long serialVersionUID = -7679663596861623947L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Alerta", nullable = false)
	@JsonBackReference
	private MasDataAlerta alerta;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_AlertaPrivados", nullable = false)
	@JsonBackReference
	private MasDataAlertaPrivados alertaPrivados;

	private boolean delegacion;

	private int diasDelegacion;

	private boolean central;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataAlertaAlertaPrivados other = (MasDataAlertaAlertaPrivados) obj;
		return  Objects.equals(this.alerta, other.alerta) &&
				Objects.equals(this.alertaPrivados, other.alertaPrivados);
	}
}

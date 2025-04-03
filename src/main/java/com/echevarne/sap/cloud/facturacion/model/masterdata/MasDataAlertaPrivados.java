package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@AllArgsConstructor
@NoArgsConstructor
@Table(name="T_MasDataAlertaPrivados",
	indexes= {@Index(name = "IDX_ByCodigoOperacion",  columnList="codigoOperacion", unique=true)})
public class MasDataAlertaPrivados extends BasicMasDataEntity {

	private static final long serialVersionUID = -7679663596861623947L;

	@Column(nullable=false)
	@NaturalId
	private String codigoOperacion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="alertaPrivados")
	private Set<MasDataAlertaAlertaPrivados> alertaAlertaPrivados;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataAlertaPrivados other = (MasDataAlertaPrivados) obj;
		return  Objects.equals(this.codigoOperacion, other.codigoOperacion);
	}
}

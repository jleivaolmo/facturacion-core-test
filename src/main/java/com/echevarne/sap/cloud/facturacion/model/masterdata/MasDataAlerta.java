package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NaturalId;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.model.privados.OperacionesPrivados;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.SelectionFields;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Hernan Girardi
 * @since 24/08/2020
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name="T_MasDataAlerta",
	indexes= {
			@Index(name = "IDX_ByCodigoAlerta",  columnList="codigoAlerta", unique=true),
			@Index(name = "IDX_byCanalVenta", columnList = "canalVenta", unique = false),
			@Index(name = "IDX_byBloqueo", columnList = "bloquea", unique = false)
	})
@SapEntitySet(creatable = false, updatable = false, deletable = false)
public class MasDataAlerta extends BasicMasDataEntity {

	private static final long serialVersionUID = 6302018834893893750L;

	@Column(length=4, nullable=false)
	@SelectionFields
	@Sap(text = "nombreAlerta", filterable = true)
	@NaturalId
	private String codigoAlerta;

	@Basic
	@SelectionFields
	private String nombreAlerta;

	@Basic
	private String descripcion;

	@Column(columnDefinition="NVARCHAR(2) default '*'", nullable=false)
	private String canalVenta;

	@Column(columnDefinition="SMALLINT default 0", nullable=false)
	@SelectionFields
	@Sap(filterRestriction = FilterRestrictionsEnum.Single)
	private boolean bloquea;

	@Basic
	@SelectionFields
	@Sap(filterRestriction = FilterRestrictionsEnum.Single)
	private boolean tratablePrivados;
	
	@Basic
	private Integer diasVigencia;

	@OneToOne(mappedBy = "alerta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	@EqualsAndHashCode.Exclude
	private MasDataMotivosEstado motivo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="alerta")
	@JsonManagedReference
	@EqualsAndHashCode.Exclude
	private Set<MasDataAlertaAlertaPrivados> alertaAlertaPrivados;
	
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataAlerta other = (MasDataAlerta) obj;
		return  Objects.equals(this.codigoAlerta, other.codigoAlerta) &&
				Objects.equals(this.nombreAlerta, other.nombreAlerta) &&
				Objects.equals(this.descripcion, other.descripcion) &&
				Objects.equals(this.canalVenta, other.canalVenta) &&
				Objects.equals(this.diasVigencia, other.diasVigencia) &&
				this.bloquea == other.bloquea &&
				this.tratablePrivados == other.tratablePrivados;
	}

	public boolean contieneOperacionPrivados(OperacionesPrivados operacionPrivados) {
		return alertaAlertaPrivados
				.stream()
				.anyMatch(aap -> aap.getAlertaPrivados().getCodigoOperacion().equals(operacionPrivados.name()));
	}

	public MasDataAlertaAlertaPrivados getOperacionPrivados(OperacionesPrivados operacionPrivados) {
		return alertaAlertaPrivados
				.stream()
				.filter(aap -> aap.getAlertaPrivados().getCodigoOperacion().equals(operacionPrivados.name()))
				.findAny().orElse(null);
	}
}

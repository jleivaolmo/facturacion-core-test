package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Objects;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * @author Germán Laso
 * @since 04/10/2020
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_RegFactEspecialidadCliente")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
@Cacheable(false)
public class RegFactEspecialidadCliente extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = -655477727989302102L;

	@Matcheable
	@Basic
	private String especialidadCliente;

	/*
	 * Custom Constructor
	 *
	 ********************************************/
	public RegFactEspecialidadCliente(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
	 * Persistence events
	 *
	 ********************************************/
	@PrePersist
	public void prePersist() {
		if (StringUtils.equalsAnyOrNull(this.especialidadCliente, StringUtils.EMPTY))
			this.especialidadCliente = StringUtils.ANY;
	}

	/*
	 * Associations
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ReglaFacturacion")
	@JsonBackReference
	private ReglasFacturacion regla;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactEspecialidadCliente other = (RegFactEspecialidadCliente) obj;
		return Objects.equals(especialidadCliente, other.especialidadCliente);
	}

}

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
@Table(name = "T_RegFactDocumentoUnico")
@SapEntitySet(creatable = true, updatable = true, searchable = true)
@Cacheable(false)
public class RegFactDocumentoUnico extends ReglaFactBase {

	/**
	 *
	 */
	private static final long serialVersionUID = -7909959408041650353L;

	@Matcheable
	@Basic
    private String documentoUnico;

	/*
     * CustomConstructor
     *
     ********************************************/
	public RegFactDocumentoUnico(ReglasFacturacion regla) {
		this.regla = regla;
	}

	/*
     * Persistence events
     *
     ********************************************/
	@PrePersist
	public void prePersist() {
	    if(StringUtils.equalsAnyOrNull(this.documentoUnico,StringUtils.EMPTY))
	    	this.documentoUnico = StringUtils.ANY;
	}

	/*
     * Associations
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="fk_ReglaFacturacion")
	@JsonBackReference
	private ReglasFacturacion regla;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		RegFactDocumentoUnico other = (RegFactDocumentoUnico) obj;
		return Objects.equals(documentoUnico, other.documentoUnico);
	}

}

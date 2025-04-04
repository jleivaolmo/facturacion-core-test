package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = "T_SolAgrPruebaActividad")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false, includeFieldNames = false)
public class SolAgrPruebaActividad extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5355118879478777055L;

	public SolAgrPruebaActividad() {
		pruebasInformadas = new HashSet<SolAgrPruebaInformada>();
		pruebasTerceros = new HashSet<SolAgrPruebaTerceros>();
		logs = new HashSet<SolAgrPruebaActividadLog>();
	}

	@Basic
	private String tipoAgrupacion;

	@Basic
	private String proveedor;

	@Basic
	private String delegacionProductiva;

	@Basic
	private String unidadProductiva;

	@Basic
	private Date fechaValidacionDesde;

	@Basic
	private Date fechaValidacionHasta;

	@Basic
	private String sociedad;

	@Basic
	private String resultados;

	public void addAgrupacion(SolAgrPruebaBaseEntity agr) {
		if (agr instanceof SolAgrPruebaInformada) {
			this.pruebasInformadas.add((SolAgrPruebaInformada) agr);
		} else if (agr instanceof SolAgrPruebaTerceros) {
			this.pruebasTerceros.add((SolAgrPruebaTerceros) agr);
		}
	}

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
	@JsonIgnore
	private Set<SolAgrPruebaInformada> pruebasInformadas;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
	@JsonIgnore
	private Set<SolAgrPruebaTerceros> pruebasTerceros;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
	@JsonIgnore
	private Set<SolAgrPruebaActividadLog> logs;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPruebaTerceros", nullable = true)
	@JsonIgnore
	private JobPruebaTerceros jobPruebaTerceros;


	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		SolAgrPruebaActividad other = (SolAgrPruebaActividad) obj;
		if (delegacionProductiva == null) {
			if (other.delegacionProductiva != null)
				return false;
		} else if (!delegacionProductiva.equals(other.delegacionProductiva))
			return false;
		if (fechaValidacionDesde == null) {
			if (other.fechaValidacionDesde != null)
				return false;
		} else if (!fechaValidacionDesde.equals(other.fechaValidacionDesde))
			return false;
		if (fechaValidacionHasta == null) {
			if (other.fechaValidacionHasta != null)
				return false;
		} else if (!fechaValidacionHasta.equals(other.fechaValidacionHasta))
			return false;
		if (proveedor == null) {
			if (other.proveedor != null)
				return false;
		} else if (!proveedor.equals(other.proveedor))
			return false;
		if (sociedad == null) {
			if (other.sociedad != null)
				return false;
		} else if (!sociedad.equals(other.sociedad))
			return false;
		if (tipoAgrupacion == null) {
			if (other.tipoAgrupacion != null)
				return false;
		} else if (!tipoAgrupacion.equals(other.tipoAgrupacion))
			return false;
		return true;
	}

}

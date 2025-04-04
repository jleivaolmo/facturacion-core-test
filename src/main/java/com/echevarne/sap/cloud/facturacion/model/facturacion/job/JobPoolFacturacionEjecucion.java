package com.echevarne.sap.cloud.facturacion.model.facturacion.job;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturados;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_JobPoolFacturacionEjecucion")
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class JobPoolFacturacionEjecucion extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7705209974115865028L;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	public Calendar fechaEjecucion;

	@OneToOne(cascade = ALL, mappedBy = "jobPoolFacturacionEjecucion", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private PeriodosFacturados periodos;

	@OneToMany(cascade = ALL, mappedBy = "ejecucion")
	@JsonManagedReference
	@Builder.Default
	private Set<LogPoolFacturacion> logs = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPoolFacturacion", nullable = false)
	@JsonBackReference
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private JobPoolFacturacion job;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPoolFacturacionEjecucion other = (JobPoolFacturacionEjecucion) obj;
		if (fechaEjecucion == null) {
			if (other.fechaEjecucion != null)
				return false;
		} else if (!fechaEjecucion.equals(other.fechaEjecucion))
			return false;
		if (periodos == null) {
			if (other.periodos != null)
				return false;
		} else if (!periodos.equals(other.periodos))
			return false;
		return true;
	}
}

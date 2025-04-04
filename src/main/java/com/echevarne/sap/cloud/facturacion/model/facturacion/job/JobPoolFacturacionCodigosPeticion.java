package com.echevarne.sap.cloud.facturacion.model.facturacion.job;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_JobPoolFacturacionCodigosPeticion")
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class JobPoolFacturacionCodigosPeticion extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7070278840062258262L;

	@Basic
	private String codigoPeticion;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPoolFacturacion", nullable = false)
	@JsonBackReference
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private JobPoolFacturacion cabecera;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JobPoolFacturacionCodigosPeticion other = (JobPoolFacturacionCodigosPeticion) obj;
		if (codigoPeticion == null) {
			if (other.codigoPeticion != null)
				return false;
		} else if (!codigoPeticion.equals(other.codigoPeticion))
			return false;
		return true;
	}

}

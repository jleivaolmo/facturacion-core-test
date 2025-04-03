package com.echevarne.sap.cloud.facturacion.model.parametrizacion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "T_ParametrosGenerales", indexes={@Index(name = "IDX_ParamGeneralesNombre",  columnList="nombre", unique=true)})
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false, includeFieldNames = false)
public class ParametrosGenerales extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -1653984886652745748L;

	public static final String CENTROPRINFOYTERCEROS = "CENTROPRINFOYTERCEROS";
	public static final String FORMATOFECHAFECTURA = "FORMATOFECHAFACTURA";

	@Basic
	@NaturalId
	private String nombre;

	@Basic
	private String valor;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ParametrosGenerales other = (ParametrosGenerales) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}

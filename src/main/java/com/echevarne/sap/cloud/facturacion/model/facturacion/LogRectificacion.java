package com.echevarne.sap.cloud.facturacion.model.facturacion;

import java.sql.Time;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_LOGRECTIFICACION")
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class LogRectificacion extends BasicEntity {

	private static final long serialVersionUID = 1L;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fecha;

	@Basic
	private Time hora;

	@Basic
	private String accionRealizada;

	@Basic
	private String factura;

	@Basic
	private String rectificativa;

	@Basic
	private String error;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogRectificacion other = (LogRectificacion) obj;
		return Objects.equals(accionRealizada, other.accionRealizada) && Objects.equals(error, other.error) && Objects.equals(factura, other.factura) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(hora, other.hora) && Objects.equals(rectificativa, other.rectificativa);
	}

}

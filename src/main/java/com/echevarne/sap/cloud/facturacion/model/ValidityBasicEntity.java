package com.echevarne.sap.cloud.facturacion.model;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;

import lombok.Data;

/**
 * Abstract class with validity date range.
 */
@Data
@MappedSuperclass
public abstract class ValidityBasicEntity extends BasicEntity {

	private static final long serialVersionUID = -7157821083997085910L;

	@Basic
	@Column(name = "validez_desde", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezDesde = ValidityBasicEntity.getDefaultFechaDesde();

	@Basic
	@Column(name = "validez_hasta", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezHasta = ValidityBasicEntity.getDefaultFechaHasta();

	public static Calendar getDefaultFechaDesde(){
		final Calendar cal = Calendar.getInstance();
		cal.set(1999, Calendar.JANUARY, 1, 0, 0, 0);
		return cal;
	}

	public static Calendar getDefaultFechaHasta(){
		final Calendar cal = Calendar.getInstance();
		cal.set(9999, Calendar.DECEMBER, 31, 0, 0, 0);
		return cal;
	}

	@Override
	public boolean onEquals(Object other) {
		return this.equals(other);
	}

}

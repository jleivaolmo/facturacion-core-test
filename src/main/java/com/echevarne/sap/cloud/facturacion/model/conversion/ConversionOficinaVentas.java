package com.echevarne.sap.cloud.facturacion.model.conversion;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

@Entity
@Table(
	name = ConstEntities.ENTIDAD_CONVERSIONOFICINAVENTAS,
	indexes = {
		@Index(name = "IDX_byTrack", columnList = "trak", unique = false),
	}
)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class ConversionOficinaVentas extends BasicEntity {

	private static final long serialVersionUID = 6162647707111191686L;

	@Basic
	private String trak;
	@Basic
	private String oficinaVentas;

	public String getTrak() {
		return trak;
	}

	public void setTrak(String trak) {
		this.trak = trak;
	}

	public String getOficinaVentas() {
		return oficinaVentas;
	}

	public void setOficinaVentas(String oficinaVentas) {
		this.oficinaVentas = oficinaVentas;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)  return true;
		if (obj == null || getClass() != obj.getClass())  return false;
		ConversionOficinaVentas other = (ConversionOficinaVentas) obj;
		return 	Objects.equals(this.trak, other.trak) &&
				Objects.equals(this.oficinaVentas, other.oficinaVentas);
	}

}

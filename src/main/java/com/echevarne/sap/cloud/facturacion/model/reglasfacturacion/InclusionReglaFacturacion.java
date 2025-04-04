package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstEntities.ENTIDAD_REGFACTINCLUSION)
@SapEntitySet(creatable = true, updatable = true, searchable = true)
public class InclusionReglaFacturacion extends BasicMasDataEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -2503677723501004635L;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="fk_ReglasFacturacion")
	@JsonBackReference
	@ToString.Exclude
	private ReglasFacturacion reglasFacturacion;

	@Basic
	private boolean incluyePoliza = true;

	@Basic
	private boolean incluyeTipoPeticion = true;

	@Basic
	private boolean incluyeOperacion = true;

	@Basic
	private boolean incluyeSector = true;

	@Basic
	private boolean incluyeEmpresa = true;

	@Basic
	private boolean incluyeCompania = true;

	@Basic
	private boolean incluyeRemitente = true;

	@Basic
	private boolean incluyeUnidadProductiva = true;

	@Basic
	private boolean incluyePrueba = true;

	@Basic
	private boolean incluyeDocumentoUnico = true;

	@Basic
	private boolean incluyeServicioConcertado = true;

	@Basic
	private boolean incluyeReferenciaCliente = true;

	@Basic
	private boolean incluyePeticion = true;

	@Basic
	private boolean incluyeMoneda = true;

	@Basic
	private boolean incluyeBaremo = true;

	@Basic
	private boolean incluyeConcepto = true;

	@Basic
	private boolean incluyeDelegacion = true;

	@Basic
	private boolean incluyeProvRemitente = true;

	@Basic
	private boolean incluyeMuestraRemitida = true;

	@Basic
	private boolean incluyeTarifa = true;

	@Basic
	private boolean incluyeEspecialidadCliente = true;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		InclusionReglaFacturacion other = (InclusionReglaFacturacion) obj;
		return  incluyeOperacion == other.incluyeOperacion &&
				incluyeTipoPeticion == other.incluyeTipoPeticion &&
				incluyePoliza == other.incluyePoliza &&
				incluyeSector == other.incluyeSector &&
				incluyeEmpresa == other.incluyeEmpresa &&
				incluyeCompania == other.incluyeCompania &&
				incluyeRemitente == other.incluyeRemitente &&
				incluyeUnidadProductiva == other.incluyeUnidadProductiva &&
				incluyePrueba == other.incluyePrueba &&
				incluyeDocumentoUnico == other.incluyeDocumentoUnico &&
				incluyeServicioConcertado == other.incluyeServicioConcertado &&
				incluyeReferenciaCliente == other.incluyeReferenciaCliente;
	}

}

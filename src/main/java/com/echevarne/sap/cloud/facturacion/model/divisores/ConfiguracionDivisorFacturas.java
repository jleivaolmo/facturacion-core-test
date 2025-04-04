package com.echevarne.sap.cloud.facturacion.model.divisores;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta clase se usa para separar las facturas segun ciertos criterios.
 * Cada atributo booleano indica si se incluye o no un valor en la separacion de las facturas.
 *
 * La annottion @SplitedBy tiene un elemento field que hace referencia a un atributo de una entidad Splittable
 * (AgrReglasFacturacion o ContratoCapitativo) y un elemento code que se usa para identificar el valor del campo en la
 * clave usada para la separacion de facturas.
 *
 * Cada atributo booleano que tenga el valor true indica que al construir la clave se va a incluir el valor del campo
 * asociado en el objeto Splittable y se se va a identificar con el codigo indicado (codigo:valor).
 * Una clave va a estar formada por varias partes separadas por un "_".
 *
 * Por ejemplo la clave "sv:1_ic:3_crc:2" esta formada por 3 partes (sv:1, ic:3 y crc:2)
 * Donde sv, ic y crc son los codigos y 1, 3 y 2 son los valores asociados a cada uno.
 * El valor 1 corresponde al codigo sv que identifica al sectorVentas.
 * El 1 es el valor que tiene el campo sectorVentas en el objeto Splittable.
 */

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstEntities.ENTIDAD_REGFACTCONFIGURACIONDIVISORFACTURA)
@SapEntitySet(creatable = true, updatable = true, searchable = true)
public class ConfiguracionDivisorFacturas extends BasicMasDataEntity {

	private static final long serialVersionUID = -1218719891125029390L;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="fk_ReglasFacturacion")
	@JsonBackReference
	private ReglasFacturacion reglasFacturacion;

	@Basic
	private String create;

	@Basic
	@SplitedBy(field = "codigoPoliza", code = "cpo")
	private boolean facturaPorPoliza;

	@Basic
	@SplitedBy(field = "tipoPeticion", code = "tp")
	private boolean facturaPorTipoPeticion;

	@Basic
	@SplitedBy(field = "codigoOperacion", code = "co")
	private boolean facturaPorOperacion;

	@Basic
	@SplitedBy(field = "sectorVentas", code = "sv")
	private boolean facturaPorSector;

	@Basic
	@SplitedBy(field = "interlocutorEmpresa", code = "ie")
	private boolean facturaPorEmpresa;

	@Basic
	@SplitedBy(field = "interlocutorCompania", code = "ic")
	private boolean facturaPorCompania;

	@Basic
	@SplitedBy(field = "interlocutorRemitente", code = "ir")
	private boolean facturaPorRemitente;

	@Basic
	@SplitedBy(field = "unidadProductiva", code = "up")
	private boolean facturaPorUnidadProductiva;

	@Basic
	@SplitedBy(field = "codigoPrueba", code = "cp")
	private boolean facturaPorPrueba;

	@Basic
	@SplitedBy(field = "documentoUnico", code = "du")
	private boolean facturaPorDocumentoUnico;

	@Basic
	@SplitedBy(field = "servicioConcertado", code = "sc")
	private boolean facturaPorServicioConcertado;

	@Basic
	@SplitedBy(field = "codigoReferenciaCliente", code = "crc")
	private boolean facturaPorReferenciaCliente;

	@Basic
	@SplitedBy(field = "codigoPeticion", code = "fpp")
	private boolean facturaPorPeticion;

	@Basic
	@SplitedBy(field = "codigoMoneda", code = "fpm")
	private boolean facturaPorMoneda;

	@Basic
	@SplitedBy(field = "codigoBaremo", code = "fpb")
	private boolean facturaPorBaremo;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "conceptoFacturacion", code = "fpc")
	private boolean facturaPorConcepto;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoDelegacion", code = "cd")
	private boolean facturaPorDelegacion;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "provinciaRemitente", code = "pr")
	private boolean facturaPorProvRemitente;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "muestraRemitida", code = "mr")
	private boolean facturaPorMuestraRemitida;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "tarifa", code = "tar")
	private boolean facturaPorTarifa;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "especialidadCliente", code = "ec")
	private boolean facturaPorEspecialidadCliente;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ConfiguracionDivisorFacturas other = (ConfiguracionDivisorFacturas) obj;
		return  facturaPorOperacion == other.facturaPorOperacion &&
				facturaPorTipoPeticion == other.facturaPorTipoPeticion &&
				facturaPorPoliza == other.facturaPorPoliza &&
				facturaPorSector == other.facturaPorSector &&
				facturaPorEmpresa == other.facturaPorEmpresa &&
				facturaPorCompania == other.facturaPorCompania &&
				facturaPorRemitente == other.facturaPorRemitente &&
				facturaPorUnidadProductiva == other.facturaPorUnidadProductiva &&
				facturaPorPrueba == other.facturaPorPrueba &&
				facturaPorDocumentoUnico == other.facturaPorDocumentoUnico &&
				facturaPorServicioConcertado == other.facturaPorServicioConcertado &&
				facturaPorReferenciaCliente == other.facturaPorReferenciaCliente;
	}

}

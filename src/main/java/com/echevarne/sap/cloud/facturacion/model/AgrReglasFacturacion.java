package com.echevarne.sap.cloud.facturacion.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;

import com.echevarne.sap.cloud.facturacion.model.divisores.SplitedBy;
// import com.echevarne.sap.cloud.facturacion.model.divisores.ConfiguracionDivisorFactura;
import com.echevarne.sap.cloud.facturacion.model.divisores.Splittable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hernan Girardi
 * @since 16/07/2020
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgrReglasFacturacion implements Splittable{

	@Basic
    private int prioridad;

	@Basic
    private String organizacionVentas;

	@Basic
    private String codigoCliente;

	@Basic
    private String codigoDelegacion;

	@Basic
    private String delegacionEmisora;

	@Basic
    private int tipoPeticion;

	@Basic
    private String sectorVentas;

	@Basic
    private String interlocutorEmpresa;

	@Basic
    private String interlocutorCompania;

	@Basic
    private String interlocutorRemitente;

	@Basic
    private String unidadProductiva;

	@Basic
    private String servicioConcertado;

	@Basic
    private String documentoUnico;

	@Basic
    private String provinciaRemitente;

	@Basic
    private String codigoPoliza;

	@Basic
    private String codigoOperacion;

	@Basic
    private String codigoPrueba;

	@Basic
    private String codigoReferenciaCliente;

	@Basic
	private String codigoPais;

	@Basic
	private String codigoMoneda;

	@Basic
	private String codigoPeticion;

	@Basic
	private String codigoBaremo;

	@Basic
	private String tarifa;

	@Basic
	private String conceptoFacturacion;

	@Basic
	private String especialidadCliente;

	@Basic
	private boolean muestraRemitida;

	@Basic
	private boolean envioPrefactura;

	@Basic
	private String sectorPrueba;

	@Basic
	private Set<String> especialidadAplicar;

	@Basic
	private String material;

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
	@SplitedBy(field = "conceptoFacturacion", code = "fpc")
	private boolean facturaPorConcepto;

	@Basic
	@SplitedBy(field = "codigoDelegacion", code = "cd")
	private boolean facturaPorDelegacion;

	@Basic
	@SplitedBy(field = "provinciaRemitente", code = "pr")
	private boolean facturaPorProvRemitente;

	@Basic
	@SplitedBy(field = "muestraRemitida", code = "mr")
	private boolean facturaPorMuestraRemitida;

	@Basic
	@SplitedBy(field = "tarifa", code = "tar")
	private boolean facturaPorTarifa;

	@Basic
	@SplitedBy(field = "especialidadCliente", code = "ec")
	private boolean facturaPorEspecialidadCliente;

	@Basic
	@SplitedBy(field = "sectorPrueba", code = "sp")
	private boolean facturaPorGrupoSector;

	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AgrReglasFacturacion other = (AgrReglasFacturacion) obj;
		return 	Objects.equals(codigoCliente, other.codigoCliente) &&
				Objects.equals(codigoDelegacion, other.codigoDelegacion) &&
				Objects.equals(codigoOperacion, other.codigoOperacion) &&
				Objects.equals(codigoPoliza, other.codigoPoliza) &&
				Objects.equals(codigoPrueba, other.codigoPrueba) &&
				Objects.equals(codigoReferenciaCliente, other.codigoReferenciaCliente) &&
				Objects.equals(delegacionEmisora, other.delegacionEmisora) &&
				Objects.equals(documentoUnico, other.documentoUnico) &&
				Objects.equals(interlocutorCompania, other.interlocutorCompania) &&
				Objects.equals(interlocutorEmpresa, other.interlocutorEmpresa) &&
				Objects.equals(interlocutorRemitente, other.interlocutorRemitente) &&
				Objects.equals(organizacionVentas, other.organizacionVentas) &&
				prioridad == other.prioridad &&
				Objects.equals(provinciaRemitente, other.provinciaRemitente) &&
				Objects.equals(sectorVentas, other.sectorVentas) &&
				Objects.equals(servicioConcertado, other.servicioConcertado) &&
				Objects.equals(codigoMoneda, other.codigoMoneda) &&
				Objects.equals(codigoPeticion, other.codigoPeticion) &&
				Objects.equals(codigoBaremo, other.codigoBaremo) &&
				tipoPeticion == other.tipoPeticion &&
				Objects.equals(unidadProductiva, other.unidadProductiva);
	}

	// @Override
	// public ConfiguracionDivisorFactura getSplitter() {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }

}

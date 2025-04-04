package com.echevarne.sap.cloud.facturacion.agrupador;

import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgrupadorDto {
	
	private ContratoCapitativo contratoCapitativo;
	private String claseDocumento;
	private String organizacionVentas;
	private String canalDistribucion;
	private String codigoCliente;
	private String textoFactura;
	private String agrupacionKey;
	private String divisorKey;
	private Long objectId;;
	private int tipologiaAgrupacion;
}

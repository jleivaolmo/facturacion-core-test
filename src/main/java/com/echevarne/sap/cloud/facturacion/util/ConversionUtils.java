package com.echevarne.sap.cloud.facturacion.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.olingo.odata2.core.uri.expression.FilterExpressionImpl;

import com.echevarne.sap.cloud.facturacion.model.texts.EstadoText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoDescuentoText;

public class ConversionUtils {

	private static final String QUOTES = "'";
	private static final String SPACE = " ";
	public static final String CAMPOCODIGOCLIENTE = "codigoCliente";
	public static final String CAMPOCODIGOPRUEBA = "codigoPrueba";
	public static final String CAMPOINTERLOCUTORPACIENTE = "interlocutorPaciente";
	public static final String CAMPOINTERLOCUTOREMPRESA = "interlocutorEmpresa";
	public static final String CAMPOINTERLOCUTORREMITENTE = "interlocutorRemitente";
	public static final String CAMPOINTERLOCUTORCOMPANIA = "interlocutorCompania";
	public static final String CAMPOPROVEEDOR = "proveedor";
	public static final String CAMPODELEGACIONPRODUCTIVA = "delegacionProductiva";
	public static final String CAMPOPRUEBA = "prueba";
	public static final String CAMPOCODIGODELEGACIONPRODUCTIVA = "codigoOficina";
	public static final String CAMPOCODIGOPROVEEDOR = "codigoProveedor";
	public static final String OFICINAVENTAS = "oficinaVentas";
	public static final String DELEGACION = "delegacion";
	public static final String CODIGODELEGACION = "codigoDelegacion";
	public static final String CODIGOREGION = "codigoRegion";
	public static final String PROVINCIAREMITENTE = "provinciaRemitente";
	public static final String PROVINCIADELEGACION = "provinciaDelegacion";
	public static final String DELEGACIONORIGEN = "delegacionOrigen";
	public static final String DELEGACIONEMISORA = "delegacionEmisora";
	public static final String SOLDTOPARTY = "soldToParty";
	public static final String CODIGOORGANIZACION = "codigoOrganizacion";
	public static final String CODIGOPETICION = "codigoPeticion";
	public static final String CODIGOSECTOR = "codigoSector";
	public static final String FECHAPRECIO = "fechaPrecio";
	public static final String TIPOPETICION = "tipoPeticion";
	public static final String CODIGOCOMPANIA = "codigoCompania";
	public static final String CODIGOREMITENTE = "codigoRemitente";
	public static final String AREAVENTAS = "areaVentas";
	public static final String CARGOPETICION = "cargoPeticion";
	public static final String CODALERTA = "codigosAlerta";
	public static final String CODDIVISA = "codigoDivisa";
	public static final String CODHISTCLINICA = "codigoHistoriaClinica";
	public static final String CODCANAL = "codigoCanal";
	public static final String CODESTADO = "estado/codigoEstado";
	public static final String CODGRUPOESTADO = "estado/codigoGrupoEstado";
	public static final String CODPACIENTE = "codigoPaciente";
	public static final String CODPETLIMS = "codigoPeticionLims";
	public static final String CODREFEXT = "codigoReferenciaExterno";
	public static final String CODTARIFA = "codigoTarifa";
	public static final String CODUSREXT = "codigoUsuarioExterno";
	public static final String CONCEPTOFACT = "conceptoFact";
	public static final String ESMIXTA = "esMixta";
	public static final String ESMUESTRAREMITIDA = "esMuestraRemitida";
	public static final String ESPRIVADA = "esPrivada";
	public static final String FECHARECMUESTRA = "fechaRecepcionMuestra";
	public static final String FECHATOMAMUESTRA = "fechaTomaMuestra";
	public static final String FECHAACTUALIZACION = "fechaActualizacion";
	public static final String FECHACREACION = "fechaCreacion";
	public static final String FECHAFACTURA = "fechaFactura";
	public static final String MOTIVOESTADO = "motivoEstado";
	public static final String NUMEROCONTRATO = "contrato/numeroContrato";
	public static final String PROVREMITENTE = "provinciaRemitente";
	public static final String REFCLIENTE = "referenciaCliente";
	public static final String TIPOCONTRATO = "contrato/tipoContrato";
	public static final String TIPOCOTIZACION = "tipoCotizacion";
	public static final String FACTURAVENTA = "facturaVenta";
	public static final String LOTE = "lote";
	public static final String LUGARMUESTREO = "lugarMuestreo";
	public static final String NOMBREANIMAL = "nombreAnimal";
	public static final String CODIGOPROPIETARIO = "codigoPropietario";
	public static final String NOMBREDESCR = "nombreDescr";
	public static final String PEDIDOVENTA = "pedidoVenta";
	public static final String PREFACTURA = "prefactura";
	
	public static final List<String> removeLeadingZerosFields = Arrays.asList(CAMPOCODIGOPRUEBA);
	
	//Mapa para campos interpretados en modo texto, que se requieren en las Fioris
	public static final Map<String, String> fioriFieldMap = Stream.of(new String[][] {
		  { "statusFiori", "active" }, 
		  { "tipoFiori", "porcentual" }, 
		}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	public static final Map<Object, Object> fioriValueMap = Stream.of(new Object[][] {
		  { EstadoText.ESTADO_ACTIVO, true }, 
		  { EstadoText.ESTADO_INACTIVO, false },
		  {TipoDescuentoText.TIPO_PORCENTUAL, true },
		  {TipoDescuentoText.TIPO_FIJO, false }
		}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	public static final List<String> operators = Arrays.asList("eq","gt","lt","ne");
	
	

	/**
	 *
	 * @param fieldName
	 * @param externalValue
	 * @return
	 */
	public static String applyAlphaConversionForField(String fieldName, String externalValue) {
		if (StringUtils.isInteger(externalValue) && applyAlphaConvertionByFieldName(fieldName)) {
			return applyAlphaConversionForField(fieldName, Integer.parseInt(externalValue));
		}
		return externalValue;

	}

	/**
	 * 
	 * @param fieldName
	 * @param externalValue
	 * @return
	 */
	public static String applyAlphaConversionForField(String fieldName, int externalValue) {
		String format = getFormatForField(fieldName);
		return String.format(format, externalValue);
	}

	/**
	 * 
	 * @param fieldName
	 * @param operator
	 * @param externalValue
	 * @return
	 */
	public static String applyAlphaConversionForField(String fieldName, String operator, int externalValue) {
		String format = getFormatForField(fieldName);
		String internalValue = QUOTES + String.format(format, externalValue) + QUOTES;
		String filterString = fieldName + SPACE + operator + SPACE + internalValue;
		return filterString;
	}

	/**
	 * 
	 * @param filter
	 */
	public static boolean applyAlphaConvertion(FilterExpressionImpl filter) {
		String expressionString = filter.getExpressionString().toLowerCase();
		return applyAlphaConvertion(expressionString);
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	public static boolean applyAlphaConvertionByFieldName(String fieldName) {
		if (fieldName.equalsIgnoreCase(CAMPOCODIGOCLIENTE)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(SOLDTOPARTY)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGOPRUEBA)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORPACIENTE)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTOREMPRESA)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORREMITENTE)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORCOMPANIA)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOPROVEEDOR)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPODELEGACIONPRODUCTIVA)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOPRUEBA)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGODELEGACIONPRODUCTIVA)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGOPROVEEDOR)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(OFICINAVENTAS)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(DELEGACION)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(CODIGODELEGACION)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(PROVINCIAREMITENTE)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(PROVINCIADELEGACION)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(DELEGACIONORIGEN)) {
			return true;
		} else if (fieldName.equalsIgnoreCase(DELEGACIONEMISORA)) {
			return true;
		}
		return false;
	}

	public static boolean applyAlphaConvertion(String expressionString) {
		if (expressionString.contains(CAMPOCODIGOCLIENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(SOLDTOPARTY.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOCODIGOPRUEBA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTORPACIENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTOREMPRESA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTORREMITENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTORCOMPANIA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOPROVEEDOR.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPODELEGACIONPRODUCTIVA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOPRUEBA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOCODIGODELEGACIONPRODUCTIVA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOCODIGOPROVEEDOR.toLowerCase())) {
			return true;
		} else if (expressionString.contains(OFICINAVENTAS.toLowerCase())) {
			return true;
		} else if (expressionString.contains(DELEGACION.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CODIGODELEGACION.toLowerCase())) {
			return true;
		} else if (expressionString.contains(PROVINCIAREMITENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(PROVINCIADELEGACION.toLowerCase())) {
			return true;
		} else if (expressionString.contains(DELEGACIONORIGEN.toLowerCase())) {
			return true;
		} else if (expressionString.contains(DELEGACIONEMISORA.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	private static String getFormatForField(String fieldName) {
		if (fieldName.equalsIgnoreCase(CAMPOCODIGOCLIENTE)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(SOLDTOPARTY)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGOPRUEBA)) {
			return "%018d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORPACIENTE)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTOREMPRESA)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORREMITENTE)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORCOMPANIA)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOPROVEEDOR)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPODELEGACIONPRODUCTIVA)) {
			return "%04d";
		} else if (fieldName.equalsIgnoreCase(CAMPOPRUEBA)) {
			return "%018d";
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGODELEGACIONPRODUCTIVA)) {
			return "%04d";
		} else if (fieldName.equalsIgnoreCase(OFICINAVENTAS)) {
			return "%04d";
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGOPROVEEDOR)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(DELEGACION)) {
			return "%04d";
		} else if (fieldName.equalsIgnoreCase(CODIGOREGION)) {
			return "%02d";
		} else if (fieldName.equalsIgnoreCase(CODIGODELEGACION)) {
			return "%04d";
		} else if (fieldName.equalsIgnoreCase(PROVINCIAREMITENTE)) {
			return "%02d";
		} else if (fieldName.equalsIgnoreCase(PROVINCIADELEGACION)) {
			return "%02d";
		} else if (fieldName.equalsIgnoreCase(DELEGACIONORIGEN)) {
			return "%04d";
		} else if (fieldName.equalsIgnoreCase(DELEGACIONEMISORA)) {
			return "%04d";
		} else
			return "%010d";
	}

	public static String getFioriCustomizedField(String fioriField) {
		return fioriFieldMap.get(fioriField);
	}

	public static boolean isOperator(String value) {
		return operators.contains(value);
	}

	public static Object getFioriCustomizedValue(String fioriValue) {
		String keyValue = fioriValue.replace("'", "");
		return fioriValueMap.get(keyValue);
	}

}

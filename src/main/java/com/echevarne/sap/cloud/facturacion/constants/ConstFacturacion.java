package com.echevarne.sap.cloud.facturacion.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstFacturacion {
	
	/**
	 * 
	 * Configuración de ventas (SAP)
	 * 
	 */
	public static final String TIPO_ORDEN_VENTAS_SIMULACION 	= "ZSIM";
	public static final String TIPO_ORDEN_VENTAS_AGRUPADO 		= "ZCGR";
	public static final String TIPO_ORDEN_VENTAS_PRIVADOS 		= "ZCPR";
	public static final String TIPO_ORDEN_VENTAS_ZCGC 			= "ZCGC";
	public static final String TIPO_ORDEN_VENTAS_ZSPR 			= "ZSPR";
	
	public static final String TIPO_POSICION_PERFIL 			= "ZTAQ";
	public static final String TIPO_POSICION_PRUEBAPERFIL 		= "ZTAE";
	public static final String TIPO_POSICION_PRUEBA 			= "ZTAD";
	public static final String TIPO_POSICION_INCONGRUENTE		= "ZTPI";
	public static final String TIPO_POSICION_NO_FACTURABLE		= "ZTNF";
	public static final String TIPO_POSICION_EXCLUIDA			= "ZTEX";
	public static final String TIPO_POSICION_BLOQUEO_CORTESIA	= "ZTBC";
	public static final String TIPO_POSICION_BLOQUEO_RECHAZO	= "ZTRE";

	public static final String S4_POSICION_NO_FACTURABLE		= "ZNFR";

	public static final String CONDICION_PRECIO_BRUTO			= "ZPR0";
	public static final String CONDICION_PRECIO_NETO			= "ZPR1";
	public static final String CONDICION_PRECIO_IVA				= "MWST";
	public static final String CONDICION_PRECIO_FIJO_PETICION	= "ZFIP";
	public static final String CONDICION_PRECIO_VALOR_PERFIL	= "ZVPE";
	public static final String CONDICION_PRECIO_MODIFICADO		= "ZNET";
	public static final String CONDICION_PRECIO_CAB_CON_CF		= "ZCAP";
	public static final String CONDICION_PRECIO_CAB_CON_CV		= "ZCAP";
	public static final String CONDICION_PRECIO_PUNTOS			= "ZPUN";
	public static final String CONDICION_PRECIO_ADICIONAL_FIJO	= "ZDPS";
	public static final String CONDICION_PRECIO_ADICIONAL_PORCENTAJE = "ZDPR";
	public static final String CONDICION_VALOR_PUNTOS			= "ZVAL";
	
	public static final String CONDITION_CURRENCY_EUROS			= "EUR";
	public static final String CONDITION_CURRENCY_USD			= "USD";
	public static final String CONDITION_CURRENCY_PUNTOS		= "ZPT";
	public static final String CONDITION_CURRENCY_CONTRATOS		= "ZFI";
	public static final String CODIGO_DELEGACION_CENTRAL		= "0001";
	
	public static final String[] CONDICIONES_ENVIO_CONDVALUE	= {CONDICION_PRECIO_VALOR_PERFIL, CONDICION_PRECIO_FIJO_PETICION};
	public static final String[] CONDITION_CURRENCY_MONEY	= {CONDITION_CURRENCY_EUROS, CONDITION_CURRENCY_USD};

	/**
	 * Tipos de agrupaciones (salesOrderType - Solicitud agrupada)
	 */
    public static final String TIPO_AGRUPACION_FACTURA_CAPFIJO = "ZFCF";
    public static final String TIPO_AGRUPACION_FACTURA_CAPVARIABLE = "ZFCV";
    public static final String TIPO_AGRUPACION_FACTURA_FIJOPETICION = "ZFFP";
    public static final String TIPO_AGRUPACION_FACTURA_ACTOMEDICO = "ZFAM";
    public static final String TIPO_AGRUPACION_PREFACTURA_FIJOPETICION = "ZPFP";
    public static final String TIPO_AGRUPACION_PREFACTURA_ACTOMEDICO = "ZPAM";

	/**
	 * Valores organizativos por defecto
	 */
	public static final String CANAL_DISTRIBUCION_COMUN = "CC";
	
	/**
	 * Sales Order Custom Fields
	 */
	
	//HEDAER
	public static final String SO_SDH_CUSTOM_FIELD_CARGO_PETICION = "ZZ1_SIM_CARGO_SDH";
	public static final String SO_SDH_CUSTOM_FIELD_FECHA_PETICION = "ZZ1_SIM_FECP_SDH";
	public static final String SO_SDH_CUSTOM_FIELD_TIPO_PETICION = "ZZ1_SIM_TIPOPET_SDH";
	public static final String SO_SDH_CUSTOM_FIELD_ID_DESCUENTO = "ZZ1_TIPO_DSCNT_SDH"; 
	public static final String SO_SDH_CUSTOM_FIELD_DESC_DESCUENTO = "ZZ1_DESC_DSCNT_SDH";
	
	//ITEMS
	public static final String SO_SDI_CUSTOM_FIELD_PRECIO_REFERENCIA_MATERIAL = "ZZ1_SIM_PMATN_SDI";
	//TODO hasta simplificar modelo de campos CUSTOM en SAP se usa ZZ1_SIM_DELPR_SDI luego ZZ1_GRP_DELPR_SDI
	public static final String SO_SDI_CUSTOM_FIELD_DELEGACION_PRODUCTIVA = "ZZ1_SIM_DELPR_SDI";
	
	/**
	 * Privados
	 */
	public static final String ONETIMECUSTOMER_PREFIX			= "CPD";
	public static final String ONETIMECUSTOMER_DEFAULT_POSTALCODE = "0008";
	public static final String ONETIMECUSTOMER_DEFAULT_REGION = "08";
	public static final String ONETIMECUSTOMER_DEFAULT_CITY = "BARCELONA(default)";
	public static final String ONETIMECUSTOMER_DEFAULT_ADDRESS = "ADDRESS(default)";	
	public static final String MATERIAL_SALES_VTWEG_VALUE = "CC";

	
	/**
	 * Interlocutores
	 */
	public static final String TIPO_INTERLOCUTOR_PERSONA 	= "1";
	public static final String TIPO_INTERLOCUTOR_ORGANIZ 	= "2";
	
	public static final String ROL_INTERLOCUTOR_REMITENTE 		= "ZR";
	public static final String ROL_INTERLOCUTOR_COMPANIA 		= "ZC";
	public static final String ROL_INTERLOCUTOR_EMPRESA 		= "ZE";
	public static final String ROL_INTERLOCUTOR_PACIENTE 		= "ZH";
	public static final String ROL_INTERLOCUTOR_PROFESIONAL		= "ZP";
	public static final String ROL_INTERLOCUTOR_VISITADOR 		= "ZV";
	//public static final String ROL_INTERLOCUTOR_VENDEDOR 		= "ZV";
	public static final String ROL_INTERLOCUTOR_COMISIONISTA	= "ZL";
	public static final String ROL_INTERLOCUTOR_SOLICITANTE		= "AG";
	
	/**
	 * Address Phone Types
	 */
	public static final String TIPO_TELEFONO_FIJO 			= "1";
	public static final String TIPO_TELEFONO_MOVIL 			= "3";
	
	/**
	 * Locale
	 */
	public static final String IDIOMA_DEFAULT 				= "ES";
	public static final String CURRENCY_DEFAULT 			= "EUR";
	
	/**
	 * Contratos Capitativos
	 */
	public static final String TIPO_CONTRATO_FP 			= "FP";
	public static final String TIPO_CONTRATO_CV 			= "CV";
	public static final String TIPO_CONTRATO_CF 			= "CF";
	
	
	/**
	 * Tipologías de facturación
	 */
	public static final String POR_ACTO_MEDICO = "AM";
	public static final String POR_ACTIVIDAD = "ACT";
	public static final String TIPOLOGIA_ACTO_MEDICO = "Acto médico";
	public static final String TIPOLOGIA_CAPITATI_FP = "Actividad - Fijo por petición";
	public static final String TIPOLOGIA_CAPITATI_CF = "Actividad - Capitativo fijo";
	public static final String TIPOLOGIA_CAPITATI_CV = "Actividad - Capitativo variable";
	
	/**
	 * Textos genericos para el pedido de ventas
	 * 
	 */
	public static final String TEXTO_FACTURA_DEFAULT = "0002";
	public static final String TEXTO_RECTIFICACION_DEFAULT = "0003";

	/**
	 * Sufijos para peticiones mixtas
	 */
	public static final String SUF_MIXTA_MUTUA = "-M";
	public static final String SUF_MIXTA_PRIVADO = "-P";
	
	public static final Integer ESTADO_PREFACTURA_PENDIENTE = 0;
	public static final Integer ESTADO_PREFACTURA_EN_PROCESO = 1;
	public static final Integer ESTADO_PREFACTURA_FINALIZADO = 2;
	public static final Integer ESTADO_PREFACTURA_CANCELADO = 3;
	
	
	/**
	 * Campos clave de filtro JSON de Fiori
	 */
	public static final String A_FILTERS = "aFilters";
	public static final String S_PATH = "sPath";
	public static final String O_VALUE1 = "oValue1";
	public static final String O_VALUE2 = "oValue2";
	public static final String S_OPERATOR = "sOperator";
	public static final String BT = "BT";
	
}

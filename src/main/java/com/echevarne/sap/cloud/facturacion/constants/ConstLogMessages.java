package com.echevarne.sap.cloud.facturacion.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstLogMessages {

    /**
     * General
     */
    public static final String MODULE_RECEPCION = "Recepción";
    public static final String MODULE_TRANSFORMACION = "Transformación";
    public static final String MODULE_AGRUPACION = "Agrupación";
    public static final String MODULE_INTERLOCUTORES = "Interlocutores"; 

    public static final String CALL_RESTTEMPLATE = "Invocación al microservicio de & en la url &";
    
    /**
     * Recepción
     */
    public static final String SEND_TRANSFORMATION = "Se envia la solicitud a transformar";

     /**
     * Transformación
     */
    public static final String INIT_TRANSFORMATION = "Se inicia el proceso de tranformación de la petición";
    public static final String INIT_SIMULATION = "Se inicia el proceso de simulación de la petición";
    public static final String INIT_REGLAPREFACTURA = "Se inicia el proceso de determinación de regla de prefactura";
    public static final String INIT_REGLAFACTURACION = "Se inicia el proceso de determinación de regla de facturación";
    public static final String INIT_CONTRATOCAPITATIVO = "Se inicia el proceso de determinación de contrato para petición";
    public static final String END_TRANSFORMATION = "Se ha finalizado el proceso de tranformación de la petición";
	public static final String ERROR_SIMULATION = "Error al simular la petición: ";

	// Tipos de acciones
	public static final String FACTURACION = "Facturación";
	public static final String PREFACTURACION = "Prefacturación";
	public static final String CANCELACION_PREFACTURA = "Cancelación Prefactura";
	public static final String ELIMINAR_PRECIO = "Eliminar Precio";
	public static final String MODIFICAR_PRECIO = "Modificar Precio";
	public static final String EXCLUIR_PRUEBA = "Excluir Prueba";
	public static final String INCLUIR_PRUEBA = "Incluir Prueba";
	public static final String BLOQUEAR_SOLICITUD = "Bloquear Solicitud";
	public static final String DESBLOQUEAR_SOLICITUD = "Desbloquear Solicitud";
	public static final String RECTIFICACION = "Rectificación";
	public static final String ANULACION_FACTURA = "Anulación Factura";
	public static final String PROPUESTA_LIQUIDACION = "Propuesta liquidación";
	public static final String CONFIRMAR_LIQUIDACION = "Confirmar liquidación";
	public static final String CANCELAR_LIQUIDACION = "Cancelar liquidación";
}

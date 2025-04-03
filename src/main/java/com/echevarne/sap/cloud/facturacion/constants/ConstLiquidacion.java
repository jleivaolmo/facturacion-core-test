package com.echevarne.sap.cloud.facturacion.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstLiquidacion {
	
	//Estados liquidacion
    public static final Integer ESTADO_LIQUIDABLE = 1;
    public static final Integer ESTADO_EN_LIQUIDACION = 2;
    public static final Integer ESTADO_LIQUIDADA = 3;
    public static final Integer ESTADO_NO_LIQUIDABLE = 4;
    public static final Integer ESTADO_PARCIALMENTE_LIQUIDADA = 5;
    public static final Integer ESTADO_CANCELADA = 6;
    public static final Integer ESTADO_CANCELANDO = 7;
    public static final Integer ESTADO_ERROR = 8;
    public static final Integer ESTADO_RECTIFICADA = 9;
    
    //Tipos de liquidación
    public static final Integer LIQUIDACION_REMITENTE = 1;
    public static final Integer LIQUIDACION_PROFESIONAL = 2;
    
    //Tipo de orden de compras
    public static final String TIPO_ORDEN_COMPRAS_REM = "ZLIR";
    public static final String TIPO_ORDEN_COMPRAS_PR = "ZLIP";
    
    //Metodos
    public static final String LIQUIDACION = "LIQUIDACION";
    public static final String CANCELACION_LIQUIDACION = "CANCELACION_LIQUIDACION";
}

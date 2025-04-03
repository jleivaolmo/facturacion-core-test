package com.echevarne.sap.cloud.facturacion.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** 
 *  Anotación para marcar los métodos para los que se desea crear
 *  mensajes de log al inicio, retorno normal y salida con 
 *  excepción.
 *  El aspecto MethodLoggingAspect se encarga de producir estas 
 *  registros en el log.
 *  En entornos productivos, normalmente se optará por compilar
 *  sin incluir AspectJ, con lo cual este comportamiento no
 *  se aplica y se asegura así que el impacto en rendimiento sea 
 *  nulo.
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
}

package com.echevarne.sap.cloud.facturacion.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstEstados {

    /**
     * 
     * Motivos de los estados
     * 
     */

    // Bloqueos
    public static final String PREFIJO_ESTADO_BLKA = "BLKA"; // La petición se ha bloqueado por contener alertas.
    public static final String PREFIJO_ESTADO_BLKPAU = "BLKPAU"; // Peticion Mixta de mutua bloqueada
    public static final String PREFIJO_ESTADO_BLKPM = "BLKPM"; // Prueba con bloqueo mixtas. Desbloqueo programado para el {0}
                                                                 

    public static final String MOTIVO_ESTADO_BLKMFA = "BLKMFA"; // La petición se ha realizado por una facturación
                                                                // anticipada.
    public static final String MOTIVO_ESTADO_BLKMZ4 = "BLKMZ4"; // Se ha aplicado un bloqueo de cortesía.
    public static final String MOTIVO_ESTADO_BLKMZM = "BLKMZM"; // Se ha recibido una carta de compromiso de pago.
    public static final String MOTIVO_ESTADO_BLKMZN = "BLKMZN"; // La peticion es antigua y ya no se puede facturar.
    public static final String MOTIVO_ESTADO_BLKMZX = "BLKMZX"; // Se ha bloqueado por estar fuera del periodo de
                                                                // facturación.
    public static final String MOTIVO_ESTADO_BLKMZY = "BLKMZY"; // La peticion no cumple las condiciones para ser
                                                                // facturada.

    // Erronea
    public static final String MOTIVO_ESTADO_ERRA = "ERRA"; // 0
    public static final String MOTIVO_ESTADO_ERRNQ = "ERRNQ"; // La petición contiene pruebas con distintod errores.
    public static final String MOTIVO_ESTADO_ERRP = "ERRP"; // Falta configurar Precio en S4.
    public static final String MOTIVO_ESTADO_ERRP2 = "ERRP2"; // Falta configurar Impuesto en S4.
    public static final String MOTIVO_ESTADO_ERRSIM = "ERRSIM"; // Error al simular. Consulte el log de proceso para más información.
    public static final String MOTIVO_ESTADO_ERRSIMC = "ERRSIMC"; // No existe el cliente en S4.
    public static final String MOTIVO_ESTADO_ERRSIMI = "ERRSIMI"; // El interlocutor {0} no ha podido ser creado.
    public static final String MOTIVO_ESTADO_ERRZFI = "ERRZFI"; // La prueba {0} está en puntos y no se ha determinado un contrato.
    public static final String MOTIVO_ESTADO_ERRPER = "ERRPER"; // Hay pruebas de diferente tipo en un mismo perfil
    public static final String MOTIVO_ESTADO_ERRPER2 = "ERRPER2"; //El Perfil contiene una o más pruebas erroneas
    public static final String MOTIVO_ESTADO_ERRZPT = "ERRZPT"; // La prueba tiene ZPR0 en ZPT y no tiene tipo de cotización.
    public static final String MOTIVO_ESTADO_ERRSC1 = "ERRSC1"; // No tiene Concepto hasta la validación de la prueba.
    public static final String MOTIVO_ESTADO_ERRSC2 = "ERRSC2"; // No tiene Concepto por error.
    public static final String MOTIVO_ESTADO_ERRSC3 = "ERRSC3";
    public static final String MOTIVO_ESTADO_ERRSC4 = "ERRSC4";
    
    // Abonada
    public static final String MOTIVO_ESTADO_ABA = "ABA"; // 0
    public static final String MOTIVO_ESTADO_ABC = "ABC"; // La petición ha sido incluida en un proceso de cancelación.
                                                          // Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_ABP = "ABP"; // 0

    // Cobrada
    public static final String MOTIVO_ESTADO_COA = "COA"; // 0
    public static final String MOTIVO_ESTADO_COS = "COS"; // La petición ha sido cobrada.
    public static final String MOTIVO_ESTADO_COP = "COP"; // 0

    // CreadaRecibida (No facturable)
    public static final String MOTIVO_ESTADO_CRA = "CRA"; // 0
    public static final String MOTIVO_ESTADO_CRS = "CRS"; // Se ha recibido la petición desde los sistemas de laboratorio.
    public static final String MOTIVO_ESTADO_CRP = "CRP"; // Se ha recibido la prueba desde los sistemas de laboratorio.
    public static final String MOTIVO_ESTADO_CRNV = "CRNV"; // Prueba sin Fecha de Validación.

    // Borrada
    public static final String MOTIVO_ESTADO_DELA = "DELA"; // 0
    public static final String MOTIVO_ESTADO_DELC = "DELS"; // La petición ha sido eliminada en los sistemas de
                                                            // laboratorio.
    public static final String MOTIVO_ESTADO_DELP = "DELP"; // 0

    // Excluida
    public static final String MOTIVO_ESTADO_EXA = "EXA"; // 0
    public static final String MOTIVO_ESTADO_EXS = "EXS"; // Toda las pruebas de la petición han sido excluidas del
                                                          // proceso de facturación.
    public static final String MOTIVO_ESTADO_EXIN = "EXIN"; // La prueba es incongruentes. Existe un material en la
                                                            // petición que la convierte en excluida.
    public static final String MOTIVO_ESTADO_EXMA = "EXMA"; // La prueba se ha excluido por acción del usuario.
    public static final String MOTIVO_ESTADO_EXNF = "EXNF"; // La prueba es no facturable por la configuración.
    public static final String MOTIVO_ESTADO_EXBC = "EXBC"; // La prueba aplica para la configuración de exclusión por
                                                            // cortesía.
    public static final String MOTIVO_ESTADO_EXMR = "EXMR"; // La prueba contiene un motivo de rechazo informado por
                                                            // trak.

    // Facturable
    public static final String MOTIVO_ESTADO_FBA = "FBA"; // 0
    public static final String MOTIVO_ESTADO_FBS = "FBS"; // Todas las pruebas de la petición son facturables.
    public static final String MOTIVO_ESTADO_FBPFP = "FBPFP"; // La prueba aplica a la configuración de los clientes que
                                                              // facturan por fecha de petición
    public static final String MOTIVO_ESTADO_FBPVA = "FBPVA"; // La prueba es facturable por haber sido validada el día
                                                              // {0}.

    // Facturada
    public static final String MOTIVO_ESTADO_FA = "FA"; // 0
    public static final String MOTIVO_ESTADO_FS = "FS"; // La petición ha sido incluida en un proceso de facturación.
                                                        // Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_FP = "FP"; // La prueba ha sido incluida en un proceso de facturación.
                                                        // Revise la trazabilidad.

    // Facturando
    public static final String MOTIVO_ESTADO_FNA = "FNA"; // 0
    public static final String MOTIVO_ESTADO_FNS = "FNS"; // La petición se encuentra actualmente en un proceso de
                                                          // facturación.
    public static final String MOTIVO_ESTADO_FNP = "FNP"; // La prueba se encuentra actualmente en un proceso de
                                                          // facturación.

    public static final String MOTIVO_ESTADO_LIQA = "LIQA"; // 0
    public static final String MOTIVO_ESTADO_LIQS = "LIQS"; // 0
    public static final String MOTIVO_ESTADO_LIQP = "LIQP"; // 0
    public static final String MOTIVO_ESTADO_PAA = "PAA"; // 0
    public static final String MOTIVO_ESTADO_PAS = "PAS"; // La petición ha sido incluida en un proceso de cancelación.
                                                          // Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_PAP = "PAP"; // La prueba ha sido incluida en un proceso de cancelación.
                                                          // Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_PCA = "PCA"; // 0
    public static final String MOTIVO_ESTADO_PCS = "PCS"; // La petición ha sido incluida en un pedido de ventas. Revise
                                                          // la trazabilidad.
    public static final String MOTIVO_ESTADO_PCOA = "PCOA"; // 0
    public static final String MOTIVO_ESTADO_PCOS = "PCOS"; // 0
    public static final String MOTIVO_ESTADO_PCOP = "PCOP"; // 0
    public static final String MOTIVO_ESTADO_PCP = "PCP"; // La prueba ha sido incluida en un pedido de ventas. Revise
                                                          // la trazabilidad.
    public static final String MOTIVO_ESTADO_PFA = "PFA"; // 0
    public static final String MOTIVO_ESTADO_PFS = "PFS"; // La petición ha sido incluida en un proceso de
                                                          // pre-facturación. Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_PFP = "PFP"; // La prueba ha sido incluida en un proceso de
                                                          // pre-facturación. Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_RA = "RA"; // 0
    public static final String MOTIVO_ESTADO_RS = "RS"; // La petición ha sido incluida en un proceso de rectificación.
                                                        // Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_RP = "RP"; // La prueba ha sido incluida en un proceso de rectificación.
                                                        // Revise la trazabilidad.
    public static final String MOTIVO_ESTADO_SEA = "SEA"; // 0
    public static final String MOTIVO_ESTADO_SES = "SES"; // No contiene estado.
    public static final String MOTIVO_ESTADO_SEP = "SEP"; // No contiene estado.
    public static final String MOTIVO_ESTADO_VAA = "VAA"; // 0
    public static final String MOTIVO_ESTADO_VAS = "VAS"; // Se ha recibido la autorización / validación desde los
                                                          // sistemas de laboratorio.
    public static final String MOTIVO_ESTADO_VAP = "VAP"; // Se ha recibido la autorización / validación desde los
                                                          // sistemas de laboratorio.
}

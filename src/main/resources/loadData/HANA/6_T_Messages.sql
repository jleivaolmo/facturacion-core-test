INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (1,'Mensaje con Clave {0} no configurado.',NOW(),1,'0000',0,'es',NOW(),'messages.not.found');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (2,'Error al procesar la solicitud.',NOW(),1,'0001',0,'es',NOW(),'solicitudmuestreo.business.exception.empty');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (3,'La Petición Nro {0} se ha recibido con éxito.',NOW(),1,'0002',0,'es',NOW(),'solicitudmuestreo.create.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (4,'Se comienza el proceso de recepcion de {0}.',NOW(),1,'0003',0,'es',NOW(),'solicitudmuestreo.create.init');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (5,'Se han gestionado con éxito los cambios en la petición {0}.',NOW(),1,'0004',0,'es',NOW(),'solicitudmuestreo.update.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (6,'Se comienza el proceso de transformación de {0}.',NOW(),1,'0005',0,'es',NOW(),'solicitudmuestreo.transform.init');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (7,'Se ha creado con éxito la solicitud individual de la petición {0}.',NOW(),1,'0006',0,'es',NOW(),'solicitudmuestreo.transform.solind.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (8,'Se ha procesado con éxito la simulación de la petición {0}.',NOW(),1,'0007',0,'es',NOW(),'solicitudmuestreo.transform.simul.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (9,'Se ha procesado con éxito la clasificación de la petición {0}.',NOW(),1,'0008',0,'es',NOW(),'solicitudmuestreo.transform.clasif.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (10,'Ha finalizado con éxito la transformación de la petición {0}.',NOW(),1,'0009',0,'es',NOW(),'solicitudmuestreo.transform.end.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (11,'En la petición {0} no se ha podido clasificar el ítem id {1}.',NOW(),1,'0010',0,'es',NOW(),'solicitudmuestreo.transform.clasif.error');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (12,'En la petición {0} no se ha encontrado clasificador para el ítem id {1}.',NOW(),1,'0011',0,'es',NOW(),'solicitudmuestreo.transform.tipologiaclasif.error');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (13,'En la petición {0} se ha clasificado el item {1}.',NOW(),1,'0012',0,'es',NOW(),'solicitudmuestreo.transform.tipologiaclasif.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (14,'Error al llamar a un microservicio.',NOW(),1,'0013',0,'es',NOW(),'solicitudmuestreo.business.exception.rest.call.error');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (15,'Error al llamar al sdk.',NOW(),1,'0014',0,'es',NOW(),'petmuesinterlocutores.business.exception.sdk.call.error');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (16,'Error al llamar al sdk.',NOW(),1,'0015',0,'es',NOW(),'simulacion.business.exception.sdk.call.error');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (17,'Error al invocar la llamada al SDK.',NOW(),1,'0016',0,'es',NOW(),'simulacion.sdk.call.error');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (18,'Error al procesar el tratamiento de interlocutores comerciales.',NOW(),1,'0200',0,'es',NOW(),'interlocutores.200');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (19,'Se ha creado el interlocutor {0} con código {1}.',NOW(),1,'0201',0,'es',NOW(),'interlocutores.201');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (20,'No se ha podido crear el interlocutor {0} con código {1}.',NOW(),1,'0202',0,'es',NOW(),'interlocutores.202');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (21,'Los datos del interlocutor {0} han sido modificados.',NOW(),1,'0203',0,'es',NOW(),'interlocutores.203');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (22,'Los datos del interlocutor {0} no han podido ser modificados: {1}.',NOW(),1,'0204',0,'es',NOW(),'interlocutores.204');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (23,'Se ha modificado la dirección de correo electrónico del interlocutor {0}.',NOW(),1,'0205',0,'es',NOW(),'interlocutores.205');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (24,'Se ha modificado el teléfono principal del interlocutor {0}.',NOW(),1,'0206',0,'es',NOW(),'interlocutores.206');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (25,'Se ha modificado el teléfono secundario del interlocutor {0}.',NOW(),1,'0207',0,'es',NOW(),'interlocutores.207');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (26,'Se han modificado los datos de identificación del interlocutor {0}.',NOW(),1,'0208',0,'es',NOW(),'interlocutores.208');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (27,'Se ha modificado el identificador fiscal del interlocutor {0}.',NOW(),1,'0209',0,'es',NOW(),'interlocutores.209');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (28,'Se han modificado los datos de dirección del interlocutor {0}',NOW(),1,'0210',0,'es',NOW(),'interlocutores.210');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (29,'No se han podido modificar los datos de dirección del interlocutor {0}: {1}.',NOW(),1,'0211',0,'es',NOW(),'interlocutores.211');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (30,'No se ha podido modificar la dirección de correo electrónico del interlocutor {0}: {1}.',NOW(),1,'0212',0,'es',NOW(),'interlocutores.212');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (31,'No se ha podido modificar  el teléfono principal del interlocutor {0}: {1}.',NOW(),1,'0213',0,'es',NOW(),'interlocutores.213');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (32,'No se ha podido modificar el teléfono secundario del interlocutor {0}: {1}.',NOW(),1,'0214',0,'es',NOW(),'interlocutores.214');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (33,'No se han podido modificar los datos de identificación del interlocutor {0}: {1}.',NOW(),1,'0215',0,'es',NOW(),'interlocutores.215');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (34,'No se ha podido modificar el identificador fiscal del interlocutor {0}: {1}.',NOW(),1,'0216',0,'es',NOW(),'interlocutores.216');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (35,'Interlocutor {0}: {1}.',NOW(),1,'0217',0,'es',NOW(),'interlocutores.217');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (36,'Mensaje con Clave {0} no configurado.',NOW(),1,'0000',0,'es',NOW(),'default');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (37,'Mensaje con Clave {0} no configurado.',NOW(),1,'0000',0,'es',NOW(),'not.found');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (38,'Mensaje con Clave {0} no configurado.',NOW(),1,'0000',0,'es',NOW(),'default.success');

--
-- Facturacion
--
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (718,'Error en validación de pool de facturación.',NOW(),1,'0718',0,'es',NOW(),'facturacion.718');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (719,'Error en validación de pool de facturación.',NOW(),1,'0719',0,'es',NOW(),'facturacion.719');

--
-- Desceuntos Privados
--
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (39,'El descuento informado es superior al importe total de la petición.',NOW(),1,'0039',0,'es',NOW(),'solicitudmuestreo.business.exception.importeDescuento');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (40,'Este descuento solo se puede aplicar en sector Clínica (CL).',NOW(),1,'0040',0,'es',NOW(),'solicitudmuestreo.business.exception.sectorDescuento');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (41,'No existe cargo para este descuento.',NOW(),1,'0041',0,'es',NOW(),'solicitudmuestreo.business.exception.cargoDescuento');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (42,'Es necesario que la petición tenga el precio bruto informado.',NOW(),1,'0042',0,'es',NOW(),'solicitudmuestreo.business.exception.precioBruto');
--
-- Privados
--
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (43,'No se encontro la cuenta de mayor con codigoOperacion: {0} y idMetodoPago: {1}',NOW(),1,'0043',0,'es',NOW(),'privados.business.exception.cuenta.inexistente');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (44,'No se encontro la solicitud de muestreo',NOW(),1,'0044',0,'es',NOW(),'privados.business.exception.solicitud.inexistente');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (45,'No se ha podido determinar el estado de la petición',NOW(),1,'0045',0,'es',NOW(),'privados.business.exception.estado.indeterminado');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (46,'La petición es inválida para la operación a realizar',NOW(),1,'0046',0,'es',NOW(),'privados.business.exception.solicitud.invalida');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (47,'El importe del cobro es inválido; No se admiten cobros parciales',NOW(),1,'0047',0,'es',NOW(),'privados.business.exception.importe.cobro.invalido');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (48,'El importe del anticipo no puede ser 0',NOW(),1,'0048',0,'es',NOW(),'privados.business.exception.importe.anticipo.zero');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (49,'El importe del anticipo no puede ser superior al importe total de la petición;',NOW(),1,'0049',0,'es',NOW(),'privados.business.exception.importe.anticipo.invalido');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (50,'Error al procesar la solicitud.',NOW(),1,'0050',0,'es',NOW(),'solicitudindividual.business.exception.tipoPosicion');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (200,'Han pasado mas de {0} días desde la contabilización.',NOW(),1,'0200',0,'es',NOW(),'privados.business.exception.noadmitidofecha');

-- facturar y cobrar
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (82,'Se ha facturado la petición {0}',NOW(),1,'0082',0,'es',NOW(),'privados.facturar.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (51,'Se ha facturado la petición {0} y se ha contabilizado el cobro Nro.: {1}',NOW(),1,'0051',0,'es',NOW(),'privados.facturarcobrar.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (52,'Se ha facturado la petición {0}. No se pudo contabilizar el cobro, ERROR: {1}',NOW(),1,'0052',0,'es',NOW(),'privados.facturarcobrar.solofacturo.success');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (53,'No es posible contabilizar un cobro para una petición NO Facturada',NOW(),1,'0053',0,'es',NOW(),'privados.business.exception.cobrar.nofacturada');

-- cobrarAnticipo
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (54,'No es posible contabilizar un anticipo para una petición Facturada',NOW(),1,'0054',0,'es',NOW(),'privados.business.exception.cobrarAnticipo.facturada');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (55,'No es posible contabilizar un anticipo para una petición que no es del canal privado (PR)',NOW(),1,'0055',0,'es',NOW(),'privados.business.exception.cobrarAnticipo.noprivado');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (56,'La petición ya tiene un anticipo registrado',NOW(),1,'0056',0,'es',NOW(),'privados.business.exception.cobrarAnticipo.pendiente');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (57,'La petición no admite un nuevo anticipo',NOW(),1,'0057',0,'es',NOW(),'privados.business.exception.cobrarAnticipo.noadmitido');

-- cobrarDeposito
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (58,'No es posible contabilizar un depósito para una petición Facturada',NOW(),1,'0058',0,'es',NOW(),'privados.business.exception.cobrarDeposito.facturada');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (59,'No es posible contabilizar un depósito para una petición del canal privado (PR)',NOW(),1,'0059',0,'es',NOW(),'privados.business.exception.cobrarDeposito.canalpr');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (60,'La petición no aplica para poder contabilizar un depósito.',NOW(),1,'0060',0,'es',NOW(),'privados.business.exception.cobrarDeposito.nodeposito');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (61,'La petición tiene un deposito registrado',NOW(),1,'0061',0,'es',NOW(),'privados.business.exception.cobrarDeposito.pendiente');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (62,'La petición no admite un nuevo depósito',NOW(),1,'0062',0,'es',NOW(),'privados.business.exception.cobrarDeposito.noadmitido');

-- devoluciones
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (63,'No se han registrado depósitos para la petición',NOW(),1,'0063',0,'es',NOW(),'privados.business.exception.devolucion.sin.depositos');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (64,'No se han informado medios de pago para hacer la devolución',NOW(),1,'0064',0,'es',NOW(), 'privados.business.exception.devolucion.mediosPago');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (65,'No se han registrado anticipos para la petición',NOW(),1,'0065',0,'es',NOW(),'privados.business.exception.devolucion.sin.anticipos');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (66,'La petición no tiene importes pendientes a devolver',NOW(),1,'0066',0,'es',NOW(),'privados.business.exception.devolucion.pendientes');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (67,'No se puede devolver un importe mayor al de los anticipos o depósitos',NOW(),1,'0067',0,'es',NOW(),'privados.business.exception.devolucion.importeMayor');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (68,'No se admiten devoluciones parciales',NOW(),1,'0068',0,'es',NOW(),'privados.business.exception.devolucion.parciales');

-- Abonos
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (69,'No se han informado medios de pago para hacer el abono',NOW(),1,'0069',0,'es',NOW(),'privados.business.exception.abono.mediosPago');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (70,'No se encontraron cobros para abonar',NOW(),1,'0070',0,'es',NOW(),'privados.business.exception.abono.nocobros');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (71,'No se puede abonar un importe mayor a lo cobrado',NOW(),1,'0071',0,'es',NOW(),'privados.business.exception.abono.importeMayor');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (72,'No se admiten abonos parciales',NOW(),1,'0072',0,'es',NOW(),'privados.business.exception.abono.parciales');

-- Cobros
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (73,'La peticion tiene un cobro registrado',NOW(),1,'0073',0,'es',NOW(),'privados.business.exception.cobrar.pendiente');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (74,'La peticion no admite un nuevo cobro',NOW(),1,'0074',0,'es',NOW(),'privados.business.exception.cobrar.noadmitido');

INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (75,'La petición se encuentra en estado: {0} y no es compatible con la operación: {1}',NOW(),1,'0075',0,'es',NOW(),'privados.business.exception.estado.incompatible');

INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (76,'La peticion tiene un abono registrado',NOW(),1,'0076',0,'es',NOW(),'privados.business.exception.abono.pendiente');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (77,'La peticion no admite un nuevo abono',NOW(),1,'0077',0,'es',NOW(),'privados.business.exception.abono.noadmitido');

-- GC y alertas
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (78,'La petición pertenece al canal Grandes Cliente y no tiene alerta configurada para ser tratada en el monitor de Privados',NOW(),1,'0078',0,'es',NOW(),'privados.business.exception.alertaPrivados.gcsinalerta');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (79,'La petición tiene informadas las siguientes alertas: {0} y no tienen permitida la operación: {1} en la delegacion.',NOW(),1,'0079',0,'es',NOW(),'privados.business.exception.alertaPrivados.nooperacionconfig');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (80,'La petición tiene informada el alerta: {0} - {1} y no está permitido gestionala desde delegación.',NOW(),1,'0080',0,'es',NOW(),'privados.business.exception.alertaPrivados.nodelegacion');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (81,'La fecha de la petición ha superado el limite de dias para ser gestionada desde delegación.',NOW(),1,'0081',0,'es',NOW(),'privados.business.exception.alertaPrivados.expirada');
	
-- Rectificacion
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (127,'Error en validación previa de rectificación.',NOW(),1,'1004',0,'es',NOW(),'rectificacion.1004');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (128,'Error en validación previa de rectificación.',NOW(),1,'1005',0,'es',NOW(),'rectificacion.1005');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (129,'Error en validación previa de rectificación.',NOW(),1,'1002',0,'es',NOW(),'rectificacion.1002');
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (130,'Error en validación previa de rectificación.',NOW(),1,'1003',0,'es',NOW(),'rectificacion.1003');

-- Cancelacion
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (1011,'Error en validación previa de cancelación.',NOW(),1,'1011',0,'es',NOW(),'cancelacion.1011');

-- Documentos SAP
INSERT INTO "FACTURACION"."T_MESSAGES" VALUES (1500,'No se ha encontrado el tipo de documento SAP.',NOW(),1,'1500',0,'es',NOW(),'documentosSAP.1500');


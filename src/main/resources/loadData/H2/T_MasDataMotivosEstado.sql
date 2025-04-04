INSERT INTO T_MasDataMotivosEstado VALUES (1,1,'ABA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (2,1,'ABC','Petición abonada',NOW(),1,0,NOW(),'La petición ha sido incluida en un proceso de cancelación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (3,1,'ABP','Prueba abonada',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (4,1,'BLKAED','Entidad desconocida',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,6);
INSERT INTO T_MasDataMotivosEstado VALUES (5,1,'BLKAND','Falta código de prueba',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,10);
INSERT INTO T_MasDataMotivosEstado VALUES (6,1,'BLKAFD','Falta documentación',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,2);
INSERT INTO T_MasDataMotivosEstado VALUES (7,1,'BLKAFR','Falta receta',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,9);
INSERT INTO T_MasDataMotivosEstado VALUES (8,1,'BLKAIR','Item Rechazado',NOW(),1,0,NOW(),'La prueba {0} ha sido rechazada.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (9,1,'BLKAPA','Petición ampliable según resultados',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,11);
INSERT INTO T_MasDataMotivosEstado VALUES (10,1,'BLKAPD','Prueba desconocida',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,12);
INSERT INTO T_MasDataMotivosEstado VALUES (11,1,'BLKAPI','Prueba Incongruente',NOW(),1,0,NOW(),'La prueba {0} tiene material incongruente.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (12,1,'BLKASC','Item Sin Concepto',NOW(),1,0,NOW(),'La prueba {0} No tiene concepto asociado.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (13,1,'BLKASP','Prueba Sin Precio',NOW(),1,0,NOW(),'La prueba {0} no ha determinado precio.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (14,1,'BLKMFA','Factura anticipada',NOW(),1,0,NOW(),'La petición se ha realizado por una facturación anticipada.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (15,1,'BLKMZ4','Cortesía',NOW(),1,0,NOW(),'Se ha aplicado un bloqueo de cortesía.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (16,1,'BLKMZM','Carta compromiso de pago',NOW(),1,0,NOW(),'Se ha recibido una carta de compromiso de pago.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (17,1,'BLKMZN','Peticiones antiguas',NOW(),1,0,NOW(),'La peticion es antigua y ya no se puede facturar.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (18,1,'BLKMZX','Bloqueo fuera de periodo',NOW(),1,0,NOW(),'Se ha bloqueado por estar fuera del periodo de facturación.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (19,1,'BLKMZY','S/Cond para facturar',NOW(),1,0,NOW(),'La peticion no cumple las condiciones para ser facturada.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (20,1,'COA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (21,1,'COC','Petición cobrada.',NOW(),1,0,NOW(),'La petición ha sido cobrada.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (22,1,'COP','Prueba cobrada.',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (23,1,'CRA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (24,1,'CRC','La petición ha sido creada.',NOW(),1,0,NOW(),'Se ha recibido la petición desde los sistemas de laboratorio.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (25,1,'CRP','La prueba ha sido creada.',NOW(),1,0,NOW(),'Se ha recibido la prueba desde los sistemas de laboratorio.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (26,1,'DELA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (27,1,'DELC','Petición eliminada',NOW(),1,0,NOW(),'La petición ha sido eliminada en los sistemas de laboratorio.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (28,1,'DELP','Prueba eliminada',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (29,1,'ERRA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (30,1,'ERRC','Petición errónea.',NOW(),1,0,NOW(),'La petición contiene errores.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (31,1,'ERRP','Prueba errónea.',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (32,1,'ERRSIM','Pendiente configuracion SAP',NOW(),1,0,NOW(),'La prueba {0} no ha determinado precio falta configurar precio en SAP.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (33,1,'EXA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (34,1,'EXC','Todas las pruebas de la petición son exluidas',NOW(),1,0,NOW(),'Toda las pruebas de la petición han sido excluidas del proceso de facturación.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (35,1,'EXIN','La prueba ha sido exlcuida por ser incongruente.',NOW(),1,0,NOW(),'La prueba es incongruentes. Existe un material en la petición que la convierte en excluida.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (36,3,'EXMA','La prueba se ha excluido manualmente.',NOW(),1,0,NOW(),'La prueba se ha excluido por acción del usuario.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (37,2,'EXNF','La prueba ha se ha establecido como no facturable.',NOW(),1,0,NOW(),'La prueba es no facturable por la configuración.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (38,1,'FA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (39,1,'FBA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (40,1,'FBC','Todas las pruebas de la petición son facturables',NOW(),1,0,NOW(),'Todas las pruebas de la petición son facturables.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (41,1,'FC','Petición facturada.',NOW(),1,0,NOW(),'La petición ha sido incluida en un proceso de facturación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (42,1,'FNA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (43,1,'FNC','Petición en proceso de facturación.',NOW(),1,0,NOW(),'La petición se encuentra actualmente en un proceso de facturación.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (44,1,'FNP','Prueba en proceso de facturación.',NOW(),1,0,NOW(),'La prueba se encuentra actualmente en un proceso de facturación.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (45,1,'FP','Prueba facturada.',NOW(),1,0,NOW(),'La prueba ha sido incluida en un proceso de facturación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (46,1,'FBPFP','Prueba facturable por fecha de petición.',NOW(),1,0,NOW(),'La prueba aplica a la configuración de los clientes que facturan por fecha de petición',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (47,1,'FBPVA','Prueba facturable por estar validada.',NOW(),1,0,NOW(),'La prueba es facturable por haber sido validada el día {0}.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (48,1,'LIQA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (49,1,'LIQC','Petición liquidada',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (50,1,'LIQP','Prueba liquidada',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (51,1,'PAA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (52,1,'PAC','Petición abonada.',NOW(),1,0,NOW(),'La petición ha sido incluida en un proceso de cancelación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (53,1,'PAP','Prueba abonada.',NOW(),1,0,NOW(),'La prueba ha sido incluida en un proceso de cancelación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (54,1,'PCA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (55,1,'PCC','Petición incluida en un pedido de ventas.',NOW(),1,0,NOW(),'La petición ha sido incluida en un pedido de ventas. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (56,1,'PCOA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (57,1,'PCOC','Petición parcialmente cobrada',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (58,1,'PCOP','Prueba parcialmente cobrada',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (59,1,'PCP','Prueba incluida en un pedido de ventas.',NOW(),1,0,NOW(),'La prueba ha sido incluida en un pedido de ventas. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (60,1,'PFA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (61,1,'PFC','Petición pre-facturada.',NOW(),1,0,NOW(),'La petición ha sido incluida en un proceso de pre-facturación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (62,1,'PFP','Prueba pre-facturada.',NOW(),1,0,NOW(),'La prueba ha sido incluida en un proceso de pre-facturación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (63,1,'RA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (64,1,'RC','Petición rectificada.',NOW(),1,0,NOW(),'La petición ha sido incluida en un proceso de rectificación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (65,1,'RP','Prueba rectificada.',NOW(),1,0,NOW(),'La prueba ha sido incluida en un proceso de rectificación. Revise la trazabilidad.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (66,1,'SEA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (67,1,'SEC','No se ha establecido un estado a la petición.',NOW(),1,0,NOW(),'No contiene estado.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (68,1,'SEP','No se ha establecido un estado a la prueba.',NOW(),1,0,NOW(),'No contiene estado.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (69,1,'VAA','',NOW(),1,0,NOW(),'',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (70,1,'VAC','Todas las pruebas de la petición han sido validadas.',NOW(),1,0,NOW(),'Se ha recibido la autorización / validación desde los sistemas de laboratorio.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (71,1,'VAP','Prueba validada.',NOW(),1,0,NOW(),'Se ha recibido la autorización / validación desde los sistemas de laboratorio.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (72,1,'ERRZFI','Prueba en puntos y sin contrato',NOW(),1,0,NOW(),'La prueba {0} está en puntos y no se ha determinado un contrato.',NULL,NULL,NULL);
INSERT INTO T_MasDataMotivosEstado VALUES (73,1,'BLKAFA','Falta autorización',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,1);
INSERT INTO T_MasDataMotivosEstado VALUES (74,1,'BLKAFP','Falta de pago',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,3);
INSERT INTO T_MasDataMotivosEstado VALUES (75,1,'BLKABP','Bloquear petición',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,4);
INSERT INTO T_MasDataMotivosEstado VALUES (76,1,'BLKADP','Faltan datos personales',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,5);
INSERT INTO T_MasDataMotivosEstado VALUES (77,1,'BLKAEX','Cargos extras facturacion erróneos',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,7);
INSERT INTO T_MasDataMotivosEstado VALUES (78,1,'BLKAFN','Falta edad y/o fecha de nacimiento',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,8);
INSERT INTO T_MasDataMotivosEstado VALUES (79,1,'BLKAPESO','Peso obligatorio',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,13);
INSERT INTO T_MasDataMotivosEstado VALUES (80,1,'BLKAFM','Falta muestra',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,14);
INSERT INTO T_MasDataMotivosEstado VALUES (81,1,'BLKACFT','Falta ttub',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,15);
INSERT INTO T_MasDataMotivosEstado VALUES (82,1,'BLKACMI','Muestra insuficiente',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,16);
INSERT INTO T_MasDataMotivosEstado VALUES (83,1,'BLKAFMP','Falta muestra en perfil',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,17);
INSERT INTO T_MasDataMotivosEstado VALUES (84,1,'BLKANCD','Muestra no conforme drogas',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,18);
INSERT INTO T_MasDataMotivosEstado VALUES (85,1,'BLKANER','No entregable resultado',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,19);
INSERT INTO T_MasDataMotivosEstado VALUES (86,1,'BLKANM','Nueva Muestra',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',NULL,NULL,20);
INSERT INTO T_MasDataMotivosEstado VALUES (87,1,'BLKA','Tiene alertas',NOW(),1,0,NOW(),'La petición se encuentra bloqueada por contener alertas informadas desde el sistema productivo.',NULL,NULL,NULL);

INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (1,1,'','Pendiente configuracion SAP',NOW(),1,0,NOW(),'La prueba {0} no ha determinado precio, falta configurar precio en SAP.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (2,1,'','La peticion tiene pruebas No validadas, o han sido determinadas como incongruentes o rechazadas.',NOW(),1,0,NOW(),'La peticion {0} contiene pruebas incongruentes o al menos alguna prueba sin Validar',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (3,1,'','Determinado por configuracion SAP',NOW(),1,0,NOW(),'La prueba {0} es NO FACTURABLE - Motivo: {1} en SAP.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (4,1,'','Peticion No Validada',NOW(),1,0,NOW(),'La peticion tiene al menos un prueba No validada.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (5,1,'','Prueba No Validada',NOW(),1,0,NOW(),'La prueba {0} no tiene informada su fecha de validación.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (6,1,'','Falta documentación',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',:2);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (7,1,'','Entidad desconocida',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',:6);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (8,1,'','Falta receta',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',:9);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (9,1,'','Falta código de prueba',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',:10);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (10,1,'','Petición ampliable según resultados',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',:11);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (11,1,'','Prueba desconocida',NOW(),1,0,NOW(),'La prueba {0} tiene definida alerta {1} por {2}',:12);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (12,1,'','Item Rechazado',NOW(),1,0,NOW(),'La prueba {0} ha sido rechazada.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (13,1,'','Item Sin Concepto',NOW(),1,0,NOW(),'La prueba {0} No tiene concepto asociado.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (14,1,'','Prueba Incongruente',NOW(),1,0,NOW(),'La prueba {0} tiene material incongruente.',NULL);
INSERT INTO "FACTURACION"."T_MASDATAMOTIVOSESTADO" VALUES (15,1,'','Prueba Sin Precio',NOW(),1,0,NOW(),'La prueba {0} no ha determinado precio.',NULL);
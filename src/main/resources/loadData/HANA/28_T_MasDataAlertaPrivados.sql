DO BEGIN
    INSERT INTO "FACTURACION"."T_MASDATAALERTAPRIVADOS" (ID,active,CODIGOOPERACION,entity_creation_timestamp,entity_version,inactive,last_updated_timestamp) VALUES (1, 1,'Facturar', NOW(), 1, 0, NOW());
    INSERT INTO "FACTURACION"."T_MASDATAALERTAPRIVADOS" (ID,active,CODIGOOPERACION,entity_creation_timestamp,entity_version,inactive,last_updated_timestamp) VALUES (2, 1, 'Cobrar', NOW(), 1, 0, NOW());
    INSERT INTO "FACTURACION"."T_MASDATAALERTAPRIVADOS" (ID,active,CODIGOOPERACION,entity_creation_timestamp,entity_version,inactive,last_updated_timestamp) VALUES (3, 1, 'Rectificar', NOW(), 1, 0, NOW());
    INSERT INTO "FACTURACION"."T_MASDATAALERTAPRIVADOS" (ID,active,CODIGOOPERACION,entity_creation_timestamp,entity_version,inactive,last_updated_timestamp) VALUES (4, 1, 'Abonar', NOW(), 1, 0, NOW());
    INSERT INTO "FACTURACION"."T_MASDATAALERTAPRIVADOS" (ID,active,CODIGOOPERACION,entity_creation_timestamp,entity_version,inactive,last_updated_timestamp) VALUES (5, 1, 'Devolver', NOW(), 1, 0, NOW());
END
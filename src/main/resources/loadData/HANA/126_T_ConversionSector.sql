DO BEGIN
	TRUNCATE TABLE "FACTURACION"."T_CONVERSIONSECTOR";
	
	INSERT INTO "FACTURACION"."T_CONVERSIONSECTOR" (ID, entity_creation_timestamp, entity_version, last_updated_timestamp, SECTOR, TIPOPETICION) VALUES (1,NOW(), 1, NOW(), 'CL', 1);
	INSERT INTO "FACTURACION"."T_CONVERSIONSECTOR" (ID, entity_creation_timestamp, entity_version, last_updated_timestamp, SECTOR, TIPOPETICION) VALUES (2,NOW(), 1, NOW(), 'CL', 2);
	INSERT INTO "FACTURACION"."T_CONVERSIONSECTOR" (ID, entity_creation_timestamp, entity_version, last_updated_timestamp, SECTOR, TIPOPETICION) VALUES (3,NOW(), 1, NOW(), 'CL', 3);
	INSERT INTO "FACTURACION"."T_CONVERSIONSECTOR" (ID, entity_creation_timestamp, entity_version, last_updated_timestamp, SECTOR, TIPOPETICION) VALUES (4,NOW(), 1, NOW(), 'AP', 4);
	INSERT INTO "FACTURACION"."T_CONVERSIONSECTOR" (ID, entity_creation_timestamp, entity_version, last_updated_timestamp, SECTOR, TIPOPETICION) VALUES (5,NOW(), 1, NOW(), 'IN', 5);
	INSERT INTO "FACTURACION"."T_CONVERSIONSECTOR" (ID, entity_creation_timestamp, entity_version, last_updated_timestamp, SECTOR, TIPOPETICION) VALUES (6,NOW(), 1, NOW(), 'AV', 6);
END
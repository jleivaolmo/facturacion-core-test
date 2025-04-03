DO BEGIN

	-- All Dates Times
	DECLARE fecha TIMESTAMP;
	SELECT TO_NVARCHAR(NOW(),'YYYY-MM-DD HH24:MI:SS') INTO fecha FROM DUMMY;

--  ANTICIPO  (2) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (1, 1, 'ANTICIPO', 2, '0001', '57000001', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (2, 1, 'ANTICIPO', 2, '0002', '57000002', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (3, 1, 'ANTICIPO', 2, '0003', '57000003', '43800001', :fecha, 0, 0, :fecha);

--  ANTICIPO (2) - tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (5, 1, 'ANTICIPO', 1, '0001', '43100001', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (6, 1, 'ANTICIPO', 1, '0002', '43100002', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (7, 1, 'ANTICIPO', 1, '0003', '43100003', '43800001', :fecha, 0, 0, :fecha);

--  ANTICIPO (2) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (9, 1, 'ANTICIPO', 3, '0001', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (10, 1, 'ANTICIPO', 3, '0002', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (11, 1, 'ANTICIPO', 3, '0003', '57200111', '43800001', :fecha, 0, 0, :fecha);

--  ANTICIPO (2) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (13, 1, 'ANTICIPO', 4, '0001', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (14, 1, 'ANTICIPO', 4, '0002', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (15, 1, 'ANTICIPO', 4, '0003', '57200111', '43800001', :fecha, 0, 0, :fecha);

--  ANTICIPO (2) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (17, 1, 'ANTICIPO', 5, '0001', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (18, 1, 'ANTICIPO', 5, '0002', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (19, 1, 'ANTICIPO', 5, '0003', '57200111', '43800001', :fecha, 0, 0, :fecha);

---------------------------------------------------------------------------------
--  DEVOLVER_ANTICIPO (5) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (21, 1, 'DEV_ANT', 2, '0001', '43800001', '57000001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (22, 1, 'DEV_ANT', 2, '0002', '43800001', '57000002', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (23, 1, 'DEV_ANT', 2, '0003', '43800001', '57000003', :fecha, 0, 0, :fecha);

--  DEVOLVER_ANTICIPO (5) - Tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (25, 1, 'DEV_ANT', 1, '0001', '43800001', '43100001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (26, 1, 'DEV_ANT', 1, '0002', '43800001', '43100002', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (27, 1, 'DEV_ANT', 1, '0003', '43800001', '43100003', :fecha, 0, 0, :fecha);

--  DEVOLVER_ANTICIPO (5) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (29, 1, 'DEV_ANT', 3, '0001', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (30, 1, 'DEV_ANT', 3, '0002', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (31, 1, 'DEV_ANT', 3, '0003', '43800001', '57200111', :fecha, 0, 0, :fecha);

--  DEVOLVER_ANTICIPO (5) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (33, 1, 'DEV_ANT', 4, '0001', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (34, 1, 'DEV_ANT', 4, '0002', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (35, 1, 'DEV_ANT', 4, '0003', '43800001', '57200111', :fecha, 0, 0, :fecha);

--  DEVOLVER_ANTICIPO (5) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (37, 1, 'DEV_ANT', 5, '0001', '43800001', '43100000', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (38, 1, 'DEV_ANT', 5, '0002', '43800001', '43100000', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (39, 1, 'DEV_ANT', 5, '0003', '43800001', '43100000', :fecha, 0, 0, :fecha);

---------------------------------------------------------------------------------
--  DEPOSITO (3) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (41, 1, 'DEPOSITO', 2, '0001', '57000001', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (42, 1, 'DEPOSITO', 2, '0002', '57000002', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (43, 1, 'DEPOSITO', 2, '0003', '57000003', '43800001', :fecha, 0, 0, :fecha);

--  DEPOSITO (3) - Tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (45, 1, 'DEPOSITO', 1, '0001', '43100001', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (46, 1, 'DEPOSITO', 1, '0002', '43100002', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (47, 1, 'DEPOSITO', 1, '0003', '43100003', '43800001', :fecha, 0, 0, :fecha);

--  DEPOSITO (3) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (49, 1, 'DEPOSITO', 3, '0001', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (50, 1, 'DEPOSITO', 3, '0002', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (51, 1, 'DEPOSITO', 3, '0003', '57200111', '43800001', :fecha, 0, 0, :fecha);

--  DEPOSITO (3) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (53, 1, 'DEPOSITO', 4, '0001', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (54, 1, 'DEPOSITO', 4, '0002', '57200111', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (55, 1, 'DEPOSITO', 4, '0003', '57200111', '43800001', :fecha, 0, 0, :fecha);

--  DEPOSITO (3) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (57, 1, 'DEPOSITO', 5, '0001', '43100000', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (58, 1, 'DEPOSITO', 5, '0002', '43100000', '43800001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (59, 1, 'DEPOSITO', 5, '0003', '43100000', '43800001', :fecha, 0, 0, :fecha);

---------------------------------------------------------------------------------
--  DEVOLVER DEPOSITO (6) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (61, 1, 'DEV_DEP', 2, '0001', '43800001', '57000001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (62, 1, 'DEV_DEP', 2, '0002', '43800001', '57000002', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (63, 1, 'DEV_DEP', 2, '0003', '43800001', '57000003', :fecha, 0, 0, :fecha);

--  DEVOLVER DEPOSITO (6) - Tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (65, 1, 'DEV_DEP', 1, '0001', '43800001', '43100001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (66, 1, 'DEV_DEP', 1, '0002', '43800001', '43100002', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (67, 1, 'DEV_DEP', 1, '0003', '43800001', '43100003', :fecha, 0, 0, :fecha);

--  DEVOLVER DEPOSITO (6) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (69, 1, 'DEV_DEP', 3, '0001', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (70, 1, 'DEV_DEP', 3, '0002', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (71, 1, 'DEV_DEP', 3, '0003', '43800001', '57200111', :fecha, 0, 0, :fecha);

--  DEVOLVER DEPOSITO (6) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (73, 1, 'DEV_DEP', 4, '0001', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (74, 1, 'DEV_DEP', 4, '0002', '43800001', '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (75, 1, 'DEV_DEP', 4, '0003', '43800001', '57200111', :fecha, 0, 0, :fecha);

--  DEVOLVER DEPOSITO (6) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (77, 1, 'DEV_DEP', 5, '0001', '43800001', '43100000', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (78, 1, 'DEV_DEP', 5, '0002', '43800001', '43100000', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (79, 1, 'DEV_DEP', 5, '0003', '43800001', '43100000', :fecha, 0, 0, :fecha);

---------------------------------------------------------------------------------
--  Cobro factura sin anticipos (1) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (81, 1, 'COBRO', 2, '0001', '57000001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (82, 1, 'COBRO', 2, '0002', '57000002', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (83, 1, 'COBRO', 2, '0003', '57000003', null, :fecha, 0, 0, :fecha);

--  Cobro factura sin anticipos (1) - Tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (85, 1, 'COBRO', 1, '0001', '43100001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (86, 1, 'COBRO', 1, '0002', '43100002', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (87, 1, 'COBRO', 1, '0003', '43100003', null, :fecha, 0, 0, :fecha);

--  Cobro factura sin anticipos (1) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (89, 1, 'COBRO', 3, '0001', '57200111', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (90, 1, 'COBRO', 3, '0002', '57200111', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (91, 1, 'COBRO', 3, '0003', '57200111', null, :fecha, 0, 0, :fecha);

--  Cobro factura sin anticipos (1) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (93, 1, 'COBRO', 4, '0001', '57200111', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (94, 1, 'COBRO', 4, '0002', '57200111', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (95, 1, 'COBRO', 4, '0003', '57200111', null, :fecha, 0, 0, :fecha);

--  Cobro factura sin anticipos (1) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (97, 1, 'COBRO', 5, '0001', '43100000', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (98, 1, 'COBRO', 5, '0002', '43100000', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (99, 1, 'COBRO', 5, '0003', '43100000', null, :fecha, 0, 0, :fecha);

---------------------------------------------------------------------------------
--  Cobro factura con anticipos (7) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (101, 1, 'COBRO_CON_ANT', 2, '0001', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (102, 1, 'COBRO_CON_ANT', 2, '0002', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (103, 1, 'COBRO_CON_ANT', 2, '0003', '43800001', null, :fecha, 0, 0, :fecha);

--  Cobro factura con anticipos (7) - Tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (104, 1, 'COBRO_CON_ANT', 1, '0001', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (105, 1, 'COBRO_CON_ANT', 1, '0002', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (106, 1, 'COBRO_CON_ANT', 1, '0003', '43800001', null, :fecha, 0, 0, :fecha);

--  Cobro factura con anticipos (7) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (107, 1, 'COBRO_CON_ANT', 3, '0001', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (108, 1, 'COBRO_CON_ANT', 3, '0002', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (109, 1, 'COBRO_CON_ANT', 3, '0003', '43800001', null, :fecha, 0, 0, :fecha);

--  Cobro factura con anticipos (7) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (110, 1, 'COBRO_CON_ANT', 4, '0001', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (111, 1, 'COBRO_CON_ANT', 4, '0002', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (112, 1, 'COBRO_CON_ANT', 4, '0003', '43800001', null, :fecha, 0, 0, :fecha);

--  Cobro factura con anticipos (7) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (113, 1, 'COBRO_CON_ANT', 5, '0001', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (114, 1, 'COBRO_CON_ANT', 5, '0002', '43800001', null, :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (115, 1, 'COBRO_CON_ANT', 5, '0003', '43800001', null, :fecha, 0, 0, :fecha);

---------------------------------------------------------------------------------
--  Abono (4) - EFECTIVO (2)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (116, 1, 'ABONO', 2, '0001', null, '57000001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (117, 1, 'ABONO', 2, '0002', null, '57000002', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (118, 1, 'ABONO', 2, '0003', null, '57000003', :fecha, 0, 0, :fecha);

--  Abono (4) - Tarjeta (1)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (119, 1, 'ABONO', 1, '0001', null, '43100001', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (120, 1, 'ABONO', 1, '0002', null, '43100002', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (121, 1, 'ABONO', 1, '0003', null, '43100003', :fecha, 0, 0, :fecha);

--  Abono (4) - cheque (3)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (122, 1, 'ABONO', 3, '0001', null, '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (123, 1, 'ABONO', 3, '0002', null, '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (124, 1, 'ABONO', 3, '0003', null, '57200111', :fecha, 0, 0, :fecha);

--  Abono (4) - transferencia (4)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (125, 1, 'ABONO', 4, '0001', null, '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (126, 1, 'ABONO', 4, '0002', null, '57200111', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (127, 1, 'ABONO', 4, '0003', null, '57200111', :fecha, 0, 0, :fecha);

--  Abono (4) - ammex (5)
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (128, 1, 'ABONO', 5, '0001', null, '43100000', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (129, 1, 'ABONO', 5, '0002', null, '43100000', :fecha, 0, 0, :fecha);
    INSERT INTO "FACTURACION"."T_MASDATACUENTASMAYOR"
    (ID, active, CODIGOOPERACION, IDMETODOPAGO, CODIGODELEGACION, CUENTADEBE, CUENTAHABER, entity_creation_timestamp, entity_version, inactive, last_updated_timestamp)
    VALUES (130, 1, 'ABONO', 5, '0003', null, '43100000', :fecha, 0, 0, :fecha);

END
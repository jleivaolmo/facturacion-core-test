DO BEGIN
	-- All Dates Times
	DECLARE fecha TIMESTAMP;
	SELECT TO_NVARCHAR(NOW(),'YYYY-MM-DD HH24:MI:SS') INTO fecha FROM DUMMY;

    INSERT INTO T_MasDataMetodoPago (ID, active, CODIGO, DESCRIPCION, entity_creation_timestamp, entity_version,inactive, last_updated_timestamp, orden) VALUES (1 , 1, 'TC', 'Tarjeta de credito', :fecha, 1, 0, :fecha, '1');
	INSERT INTO T_MasDataMetodoPago (ID, active, CODIGO, DESCRIPCION, entity_creation_timestamp, entity_version,inactive, last_updated_timestamp, orden) VALUES (2 , 1, 'EF', 'Efectivo', :fecha, 1, 0, :fecha, '2');
    INSERT INTO T_MasDataMetodoPago (ID, active, CODIGO, DESCRIPCION, entity_creation_timestamp, entity_version,inactive, last_updated_timestamp, orden) VALUES (3 , 1, 'CH', 'Cheque', :fecha, 1, 0, :fecha, '3');
    INSERT INTO T_MasDataMetodoPago (ID, active, CODIGO, DESCRIPCION, entity_creation_timestamp, entity_version,inactive, last_updated_timestamp, orden) VALUES (4 , 1, 'TR', 'Transferencia', :fecha, 1, 0, :fecha, '4');
END
DO BEGIN 
	-- All Dates Times
	DECLARE fecha TIMESTAMP;
	SELECT TO_NVARCHAR(NOW(),'YYYY-MM-DD HH24:MI:SS') INTO fecha FROM DUMMY;
	
	TRUNCATE TABLE "FACTURACION"."T_CONVERSIONORGANIZACIONVENTAS";

	INSERT INTO "FACTURACION"."T_CONVERSIONORGANIZACIONVENTAS" (ID,CODIGODELEGACION, entity_creation_timestamp, entity_version, last_updated_timestamp, ORGANIZACIONVENTAS,SECTOR)
	VALUES(1,'*', :fecha, 1, :fecha, 'ECHE', '*');
END
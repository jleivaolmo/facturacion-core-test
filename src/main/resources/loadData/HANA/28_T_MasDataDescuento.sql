DO BEGIN
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (1,1,'==','ZDPS','Importe fijo',NOW(),1,0,NOW(),0,0);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (2,1,'D2','ZDPR','Médico Colaborador',NOW(),1,0,NOW(),1,50);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (3,1,'D3','ZDPR','Farmacéuticos',NOW(),1,0,NOW(),1,40);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (4,1,'D5','ZDPR','10% Imp. Bruto > 450€',NOW(),1,0,NOW(),1,10);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (5,1,'D6','ZDPR','Familia no directa',NOW(),1,0,NOW(),1,50);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (6,1,'D7','ZDPR','Ex-Empleados',NOW(),1,0,NOW(),1,50);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (7,1,'P4','ZDPR','5% Imp. Bruto > 300€',NOW(),1,0,NOW(),1,5);
 	INSERT INTO "FACTURACION"."T_MASDATADESCUENTO" VALUES (8,1,'P5','ZDPR','15% Imp. Bruto > 600€',NOW(),1,0,NOW(),1,15);
END

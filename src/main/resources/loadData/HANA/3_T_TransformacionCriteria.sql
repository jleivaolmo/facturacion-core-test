--
-- Header of SolicitudIndividual
--

-- DistributionChannel
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (001,NOW(),1,0,NOW(),'EQ','sm.peticion.esPrivado','false',008);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (002,NOW(),1,0,NOW(),'EQ','sm.peticion.esPrivado','true',009);

-- SoldToParty
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (010,NOW(), 1,0, NOW(), 'EQ', 'sm.peticion.esPrivado', 'false', 006);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (011,NOW(), 1,0, NOW(), 'EQ', 'sm.peticion.esPrivado', 'true',  007);

--
-- Items of SolicitudIndividual
--

-- Presupuesto
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (020,NOW(), 1,0, NOW(), 'EQ', 'sm.peticion.esPresupuesto', 'true', 052);

-- Perfiles (Item superior)
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (030,NOW(), 1,0, NOW(), 'EQ', 'sm_items.esPerfil', 'false', 047);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (031,NOW(), 1,0, NOW(), 'NEQ', 'sm_items.idParent', '0', 047);

--
-- Business Role
--
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (035,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 180);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (036,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 181);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (037,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 182);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (038,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 183);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (039,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZM', 184);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (040,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 185);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (041,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 186);

-- Contiene NIF mapeamos el tax
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (045,NOW(), 1,0, NOW(), 'NEQ', 'ic.nifInterlocutor', '', 201);

-- Tipo de TAX para España
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (046,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '1', 251);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (047,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '2', 252);

-- Contiene identificacion
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (048,NOW(), 1,0, NOW(), 'NEQ', 'ic.codigoIdentificacion', '', 203);

--
-- Tipo de BP (Personas)
--
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (050,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 101);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (051,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 111);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (052,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 121);

-- First Name
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (060,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 102);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (061,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 112);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (062,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 122);

-- Last Name
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (070,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 103);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (071,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 113);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (072,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 123);

-- Additional Last Name
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (080,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 104);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (081,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 114);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (082,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 124);

-- Tratamiento
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (090,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 105);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (091,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 115);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (092,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 125);

-- Titulo academico
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (100,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZP', 106);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (101,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZH', 116);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (102,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZV', 126);

--
-- Tipo de BP (Organización)
--
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (200,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 130);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (201,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 140);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (202,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 150);

-- Name 1 (Organizational)
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (210,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 131);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (211,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 141);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (212,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 151);

-- Name 2 (Organizational)
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (220,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 132);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (221,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 142);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (222,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 152);

-- Name 3 (Organizational)
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (230,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 133);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (231,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 143);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (232,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 153);

-- Name 4 (Organizational)
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (240,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 134);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (241,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 144);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (242,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 154);

-- Tratamiento
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (250,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZR', 135);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (251,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZC', 145);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (252,NOW(), 1,0, NOW(), 'EQ', 'ic.rolInterlocutor', 'ZE', 155);

--
-- Direcciones
--
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (270,NOW(), 1,0, NOW(), 'NEQ', 'ic.emailInterlocutor', '', 246);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (271,NOW(), 1,0, NOW(), 'NEQ', 'ic.telefono2Interlocutor', '', 247);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (272,NOW(), 1,0, NOW(), 'NEQ', 'ic.telefono1Interlocutor', '', 248);

--
-- Tipo de identificacion
--
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (300,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '3', 270);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (301,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '4', 271);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (302,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '5', 272);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (303,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '6', 273);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (304,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '7', 274);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (305,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '8', 275);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (306,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '1', 276);
INSERT INTO "FACTURACION"."T_TRANSFORMACIONCRITERIA" VALUES (307,NOW(), 1,0, NOW(), 'EQ', 'ic.tipoIdentificacion', '2', 277);
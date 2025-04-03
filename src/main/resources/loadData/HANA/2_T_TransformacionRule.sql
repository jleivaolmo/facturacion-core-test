DO BEGIN
	
	-- *******************************************
	-- Solicitud Individual Rules
	-- *******************************************
	
	--
	-- Header of Solicitud Individual
	--
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (001,NOW(), 1, 10, 0, NOW(), 'si.salesOrderType', 'ZSIM', 'VALUE', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (002,NOW(), 1, 20, 0, NOW(), 'si.salesOffice', 'getSalesOffice', 'CALL', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (003,NOW(), 1, 40, 0, NOW(), 'si.purchaseOrderByCustomer', 'sm.peticion.codigoPeticion', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (004,NOW(), 1, 50, 0, NOW(), 'si.salesOrderDate', 'sm.peticion.fechas.fechaPeticion', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (005,NOW(), 1, 30, 0, NOW(), 'si.pricingDate', 'sm.peticion.fechas.fechaPeticion', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (006,NOW(), 1, 60, 0, NOW(), 'si.soldToParty', 'getSAPCustomerID', 'CALL', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (007,NOW(), 1, 61, 0, NOW(), 'si.soldToParty', '"CPD" + sm.codigoOficinaVentas', 'JEXL', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (008,NOW(), 1, 70, 0, NOW(), 'si.distributionChannel', 'GC', 'VALUE', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (009,NOW(), 1, 71, 0, NOW(), 'si.distributionChannel', 'PR', 'VALUE', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (010,NOW(), 1, 80, 0, NOW(), 'si.organizationDivision', 'getOrganizationDivision', 'CALL', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (011,NOW(), 1, 90, 0, NOW(), 'si.salesOrganization', 'getSalesOrganization', 'CALL', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (012,NOW(), 1, 100, 0, NOW(), 'si.codigoPoliza', 'sm.peticion.paciente.codigoPoliza', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (013,NOW(), 1, 101, 0, NOW(), 'si.documentoUnico', 'sm.peticion.paciente.codigoDocumento', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (014,NOW(), 1, 102, 0, NOW(), 'si.servicioConcertado', 'sm.peticion.clinicos.codigoServicio', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (015,NOW(), 1, 103, 0, NOW(), 'si.codigoOperacion', 'sm.peticion.operacion.codigoOperacion', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (016,NOW(), 1, 104, 0, NOW(), 'si.tipoPeticion', 'sm.peticion.tipoPeticion', 'FIELD', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (017,NOW(), 1, 1001, 0,NOW(), '', 'getPruebaIncongruente', 'CALL', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));

	--
	-- Items of Solicitud Individual
	--
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (040,NOW(), 1, 500, 0,NOW(), 'si.items', 'PeticionMuestreoItems-SolIndItems(sm.peticion.pruebas)', 'RULE', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (041,NOW(), 1, 510, 0,NOW(), 'si_items.requestedQuantity', 'sm_items.cantidadRequerida', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (042,NOW(), 1, 520, 0,NOW(), 'si_items.salesOrderItemCategory', 'getSalesOrderItemCategory', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (043,NOW(), 1, 521, 0,NOW(), 'si_items.salesOrderItemCategory', NULL, 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (044,NOW(), 1, 530, 0,NOW(), 'si_items.fechaValidacion', 'getFechaValidacion', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (045,NOW(), 1, 540, 0,NOW(), 'si_items.material', 'sm_items.codigoMaterial', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (046,NOW(), 1, 550, 0,NOW(), 'si_items.salesOrderIndItem', 'sm_items.idItem', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (047,NOW(), 1, 560, 0,NOW(), 'si_items.higherLevelltem', 'sm_items.idParent', 'FIELD', 2);
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (048,NOW(), 1, 570, 0,NOW(), 'si_items.delProductiva', 'getDelegacionProductiva', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	-- INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (049,NOW(), 1, 580, 0,NOW(), 'si_items.priceReferenceMaterial', 'getConceptoFacturacion', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (049,NOW(), 1, 580, 0,NOW(), 'si_items.priceReferenceMaterial', 'sm_items.codigoMaterialFacturacion', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (050,NOW(), 1, 590, 0,NOW(), 'si_items.unidadProductiva', 'getUnidadProductiva', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (051,NOW(), 1, 600, 0,NOW(), 'si_items.productionPlant', 'getItemPlant', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (052,NOW(), 1, 699, 0,NOW(), '', 'createPresupuestoPricing', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (053,NOW(), 1, 650, 0,NOW(), 'si_items.solicitudInd', 'si', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PeticionMuestreoItems-SolIndItems'));

	--
	-- Partners of Solicitud Individual
	--
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (080,NOW(), 1, 700, 0,NOW(), 'si.partners', 'PetMuesInterlocutores-SolIndPartner(sm.peticion.interlocutores)', 'RULE', (SELECT ID  FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'SolicitudMuestreo-SolicitudIndividual'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (081,NOW(), 1, 710, 0,NOW(), 'si_partner.partnerFunction', 'sm_interlocutor.rolInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-SolIndPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (082,NOW(), 1, 720, 0,NOW(), 'si_partner.customer', 'createCustomerKey', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-SolIndPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (083,NOW(), 1, 799, 0,NOW(), 'si_partner.solicitudInd', 'si', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-SolIndPartner'));

	-- ******************************************
	-- Business Partner Rules
	-- ******************************************
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (100,NOW(), 1, 10, 0, NOW(), 'bp.customer', 'createCustomerKey', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	-- Para el caso de personas
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (101,NOW(), 1, 20, 0, NOW(), 'bp.businessPartnerCategory', '1', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (102,NOW(), 1, 21, 0, NOW(), 'bp.firstName', 'ic.nombreInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (103,NOW(), 1, 22, 0, NOW(), 'bp.lastName', 'ic.primApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (104,NOW(), 1, 23, 0, NOW(), 'bp.additionalLastName', 'ic.segApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (105,NOW(), 1, 24, 0, NOW(), 'bp.formOfAddress', '', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (106,NOW(), 1, 25, 0, NOW(), 'bp.academicTitle', '0001', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (111,NOW(), 1, 30, 0, NOW(), 'bp.businessPartnerCategory', '1', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (112,NOW(), 1, 31, 0, NOW(), 'bp.firstName', 'ic.nombreInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (113,NOW(), 1, 32, 0, NOW(), 'bp.lastName', 'ic.primApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (114,NOW(), 1, 33, 0, NOW(), 'bp.additionalLastName', 'ic.segApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (115,NOW(), 1, 34, 0, NOW(), 'bp.formOfAddress', '', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (116,NOW(), 1, 35, 0, NOW(), 'bp.academicTitle', '0001', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (121,NOW(), 1, 40, 0, NOW(), 'bp.businessPartnerCategory', '1', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (122,NOW(), 1, 41, 0, NOW(), 'bp.firstName', 'ic.nombreInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (123,NOW(), 1, 42, 0, NOW(), 'bp.lastName', 'ic.primApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (124,NOW(), 1, 43, 0, NOW(), 'bp.additionalLastName', 'ic.segApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (125,NOW(), 1, 44, 0, NOW(), 'bp.formOfAddress', '', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (126,NOW(), 1, 45, 0, NOW(), 'bp.academicTitle', '0001', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	
	-- Para el caso de organizaciones
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (130,NOW(), 1, 50, 0, NOW(), 'bp.businessPartnerCategory', '2', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (131,NOW(), 1, 51, 0, NOW(), 'bp.organizationBPName1', 'ic.nombreInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (132,NOW(), 1, 52, 0, NOW(), 'bp.organizationBPName2', 'ic.primApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (133,NOW(), 1, 53, 0, NOW(), 'bp.organizationBPName3', 'ic.segApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (134,NOW(), 1, 54, 0, NOW(), 'bp.organizationBPName4', '', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (135,NOW(), 1, 55, 0, NOW(), 'bp.formOfAddress', '3000', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (140,NOW(), 1, 60, 0, NOW(), 'bp.businessPartnerCategory', '2', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (141,NOW(), 1, 61, 0, NOW(), 'bp.organizationBPName1', 'ic.nombreInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (142,NOW(), 1, 62, 0, NOW(), 'bp.organizationBPName2', 'ic.primApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (143,NOW(), 1, 63, 0, NOW(), 'bp.organizationBPName3', 'ic.segApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (144,NOW(), 1, 64, 0, NOW(), 'bp.organizationBPName4', '', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (145,NOW(), 1, 65, 0, NOW(), 'bp.formOfAddress', '3000', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (150,NOW(), 1, 70, 0, NOW(), 'bp.businessPartnerCategory', '2', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (151,NOW(), 1, 71, 0, NOW(), 'bp.organizationBPName1', 'ic.nombreInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (152,NOW(), 1, 72, 0, NOW(), 'bp.organizationBPName2', 'ic.primApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (153,NOW(), 1, 73, 0, NOW(), 'bp.organizationBPName3', 'ic.segApellidoInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (154,NOW(), 1, 74, 0, NOW(), 'bp.organizationBPName4', '', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (155,NOW(), 1, 75, 0, NOW(), 'bp.formOfAddress', '3000', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	
	-- Reglas generales
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (180,NOW(), 1, 80, 0, NOW(), 'bp.businessPartnerGrouping', 'BP03', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (181,NOW(), 1, 81, 0, NOW(), 'bp.businessPartnerGrouping', 'BP04', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (182,NOW(), 1, 82, 0, NOW(), 'bp.businessPartnerGrouping', 'BP05', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (183,NOW(), 1, 83, 0, NOW(), 'bp.businessPartnerGrouping', 'BP06', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (184,NOW(), 1, 84, 0, NOW(), 'bp.businessPartnerGrouping', 'BP07', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (185,NOW(), 1, 85, 0, NOW(), 'bp.businessPartnerGrouping', 'BP07', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (186,NOW(), 1, 86, 0, NOW(), 'bp.businessPartnerGrouping', 'BP08', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (187,NOW(), 1, 87, 0, NOW(), 'bp.searchTerm1', 'createCustomerKey', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (188,NOW(), 1, 88, 0, NOW(), 'bp.language', 'ic.codigoIdioma', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (189,NOW(), 1, 89, 0, NOW(), 'bp.correspondenceLanguage', 'ic.codigoIdioma', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	
	
	-- Asociaciones
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (200,NOW(), 1, 400, 0, NOW(), 'bp.toBusinessPartnerAddress', 'PetMuesInterlocutores-BusinessPartnerAddress(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (201,NOW(), 1, 500, 0, NOW(), 'bp.toBusinessPartnerTax', 'PetMuesInterlocutores-BusinessPartnerTax(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (202,NOW(), 1, 600, 0, NOW(), 'bp.toBusinessPartnerRole', 'PetMuesInterlocutores-BusinessPartnerRole(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (203,NOW(), 1, 700, 0, NOW(), 'bp.toBuPaIdentification', 'PetMuesInterlocutores-BuPaIdentification(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (204,NOW(), 1, 800, 0, NOW(), 'bp.toCustomer', 'createCustomer', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartner'));	
	
															 
	--                                                       
	-- Business Partner Address Rules                        
	--                                                       
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (240,NOW(), 1, 10, 0, NOW(), 'bpa.cityName', 'ic.poblacionInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (241,NOW(), 1, 20, 0, NOW(), 'bpa.country', 'ic.codigoPaisInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (242,NOW(), 1, 30, 0, NOW(), 'bpa.postalCode', 'ic.codigoPostalInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (243,NOW(), 1, 40, 0, NOW(), 'bpa.streetName', 'ic.direccionInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (244,NOW(), 1, 50, 0, NOW(), 'bpa.region', 'ic.provinciaInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (245,NOW(), 1, 60, 0, NOW(), 'bpa.language', 'ic.codigoIdioma', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (246,NOW(), 1, 100, 0, NOW(), 'bpa.toEmailAddress', 'PetMuesInterlocutores-AddressEmailAddress(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (247,NOW(), 1, 101, 0, NOW(), 'bpa.toMobilePhoneNumber', 'PetMuesInterlocutores-AddressMobileNumber(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (248,NOW(), 1, 102, 0, NOW(), 'bpa.toPhoneNumber', 'PetMuesInterlocutores-AddressPhoneNumber(ic)', 'RULE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerAddress'));
								 
	--                                                       
	-- Business Partner Tax Rules                            
	--                                                       
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (250,NOW(), 1, 10, 0, NOW(), 'bpt.bPTaxNumber', 'ic.nifInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerTax'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (251,NOW(), 1, 20, 0, NOW(), 'bpt.bPTaxType', 'ES1', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerTax'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (252,NOW(), 1, 21, 0, NOW(), 'bpt.bPTaxType', 'ES0', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerTax'));
															 
	--                                                       
	-- Business Partner Role Rules                           
	--                                                       
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (260,NOW(), 1, 10, 0, NOW(), 'bpr.businessPartnerRole', 'CLIEGR', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerRole'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (261,NOW(), 1, 20, 0, NOW(), 'bpr.validFrom', 'currentDate', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerRole'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (262,NOW(), 1, 30, 0, NOW(), 'bpr.validTo', 'endOfTimeDate', 'CALL', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BusinessPartnerRole'));
															 
	--                                                       
	-- Business Partner Identification                       
	--
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (270,NOW(), 1, 10, 0, NOW(), 'bpi.bPIdentificationType', 'FS0002', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (271,NOW(), 1, 11, 0, NOW(), 'bpi.bPIdentificationType', 'ZCHIP', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (272,NOW(), 1, 12, 0, NOW(), 'bpi.bPIdentificationType', 'ZOTROS', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (273,NOW(), 1, 13, 0, NOW(), 'bpi.bPIdentificationType', 'ZOTROS', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (274,NOW(), 1, 14, 0, NOW(), 'bpi.bPIdentificationType', 'ZOTROS', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (275,NOW(), 1, 15, 0, NOW(), 'bpi.bPIdentificationType', 'ZOTROS', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (276,NOW(), 1, 16, 0, NOW(), 'bpi.bPIdentificationType', 'FS0001', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (277,NOW(), 1, 17, 0, NOW(), 'bpi.bPIdentificationType', 'FS0001', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));	
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (278,NOW(), 1, 20, 0, NOW(), 'bpi.bPIdentificationNumber', 'ic.codigoIdentificacion', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-BuPaIdentification'));
				
	--                                                       
	-- Business Partner Mobile Phone Address
	--                                                       
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (280,NOW(), 1, 10, 0, NOW(), 'bam.isDefaultPhoneNumber', 'true', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressMobileNumber'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (281,NOW(), 1, 20, 0, NOW(), 'bam.phoneNumber', 'ic.telefono2Interlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressMobileNumber'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (282,NOW(), 1, 30, 0, NOW(), 'bam.destinationLocationCountry', 'ES', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressPhoneNumber'));
	
	--                                                       
	-- Business Partner Phone Address
	--                                                       
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (285,NOW(), 1, 10, 0, NOW(), 'bap.isDefaultPhoneNumber', 'true', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressPhoneNumber'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (286,NOW(), 1, 20, 0, NOW(), 'bap.phoneNumber', 'ic.telefono1Interlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressPhoneNumber'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (287,NOW(), 1, 30, 0, NOW(), 'bap.destinationLocationCountry', 'ES', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressPhoneNumber'));
	
	--                                                       
	-- Business Partner Email Address
	--                                                       
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (290,NOW(), 1, 10, 0, NOW(), 'bae.isDefaultEmailAddress', 'true', 'VALUE', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressEmailAddress'));
	INSERT INTO "FACTURACION"."T_TRANSFORMACIONRULE" VALUES (291,NOW(), 1, 20, 0, NOW(), 'bae.emailAddress', 'ic.emailInterlocutor', 'FIELD', (SELECT ID FROM "FACTURACION"."T_TRANSFORMACION" WHERE name = 'PetMuesInterlocutores-AddressEmailAddress'));
	
END
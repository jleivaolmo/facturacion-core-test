INSERT INTO T_TransformacionRule VALUES (1,NOW(),1,10,0,NOW(),'si.salesOrderType',NULL,NULL,'ZSIM','VALUE',1);
INSERT INTO T_TransformacionRule VALUES (2,NOW(),1,20,0,NOW(),'si.salesOffice',NULL,NULL,'getSalesOffice','CALL',1);
INSERT INTO T_TransformacionRule VALUES (3,NOW(),1,40,0,NOW(),'si.purchaseOrderByCustomer',NULL,NULL,'sm.peticion.codigoPeticion','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (4,NOW(),1,50,0,NOW(),'si.salesOrderDate',NULL,NULL,'sm.peticion.fechas.fechaPeticion','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (5,NOW(),1,30,0,NOW(),'si.pricingDate',NULL,NULL,'sm.peticion.fechas.fechaPeticion','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (6,NOW(),1,60,0,NOW(),'si.soldToParty',NULL,NULL,'getSAPCustomerID','CALL',1);
INSERT INTO T_TransformacionRule VALUES (7,NOW(),1,61,0,NOW(),'si.soldToParty',NULL,NULL,'"CPD" + sm.codigoOficinaVentas','JEXL',1);
INSERT INTO T_TransformacionRule VALUES (8,NOW(),1,70,0,NOW(),'si.distributionChannel',NULL,NULL,'GC','VALUE',1);
INSERT INTO T_TransformacionRule VALUES (9,NOW(),1,71,0,NOW(),'si.distributionChannel',NULL,NULL,'PR','VALUE',1);
INSERT INTO T_TransformacionRule VALUES (10,NOW(),1,80,0,NOW(),'si.organizationDivision',NULL,NULL,'getOrganizationDivision','CALL',1);
INSERT INTO T_TransformacionRule VALUES (11,NOW(),1,90,0,NOW(),'si.salesOrganization',NULL,NULL,'getSalesOrganization','CALL',1);
INSERT INTO T_TransformacionRule VALUES (12,NOW(),1,100,0,NOW(),'si.codigoPoliza',NULL,NULL,'sm.peticion.paciente.codigoPoliza','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (13,NOW(),1,101,0,NOW(),'si.documentoUnico',NULL,NULL,'sm.peticion.paciente.codigoDocumento','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (14,NOW(),1,102,0,NOW(),'si.servicioConcertado',NULL,NULL,'sm.peticion.clinicos.codigoServicio','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (15,NOW(),1,103,0,NOW(),'si.codigoOperacion',NULL,NULL,'sm.peticion.operacion.codigoOperacion','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (16,NOW(),1,104,0,NOW(),'si.tipoPeticion',NULL,NULL,'sm.peticion.tipoPeticion','FIELD',1);
INSERT INTO T_TransformacionRule VALUES (17,NOW(),1,1001,0,NOW(),'',NULL,NULL,'getPruebaIncongruente','CALL',1);
INSERT INTO T_TransformacionRule VALUES (18,NOW(),1,500,0,NOW(),'si.items',NULL,NULL,'PeticionMuestreoItems-SolIndItems(sm.peticion.pruebas)','RULE',1);
INSERT INTO T_TransformacionRule VALUES (19,NOW(),1,510,0,NOW(),'si_items.requestedQuantity',NULL,NULL,'sm_items.cantidadRequerida','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (20,NOW(),1,520,0,NOW(),'si_items.salesOrderItemCategory',NULL,NULL,'getSalesOrderItemCategory','CALL',2);
INSERT INTO T_TransformacionRule VALUES (21,NOW(),1,521,0,NOW(),'si_items.salesOrderItemCategory',NULL,NULL,'','VALUE',2);
INSERT INTO T_TransformacionRule VALUES (22,NOW(),1,530,0,NOW(),'si_items.fechaValidacion',NULL,NULL,'getFechaValidacion','CALL',2);
INSERT INTO T_TransformacionRule VALUES (23,NOW(),1,540,0,NOW(),'si_items.material',NULL,NULL,'sm_items.codigoMaterial','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (24,NOW(),1,550,0,NOW(),'si_items.salesOrderIndItem',NULL,NULL,'sm_items.idItem','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (25,NOW(),1,560,0,NOW(),'si_items.higherLevelltem',NULL,NULL,'sm_items.idParent','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (26,NOW(),1,570,0,NOW(),'si_items.delProductiva',NULL,NULL,'getDelegacionProductiva','CALL',2);
INSERT INTO T_TransformacionRule VALUES (27,NOW(),1,580,0,NOW(),'si_items.priceReferenceMaterial',NULL,NULL,'sm_items.codigoMaterialFacturacion','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (28,NOW(),1,590,0,NOW(),'si_items.unidadProductiva',NULL,NULL,'getUnidadProductiva','CALL',2);
INSERT INTO T_TransformacionRule VALUES (29,NOW(),1,600,0,NOW(),'si_items.productionPlant',NULL,NULL,'getItemPlant','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (30,NOW(),1,699,0,NOW(),'',NULL,NULL,'createPresupuestoPricing','CALL',2);
INSERT INTO T_TransformacionRule VALUES (31,NOW(),1,650,0,NOW(),'si_items.solicitudInd',NULL,NULL,'si','FIELD',2);
INSERT INTO T_TransformacionRule VALUES (32,NOW(),1,700,0,NOW(),'si.partners',NULL,NULL,'PetMuesInterlocutores-SolIndPartner(sm.peticion.interlocutores)','RULE',1);
INSERT INTO T_TransformacionRule VALUES (33,NOW(),1,710,0,NOW(),'si_partner.partnerFunction',NULL,NULL,'sm_interlocutor.rolInterlocutor','FIELD',3);
INSERT INTO T_TransformacionRule VALUES (34,NOW(),1,720,0,NOW(),'si_partner.customer',NULL,NULL,'createCustomerKey','CALL',3);
INSERT INTO T_TransformacionRule VALUES (35,NOW(),1,799,0,NOW(),'si_partner.solicitudInd',NULL,NULL,'si','FIELD',3);
INSERT INTO T_TransformacionRule VALUES (36,NOW(),1,10,0,NOW(),'bp.customer',NULL,NULL,'createCustomerKey','CALL',4);
INSERT INTO T_TransformacionRule VALUES (37,NOW(),1,20,0,NOW(),'bp.businessPartnerCategory',NULL,NULL,'1','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (38,NOW(),1,21,0,NOW(),'bp.firstName',NULL,NULL,'ic.nombreInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (39,NOW(),1,22,0,NOW(),'bp.lastName',NULL,NULL,'ic.primApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (40,NOW(),1,23,0,NOW(),'bp.additionalLastName',NULL,NULL,'ic.segApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (41,NOW(),1,24,0,NOW(),'bp.formOfAddress',NULL,NULL,'','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (42,NOW(),1,25,0,NOW(),'bp.academicTitle',NULL,NULL,'1','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (43,NOW(),1,30,0,NOW(),'bp.businessPartnerCategory',NULL,NULL,'1','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (44,NOW(),1,31,0,NOW(),'bp.firstName',NULL,NULL,'ic.nombreInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (45,NOW(),1,32,0,NOW(),'bp.lastName',NULL,NULL,'ic.primApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (46,NOW(),1,33,0,NOW(),'bp.additionalLastName',NULL,NULL,'ic.segApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (47,NOW(),1,34,0,NOW(),'bp.formOfAddress',NULL,NULL,'','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (48,NOW(),1,35,0,NOW(),'bp.academicTitle',NULL,NULL,'1','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (49,NOW(),1,40,0,NOW(),'bp.businessPartnerCategory',NULL,NULL,'1','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (50,NOW(),1,41,0,NOW(),'bp.firstName',NULL,NULL,'ic.nombreInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (51,NOW(),1,42,0,NOW(),'bp.lastName',NULL,NULL,'ic.primApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (52,NOW(),1,43,0,NOW(),'bp.additionalLastName',NULL,NULL,'ic.segApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (53,NOW(),1,44,0,NOW(),'bp.formOfAddress',NULL,NULL,'','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (54,NOW(),1,45,0,NOW(),'bp.academicTitle',NULL,NULL,'1','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (55,NOW(),1,50,0,NOW(),'bp.businessPartnerCategory',NULL,NULL,'2','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (56,NOW(),1,51,0,NOW(),'bp.organizationBPName1',NULL,NULL,'ic.nombreInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (57,NOW(),1,52,0,NOW(),'bp.organizationBPName2',NULL,NULL,'ic.primApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (58,NOW(),1,53,0,NOW(),'bp.organizationBPName3',NULL,NULL,'ic.segApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (59,NOW(),1,54,0,NOW(),'bp.organizationBPName4',NULL,NULL,'','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (60,NOW(),1,55,0,NOW(),'bp.formOfAddress',NULL,NULL,'3000','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (61,NOW(),1,60,0,NOW(),'bp.businessPartnerCategory',NULL,NULL,'2','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (62,NOW(),1,61,0,NOW(),'bp.organizationBPName1',NULL,NULL,'ic.nombreInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (63,NOW(),1,62,0,NOW(),'bp.organizationBPName2',NULL,NULL,'ic.primApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (64,NOW(),1,63,0,NOW(),'bp.organizationBPName3',NULL,NULL,'ic.segApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (65,NOW(),1,64,0,NOW(),'bp.organizationBPName4',NULL,NULL,'','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (66,NOW(),1,65,0,NOW(),'bp.formOfAddress',NULL,NULL,'3000','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (67,NOW(),1,70,0,NOW(),'bp.businessPartnerCategory',NULL,NULL,'2','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (68,NOW(),1,71,0,NOW(),'bp.organizationBPName1',NULL,NULL,'ic.nombreInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (69,NOW(),1,72,0,NOW(),'bp.organizationBPName2',NULL,NULL,'ic.primApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (70,NOW(),1,73,0,NOW(),'bp.organizationBPName3',NULL,NULL,'ic.segApellidoInterlocutor','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (71,NOW(),1,74,0,NOW(),'bp.organizationBPName4',NULL,NULL,'','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (72,NOW(),1,75,0,NOW(),'bp.formOfAddress',NULL,NULL,'3000','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (73,NOW(),1,80,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP03','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (74,NOW(),1,81,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP04','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (75,NOW(),1,82,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP05','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (76,NOW(),1,83,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP06','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (77,NOW(),1,84,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP07','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (78,NOW(),1,85,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP07','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (79,NOW(),1,86,0,NOW(),'bp.businessPartnerGrouping',NULL,NULL,'BP08','VALUE',4);
INSERT INTO T_TransformacionRule VALUES (80,NOW(),1,87,0,NOW(),'bp.searchTerm1',NULL,NULL,'createCustomerKey','CALL',4);
INSERT INTO T_TransformacionRule VALUES (81,NOW(),1,88,0,NOW(),'bp.language',NULL,NULL,'ic.codigoIdioma','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (82,NOW(),1,89,0,NOW(),'bp.correspondenceLanguage',NULL,NULL,'ic.codigoIdioma','FIELD',4);
INSERT INTO T_TransformacionRule VALUES (83,NOW(),1,400,0,NOW(),'bp.toBusinessPartnerAddress',NULL,NULL,'PetMuesInterlocutores-BusinessPartnerAddress(ic)','RULE',4);
INSERT INTO T_TransformacionRule VALUES (84,NOW(),1,500,0,NOW(),'bp.toBusinessPartnerTax',NULL,NULL,'PetMuesInterlocutores-BusinessPartnerTax(ic)','RULE',4);
INSERT INTO T_TransformacionRule VALUES (85,NOW(),1,600,0,NOW(),'bp.toBusinessPartnerRole',NULL,NULL,'PetMuesInterlocutores-BusinessPartnerRole(ic)','RULE',4);
INSERT INTO T_TransformacionRule VALUES (86,NOW(),1,700,0,NOW(),'bp.toBuPaIdentification',NULL,NULL,'PetMuesInterlocutores-BuPaIdentification(ic)','RULE',4);
INSERT INTO T_TransformacionRule VALUES (87,NOW(),1,800,0,NOW(),'bp.toCustomer',NULL,NULL,'createCustomer','CALL',4);
INSERT INTO T_TransformacionRule VALUES (88,NOW(),1,10,0,NOW(),'bpa.cityName',NULL,NULL,'ic.poblacionInterlocutor','FIELD',5);
INSERT INTO T_TransformacionRule VALUES (89,NOW(),1,20,0,NOW(),'bpa.country',NULL,NULL,'ic.codigoPaisInterlocutor','FIELD',5);
INSERT INTO T_TransformacionRule VALUES (90,NOW(),1,30,0,NOW(),'bpa.postalCode',NULL,NULL,'ic.codigoPostalInterlocutor','FIELD',5);
INSERT INTO T_TransformacionRule VALUES (91,NOW(),1,40,0,NOW(),'bpa.streetName',NULL,NULL,'ic.direccionInterlocutor','FIELD',5);
INSERT INTO T_TransformacionRule VALUES (92,NOW(),1,50,0,NOW(),'bpa.region',NULL,NULL,'ic.provinciaInterlocutor','FIELD',5);
INSERT INTO T_TransformacionRule VALUES (93,NOW(),1,60,0,NOW(),'bpa.language',NULL,NULL,'ic.codigoIdioma','FIELD',5);
INSERT INTO T_TransformacionRule VALUES (94,NOW(),1,100,0,NOW(),'bpa.toEmailAddress',NULL,NULL,'PetMuesInterlocutores-AddressEmailAddress(ic)','RULE',5);
INSERT INTO T_TransformacionRule VALUES (95,NOW(),1,101,0,NOW(),'bpa.toMobilePhoneNumber',NULL,NULL,'PetMuesInterlocutores-AddressMobileNumber(ic)','RULE',5);
INSERT INTO T_TransformacionRule VALUES (96,NOW(),1,102,0,NOW(),'bpa.toPhoneNumber',NULL,NULL,'PetMuesInterlocutores-AddressPhoneNumber(ic)','RULE',5);
INSERT INTO T_TransformacionRule VALUES (97,NOW(),1,10,0,NOW(),'bpt.bPTaxNumber',NULL,NULL,'ic.nifInterlocutor','FIELD',6);
INSERT INTO T_TransformacionRule VALUES (98,NOW(),1,20,0,NOW(),'bpt.bPTaxType',NULL,NULL,'ES1','VALUE',6);
INSERT INTO T_TransformacionRule VALUES (99,NOW(),1,21,0,NOW(),'bpt.bPTaxType',NULL,NULL,'ES0','VALUE',6);
INSERT INTO T_TransformacionRule VALUES (100,NOW(),1,10,0,NOW(),'bpr.businessPartnerRole',NULL,NULL,'CLIEGR','VALUE',7);
INSERT INTO T_TransformacionRule VALUES (101,NOW(),1,20,0,NOW(),'bpr.validFrom',NULL,NULL,'currentDate','CALL',7);
INSERT INTO T_TransformacionRule VALUES (102,NOW(),1,30,0,NOW(),'bpr.validTo',NULL,NULL,'endOfTimeDate','CALL',7);
INSERT INTO T_TransformacionRule VALUES (103,NOW(),1,10,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'FS0002','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (104,NOW(),1,11,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'ZCHIP','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (105,NOW(),1,12,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'ZOTROS','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (106,NOW(),1,13,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'ZOTROS','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (107,NOW(),1,14,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'ZOTROS','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (108,NOW(),1,15,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'ZOTROS','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (109,NOW(),1,16,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'FS0001','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (110,NOW(),1,17,0,NOW(),'bpi.bPIdentificationType',NULL,NULL,'FS0001','VALUE',8);
INSERT INTO T_TransformacionRule VALUES (111,NOW(),1,20,0,NOW(),'bpi.bPIdentificationNumber',NULL,NULL,'ic.codigoIdentificacion','FIELD',8);
INSERT INTO T_TransformacionRule VALUES (112,NOW(),1,10,0,NOW(),'bam.isDefaultPhoneNumber',NULL,NULL,'true','VALUE',10);
INSERT INTO T_TransformacionRule VALUES (113,NOW(),1,20,0,NOW(),'bam.phoneNumber',NULL,NULL,'ic.telefono2Interlocutor','FIELD',10);
INSERT INTO T_TransformacionRule VALUES (114,NOW(),1,30,0,NOW(),'bam.destinationLocationCountry',NULL,NULL,'ES','VALUE',11);
INSERT INTO T_TransformacionRule VALUES (115,NOW(),1,10,0,NOW(),'bap.isDefaultPhoneNumber',NULL,NULL,'true','VALUE',11);
INSERT INTO T_TransformacionRule VALUES (116,NOW(),1,20,0,NOW(),'bap.phoneNumber',NULL,NULL,'ic.telefono1Interlocutor','FIELD',11);
INSERT INTO T_TransformacionRule VALUES (117,NOW(),1,30,0,NOW(),'bap.destinationLocationCountry',NULL,NULL,'ES','VALUE',11);
INSERT INTO T_TransformacionRule VALUES (118,NOW(),1,10,0,NOW(),'bae.isDefaultEmailAddress',NULL,NULL,'true','VALUE',9);
INSERT INTO T_TransformacionRule VALUES (119,NOW(),1,20,0,NOW(),'bae.emailAddress',NULL,NULL,'ic.emailInterlocutor','FIELD',9);
INSERT INTO T_TransformacionRule VALUES (120,NOW(),1,10,0,NOW(),'si.salesOrderType',NULL,NULL,'ZSIM','VALUE',12);
INSERT INTO T_TransformacionRule VALUES (121,NOW(),1,20,0,NOW(),'si.salesOffice',NULL,NULL,'getSalesOffice','CALL',12);
INSERT INTO T_TransformacionRule VALUES (122,NOW(),1,40,0,NOW(),'si.purchaseOrderByCustomer',NULL,NULL,'pm.codigoPeticion','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (123,NOW(),1,50,0,NOW(),'si.salesOrderDate',NULL,NULL,'pm.fechas.fechaPeticion','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (124,NOW(),1,30,0,NOW(),'si.pricingDate',NULL,NULL,'pm.fechas.fechaPeticion','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (125,NOW(),1,60,0,NOW(),'si.soldToParty',NULL,NULL,'getSAPCustomerID','CALL',12);
INSERT INTO T_TransformacionRule VALUES (126,NOW(),1,61,0,NOW(),'si.soldToParty',NULL,NULL,'"CPD" + pm.solicitud.codigoOficinaVentas','JEXL',12);
INSERT INTO T_TransformacionRule VALUES (127,NOW(),1,70,0,NOW(),'si.distributionChannel',NULL,NULL,'GC','VALUE',12);
INSERT INTO T_TransformacionRule VALUES (128,NOW(),1,71,0,NOW(),'si.distributionChannel',NULL,NULL,'PR','VALUE',12);
INSERT INTO T_TransformacionRule VALUES (129,NOW(),1,80,0,NOW(),'si.organizationDivision',NULL,NULL,'getOrganizationDivision','CALL',12);
INSERT INTO T_TransformacionRule VALUES (130,NOW(),1,90,0,NOW(),'si.salesOrganization',NULL,NULL,'getSalesOrganization','CALL',12);
INSERT INTO T_TransformacionRule VALUES (131,NOW(),1,100,0,NOW(),'si.codigoPoliza',NULL,NULL,'pm.paciente.codigoPoliza','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (132,NOW(),1,101,0,NOW(),'si.documentoUnico',NULL,NULL,'pm.paciente.codigoDocumento','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (133,NOW(),1,102,0,NOW(),'si.servicioConcertado',NULL,NULL,'pm.clinicos.codigoServicio','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (134,NOW(),1,103,0,NOW(),'si.codigoOperacion',NULL,NULL,'pm.operacion.codigoOperacion','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (135,NOW(),1,104,0,NOW(),'si.tipoPeticion',NULL,NULL,'pm.tipoPeticion','FIELD',12);
INSERT INTO T_TransformacionRule VALUES (136,NOW(),1,1001,0,NOW(),'',NULL,NULL,'getPruebaIncongruente','CALL',12);
INSERT INTO T_TransformacionRule VALUES (137,NOW(),1,500,0,NOW(),'si.items',NULL,NULL,'PeticionMuestreoItems-SolIndItems(pm.pruebas)','RULE',12);
INSERT INTO T_TransformacionRule VALUES (138,NOW(),1,700,0,NOW(),'si.partners',NULL,NULL,'PetMuesInterlocutores-SolIndPartner(pm.interlocutores)','RULE',12);

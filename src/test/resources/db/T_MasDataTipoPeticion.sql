CREATE TABLE `T_MasDataTipoPeticion` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `entity_creation_timestamp` datetime NOT NULL,
  `entity_version` bigint(20) NOT NULL,
  `inactive` smallint(6) DEFAULT 0,
  `last_updated_timestamp` datetime NOT NULL,
  `NOMBRETIPOPETICION` varchar(255) DEFAULT NULL,
  `TIPOPETICION` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_byTipoPeticion` (`TIPOPETICION`)
);
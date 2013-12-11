CREATE TABLE `geography_country` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(255) NOT NULL,
  `phoneCode` int(11) default NULL,
  `name` varchar(255) NOT NULL,
  `sortOrder` int(11) default NULL,
  `visible` bit(1) NOT NULL default 1,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) default charset=utf8;

CREATE TABLE `geography_state_province` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) default NULL,
  `sortOrder` int(11) default NULL,
  `visible` bit(1) NOT NULL default 1,
  `countryId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_StateProvince__Country` (`countryId`),
  CONSTRAINT `fk_StateProvince__Country` FOREIGN KEY (`countryId`) REFERENCES `geography_country` (`id`)
) default charset=utf8;

CREATE TABLE `pay_business_unit` (
  `id` int(11) NOT NULL auto_increment,
  `uuid` varchar(36) NOT NULL,
  `code` varchar(255) NOT NULL,  
  `name` varchar(255) NOT NULL,
  `industryType` varchar(255) NOT NULL,
  `merchantAccountId` int(11) default NULL,
  `enabled` bit(1) default NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key_uuid` (`uuid`),
  KEY `fk_BusinessUnit__MerchantAccount` (`merchantAccountId`)
) default charset=utf8;

CREATE TABLE `pay_credit_card` (
  `id` int(11) NOT NULL auto_increment,
  `businessUnitId` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `numberMasked` varchar(32) NOT NULL,
  `cardHolder` varchar(255) default NULL,
  `expDate` date NOT NULL,
  `secCode` varchar(255) default NULL,
  `swiped` bit(1) NOT NULL,
  `line1` varchar(255) default NULL,
  `line2` varchar(255) default NULL,
  `line3` varchar(255) default NULL,
  `postalCode` varchar(255) default NULL,
  `city` varchar(255) default NULL,
  `state` varchar(255) default NULL,
  `country` varchar(255) default NULL,
  `cardNumberHash` varchar(255) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `token` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key_uuid` (`uuid`),
  UNIQUE INDEX `key_unique_businessUnitId_cardNumberHash` USING BTREE (`businessUnitId`, `cardNumberHash`),
  KEY `fk_CreditCard__CreditCardType` (`typeId`),
  KEY `fk_CreditCard__BusinessUnit` (`businessUnitId`)
) default charset=utf8;

CREATE TABLE `pay_credit_card_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(2) NOT NULL,
  `description` varchar(32) default NULL,
  `sortOrder` int(11) default NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key_code` (`code`)
) default charset=utf8;

CREATE TABLE `pay_merchant_account` (
  `id` int(11) NOT NULL auto_increment,
  `number` varchar(12) NOT NULL,
  `bankBin` varchar(6) NOT NULL,
  `name` varchar(256) default NULL,
  `agentBankNum` varchar(6) default NULL,
  `agentChainNum` varchar(6) default NULL,
  `storeNum` varchar(4) default NULL,
  `terminalId` varchar(8) default NULL,
  `terminalNum` varchar(4) default NULL,
  `line1` varchar(255) default NULL,
  `line2` varchar(255) default NULL,
  `line3` varchar(255) default NULL,
  `postalCode` varchar(255) default NULL,
  `city` varchar(255) default NULL,
  `state` varchar(255) default NULL,
  `country` varchar(255) default NULL,
  `merchantPhone` varchar(32) default NULL,
  `servicePhone` varchar(32) default NULL,
  `timezone` varchar(255) default NULL,
  `currency` varchar(3) default NULL,
  `uuid` varchar(36) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key_uuid` (`uuid`),
  UNIQUE KEY `key_number_bankBin` (`number`,`bankBin`)
) default charset=utf8;

CREATE TABLE `pay_transaction_response` (
  `id` int(11) NOT NULL auto_increment,
  `date` date NOT NULL,
  `approved` bit(1) NOT NULL,
  `originalBytes` tinyblob,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`)
) default charset=utf8;

CREATE TABLE `pay_transaction` (
  `id` int(11) NOT NULL auto_increment,
  `date` datetime NOT NULL,
  `businessUnitId` int(11) NOT NULL,
  `transactionResponseId` int(11) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `enabled` bit(1) default NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `key_uuid` (`uuid`),
  KEY `fk_Transaction__TransactionResponse` (`transactionResponseId`),
  KEY `fk_Transaction__BusinessUnit` (`businessUnitId`),
  CONSTRAINT `fk_Transaction__BusinessUnit` FOREIGN KEY (`businessUnitId`) REFERENCES `pay_business_unit` (`id`),
  CONSTRAINT `fk_Transaction__TransactionResponse` FOREIGN KEY (`transactionResponseId`) REFERENCES `pay_transaction_response` (`id`)
) default charset=utf8;

CREATE TABLE `pay_authorization` (
  `id` int(11) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `externalSourceId` varchar(255) NOT NULL,
  `creditCardId` int(11) NOT NULL,
  `batched` bit(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_Authorization__Transaction` (`id`),
  KEY `fk_Authorization__CreditCard` (`creditCardId`)
) default charset=utf8;

CREATE TABLE `pay_authorization_hotel` (
  `id` int(11) NOT NULL,
  `stayDuration` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_AuthorizationHotel__Transaction` (`id`),
  CONSTRAINT `fk_AuthorizationHotel__Transaction` FOREIGN KEY (`id`) REFERENCES `pay_authorization` (`id`)
) default charset=utf8;

CREATE TABLE `pay_authorization_modificaiton` (
  `id` int(11) NOT NULL auto_increment,
  `date` date NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `authorizationId` int(11) NOT NULL,
  `position` int(11) default NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_AuthorizationModification__Authorization` (`authorizationId`),
  CONSTRAINT `fk_AuthorizationModification__Authorization` FOREIGN KEY (`authorizationId`) REFERENCES `pay_authorization` (`id`)
) default charset=utf8;

CREATE TABLE `pay_authorization_response` (
  `id` int(11) NOT NULL,
  `approvalCode` varchar(255) NOT NULL,
  `storeNumber` varchar(255) default NULL,
  `terminalNumber` varchar(255) default NULL,
  `requestedACI` varchar(255) NOT NULL,
  `returnedACI` varchar(255) NOT NULL,
  `authSourceCode` varchar(255) NOT NULL,
  `transactionSequenceNumber` varchar(255) default NULL,
  `responseCode` varchar(255) NOT NULL,
  `avsResultCode` varchar(255) NOT NULL,
  `authResponseText` varchar(255) default NULL,
  `retrievalReferenceNumber` varchar(255) NOT NULL,
  `transactionIdentifier` varchar(255) NOT NULL,
  `validationCode` varchar(255) NOT NULL,
  `localTransDate` varchar(255) NOT NULL,
  `localTransTime` varchar(255) NOT NULL,
  `reversal` bit(1) NOT NULL,
  `offline` bit(1) NOT NULL,
  `declined` bit(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_AuthorizationResponse__Transaction` (`id`),
  CONSTRAINT `fk_AuthorizationResponse__Transaction` FOREIGN KEY (`id`) REFERENCES `pay_transaction_response` (`id`)
) default charset=utf8;

CREATE TABLE `pay_settlement` (
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_Settlement__Transaction` (`id`),
  CONSTRAINT `fk_Settlement__Transaction` FOREIGN KEY (`id`) REFERENCES `pay_transaction` (`id`)
) default charset=utf8;

CREATE TABLE `pay_settlement_item` (
  `id` int(11) NOT NULL auto_increment,
  `date` date NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `purchaseIdentifier` varchar(255) NOT NULL,
  `authorizationId` int(11) default NULL,
  `type` varchar(255) NOT NULL,
  `externalSourceId` varchar(255) NOT NULL,
  `creditCardId` int(11) NOT NULL,
  `businessUnitId` int(11) NOT NULL,
  `settlementId` int(11) default NULL,
  `position` int(11) default NULL,
  `uuid` varchar(36) NOT NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_SettlementItem__Authorization` (`authorizationId`),
  KEY `fk_SettlementItem__Settlement` (`settlementId`),
  KEY `fk_SettlementItem__BusinessUnit` (`businessUnitId`),
  KEY `fk_SettlementItem__CreditCard` (`creditCardId`),
  CONSTRAINT `fk_SettlementItem__Authorization` FOREIGN KEY (`authorizationId`) REFERENCES `pay_authorization` (`id`),
  CONSTRAINT `fk_SettlementItem__BusinessUnit` FOREIGN KEY (`businessUnitId`) REFERENCES `pay_business_unit` (`id`),
  CONSTRAINT `fk_SettlementItem__CreditCard` FOREIGN KEY (`creditCardId`) REFERENCES `pay_credit_card` (`id`),
  CONSTRAINT `fk_SettlementItem__Settlement` FOREIGN KEY (`settlementId`) REFERENCES `pay_settlement` (`id`)
) default charset=utf8;

CREATE TABLE `pay_settlement_item_hotel` (
  `id` int(11) NOT NULL,
  `checkOutDate` datetime NOT NULL,
  `averageRate` decimal(19,2) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_SettlementHotelItem__SettlementItem` (`id`),
  CONSTRAINT `fk_SettlementHotelItem__SettlementItem` FOREIGN KEY (`id`) REFERENCES `pay_settlement_item` (`id`)
) default charset=utf8;

CREATE TABLE `pay_settlement_response` (
  `id` int(11) NOT NULL,
  `recordFormat` varchar(255) NOT NULL,
  `applicationType` varchar(255) NOT NULL,
  `routingID` varchar(255) NOT NULL,
  `recordType` varchar(255) NOT NULL,
  `batchRecordCount` varchar(255) NOT NULL,
  `batchNetDeposit` varchar(255) NOT NULL,
  `batchResponseCode` varchar(255) NOT NULL,
  `batchNumber` varchar(255) NOT NULL,
  `batchResponseText` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_SettlementResponse__Transaction` (`id`),
  CONSTRAINT `fk_SettlementResponse__Transaction` FOREIGN KEY (`id`) REFERENCES `pay_transaction_response` (`id`)
) default charset=utf8;

CREATE TABLE `pay_batch_number` (
  `id` int(11) NOT NULL auto_increment,
  `number` int(11) NOT NULL,
  `merchantAccountId` int(11) NOT NULL,
  `updated` datetime NOT NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_BatchNumber__MerchantAccount` (`merchantAccountId`),
  CONSTRAINT `fk_BatchNumber__MerchantAccount` FOREIGN KEY (`merchantAccountId`) REFERENCES `pay_merchant_account` (`id`)
) default charset=utf8;


ALTER TABLE pay_authorization 
  ADD CONSTRAINT `fk_Authorization__CreditCard` FOREIGN KEY (`creditCardId`) REFERENCES `pay_credit_card` (`id`),
  ADD CONSTRAINT `fk_Authorization__Transaction` FOREIGN KEY (`id`) REFERENCES `pay_transaction` (`id`);

ALTER TABLE pay_business_unit
  ADD CONSTRAINT `fk_BusinessUnit__MerchantAccount` FOREIGN KEY (`merchantAccountId`) REFERENCES `pay_merchant_account` (`id`);

ALTER TABLE pay_credit_card
  ADD CONSTRAINT `fk_CreditCard__CreditCardType` FOREIGN KEY (`typeId`) REFERENCES `pay_credit_card_type` (`id`);
ALTER TABLE pay_credit_card
  ADD CONSTRAINT `fk_CreditCard__BusinessUnit` FOREIGN KEY (`businessUnitId`) REFERENCES `pay_business_unit` (`id`);


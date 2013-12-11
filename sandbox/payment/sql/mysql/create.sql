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

ALTER TABLE pay_credit_card
  ADD CONSTRAINT `fk_CreditCard__CreditCardType` FOREIGN KEY (`typeId`) REFERENCES `pay_credit_card_type` (`id`);
ALTER TABLE pay_credit_card
  ADD CONSTRAINT `fk_CreditCard__BusinessUnit` FOREIGN KEY (`businessUnitId`) REFERENCES `pay_business_unit` (`id`);


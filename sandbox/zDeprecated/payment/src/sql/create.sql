/*
SQLyog - Free MySQL GUI v5.01
Host - 5.0.18-nt : Database - ccservice
*********************************************************************
Server version : 5.0.18-nt
*/


-- create database if not exists `test_db`;

-- USE `test_db`;





DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `countryId` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `iso` char(2) default NULL,
  `iso3letter` char(3) default NULL,
  `iso3numeric` char(3) default NULL,
  PRIMARY KEY  (`countryId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `country` */

insert into `country` values 

(1,'Unknown','XX','XXX','000'),

(2,'United States','US','USA','840'),

(3,'Afghanistan','AF','AFG','4'),

(4,'Albania','AL','ALB','8'),

(5,'Algeria','DZ','DZA','12'),

(6,'American Samoa','AS','ASM','16'),

(7,'Andorra','AD','AND','20'),

(8,'Angola','AO','AGO','24'),

(9,'Anguilla','AI','AIA','660'),

(10,'Antarctica','AQ','ATA','10'),

(11,'Antigua and Barbuda','AG','ATG','28'),

(12,'Argentina','AR','ARG','32'),

(13,'Aruba','AW','ABW','533'),

(14,'Australia','AU','AUS','36'),

(15,'Austria','AT','AUT','40'),

(16,'Bahamas','BS','BHS','44'),

(17,'Bahrain','BH','BHR','48'),

(18,'Bangladesh','BD','BGD','50'),

(19,'Barbados','BB','BRB','52'),

(20,'Belgium','BE','BEL','56'),

(21,'Belize','BZ','BLZ','84'),

(22,'Benin','BJ','BEN','204'),

(23,'Bermuda','BM','BMU','60'),

(24,'Bhutan','BT','BTN','64'),

(25,'Bolivia','BO','BOL','68'),

(26,'Botswana','BW','BWA','72'),

(27,'Bouvet Island','BV','BVT','74'),

(28,'Brazil','BR','BRA','76'),

(29,'British Indian Ocean Territory','IO','IOT','86'),

(30,'Brunei Darussalam','BN','BRN','96'),

(31,'Bulgaria','BG','BGR','100'),

(32,'Burkina Faso','BF','BFA','854'),

(33,'Burma','BU','BUR','104'),

(34,'Burundi','BI','BDI','108'),

(35,'Byelorussian','BY','BYS','112'),

(36,'Cameroon','CM','CMR','120'),

(37,'Canada','CA','CAN','124'),

(38,'Cape Verde','CV','CPV','132'),

(39,'Cayman Islands','KY','CYM','136'),

(40,'Central African Republic','CF','CAF','140'),

(41,'Chad','TD','TCD','148'),

(42,'Chile','CL','CHL','152'),

(43,'China','CN','CHN','156'),

(44,'Christmas Island','CX','CXR','162'),

(45,'Cocos (Keeling) Islands','CC','CCK','166'),

(46,'Colombia','CO','COL','170'),

(47,'Comoros','KM','COM','174'),

(48,'Congo','CG','COG','178'),

(49,'Cook Islands','CK','COK','184'),

(50,'Costa Rica','CR','CRI','188'),

(51,'Cote D\'Ivoire','CI','CIV','384'),

(52,'Cuba','CU','CUB','192'),

(53,'Cyprus','CY','CYP','196'),

(54,'Czechoslovakia','CS','CSK','200'),

(55,'Denmark','DK','DNK','208'),

(56,'Djibouti','DJ','DJI','262'),

(57,'Dominica','DM','DMA','212'),

(58,'Dominican Republic','DO','DOM','214'),

(59,'East Timor','TP','TMP','626'),

(60,'Ecuador','EC','ECU','218'),

(61,'Egypt','EG','EGY','818'),

(62,'El Salvador','SV','SLV','222'),

(63,'Equatorial Guinea','GQ','GNQ','226'),

(64,'Ethiopia','ET','ETH','230'),

(65,'Falkland Islands','FK','FLK','238'),

(66,'Faroe Islands','FO','FRO','234'),

(67,'Fiji','FJ','FJI','242'),

(68,'Finland','FI','FIN','246'),

(69,'France','FR','FRA','250'),

(70,'French Guiana','GF','GUF','254'),

(71,'French Polynesia','PF','PYF','258'),

(72,'French Southern Territories','TF','ATF','260'),

(73,'Gabon','GA','GAB','266'),

(74,'Gambia','GM','GMB','270'),

(75,'German Democratic Republic','DD','DDR','278'),

(76,'Germany Federal Republic of','DE','DEU','280'),

(77,'Ghana','GH','GHA','288'),

(78,'Gibraltar','GI','GIB','292'),

(79,'Greece','GR','GRC','300'),

(80,'Greenland','GL','GRL','304'),

(81,'Grenada','GD','GRD','308'),

(82,'Gudeloupe','GP','GLP','312'),

(83,'Guam','GU','GUM','316'),

(84,'Guatemala','GT','GTM','320'),

(85,'Guinea','GN','GIN','324'),

(86,'Guinea-Bissau','GW','GNB','624'),

(87,'Guyana','GY','GUY','328'),

(88,'Haiti','HT','HTI','332'),

(89,'Heard and Mc Donald Islands','HM','HMD','334'),

(90,'Honduras','HN','HND','340'),

(91,'Hong Kong','HK','HKG','344'),

(92,'Hungary','HU','HUN','348'),

(93,'Iceland','IS','ISL','352'),

(94,'India','IN','IND','356'),

(95,'Indonesia','ID','IDN','360'),

(96,'Iran','IR','IRN','364'),

(97,'Iraq','IQ','IRQ','368'),

(98,'Ireland','IE','IRL','372'),

(99,'Israel','IL','ISR','376'),

(100,'Italy','IT','ITA','380'),

(101,'Jamaica','JM','JAM','388'),

(102,'Japan','JP','JPN','392'),

(103,'Jordan','JO','JOR','400'),

(104,'Kampuchea Democratic','KH','KHM','116'),

(105,'Kenya','KE','KEN','404'),

(106,'Kiribati','KI','KIR','296'),

(107,'Korea Democratic People\'s Republic of','KP','PRK','408'),

(108,'Korea Republic of','KR','KOR','410'),

(109,'Kuwait','KW','KWT','414'),

(110,'Lao People\'s Democratic Republic','LA','LAO','418'),

(111,'Lebanon','LB','LBN','422'),

(112,'Lesotho','LS','LSO','426'),

(113,'Liberia','LR','LBR','430'),

(114,'Libyan Arab Jamahiriya','LY','LBY','434'),

(115,'Liechtenstein','LI','LIE','438'),

(116,'Luxembourg','LU','LUX','442'),

(117,'Macau','MO','MAC','446'),

(118,'Madagascar','MG','MDG','450'),

(119,'Malawi','MW','MWI','454'),

(120,'Malaysia','MY','MYS','458'),

(121,'Maldives','MV','MDV','462'),

(122,'Mali','ML','MLI','466'),

(123,'Malta','MT','MLT','470'),

(124,'Marshall Islands','MH','MHL','584'),

(125,'Martinique','MQ','MTQ','474'),

(126,'Mauritania','MR','MRT','478'),

(127,'Mauritius','MU','MUS','480'),

(128,'Mexico','MX','MEX','484'),

(129,'Micronesia','FM','FSM','583'),

(130,'Monaco','MC','MCO','492'),

(131,'Mongolia','MN','MNG','496'),

(132,'Montserrat','MS','MSR','500'),

(133,'Morocco','MA','MAR','504'),

(134,'Mozambique','MZ','MOZ','508'),

(135,'Namibia','NA','NAM','516'),

(136,'Nauru','NR','NRU','520'),

(137,'Nepal','NP','NPL','524'),

(138,'Netherlands','NL','NLD','528'),

(139,'Netherlands Antilles','AN','ANT','532'),

(140,'Neutral Zone','NT','NTZ','536'),

(141,'New Caledonia','NC','NCL','540'),

(142,'New Zealand','NZ','NZL','554'),

(143,'Nicaragua','NI','NIC','558'),

(144,'Niger','NE','NER','562'),

(145,'Nigeria','NG','NGA','566'),

(146,'Niue','NU','NIU','570'),

(147,'Norfolk Island','NF','NFK','574'),

(148,'Northern Mariana Islands','MP','MNP','580'),

(149,'Norway','NO','NOR','578'),

(150,'Oman','OM','OMN','512'),

(151,'Pakistan','PK','PAK','586'),

(152,'Palau','PW','PLW','585'),

(153,'Panama','PA','PAN','590'),

(154,'Papua New Guinea','PG','PNG','598'),

(155,'Paraguay','PY','PRY','600'),

(156,'Peru','PE','PER','604'),

(157,'Philippines','PH','PHL','608'),

(158,'Pitcairn Island','PN','PCN','612'),

(159,'Poland','PL','POL','616'),

(160,'Portugal','PT','PRT','620'),

(161,'Puerto Rico','PR','PRI','630'),

(162,'Qatar','QA','QAT','634'),

(163,'Reunion','RE','REU','638'),

(164,'Romania','RO','ROM','642'),

(165,'Rwanda','RW','RWA','646'),

(166,'St. Helena','SH','SHN','654'),

(167,'Saint Kitts and Nevis','KN','KNA','659'),

(168,'Saint Lucia','LC','LCA','662'),

(169,'St. Pierre and Miquelon','PM','SPM','666'),

(170,'Saint Vincent and the Grenadines','VC','VCT','670'),

(171,'Samoa','WS','WSM','882'),

(172,'San Marino','SM','SMR','674'),

(173,'Sao Tome and Principe','ST','STP','678'),

(174,'Saudia Arabia','SA','SAU','682'),

(175,'Senegal','SN','SEN','686'),

(176,'Seychelles','SC','SYC','690'),

(177,'Sierra Leones','SL','SLE','694'),

(178,'Singapore','SG','SGP','702'),

(179,'Solomon Islands','SB','SLB','90'),

(180,'Somalia','SO','SOM','706'),

(181,'South Africa','ZA','ZAF','710'),

(182,'Spain','ES','ESP','724'),

(183,'Sri Lanka','LK','LKA','144'),

(184,'Sudan','SD','SDN','736'),

(185,'Suriname','SR','SUR','740'),

(186,'Svalbard and Jan Mayen Islands','SJ','SJM','744'),

(187,'Swaziland','SZ','SWZ','748'),

(188,'Sweden','SE','SWE','752'),

(189,'Switzerland','CH','CHE','756'),

(190,'Syrian Arab Republic','SY','SYR','760'),

(191,'Taiwan Province of China','TW','TWN','158'),

(192,'Tanzania United Republic of','TZ','TZA','834'),

(193,'Thailand','TH','THA','764'),

(194,'Togo','TG','TGO','768'),

(195,'Tokelau','TK','TKL','772'),

(196,'Tonga','TO','TON','776'),

(197,'Trinidad and Tobago','TT','TTO','780'),

(198,'Tunisia','TN','TUN','788'),

(199,'Turkey','TR','TUR','792'),

(200,'Turks and Caicos Islands','TC','TCA','796'),

(201,'Tuvalu','TV','TUV','798'),

(202,'Uganda','UG','UGA','800'),

(203,'Ukranian SSR','UA','UKR','804'),

(204,'United Arab Emirates','AE','ARE','784'),

(205,'United Kingdom','GB','GBR','826'),

(206,'United States Minor Outlying Islands','UM','UMI','581'),

(207,'Uruguay','UY','URY','858'),

(208,'USSR','SU','SUN','810'),

(209,'Vanuatu','VU','VUT','548'),

(210,'Vatican City State (Holy See)','VA','VAT','336'),

(211,'Venezuela','VE','VEN','862'),

(212,'Viet Nam','VN','VNM','704'),

(213,'Virgin Islands (British)','VG','VGB','92'),

(214,'Virgin Islands (U.S.)','VI','VIR','850'),

(215,'Wallis and Futuna Islands','WF','WLF','876'),

(216,'Western Sahara','EH','ESH','732'),

(217,'Yemen','YE','YEM','886'),

(218,'Yemen Democratic','YD','YMD','720'),

(219,'Yugoslavia','YU','YUG','890'),

(220,'Zaire','ZR','ZAR','180'),

(221,'Zambia','ZM','ZMB','894'),

(222,'Zimbabwe','ZW','ZWE','716'),

(223,'United States','US','USA','840'),

(224,'testnited States','US','USA','840');


DROP TABLE IF EXISTS `batch_number`;

CREATE TABLE `batch_number` (
  `batchNumberID` int(11) NOT NULL,
  `merchantId` int(11) default NULL,
  `hotelId` int(11) default NULL,
  PRIMARY KEY  (`batchNumberID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `batch_number` */


DROP TABLE IF EXISTS `vital_authorization_response`;

CREATE TABLE `vital_authorization_response` (               
                                `Id` bigint(20) NOT NULL auto_increment,                  
                                `merchantId` bigint(20) default NULL,                     
                                `InstrumentId` bigint(20) default NULL,                   
                                `requestedACI` char(1) default NULL,                      
                                `returnedACI` tinyblob,                                   
                                `AuthorizationAmount` varchar(255) default NULL,          
                                `authSourceCode` char(1) default NULL,                    
                                `transactionSequenceNumber` varchar(4) default NULL,      
                                `localTransDate` varchar(255) default NULL,               
                                `localTransTime` varchar(255) default NULL,               
                                `transactionIdentifier` varchar(255) default NULL,        
                                `responseCode` tinyblob,                                  
                                `avsResultCode` char(1) default NULL,                     
                                `approvalCode` varchar(6) default NULL,                   
                                `retrievalReferenceNumber` varchar(255) default NULL,     
                                `validationCode` varchar(255) default NULL,               
                                `authResponseText` varchar(255) default NULL,             
                                `disabled` int(11) default NULL,                          
                                `batched` int(11) default NULL,                           
                                `localTransactionDateTime` datetime default NULL,         
                                `originalAuthorizationAmount` decimal(8,2) default NULL,  
                                `storeNumber` varchar(255) default NULL,                  
                                `terminalNumber` varchar(255) default NULL,               
                                `offline` int(11) default NULL,                           
                                `reversal` int(11) default NULL,                          
                                `arAccountId` int(11) default NULL,                       
                                `systemDate` date default NULL,                           
                                PRIMARY KEY  (`Id`)                                       
                              ) ENGINE=InnoDB DEFAULT CHARSET=latin1;                      


/*Table structure for table `creditcardtype` */


DROP TABLE IF EXISTS `creditcardtype`;

CREATE TABLE `creditcardtype` (
  `creditCardTypeId` int(10) unsigned NOT NULL auto_increment,
  `code` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`creditCardTypeId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `creditcardtype` */

insert into `creditcardtype` values 

(1,'XX','Unknown'),

(2,'A3','Air Plus'),

(3,'AX','American Express'),

(4,'CB','Carte Blanche'),

(5,'DC','Diners Club'),

(6,'DS','Discover'),

(7,'ER','En Route'),

(8,'EC','Eurocard'),

(9,'JC','Japan Credit Bureau'),

(10,'MC','Mastercard'),

(11,'O1','Optima'),

(12,'VI','Visa');

/*
SQLyog - Free MySQL GUI v5.01
Host - 5.0.18-nt : Database - test_case_db
*********************************************************************
Server version : 5.0.18-nt
*/
 
/*Table structure for table `vital_merchant_account` */

DROP TABLE IF EXISTS `vital_merchant_account`;

CREATE TABLE `vital_merchant_account` (
  `ID` bigint(20) NOT NULL,
  `ACQ_BANK_IDENTIFICATION_NO` varchar(255) default NULL,
  `AGENT_BANK_NO` varchar(255) default NULL,
  `CARD_HOLDER_SERVICE_NO` varchar(255) default NULL,
  `CHAIN_NUMBER` varchar(255) default NULL,
  `CITY` varchar(255) default NULL,
  `CITY_CODE` varchar(255) default NULL,
  `COUNTRY_CODE` varchar(255) default NULL,
  `CURRENCY_CODE` varchar(255) default NULL,
  `LOCAL_PHONE_NO` varchar(255) default NULL,
  `LOCATION` varchar(255) default NULL,
  `NAME` varchar(255) default NULL,
  `NUMBER` varchar(255) default NULL,
  `STATE` varchar(255) default NULL,
  `STORE_NUMBER` varchar(255) default NULL,
  `TERMINAL_ID` varchar(255) default NULL,
  `TERMINAL_NUMBER` varchar(255) default NULL,
  `TIME_ZONE_DIFFERENTIAL` varchar(255) default NULL,
  `TIME_ZONE` varchar(255) default NULL,
  `countryId` bigint(20) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `vital_merchant_account` */

insert into `vital_merchant_account` values 

(7,'999995','111111','505-2472333','000000','Albuquerque  ','87104    ','840','840','505-2472333','Albuquerque  ','INNPOINTS                ','888000002329','44','5999','71004882','1515','000','America/Denver',2),

DROP TABLE IF EXISTS `credit_card`;

CREATE TABLE `credit_card` (
  `Credit_Card_ID` bigint(20) NOT NULL,
  `CARD_NUMBER` varchar(255) default NULL,
  `EXPIRATION_DATE` varchar(255) default NULL,
  `HOLDERS_NAME` varchar(255) default NULL,
  `ADDRESS1` varchar(255) default NULL,
  `ADDRESS2` varchar(255) default NULL,
  `CITY` varchar(255) default NULL,
  `STATE` varchar(255) default NULL,
  `ZIP` varchar(255) default NULL,
  `PHONENUMBER` varchar(255) default NULL,
  `TRACK1` varchar(255) default NULL,
  `TRACK2` varchar(255) default NULL,
  `LOGGABLE_INFO` varchar(255) default NULL,
  `creditCardTypeId` bigint(20) default NULL,
  `countryId` bigint(20) default NULL,
  PRIMARY KEY  (`Credit_Card_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `credit_card` */

insert into `credit_card` values 
(650795,'E70CVWnuIfAsvsUG4XAcxEhGSKNEpRrK','0604','Mark A. Brown','PO Box 67527','','Albuquerque','New Mexico','87193','','','null','',10,2);

/*
SQLyog - Free MySQL GUI v5.01
Host - 5.0.18-nt : Database - ccservice
*********************************************************************
Server version : 5.0.18-nt
*/




/*
SQLyog - Free MySQL GUI v5.01
Host - 5.0.18-nt : Database - ccservice
*********************************************************************
Server version : 5.0.18-nt
*/

/*Table structure for table `trans_seq_number` */

DROP TABLE IF EXISTS `trans_seq_number`;

CREATE TABLE `trans_seq_number` (
  `transID` int(11) NOT NULL,
  `transnum` int(11) default NULL,
  `merchentId` int(11) default NULL,
  PRIMARY KEY  (`transID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `trans_seq_number` */

DROP TABLE IF EXISTS `state`;

CREATE TABLE `state` (
  `stateId` bigint(10) unsigned NOT NULL auto_increment,
  `code` varchar(255) NOT NULL default '',
  `title` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`stateId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `state` */

insert into `state` values 

(1,'XX','NA'),

(2,'AL','Alabama'),

(3,'AK','Alaska'),

(4,'AB','Alberta (Cnda)'),

(5,'AZ','Arizona'),

(6,'AR','Arkansas'),

(7,'XX','Australia Capital Terr.'),

(8,'BC','British Columbia (Cnda)'),

(9,'CA','California'),

(10,'XX','Channel Islands (U.K.)'),

(11,'CO','Colorado'),

(12,'CT','Connecticut'),

(13,'DE','Delaware'),

(14,'DC','District of Columbia'),

(15,'XX','England (U.K.)'),

(16,'FL','Florida'),

(17,'GA','Georgia'),

(18,'GU','Guam'),

(19,'HI','Hawaii'),

(20,'ID','Idaho'),

(21,'IL','Illinois'),

(22,'IN','Indiana'),

(23,'IA','Iowa'),

(24,'XX','Isle of Man (U.K.)'),

(25,'XX','Isle of Wight (U.K.)'),

(26,'KS','Kansas'),

(27,'KY','Kentucky'),

(28,'XX','Labrador (Cnda)'),

(29,'LA','Louisiana'),

(30,'ME','Maine'),

(31,'MB','Manitoba (Cnda)'),

(32,'MD','Maryland'),

(33,'MA','Massachusetts'),

(34,'MI','Michigan'),

(35,'MN','Minnesota'),

(36,'MS','Mississippi'),

(37,'MO','Missouri'),

(38,'MT','Montana'),

(39,'NE','Nebraska'),

(40,'NV','Nevada'),

(41,'NB','New Brunswick (Cnda)'),

(42,'NH','New Hampshire'),

(43,'NJ','New Jersey'),

(44,'NM','New Mexico'),

(45,'XX','New Sth Wales (Astrl)'),

(46,'NY','New York'),

(47,'NF','Newfoundland (Cnda)'),

(48,'NC','North Carolina'),

(49,'ND','North Dakota'),

(50,'XX','Northern Ireland (U.K.)'),

(51,'XX','North Territory (Astrl)'),

(52,'NT','Northwest Terr. (Cnda)'),

(53,'NS','Nova Scotia (Cnda)'),

(54,'OH','Ohio'),

(55,'OK','Oklahoma'),

(56,'ON','Ontario (Cnda)'),

(57,'OR','Oregon'),

(58,'PA','Pennsylvania'),

(59,'PR','Puerto Rico'),

(60,'PE','Prince E. Island (Cnda)'),

(61,'QC','Quebec (Cnda)'),

(62,'XX','Queensland (Astrl)'),

(63,'RI','Rhode Island'),

(64,'SK','Saskatchewan (Cnda)'),

(65,'XX','Scotland (U.K.)'),

(66,'XX','South Australia'),

(67,'SC','South Carolina'),

(68,'SD','South Dakota'),

(69,'XX','Tasmania (Astrl)'),

(70,'TN','Tennessee'),

(71,'TX','Texas'),

(72,'UT','Utah'),

(73,'VT','Vermont'),

(74,'XX','Victoria (Astrl)'),

(75,'VA','Virginia'),

(76,'VI','Virgin Islands'),

(77,'XX','Wales (U.K.)'),

(78,'WA','Washington'),

(79,'WV','West Virginia'),

(80,'XX','Western Australia'),

(81,'WI','Wisconsin'),

(82,'WY','Wyoming'),

(83,'YT','Yukon Territory (Cnda)'),

(84,'','');

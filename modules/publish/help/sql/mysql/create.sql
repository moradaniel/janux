SET storage_engine=INNODB;
-- TODO: each table was: ENGINE=MyISAM DEFAULT CHARSET=latin1; set default charset??

-- Table structure for table help
CREATE TABLE help_entry (
  id mediumint(5) unsigned NOT NULL auto_increment,
  code varchar(64) not null,
  categoryId mediumint(5) unsigned,
  label varchar(64) default NULL,
  helpText text,
  sortOrder mediumint(5) unsigned NOT NULL,
  cdate timestamp NOT NULL default '0000-00-00 00:00:00',
  mdate timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (id),
  KEY k_labelName (label),
  KEY k_sortOrder (sortOrder),
  KEY fk_HelpEntry__HelpCategory (categoryId)
);

CREATE TABLE help_category (
  id mediumint(5) unsigned NOT NULL auto_increment,
  title varchar(64) default NULL,
  description text,
  sortOrder mediumint(5) unsigned NOT NULL,
  cdate timestamp NOT NULL default '0000-00-00 00:00:00',
  mdate timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (id),
  KEY k_sortOrder (sortOrder)
);


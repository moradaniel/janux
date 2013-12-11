
-- contains form of payment entries
create table commerce_form_of_payment (
	id integer unsigned not null auto_increment
	,partyId integer unsigned not null
	,position integer unsigned not null
	,primary key (id)
	,unique fop_key (partyId, position)
);

-- contains credit card entries
create table commerce_credit_card (
	formOfPaymentId integer unsigned not null
	,cardTypeId integer unsigned not null
	,cardNumber varchar(255) not null
	,ownerName varchar(255) character set utf8 default null
	,expiration date not null
	,isEncrypted tinyint(1) not null default 0
	,isMasked tinyint(1) not null default 0
	,primary key (formOfPaymentId)
);

-- a minimal currency table
create table commerce_currency (
	id integer unsigned not null auto_increment,
	code varchar(10) not null,
	descr varchar(255),
	primary key (id),
	unique currency_key (code)
) default charset=utf8;

-- the payment method table
-- Currently, this is just our global list of credit cards, with a handy 
-- "extraInfo" field for encoding simple, card-specific validation info
-- (E.g. "16,13:51,52,53" could represent two valid card lengths and three valid
-- card prefixes.)
--
-- NOTE: The "extraInfo" field is a bit hackish in that it leaves it up to
-- the developer to decide how to get data in and out of there and how to interpret it.
-- Nevertheless, it allows that information to be maintained as global data as opposed to
-- being hard-coded god knows where in the application.
--
-- TBD: Whether or not other types of payment mechanisms are needed/allowed in this
-- table (e.g. cash, check, wire transfer). If not, then this table and its
-- corresponding classes should be renamed. If so, then how should the other
-- types be distinguished? Options would be:
--      1) a boolean "isCreditCard" field
--      2) a "type" field (which would then have to be formalized)
--
create table commerce_payment_method (
	id integer unsigned not null auto_increment,
	code varchar(20) not null default '',
	descr varchar(255),
	extraInfo varchar(100),
	primary key (id),
	unique paymentMethod_key (code)
) default charset=utf8;

-- the deposit method table
create table commerce_deposit_method (
	id integer unsigned not null auto_increment,
	code varchar(20) not null,
	descr varchar(255),
	primary key (id),
	unique depositMethod_key (code)
) default charset=utf8;

-- the guarantee method table
create table commerce_guarantee_method (
	id integer unsigned not null auto_increment,
	code varchar(20) not null,
	descr varchar(255),
	primary key (id),
	unique guaranteeMethod_key (code)
) default charset=utf8;

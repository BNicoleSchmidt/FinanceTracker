-- ---------------------------------------------------
-- 	This file creates the Roommates1 Database       --
-- ---------------------------------------------------
/*
   to run the script from mySQL command prompt, type in:
   mysql> \. C:/filename
*/

SET foreign_key_checks=0;

-- delete the database if it already exists
DROP database IF EXISTS roommates1;

CREATE DATABASE IF NOT EXISTS roommates1;

USE roommates1;

-- --------------------------------------------------
-- 			Transactions Table                 --
-- --------------------------------------------------
create table Transactions
(
transactionID	integer		primary key	auto_increment,
name			varchar(16)	not null,
transactionDate		date		not null,
transactionType	char(1)		not null,
amount		decimal(10,2)	not null,	
category		varchar(30)	not null,
description	varchar(255)
)
ENGINE=INNODB;

SET foreign_key_checks=1;

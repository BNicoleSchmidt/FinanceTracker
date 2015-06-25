-- ---------------------------------------------------
-- 	This file creates the Roommates Database       --
-- ---------------------------------------------------

/*
   to run the script from mySQL command prompt, type in:
   mysql> \. C:/filename
*/


SET foreign_key_checks=0;

-- delete the database if it already exists
DROP database IF EXISTS roommates;

CREATE DATABASE IF NOT EXISTS roommates;

USE roommates;


-- --------------------------------------------------
-- 			Transactions Table                 --
-- --------------------------------------------------
create table Transactions
(
transactionID	integer		primary key	auto_increment,
transactionDate		date		not null,
userID		integer		not null,
transactionType	char(1)		not null,
amount		decimal(10,2)	not null,	
categoryID		integer		not null,
description	varchar(255),
foreign key (userID) references Users (userID),
foreign key (categoryID) references Categories (categoryID)
)
ENGINE=INNODB;


-- --------------------------------------------------
-- 			Users Table                        --
-- --------------------------------------------------
create table Users
(
userID		integer		primary key	auto_increment,
name			varchar(20)	not null
)
ENGINE=INNODB;


-- --------------------------------------------------
-- 			Categories Table                   --
-- --------------------------------------------------	
create table Categories
(
categoryID		integer		primary key	auto_increment,
categoryName	varchar(30)	not null
)
ENGINE=INNODB;


-- --------------------------------------------------
-- 			Records View                       --
-- --------------------------------------------------	
CREATE OR REPLACE VIEW records AS

SELECT transactionID, transactionDate, name, transactionType, 
	  amount, categoryName, description 
FROM transactions t, users u, categories c
WHERE t.userID = u.userID and t.categoryID = c.categoryID;


SET foreign_key_checks=1;

/* 
-- This script inserts test data in the Roommates database.
*/
USE roommates;

SET foreign_key_checks=0;

-- delete any existing data to prevent duplicates 
delete from Transactions;
delete from Users;	
delete from Categories;

-- reset the auto increment values
alter table Transactions AUTO_INCREMENT=1;
alter table Users AUTO_INCREMENT=1;
alter table Categories AUTO_INCREMENT=1;				
 
 			-- Transactions table

insert into Transactions (transactionDate, userID, transactionType, amount, categoryID, description) 
                  VALUES ("2015-01-01", 1, 'D', 300, 1, "Miscellaneous expense"); 
insert into Transactions (transactionDate, userID, transactionType, amount, categoryID, description) 
                  VALUES ("2015-03-01", 2, 'D', 500, 2, "Description 2"); 
insert into Transactions (transactionDate, userID, transactionType, amount, categoryID, description) 
                  VALUES ("2015-06-01", 3, 'D', 876, 3, "Description 3"); 

			-- Users table

insert into Users (name) 
                  VALUES ("Alan Turing"); 
insert into Users (name) 
                  VALUES ("My Roommmate"); 
insert into Users (name) 
                  VALUES ("Daen Targaryan");
insert into Users (name) 
                  VALUES ("Carl Gauss"); 

			-- Categories table

insert into Categories (categoryName) VALUES ("Miscellaneous");
insert into Categories (categoryName) VALUES ("House");
insert into Categories (categoryName) VALUES ("Car");
insert into Categories (categoryName) VALUES ("Utilities");

SET foreign_key_checks=1;

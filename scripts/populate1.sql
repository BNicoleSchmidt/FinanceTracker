/* 
-- This script inserts test data in the Roommates1 database.
*/

USE roommates1;

SET foreign_key_checks=0;

-- delete any existing data to prevent duplicates 
delete from Transactions;

-- reset the auto increment values
alter table Transactions AUTO_INCREMENT=1;

 			-- Transactions table

insert into Transactions (name, transactionDate, transactionType, amount, category, description) 
                  VALUES ("Roomie", "2015-01-01", 'D', 300, "Misc", "Miscellaneous expense"); 
insert into Transactions (name, transactionDate, transactionType, amount, category, description) 
                  VALUES ("Buddy", "2015-03-01", 'D', 500, "Car", "Car payment"); 
insert into Transactions (name, transactionDate, transactionType, amount, category, description) 
                  VALUES ("John", "2015-06-11", 'C', 300, "Utility", "Electricity bill");  


SET foreign_key_checks=1;

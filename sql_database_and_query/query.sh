#!/bin/bash
mysql<<EOFMYSQL
use wolvey;
SHOW TABLES;

DESC Bookstore;
DESC Book;
Desc Copy;
Desc Purchase;

SHOW CREATE TABLE Bookstore;
SHOW CREATE TABLE Book;
SHOW CREATE TABLE Copy;
SHOW CREATE TABLE Purchase;

SELECT COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_COLUMN_NAME, REFERENCED_TABLE_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE REFERENCED_COLUMN_NAME IS NOT NULL AND CONSTRAINT_SCHEMA = 'database';

SELECT * 
FROM Bookstore;

SELECT * 
FROM Book;

SELECT * 
FROM Copy;

SELECT * 
FROM Purchase;

SELECT bookstoreName, city 
FROM Bookstore a, Copy b, Book c 
WHERE a.bookstoreID=b.bookstoreID AND b.bookID=c.bookID AND c.bookName='To Kill a Mockingbird';

SELECT bookName, bookstoreName, city, state, price  
FROM Book a, Copy b, Bookstore c  
WHERE a.bookID=b.bookID AND b.bookstoreID=c.bookstoreID AND c.state='AR' 
ORDER BY b.price ASC;

SELECT COUNT(bookstoreID) AS "Total copies" , AVG(price) AS "Average price of copies" 
FROM  Copy  
GROUP BY bookstoreID;

SELECT COUNT(purchaseID) AS "Number of Purchases", AVG(price) AS "Average Price", sum(price) AS 'Total Price' , (sum(price)*0.15)+sum(price) AS 'Total Price with tax'  from Purchase a, Copy b, Bookstore c 
WHERE a.copyID=b.copyID AND b.bookstoreID=c.bookstoreID AND c.bookstoreName='Pearls Books';

SELECT COUNT(purchaseID) AS "Number of Purchases", AVG(price) AS "Average Price", sum(price) AS 'Total Price' , (sum(price)*0.15)+sum(price) AS 'Total Price with tax'  from Purchase a, Copy b 
WHERE a.copyID=b.copyID AND MONTH(a.date)=1;
EOFMYSQL


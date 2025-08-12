#!/bin/bash
mysql<<EOFMYSQL
use wolvey;

CREATE TABLE Bookstore(
bookstoreID INT PRIMARY KEY,
bookstoreName CHAR(25) NOT NULL,
state	CHAR(2) NOT NULL,
city     CHAR(15) NOT NULL
);

CREATE TABLE Book(
bookID INT PRIMARY KEY, 
bookName CHAR(25) NOT NULL,
author CHAR(25) NOT NULL,
publicationDate DATE NOT NULL, 
type CHAR(3),
CHECK (type IN ('fic','non'))
);

CREATE TABLE Copy(
copyID INT PRIMARY KEY,
bookstoreID INT,
bookID INT,
price DECIMAL(4,2) CHECK (price>=5 AND price<=50),
FOREIGN KEY (bookstoreID) 
   REFERENCES Bookstore(bookstoreID)
   ON DELETE RESTRICT
   ON UPDATE CASCADE,
FOREIGN KEY (bookID) REFERENCES Book(bookID) 
   ON DELETE SET NULL
   ON UPDATE CASCADE,
);

create table Purchase(
purchaseID INT PRIMARY KEY,
copyID  INT,
date DATE CHECK (date >='01-01-2025'),
time TIME,
FOREIGN KEY (copyID) REFERENCES Copy(copyID) 
   ON DELETE CASCADE
   ON UPDATE CASCADE
);
EOFMYSQL


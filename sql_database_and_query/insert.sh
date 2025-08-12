#!/bin/bash
mysql<<EOFMYSQL
use wolvey;
INSERT INTO Bookstore (bookstoreID,bookstoreName,state,city) VALUES(0,'Barnes and Noble','MO','Kansas City');
INSERT INTO Bookstore (bookstoreID,bookstoreName,state,city) VALUES(5,'Dickson Street Bookshop','AR','Fayetteville');
INSERT INTO Bookstore (bookstoreID,bookstoreName,state,city) VALUES(11,'Pearls Books','AR','Fayetteville');


INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(9,'Brave New World','Aldous Huxley',STR_To_DATE('02,04,1932','%m,%d,%Y'),'fic');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(10,'To Kill a Mockingbird','Harper Lee',STR_To_DATE('07,11,1960','%m,%d,%Y'),'fic');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(13,'Godel, Escher, Bach','Douglas Hofstadter',STR_To_DATE('01,01,1979','%m,%d,%Y'),'non');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(21,'The Brothers Karamazov','Fyodor Dostoevsky',STR_To_DATE('11,01,1880','%m,%d,%Y'),'fic');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(15,'The Hiding Place','Corrie Ten Boom',STR_To_DATE('01,01,1971','%m,%d,%Y'),'non');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(16,'The Grapes of Wrath','John Steinbeck',STR_To_DATE('04,14,1939','%m,%d,%Y'),'fic');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(37,'Watchmen','Alan Moore',STR_To_DATE('05,13,1986','%m,%d,%Y'),'fic');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(4,'Life of Pi','Yann Martel',STR_To_DATE('09,11,2001','%m,%d,%Y'),'fic');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(29,'Unbroken','Laura Hillenbrand',STR_To_DATE('11,16,2010','%m,%d,%Y'),'non');
INSERT INTO Book (bookID,bookName,author,publicationDate,type) VALUES(42,'The Return of The King','J. R. R. Tolkien',STR_To_DATE('10,20,1955','%m,%d,%Y'),'fic');


INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(0,0,42,25.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(1,0,13,35.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(2,0,37,18.75);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(3,0,10,12.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(4,0,29,15.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(5,5,16,10.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(6,5,42,15.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(7,5,4,8.25);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(8,5,21,21.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(9,11,10,15.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(10,11,9,12.00);
INSERT INTO Copy (copyID,bookstoreID,bookID,price) VALUES(11,11,15,10.00);


INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(0,6,STR_To_DATE('01,15,2025','%m,%d,%Y'),'10:32');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(1,8,STR_To_DATE('01,18,2025','%m,%d,%Y'),'08:45');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(2,11,STR_To_DATE('01,04,2025','%m,%d,%Y'),'14:37');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(3,4,STR_To_DATE('01,29,2025','%m,%d,%Y'),'18:00');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(4,5,STR_To_DATE('02,01,2025','%m,%d,%Y'),'12:18');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(5,1,STR_To_DATE('02,03,2025','%m,%d,%Y'),'21:30');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(6,10,STR_To_DATE('02,09,2025','%m,%d,%Y'),'16:18');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(7,7,STR_To_DATE('02,14,2025','%m,%d,%Y'),'23:00');
INSERT INTO Purchase (purchaseID,copyID,date,time) VALUES(8,9,STR_To_DATE('02,21,2025','%m,%d,%Y'),'20:05');
EOFMYSQL


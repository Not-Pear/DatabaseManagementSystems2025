#!/bin/bash

mysql <<EOFMYSQL
USE wolvey;

DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS Building;

CREATE TABLE Building (
    BuildingId INT PRIMARY KEY,
    Name VARCHAR(25) NOT NULL,
    Address VARCHAR(25),
    HasAC ENUM('y', 'n'),
    HasDining ENUM('y', 'n')
);

CREATE TABLE Room (
    BuildingId INT,
    RoomNumber VARCHAR(10),
    NumBedrooms INT,
    PrivateBathrooms ENUM('y', 'n'),
    HasKitchen ENUM('y', 'n'),
    PRIMARY KEY (BuildingId, RoomNumber),
    FOREIGN KEY (BuildingId) REFERENCES Building(BuildingId)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE Student (
    StudentId INT PRIMARY KEY,
    Name VARCHAR(25) NOT NULL,
    WantsAC ENUM('y', 'n'),
    WantsDining ENUM('y', 'n'),
    WantsKitchen ENUM('y', 'n'),
    WantsPrivateBathroom ENUM('y', 'n')
);

CREATE TABLE Assignment (
    StudentId INT PRIMARY KEY,
    BuildingId INT,
    RoomNumber VARCHAR(10),
    FOREIGN KEY (StudentId) REFERENCES Student(StudentId)
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    FOREIGN KEY (BuildingId, RoomNumber) REFERENCES Room(BuildingId, RoomNumber)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

INSERT INTO Building VALUES (1, 'Oak Hall', '100 Oak Lane', 'y', 'y');
INSERT INTO Building VALUES (2, 'Maple Hall', '200 Maple St', 'n', 'y');
INSERT INTO Building VALUES (3, 'Pine Hall', '300 Pine Ave', 'y', 'n');

INSERT INTO Room VALUES (1, '101A', 2, 'y', 'y');
INSERT INTO Room VALUES (1, '101B', 2, 'n', 'n');
INSERT INTO Room VALUES (1, '102', 3, 'y', 'y');
INSERT INTO Room VALUES (2, '201', 2, 'n', 'n');
INSERT INTO Room VALUES (2, '202', 1, 'y', 'n');
INSERT INTO Room VALUES (2, '203', 3, 'n', 'n');
INSERT INTO Room VALUES (3, '301', 2, 'y', 'y');
INSERT INTO Room VALUES (3, '302', 1, 'n', 'n');
INSERT INTO Room VALUES (3, '303', 3, 'y', 'n');

INSERT INTO Student VALUES (1, 'Shane', 'y', 'y', 'y', 'y');
INSERT INTO Student VALUES (2, 'Max', 'n', 'y', 'n', 'n');
INSERT INTO Student VALUES (3, 'Alice in Chains', 'y', 'n', 'y', 'y');
EOFMYSQL
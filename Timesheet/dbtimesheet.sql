-- Reconstruction de la base de données
DROP DATABASE IF EXISTS dbtimesheet;
CREATE DATABASE dbtimesheet;
USE dbtimesheet;

-- Construction de la table des employés
CREATE TABLE Employee (
	IdEmployee				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Login					char(20)	NOT NULL,
	Password				char(20)	NOT NULL,
	ConnectionNumber		int(4)		NOT NULL DEFAULT 0
) ENGINE = InnoDB;

INSERT INTO Employee (IdEmployee, Login, Password) VALUES ( 1, 'Jean', 'Pierre' );

SELECT * FROM Employee;

-- Construction de la tables des projets
CREATE TABLE Project (
	IdProject		int(4)		PRIMARY KEY AUTO_INCREMENT,
	IdEmployee		int(4)		NOT NULL REFERENCES Employee(IdEmployee),
	ProjectName		char(30)	NOT NULL
) ENGINE = InnoDB;

INSERT INTO Project (IdProject, IdEmployee, ProjectName) VALUES ( 1, 1, 'Desktop' );

SELECT * FROM Project;

-- Construction de la table des activités
CREATE TABLE Activity (
	IdActivity			int(4)		PRIMARY KEY AUTO_INCREMENT,
	IdEmployee			int(4)		NOT NULL REFERENCES Employee(IdEmployee),
	IdProject			int(4)		NOT NULL REFERENCES Project(IdProject),
	ActivityDescription	char(30)	NOT NULL,
	ActivityDate		date    NOT NULL
) ENGINE = InnoDB;

INSERT INTO Activity (IdActivity, IdEmployee, IdProject, ActivityDescription, ActivityDate) VALUES ( 1, 1, 1, 'Windows installation', '2020-01-24' );

SELECT * FROM Activity;

-- Construction de la table des timesheet
CREATE TABLE Timesheet (
	IdTimesheet			int(4)		PRIMARY KEY AUTO_INCREMENT,
	IdActivity			int(4)		NOT NULL REFERENCES Activity(IdActivity),
	Hours				int(4)    	NOT NULL
) ENGINE = InnoDB;

INSERT INTO Timesheet (IdTimesheet, IdActivity, Hours) VALUES ( 1, 1, 3 );

SELECT * FROM Timesheet;

-- Construction de la table des coût
CREATE TABLE Cost (
	IdCost			int(4)		PRIMARY KEY AUTO_INCREMENT,
	IdTimesheet		int(4)		NOT NULL REFERENCES Employee(IdTimesheet),
	Cost			int(4)		NOT NULL
) ENGINE = InnoDB;

INSERT INTO Cost (IdCost, IdTimesheet, Cost) VALUES (1, 1, 50);

SELECT * FROM Cost;

/*Alexander Zhao (ayz10) - CS336 Homework 2
Question 1*/
CREATE TABLE Employee (
	id INT,
    name VARCHAR(30),
    age INT,
    salary FLOAT,
    PRIMARY KEY(id)
);
CREATE TABLE Dept (
	id INT,
    name VARCHAR(30),
    budget FLOAT,
    PRIMARY KEY(id)
);
CREATE TABLE WorksIn (
	percentTime FLOAT,
    employee INT,
    dept INT,
    PRIMARY KEY (employee, dept),
    FOREIGN KEY (employee) REFERENCES Employee(id),
    FOREIGN KEY (dept) REFERENCES Dept(id)
);

/*Question 2*/
/*a*/
SELECT DISTINCT Supplier.name 
FROM (Suppliers INNER JOIN Catalog ON Suppliers.id = Catalog.sid)
	INNER JOIN Parts ON Catalog.pid = Parts.id
WHERE Parts.color = "red";

/*b*/
SELECT DISTINCT Suppliers.id
FROM (Suppliers INNER JOIN Catalog ON Suppliers.id = Catalog.sid)
	INNER JOIN Parts ON Catalog.pid = Parts.id
WHERE Parts.color = "red" OR Supplier.address = "123 College Ave."
;

/*c*/
SELECT DISTINCT Catalog.sid
FROM Catalog INNER JOIN Parts ON Catalog.pid = Parts.id
WHERE Parts.color = "red" OR Parts.color ="green"
GROUP BY Catalog.sid 
HAVING COUNT(DISTINCT Parts.color) = 2
;

/*d*/
SELECT DISTINCT Catalog.sid
FROM Catalog INNER JOIN Parts ON Catalog.pid = Parts.id
WHERE Parts.color = "red" OR Parts.color ="green"
GROUP BY Catalog.sid 
HAVING COUNT(DISTINCT CASE WHEN Parts.color = 'red' THEN Parts.color END) = 1 
OR COUNT(DISTINCT CASE WHEN Parts.color = 'green' THEN Parts.color END) = 1
;

/*e*/
SELECT DISTINCT Parts.id
FROM (Parts INNER JOIN Catalog ON Parts.id = Catalog.pid)
	INNER JOIN Suppliers ON Catalog.sid = Suppliers.id
WHERE Suppliers.name = "Toshiba"
	AND Catalog.cost = (SELECT MAX(cost) FROM Catalog WHERE Catalog.sid = Suppliers.id)
;

/*Question 3*/
/*a*/
SELECT DISTINCT Students.name
FROM (Students INNER JOIN Takes ON Students.id = Takes.sid)
	INNER JOIN Classes ON Takes.cname = Classes.name AND Classes.pid = (
		SELECT id FROM Profs WHERE name = "Marie Curie"
	)
WHERE Students.level = "JR"
;

/*b*/
SELECT DISTINCT Classes.name
FROM Classes INNER JOIN Takes ON Classes.name = Takes.cname
WHERE Classes.room = "Tillet 232" 
	OR (SELECT cname FROM Takes GROUP BY cname HAVING COUNT(DISTINCT sid)) >= 5
;
/*c*/
SELECT DISTINCT Profs.id 
FROM Profs INNER JOIN Classes ON Profs.id = Classes.pid
WHERE EXISTS (
	SELECT DISTINCT room FROM Classes
	WHERE EXISTS (
		SELECT * FROM Classes WHERE Classes.pid = Profs.id
	)
);

/*d*/
SELECT level, AVG(age) AS avg_age
FROM Students
GROUP BY level
;

/*e*/
SELECT Profs.name, COUNT(DISTINCT Classes.name)
FROM Profs INNER JOIN Classes ON Profs.id = Classes.pid
WHERE EXISTS (
	SELECT * FROM Classes WHERE room = "Tillet 232" AND Classes.pid = Profs.id
)
;

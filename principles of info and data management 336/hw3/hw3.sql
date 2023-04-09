SHOW databases;
CREATE DATABASE hw3;
USE hw3;

CREATE TABLE Departments (
	name VARCHAR(50),
    campus VARCHAR(50),
    PRIMARY KEY(name)
);

CREATE TABLE Students (
	first_name VARCHAR(50),
    last_name VARCHAR(50),
    id INT,
    primary key(id)
);

create table classes (
	name varchar(100),
    credits int,
    primary key(name)
);

create table majors (
	sid int,
    dname varchar(50),
    primary key(sid, dname),
    foreign key (sid) references students(id)
);

create table minors (
	sid int,
    dname varchar(50),
    primary key(sid, dname),
	foreign key (sid) references students(id)
);

create table istaking (
	sid int,
    dname varchar(100),
    primary key(sid, dname),
    foreign key (dname) references classes(name)
);

create table hastaken (
	sid int,
    dname varchar(60),
    grade varchar(1),
    primary key(sid, dname),
    foreign key (dname) references classes(name)
);

create view studentMajors as
SELECT DISTINCT id, group_concat(dname) as majors FROM students inner join majors on students.id = majors.sid group by id; 

create view studentMinors as
SELECT DISTINCT id, group_concat(dname) as minors FROM students inner join minors on students.id = minors.sid group by id; 

create view majorMinorList as
select studentmajors.id, first_name, last_name, majors, minors from studentMajors inner join studentMinors on studentMajors.id =studentMinors.id inner join students on students.id = studentMinors.id;

create view hasTakenGradeNum as 
select students.id,
CASE 
	when grade = 'A' THEN 4
    when grade = 'B' THEN 3
    when grade = 'C' THEN 2
    when grade = 'D' THEN 1
    when grade = 'F' THEN 0
end as grade_num
from students inner join hastaken on students.id = hastaken.sid;

create view avg_gpas as
select id, avg(grade_num) as avg_gpa from hastakengradenum group by id;

create view details_nc as
select majorminorlist.id, first_name, last_name, majors, minors, avg_gpa from majorminorlist left join avg_gpas on majorminorlist.id = avg_gpas.id
union
select majorminorlist.id, first_name, last_name, majors, minors, avg_gpa from majorminorlist right join avg_gpas on majorminorlist.id = avg_gpas.id;
;

create view creditsTaken as
select id, sum(credits) as total_credits from students inner join hastaken on students.id = hastaken.sid
	inner join classes on hastaken.dname = classes.name
    group by students.id;
    
create view studentsfulldetails as
select details_nc.id, first_name, last_name, majors, minors, avg_gpa, total_credits from details_nc left join creditsTaken on details_nc.id = creditsTaken.id
union
select details_nc.id, first_name, last_name, majors, minors, avg_gpa, total_credits from details_nc right join creditsTaken on details_nc.id = creditsTaken.id;

create view onecolumndept as
select * from majors 
union
select * from minors;
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mattanderson
 * Created: 3/05/2021
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  mattanderson
 * Created: 3/05/2021
 */

create table Paper (
    Paper_ID int(8) zerofill NOT NULL  auto_increment,
    Department varchar(50) not null,
    Paper_Title varchar(50) not null,
    Paper_Code varchar(10) not null,
    Description varchar(90),
    Review varchar(90),
    constraint Paper_PK primary key (Paper_ID)
);

create table Tutor (
    Tutor_ID int(8) ZEROFILL NOT NULL auto_increment,
    Paper_ID int(8) zerofill NOT NULL,
    Name_ varchar(100) not null,
    Email_Address varchar(50) not null,
    Description varchar(100),
    Review varchar(90),
    constraint Tutor_PK primary key (Tutor_ID),
    constraint Paper_FK foreign key (Paper_ID)
    references Paper(Paper_ID)
);

create table Student (
    Student_ID int(7) unique not null,
    First_Name varchar(25) not null,
    Last_Name varchar(25) not null,
    Mobile_Phone_Number integer(10),
    Email_Address varchar(50) not null,
    Password varchar(25) not null,
    constraint Student_PK primary key (Student_ID)
);

create table Library (
    Library_ID int(8) ZEROFILL unique not null auto_increment,
    Library_Title varchar(50) not null,
    Description varchar(100),
    Review varchar(90),
    constraint Library_Pk primary key (Library_ID)
);

create table Hall (
    Hall_ID int(8) ZEROFILL unique not null auto_increment,
    Hall_Title varchar(50) not null,
    Description varchar(100),
    Review varchar(90),
    constraint Hall_PK primary key (Hall_ID)
);

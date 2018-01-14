CREATE TABLE Person (
    SSN int,
    P_name varchar(20),
    BirthDate date,
    Race varchar(20),
    Gender varchar(10),
    Profession varchar(20),
    MailAddress varchar(20),
    Email varchar(20),
    HomeNumber number(10),
    WorkNumber number(10),
    CellNumber number(10),
    MailingList char,
    primary key (SSN)
);

CREATE TABLE E_Contact (
    SSN int,
    E_name varchar(20),
    MailAddress varchar(20),
    Email varchar(20),
    HomeNumber number(10),
    WorkNumber number(10),
    CellNumber number(10),
    Relation varchar(20),
    foreign key (SSN) references Person on delete cascade,
    primary key (SSN,E_name,email)
);

CREATE TABLE Ext_Organization (
    O_name varchar(20),
    Mailing_Address varchar(20),
    ContactPerson varchar(20),
    PhoneNumber number(10),
    Anonymous char,
    primary key (O_name)
);

CREATE TABLE Affiliated (
    SSN int,
    O_name varchar(20),
    foreign key (O_name) references Ext_Organization on delete cascade,
    foreign key (SSN) references Person on delete cascade,
    primary key (SSN)
);

CREATE TABLE Church (
    O_name varchar(20),
    Religious_Affiliation varchar(20),
    foreign key (O_name) references Ext_Organization on delete cascade,
    primary key (O_name)
);

CREATE TABLE Business (
    O_name varchar(20),
    B_type varchar(20),
    B_size int,
    Company_website varchar(30),
    foreign key (O_name) references Ext_Organization on delete cascade,
    primary key (O_name)
);

CREATE TABLE Client (
    SSN int,
    Doctor_Name varchar(20),
    Doctor_Number number(10),
    Attorney_Name varchar(20),
    Attorney_Number number(10),
    Date_Assisgned date,
    foreign key (SSN) references person on delete cascade,
    primary key (SSN)
);

CREATE TABLE Client_Need (
    SSN int,
    Need varchar(20),
    N_value int,
    foreign key (SSN) references Client on delete cascade,
    primary key (SSN,Need)
);

CREATE TABLE Volunteer (
    SSN int,
    Date_Joined date,
    Recent_Date date,
    Recent_Location varchar(20),
    foreign key (SSN) references person on delete cascade,
    primary key (SSN)
);

CREATE TABLE Employee (
    SSN int,
    Salary number(10,2),
    Marital_Status varchar(12),
    HireDate date,
    foreign key (SSN) references person on delete cascade,
    primary key (SSN)
);

CREATE TABLE Donor (
    SSN int,
    Anonymous char,
    foreign key (SSN) references person on delete cascade,
    primary key (SSN)
);

CREATE TABLE Insurance (
    Policy_ID varchar(20),
    SSN int,
    Provider_ID varchar(20),
    ProviderAddress varchar(20),
    I_type varchar(10),
    foreign key (SSN) references Client on delete cascade,
    primary key (Policy_ID)
);

CREATE TABLE Team (
    T_name varchar(20),
    T_type varchar(20),
    DateFormed date,
    primary key (T_name)
);

CREATE TABLE Care (
    SSN int,
    T_name varchar(20),
    Active char,
    foreign key (SSN) references Client on delete cascade,
    foreign key (T_name) references Team on delete cascade,
    primary key (SSN,T_name)
);

CREATE TABLE Serve (
    SSN int,
    T_name varchar(20),
    S_month int,
    No_of_hours int,
    Active char,
    foreign key (SSN) references volunteer on delete cascade,
    foreign key (T_name) references Team on delete cascade,
    primary key (SSN,T_name,S_month)
);

CREATE TABLE Team_Lead (
    T_name varchar(20),
    SSN int,
    foreign key (SSN) references volunteer on delete cascade,
    foreign key (T_name) references Team on delete cascade,
    primary key (T_name)
);

CREATE TABLE Sponsor (
    O_name varchar(20),
    T_name varchar(20),
    foreign key (O_name) references Ext_Organization on delete cascade,
    foreign key (T_name) references Team on delete cascade,
    primary key (O_name,T_name)
);

CREATE TABLE Report (
    T_name varchar(20),
    SSN int,
    R_date date,
    R_description varchar(50),
    foreign key (SSN) references Employee on delete cascade,
    foreign key (T_name) references Team on delete cascade,
    primary key (T_name)
);

CREATE TABLE Expenses (
    SSN int,
    E_date date,
    Amount number(10,2),
    E_description varchar(50),
    foreign key (SSN) references Employee on delete cascade,
    primary key (SSN,E_date,Amount,E_description)
);

CREATE TABLE Donations (
    SSN int,
    D_date date,
    Amount number(10,2),
    D_type varchar(12),
    Campaign varchar(20),
    foreign key (SSN) references Donor on delete cascade,
    primary key (SSN,D_date,Amount,D_type)
);

CREATE TABLE D_Check (
    SSN int,
    D_date date,
    Amount number (10,2),
    D_type varchar(12),
    Check_No varchar(10),
    foreign key (SSN,D_date,Amount,D_type) references Donations on delete cascade,
    primary key (SSN,D_date,Amount,D_type,Check_No)
);

CREATE TABLE D_Card (
    SSN int,
    D_date date,
    Amount number(10,2),
    D_type varchar(12),
    Card_No varchar(20),
    Card_Type varchar(15),
    Exp_Date date,
    foreign key (SSN,D_date,Amount,D_type) references Donations on delete cascade,
    primary key (SSN,D_date,Amount,D_type,Card_No)
);

CREATE TABLE Org_Donations (
    O_name varchar(20),
    OD_date date,
    Amount number(10,2),
    OD_type varchar(12),
    Campaign varchar(20),
    foreign key (O_name) references Ext_Organization on delete cascade,
    primary key (O_name,OD_date,Amount,OD_type)
);

CREATE TABLE Org_Check (
    O_name varchar(20),
    OD_date date,
    Amount number(10,2),
    OD_type varchar(12),
    Check_No varchar(10),
    foreign key (O_name,OD_date,Amount,OD_type) references Org_Donations on delete cascade,
    primary key (O_name,OD_date,Amount,OD_type,Check_No)
);

CREATE TABLE Org_Card (
    O_name varchar(20),
    OD_date date,
    Amount number(10,2),
    OD_type varchar(12),
    Card_No varchar(20),
    Card_Type varchar(15),
    Exp_Date date,
    foreign key (O_name,OD_date,Amount,OD_type) references Org_Donations on delete cascade,
    primary key (O_name,OD_date,Amount,OD_type,Card_No)
);

CREATE INDEX CN_Index ON Client_Need (SSN);

CREATE INDEX I_Index ON Insurance (I_type);

CREATE INDEX C_Index ON Care (T_name);

CREATE INDEX S_Index ON Serve (T_name);

CREATE INDEX SP_Index ON Sponsor (O_name);

CREATE INDEX R_Index ON Report (SSN);

CREATE INDEX E_Index ON Expenses (SSN);

CREATE INDEX D_Index ON Donations (SSN);
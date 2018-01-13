/* create tables */

create table Person(
    Email           varchar(20),
    PersonName      varchar(20),
    PersonAddress   varchar(20),
    primary key(Email)
);

create table Author(
    Email           varchar(20),
    Genre           varchar(20),
    AuthorID        varchar(20),
    foreign key(Email) references Person,
    primary key(Email)
);

create table Customer(
    Email           varchar(20),
    Details         varchar(20),
    foreign key(Email) references Person,
    primary key(Email)
);

create table CustomerNumber(
    Email               varchar(20),
    CustomerPhoneNumber real,
    foreign key(Email) references Person,
    primary key(Email, CustomerPhoneNumber)
);

create table Publisher(
    PublisherName           varchar(20),
    Address                 varchar(20),
    PublisherPhoneNumber    real,
    primary key(PublisherName)
);

create table Press(
    PublisherName   varchar(20),
    InkUsed         varchar(20),
    PrintingCost    real,
    foreign key(PublisherName) references Publisher,
    primary key(PublisherName, InkUsed, PrintingCost)
);

create table WritesBook(
    BookID      real,
    Email       varchar(20),
    BookYear    real,
    Title       varchar(20),
    foreign key(Email) references Person,
    primary key(BookID)
);

create table Buys(
    Email   varchar(20),
    BookID  real,
    Price   real,
    foreign key(Email) references Person,
    foreign key(BookID) references WritesBook,
    primary key(Email, BookID)
);

create table Publishes(
    BookID          real,
    PublisherName   varchar(20),
    foreign key(BookID) references WritesBook,
    foreign key(PublisherName) references Publisher,
    primary key(BookID, PublisherName)
);


/* insert person */

insert into Person values('sam@ou.edu', 'Sam', '740 St');
insert into Person values('ryan@ou.edu', 'Ryan', '610 Ave');
insert into Person values('amy@ou.edu', 'Amy', '71 Paseo');
insert into Person values('julia@ou.edu', 'Julia', '618 Plaza');
insert into Person values('sofia@ou.edu', 'Sofia', '714 Cir');
insert into Person values('valter@ou.edu', 'Valter', '23 St');
insert into Person values('brandon@ou.edu', 'Brandon', '140 St');
insert into Person values('hunt@ou.edu', 'Hunt', '71 Plaza');
insert into Person values('rock@ou.edu', 'Rock', '9 Ave');
insert into Person values('sai@ou.edu', 'Sai', '2 Blvd');

/* insert author */

insert into Author values('sam@ou.edu', 'Mystery', 1);
insert into Author values('julia@ou.edu', 'Fiction', 2);
insert into Author values('hunt@ou.edu', 'Mystery', 3);
insert into Author values('brandon@ou.edu', 'Comedy', 4);

/* insert customer */

insert into Customer values('amy@ou.edu', 'school');
insert into Customer values('rock@ou.edu', 'work');
insert into Customer values('sai@ou.edu', 'personal');
insert into Customer values('valter@ou.edu', 'personal');
insert into Customer values('sofia@ou.edu', 'work');
insert into Customer values('ryan@ou.edu', 'work');

/* insert customernumber */

insert into CustomerNumber values('amy@ou.edu', 7178);
insert into CustomerNumber values('rock@ou.edu', 6811);
insert into CustomerNumber values('sai@ou.edu', 6969);
insert into CustomerNumber values('valter@ou.edu', 0918);
insert into CustomerNumber values('valter@ou.edu', 0919);
insert into CustomerNumber values('sofia@ou.edu', 2145);
insert into CustomerNumber values('sofia@ou.edu', 4521);
insert into CustomerNumber values('ryan@ou.edu', 6061);
insert into CustomerNumber values('ryan@ou.edu', 2121);
insert into CustomerNumber values('ryan@ou.edu', 3343);

/* insert publisher */

insert into Publisher values('Cambridge', '21 St', 1421);
insert into Publisher values('Oxford', '2 Elm', 8824);

/* insert press */

insert into Press values('Cambridge', 'Black', 9);
insert into Press values('Cambridge', 'Multi', 20);
insert into Press values('Oxford', 'Black', 8);
insert into Press values('Oxford', 'Multi', 15);

/* insert writesbook */

insert into WritesBook values(1, 'sam@ou.edu', 2010, 'Holmes');
insert into WritesBook values(2, 'julia@ou.edu', 2015, 'Space Monkeys');
insert into WritesBook values(3, 'hunt@ou.edu', 2009, 'Kidnaps');
insert into WritesBook values(4, 'hunt@ou.edu', 2015, 'FBI');
insert into WritesBook values(5, 'brandon@ou.edu', 2017, 'Cats');
insert into WritesBook values(6, 'brandon@ou.edu', 2016, 'Dogs');
insert into WritesBook values(7, 'brandon@ou.edu', 2006, 'Babies');

/* insert buys */

insert into Buys values('amy@ou.edu', 7, 13);
insert into Buys values('rock@ou.edu', 1, 20);
insert into Buys values('sai@ou.edu', 3, 9);
insert into Buys values('valter@ou.edu', 2, 10);
insert into Buys values('sofia@ou.edu', 1, 24);
insert into Buys values('sofia@ou.edu', 5, 25);
insert into Buys values('ryan@ou.edu', 4, 7);
insert into Buys values('ryan@ou.edu', 3, 14);

/* insert publishes */

insert into Publishes values(1, 'Oxford');
insert into Publishes values(2, 'Oxford');
insert into Publishes values(3, 'Cambridge');
insert into Publishes values(4, 'Oxford');
insert into Publishes values(5, 'Cambridge');
insert into Publishes values(6, 'Cambridge');
insert into Publishes values(7, 'Oxford');

/* print tables */

select * from Person;
select * from Author;
select * from Customer;
select * from CustomerNumber;
select * from Publisher;
select * from Press;
select * from WritesBook;
select * from Buys;
select * from Publishes;

/* query 1 - one query must involve more than one relation */
--Alternative 1
--find the name of a customer who buys a book written by the author "SAM"
select personname from person where email in(select email from buys where bookid in(select bookid from writesbook where email in(select email from author where email in (select email from person where email='sam@ou.edu'))));

--Alternative 2
--/* Find all the customers who bought the 'Holmes' book. */
select personname from person where email in (select email from buys where bookid = '1');

--Alternative 3
--Find Name,Genre of each Author
select personname,genre from person,Author where person.email=Author.email;

--
--/* query 2 */
--Involved in aggregation function
--Find the Author_ID and their Genre for the books published by publisher whose Phonenumber is 1421
select authorid,genre from author where email in(select email from writesbook where bookid in(select bookid from publishes where publishername in(select publishername from publisher where publisherphonenumber='1421')));

--/* query 3 - one query must involve a group by clause */
--/* For each customer, display how much they spent on books. */
select Email, sum(Price) from Buys group by Email;

--/* query 4 */
--/* Find all the books that were published by Cambridge. */
select Title from WritesBook where BookID in(select distinct BookID from Publishes where PublisherName not in ('Oxford'));

--/* query 5 */
--Violation of Parent Key
--Change exsting Email 'sam@ou.edu' to 775 in Person table
update Person set Email = 775 where Email = 'sam@ou.edu';

--/* query 6 */
--Violation of Foreign Key
--Insert a record into Buys with Email 'ivan@ou.edu', bookId is 5 and Price of book is 20
insert into Buys values('ivan@ou.edu', 5, 20);


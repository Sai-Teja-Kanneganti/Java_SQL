
CREATE TABLE Author (
    aid int,
    aname varchar(20),
    age int,
    number_of_publication int,
    primary key (aid)
);

CREATE TABLE Translator (
    tid int,
    tname varchar(20),
    salary real,
    primary key (tid)
);

CREATE TABLE Book (
    btitle varchar(25),
    category varchar(20),
    release_year int,
    edition int,
    tid int,
    primary key(btitle),
    foreign key (tid) references Translator
);

CREATE TABLE Customer (
    cid int,
    cname varchar(20),
    number_of_orders int,
    clevel varchar(20),
    primary key(cid)
);

CREATE TABLE written (
    aid int,
    btitle varchar(25),
    foreign key (aid) references Author,
    foreign key (btitle) references Book,
    primary key(aid,btitle)
);

CREATE TABLE Purchased (
    cid int,
    btitle varchar(25),
    foreign key (cid) references Customer on delete cascade,
    foreign key (btitle) references Book,
    primary key(cid,btitle)
);

INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (1, 'Morgan', 60, 26);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (2, 'Cruz', 38, 8);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (3, 'Adams', 30, 5);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (4, 'Perry', 65, 24);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (5, 'Derek', 50, 10);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (6, 'Derek', 32, 4);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (7, 'Lewis', 70, 20);
INSERT INTO Author (aid,aname,age,number_of_publication) VALUES (8, 'Alice', 47, 7);

INSERT INTO TRANSLATOR (tid,tname,salary) VALUES (101, 'Mark', 58000);
INSERT INTO TRANSLATOR (tid,tname,salary) VALUES (102, 'Black', 25000);
INSERT INTO TRANSLATOR (tid,tname,salary) VALUES (103, 'Ed', 30000);
INSERT INTO TRANSLATOR (tid,tname,salary) VALUES (104, 'Mark', 82000);

INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('Jurassic Park','Novel',8,1984,101);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('The Power of Habit','Business',10,2001,102);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('Heidi','Children',9,1960,104);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('Anna Karenina','Novel',20,1994,103);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('War and Peace','Novel',17,1968,104);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('The Lion King','Children',5,1990,103);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('Rich Dad','Business',9,1997,103);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('The Goal','Business',14,1963,103);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('Rework','Business',6,2003,102);
INSERT INTO Book (btitle,category,Edition,release_year,tid) VALUES ('Beautiful Mind','Phycology',3,1979,102);

INSERT INTO written (aid,btitle) VALUES (4, 'Jurassic Park');
INSERT INTO written (aid,btitle) VALUES (7, 'The Goal');
INSERT INTO written (aid,btitle) VALUES (1, 'Heidi');
INSERT INTO written (aid,btitle) VALUES (8, 'Rework');
INSERT INTO written (aid,btitle) VALUES (5, 'Rework');
INSERT INTO written (aid,btitle) VALUES (1, 'Anna Karenina');
INSERT INTO written (aid,btitle) VALUES (2, 'Rework');
INSERT INTO written (aid,btitle) VALUES (3, 'The Lion King');
INSERT INTO written (aid,btitle) VALUES (4, 'Rich Dad');
INSERT INTO written (aid,btitle) VALUES (8, 'The Power of Habit');

INSERT INTO customer (cid,cname,number_of_orders,clevel) VALUES (200, 'John', 10, 'Children');
INSERT INTO customer (cid,cname,number_of_orders,clevel) VALUES (201, 'Emily', 7, 'Adult');
INSERT INTO customer (cid,cname,number_of_orders,clevel) VALUES (202, 'Ashely', 19, 'Adult');
INSERT INTO customer (cid,cname,number_of_orders,clevel) VALUES (203, 'Edward', 3, 'Children');
INSERT INTO customer (cid,cname,number_of_orders,clevel) VALUES (204, 'Wu', 12, 'Adult');

INSERT INTO purchased (cid,btitle) VALUES (200, 'Anna Karenina');
INSERT INTO purchased (cid,btitle) VALUES (204, 'Heidi');
INSERT INTO purchased (cid,btitle) VALUES (203, 'War and Peace');
INSERT INTO purchased (cid,btitle) VALUES (204, 'Rework');
INSERT INTO purchased (cid,btitle) VALUES (201, 'Anna Karenina');
INSERT INTO purchased (cid,btitle) VALUES (204, 'The Lion King');
INSERT INTO purchased (cid,btitle) VALUES (200, 'The Lion King');
INSERT INTO purchased (cid,btitle) VALUES (202, 'War and Peace');

-- Problem 2 (1)
select * from author; 
select * from Translator;
select * from book;
select * from written;
select * from customer;
select * from purchased;


select btitle,category,edition from BOOK where edition >10 and tid in(Select tid from Translator where tname ='Mark');
CREATE INDEX Book_Edition_Index ON Book (EDITION);
select btitle,category,edition from BOOK where edition >10 and tid in(Select tid from Translator where tname ='Mark');
--Problem 2 (2)
--Select cname, clevel from Customer where cid in (Select cid from Purchased where bTitle in(Select bTitle from written where aid in(Select aid from Author where aname='Adams')));
--
--
----Problem 3
--select btitle,category,edition from BOOK where edition >10 and tid in(Select tid from Translator where tname ='Mark');
--
----Problem 4
--select aname from Author where aid in(select distinct aid from Written where bTitle in(select bTitle from Written group by bTitle having COUNT(*)>2));
--
----Problem 5
--select tname from translator where tid in(select tid from Book group by tid having count(*)>2);
--
----Problem 6
--select category, count(*) from BOOK group by category having COUNT(*)=2;
--
----Problem 7
----select bookTitle, bcategory from Book where  bookTitle in(select bookTitle from Written where aid in(select aid from Written group by aid having count(*)=2));
--select a.aid, a.aname from author a where a.aid IN(select  w.aid from book b, written w where b.btitle=w.btitle and w.aid IN (select unique(a.aid) from author a, written w, book b where a.aid = w.aid and w.btitle = b.btitle group by a.aid having count(aname) = 2) group by b.category, w.aid having count(b.category) =2);
--
--
----Problem 8
--select category, avg(edition) from book group by category;
--
----Problem 9
--update translator set salary = salary * 1.10 where tid in (select tid from book where btitle='Beautiful Mind');
--
----Problem 10
--delete from customer where clevel = 'Children';

--Crete Index
--CREATE INDEX book_category_Index ON BOOK (category);

--See Indexed Table Query
--SELECT * FROM BOOK USE INDEX (Book_Category_Index,SYS_C00170043) WHERE col1=1 AND col2=2 AND col3=3;
--

--SELECT count(cid) from purchased group by cid;
----Option2 Query
--UPDATE translator set salary = (salary)*1.10 where tid in(select tid from book where btitle in (select btitle from written where aid in(select aid from author where aname='Morgan')));
--
--update translator set salary = salary*1.10 where TID in (select TID from Book where btitle in (select btitle from written where AID in (select AID from author where aname = 'Morgan')));
--
----Option2 Query using join
--SELECT t.tid, b.btitle, w.aid, a.aname FROM Translator t, Book b, Written w, Author a WHERE a.aid=w.aid and b.btitle=w.btitle and b.tid=t.tid and a.aname='Morgan';
--
--UPDATE translator set salary=salary*1.05 where tid in((select TID from book group by TID having count(TID)>=3) minus (select TID from Book where btitle in (select btitle from written where AID in (select AID from author where aname = 'Morgan'))));
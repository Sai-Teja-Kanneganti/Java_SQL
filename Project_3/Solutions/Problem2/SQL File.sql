--Option1
create or replace PROCEDURE TEST(
CID_1 in NUMBER,                 --declaring the variables
CNAME_1 in VARCHAR2, 
CLEVEL_1 in VARCHAR2) AS
cid varchar2(20);
cate varchar(20);
z varchar2(20);
y NUMBER;
x VARCHAR2(20);

--Function to insert values into Customer Table
PROCEDURE INSERT_CUSTOMER (CID_1 IN NUMBER,CNAME_1 IN VARCHAR2, NUMBER_OF_ORDERS_1 IN NUMBER,  CLEVEL_1 IN VARCHAR2) AS
 BEGIN
 --Inserting the values into the customer Table
 INSERT INTO CUSTOMER(CID, CNAME,NUMBER_OF_ORDERS,CLEVEL) VALUES (CID_1, CNAME_1,y,CLEVEL_1);
 END;

BEGIN
cate := CLEVEL_1;


FOR CID IN (SELECT CLEVEL into z FROM CUSTOMER)
LOOP
IF cate='Children' THEN
SELECT AVG(NUMBER_OF_ORDERS)INTO y FROM CUSTOMER WHERE CLEVEL=cate;
ELSIF cate='Adult' THEN
SELECT AVG(NUMBER_OF_ORDERS)INTO y FROM CUSTOMER WHERE CLEVEL=cate;
ELSE
SELECT AVG(NUMBER_OF_ORDERS)into y FROM CUSTOMER;
END IF;
END LOOP;

INSERT_CUSTOMER(CID_1, CNAME_1,y,CLEVEL_1);                                      --inserting the value
DBMS_OUTPUT.PUT_LINE('CID' || ' ' || 'CNAME' || ' ' || 'NUMBER_OF_ORDERS' || ' ' || 'CLEVEL');
 FOR object IN (select * from CUSTOMER order by CID)
 LOOP
 DBMS_OUTPUT.PUT_LINE(object.CID || ' ' || object.CNAME || ' ' || object.NUMBER_OF_ORDERS || ' ' || object.CLEVEL);
 END LOOP;

END TEST;




--Option2
create or replace PROCEDURE TEST2(userinput in Varchar2) AS 
--Declaring Variables
BEGIN
DBMS_OUTPUT.PUT_LINE('Salaries before update');
DBMS_OUTPUT.PUT_LINE('TID' || ' ' || 'TNAME' || ' ' || 'Salary');
 FOR object IN (select * from Translator order by TID)
 LOOP
 DBMS_OUTPUT.PUT_LINE(object.TID || ' ' || object.TNAME || ' ' || object.Salary);
 END LOOP;

update translator set salary = salary*1.10 where TID in (select TID from Book where btitle in (select btitle from written where AID in (select AID from author where aname = userinput)));
UPDATE translator set salary=salary*1.05 where tid in((select TID from book group by TID having count(TID)>=3) minus (select TID from Book where btitle in (select btitle from written where AID in (select AID from author where aname = userinput))));
update translator set salary = salary*1.02 where TID in (select TID from translator where TID not in ((select TID from book group by TID having count(TID)>=3) union (select TID from Book where btitle in (select btitle from written where AID in (select AID from author where aname = userinput)))));

DBMS_OUTPUT.PUT_LINE('Salaries After update');
DBMS_OUTPUT.PUT_LINE('TID' || ' ' || 'TNAME' || ' ' || 'Salary');
 FOR object IN (select * from Translator order by TID)
 LOOP
 DBMS_OUTPUT.PUT_LINE(object.TID || ' ' || object.TNAME || ' ' || object.Salary);
 END LOOP;
END TEST2;
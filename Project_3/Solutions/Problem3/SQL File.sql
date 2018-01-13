--Module 1
--This Anonymous block updates the number of orders each customer has based on the number of books they have purchased.
--User is supposed to give input about the number of purchases done by the customer.
--If the Customer Purchases 1 book then number of orders is incremented by 1.
--If the Customer Purchases 2 book then number of orders is incremented by 3.
--If the Customer Purchases 3 book then number of orders is incremented by 5.
DECLARE
cnumber number;

BEGIN
cnumber := &cnumber;
    IF cnumber=3 THEN
        UPDATE CUSTOMER SET NUMBER_OF_ORDERS=NUMBER_OF_ORDERS+5 WHERE CID IN (SELECT CID FROM PURCHASED GROUP BY CID HAVING count(CID)=cnumber);
    ELSIF cnumber=2 THEN
        UPDATE CUSTOMER SET NUMBER_OF_ORDERS=NUMBER_OF_ORDERS+3 WHERE CID IN (SELECT CID FROM PURCHASED GROUP BY CID HAVING count(CID)=cnumber);
    ELSE
        UPDATE CUSTOMER SET NUMBER_OF_ORDERS=NUMBER_OF_ORDERS+1 WHERE CID IN (SELECT CID FROM PURCHASED GROUP BY CID HAVING count(CID)=cnumber);
    END IF;
END;
/

--Module 2
--Updating Customer Records using Cursor
--Using While Loop to loop through the counts and incrementing the number of orders accordingly
--Using Function Block
create or replace Function FindCourse
Return number IS 
cnumber number;

CURSOR c1 IS SELECT DISTINCT count(cid) FROM purchased group by cid;
BEGIN

   open c1;
   fetch c1 into cnumber;
    dbms_output.put_line('Name is: ' || cnumber);
    WHILE c1%FOUND LOOP
        DBMS_OUTPUT.put_line(cnumber);
        IF cnumber=3 THEN
            UPDATE CUSTOMER SET NUMBER_OF_ORDERS=NUMBER_OF_ORDERS+5 WHERE CID IN (SELECT CID FROM PURCHASED GROUP BY CID HAVING count(CID)=cnumber);
        ELSIF cnumber=2 THEN
            UPDATE CUSTOMER SET NUMBER_OF_ORDERS=NUMBER_OF_ORDERS+3 WHERE CID IN (SELECT CID FROM PURCHASED GROUP BY CID HAVING count(CID)=cnumber);
        ELSE
            UPDATE CUSTOMER SET NUMBER_OF_ORDERS=NUMBER_OF_ORDERS+1 WHERE CID IN (SELECT CID FROM PURCHASED GROUP BY CID HAVING count(CID)=cnumber);
        END IF;
        FETCH c1 into cnumber;
    END LOOP;
   close c1;
   
   Return cnumber;
END;



--Module 3
--Procedure Block
--Based on the salary from the translator table we update number of editions in the book table.
--If translator salary is greater than 60,000 then number of Editions is tripled
--If translator salary is between 30,000 and 60,000 then number of Editions is doubled
--If translator salary is less than 30,000 then number of Editions remains the same.
--Using FOR LOOP and IF ELSIF LOOP

create or replace PROCEDURE PROBLEM3_SOLUTION1 AS
a NUMBER;              --declaring the variables
b VARCHAR2(20);

BEGIN

--FOR tid IN (SELECT salary into a FROM Translator)
--LOOP
FOR tid IN (SELECT salary FROM Translator)
LOOP
IF tid.salary>60000 THEN
UPDATE BOOK  SET Edition = Edition *3 where TID IN(select tid from translator where salary > 59999);
ELSIF tid.salary<30000 THEN
UPDATE BOOK SET Edition= Edition *2 where TID IN(select tid from translator where salary > 29999 and salary < 60000);
ELSE 
UPDATE BOOK SET Edition= Edition *1 where TID IN (select tid from translator where salary < 30000);
END IF;
END LOOP;

DBMS_OUTPUT.PUT_LINE('BTITLE' || ' ' || 'CATEGORY' || ' ' || 'EDITION' || ' ' || 'RELEASE_YEAR' ||' ' || 'TID');
 FOR object IN (select * from BOOK)
 LOOP
 DBMS_OUTPUT.PUT_LINE(object.BTITLE || ' ' || object.CATEGORY || ' ' || object.EDITION || ' ' || object.RELEASE_YEAR || ' ' || object.TID);
 END LOOP;

END PROBLEM3_SOLUTION1;
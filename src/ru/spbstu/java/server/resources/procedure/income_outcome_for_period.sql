CREATE OR REPLACE PROCEDURE get_income_and_outcome(
    date1 IN DATE,
    date2 IN DATE,
    OUTCOME OUT NUMBER,
    INCOME OUT NUMBER
)
    IS
BEGIN
    SELECT SUM(AMOUNT) INTO INCOME FROM SALES WHERE SALE_DATE BETWEEN date1 AND date2;
    SELECT SUM(AMOUNT) INTO OUTCOME FROM CHARGES WHERE CHARGE_DATE BETWEEN date1 AND date2;
END;



/*
CREATE type average_obj is object
(
    name    varchar2(50 BYTE),
    average number(18, 2)
);
CREATE TYPE average_table is table of average_obj;*/

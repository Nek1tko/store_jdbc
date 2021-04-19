CREATE OR REPLACE FUNCTION average_charges RETURN average_table
    IS
    result_table average_table := AVERAGE_TABLE();
    n number := 0;
BEGIN
    FOR el IN (SELECT NAME, AVG(SALES.AMOUNT) AS average
    FROM SALES
             JOIN WAREHOUSES W on W.ID = SALES.WAREHOUSE_ID
    GROUP BY NAME)
    LOOP
        result_table.extend;
        n := n + 1;
        result_table(n) := average_obj(el.NAME, el.average);
    END LOOP;
    return result_table;
END;
COMMIT;
/*
CREATE type average_obj is object
(
    name    varchar2(50 BYTE),
    average number(18, 2)
);
CREATE TYPE average_table is table of average_obj;*/

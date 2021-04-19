CREATE OR REPLACE TYPE date_table IS TABLE OF CHAR(10);
CREATE OR REPLACE FUNCTION get_same_date_sale(item1 IN varchar2,
                                              item2 IN varchar2)
    RETURN date_table
    IS
    result_table date_table := date_table();
    n            number     := 0;
BEGIN
    for el in (SELECT TO_CHAR(SALE_DATE, 'dd.mm.yyyy') AS SALE_DATE
                FROM SALES
                         JOIN WAREHOUSES W on W.ID = SALES.WAREHOUSE_ID
                WHERE NAME = item1
               INTERSECT
               SELECT TO_CHAR(SALE_DATE, 'dd.mm.yyyy') AS SALE_DATE
                FROM SALES
                         JOIN WAREHOUSES W on W.ID = SALES.WAREHOUSE_ID
                WHERE NAME = item2)
        loop
            result_table.extend;
            n := n + 1;
            result_table(n) := el.SALE_DATE;
        end loop;
    return result_table;
END;
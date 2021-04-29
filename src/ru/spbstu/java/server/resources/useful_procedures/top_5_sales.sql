CREATE OR REPLACE FUNCTION top_5_sales(startDate IN TIMESTAMP,
                                       endDate IN TIMESTAMP) RETURN AVERAGE_TABLE
    IS
    result_table average_table;
    n            number        := 1;
BEGIN
    for some in (SELECT NAME, SUM(SALES.AMOUNT) AS AMOUNT
                INTO result_table
                FROM SALES
                         JOIN WAREHOUSES on WAREHOUSES.ID = SALES.WAREHOUSE_ID
                WHERE SALE_DATE BETWEEN startDate AND
                          endDate
                GROUP BY NAME
                ORDER BY AMOUNT DESC)
        loop
        result_table(n) := AVERAGE_OBJ(some.NAME, some.AMOUNT);
        n := n + 1;
        end loop;

    return result_table;
END;
COMMIT;
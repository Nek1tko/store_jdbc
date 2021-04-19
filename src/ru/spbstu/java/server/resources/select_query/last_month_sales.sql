select distinct NAME
from SALES
         JOIN WAREHOUSES
              on SALES.WAREHOUSE_ID = WAREHOUSES.ID
WHERE WAREHOUSES.AMOUNT != 0
  AND
    SALE_DATE
        BETWEEN
        add_months(trunc(sysdate, 'MONTH'), -1)
        AND
        trunc(sysdate, 'MONTH')
;
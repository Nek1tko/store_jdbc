select NAME, SUM(AMOUNT) AS AMOUNT
from CHARGES
         JOIN EXPENSE_ITEMS on EXPENSE_ITEMS.ID = CHARGES.EXPENSE_ITEM_ID
WHERE CHARGE_DATE
          BETWEEN
          add_months(trunc(sysdate, 'YEAR'), -12)
          AND
          trunc(sysdate, 'YEAR')
GROUP BY NAME
ORDER BY AMOUNT DESC
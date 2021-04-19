SELECT (INCOME.AMOUNT - OUTCOME.AMOUNT) AS MARGIN
FROM (SELECT sum(AMOUNT) AS AMOUNT
      FROM SALES
      WHERE SALE_DATE BETWEEN add_months(trunc(sysdate, 'MONTH'), -1)
                AND
                trunc(sysdate, 'MONTH')) INCOME,
     (SELECT SUM(AMOUNT) AS AMOUNT
      FROM CHARGES
      WHERE CHARGE_DATE BETWEEN add_months(trunc(sysdate, 'MONTH'), -1)
                AND
                trunc(sysdate, 'MONTH')) OUTCOME
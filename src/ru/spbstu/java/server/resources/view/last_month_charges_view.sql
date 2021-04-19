CREATE OR REPLACE VIEW month_charges_view AS
SELECT NAME, COUNT(NAME) AS COUNT
FROM CHARGES
         JOIN EXPENSE_ITEMS EI on EI.ID = CHARGES.EXPENSE_ITEM_ID
WHERE CHARGE_DATE
          BETWEEN
          add_months(sysdate, -1)
          AND
          sysdate
GROUP BY NAME
CREATE OR REPLACE VIEW expensive_charges AS
SELECT NAME
FROM CHARGES
         JOIN EXPENSE_ITEMS EI on CHARGES.EXPENSE_ITEM_ID = EI.ID
GROUP BY NAME
HAVING (SUM(AMOUNT) >= 40000)
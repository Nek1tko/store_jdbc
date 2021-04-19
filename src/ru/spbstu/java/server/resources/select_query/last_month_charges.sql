select *
from CHARGES WHERE CHARGE_DATE
    BETWEEN
    add_months(trunc(sysdate, 'MONTH'), -1)
    AND
    trunc(sysdate, 'MONTH');
CREATE OR REPLACE TRIGGER rollback_charge
    BEFORE DELETE
    ON CHARGES
    FOR EACH ROW
BEGIN
    IF (:OLD.CHARGE_DATE < add_months(sysdate, -1)) THEN
        RAISE_APPLICATION_ERROR(-20000, 'Could not delete record older than a month');
    end if;
END;

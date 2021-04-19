CREATE OR REPLACE TRIGGER max_charge
    BEFORE INSERT
    ON CHARGES
    FOR EACH ROW
BEGIN
    IF (:NEW.AMOUNT > 100000) THEN
        RAISE_APPLICATION_ERROR(-20000, 'Charge amount could not be better then 100000');
    end if;
END;

CREATE OR REPLACE TRIGGER sales_modify
    BEFORE UPDATE
    ON SALES
    FOR EACH ROW
BEGIN
    IF (:NEW.SALE_DATE < trunc(sysdate)) THEN
        RAISE_APPLICATION_ERROR(-20000, 'Sale record could not be modified from the future');
    end if;
END;

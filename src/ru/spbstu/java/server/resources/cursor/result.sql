DECLARE
    margin number;
BEGIN
    PREDICT_MARGIN(margin);
    DBMS_OUTPUT.PUT_LINE('margin = ' || margin);
END;
CREATE OR REPLACE PROCEDURE predict_margin(margin OUT NUMBER)
    IS
    CURSOR sale
        IS
        SELECT AMOUNT, SALE_DATE
        FROM SALES
        WHERE SALE_DATE BETWEEN add_months(sysdate, -4) AND sysdate;
    CURSOR charge
        IS
        SELECT AMOUNT, CHARGE_DATE
        FROM CHARGES
        WHERE CHARGE_DATE BETWEEN add_months(sysdate, -4) AND sysdate;
    current_amount number;
    current_date_v date;
    avg_sales      number := 0;
    avg_charges    number := 0;
BEGIN
    open sale;
    LOOP
        FETCH sale INTO current_amount, current_date_v;
        EXIT WHEN sale%NOTFOUND;
        IF (current_date_v between add_months(sysdate, -1) AND sysdate) THEN
            avg_sales := avg_sales + current_amount;
        ELSIF (current_date_v between add_months(sysdate, -2) AND add_months(sysdate, -1)) THEN
            avg_sales := avg_sales + current_amount * 0.5;
        ELSE
            avg_sales := avg_sales + current_amount * 0.25;
        end if;
    end loop;
    close sale;



    open charge;
    LOOP
        FETCH charge INTO current_amount, current_date_v;
        EXIT WHEN charge%NOTFOUND;
        IF (current_date_v between add_months(sysdate, -1) AND sysdate) THEN
            avg_charges := avg_charges + current_amount;
        ELSIF (current_date_v between add_months(sysdate, -2) AND add_months(sysdate, -1)) THEN
            avg_charges := avg_charges + current_amount * 0.5;
        ELSE
            avg_charges := avg_charges + current_amount * 0.25;
        end if;
    end loop;
    close charge;

    margin := avg_sales - avg_charges;
END;
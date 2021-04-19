DECLARE
    income  number;
    outcome number;
begin
    GET_INCOME_AND_OUTCOME('19.02.2021', '19.03.2021', income, outcome);
    DBMS_OUTPUT.PUT_LINE('income = ' || income);
    DBMS_OUTPUT.PUT_LINE('outcome = ' || outcome);
end;
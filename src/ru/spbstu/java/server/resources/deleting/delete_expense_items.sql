declare
    v_expense_item_pk number;
    v_name varchar2(100 byte) := 'аренда';
begin
    select id into v_expense_item_pk from EXPENSE_ITEMS where name = v_name;
    delete from CHARGES where EXPENSE_ITEM_ID = v_expense_item_pk;
    delete from EXPENSE_ITEMS where id = v_expense_item_pk;
end;
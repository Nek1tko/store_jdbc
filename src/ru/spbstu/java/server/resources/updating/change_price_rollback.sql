declare
    v_warehouse_pk number;
    v_name         varchar2(50 byte) := 'обои';
begin
    select id
    into v_warehouse_pk
    from WAREHOUSES
    WHERE NAME = v_name;
    update SALES
    set AMOUNT = AMOUNT + 5.0
    where SALE_DATE = (select max(SALE_DATE) from SALES where WAREHOUSE_ID = v_warehouse_pk)
      and WAREHOUSE_ID = v_warehouse_pk;
    rollback;
end;
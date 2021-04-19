declare
    v_warehouse_pk number;
    v_name         varchar2(50 BYTE) := 'обои';
begin
    select WAREHOUSES.id
    into v_warehouse_pk
    from WAREHOUSES
    WHERE NAME = v_name;
    delete
    from SALES
    where QUANTITY = (select min(QUANTITY) from SALES where WAREHOUSE_ID = v_warehouse_pk)
      and WAREHOUSE_ID = v_warehouse_pk;
    rollback;
end;
declare
    warehouse_pk number;
    error        char(1);
begin
    insert into WAREHOUSES (NAME, QUANTITY, AMOUNT)
    values ('гвозди', 5000, 2.00) returning  id into warehouse_pk;

/*select max(id) into warehouse_pk from warehouses;*/

    insert into SALES (AMOUNT, QUANTITY, SALE_DATE, WAREHOUSE_ID)
    values (1200.00, 6000, CURRENT_DATE, warehouse_pk);

    select case
               when exists(select *
                           from SALES
                                    JOIN
                                WAREHOUSES
                                ON SALES.WAREHOUSE_ID = WAREHOUSES.ID
                           where WAREHOUSE_ID = warehouse_pk
                             and SALES.QUANTITY < WAREHOUSES.QUANTITY
                   )
                   then 0
               else 1
               end
    into error
    from dual;

    if error = 1
    then
        rollback;
    else
        commit;
    end if;

end;
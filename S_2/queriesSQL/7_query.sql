with t1 as (
    --Выданная на текущий момент литература
    select ID_EDITION, ID_COMPOSITION from ISSUED_BOOKS where IS_RETURNED = 'нет'
),
     t2 as (
         --Информация о выданной литературе
         select t1.ID_EDITION, TITLE, AUTHOR, GENRE from COMPOSITIONS
            inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION
     ),
     t3 as (
         --Литература с конкретной полки, конкретной библиотеки
         select * from t2 inner join EDITIONS on t2.ID_EDITION = EDITIONS.ID_EDITION
         where SHELF_NUM = 2 and ID_LIBRARY = 2
     )
select * from t3
with t1 as (
    --Выбор изданий, которые относятся к произведению автора
    select ID_EDITION, TITLE
    from COMPOSITIONS
    where AUTHOR = 'Киреев А.И.'
),
     t2 as (
         --Получить инвентарные номера (номера полок, где лежит книга)
         select SHELF_NUM as "Инвентарный номер", TITLE as "Название" from EDITIONS
                                                                               inner join t1 on t1.ID_EDITION = EDITIONS.ID_EDITION
     )
select *
from t2
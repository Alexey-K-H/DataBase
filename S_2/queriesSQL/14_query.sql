with t1 as (
    --Выбор изданий, которые относятся к произведению
    select ID_EDITION, TITLE
    from COMPOSITIONS
    where GENRE = 'Учебное пособие'
),
     t2 as (
         --Получить инвертарные номера (номера полок, где лежит книга)
         select SHELF_NUM as "Инвентарный номер", TITLE as "Название" from EDITIONS
                inner join t1 on t1.ID_EDITION = EDITIONS.ID_EDITION
     )
select *
from t2
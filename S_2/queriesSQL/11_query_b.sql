with t1 as (
    --Перечень литературы, которая была списана в течение определенного периода
    --Поиск изданий данной категории
    select ID_EDITION from EDITIONS
    where WRITE_OFF_DATE >= TO_DATE('02.01.2020', 'dd.mm.yyyy')
      and WRITE_OFF_DATE <= TO_DATE('30.12.2021', 'dd.mm.yyyy')
),
     t2 as (
         --Получение дополнительной информации об этих изданиях
         select COMPOSITIONS.ID_EDITION as "Издание",
                TITLE as "Название",
                AUTHOR as "Автор",
                GENRE as "Жанр"
         from COMPOSITIONS
                  inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION
     )
select * from t2
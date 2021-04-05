with t1 as (
    --Выбираем произведения и их популярность
    select TITLE, ID_EDITION, POPULARITY from COMPOSITIONS
),
t2 as (
    --Ищем максимальное значение популярности
    select max(POPULARITY)
    from t1
)
select TITLE as "Название", ID_EDITION as "Издание" from t1
where POPULARITY = (select * from t2)
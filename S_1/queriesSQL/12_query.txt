with t1 as (
    --Выбираем конкретный зал
    select ID_HALL, ID_LIBRARY from HALLS
    where ID_HALL = 2 and ID_LIBRARY = 1
),
t2 as (
    select ID_LIBRARIAN, HALL_NUM, LIBRARIANS.ID_LIBRARY from LIBRARIANS
    inner join t1 on ID_HALL = LIBRARIANS.HALL_NUM and t1.ID_LIBRARY = LIBRARIANS.ID_LIBRARY
)
select ID_LIBRARIAN as "ID-библиотекаря" from t2
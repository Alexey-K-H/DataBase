with t1 as (
    --Выданные экземпляры произведения конткретного издания
    select ID_READER, ID_COMPOSITION, TITLE from ISSUED_BOOKS
    inner join COMPOSITIONS C2 on C2.ID_RECORD = ISSUED_BOOKS.ID_COMPOSITION
    where TITLE = 'ЭВМ и Архитектура компьютера' and C2.ID_EDITION = 3
),
t2 as (
    select READERS.ID_READER, SURNAME, NAME, PATRONYMIC from READERS
    inner join t1 on t1.ID_READER = READERS.ID_READER
)
select * from t2
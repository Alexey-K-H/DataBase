with t1 as (
    --Поиск читатаелей с просроченным сроком литературы
    select ID_READER from ISSUED_BOOKS where RETURN_DATE <= CURRENT_DATE and IS_RETURNED = 'нет'
),
     t2 as (
         --Подробная информация о должниках
         select t1.ID_READER as "ID", SURNAME as "Фамилия", NAME as "Имя", PATRONYMIC as "Отчество", ID_LIBRARY as "Библиотека"
         from READERS
                  inner join t1 on t1.ID_READER = READERS.ID_READER
     )
select * from t2
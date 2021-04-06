with t1 as (
    --Выбрать читателей которым выдавали книгу в указанный промежуток времени
    select ID_READER from ISSUED_BOOKS
    where ID_LIBRARIAN = 1
      and DATE_OF_ISSUE >= TO_DATE('02.01.2020','dd.mm.yyyy') and DATE_OF_ISSUE <= TO_DATE('31.12.2021', 'dd.mm.yyyy')
),
     t2 as (
         --Получить дпоплнительную информацию о найденых читателях
         select READERS.ID_READER as "Идентификатор", SURNAME as "Фамлиия", NAME as "Имя", PATRONYMIC as "Отчество" from READERS
                                                                                                                             inner join t1 on t1.ID_READER = READERS.ID_READER
     )
select * from t2
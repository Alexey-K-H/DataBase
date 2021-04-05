with t1 as (
    --Издания, хранящиеся в течение указанного промежуткка времени
    select ID_EDITION from EDITIONS
    where DATE_OF_ADMISSION >= to_date('04.01.2020', 'dd.mm.yyyy') and WRITE_OFF_DATE <= to_date('12.08.2021','dd.mm.yyyy')
),
     t2 as (
         --Издания и соответствующие им произведения
         select ID_RECORD, TITLE from COMPOSITIONS
                                          inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION
     ),
     t3 as (
         --Издания которые на руках у пользователей
         select ID_READER, t2.ID_RECORD, TITLE from ISSUED_BOOKS
                                                        inner join t2 on t2.ID_RECORD = ID_READER
     ),
     t4 as (
         --Выбор из списка изадний тех, которые были у конкретного пользователя
         select READERS.ID_READER as "Идентификатор читателя",SURNAME as "Фамилия", NAME as "Имя", PATRONYMIC as "Отчество",
                t3.ID_RECORD as "Номер издания", t3.TITLE as "Название произведения" from READERS
                                                                                              inner join t3 on READERS.ID_READER = t3.ID_READER
         where READERS.ID_READER = 5
     )
select * from t4
with t1 as (
    --Читатаели, которым не выдавались книги или читатели, которые не вренули книги в бибилиотеку
    --в течение указанного периода времени (показатель непосещения)
    select ID_READER
    from ISSUED_BOOKS
    where (DATE_OF_ISSUE < TO_DATE('02.08.2020', 'dd.mm.yyyy')
        or DATE_OF_ISSUE > TO_DATE('31.12.2021', 'dd.mm.yyyy'))
       or ((RETURN_DATE < TO_DATE('02.08.2020', 'dd.mm.yyyy')
        or RETURN_DATE > TO_DATE('31.12.2021', 'dd.mm.yyyy')) and IS_RETURNED = 'да')
),
     t2 as (
         --Подробная информация об этих читателях
         select READERS.ID_READER as "Идентификатор", SURNAME as "Фамилия", NAME as "Имя",
                PATRONYMIC as "Отчество" from READERS
                                                  inner join t1 on t1.ID_READER = READERS.ID_READER
     )
select * from t2
with t1 as (
    --Выданные книги в указанном промежутке для выбранного читателя
    select ID_COMPOSITION, ID_EDITION
    from ISSUED_BOOKS where DATE_OF_ISSUE >= to_date('02.01.2020', 'dd.mm.yyyy') and DATE_OF_ISSUE <= to_date('08.12.2021', 'dd.mm.yyyy') and ID_READER = 1
),
     t2 as (
         --Поиск библиотеки пользователя
         select ID_LIBRARY
         from READERS where ID_READER = 1
     ),
     t3 as (
         --Получить список изданий не из библиотеки пользователя
         select ID_EDITION
         from EDITIONS inner join t2 on t2.ID_LIBRARY != EDITIONS.ID_LIBRARY
     ),
     t4 as (
         --Издания которые выдавались пользователю не в его бибилиотеке
         select t1.ID_EDITION, t1.ID_COMPOSITION from t1 inner join t3 on t1.ID_EDITION = t3.ID_EDITION
     ),
     t5 as (
         --Поиск изданий, которые есть в библиотеке польователя и которые выдавались ему
         select t4.ID_EDITION as "Издание", TITLE as "Название" from COMPOSITIONS inner join t4 on t4.ID_COMPOSITION = ID_RECORD
     )
select * from t5
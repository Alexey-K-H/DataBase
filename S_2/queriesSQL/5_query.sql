with t1 as (
    --Выданные книги в указанном промежутке для выбранного читателя
    select ID_COMPOSITION, ID_EDITION
    from ISSUED_BOOKS where DATE_OF_ISSUE >= to_date('02.01.2020', 'dd.mm.yyyy') and DATE_OF_ISSUE <= to_date('08.12.2021', 'dd.mm.yyyy') and ID_READER = 1
),
     t2 as (
         select t1.ID_EDITION as "Издание", TITLE as "Название" from COMPOSITIONS inner join t1 on t1.ID_COMPOSITION = ID_RECORD
     )
select * from t2
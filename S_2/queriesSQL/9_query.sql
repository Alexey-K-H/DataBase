with t1 as (
    --Библиотекари и число обслуженных ими читателей
    select ID_LIBRARIAN, COUNT(ID_READER) as COUNT_READERS
    from ISSUED_BOOKS
    where DATE_OF_ISSUE >= TO_DATE('02.01.2020','dd.mm.yyyy')
      and DATE_OF_ISSUE <= TO_DATE('30.12.2021','dd.mm.yyyy')
    group by ID_LIBRARIAN
)
select t1.ID_LIBRARIAN as "ID библиотекаря", COUNT_READERS as "Кол-во читателей" from t1
package connection;

import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class DBConnection{
    private final Connection conn;

    public Connection getConn() {
        return conn;
    }

    public DBConnection(String url, Properties props) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);

        conn = DriverManager.getConnection(url, props);
    }

    public void close() throws SQLException {
        System.out.println("Delete tmp data...");
        Statement statement = conn.createStatement();
        //category
        statement.executeUpdate("drop table Teachers");
        statement.executeUpdate("drop table Researchers");
        statement.executeUpdate("drop table SchoolChild");
        statement.executeUpdate("drop table Students");
        statement.executeUpdate("drop table Pensioners");
        statement.executeUpdate("drop table Workers");
        //second level
        statement.executeUpdate("drop table Issued_books");
        statement.executeUpdate("drop table Rules");
        statement.executeUpdate("drop table Compositions");
        //Delete dependent tables 1 level
        statement.executeUpdate("drop table Librarians");
        statement.executeUpdate("drop table Readers");
        statement.executeUpdate("drop table Editions");
        //Delete independent tables
        statement.executeUpdate("drop table Libraries");
        System.out.println("Close connection...");
        conn.close();
    }

    public void initSchema() throws SQLException{
        System.out.println("Init schema....");
        Statement statement = conn.createStatement();
        //zero level
        statement.executeUpdate(
                "create table Libraries(id_library integer primary key," +
                " quantity_books integer not null," +
                        "check ( quantity_books >= 0 ))"
        );
        //first level
        statement.executeUpdate(
                "create table Librarians (" +
                        "id_librarian integer primary key, " +
                        "id_library integer not null , " +
                        "hall_num integer not null," +
                        "foreign key (id_library) references Libraries(id_library) on delete cascade," +
                        " check ( hall_num > 0 ))"
        );
        statement.executeUpdate("" +
                "create table Readers (" +
                "id_reader integer primary key, " +
                "id_library integer not null," +
                "surname varchar(50) not null," +
                "name varchar(40) not null," +
                "patronymic varchar(60) not null," +
                "status varchar(50) not null," +
                "foreign key (id_library) references Libraries(id_library) on delete cascade " +
                ")"
        );
        statement.executeUpdate(
                "create table Editions(" +
                        "id_edition integer primary key ," +
                        "id_library integer not null," +
                        "hall_num integer not null," +
                        "rack_num integer not null," +
                        "shelf_num integer not null," +
                        "date_of_admission date not null," +
                        "write_off_date date not null," +
                        "foreign key (id_library) references Libraries(id_library) on delete cascade " +
                        ")"
        );
        //second level
        statement.executeUpdate(
                "create table Issued_books(" +
                        "id_record integer primary key," +
                        "id_librarian integer not null," +
                        "id_edition integer not null," +
                        "id_reader integer not null," +
                        "date_of_issue date not null," +
                        "return_date date not null," +
                        "is_returned varchar(3) not null," +
                        "foreign key (id_librarian) references Librarians(id_librarian) on delete cascade ," +
                        "foreign key (id_edition) references Editions(id_edition) on delete cascade ," +
                        "foreign key (id_reader) references Readers(id_reader) on delete cascade " +
                        ")"
        );
        statement.executeUpdate("create table Rules(" +
                "id_rule integer primary key," +
                " id_edition integer not null," +
                " rule_text varchar(500)," +
                " foreign key (id_edition) references Editions(id_edition) on delete cascade " +
                ")"
        );
        statement.executeUpdate("create table Compositions(" +
                "id_record integer," +
                " id_edition integer," +
                " author varchar(100) not null," +
                " title varchar(100) not null," +
                " popularity integer not null," +
                " genre varchar(50) not null," +
                " primary key(id_record, id_edition)," +
                " foreign key (id_edition) references Editions(id_edition) on delete cascade " +
                ")"
        );
        //category
        statement.executeUpdate("create table Teachers(" +
                "id_reader integer primary key," +
                "id_university integer not null," +
                "faculty varchar(100) not null," +
                "name_university varchar(100) not null," +
                "foreign key (id_reader) references Readers (id_reader) on delete cascade " +
                ")"
        );
        statement.executeUpdate("create table Researchers(" +
                "id_reader integer primary key," +
                "id_university integer not null," +
                "address_university varchar(100) not null," +
                "degree varchar(100) not null," +
                "name_university varchar(200) not null," +
                "foreign key (id_reader) references Readers(id_reader) on delete cascade " +
                ")"
        );
        statement.executeUpdate("create table SchoolChild(" +
                "id_reader integer primary key," +
                "id_school integer not null," +
                "grade integer not null," +
                "name_school varchar(100) not null," +
                "foreign key(id_reader) references Readers(id_reader) on delete cascade " +
                ")"
        );
        statement.executeUpdate("create table Students(" +
                "id_reader integer primary key," +
                "id_university integer not null," +
                "faculty varchar(100) not null," +
                "name_university varchar(100) not null," +
                "foreign key(id_reader) references Readers(id_reader) on delete cascade " +
                ")"
        );
        statement.executeUpdate("create table Pensioners (" +
                "id_reader integer primary key," +
                "id_pensioner integer unique," +
                "foreign key(id_reader) references Readers(id_reader) on delete cascade " +
                ")"
        );
        statement.executeUpdate("create table Workers(" +
                "id_reader integer primary key," +
                "firm_address varchar(200)," +
                "name_firm varchar(200)," +
                "foreign key(id_reader) references Readers(id_reader) on delete cascade " +
                ")"
        );

        statement.executeUpdate("insert into Libraries values (1, 5)");
        statement.executeUpdate("insert into Libraries values (2, 10)");
        statement.executeUpdate("insert into Libraries values (3, 7)");
        statement.executeUpdate("insert into Libraries values (4, 15)");
        statement.executeUpdate("insert into Libraries values (5, 12)");
        statement.executeUpdate("insert into Libraries values (6, 10)");

        statement.executeUpdate("insert into LIBRARIANS values (1, 2, 102)");
        statement.executeUpdate("insert into LIBRARIANS values (2, 3, 203)");
        statement.executeUpdate("insert into LIBRARIANS values (3, 4, 521)");
        statement.executeUpdate("insert into LIBRARIANS values (4, 6, 302)");
        statement.executeUpdate("insert into LIBRARIANS values (5, 1, 434)");
    }
}

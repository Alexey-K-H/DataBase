package connection;

import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class DBConnection{
    private final Connection conn;

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
        //second level
        statement.executeUpdate("drop table Issued_books");

        //Delete dependent tables 1 level
        statement.executeUpdate("drop table Librarians");
        statement.executeUpdate("drop table Readers");
        statement.executeUpdate("drop table Editions");
        //Delete independent tables
        statement.executeUpdate("drop table Libraries");
        System.out.println("Close connection...");
        conn.close();
    }

    public int performSQLQuery() throws SQLException {
        System.out.println("Perform SQL Query...");
        String sql = "select count(*) from Libraries";

        PreparedStatement preStatement = conn.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();
        int count = 0;
        while (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }

    public void initSchema() throws SQLException{
        System.out.println("Init schema....");
        Statement statement = conn.createStatement();
        //zero level
        statement.executeUpdate(
                "create table Libraries(id_library integer primary key," +
                " quantity_books integer not null)"
        );
        //first level
        statement.executeUpdate(
                "create table Librarians (" +
                        "id_librarian integer primary key, " +
                        "id_library integer not null , " +
                        "hall_num integer not null," +
                        "foreign key (id_library) references Libraries(id_library))"
        );
        statement.executeUpdate("" +
                "create table Readers (" +
                "id_reader integer primary key, " +
                "id_library integer not null," +
                "surname varchar(50) not null," +
                "name varchar(40) not null," +
                "patronymic varchar(60) not null," +
                "status varchar(50) not null," +
                "foreign key (id_library) references Libraries(id_library)" +
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
                        "foreign key (id_library) references Libraries(id_library)" +
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
                        "foreign key (id_librarian) references Librarians(id_librarian)," +
                        "foreign key (id_edition) references Editions(id_edition)," +
                        "foreign key (id_reader) references Readers(id_reader)" +
                        ")"
        );

        statement.executeUpdate("insert into Libraries values (1, 5)");
        statement.executeUpdate("insert into Libraries values (2, 10)");
        statement.executeUpdate("insert into Libraries values (3, 7)");
        statement.executeUpdate("insert into Libraries values (4, 15)");
        statement.executeUpdate("insert into Libraries values (5, 12)");
        statement.executeUpdate("insert into Libraries values (6, 10)");
    }
}

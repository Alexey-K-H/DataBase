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
        statement.executeUpdate("create table Libraries(id_library integer primary key , quantity_books integer not null)");
        statement.executeUpdate("insert into Libraries values (1, 5)");
        statement.executeUpdate("insert into Libraries values (2, 10)");
        statement.executeUpdate("insert into Libraries values (3, 7)");
        statement.executeUpdate("insert into Libraries values (4, 15)");
        statement.executeUpdate("insert into Libraries values (5, 12)");
        statement.executeUpdate("insert into Libraries values (6, 10)");
    }
}

import connection.DBConnection;
import gui.Window;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Connecting to database...");
        DBConnection connection = new DBConnection();
        System.out.println("Success");
        Window window = new Window(connection);
        window.run();
    }
}

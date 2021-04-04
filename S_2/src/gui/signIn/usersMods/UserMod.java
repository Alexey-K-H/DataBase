package gui.signIn.usersMods;

import connection.DBConnection;

import javax.swing.*;
import java.sql.SQLException;

public abstract class UserMod extends JDialog {
    private final DBConnection connection;
    private final String url;

    protected UserMod(DBConnection connection, String url) {
        this.connection = connection;
        this.url = url;
    }

    public DBConnection getConnection() {
        return connection;
    }

    public String getUrl() {
        return url;
    }

    public abstract boolean checkSecurity(String identity) throws SQLException;

    public abstract void openSecurityCheckWindow();
}

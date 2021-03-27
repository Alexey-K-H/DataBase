package gui.queryWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryFrame {
    void openQueryConfig();
    ResultSet performQuery(String sql) throws SQLException;
}

package gui.queryWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryFrame {
    void openQueryConfig();
    void performQuery(String sql) throws SQLException;
    void showQueryResult(ResultSet resultSet);
}

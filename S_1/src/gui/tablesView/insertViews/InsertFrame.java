package gui.tablesView.insertViews;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InsertFrame {
    void openInsertWindow();
    void performInsertOperation(String sql) throws SQLException;
}

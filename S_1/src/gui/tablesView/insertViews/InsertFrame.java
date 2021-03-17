package gui.tablesView.insertViews;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InsertFrame {
    void openInsertWindow();
    void performInsertOperation(ArrayList<String> values) throws SQLException;
}

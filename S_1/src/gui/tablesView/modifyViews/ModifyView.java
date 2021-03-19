package gui.tablesView.modifyViews;

import java.sql.SQLException;

public interface ModifyView {
    void openModifyWindow();
    void performUpdateOperation(String sqlValuesSet) throws SQLException;
}

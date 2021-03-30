package gui.tablesView.modifyViews.categoryModify;

import controllers.TableController;
import gui.tablesView.modifyViews.ModifyView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class CategoryModify extends JDialog implements ModifyView {
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public CategoryModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        this.tableController = tableController;
        this.currValues = currValues;
        this.tableModel = tableModel;
        this.indexRow = indexRow;
    }

    public TableController getTableController() {
        return tableController;
    }

    public ArrayList<String> getCurrValues() {
        return currValues;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public int getIndexRow() {
        return indexRow;
    }

    @Override
    public void performUpdateOperation(String sqlValuesSet) throws SQLException {
        tableController.modifyRow(sqlValuesSet);
    }
}

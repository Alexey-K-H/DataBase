package controllers;

import connection.DBConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableController {
    private final String tableName;
    private final DBConnection connection;

    public String getTableName() {
        return tableName;
    }

    public DBConnection getConnection() {
        return connection;
    }

    public TableController(String tableName, DBConnection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }

    private String[] createColumnsHeaders(){
        switch (tableName){
            case "Libraries":
                return new String[]{"Идентификатор", "Количество книг"};
            default:
                return null;
        }
    }

    public DefaultTableModel getTableSet() throws SQLException {
        Object[] columnsHeaders = createColumnsHeaders();
        if(columnsHeaders == null){
            throw new SQLException("Нет таблицы с таким названием!");
        }

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeaders);

        String sql = "select * from " + tableName;
        PreparedStatement preStatement = connection.getConn().prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();

        switch (tableName){
            case "Libraries":{
                while (result.next()) {
                    tableModel.addRow(new Object[]{result.getInt("id_library"), result.getInt("quantity_books")});
                }
                result.close();
            }
        }

        return tableModel;
    }

    public void insertNewRecord(ArrayList<String> newValues) throws SQLException {
        StringBuilder valuesSet = new StringBuilder();
        for (String value : newValues){
           valuesSet.append(value).append(", ");
        }
        String values = valuesSet.substring(0, valuesSet.toString().length() - 2);
        Statement statement = connection.getConn().createStatement();
        String sql = "insert into " + tableName + " values(" + values + ")";
        statement.executeUpdate(sql);
    }

    private String getPrimaryKeyNameByTableName(){
        switch (tableName){
            case "Libraries":{
                return "id_library";
            }
        }
        return null;
    }

    public void deleteRecord(Object rowKey) throws SQLException {
        Statement statement = connection.getConn().createStatement();
        String sql = "delete from " + tableName + " where " + getPrimaryKeyNameByTableName() + " = " + rowKey;
        statement.executeUpdate(sql);
    }

    public void modifyRow(String sqlValuesSet) throws SQLException {
        Statement statement = connection.getConn().createStatement();
        String sql = "update " + tableName + " " + sqlValuesSet;
        statement.executeUpdate(sql);
    }
}

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
            System.out.println("Нет таблицы с таким названием!");
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
        System.out.println("Insert values to table " + tableName + ": " + values);

        Statement statement = connection.getConn().createStatement();
        String sql = "insert into " + tableName + " values(" + values + ")";
        statement.executeUpdate(sql);
    }
}

package controllers;

import connection.DBConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            case "Halls":
                return new String[]{"Номер зала", "Id-библиотека"};
            case "Librarians":
                return new String[]{"Идентификатор", "Id - Библиотека",  "Номер зала"};
            case "Readers":
                return new String[]{"Идентификатор", "Id - Библиотека", "ФИО", "Статус"};
            case "Teachers":
                return new String[]{"Id-читатель", "Id-университет", "Факультет", "Название ВУЗа"};
            default:
                return null;
        }
    }

    public DefaultTableModel getTableSet() throws SQLException {
        Object[] columnsHeaders = createColumnsHeaders();
        if(columnsHeaders == null){
            throw new SQLException("Нет таблицы с таким названием!");
        }

        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnsHeaders);

        String sql = "select * from " + tableName;
        PreparedStatement preStatement = connection.getConn().prepareStatement(sql);
        ResultSet result = preStatement.executeQuery();

        switch (tableName){
            case "Libraries":{
                while (result.next()) {
                    tableModel.addRow(new Object[]{
                            result.getInt("id_library"),
                            result.getInt("quantity_books")});
                }
                break;
            }
            case "Halls":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_hall"),
                            result.getInt("id_library")
                    });
                }
                break;
            }
            case "Librarians":{
                while (result.next()) {
                    tableModel.addRow(new Object[]{
                            result.getInt("id_librarian"),
                            result.getInt("id_library"),
                            result.getInt("hall_num")});
                }
                break;
            }
            case "Readers":{
                while (result.next()) {
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getInt("id_library"),
                            result.getString("surname") + " " +
                                    result.getString("name") + " " +
                                    result.getString("patronymic"),
                            result.getString("status")});
                }
                break;
            }
            case "Teachers":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getInt("id_university"),
                            result.getString("faculty"),
                            result.getString("name_university")
                    });
                }
                break;
            }
        }

        result.close();

        return tableModel;
    }

    public void insertNewRecord(String sql) throws SQLException {
        Statement statement = connection.getConn().createStatement();
        statement.executeUpdate(sql);
    }

    private String getPrimaryKeyNameByTableName(){
        switch (tableName){
            case "Libraries":{
                return "id_library";
            }
            case "Halls":{
                return "id_hall";
            }
            case "Librarians":{
                return "id_librarian";
            }
            case "Readers":
            case "Teachers": {
                return "id_reader";
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

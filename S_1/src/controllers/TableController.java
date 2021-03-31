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
            case "Researchers":
                return new String[]{"Id-читатель", "Id-институт", "Адрес института", "Степень", "Название института"};
            case "SchoolChild":
                return new String[]{"Id-читетль", "Id-школа", "Класс", "Название школы"};
            case "Students":
                return new String[]{"Id-читатель", "Id-университет", "Факультет", "Нзавание университета"};
            case "Pensioners":
                return new String[]{"Id-читатель", "Номер пенсионного свид."};
            case "Workers":
                return new String[]{"Id-читатель", "Адрес фирмы", "Название фирмы"};
            case "Editions":
                return new String[]{"Идентификатор", "Id-библиотека", "Зал", "Стеллаж", "Полка", "Поступление", "Списание"};
            case "Compositions":
                return new String[]{"Идентификатор", "Id-издание", "Автор", "Название", "Популярность", "Жанр"};
            case "Rules":
                return new String[]{"Идентификатор", "Id-издание", "Текст"};
            case "Issued_Books":
                return new String[]{"Идентификатор", "Id-бибилотекарь", "Id-издание", "Id-произведения", "Id-читатель", "Дата выдачи", "Дата возврата", "Возврат выполнен"};
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
            case "Editions":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_edition"),
                            result.getInt("id_library"),
                            result.getInt("hall_num"),
                            result.getInt("rack_num"),
                            result.getInt("shelf_num"),
                            result.getDate("date_of_admission"),
                            result.getDate("write_off_date")
                    });
                }
                break;
            }
            case "Compositions":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_record"),
                            result.getInt("id_edition"),
                            result.getString("author"),
                            result.getString("title"),
                            result.getFloat("popularity"),
                            result.getString("genre")
                    });
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
            case "Researchers":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getInt("id_university"),
                            result.getString("address_university"),
                            result.getString("degree"),
                            result.getString("name_university")
                    });
                }
            }
            case "SchoolChild":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getInt("id_school"),
                            result.getInt("grade"),
                            result.getString("name_school")
                    });
                }
            }
            case "Students":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getInt("id_university"),
                            result.getString("faculty"),
                            result.getString("name_university")
                    });
                }
            }
            case "Pensioners":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getInt("id_pensioner")
                    });
                }
            }
            case "Workers":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_reader"),
                            result.getString("firm_address"),
                            result.getString("name_firm")
                    });
                }
            }

            case "Rules":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_rule"),
                            result.getInt("id_edition"),
                            result.getString("rule_text")
                    });
                }
                break;
            }
            case "Issued_Books":{
                while (result.next()){
                    tableModel.addRow(new Object[]{
                            result.getInt("id_record"),
                            result.getInt("id_librarian"),
                            result.getInt("id_edition"),
                            result.getInt("id_composition"),
                            result.getInt("id_reader"),
                            result.getDate("date_of_issue"),
                            result.getDate("return_date"),
                            result.getString("is_returned")
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
            case "Editions":{
                return "id_edition";
            }
            case "Compositions":
            case "Issued_Books": {
                return "id_record";
            }
            case "Readers":
            case "Researchers":
            case "Students":
            case "SchoolChild":
            case "Pensioners":
            case "Workers":
            case "Teachers": {
                return "id_reader";
            }
            case "Rules":{
                return "id_rule";
            }
        }
        return null;
    }

    public void deleteRecord(Object rowKey) throws SQLException {
        Statement statement = connection.getConn().createStatement();
        String sql = "delete from " + tableName + " where " + getPrimaryKeyNameByTableName() + " = " + rowKey;
        //System.out.println(sql);
        statement.executeUpdate(sql);
        statement.executeUpdate("commit ");
    }

    public void modifyRow(String sqlValuesSet) throws SQLException {
        Statement statement = connection.getConn().createStatement();
        String sql = "update " + tableName + " " + sqlValuesSet;
        statement.executeUpdate(sql);
        statement.executeUpdate("commit ");
    }
}

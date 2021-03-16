package gui.tablesView;

import connection.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableFrame extends JFrame {
    private String tableName;
    DBConnection connection;

    public TableFrame(String tableName, DBConnection connection){
        this.tableName = tableName;
        this.connection = connection;
    }

    private String[] createColumnsHeaders(){
        switch (tableName){
            case "Libraries":
                return new String[]{"Id", "Количество книг"};
            default:
                return null;
        }
    }

    private String translateTableName(){
        switch (tableName){
            case "Libraries":
                return "Библиотеки";
            default:
                return null;
        }
    }

    public void openTable() throws SQLException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 400, dimension.height/2 - 300, 800, 600);
        this.setTitle(translateTableName());

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

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

        JLabel tableTitle = new JLabel("Таблица \""+ translateTableName() + "\"");
        tableTitle.setFont(new Font(tableTitle.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, tableTitle, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, tableTitle, 20, SpringLayout.WEST, jPanel);
        jPanel.add(tableTitle);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.NORTH, tableTitle);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, jPanel);
        jPanel.add(scrollPane);

        JLabel infoTable = new JLabel("<html>Модификация<br>таблицы данных:</html>");
        infoTable.setFont(new Font(infoTable.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, infoTable, 50, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, infoTable, 20, SpringLayout.EAST, scrollPane);
        jPanel.add(infoTable);

        //ввод данных в таблицу
        JButton insert = new JButton("Добавить запись");
        insert.setFont(new Font(insert.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, insert, 20, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, insert, 30, SpringLayout.SOUTH, infoTable);
        jPanel.add(insert);

        //Удаление данных из таблицы
        JButton delete = new JButton("Удалить запись");
        delete.setFont(new Font(delete.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, delete, 20, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, delete, 30, SpringLayout.SOUTH, insert);
        jPanel.add(delete);

        //Модификация данных таблицы
        JButton modify = new JButton("Изменить запись");
        modify.setFont(new Font(modify.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, modify, 20, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, modify, 30, SpringLayout.SOUTH, delete);
        jPanel.add(modify);




        JButton exit = new JButton("Закрыть таблицу");
        exit.setFont(new Font(exit.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, exit, 20, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.SOUTH, exit, -20, SpringLayout.SOUTH, jPanel);
        exit.addActionListener(e -> setVisible(false));
        jPanel.add(exit);

        this.setResizable(false);
        this.setVisible(true);
    }
}

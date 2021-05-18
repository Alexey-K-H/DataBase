package gui.tablesView;

import connection.DBConnection;
import controllers.TableController;
import gui.tablesView.insertViews.*;
import gui.tablesView.modifyViews.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableFrame extends JDialog {
    private final String tableName;
    DBConnection connection;
    private final TableController tableController;
    private String userId;

    public TableFrame(TableController tableController){
        this.tableName = tableController.getTableName();
        this.connection = tableController.getConnection();
        this.tableController = tableController;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String translateTableName(){
        switch (tableName){
            case "Libraries":
                return "Библиотеки";
            case "Halls":
                return "Читальные залы";
            case "Librarians":
                return "Библиотекари";
            case "Readers":
                return "Читатели.Общее";
            case "Editions":
                return "Издания";
            case "Compositions":
                return "Произведения";
            case "Rules":
                return "Правила пользования";
            case "Issued_Books":
                return "Выданные книги";
            default:
                return null;
        }
    }

    public void openTable(boolean isForUser) throws SQLException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 500, dimension.height/2 - 400, 1000, 800);
        this.setTitle(translateTableName());

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        DefaultTableModel tableModel;
        if(!isForUser){
            tableModel = tableController.getTableSet();
        }else {
            tableModel = tableController.getUserTableSet(userId);
        }


        JLabel tableTitle = new JLabel();
        if(!isForUser){
            tableTitle.setText("Таблица \""+ translateTableName() + "\"");
        }
        else{
            tableTitle.setText("Выданные вам книги");
        }

        tableTitle.setFont(new Font(tableTitle.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, tableTitle, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, tableTitle, 20, SpringLayout.WEST, jPanel);
        jPanel.add(tableTitle);

        JTable table = new JTable(tableModel);
        if(tableName.equals("Rules")){
            table.setRowHeight(50);
        }
        else {
            table.setRowHeight(20);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        int horizontalPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        int verticalPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        scrollPane.setHorizontalScrollBarPolicy(horizontalPolicy);
        scrollPane.setVerticalScrollBarPolicy(verticalPolicy);

        layout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.NORTH, tableTitle);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, jPanel);
        scrollPane.setPreferredSize(new Dimension(2*this.getWidth()/3, this.getHeight()));

        int prefSize = scrollPane.getPreferredSize().width/table.getColumnCount();
        for(int i = 0; i < table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(prefSize);
        }

        jPanel.add(scrollPane);

        JLabel infoTable = new JLabel("<html>Модификация<br>таблицы данных:</html>");
        infoTable.setFont(new Font(infoTable.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, infoTable, 50, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, infoTable, 10, SpringLayout.EAST, scrollPane);
        if(!isForUser){
            jPanel.add(infoTable);
        }

        //ввод данных в таблицу
        JButton insert = new JButton("Добавить запись");
        insert.setFont(new Font(insert.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, insert, 10, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, insert, 30, SpringLayout.SOUTH, infoTable);

        insert.addActionListener(e -> {
            switch (tableName){
                case "Libraries":{
                    LibraryInsert libraryInsert = new LibraryInsert(tableController, tableModel);
                    libraryInsert.openInsertWindow();
                    break;
                }
                case "Halls":{
                    HallInsert hallInsert = new HallInsert(tableController, tableModel);
                    hallInsert.openInsertWindow();
                    break;
                }
                case "Librarians":{
                    LibrarianInsert librarianInsert = new LibrarianInsert(tableController, tableModel);
                    librarianInsert.openInsertWindow();
                    break;
                }
                case "Readers":{
                    ReaderInsert readerInsert = new ReaderInsert(tableController, tableModel);
                    readerInsert.openInsertWindow();
                    break;
                }
                case "Editions":{
                    EditionInsert editionInsert = new EditionInsert(tableController, tableModel);
                    editionInsert.openInsertWindow();
                    break;
                }
                case "Compositions":{
                    CompositionInsert compositionInsert = new CompositionInsert(tableController, tableModel);
                    compositionInsert.openInsertWindow();
                    break;
                }
                case "Issued_Books":{
                    IssuedBooksInsert issuedBooksInsert = new IssuedBooksInsert(tableController,tableModel);
                    issuedBooksInsert.openInsertWindow();
                    break;
                }
                case "Rules":{
                    RuleInsert ruleInsert = new RuleInsert(tableController, tableModel);
                    ruleInsert.openInsertWindow();
                    break;
                }
            }

        });

        if(!isForUser) {
            jPanel.add(insert);
        }

        String beforeDelOdUpdateStr;
        if(!tableName.equals("Halls") && !tableName.equals("Rules")){
            beforeDelOdUpdateStr = "<html><p>Перед <b>удалением</b><br> или <b>изменением</b> данных<br>" +
                    " выберите строку в таблице</html>";
        }
        else{
            beforeDelOdUpdateStr = "<html><p>Перед <b>удалением</b><br>данных<br>" +
                    " выберите строку в таблице</html>";
        }
        JLabel beforeDelOrUpdateInfo = new JLabel(beforeDelOdUpdateStr);

        beforeDelOrUpdateInfo.setFont(new Font(beforeDelOrUpdateInfo.getFont().getName(), Font.ITALIC, 16));
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        beforeDelOrUpdateInfo.setBorder(solidBorder);
        beforeDelOrUpdateInfo.setIcon(icon);
        layout.putConstraint(SpringLayout.NORTH, beforeDelOrUpdateInfo, 70, SpringLayout.NORTH, insert);
        layout.putConstraint(SpringLayout.WEST, beforeDelOrUpdateInfo, 10, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.EAST, beforeDelOrUpdateInfo, -10, SpringLayout.EAST, jPanel);
        if(!isForUser){
            jPanel.add(beforeDelOrUpdateInfo);
        }


        //Удаление данных из таблицы
        JButton delete = new JButton("Удалить запись");
        delete.setFont(new Font(delete.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, delete, 10, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, delete, 30, SpringLayout.SOUTH, beforeDelOrUpdateInfo);
        delete.addActionListener(e -> {
            int i = table.getSelectedRow();
            if(i == -1){
                JLabel error = new JLabel("Перед удалением необходимо выбрать запись в таблице!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Object rowDataKey = table.getValueAt(i,0);
                try {
                    tableController.deleteRecord(rowDataKey, tableController.getTableName().equals("Readers") ||
                            tableController.getTableName().equals("Librarians"));
                    tableModel.removeRow(i);
                    JLabel success = new JLabel("Запись удалена успешно!");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "DELETE", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    error.setText(exception.getMessage());
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        if(!isForUser) {
            jPanel.add(delete);
        }
        delete.setVisible(false);

        //Модификация данных таблицы
        JButton modify = new JButton("Изменить запись");
        modify.setFont(new Font(modify.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, modify, 10, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, modify, 30, SpringLayout.SOUTH, delete);
        modify.addActionListener(e -> {
            int i = table.getSelectedRow();
            if(i == -1){
                JLabel error = new JLabel("Перед модификацией необходимо выбрать запись в таблице!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> oldValues = new ArrayList<>();
                for(int k = 0; k < table.getColumnCount(); k++){
                    oldValues.add(table.getValueAt(i, k).toString());
                }
                switch (tableName){
                    case "Libraries":{
                        LibraryModify libraryModify = new LibraryModify(tableController, oldValues, tableModel, i);
                        libraryModify.openModifyWindow();
                        break;
                    }
                    case "Librarians":{
                        LibrarianModify librarianModify = new LibrarianModify(tableController, oldValues, tableModel, i);
                        librarianModify.openModifyWindow();
                        break;
                    }
                    case "Readers":{
                        ReaderModify readerModify = new ReaderModify(tableController, oldValues, tableModel, i);
                        readerModify.openModifyWindow();
                        break;
                    }
                    case "Editions":{
                        EditionsModify editionsModify = new EditionsModify(tableController, oldValues, tableModel, i);
                        editionsModify.openModifyWindow();
                        break;
                    }
                    case "Compositions":{
                        CompositionsModify compositionsModify = new CompositionsModify(tableController, oldValues, tableModel, i);
                        compositionsModify.openModifyWindow();
                        break;
                    }
                    case "Issued_Books":{
                        IssuedBooksModify issuedBooksModify = new IssuedBooksModify(tableController, oldValues, tableModel, i);
                        issuedBooksModify.openModifyWindow();
                        break;
                    }
                }
            }
        });
        if(!tableName.equals("Halls") && !tableName.equals("Rules") && !isForUser){
            jPanel.add(modify);
            modify.setVisible(false);
        }


        //Выход из окна просмотра
        JButton exit = new JButton("Закрыть");
        exit.setFont(new Font(exit.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.EAST, exit, -20, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, exit, -20, SpringLayout.SOUTH, jPanel);
        exit.addActionListener(e -> setVisible(false));
        jPanel.add(exit);

        //Делает доступными кнопки удаления и модификации
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1){
                delete.setVisible(true);
                if(!tableName.equals("Halls")){
                    modify.setVisible(true);
                }
                repaint();
            }
        });

        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

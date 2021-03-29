package gui.tablesView;

import connection.DBConnection;
import controllers.TableController;
import gui.tablesView.modifyViews.categoryModify.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryTableFrame extends JDialog{
    private final String tableName;
    DBConnection connection;
    private final TableController tableController;

    public CategoryTableFrame(TableController tableController) {
        this.tableName = tableController.getTableName();
        this.connection = tableController.getConnection();
        this.tableController = tableController;
    }

    private String translateTableName(){
        switch (tableName){
            case "Teachers":
                return "Читатели.Учителя";
            case "Researchers":
                return "Читатели.Научные сотрудники";
            case "SchoolChild":
                return "Читатели.Школьники";
            case "Students":
                return "Читатели.Студенты";
            case "Pensioners":
                return "Читатели.Пенсионеры";
            case "Workers":
                return "Читатели.Рабочие";
            case "Others":
                return "Читатели.Прочие";
            default:
                return null;
        }
    }


    public void openTable() throws SQLException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 500, dimension.height/2 - 400, 1000, 800);
        this.setTitle(translateTableName());

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        DefaultTableModel tableModel = tableController.getTableSet();

        JLabel tableTitle = new JLabel("Таблица \""+ translateTableName() + "\"");
        tableTitle.setFont(new Font(tableTitle.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, tableTitle, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, tableTitle, 20, SpringLayout.WEST, jPanel);
        jPanel.add(tableTitle);

        JTable table = new JTable(tableModel);
        table.setRowHeight(20);
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
        jPanel.add(infoTable);

        JLabel addDeleteAlert = new JLabel("<html><p><b>Удаление</b><br> или <b>добавление</b> записей<br>" +
                "доступно <b>только через<br>" +
                "общую<b/> таблицу<br>" +
                "читателей!</html>");
        addDeleteAlert.setFont(new Font(addDeleteAlert.getFont().getName(), Font.ITALIC, 16));
        Icon icon = UIManager.getIcon("OptionPane.warningIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        addDeleteAlert.setBorder(solidBorder);
        addDeleteAlert.setIcon(icon);
        layout.putConstraint(SpringLayout.NORTH, addDeleteAlert, 70, SpringLayout.NORTH, infoTable);
        layout.putConstraint(SpringLayout.WEST, addDeleteAlert, 10, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.EAST, addDeleteAlert, -20, SpringLayout.EAST, jPanel);
        jPanel.add(addDeleteAlert);

        JLabel beforeDelOrUpdateInfo = new JLabel("<html><p>Перед <b>изменением</b> данных<br>" +
                " выберите строку в таблице</html>");
        beforeDelOrUpdateInfo.setFont(new Font(beforeDelOrUpdateInfo.getFont().getName(), Font.ITALIC, 16));
        Icon icon1 = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder1 = BorderFactory.createLineBorder(Color.BLACK, 1);
        beforeDelOrUpdateInfo.setBorder(solidBorder1);
        beforeDelOrUpdateInfo.setIcon(icon1);
        layout.putConstraint(SpringLayout.NORTH, beforeDelOrUpdateInfo, 30, SpringLayout.SOUTH, addDeleteAlert);
        layout.putConstraint(SpringLayout.WEST, beforeDelOrUpdateInfo, 10, SpringLayout.EAST, scrollPane);
        jPanel.add(beforeDelOrUpdateInfo);

        //Модификация данных таблицы
        JButton modify = new JButton("Изменить запись");
        modify.setFont(new Font(modify.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, modify, 10, SpringLayout.EAST, scrollPane);
        layout.putConstraint(SpringLayout.NORTH, modify, 30, SpringLayout.SOUTH, beforeDelOrUpdateInfo);
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
                    case "Teachers":{
                        TeacherModify teacherModify = new TeacherModify(tableController, oldValues, tableModel, i);
                        teacherModify.openModifyWindow();
                        break;
                    }
                    case "Students":{
                        StudentModify studentModify = new StudentModify(tableController, oldValues, tableModel, i);
                        studentModify.openModifyWindow();
                        break;
                    }
                    case "SchoolChild":{
                        SchoolChildModify schoolChildModify = new SchoolChildModify(tableController, oldValues, tableModel, i);
                        schoolChildModify.openModifyWindow();
                        break;
                    }
                    case "Pensioners":{
                        PensionersModify pensionersModify = new PensionersModify(tableController, oldValues, tableModel, i);
                        pensionersModify.openModifyWindow();
                        break;
                    }
                    case "Researchers":{
                        ResearchersModify researchersModify = new ResearchersModify(tableController, oldValues, tableModel, i);
                        researchersModify.openModifyWindow();
                        break;
                    }
                    case "Workers":{
                        WorkersModify workersModify = new WorkersModify(tableController, oldValues, tableModel, i);
                        workersModify.openModifyWindow();
                        break;
                    }
                }

            }
        });
        jPanel.add(modify);
        modify.setVisible(false);

        //Выход из окна просмотра
        JButton exit = new JButton("Закрыть таблицу");
        exit.setFont(new Font(exit.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.EAST, exit, -20, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, exit, -20, SpringLayout.SOUTH, jPanel);
        exit.addActionListener(e -> setVisible(false));
        jPanel.add(exit);

        //Делает доступными кнопки удаления и модификации
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1){
                modify.setVisible(true);
                repaint();
            }
        });

        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

package gui.queryWindow.query7;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class SeventhQuery extends QueryFrame {
    public SeventhQuery(QueryController queryController) {
        super(queryController);
    }

    @Override
    public void openQueryConfig() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск литературы");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>Выберите бибилиотеку из списка фонда<br><br>" +
                "Список имеет формат:<br>" +
                "[название библиотеки][ID]</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        try {
            String sql = "select id_library, name from libraries";
            getQueryController().performSQLQuery(sql);
            ArrayList<String> librariesTitles = new ArrayList<>();
            while (getQueryController().getCurrResultSet().next()){
                librariesTitles.add(getQueryController().getCurrResultSet().getString("name") + " [" +
                        getQueryController().getCurrResultSet().getInt("id_library") + "]");
            }
            getQueryController().closeSQLSet();

            JComboBox<String> libraryChoose = new JComboBox(librariesTitles.toArray());
            libraryChoose.setFont(new Font(libraryChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, libraryChoose, 10, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.WEST, libraryChoose, 20, SpringLayout.WEST, jPanel);
            jPanel.add(libraryChoose);

            JButton chooseShelf = new JButton("Выбрать полку");
            chooseShelf.setFont(new Font(chooseShelf.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, chooseShelf, 10, SpringLayout.SOUTH, libraryChoose);
            layout.putConstraint(SpringLayout.WEST, chooseShelf, 20, SpringLayout.WEST, jPanel);
            jPanel.add(chooseShelf);


            JLabel shelfLabel = new JLabel();
            shelfLabel.setFont(new Font(shelfLabel.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, shelfLabel, -40, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.WEST, shelfLabel, 20, SpringLayout.EAST, libraryChoose);
            shelfLabel.setVisible(false);
            jPanel.add(shelfLabel);

            JComboBox<String> shelfChoose = new JComboBox();
            shelfChoose.setFont(new Font(shelfChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, shelfChoose, 10, SpringLayout.SOUTH, shelfLabel);
            layout.putConstraint(SpringLayout.WEST, shelfChoose, 20, SpringLayout.EAST, libraryChoose);
            shelfChoose.setVisible(false);
            jPanel.add(shelfChoose);

            JButton confirm = new JButton("Найти литературу");
            confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
            layout.putConstraint(SpringLayout.EAST, confirm, -10, SpringLayout.EAST, jPanel);
            confirm.setVisible(false);
            jPanel.add(confirm);

            chooseShelf.addActionListener(e->{
                String choose = Objects.requireNonNull(libraryChoose.getSelectedItem()).toString();
                String id = choose.substring(choose.indexOf('[') + 1, choose.lastIndexOf(']'));
                String shelfSql = "select SHELF_NUM from EDITIONS\n" +
                        "where ID_LIBRARY = " + id;
                try {
                    getQueryController().performSQLQuery(shelfSql);
                    ArrayList<String> shelfNums = new ArrayList<>();
                    while (getQueryController().getCurrResultSet().next()){
                        shelfNums.add("[" + getQueryController().getCurrResultSet().getInt("shelf_num") + "]");
                    }
                    getQueryController().closeSQLSet();
                    shelfLabel.setText("<html>Номер полки:<br>" + choose + "</html>");
                    shelfLabel.setVisible(true);

                    shelfChoose.removeAllItems();
                    for(String shelfNum : shelfNums){
                        shelfChoose.addItem(shelfNum);
                    }

                    libraryChoose.addItemListener(e1 -> {
                        if(e1.getStateChange() == ItemEvent.SELECTED){
                            shelfLabel.setVisible(false);
                            shelfChoose.removeAllItems();
                            shelfChoose.setVisible(false);
                            confirm.removeActionListener(confirm.getActionListeners()[0]);
                            confirm.setVisible(false);
                        }
                    });

                    shelfChoose.setVisible(true);
                    confirm.setVisible(true);
                    confirm.addActionListener(e2->{
                        String chooseNum = Objects.requireNonNull(shelfChoose.getSelectedItem()).toString();
                        String shelfNum = chooseNum.substring(chooseNum.indexOf('[') + 1, chooseNum.lastIndexOf(']'));

                        String queryStr = "with t1 as (\n" +
                                "    --Выданная на текущий момент литература\n" +
                                "    select ID_EDITION, ID_COMPOSITION from ISSUED_BOOKS where IS_RETURNED = 'нет'\n" +
                                "),\n" +
                                "     t2 as (\n" +
                                "         --Информация о выданной литературе\n" +
                                "         select t1.ID_EDITION as \"Издание\", TITLE as \"Название\", AUTHOR as \"Автор\", GENRE as \"Жанр\" from COMPOSITIONS\n" +
                                "            inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION\n" +
                                "     ),\n" +
                                "     t3 as (\n" +
                                "         --Литература с конкретной полки, конкретной библиотеки\n" +
                                "         select \"Издание\", \"Название\", \"Автор\", \"Жанр\" from t2 inner join EDITIONS on \"Издание\" = EDITIONS.ID_EDITION\n" +
                                "         where SHELF_NUM = " + shelfNum + " and ID_LIBRARY = "+ id +"\n" +
                                "     )\n" +
                                "select * from t3";

                        try {
                            getQueryController().performSQLQuery(queryStr);
                            ResultSet resultSet = getQueryController().getCurrResultSet();
                            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                            int columnsCount = resultSetMetaData.getColumnCount();
                            ArrayList<String> list = new ArrayList<>();
                            for(int i = 1; i <= columnsCount; i++){
                                list.add(resultSetMetaData.getColumnLabel(i));
                            }
                            String[] columnsHeaders = list.toArray(new String[0]);
                            ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                            getQueryController().closeSQLSet();
                        }
                        catch (SQLException exception){
                            JLabel error = new JLabel();
                            error.setText(exception.getMessage());
                            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    error.setText(exception.getMessage());
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            });
        }
        catch (SQLException exception){
            JLabel error = new JLabel();
            error.setText(exception.getMessage());
            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

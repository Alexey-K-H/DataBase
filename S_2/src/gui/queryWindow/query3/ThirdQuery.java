package gui.queryWindow.query3;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ThirdQuery extends QueryFrame {
    public ThirdQuery(QueryController queryController) {
        super(queryController);
    }

    public void openQueryConfig(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск читателей");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>Выберите издание из списка фонда<br><br>" +
                "Список имеет формат:<br>" +
                "[произведение][издание]</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        String sql = "select id_edition, title from Compositions";
        try {
            getQueryController().performSQLQuery(sql);
            ArrayList<String> compositionsTitles = new ArrayList<>();
            while (getQueryController().getCurrResultSet().next()){
                compositionsTitles.add(getQueryController().getCurrResultSet().getString("title") + " [" +
                        getQueryController().getCurrResultSet().getInt("id_edition") + "]");
            }
            getQueryController().closeSQLSet();

            JComboBox<String> editionChoose = new JComboBox(compositionsTitles.toArray());
            editionChoose.setFont(new Font(editionChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, editionChoose, 10, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.WEST, editionChoose, 20, SpringLayout.WEST, jPanel);
            jPanel.add(editionChoose);

            JButton find = new JButton("Найти читателей");
            find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
            layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
            find.addActionListener(e -> {
                String choose = Objects.requireNonNull(editionChoose.getSelectedItem()).toString();
                String title = choose.substring(0, choose.indexOf('[') - 1);
                //System.out.println(title);
                String edition = choose.substring(choose.indexOf('[') + 1, choose.lastIndexOf(']'));
                //System.out.println(edition);

                String queryStr = "with t1 as (\n" +
                        "    --Выданные экземпляры произведения конткретного издания\n" +
                        "    select ID_READER, ID_COMPOSITION, TITLE from ISSUED_BOOKS\n" +
                        "    inner join COMPOSITIONS C2 on C2.ID_RECORD = ISSUED_BOOKS.ID_COMPOSITION\n" +
                        "    where TITLE = '" + title + "' and C2.ID_EDITION = " + edition + "\n" +
                        "),\n" +
                        "t2 as (\n" +
                        "    select READERS.ID_READER, SURNAME, NAME, PATRONYMIC from READERS\n" +
                        "    inner join t1 on t1.ID_READER = READERS.ID_READER\n" +
                        ")\n" +
                        "select ID_READER as \"ID-читателя\", SURNAME as \"Фамилия\", NAME as \"Имя\", PATRONYMIC as \"Отчество\" from t2";
                //System.out.println(queryStr);


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

                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    error.setText(exception.getMessage());
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            });
            jPanel.add(find);

        } catch (SQLException exception) {
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

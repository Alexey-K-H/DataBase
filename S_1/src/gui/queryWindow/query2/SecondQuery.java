package gui.queryWindow.query2;

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

public class SecondQuery extends QueryFrame {
    public SecondQuery(QueryController queryController) {
        super(queryController);
    }

    public void openQueryConfig() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск читателей");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Выберите название произведения из списка книг фонда");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        String sql = "select distinct title from Compositions";
        try {
            getQueryController().performSQLQuery(sql);
            ArrayList<String> compositionsTitles = new ArrayList<>();
            while (getQueryController().getCurrResultSet().next()){
                compositionsTitles.add(getQueryController().getCurrResultSet().getString("title"));
            }
            getQueryController().closeSQLSet();

            JComboBox<String> titleChoose = new JComboBox(compositionsTitles.toArray());
            titleChoose.setFont(new Font(titleChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, titleChoose, 10, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.WEST, titleChoose, 20, SpringLayout.WEST, jPanel);
            jPanel.add(titleChoose);

            JButton find = new JButton("Найти читателей");
            find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
            layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
            find.addActionListener(e -> {
                String title = Objects.requireNonNull(titleChoose.getSelectedItem()).toString();
                String queryStr = "with t1 as (\n" +
                        "    --Выданные экземпляры произведения\n" +
                        "    select ID_READER, ID_COMPOSITION, TITLE from ISSUED_BOOKS\n" +
                        "    inner join COMPOSITIONS C2 on C2.ID_RECORD = ISSUED_BOOKS.ID_COMPOSITION\n" +
                        "    where TITLE = '"+ title + "'\n" +
                        "),\n" +
                        "t2 as (\n" +
                        "    select READERS.ID_READER AS \"ID-читателя\", SURNAME AS \"Фамилия\", NAME AS \"Имя\", " +
                        "    PATRONYMIC AS \"Отчество\" from READERS\n" +
                        "    inner join t1 on t1.ID_READER = READERS.ID_READER\n" +
                        ")\n" +
                        "select * from t2";

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

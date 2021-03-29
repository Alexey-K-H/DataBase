package gui.queryWindow.query12;

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

public class TwelveQuery extends QueryFrame {

    public TwelveQuery(QueryController queryController) {
        super(queryController);
    }

    public void openQueryConfig(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск библиотекарей");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>Выберите библиотеку и зал из списка фонда<br><br>" +
                "Список имеет формат:<br>" +
                "[бибилиотека_№][зал_№]</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        String sql = "select id_hall, id_library from Halls";

        try {
            getQueryController().performSQLQuery(sql);
            ArrayList<String> hallsOfLibraries = new ArrayList<>();
            while (getQueryController().getCurrResultSet().next()){
                hallsOfLibraries.add("Бибилиотека_№" + getQueryController().getCurrResultSet().getInt("id_library") +
                        " [Зал_№" + getQueryController().getCurrResultSet().getInt("id_hall") + "]");
            }
            getQueryController().closeSQLSet();

            JComboBox<String> hallChoose = new JComboBox(hallsOfLibraries.toArray());
            hallChoose.setFont(new Font(hallChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, hallChoose, 10, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.WEST, hallChoose, 20, SpringLayout.WEST, jPanel);
            jPanel.add(hallChoose);

            JButton find = new JButton("Найти бибилотекарей");
            find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
            layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
            find.addActionListener(e->{
                String choose = Objects.requireNonNull(hallChoose.getSelectedItem()).toString();
                String lib = choose.substring(choose.indexOf('№') + 1, choose.indexOf('[') - 1);
                //System.out.println("(" + lib + ")");

                String hall = choose.substring(choose.lastIndexOf('№') + 1, choose.indexOf(']'));
                //System.out.println("(" + hall + ")");

                String queryStr = "with t1 as (\n" +
                        "    --Выбираем конкретный зал\n" +
                        "    select ID_HALL, ID_LIBRARY from HALLS\n" +
                        "    where ID_HALL = " + hall + " and ID_LIBRARY = " + lib + "\n" +
                        "),\n" +
                        "t2 as (\n" +
                        "    select ID_LIBRARIAN, HALL_NUM, LIBRARIANS.ID_LIBRARY from LIBRARIANS\n" +
                        "    inner join t1 on ID_HALL = LIBRARIANS.HALL_NUM and t1.ID_LIBRARY = LIBRARIANS.ID_LIBRARY\n" +
                        ")\n" +
                        "select ID_LIBRARIAN as \"ID-библиотекаря\" from t2";

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

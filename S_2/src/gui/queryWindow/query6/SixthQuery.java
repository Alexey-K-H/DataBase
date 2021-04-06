package gui.queryWindow.query6;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class SixthQuery extends QueryFrame {

    public SixthQuery(QueryController queryController) {
        super(queryController);
    }

    @Override
    public void openQueryConfig() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск изданий");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        try {
            String sql = "select id_reader, surname, name, patronymic from readers";
            getQueryController().performSQLQuery(sql);
            ArrayList<String> readersIds = new ArrayList<>();
            while (getQueryController().getCurrResultSet().next()){
                readersIds.add("[" + getQueryController().getCurrResultSet().getInt("id_reader") + "] " +
                        getQueryController().getCurrResultSet().getString("surname") + " " +
                        getQueryController().getCurrResultSet().getString("name") + " " +
                        getQueryController().getCurrResultSet().getString("patronymic"));
            }
            getQueryController().closeSQLSet();

            JLabel info = new JLabel("<html>Укажите период вермени для поиска</html>");
            info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
            layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
            jPanel.add(info);

            JLabel begin = new JLabel("Начало периода в формате (дд.мм.гггг)");
            begin.setFont(new Font(begin.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, begin, 30, SpringLayout.NORTH, info);
            layout.putConstraint(SpringLayout.WEST, begin, 30, SpringLayout.WEST, jPanel);
            jPanel.add(begin);

            MaskFormatter maskFormatter = null;
            try {
                maskFormatter = new MaskFormatter("##.##.####");
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            JFormattedTextField beginValue = new JFormattedTextField(maskFormatter);
            beginValue.setColumns(7);
            beginValue.setFont(new Font(beginValue.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, beginValue, 10, SpringLayout.SOUTH, begin);
            layout.putConstraint(SpringLayout.WEST, beginValue, 30, SpringLayout.WEST, jPanel);
            jPanel.add(beginValue);

            JLabel end = new JLabel("Конец периода в формате (дд.мм.гггг)");
            end.setFont(new Font(end.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, end, 30, SpringLayout.NORTH, beginValue);
            layout.putConstraint(SpringLayout.WEST, end, 30, SpringLayout.WEST, jPanel);
            jPanel.add(end);

            JFormattedTextField endValue = new JFormattedTextField(maskFormatter);
            endValue.setColumns(7);
            endValue.setFont(new Font(endValue.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, endValue, 10, SpringLayout.SOUTH, end);
            layout.putConstraint(SpringLayout.WEST, endValue, 30, SpringLayout.WEST, jPanel);
            jPanel.add(endValue);

            JLabel idReaderLabel = new JLabel("Идентификатор пользователя");
            idReaderLabel.setFont(new Font(idReaderLabel.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, idReaderLabel, 20, SpringLayout.SOUTH, endValue);
            layout.putConstraint(SpringLayout.WEST, idReaderLabel, 30, SpringLayout.WEST, jPanel);
            jPanel.add(idReaderLabel);

            JComboBox<String> idReaderChoose = new JComboBox(readersIds.toArray());
            idReaderChoose.setFont(new Font(idReaderChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, idReaderChoose, 20, SpringLayout.SOUTH, idReaderLabel);
            layout.putConstraint(SpringLayout.WEST, idReaderChoose, 30, SpringLayout.WEST, jPanel);
            jPanel.add(idReaderChoose);

            JButton find = new JButton("Найти издания");
            find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
            layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
            find.addActionListener(e->{
                String choose = Objects.requireNonNull(idReaderChoose.getSelectedItem()).toString();
                String id = choose.substring(choose.indexOf('[') + 1, choose.lastIndexOf(']'));
                System.out.println(id);
                String beginDate = beginValue.getText();
                String endDate = endValue.getText();
                String querySql = "with t1 as (\n" +
                        "    --Выданные книги в указанном промежутке для выбранного читателя\n" +
                        "    select ID_COMPOSITION, ID_EDITION\n" +
                        "    from ISSUED_BOOKS where DATE_OF_ISSUE >= to_date('"+beginDate+"', 'dd.mm.yyyy') and DATE_OF_ISSUE <= to_date('"+endDate+"', 'dd.mm.yyyy') and ID_READER = "+id+"\n" +
                        "),\n" +
                        "     t2 as (\n" +
                        "         --Поиск библиотеки пользователя\n" +
                        "         select ID_LIBRARY\n" +
                        "         from READERS where ID_READER = "+id+"\n" +
                        "     ),\n" +
                        "     t3 as (\n" +
                        "         --Получить список изданий не из библиотеки пользователя\n" +
                        "         select ID_EDITION\n" +
                        "         from EDITIONS inner join t2 on t2.ID_LIBRARY != EDITIONS.ID_LIBRARY\n" +
                        "     ),\n" +
                        "     t4 as (\n" +
                        "         --Издания которые выдавались пользователю не в его бибилиотеке\n" +
                        "         select t1.ID_EDITION, t1.ID_COMPOSITION from t1 inner join t3 on t1.ID_EDITION = t3.ID_EDITION\n" +
                        "     ),\n" +
                        "     t5 as (\n" +
                        "         --Поиск изданий, которые есть в библиотеке польователя и которые выдавались ему\n" +
                        "         select t4.ID_EDITION as \"Издание\", TITLE as \"Название\" from COMPOSITIONS inner join t4 on t4.ID_COMPOSITION = ID_RECORD\n" +
                        "     )\n" +
                        "select * from t5";
                try {
                    getQueryController().performSQLQuery(querySql);
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

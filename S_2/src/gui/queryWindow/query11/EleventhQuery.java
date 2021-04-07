package gui.queryWindow.query11;

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

public class EleventhQuery extends QueryFrame {
    public EleventhQuery(QueryController queryController) {
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
        }
        catch (ParseException ex){
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

        JLabel category1 = new JLabel("Поиск");
        category1.setFont(new Font(category1.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, category1, 13, SpringLayout.SOUTH, endValue);
        layout.putConstraint(SpringLayout.WEST, category1, 30, SpringLayout.WEST, jPanel);
        jPanel.add(category1);

        String[] categories = {"поступившей", "списанной"};
        JComboBox<String> categoryChoose = new JComboBox(categories);
        categoryChoose.setFont(new Font(categoryChoose.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, categoryChoose, 10, SpringLayout.SOUTH, endValue);
        layout.putConstraint(SpringLayout.WEST, categoryChoose, 5, SpringLayout.EAST, category1);
        jPanel.add(categoryChoose);

        JLabel category2 = new JLabel("литературы");
        category2.setFont(new Font(category2.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, category2, 13, SpringLayout.SOUTH, endValue);
        layout.putConstraint(SpringLayout.WEST, category2, 5, SpringLayout.EAST, categoryChoose);
        jPanel.add(category2);


        JButton find = new JButton("Найти литературу");
        find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
        find.addActionListener(e -> {
            String category = Objects.requireNonNull(categoryChoose.getSelectedItem()).toString();
            String beginDate = beginValue.getText();
            String endDate = endValue.getText();

            String querySql;

            switch (category){
                case "поступившей":{
                    querySql = "with t1 as (\n" +
                            "    --Перечень литературы, которая поступила в течение определенного периода\n" +
                            "    --Поиск изданий данной категории\n" +
                            "    select ID_EDITION from EDITIONS\n" +
                            "    where DATE_OF_ADMISSION >= TO_DATE('"+ beginDate +"', 'dd.mm.yyyy')\n" +
                            "      and DATE_OF_ADMISSION <= TO_DATE('"+ endDate +"', 'dd.mm.yyyy')\n" +
                            "),\n" +
                            "     t2 as (\n" +
                            "         --Получение дополнительной информации об этих изданиях\n" +
                            "         select COMPOSITIONS.ID_EDITION as \"Издание\",\n" +
                            "                TITLE as \"Название\",\n" +
                            "                AUTHOR as \"Автор\",\n" +
                            "                GENRE as \"Жанр\"\n" +
                            "         from COMPOSITIONS\n" +
                            "                  inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION\n" +
                            "     )\n" +
                            "select * from t2";
                    break;
                }
                case "списанной":{
                    querySql = "with t1 as (\n" +
                            "    --Перечень литературы, которая была списана в течение определенного периода\n" +
                            "    --Поиск изданий данной категории\n" +
                            "    select ID_EDITION from EDITIONS\n" +
                            "    where WRITE_OFF_DATE >= TO_DATE('"+ beginDate +"', 'dd.mm.yyyy')\n" +
                            "      and WRITE_OFF_DATE <= TO_DATE('"+ endDate +"', 'dd.mm.yyyy')\n" +
                            "),\n" +
                            "     t2 as (\n" +
                            "         --Получение дополнительной информации об этих изданиях\n" +
                            "         select COMPOSITIONS.ID_EDITION as \"Издание\",\n" +
                            "                TITLE as \"Название\",\n" +
                            "                AUTHOR as \"Автор\",\n" +
                            "                GENRE as \"Жанр\"\n" +
                            "         from COMPOSITIONS\n" +
                            "                  inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION\n" +
                            "     )\n" +
                            "select * from t2";
                    break;
                }
                default:{
                    querySql = "";
                    break;
                }
            }

            try{
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

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

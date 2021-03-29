package gui.queryWindow.query1;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class FirstQuery extends QueryFrame {
    public FirstQuery(QueryController queryController) {
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

        JLabel info = new JLabel("<html>Выберите параметры поиска читателей:</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel category = new JLabel("Категория");
        category.setFont(new Font(category.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, category, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, category, 20, SpringLayout.WEST, jPanel);
        jPanel.add(category);

        String[] items = {
                "прочие",
                "ученик",
                "учитель",
                "студент",
                "научный сотрудник",
                "работник",
                "пенсионер"
        };
        JComboBox<String> categoryChose = new JComboBox<>(items);
        categoryChose.setFont(new Font(categoryChose.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, categoryChose, 20, SpringLayout.SOUTH, category);
        layout.putConstraint(SpringLayout.WEST, categoryChose, 20, SpringLayout.WEST, jPanel);
        jPanel.add(categoryChose);

        JButton categoryParameters = new JButton("Задать параметры выбранной категории");
        categoryParameters.setFont(new Font(categoryParameters.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, categoryParameters, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, categoryParameters, 20, SpringLayout.WEST, jPanel);
        categoryParameters.addActionListener(e ->{
            if(!categoryChose.getSelectedItem().toString().equals("прочие")){
                try {
                    AdditionalParameters additionalParameters = new AdditionalParameters(categoryChose.getSelectedItem().toString(), getQueryController());
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }

        });
        jPanel.add(categoryParameters);

        JLabel additionalParametersInfo = new JLabel("<html>Выбрав категорию,<br>можно задать<br>дополнительные параметры поиска<br>" +
                "по нажаитю кнопки под значением<br>выбранной категории.<br>Если параметры не указываются,<br>то будет показна вся категория</html>");

        additionalParametersInfo.setFont(new Font(additionalParametersInfo.getFont().getName(), Font.ITALIC, 16));
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        additionalParametersInfo.setBorder(solidBorder);
        additionalParametersInfo.setIcon(icon);
        layout.putConstraint(SpringLayout.NORTH, additionalParametersInfo, 10, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.EAST, additionalParametersInfo, -10, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.WEST, additionalParametersInfo, -360, SpringLayout.EAST, additionalParametersInfo);
        jPanel.add(additionalParametersInfo);

        JButton performQuery = new JButton("Вывести всех");
        performQuery.setFont(new Font(performQuery.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, performQuery, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, performQuery, -10, SpringLayout.EAST, jPanel);
        performQuery.addActionListener(e -> {
            String sql = "";
            switch (categoryChose.getSelectedItem().toString()){
                case "прочие":{
                    sql = "select id_reader as \"Id-читателя\" from Others";
                    break;
                }
                case "ученик":{
                    sql = "select id_reader as \"Id-читателя\", grade as \"Класс\", name_school as \"Школа\" " +
                            "from Schoolchild";
                    break;
                }
                case "учитель":{
                    sql = "select id_reader as \"Id-читателя\", faculty as \"Факультет\", name_university as \"Университет\" " +
                            "from Teachers";
                    break;
                }
                case "студент":{
                    sql = "select id_reader as \"Id-читателя\", faculty as \"Факультет\", name_university as \"Университет\" " +
                            "from Students";
                    break;
                }
                case "научный сотрудник":{
                    sql = "select id_reader as \"Id-читателя\", degree as \"Научная степень\", name_university as \"Институт\", " +
                            "address_university as \"Адрес института\" from Researchers";
                    break;
                }
                case "работник":{
                    sql = "select id_reader as \"Id-читателя\", name_firm as \"Фирма\", firm_address as \"Адрес фирмы\" " +
                            "from Workers";
                    break;
                }
                case "пенсионер":{
                    sql = "select id_reader as \"Id-читателя\", id_pensioner as \"№ пенсионного свидетельства\" from " +
                            "Pensioners";
                    break;
                }
            }

            try {
                getQueryController().performSQLQuery(sql);
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
        jPanel.add(performQuery);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

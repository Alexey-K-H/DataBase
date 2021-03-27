package gui.queryWindow.firstQuery;

import controllers.QueryController;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdditionalParameters extends JDialog {
    private QueryController queryController;
    private String categoryName;
    public AdditionalParameters(String categoryName, QueryController queryController) throws SQLException {

        this.categoryName = categoryName;
        this.queryController = queryController;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 150, 500, 300);
        this.setTitle("Дополнительные параметры");


        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel addList = new JLabel("<html>Список дополнительных<br>параметров категории: <b>\""+ categoryName + "\"</b></html>");
        addList.setFont(new Font(addList.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, addList, 10, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, addList, 10, SpringLayout.WEST, jPanel);
        jPanel.add(addList);

        switch (categoryName){
            case "ученик":{
                //название школы, класс
                JLabel nameSchool = new JLabel("Название школы");
                nameSchool.setFont(new Font(nameSchool.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, nameSchool, 10, SpringLayout.SOUTH, addList);
                layout.putConstraint(SpringLayout.WEST, nameSchool, 20, SpringLayout.WEST, jPanel);
                jPanel.add(nameSchool);

                String sql = "select name_school from SchoolChild";
                queryController.performSQLQuery(sql);

                ArrayList<String> schoolNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    schoolNames.add(queryController.getCurrResultSet().getString("name_school"));
                }

                queryController.closeSQLSet();

                JComboBox<String> schoolNameChoose = new JComboBox(schoolNames.toArray());
                schoolNameChoose.setFont(new Font(schoolNameChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, schoolNameChoose, 10, SpringLayout.SOUTH, nameSchool);
                layout.putConstraint(SpringLayout.WEST, schoolNameChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(schoolNameChoose);

                JLabel classLabel = new JLabel("Класс");
                classLabel.setFont(new Font(classLabel.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, classLabel, 20, SpringLayout.SOUTH, schoolNameChoose);
                layout.putConstraint(SpringLayout.WEST, classLabel, 20, SpringLayout.WEST, jPanel);
                jPanel.add(classLabel);

                String[] items = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "Все"};
                JComboBox<String> classChoose = new JComboBox<>(items);
                classChoose.setFont(new Font(classChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, classChoose, 10, SpringLayout.SOUTH, classLabel);
                layout.putConstraint(SpringLayout.WEST, classChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(classChoose);

                JButton find = new JButton("Найти");
                find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
                layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
                find.addActionListener(e->{
                    String sqlQuery;
                    if(classChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-школьника\", grade as \"Класс\", name_school as \"Школа\" from " +
                                "SchoolChild where name_school = '" + schoolNameChoose.getSelectedItem().toString() + "'";
                    }
                    else{
                        sqlQuery = "select id_reader as \"Id-школьника\", grade as \"Класс\", name_school as \"Школа\" from " +
                                "SchoolChild where grade = " + classChoose.getSelectedItem().toString() + " and " +
                                "name_school = '" + schoolNameChoose.getSelectedItem().toString() + "'";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultQueryView queryView = new ResultQueryView(resultSet);
                        queryController.closeSQLSet();

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
            }
        }

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);

    }

}

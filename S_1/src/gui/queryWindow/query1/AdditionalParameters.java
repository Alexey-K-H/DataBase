package gui.queryWindow.query1;

import controllers.QueryController;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

                String sql = "select distinct name_school from SchoolChild";
                queryController.performSQLQuery(sql);

                ArrayList<String> schoolNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    schoolNames.add(queryController.getCurrResultSet().getString("name_school"));
                }
                schoolNames.add("Все");
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
                    String sqlQuery = "";
                    if(!classChoose.getSelectedItem().toString().equals("Все") &&
                            !schoolNameChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-школьника\", grade as \"Класс\", name_school as \"Школа\" from " +
                                "SchoolChild where name_school = '" + schoolNameChoose.getSelectedItem().toString() + "' and " +
                                "grade = " + classChoose.getSelectedItem().toString();
                    }
                    else if(classChoose.getSelectedItem().toString().equals("Все") &&
                    !schoolNameChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-школьника\", grade as \"Класс\", name_school as \"Школа\" from " +
                                "SchoolChild where name_school = '" + schoolNameChoose.getSelectedItem().toString() + "'";
                    }
                    else if(!classChoose.getSelectedItem().toString().equals("Все") &&
                            schoolNameChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-школьника\", grade as \"Класс\", name_school as \"Школа\" from " +
                                "SchoolChild where grade = " + classChoose.getSelectedItem().toString();
                    }
                    else if(classChoose.getSelectedItem().toString().equals("Все") &&
                            schoolNameChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-школьника\", grade as \"Класс\", name_school as \"Школа\" from " +
                                "SchoolChild";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnsCount = resultSetMetaData.getColumnCount();

                        ArrayList<String> list = new ArrayList<>();
                        for(int i = 1; i <= columnsCount; i++){
                            list.add(resultSetMetaData.getColumnLabel(i));
                        }

                        String[] columnsHeaders = list.toArray(new String[0]);

                        ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                        queryController.closeSQLSet();
                        this.setVisible(false);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
                break;
            }
            case "учитель":{
                //название вуза, название факультета
                JLabel nameUniversity = new JLabel("Название вуза");
                nameUniversity.setFont(new Font(nameUniversity.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, nameUniversity, 10, SpringLayout.SOUTH, addList);
                layout.putConstraint(SpringLayout.WEST, nameUniversity, 20, SpringLayout.WEST, jPanel);
                jPanel.add(nameUniversity);

                String sql = "select distinct name_university from Teachers";
                queryController.performSQLQuery(sql);
                ArrayList<String> universityNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    universityNames.add(queryController.getCurrResultSet().getString("name_university"));
                }
                universityNames.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> universityNameChoose = new JComboBox(universityNames.toArray());
                universityNameChoose.setFont(new Font(universityNameChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, universityNameChoose, 10, SpringLayout.SOUTH, nameUniversity);
                layout.putConstraint(SpringLayout.WEST, universityNameChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(universityNameChoose);

                JLabel facultyNameLabel = new JLabel("Факультет");
                facultyNameLabel.setFont(new Font(facultyNameLabel.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, facultyNameLabel, 20, SpringLayout.SOUTH, universityNameChoose);
                layout.putConstraint(SpringLayout.WEST, facultyNameLabel, 20, SpringLayout.WEST, jPanel);
                jPanel.add(facultyNameLabel);

                String sql1 = "select distinct faculty from Teachers";
                queryController.performSQLQuery(sql1);
                ArrayList<String> facultyNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    facultyNames.add(queryController.getCurrResultSet().getString("faculty"));
                }
                facultyNames.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> facultyNamesChoose = new JComboBox(facultyNames.toArray());
                facultyNamesChoose.setFont(new Font(facultyNamesChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, facultyNamesChoose, 10, SpringLayout.SOUTH, facultyNameLabel);
                layout.putConstraint(SpringLayout.WEST, facultyNamesChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(facultyNamesChoose);


                JButton find = new JButton("Найти");
                find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
                layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
                find.addActionListener(e->{
                    String sqlQuery = "";
                    if(!universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            !facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Teachers where name_university = '" + universityNameChoose.getSelectedItem().toString() + "' and " +
                                "faculty = '" + facultyNamesChoose.getSelectedItem().toString() + "'";
                    }
                    else if(!universityNameChoose.getSelectedItem().toString().equals("Все") &&
                    facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Teachers where name_university = '" + universityNameChoose.getSelectedItem().toString() + "'";
                    }
                    else if(universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            !facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Teachers where faculty = '" + facultyNamesChoose.getSelectedItem().toString() + "'";
                    }
                    else if(universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Teachers";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnsCount = resultSetMetaData.getColumnCount();

                        ArrayList<String> list = new ArrayList<>();
                        for(int i = 1; i <= columnsCount; i++){
                            list.add(resultSetMetaData.getColumnLabel(i));
                        }

                        String[] columnsHeaders = list.toArray(new String[0]);

                        ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                        queryController.closeSQLSet();
                        this.setVisible(false);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
                break;
            }
            case "студент":{
                //название вуза, название факультета
                JLabel nameUniversity = new JLabel("Название вуза");
                nameUniversity.setFont(new Font(nameUniversity.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, nameUniversity, 10, SpringLayout.SOUTH, addList);
                layout.putConstraint(SpringLayout.WEST, nameUniversity, 20, SpringLayout.WEST, jPanel);
                jPanel.add(nameUniversity);

                String sql = "select distinct name_university from Students";
                queryController.performSQLQuery(sql);
                ArrayList<String> universityNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    universityNames.add(queryController.getCurrResultSet().getString("name_university"));
                }
                universityNames.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> universityNameChoose = new JComboBox(universityNames.toArray());
                universityNameChoose.setFont(new Font(universityNameChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, universityNameChoose, 10, SpringLayout.SOUTH, nameUniversity);
                layout.putConstraint(SpringLayout.WEST, universityNameChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(universityNameChoose);

                JLabel facultyNameLabel = new JLabel("Факультет");
                facultyNameLabel.setFont(new Font(facultyNameLabel.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, facultyNameLabel, 20, SpringLayout.SOUTH, universityNameChoose);
                layout.putConstraint(SpringLayout.WEST, facultyNameLabel, 20, SpringLayout.WEST, jPanel);
                jPanel.add(facultyNameLabel);

                String sql1 = "select distinct faculty from Students";
                queryController.performSQLQuery(sql1);
                ArrayList<String> facultyNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    facultyNames.add(queryController.getCurrResultSet().getString("faculty"));
                }
                facultyNames.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> facultyNamesChoose = new JComboBox(facultyNames.toArray());
                facultyNamesChoose.setFont(new Font(facultyNamesChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, facultyNamesChoose, 10, SpringLayout.SOUTH, facultyNameLabel);
                layout.putConstraint(SpringLayout.WEST, facultyNamesChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(facultyNamesChoose);


                JButton find = new JButton("Найти");
                find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
                layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
                find.addActionListener(e->{
                    String sqlQuery = "";
                    if(!universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            !facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Students where name_university = '" + universityNameChoose.getSelectedItem().toString() + "' and " +
                                "faculty = '" + facultyNamesChoose.getSelectedItem().toString() + "'";
                    }
                    else if(!universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Students where name_university = '" + universityNameChoose.getSelectedItem().toString() + "'";
                    }
                    else if(universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            !facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Students where faculty = '" + facultyNamesChoose.getSelectedItem().toString() + "'";
                    }
                    else if(universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            facultyNamesChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-учителя\", name_university as \"Название университета\", faculty as \"Факультет\" from " +
                                "Students";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnsCount = resultSetMetaData.getColumnCount();

                        ArrayList<String> list = new ArrayList<>();
                        for(int i = 1; i <= columnsCount; i++){
                            list.add(resultSetMetaData.getColumnLabel(i));
                        }

                        String[] columnsHeaders = list.toArray(new String[0]);

                        ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                        queryController.closeSQLSet();
                        this.setVisible(false);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
                break;
            }
            case "научный сотрудник":{
                //научная степень, название института
                JLabel degree = new JLabel("Научная степень");
                degree.setFont(new Font(degree.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, degree, 10, SpringLayout.SOUTH, addList);
                layout.putConstraint(SpringLayout.WEST, degree, 20, SpringLayout.WEST, jPanel);
                jPanel.add(degree);

                String sql = "select distinct degree from Researchers";
                queryController.performSQLQuery(sql);
                ArrayList<String> degrees = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    degrees.add(queryController.getCurrResultSet().getString("degree"));
                }
                degrees.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> degreeChoose = new JComboBox(degrees.toArray());
                degreeChoose.setFont(new Font(degreeChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, degreeChoose, 10, SpringLayout.SOUTH, degree);
                layout.putConstraint(SpringLayout.WEST, degreeChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(degreeChoose);

                JLabel universityName = new JLabel("Название института");
                universityName.setFont(new Font(universityName.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, universityName, 20, SpringLayout.SOUTH, degreeChoose);
                layout.putConstraint(SpringLayout.WEST, universityName, 20, SpringLayout.WEST, jPanel);
                jPanel.add(universityName);

                String sql1 = "select distinct name_university from Researchers";
                queryController.performSQLQuery(sql1);
                ArrayList<String> universityNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    universityNames.add(queryController.getCurrResultSet().getString("name_university"));
                }
                universityNames.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> universityNameChoose = new JComboBox(universityNames.toArray());
                universityNameChoose.setFont(new Font(universityNameChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, universityNameChoose, 10, SpringLayout.SOUTH, universityName);
                layout.putConstraint(SpringLayout.WEST, universityNameChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(universityNameChoose);

                JButton find = new JButton("Найти");
                find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
                layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
                find.addActionListener(e->{
                    String sqlQuery = "";
                    if(!universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            !degreeChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-читателя\", degree as \"Научная степень\", name_university" +
                                " as \"Институт\" from Researchers " +
                                "where degree = '" + degreeChoose.getSelectedItem().toString() + "' and" +
                                " name_university = '" + universityNameChoose.getSelectedItem().toString() + "'";
                    }
                    else if(!universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            degreeChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-читателя\", degree as \"Научная степень\", name_university" +
                                " as \"Институт\" from Researchers " +
                                "where" +
                                " name_university = '" + universityNameChoose.getSelectedItem().toString() + "'";
                    }
                    else if(universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            !degreeChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-читателя\", degree as \"Научная степень\", name_university" +
                                " as \"Институт\" from Researchers " +
                                "where degree = '" + degreeChoose.getSelectedItem().toString() + "'";
                    }
                    else if(universityNameChoose.getSelectedItem().toString().equals("Все") &&
                            degreeChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-читателя\", degree as \"Научная степень\", name_university" +
                                " as \"Институт\" from Researchers";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnsCount = resultSetMetaData.getColumnCount();

                        ArrayList<String> list = new ArrayList<>();
                        for(int i = 1; i <= columnsCount; i++){
                            list.add(resultSetMetaData.getColumnLabel(i));
                        }

                        String[] columnsHeaders = list.toArray(new String[0]);

                        ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                        queryController.closeSQLSet();
                        this.setVisible(false);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
                break;
            }
            case "работник":{
                //название фирмы
                JLabel firmName = new JLabel("Название фирмы");
                firmName.setFont(new Font(firmName.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, firmName, 10, SpringLayout.SOUTH, addList);
                layout.putConstraint(SpringLayout.WEST, firmName, 20, SpringLayout.WEST, jPanel);
                jPanel.add(firmName);

                String sql = "select distinct name_firm from Workers";
                queryController.performSQLQuery(sql);
                ArrayList<String> firmNames = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    firmNames.add(queryController.getCurrResultSet().getString("name_firm"));
                }
                firmNames.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> firmNameChoose = new JComboBox(firmNames.toArray());
                firmNameChoose.setFont(new Font(firmNameChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, firmNameChoose, 10, SpringLayout.SOUTH, firmName);
                layout.putConstraint(SpringLayout.WEST, firmNameChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(firmNameChoose);

                JButton find = new JButton("Найти");
                find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
                layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
                find.addActionListener(e->{
                    String sqlQuery = "";
                    if(!firmNameChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-читателя\", name_firm as \"Название фирмы\", " +
                                "firm_address as \"Адрес фирмы\" from Workers where name_firm = '" + firmNameChoose.getSelectedItem().toString() + "'";
                    }
                    else {
                        sqlQuery = "select id_reader as \"Id-читателя\", name_firm as \"Название фирмы\", " +
                                "firm_address as \"Адрес фирмы\" from Workers";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnsCount = resultSetMetaData.getColumnCount();

                        ArrayList<String> list = new ArrayList<>();
                        for(int i = 1; i <= columnsCount; i++){
                            list.add(resultSetMetaData.getColumnLabel(i));
                        }

                        String[] columnsHeaders = list.toArray(new String[0]);

                        ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                        queryController.closeSQLSet();
                        this.setVisible(false);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
                break;
            }
            case "пенсионер":{
                //пенсионное свидетельство
                JLabel pensionerIdLabel = new JLabel("Номер пенсионного свидетельства");
                pensionerIdLabel.setFont(new Font(pensionerIdLabel.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.NORTH, pensionerIdLabel, 10, SpringLayout.SOUTH, addList);
                layout.putConstraint(SpringLayout.WEST, pensionerIdLabel, 20, SpringLayout.WEST, jPanel);
                jPanel.add(pensionerIdLabel);

                String sql = "select id_pensioner from Pensioners";
                queryController.performSQLQuery(sql);
                ArrayList<String> idPensioners = new ArrayList<>();
                while (queryController.getCurrResultSet().next()){
                    idPensioners.add(queryController.getCurrResultSet().getString("id_pensioner"));
                }
                idPensioners.add("Все");
                queryController.closeSQLSet();

                JComboBox<String> pensionerChoose = new JComboBox(idPensioners.toArray());
                pensionerChoose.setFont(new Font(pensionerChoose.getFont().getName(), Font.PLAIN, 16));
                layout.putConstraint(SpringLayout.NORTH, pensionerChoose, 10, SpringLayout.SOUTH, pensionerIdLabel);
                layout.putConstraint(SpringLayout.WEST, pensionerChoose, 20, SpringLayout.WEST, jPanel);
                jPanel.add(pensionerChoose);

                JButton find = new JButton("Найти");
                find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
                layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
                layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
                find.addActionListener(e->{
                    String sqlQuery = "";
                    if(!pensionerChoose.getSelectedItem().toString().equals("Все")){
                        sqlQuery = "select id_reader as \"Id-читатель\", id_pensioner as \"№ пенсионноого свидетельства\" " +
                                " from Pensioners where id_pensioner = " + pensionerChoose.getSelectedItem().toString();
                    }
                    else {
                        sqlQuery = "select id_reader as \"Id-читатель\", id_pensioner as \"№ пенсионноого свидетельства\" " +
                                " from Pensioners";
                    }

                    try {
                        queryController.performSQLQuery(sqlQuery);
                        ResultSet resultSet = queryController.getCurrResultSet();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnsCount = resultSetMetaData.getColumnCount();

                        ArrayList<String> list = new ArrayList<>();
                        for(int i = 1; i <= columnsCount; i++){
                            list.add(resultSetMetaData.getColumnLabel(i));
                        }

                        String[] columnsHeaders = list.toArray(new String[0]);

                        ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                        queryController.closeSQLSet();
                        this.setVisible(false);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
                jPanel.add(find);
                break;
            }
        }

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);

    }

}

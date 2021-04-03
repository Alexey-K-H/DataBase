package gui.tablesView.modifyViews.categoryModify;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class SchoolChildModify extends CategoryModify {

    public SchoolChildModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        super(tableController, currValues, tableModel, indexRow);
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 500, 500);
        this.setTitle("Изменение данных школьника");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные школьника");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idSchool = new JLabel("ID-школы");
        idSchool.setFont(new Font(idSchool.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idSchool, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idSchool, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idSchool);

        JTextField idSchoolValue = new JTextField(15);
        idSchoolValue.setFont(new Font(idSchoolValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idSchoolValue, 20, SpringLayout.SOUTH, idSchool);
        layout.putConstraint(SpringLayout.WEST, idSchoolValue, 20, SpringLayout.WEST, jPanel);
        idSchoolValue.setText(getCurrValues().get(1));
        jPanel.add(idSchoolValue);

        JLabel grade = new JLabel("Класс");
        grade.setFont(new Font(grade.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, grade, 20, SpringLayout.SOUTH, idSchoolValue);
        layout.putConstraint(SpringLayout.WEST, grade, 20, SpringLayout.WEST, jPanel);
        jPanel.add(grade);

        JTextField gradeValue = new JTextField(5);
        gradeValue.setFont(new Font(gradeValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, gradeValue, 20, SpringLayout.SOUTH, grade);
        layout.putConstraint(SpringLayout.WEST, gradeValue, 20, SpringLayout.WEST, jPanel);
        gradeValue.setText(getCurrValues().get(2));
        jPanel.add(gradeValue);


        JLabel schoolName = new JLabel("Название школы");
        schoolName.setFont(new Font(schoolName.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, schoolName, 20, SpringLayout.SOUTH, gradeValue);
        layout.putConstraint(SpringLayout.WEST, schoolName, 20, SpringLayout.WEST, jPanel);
        jPanel.add(schoolName);

        JTextField schoolNameValue = new JTextField(15);
        schoolNameValue.setFont(new Font(schoolNameValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, schoolNameValue, 20, SpringLayout.SOUTH, schoolName);
        layout.putConstraint(SpringLayout.WEST, schoolNameValue, 20, SpringLayout.WEST, jPanel);
        schoolNameValue.setText(getCurrValues().get(3));
        jPanel.add(schoolNameValue);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idSchoolValue.getText().isEmpty()
                    || gradeValue.getText().isEmpty()
                    || schoolNameValue.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idSchoolValue.getText());
                newValues.add(gradeValue.getText());
                newValues.add(schoolNameValue.getText());

                try {
                    String sqlValuesSet = "set id_school = " + newValues.get(0) +  ", grade = " + newValues.get(1)
                            + ", name_school = '" + newValues.get(2) + "' where id_reader = " + getCurrValues().get(0);
                    performUpdateOperation(sqlValuesSet);
                    getTableModel().setValueAt(newValues.get(0), getIndexRow(), 1);
                    getTableModel().setValueAt(newValues.get(1), getIndexRow(), 2);
                    getTableModel().setValueAt(newValues.get(2), getIndexRow(), 3);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    getTableController().getConnection().getConn().createStatement().executeUpdate("COMMIT ");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    if (exception.getErrorCode() == 2290) {
                        error.setText("Ошибка изменения записи! Невреный ID школы или номер класса!");
                    } else {
                        error.setText(exception.getMessage());
                    }
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                    try {
                        getTableController().getConnection().getConn().createStatement().executeUpdate("ROLLBACK ");
                    } catch (SQLException sqlException) {
                        error.setText(sqlException.getMessage());
                        JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        jPanel.add(confirmUpdates);


        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }

}

package gui.tablesView.modifyViews.categoryModify;

import controllers.TableController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherModify extends CategoryModify {

    public TeacherModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        super(tableController, currValues, tableModel, indexRow);
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 500, 500);
        this.setTitle("Изменение данных учителя");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные учителя");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        System.out.println(getCurrValues());

        JLabel idUniversity = new JLabel("ID-вуза");
        idUniversity.setFont(new Font(idUniversity.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversity, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idUniversity, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idUniversity);

        JTextField idUnivValue = new JTextField(15);
        idUnivValue.setFont(new Font(idUnivValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idUnivValue, 20, SpringLayout.SOUTH, idUniversity);
        layout.putConstraint(SpringLayout.WEST, idUnivValue, 20, SpringLayout.WEST, jPanel);
        idUnivValue.setText(getCurrValues().get(1));
        jPanel.add(idUnivValue);

        JLabel facultyName = new JLabel("Название факультета");
        facultyName.setFont(new Font(facultyName.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, facultyName, 20, SpringLayout.SOUTH, idUnivValue);
        layout.putConstraint(SpringLayout.WEST, facultyName, 20, SpringLayout.WEST, jPanel);
        jPanel.add(facultyName);

        JTextField facultyValue = new JTextField(15);
        facultyValue.setFont(new Font(facultyValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, facultyValue, 20, SpringLayout.SOUTH, facultyName);
        layout.putConstraint(SpringLayout.WEST, facultyValue, 20, SpringLayout.WEST, jPanel);
        facultyValue.setText(getCurrValues().get(2));
        jPanel.add(facultyValue);


        JLabel universityName = new JLabel("Название вуза");
        universityName.setFont(new Font(universityName.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, universityName, 20, SpringLayout.SOUTH, facultyValue);
        layout.putConstraint(SpringLayout.WEST, universityName, 20, SpringLayout.WEST, jPanel);
        jPanel.add(universityName);

        JTextField universityValue = new JTextField(15);
        universityValue.setFont(new Font(universityValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, universityValue, 20, SpringLayout.SOUTH, universityName);
        layout.putConstraint(SpringLayout.WEST, universityValue, 20, SpringLayout.WEST, jPanel);
        universityValue.setText(getCurrValues().get(3));
        jPanel.add(universityValue);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idUnivValue.getText().isEmpty()
                    || facultyValue.getText().isEmpty()
                    || universityValue.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idUnivValue.getText());
                newValues.add(facultyValue.getText());
                newValues.add(universityValue.getText());

                try {
                    String sqlValuesSet = "set id_university = " + newValues.get(0) +  ", faculty = '" + newValues.get(1)
                            + "', name_university = '" + newValues.get(2) + "' where id_reader = " + getCurrValues().get(0);
                    performUpdateOperation(sqlValuesSet);
                    getTableModel().setValueAt(newValues.get(0), getIndexRow(), 1);
                    getTableModel().setValueAt(newValues.get(1), getIndexRow(), 2);
                    getTableModel().setValueAt(newValues.get(2), getIndexRow(), 3);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    if (exception.getErrorCode() == 2290) {
                        error.setText("Ошибка изменения записи! Идентификатор вуза положительное число!");
                    } else {
                        error.setText(exception.getMessage());
                    }
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jPanel.add(confirmUpdates);


        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

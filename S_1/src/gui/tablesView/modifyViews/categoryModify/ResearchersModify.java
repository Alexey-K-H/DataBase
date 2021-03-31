package gui.tablesView.modifyViews.categoryModify;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResearchersModify extends CategoryModify {

    public ResearchersModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        super(tableController, currValues, tableModel, indexRow);
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 500, 500);
        this.setTitle("Изменение данных научного сотрудника");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные научного сотрудника");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idUniversity = new JLabel("ID-института");
        idUniversity.setFont(new Font(idUniversity.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversity, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idUniversity, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idUniversity);

        JTextField idUniversityValue = new JTextField(15);
        idUniversityValue.setFont(new Font(idUniversityValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversityValue, 20, SpringLayout.SOUTH, idUniversity);
        layout.putConstraint(SpringLayout.WEST, idUniversityValue, 20, SpringLayout.WEST, jPanel);
        idUniversityValue.setText(getCurrValues().get(1));
        jPanel.add(idUniversityValue);

        JLabel universityAddress = new JLabel("Адрес института");
        universityAddress.setFont(new Font(universityAddress.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, universityAddress, 20, SpringLayout.SOUTH, idUniversityValue);
        layout.putConstraint(SpringLayout.WEST, universityAddress, 20, SpringLayout.WEST, jPanel);
        jPanel.add(universityAddress);

        JTextField universityAddressValue = new JTextField(25);
        universityAddressValue.setFont(new Font(universityAddressValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, universityAddressValue, 20, SpringLayout.SOUTH, universityAddress);
        layout.putConstraint(SpringLayout.WEST, universityAddressValue, 20, SpringLayout.WEST, jPanel);
        universityAddressValue.setText(getCurrValues().get(2));
        jPanel.add(universityAddressValue);


        JLabel degree = new JLabel("Научная степень");
        degree.setFont(new Font(degree.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, degree, 20, SpringLayout.SOUTH, universityAddressValue);
        layout.putConstraint(SpringLayout.WEST, degree, 20, SpringLayout.WEST, jPanel);
        jPanel.add(degree);

        JTextField degreeValue = new JTextField(25);
        degreeValue.setFont(new Font(degreeValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, degreeValue, 20, SpringLayout.SOUTH, degree);
        layout.putConstraint(SpringLayout.WEST, degreeValue, 20, SpringLayout.WEST, jPanel);
        degreeValue.setText(getCurrValues().get(3));
        jPanel.add(degreeValue);


        JLabel universityName = new JLabel("Название института");
        universityName.setFont(new Font(universityName.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, universityName, 20, SpringLayout.SOUTH, degreeValue);
        layout.putConstraint(SpringLayout.WEST, universityName, 20, SpringLayout.WEST, jPanel);
        jPanel.add(universityName);

        JTextField universityNameValue = new JTextField(15);
        universityNameValue.setFont(new Font(universityNameValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, universityNameValue, 20, SpringLayout.SOUTH, universityName);
        layout.putConstraint(SpringLayout.WEST, universityNameValue, 20, SpringLayout.WEST, jPanel);
        universityNameValue.setText(getCurrValues().get(4));
        jPanel.add(universityNameValue);


        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idUniversityValue.getText().isEmpty()
                    || universityAddressValue.getText().isEmpty()
                    || degreeValue.getText().isEmpty()
                    || universityNameValue.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idUniversityValue.getText());
                newValues.add(universityAddressValue.getText());
                newValues.add(degreeValue.getText());
                newValues.add(universityNameValue.getText());

                try {
                    String sqlValuesSet = "set id_university = " + newValues.get(0) +  ", address_university = '" + newValues.get(1)
                            + "', degree = '" + newValues.get(2) + "', name_university = '" + newValues.get(3) + "' where id_reader = " + getCurrValues().get(0);
                    performUpdateOperation(sqlValuesSet);
                    getTableModel().setValueAt(newValues.get(0), getIndexRow(), 1);
                    getTableModel().setValueAt(newValues.get(1), getIndexRow(), 2);
                    getTableModel().setValueAt(newValues.get(2), getIndexRow(), 3);
                    getTableModel().setValueAt(newValues.get(3), getIndexRow(), 4);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    getTableController().getConnection().getConn().createStatement().executeUpdate("COMMIT ");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    if (exception.getErrorCode() == 2290) {
                        error.setText("Ошибка изменения записи! Невреный ID института!");
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

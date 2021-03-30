package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibrarianModify extends JDialog implements ModifyView {
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public LibrarianModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        this.tableController = tableController;
        this.currValues = currValues;
        this.tableModel = tableModel;
        this.indexRow = indexRow;
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 125, 500, 250);
        this.setTitle("Изменение данных библиотекаря");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные библиотекаря");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idLibraryLabel = new JLabel("Библиотека");
        idLibraryLabel.setFont(new Font(idLibraryLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibraryLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLibraryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibraryLabel);

        JTextField idLibraryField = new JTextField(10);
        idLibraryField.setText(currValues.get(1));
        idLibraryField.setFont(new Font(idLibraryField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibraryField, 10, SpringLayout.SOUTH, idLibraryLabel);
        layout.putConstraint(SpringLayout.WEST, idLibraryField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibraryField);

        JLabel hallLabel = new JLabel("Номер зала");
        hallLabel.setFont(new Font(hallLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, hallLabel, 20, SpringLayout.SOUTH, idLibraryField);
        layout.putConstraint(SpringLayout.WEST, hallLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallLabel);

        JTextField hallField = new JTextField(10);
        hallField.setText(currValues.get(2));
        hallField.setFont(new Font(hallField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, hallField, 10, SpringLayout.SOUTH, hallLabel);
        layout.putConstraint(SpringLayout.WEST, hallField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallField);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, confirmUpdates, 30, SpringLayout.SOUTH, idLibraryField);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idLibraryField.getText().isEmpty() || hallField.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idLibraryField.getText());
                newValues.add(hallField.getText());

                try {
                    String sqlValuesSet = "set id_library = " + newValues.get(0) +  ", hall_num = " + newValues.get(1) + " where id_librarian = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(newValues.get(0), indexRow, 1);
                    tableModel.setValueAt(newValues.get(1), indexRow, 2);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    switch (exception.getErrorCode()){
                        case 2290:{
                            error.setText("Ошибка добавления записи! Номер зала есть положительное число!");
                            break;
                        }
                        case 2291:{
                            error.setText("Ошибка добваления записи! Нет библиотеки с таким ID!");
                            break;
                        }
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

    @Override
    public void performUpdateOperation(String sqlValuesSet) throws SQLException {
        tableController.modifyRow(sqlValuesSet);
    }
}

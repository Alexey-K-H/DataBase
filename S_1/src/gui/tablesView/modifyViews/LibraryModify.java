package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryModify extends JDialog implements ModifyView {
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public LibraryModify(TableController tableController, ArrayList<String> oldValues, DefaultTableModel tableModel, int index) {
        this.tableController = tableController;
        this.currValues = oldValues;
        this.tableModel = tableModel;
        this.indexRow = index;
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 125, 500, 250);
        this.setTitle("Изменение данных библиотеки");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные библиотеки");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel quantityBooksLabel = new JLabel("Количество книг в фонде бибилиотеки:");
        quantityBooksLabel.setFont(new Font(quantityBooksLabel.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, quantityBooksLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, quantityBooksLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(quantityBooksLabel);

        JTextField quantityBooksTextField = new JTextField(10);
        quantityBooksTextField.setText(currValues.get(1));
        quantityBooksTextField.setFont(new Font(quantityBooksTextField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, quantityBooksTextField, 10, SpringLayout.SOUTH, quantityBooksLabel);
        layout.putConstraint(SpringLayout.WEST, quantityBooksTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(quantityBooksTextField);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, confirmUpdates, 30, SpringLayout.SOUTH, quantityBooksTextField);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(quantityBooksTextField.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Не заполнено поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(quantityBooksTextField.getText());
                try {
                    String sqlValuesSet = "set quantity_books = " + newValues.get(0) + " where id_library = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(newValues.get(0), indexRow, 1);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    tableController.getConnection().getConn().createStatement().executeUpdate("COMMIT ");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel("Ошибка изменения записи! Количество книг в библиотеке не может быть отрицательным!");
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                    try {
                        tableController.getConnection().getConn().createStatement().executeUpdate("ROLLBACK ");
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

    @Override
    public void performUpdateOperation(String sqlValuesSet) throws SQLException{
        tableController.modifyRow(sqlValuesSet);
    }
}

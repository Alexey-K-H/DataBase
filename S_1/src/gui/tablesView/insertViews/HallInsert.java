package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HallInsert extends JDialog implements InsertFrame{
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public HallInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 200, 500, 400);
        this.setTitle("Добавление информации о залах библиотеки");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные для добавления нового зала");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idLabel = new JLabel("Номер зала:");
        idLabel.setFont(new Font(idLabel.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLabel, 50, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLabel);

        JTextField idHallTexField = new JTextField(10);
        idHallTexField.setFont(new Font(idHallTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idHallTexField, 10, SpringLayout.SOUTH, idLabel);
        layout.putConstraint(SpringLayout.WEST, idHallTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idHallTexField);

        JLabel idLibLabel = new JLabel("Идентификатор бибилиотеки");
        idLibLabel.setFont(new Font(idLibLabel.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibLabel, 20, SpringLayout.SOUTH, idHallTexField);
        layout.putConstraint(SpringLayout.WEST, idLibLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibLabel);

        JTextField idLibTextField = new JTextField(10);
        idLibTextField.setFont(new Font(idLibTextField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibTextField, 10, SpringLayout.SOUTH, idLibLabel);
        layout.putConstraint(SpringLayout.WEST, idLibTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibTextField);

        JButton confirm = new JButton("Добавить зал");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idHallTexField.getText());
            currValues.add(idLibTextField.getText());
            String sql = "insert into HALLS values (" + currValues.get(0) + "," + currValues.get(1) + ")";
            try {
                performInsertOperation(sql);
                idHallTexField.setText("");
                idLibTextField.setText("");

                Object[] values = new Object[]{tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 0), tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 1)};
                tableModel.addRow(values);
                JLabel success = new JLabel("Запись добавлена успешно!");
                tableController.getConnection().getConn().createStatement().executeUpdate("COMMIT ");
                success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, success, "INSERT", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException exception) {
                JLabel error = new JLabel();
                switch (exception.getErrorCode()){
                    case 936:{
                        error.setText("Ошибка добавленя записи! Незаполненные поля!");
                        break;
                    }
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
                try {
                    tableController.getConnection().getConn().createStatement().executeUpdate("ROLLBACK ");
                } catch (SQLException sqlException) {
                    error.setText(sqlException.getMessage());
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jPanel.add(confirm);

        JButton cleanValues = new JButton("Очистить поля");
        cleanValues.setFont(new Font(cleanValues.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, cleanValues, 50, SpringLayout.SOUTH, idLibTextField);
        layout.putConstraint(SpringLayout.WEST, cleanValues, 20, SpringLayout.WEST, jPanel);
        cleanValues.addActionListener(e -> {
            idHallTexField.setText("");
            idLibTextField.setText("");
        });
        jPanel.add(cleanValues);

        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void performInsertOperation(String sql) throws SQLException {
        tableController.insertNewRecord(sql);
    }
}

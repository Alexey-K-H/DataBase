package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibrarianInsert extends JDialog implements InsertFrame {
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public LibrarianInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 200, 500, 400);
        this.setTitle("Добавление нового сотрудника бибилиотеки");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные для регистрации библиотекаря");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

//        JLabel idLabel = new JLabel("Идентификатор сотрудника");
//        idLabel.setFont(new Font(idLabel.getFont().getName(), Font.BOLD, 16));
//        layout.putConstraint(SpringLayout.NORTH, idLabel, 50, SpringLayout.SOUTH, info);
//        layout.putConstraint(SpringLayout.WEST, idLabel, 20, SpringLayout.WEST, jPanel);
//        jPanel.add(idLabel);
//
//        JTextField idTextField = new JTextField(10);
//        idTextField.setFont(new Font(idTextField.getFont().getName(), Font.PLAIN, 16));
//        layout.putConstraint(SpringLayout.NORTH, idTextField, 10, SpringLayout.SOUTH, idLabel);
//        layout.putConstraint(SpringLayout.WEST, idTextField, 20, SpringLayout.WEST, jPanel);
//        jPanel.add(idTextField);

        JLabel idLibLabel = new JLabel("Идентификатор библиотеки");
        idLibLabel.setFont(new Font(idLibLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLibLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibLabel);

        JTextField idLibTexField = new JTextField(10);
        idLibTexField.setFont(new Font(idLibTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibTexField, 10, SpringLayout.SOUTH, idLibLabel);
        layout.putConstraint(SpringLayout.WEST, idLibTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibTexField);

        JLabel hallNumLabel = new JLabel("Номер зала сотрудника");
        hallNumLabel.setFont(new Font(hallNumLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, hallNumLabel, 20, SpringLayout.SOUTH, idLibTexField);
        layout.putConstraint(SpringLayout.WEST, hallNumLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallNumLabel);

        JTextField hallNumTexFiled = new JTextField(10);
        hallNumTexFiled.setFont(new Font(hallNumTexFiled.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, hallNumTexFiled, 10, SpringLayout.SOUTH, hallNumLabel);
        layout.putConstraint(SpringLayout.WEST, hallNumTexFiled, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallNumTexFiled);

        JButton confirm = new JButton("Добавить сотрудника");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            //currValues.add(idTextField.getText());
            currValues.add(idLibTexField.getText());
            currValues.add(hallNumTexFiled.getText());
            String sql = "insert into LIBRARIANS(ID_LIBRARY, HALL_NUM) values (" + currValues.get(0) + "," + currValues.get(1) + ")";
            try {
                performInsertOperation(sql);
                //idTextField.setText("");
                idLibTexField.setText("");
                hallNumTexFiled.setText("");

                Object[] values = new Object[]{tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 0), tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 1), tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 2)};
                tableModel.addRow(values);
                JLabel success = new JLabel("Запись добавлена успешно!");
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
            }
        });
        jPanel.add(confirm);

        JButton clear = new JButton("Очистить поля");
        clear.setFont(new Font(clear.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, clear, -20, SpringLayout.NORTH, confirm);
        layout.putConstraint(SpringLayout.EAST, clear, -20, SpringLayout.EAST, jPanel);
        clear.addActionListener(e -> {
            //idTextField.setText("");
            idLibTexField.setText("");
            hallNumTexFiled.setText("");
        });
        jPanel.add(clear);


        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void performInsertOperation(String sql) throws SQLException {
        tableController.insertNewRecord(sql);
    }
}

package gui.tablesView.insertViews.categoryInserts;

import controllers.TableController;
import gui.tablesView.insertViews.InsertFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentsInsert extends JDialog implements InsertFrame {
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public StudentsInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 200, 500, 400);
        this.setTitle("Добавление нового студента");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите дополнительные данные студента");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idUniversityLabel = new JLabel("Идентификатор вуза");
        idUniversityLabel.setFont(new Font(idUniversityLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversityLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idUniversityLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idUniversityLabel);

        JTextField idUniversityField = new JTextField(15);
        idUniversityField.setFont(new Font(idUniversityField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversityField, 10, SpringLayout.SOUTH, idUniversityLabel);
        layout.putConstraint(SpringLayout.WEST, idUniversityField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idUniversityField);

        JLabel facultyNameLabel = new JLabel("Название факультета");
        facultyNameLabel.setFont(new Font(facultyNameLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, facultyNameLabel, 20, SpringLayout.SOUTH, idUniversityField);
        layout.putConstraint(SpringLayout.WEST, facultyNameLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(facultyNameLabel);

        JTextField facultyNameField = new JTextField(15);
        facultyNameField.setFont(new Font(facultyNameField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, facultyNameField, 10, SpringLayout.SOUTH, facultyNameLabel);
        layout.putConstraint(SpringLayout.WEST, facultyNameField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(facultyNameField);

        JLabel universityNameLabel = new JLabel("Название вуза");
        universityNameLabel.setFont(new Font(universityNameLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, universityNameLabel, 20, SpringLayout.SOUTH, facultyNameField);
        layout.putConstraint(SpringLayout.WEST, universityNameLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(universityNameLabel);

        JTextField universityNameField = new JTextField(15);
        universityNameField.setFont(new Font(universityNameField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, universityNameField, 10, SpringLayout.SOUTH, universityNameLabel);
        layout.putConstraint(SpringLayout.WEST, universityNameField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(universityNameField);

        JButton confirm = new JButton("Добавить студента");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idUniversityField.getText());
            currValues.add("'" + facultyNameField.getText() + "'");
            currValues.add("'" + universityNameField.getText() + "'");

            try {
                String sql = "insert into Students values (" + tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 0) + "," + currValues.get(0) + ","
                        + currValues.get(1) + "," + currValues.get(2) +")";

                performInsertOperation(sql);

                Object[] values = new Object[]{
                        tableController.getTableSet().getValueAt(
                                tableController.getTableSet().getRowCount() - 1, 0),
                        tableController.getTableSet().getValueAt(
                                tableController.getTableSet().getRowCount() - 1, 1),
                        tableController.getTableSet().getValueAt(
                                tableController.getTableSet().getRowCount() - 1, 2),
                        tableController.getTableSet().getValueAt(
                                tableController.getTableSet().getRowCount() - 1, 3)
                };

                tableModel.addRow(values);
                this.setVisible(false);
                JLabel success = new JLabel("Запись добавлена успешно!");
                tableController.getConnection().getConn().createStatement().executeUpdate("COMMIT ");
                success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, success, "INSERT", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException exception) {
                JLabel error = new JLabel();
                switch (exception.getErrorCode()) {
                    case 1400:{
                        error.setText("Ошибка добавления записи! Незаполненнные поля!");
                        break;
                    }
                    case 936: {
                        error.setText("Ошибка добавленя записи! Незаполненные поля!");
                        break;
                    }
                    default: {
                        error.setText(exception.getMessage());
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

        JButton clear = new JButton("Очистить поля");
        clear.setFont(new Font(clear.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, clear, -20, SpringLayout.NORTH, confirm);
        layout.putConstraint(SpringLayout.EAST, clear, -20, SpringLayout.EAST, jPanel);
        clear.addActionListener(e -> {
            idUniversityField.setText("");
            facultyNameField.setText("");
            universityNameField.setText("");
        });
        jPanel.add(clear);

        JButton cancel = new JButton("Отменить создание записи");
        cancel.setFont(new Font(cancel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, cancel, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, cancel, 20, SpringLayout.WEST, jPanel);
        cancel.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Отмена повлечет удаление читателя из основной таблицы! Вы уверены?", "Отмена",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION){
                try {
                    tableController.deleteRecord(tableController.getTableSet().getValueAt(
                            tableController.getTableSet().getRowCount() - 1, 0));

                    this.setVisible(false);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    error.setText(exception.getMessage());
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                //Do nothing
            }
        });
        jPanel.add(cancel);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);

    }

    @Override
    public void performInsertOperation(String sql) throws SQLException {
        tableController.insertNewRecord(sql);
    }
}

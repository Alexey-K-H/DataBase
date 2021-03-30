package gui.tablesView.insertViews.categoryInserts;

import controllers.TableController;
import gui.tablesView.insertViews.InsertFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class SchoolChildInsert extends JDialog implements InsertFrame {
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public SchoolChildInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 200, 500, 400);
        this.setTitle("Добавление нового школьника");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите дополнительные данные школьника");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idSchoolLabel = new JLabel("Идентификатор школы");
        idSchoolLabel.setFont(new Font(idSchoolLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idSchoolLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idSchoolLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idSchoolLabel);

        JTextField idSchoolField = new JTextField(15);
        idSchoolField.setFont(new Font(idSchoolField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idSchoolField, 10, SpringLayout.SOUTH, idSchoolLabel);
        layout.putConstraint(SpringLayout.WEST, idSchoolField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idSchoolField);

        JLabel gradeLabel = new JLabel("Класс№:");
        gradeLabel.setFont(new Font(gradeLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, gradeLabel, 20, SpringLayout.SOUTH, idSchoolField);
        layout.putConstraint(SpringLayout.WEST, gradeLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(gradeLabel);

        JTextField gradeField = new JTextField(15);
        gradeField.setFont(new Font(gradeField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, gradeField, 10, SpringLayout.SOUTH, gradeLabel);
        layout.putConstraint(SpringLayout.WEST, gradeField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(gradeField);

        JLabel schoolNameLabel = new JLabel("Название школы");
        schoolNameLabel.setFont(new Font(schoolNameLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, schoolNameLabel, 20, SpringLayout.SOUTH, gradeField);
        layout.putConstraint(SpringLayout.WEST, schoolNameLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(schoolNameLabel);

        JTextField schoolNameField = new JTextField(15);
        schoolNameField.setFont(new Font(schoolNameField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, schoolNameField, 10, SpringLayout.SOUTH, schoolNameLabel);
        layout.putConstraint(SpringLayout.WEST, schoolNameField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(schoolNameField);


        JButton confirm = new JButton("Добавить школьника");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -10, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idSchoolField.getText());
            currValues.add(gradeField.getText());
            currValues.add("'" + schoolNameField.getText() + "'");

            try {
                String sql = "insert into SchoolChild values (" + tableController.getTableSet().getValueAt(
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
                    case 2290:{
                        error.setText("Неверно указан номер класса ученика!");
                        break;
                    }
                    default: {
                        error.setText(exception.getMessage());
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
            idSchoolField.setText("");
            gradeField.setText("");
            schoolNameField.setText("");
        });
        jPanel.add(clear);


        JButton cancel = new JButton("Отменить создание записи");
        cancel.setFont(new Font(cancel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, cancel, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, cancel, 10, SpringLayout.WEST, jPanel);
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

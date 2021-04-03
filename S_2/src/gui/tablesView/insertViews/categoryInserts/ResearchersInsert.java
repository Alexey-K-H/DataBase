package gui.tablesView.insertViews.categoryInserts;

import controllers.TableController;
import gui.tablesView.insertViews.InsertFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResearchersInsert extends JDialog implements InsertFrame {
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public ResearchersInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 300, 500, 600);
        this.setTitle("Добавление нового научного сотрудника");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите дополнительные данные научного сотрудника");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idUniversityLabel = new JLabel("Идентификатор института");
        idUniversityLabel.setFont(new Font(idUniversityLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversityLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idUniversityLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idUniversityLabel);

        JTextField idUniversityField = new JTextField(15);
        idUniversityField.setFont(new Font(idUniversityField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idUniversityField, 10, SpringLayout.SOUTH, idUniversityLabel);
        layout.putConstraint(SpringLayout.WEST, idUniversityField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idUniversityField);

        JLabel addressUniversityLabel = new JLabel("Адрес института");
        addressUniversityLabel.setFont(new Font(addressUniversityLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, addressUniversityLabel, 20, SpringLayout.SOUTH, idUniversityField);
        layout.putConstraint(SpringLayout.WEST, addressUniversityLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(addressUniversityLabel);

        JTextField addressUniversityField = new JTextField(15);
        addressUniversityField.setFont(new Font(addressUniversityField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, addressUniversityField, 10, SpringLayout.SOUTH, addressUniversityLabel);
        layout.putConstraint(SpringLayout.WEST, addressUniversityField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(addressUniversityField);

        JLabel degreeLabel = new JLabel("Научная степень");
        degreeLabel.setFont(new Font(degreeLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, degreeLabel, 20, SpringLayout.SOUTH, addressUniversityField);
        layout.putConstraint(SpringLayout.WEST, degreeLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(degreeLabel);

        JTextField degreeField = new JTextField(15);
        degreeField.setFont(new Font(degreeField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, degreeField, 10, SpringLayout.SOUTH, degreeLabel);
        layout.putConstraint(SpringLayout.WEST, degreeField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(degreeField);

        JLabel nameUniversity = new JLabel("Название института");
        nameUniversity.setFont(new Font(nameUniversity.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, nameUniversity, 20, SpringLayout.SOUTH, degreeField);
        layout.putConstraint(SpringLayout.WEST, nameUniversity, 20, SpringLayout.WEST, jPanel);
        jPanel.add(nameUniversity);

        JTextField nameUniversityField = new JTextField(15);
        nameUniversityField.setFont(new Font(nameUniversityField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, nameUniversityField, 10, SpringLayout.SOUTH, nameUniversity);
        layout.putConstraint(SpringLayout.WEST, nameUniversityField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(nameUniversityField);

        JButton confirm = new JButton("Добавить научного сотрудника");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -10, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idUniversityField.getText());
            currValues.add("'" + addressUniversityField.getText() + "'");
            currValues.add("'" + degreeField.getText() + "'");
            currValues.add("'" + nameUniversityField.getText() + "'");

            try {
                String sql = "insert into Researchers values (" + tableController.getTableSet().getValueAt(
                        tableController.getTableSet().getRowCount() - 1, 0) + "," + currValues.get(0) + ","
                        + currValues.get(1) + "," + currValues.get(2) + "," + currValues.get(3) + ")";

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
            addressUniversityField.setText("");
            degreeField.setText("");
            nameUniversityField.setText("");
        });
        jPanel.add(clear);

        JButton cancel = new JButton("Отменить создание записи");
        cancel.setFont(new Font(cancel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, cancel, 30, SpringLayout.SOUTH, nameUniversityField);
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

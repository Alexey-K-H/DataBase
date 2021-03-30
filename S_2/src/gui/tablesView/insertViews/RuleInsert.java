package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class RuleInsert extends JDialog implements InsertFrame{
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public RuleInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Задать новое правило пользования");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Выберите издание и укажите правило пользования");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idEditionLabel = new JLabel("Идентификатор библиотеки");
        idEditionLabel.setFont(new Font(idEditionLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idEditionLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idEditionLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEditionLabel);

        JTextField idEditionField = new JTextField(10);
        idEditionField.setFont(new Font(idEditionField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idEditionField, 10, SpringLayout.SOUTH, idEditionLabel);
        layout.putConstraint(SpringLayout.WEST, idEditionField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEditionField);

        JLabel textRuleLabel = new JLabel("Текст правила");
        textRuleLabel.setFont(new Font(textRuleLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, textRuleLabel, 20, SpringLayout.SOUTH, idEditionField);
        layout.putConstraint(SpringLayout.WEST, textRuleLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(textRuleLabel);

        JTextArea textRuleField = new JTextArea(10,20);
        textRuleField.setLineWrap(true);
        textRuleField.setWrapStyleWord(true);
        textRuleField.setFont(new Font(textRuleField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, textRuleField, 10, SpringLayout.SOUTH, textRuleLabel);
        layout.putConstraint(SpringLayout.WEST, textRuleField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(textRuleField);

        JButton confirm = new JButton("Сохранить новое правило");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idEditionField.getText());
            currValues.add(textRuleField.getText());

            String sql = "insert into RULES(ID_EDITION, RULE_TEXT) values (" + currValues.get(0) + ",'" + currValues.get(1) + "')";
            try {
                performInsertOperation(sql);
                idEditionField.setText("");
                textRuleField.setText("");

                Object[] values = new Object[]{
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 0),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 1),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 2)
                };
                tableModel.addRow(values);
                JLabel success = new JLabel("Запись добавлена успешно!");
                success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, success, "INSERT", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException exception) {
                JLabel error = new JLabel();
                System.out.println(exception.getErrorCode());
                switch (exception.getErrorCode()){
                    case 936:{
                        error.setText("Ошибка добавленя записи! Незаполненные поля!");
                        break;
                    }
                    case 2291:{
                        error.setText("Ошибка добваления записи! Нверный Id издания");
                        break;
                    }
                    default:{
                        error.setText(exception.getMessage());
                        break;
                    }
                }
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        jPanel.add(confirm);

        JButton cleanValues = new JButton("Очистить поля");
        cleanValues.setFont(new Font(cleanValues.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, cleanValues, 50, SpringLayout.SOUTH, textRuleField);
        layout.putConstraint(SpringLayout.WEST, cleanValues, 20, SpringLayout.WEST, jPanel);
        cleanValues.addActionListener(e -> {
            idEditionField.setText("");
            textRuleField.setText("");
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

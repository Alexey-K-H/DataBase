package gui.tablesView.modifyViews.categoryModify;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkersModify extends CategoryModify {

    public WorkersModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        super(tableController, currValues, tableModel, indexRow);
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 500, 500);
        this.setTitle("Изменение данных работника");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные работника");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel firmAddressLabel = new JLabel("Адрес фирмы/компании/чатсной организации");
        firmAddressLabel.setFont(new Font(firmAddressLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, firmAddressLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, firmAddressLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(firmAddressLabel);

        JTextField firmAddressValue = new JTextField(15);
        firmAddressValue.setFont(new Font(firmAddressValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, firmAddressValue, 20, SpringLayout.SOUTH, firmAddressLabel);
        layout.putConstraint(SpringLayout.WEST, firmAddressValue, 20, SpringLayout.WEST, jPanel);
        firmAddressValue.setText(getCurrValues().get(1));
        jPanel.add(firmAddressValue);

        JLabel firmNameLabel = new JLabel("Название фирмы/компании/частной организации");
        firmNameLabel.setFont(new Font(firmNameLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, firmNameLabel, 20, SpringLayout.SOUTH, firmAddressValue);
        layout.putConstraint(SpringLayout.WEST, firmNameLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(firmNameLabel);

        JTextField firmNameValue = new JTextField(25);
        firmNameValue.setFont(new Font(firmNameValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, firmNameValue, 20, SpringLayout.SOUTH, firmNameLabel);
        layout.putConstraint(SpringLayout.WEST, firmNameValue, 20, SpringLayout.WEST, jPanel);
        firmNameValue.setText(getCurrValues().get(2));
        jPanel.add(firmNameValue);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(firmAddressValue.getText().isEmpty()
                    || firmNameValue.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(firmAddressValue.getText());
                newValues.add(firmNameValue.getText());

                try {
                    String sqlValuesSet = "set firm_address = '" + newValues.get(0)
                            + "', name_firm = '" + newValues.get(1) + "' where id_reader = " + getCurrValues().get(0);
                    performUpdateOperation(sqlValuesSet);
                    getTableModel().setValueAt(newValues.get(0), getIndexRow(), 1);
                    getTableModel().setValueAt(newValues.get(1), getIndexRow(), 2);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    error.setText(exception.getMessage());
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

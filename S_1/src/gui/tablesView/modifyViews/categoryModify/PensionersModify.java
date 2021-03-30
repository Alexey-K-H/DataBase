package gui.tablesView.modifyViews.categoryModify;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class PensionersModify extends CategoryModify {

    public PensionersModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        super(tableController, currValues, tableModel, indexRow);
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 500, 500);
        this.setTitle("Изменение данных пенсионера");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные пенсионера");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel pensionerLabel = new JLabel("ID-пенсионного свидетельства");
        pensionerLabel.setFont(new Font(pensionerLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, pensionerLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, pensionerLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(pensionerLabel);

        JTextField pensionerId = new JTextField(15);
        pensionerId.setFont(new Font(pensionerId.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, pensionerId, 20, SpringLayout.SOUTH, pensionerLabel);
        layout.putConstraint(SpringLayout.WEST, pensionerId, 20, SpringLayout.WEST, jPanel);
        pensionerId.setText(getCurrValues().get(1));
        jPanel.add(pensionerId);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(pensionerId.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(pensionerId.getText());

                try {
                    String sqlValuesSet = "set id_pensioner = " + newValues.get(0) +  " where id_reader = " + getCurrValues().get(0);
                    performUpdateOperation(sqlValuesSet);
                    getTableModel().setValueAt(newValues.get(0), getIndexRow(), 1);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    if (exception.getErrorCode() == 2290) {
                        error.setText("Ошибка изменения записи! Невреный ID пенсионного свидетельства!");
                    } else if(exception.getErrorCode() == 1) {
                        error.setText("Ошибка изменения записи! Уже есть человек с таким пенсионным свидетельством!");
                    }
                    else {
                        error.setText(exception.getMessage() + exception.getErrorCode());
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

}

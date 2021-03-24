package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class EditionsModify extends JDialog implements ModifyView{
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public EditionsModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
        this.tableController = tableController;
        this.currValues = currValues;
        this.tableModel = tableModel;
        this.indexRow = indexRow;
    }

    @Override
    public void openModifyWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 250, 500, 500);
        this.setTitle("Изменение данных издания");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные издания");
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

        JLabel hallNumLabel = new JLabel("Номер зала");
        hallNumLabel.setFont(new Font(hallNumLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, hallNumLabel, 20, SpringLayout.SOUTH, idLibraryField);
        layout.putConstraint(SpringLayout.WEST, hallNumLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallNumLabel);

        JTextField hallNumTexField = new JTextField(10);
        hallNumTexField.setText(currValues.get(2));
        hallNumTexField.setFont(new Font(hallNumTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, hallNumTexField, 10, SpringLayout.SOUTH, hallNumLabel);
        layout.putConstraint(SpringLayout.WEST, hallNumTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallNumTexField);

        JLabel rackNumLabel = new JLabel("Номер стеллажа");
        rackNumLabel.setFont(new Font(rackNumLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, rackNumLabel, 20, SpringLayout.SOUTH, hallNumTexField);
        layout.putConstraint(SpringLayout.WEST, rackNumLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(rackNumLabel);

        JTextField rackNumField = new JTextField(10);
        rackNumField.setText(currValues.get(3));
        rackNumField.setFont(new Font(rackNumField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, rackNumField, 10, SpringLayout.SOUTH, rackNumLabel);
        layout.putConstraint(SpringLayout.WEST, rackNumField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(rackNumField);

        JLabel shelfLabel = new JLabel("Номер полки");
        shelfLabel.setFont(new Font(shelfLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, shelfLabel, 20, SpringLayout.SOUTH, rackNumField);
        layout.putConstraint(SpringLayout.WEST, shelfLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(shelfLabel);

        JTextField shelfTexField = new JTextField(10);
        shelfTexField.setText(currValues.get(4));
        shelfTexField.setFont(new Font(shelfTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, shelfTexField, 10, SpringLayout.SOUTH, shelfLabel);
        layout.putConstraint(SpringLayout.WEST, shelfTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(shelfTexField);


        JLabel terms = new JLabel("<html>Дата поступления/Дата списания<br>В формате дд.мм.гггг</html>");
        terms.setFont(new Font(terms.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, terms, 20, SpringLayout.SOUTH, shelfTexField);
        layout.putConstraint(SpringLayout.WEST, terms, 20, SpringLayout.WEST, jPanel);
        jPanel.add(terms);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }

        JFormattedTextField admissionDate = new JFormattedTextField(maskFormatter);
        admissionDate.setColumns(7);
        String[] dateItems1 = currValues.get(5).split("-");
        admissionDate.setText(dateItems1[2] + dateItems1[1] + dateItems1[0]);
        JFormattedTextField writeOffDate = new JFormattedTextField(maskFormatter);
        writeOffDate.setColumns(7);
        String[] dateItems2 = currValues.get(6).split("-");
        writeOffDate.setText(dateItems2[2] + dateItems2[1] + dateItems2[0]);

        admissionDate.setFont(new Font(admissionDate.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, admissionDate, 10, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, admissionDate, 20, SpringLayout.WEST, jPanel);
        jPanel.add(admissionDate);

        writeOffDate.setFont(new Font(writeOffDate.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, writeOffDate, 10, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, writeOffDate, 10, SpringLayout.EAST, admissionDate);
        jPanel.add(writeOffDate);


        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idLibraryField.getText().isEmpty()
                    || hallNumLabel.getText().isEmpty()
                    || shelfTexField.getText().isEmpty()
                    || rackNumField.getText().isEmpty()
                    || admissionDate.getText().isEmpty()
                    || writeOffDate.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idLibraryField.getText());
                newValues.add(hallNumTexField.getText());
                newValues.add(rackNumField.getText());
                newValues.add(shelfTexField.getText());
                newValues.add(admissionDate.getText());
                newValues.add(writeOffDate.getText());

                try {
                    String sqlValuesSet = "set id_library = " + newValues.get(0) +  ", hall_num = '" + newValues.get(1)
                            + "', rack_num = '" + newValues.get(2) + "', shelf_num = '" + newValues.get(3) + "', DATE_OF_ADMISSION = to_date('"
                            + newValues.get(4) + "','dd.mm.yyyy'), WRITE_OFF_DATE = to_date('" + newValues.get(5) + "', 'dd.mm.yyyy') where id_edition = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 1), indexRow, 1);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 2), indexRow, 2);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 3), indexRow, 3);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 4), indexRow, 4);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 5), indexRow, 5);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 6), indexRow, 6);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    switch (exception.getErrorCode()){
                        case 2290:{
                            error.setText("Ошибка добавления записи! Обнаружены отрицательные числа номеров или неверно проставлены даты!");
                            break;
                        }
                        case 2291:{
                            error.setText("Ошибка добваления записи! Неверные Id-бибилиотеки или номер зала!");
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

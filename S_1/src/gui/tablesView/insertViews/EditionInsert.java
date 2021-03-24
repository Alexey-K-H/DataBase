package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class EditionInsert extends JDialog implements InsertFrame{
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public EditionInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Добавление нового издания");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные для новго издания");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

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

        JLabel hall_num = new JLabel("Номер зала");
        hall_num.setFont(new Font(hall_num.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, hall_num, 20, SpringLayout.SOUTH, idLibTexField);
        layout.putConstraint(SpringLayout.WEST, hall_num, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hall_num);

        JTextField hallNumTexField = new JTextField(10);
        hallNumTexField.setFont(new Font(hallNumTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, hallNumTexField, 10, SpringLayout.SOUTH, hall_num);
        layout.putConstraint(SpringLayout.WEST, hallNumTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(hallNumTexField);

        JLabel rackNumLabel = new JLabel("Номер стеллажа");
        rackNumLabel.setFont(new Font(rackNumLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, rackNumLabel, 20, SpringLayout.SOUTH, hallNumTexField);
        layout.putConstraint(SpringLayout.WEST, rackNumLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(rackNumLabel);

        JTextField rackNumTexField = new JTextField(10);
        rackNumTexField.setFont(new Font(rackNumTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, rackNumTexField, 10, SpringLayout.SOUTH, rackNumLabel);
        layout.putConstraint(SpringLayout.WEST, rackNumTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(rackNumTexField);

        JLabel shelfNumLabel = new JLabel("Номер полки");
        shelfNumLabel.setFont(new Font(shelfNumLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, shelfNumLabel, 20, SpringLayout.SOUTH, rackNumTexField);
        layout.putConstraint(SpringLayout.WEST, shelfNumLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(shelfNumLabel);

        JTextField shelfNumTexField = new JTextField(10);
        shelfNumTexField.setFont(new Font(shelfNumTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, shelfNumTexField, 10, SpringLayout.SOUTH, shelfNumLabel);
        layout.putConstraint(SpringLayout.WEST, shelfNumTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(shelfNumTexField);

        JLabel terms = new JLabel("<html>Дата поступления/Дата списания<br>В формате дд.мм.гггг</html>");
        terms.setFont(new Font(terms.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, terms, 20, SpringLayout.SOUTH, shelfNumTexField);
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
        JFormattedTextField writeOffDate = new JFormattedTextField(maskFormatter);
        writeOffDate.setColumns(7);

        admissionDate.setFont(new Font(admissionDate.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, admissionDate, 10, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, admissionDate, 20, SpringLayout.WEST, jPanel);
        jPanel.add(admissionDate);

        writeOffDate.setFont(new Font(writeOffDate.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, writeOffDate, 10, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, writeOffDate, 10, SpringLayout.EAST, admissionDate);
        jPanel.add(writeOffDate);


        JButton confirm = new JButton("Добавить издание");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idLibTexField.getText());
            currValues.add(hallNumTexField.getText());
            currValues.add(rackNumTexField.getText());
            currValues.add(shelfNumTexField.getText());
            currValues.add(admissionDate.getText());
            currValues.add(writeOffDate.getText());

            String sql = "insert into EDITIONS (ID_LIBRARY, HALL_NUM, RACK_NUM, SHELF_NUM, DATE_OF_ADMISSION, WRITE_OFF_DATE) values (" + currValues.get(0) + "," + currValues.get(1) + "," +
                    currValues.get(2) + "," + currValues.get(3) + "," +
                    "to_date('" + currValues.get(4) + "', 'dd.mm.yyyy')" + "," +
                    "to_date('" + currValues.get(5) + "', 'dd.mm.yyyy')" +
                    ")";
            try {
                performInsertOperation(sql);
                idLibTexField.setText("");
                hallNumTexField.setText("");
                rackNumTexField.setText("");
                shelfNumTexField.setText("");
                admissionDate.setText("");
                writeOffDate.setText("");

                Object[] values = new Object[]{
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 0),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 1),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 2),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 3),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 4),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 5),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 6)
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
                    case 2290:{
                        error.setText("Ошибка добавления записи! Обнаружены отрицательные числа номеров или неверно проставлены даты!");
                        break;
                    }
                    case 2291:{
                        error.setText("Ошибка добваления записи! Нверные Id бибилиотеки или Номер зала!");
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
        layout.putConstraint(SpringLayout.NORTH, cleanValues, 50, SpringLayout.SOUTH, admissionDate);
        layout.putConstraint(SpringLayout.WEST, cleanValues, 20, SpringLayout.WEST, jPanel);
        cleanValues.addActionListener(e -> {
            idLibTexField.setText("");
            hallNumTexField.setText("");
            rackNumTexField.setText("");
            shelfNumTexField.setText("");
            admissionDate.setText("");
            writeOffDate.setText("");
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

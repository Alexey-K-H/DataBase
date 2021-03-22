package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReaderInsert extends JDialog implements InsertFrame{
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public ReaderInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Добавление нового читателя");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные для регистрации читателя");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idLabel = new JLabel("Идентификатор читателя");
        idLabel.setFont(new Font(idLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idLabel, 50, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLabel);

        JTextField idTextField = new JTextField(10);
        idTextField.setFont(new Font(idTextField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idTextField, 10, SpringLayout.SOUTH, idLabel);
        layout.putConstraint(SpringLayout.WEST, idTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idTextField);

        JLabel idLibLabel = new JLabel("Идентификатор библиотеки");
        idLibLabel.setFont(new Font(idLibLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibLabel, 20, SpringLayout.SOUTH, idTextField);
        layout.putConstraint(SpringLayout.WEST, idLibLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibLabel);

        JTextField idLibTexField = new JTextField(10);
        idLibTexField.setFont(new Font(idLibTexField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibTexField, 10, SpringLayout.SOUTH, idLibLabel);
        layout.putConstraint(SpringLayout.WEST, idLibTexField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibTexField);

        JLabel surname = new JLabel("Фамилия");
        surname.setFont(new Font(surname.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, surname, 20, SpringLayout.SOUTH, idLibTexField);
        layout.putConstraint(SpringLayout.WEST, surname, 20, SpringLayout.WEST, jPanel);
        jPanel.add(surname);

        JTextField surnameTexFiled = new JTextField(10);
        surnameTexFiled.setFont(new Font(surnameTexFiled.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, surnameTexFiled, 10, SpringLayout.SOUTH, surname);
        layout.putConstraint(SpringLayout.WEST, surnameTexFiled, 20, SpringLayout.WEST, jPanel);
        jPanel.add(surnameTexFiled);

        JLabel name = new JLabel("Имя");
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, surnameTexFiled);
        layout.putConstraint(SpringLayout.WEST, name, 20, SpringLayout.WEST, jPanel);
        jPanel.add(name);

        JTextField nameTextField = new JTextField(10);
        nameTextField.setFont(new Font(surnameTexFiled.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, nameTextField, 10, SpringLayout.SOUTH, name);
        layout.putConstraint(SpringLayout.WEST, nameTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(nameTextField);

        JLabel patronymic = new JLabel("Отчество");
        patronymic.setFont(new Font(patronymic.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, patronymic, 20, SpringLayout.SOUTH, nameTextField);
        layout.putConstraint(SpringLayout.WEST, patronymic, 20, SpringLayout.WEST, jPanel);
        jPanel.add(patronymic);

        JTextField patronymicTextField = new JTextField(10);
        patronymicTextField.setFont(new Font(surnameTexFiled.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, patronymicTextField, 10, SpringLayout.SOUTH, patronymic);
        layout.putConstraint(SpringLayout.WEST, patronymicTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(patronymicTextField);


        JLabel status = new JLabel("Статус");
        status.setFont(new Font(status.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, status, 20, SpringLayout.SOUTH, patronymicTextField);
        layout.putConstraint(SpringLayout.WEST, status, 20, SpringLayout.WEST, jPanel);
        jPanel.add(status);

        JTextField statusTextField = new JTextField(10);
        statusTextField.setFont(new Font(surnameTexFiled.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, statusTextField, 10, SpringLayout.SOUTH, status);
        layout.putConstraint(SpringLayout.WEST, statusTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(statusTextField);

        JButton confirm = new JButton("Добавить читателя");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            if(!statusTextField.getText().equals("ученик") ||
            !statusTextField.getText().equals("учитель") ||
            !statusTextField.getText().equals("студент") ||
            !statusTextField.getText().equals("научный сотрудник") ||
            !statusTextField.getText().equals("работник") ||
            !statusTextField.getText().equals("пенсионер")){
                JLabel error = new JLabel("Неправильно задан статус читателя!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                currValues = new ArrayList<>();
                currValues.add("'" + idTextField.getText() + "'");
                currValues.add("'" + idLibTexField.getText() + "'");
                currValues.add("'" + surnameTexFiled.getText() + "'");
                currValues.add("'" + nameTextField.getText() + "'");
                currValues.add("'" + patronymicTextField.getText() + "'");
                currValues.add("'" + statusTextField.getText() + "'");

                try {
                    performInsertOperation(currValues);
                    idTextField.setText("");
                    idLibTexField.setText("");
                    surnameTexFiled.setText("");
                    nameTextField.setText("");
                    patronymicTextField.setText("");
                    statusTextField.setText("");

                    Object[] values = new Object[]{
                            currValues.get(0).substring(1, currValues.get(0).length() - 1),
                            currValues.get(1).substring(1, currValues.get(1).length() - 1),
                            currValues.get(2).substring(1, currValues.get(2).length() - 1) + " " +
                                    currValues.get(3).substring(1, currValues.get(3).length() - 1) + " " +
                                    currValues.get(4).substring(1, currValues.get(4).length() - 1),
                            currValues.get(5).substring(1, currValues.get(5).length() - 1)
                    };

                    tableModel.addRow(values);
                    JLabel success = new JLabel("Запись добавлена успешно!");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "INSERT", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    switch (exception.getErrorCode()) {
                        case 1: {
                            error.setText("Ошибка добавления записи! Нарушена уникальность идентификаторов библиотекарей!");
                            break;
                        }
                        case 936: {
                            error.setText("Ошибка добавленя записи! Незаполненные поля!");
                            break;
                        }
                        case 2291: {
                            error.setText("Ошибка добваления записи! Нет библиотеки с таким ID!");
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
            }
        });
        jPanel.add(confirm);

        JButton clear = new JButton("Очистить поля");
        clear.setFont(new Font(clear.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, clear, -20, SpringLayout.NORTH, confirm);
        layout.putConstraint(SpringLayout.EAST, clear, -20, SpringLayout.EAST, jPanel);
        clear.addActionListener(e -> {
            idTextField.setText("");
            idLibTexField.setText("");
            surnameTexFiled.setText("");
            nameTextField.setText("");
            patronymicTextField.setText("");
            statusTextField.setText("");
        });
        jPanel.add(clear);

        JLabel statusInfo = new JLabel("<html><b>Статус</b> читателя<br>может быть<br>следующим:<br>" +
                "<b>1.ученик</b><br>" +
                "<b>2.учитель</b><br>" +
                "<b>3.студент</b><br>" +
                "<b>4.научный сотрудник</b><br>" +
                "<b>5.работник</b><br>" +
                "<b>6.пенсионер</b></html>");
        statusInfo.setFont(new Font(statusInfo.getFont().getName(), Font.PLAIN, 16));
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        statusInfo.setBorder(solidBorder);
        statusInfo.setIcon(icon);
        layout.putConstraint(SpringLayout.SOUTH, statusInfo, -50, SpringLayout.NORTH, clear);
        layout.putConstraint(SpringLayout.EAST, statusInfo, -10, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.WEST, statusInfo, 10, SpringLayout.EAST, idLibLabel);
        jPanel.add(statusInfo);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void performInsertOperation(ArrayList<String> values) throws SQLException {
        tableController.insertNewRecord(values);
    }
}

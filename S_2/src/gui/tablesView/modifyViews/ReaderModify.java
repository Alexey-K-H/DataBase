package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReaderModify extends JDialog implements ModifyView{
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public ReaderModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
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
        this.setTitle("Изменение данных читателя");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные читателя");
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

        String[] personNames = currValues.get(2).split(" ");

        JLabel surname = new JLabel("Фамилия");
        surname.setFont(new Font(surname.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, surname, 20, SpringLayout.SOUTH, idLibraryField);
        layout.putConstraint(SpringLayout.WEST, surname, 20, SpringLayout.WEST, jPanel);
        jPanel.add(surname);

        JTextField surnameField = new JTextField(10);
        surnameField.setText(personNames[0]);
        surnameField.setFont(new Font(surnameField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, surnameField, 10, SpringLayout.SOUTH, surname);
        layout.putConstraint(SpringLayout.WEST, surnameField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(surnameField);

        JLabel name = new JLabel("Имя");
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, surnameField);
        layout.putConstraint(SpringLayout.WEST, name, 20, SpringLayout.WEST, jPanel);
        jPanel.add(name);

        JTextField nameField = new JTextField(10);
        nameField.setText(personNames[1]);
        nameField.setFont(new Font(nameField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.SOUTH, name);
        layout.putConstraint(SpringLayout.WEST, nameField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(nameField);

        JLabel patronymic = new JLabel("Отчество");
        patronymic.setFont(new Font(patronymic.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, patronymic, 20, SpringLayout.SOUTH, nameField);
        layout.putConstraint(SpringLayout.WEST, patronymic, 20, SpringLayout.WEST, jPanel);
        jPanel.add(patronymic);

        JTextField patronymicField = new JTextField(10);
        patronymicField.setText(personNames[2]);
        patronymicField.setFont(new Font(patronymicField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, patronymicField, 10, SpringLayout.SOUTH, patronymic);
        layout.putConstraint(SpringLayout.WEST, patronymicField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(patronymicField);


        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idLibraryField.getText().isEmpty()
                    || surnameField.getText().isEmpty()
                    || nameField.getText().isEmpty()
                    || patronymicField.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idLibraryField.getText());
                newValues.add(surnameField.getText());
                newValues.add(nameField.getText());
                newValues.add(patronymicField.getText());

                try {
                    String sqlValuesSet = "set id_library = " + newValues.get(0) +  ", surname = '" + newValues.get(1)
                            + "', name = '" + newValues.get(2) + "', patronymic = '" + newValues.get(3) + "' where id_reader = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(newValues.get(0), indexRow, 1);
                    tableModel.setValueAt(newValues.get(1) + " " + newValues.get(2) + " " + newValues.get(3), indexRow, 2);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    tableController.getConnection().getConn().createStatement().executeUpdate("COMMIT ");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    switch (exception.getErrorCode()){
                        case 2290:{
                            error.setText("Ошибка добавления записи! Номер зала есть положительное число!");
                            break;
                        }
                        case 2291:{
                            error.setText("Ошибка добваления записи! Нет библиотеки с таким ID!");
                            break;
                        }
                        default:{
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
            }
        });
        jPanel.add(confirmUpdates);

        JLabel statusInfo = new JLabel("<html><b>Статус</b> читателя<br><b>нельзя изменить!</b><br>Чтобы сменить<br>" +
                "статус, необходимо удалить читателя и снова его<br>" +
                "зарегистрировать</html>");
        statusInfo.setFont(new Font(statusInfo.getFont().getName(), Font.PLAIN, 16));
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        statusInfo.setBorder(solidBorder);
        statusInfo.setIcon(icon);
        layout.putConstraint(SpringLayout.NORTH, statusInfo, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.EAST, statusInfo, -10, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.WEST, statusInfo, 30, SpringLayout.EAST, patronymicField);
        jPanel.add(statusInfo);

        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void performUpdateOperation(String sqlValuesSet) throws SQLException {
        tableController.modifyRow(sqlValuesSet);
    }
}

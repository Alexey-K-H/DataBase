package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModify extends JDialog implements ModifyView{
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public UserModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow){
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
        this.setTitle("Изменение данных системного пользователя");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные пользователя");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idLibraryLabel = new JLabel("Пароль");
        idLibraryLabel.setFont(new Font(idLibraryLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibraryLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLibraryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibraryLabel);

        JTextField passwordField = new JTextField(10);
        passwordField.setText(currValues.get(1));
        passwordField.setFont(new Font(passwordField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, passwordField, 10, SpringLayout.SOUTH, idLibraryLabel);
        layout.putConstraint(SpringLayout.WEST, passwordField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(passwordField);


        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(passwordField.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(passwordField.getText());

                try {
                    String sqlValuesSet = "set password = '" + newValues.get(0) + "' where user_id = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(newValues.get(0), indexRow, 1);
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

        JLabel statusInfo = new JLabel("<html><b>Статус</b> пользователя<br><b>нельзя изменить!</b><br>Чтобы сменить<br>" +
                "статус, необходимо удалить пользователя и снова его<br>" +
                "зарегистрировать через нужный раздел \"Читатели\" или \"Библиотекари\"</html>");
        statusInfo.setFont(new Font(statusInfo.getFont().getName(), Font.PLAIN, 16));
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        statusInfo.setBorder(solidBorder);
        statusInfo.setIcon(icon);
        layout.putConstraint(SpringLayout.NORTH, statusInfo, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.EAST, statusInfo, -10, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.WEST, statusInfo, 30, SpringLayout.EAST, passwordField);
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

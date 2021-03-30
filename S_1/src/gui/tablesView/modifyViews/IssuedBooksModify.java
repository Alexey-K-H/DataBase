package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class IssuedBooksModify extends JDialog implements ModifyView{
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public IssuedBooksModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
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
        this.setTitle("Изменение данных о выданной книге");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>В данном разделе можно<br>" +
                "изменить сроки возврата книги<br>" +
                "или указать о возврате книги в бибилиотеку</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel terms = new JLabel("<html>Дата возврата<br>В формате дд.мм.гггг</html>");
        terms.setFont(new Font(terms.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, terms, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, terms, 20, SpringLayout.WEST, jPanel);
        jPanel.add(terms);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }
        JFormattedTextField date_of_return = new JFormattedTextField(maskFormatter);
        date_of_return.setColumns(7);
        String[] dateItems2 = currValues.get(6).split("-");
        date_of_return.setText(dateItems2[2] + dateItems2[1] + dateItems2[0]);

        date_of_return.setFont(new Font(date_of_return.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, date_of_return, 20, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, date_of_return, 20, SpringLayout.WEST, jPanel);
        jPanel.add(date_of_return);

        JLabel returned = new JLabel("Книгу вернул читатель? (Да/Нет)");
        returned.setFont(new Font(returned.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, returned, 20, SpringLayout.SOUTH, date_of_return);
        layout.putConstraint(SpringLayout.WEST, returned, 20, SpringLayout.WEST, jPanel);
        jPanel.add(returned);

        JTextField is_returned = new JTextField(5);
        is_returned.setText(currValues.get(7));
        is_returned.setFont(new Font(is_returned.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, is_returned, 20, SpringLayout.SOUTH, returned);
        layout.putConstraint(SpringLayout.WEST, is_returned, 20, SpringLayout.WEST, jPanel);
        jPanel.add(is_returned);

        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(date_of_return.getText().isEmpty()
                    || is_returned.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(date_of_return.getText());
                newValues.add(is_returned.getText());

                try {
                    String sqlValuesSet = "set return_date = to_date('"
                            + newValues.get(0) + "','dd.mm.yyyy'), is_returned = '" + newValues.get(1) + "'" +
                            " where id_record = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 1), indexRow, 1);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 2), indexRow, 2);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 3), indexRow, 3);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 4), indexRow, 4);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 5), indexRow, 5);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 6), indexRow, 6);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 7), indexRow, 7);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    if (exception.getErrorCode() == 2290) {
                        error.setText("Ошибка добавления записи! Неверно заполнено поле возврата или неверно выставлена дата!");
                    } else {
                        error.setText(exception.getMessage());
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

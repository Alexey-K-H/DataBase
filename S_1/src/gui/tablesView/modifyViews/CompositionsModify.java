package gui.tablesView.modifyViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompositionsModify extends JDialog implements ModifyView{
    private final TableController tableController;
    private final ArrayList<String> currValues;
    private final DefaultTableModel tableModel;
    private final int indexRow;

    public CompositionsModify(TableController tableController, ArrayList<String> currValues, DefaultTableModel tableModel, int indexRow) {
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
        this.setTitle("Изменение данных проивзедения");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите новые данные проивзедения");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idEdition = new JLabel("Издание");
        idEdition.setFont(new Font(idEdition.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idEdition, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idEdition, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEdition);

        JTextField idEditionField = new JTextField(10);
        idEditionField.setText(currValues.get(1));
        idEditionField.setFont(new Font(idEditionField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idEditionField, 10, SpringLayout.SOUTH, idEdition);
        layout.putConstraint(SpringLayout.WEST, idEditionField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEditionField);

        JLabel author = new JLabel("Автор (Фамилия и инициалы)");
        author.setFont(new Font(author.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, author, 20, SpringLayout.SOUTH, idEditionField);
        layout.putConstraint(SpringLayout.WEST, author, 20, SpringLayout.WEST, jPanel);
        jPanel.add(author);

        JTextField authorField = new JTextField(10);
        authorField.setText(currValues.get(2));
        authorField.setFont(new Font(authorField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, authorField, 10, SpringLayout.SOUTH, author);
        layout.putConstraint(SpringLayout.WEST, authorField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(authorField);

        JLabel name = new JLabel("Название");
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, authorField);
        layout.putConstraint(SpringLayout.WEST, name, 20, SpringLayout.WEST, jPanel);
        jPanel.add(name);

        JTextField nameField = new JTextField(10);
        nameField.setText(currValues.get(3));
        nameField.setFont(new Font(nameField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.SOUTH, name);
        layout.putConstraint(SpringLayout.WEST, nameField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(nameField);

        JLabel popularity = new JLabel("Популярность (число в пределах от 0 до 1)");
        popularity.setFont(new Font(popularity.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, popularity, 20, SpringLayout.SOUTH, nameField);
        layout.putConstraint(SpringLayout.WEST, popularity, 20, SpringLayout.WEST, jPanel);
        jPanel.add(popularity);

        JTextField popularityField = new JTextField(10);
        popularityField.setText(currValues.get(4));
        popularityField.setFont(new Font(popularityField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, popularityField, 10, SpringLayout.SOUTH, popularity);
        layout.putConstraint(SpringLayout.WEST, popularityField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(popularityField);

        JLabel genre = new JLabel("Жанр");
        genre.setFont(new Font(genre.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, genre, 20, SpringLayout.SOUTH, popularityField);
        layout.putConstraint(SpringLayout.WEST, genre, 20, SpringLayout.WEST, jPanel);
        jPanel.add(genre);

        JTextField genreField = new JTextField(10);
        genreField.setText(currValues.get(5));
        genreField.setFont(new Font(genreField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, genreField, 10, SpringLayout.SOUTH, genre);
        layout.putConstraint(SpringLayout.WEST, genreField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(genreField);


        JButton confirmUpdates = new JButton("Сохранить изменения");
        confirmUpdates.setFont(new Font(confirmUpdates.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirmUpdates, -20, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirmUpdates, -20, SpringLayout.EAST, jPanel);
        confirmUpdates.addActionListener(e -> {
            if(idEditionField.getText().isEmpty()
                    || authorField.getText().isEmpty()
                    || nameField.getText().isEmpty()
                    || popularityField.getText().isEmpty()
                    || genreField.getText().isEmpty()){
                JLabel error = new JLabel("Сохранение невозможно! Незаполненное поле!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                ArrayList<String> newValues = new ArrayList<>();
                newValues.add(idEditionField.getText());
                newValues.add(authorField.getText());
                newValues.add(nameField.getText());
                newValues.add(popularityField.getText());
                newValues.add(genreField.getText());

                try {
                    String sqlValuesSet = "set id_edition = " + newValues.get(0) +  ", AUTHOR = '" + newValues.get(1)
                            + "', TITLE = '" + newValues.get(2) + "', POPULARITY = '" + newValues.get(3) + "', GENRE = '"
                            + newValues.get(4) + "' where id_record = " + currValues.get(0);
                    performUpdateOperation(sqlValuesSet);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 1), indexRow, 1);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 2), indexRow, 2);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 3), indexRow, 3);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 4), indexRow, 4);
                    tableModel.setValueAt(tableController.getTableSet().getValueAt(indexRow, 5), indexRow, 5);
                    this.setVisible(false);
                    JLabel success = new JLabel("Изменения сохранены");
                    success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, success, "Модификация записи", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException exception) {
                    JLabel error = new JLabel();
                    switch (exception.getErrorCode()){
                        case 936:
                        case 1400:{
                            error.setText("Ошибка добавленя записи! Незаполненные поля!");
                            break;
                        }
                        case 2290:{
                            error.setText("Ошибка добавления записи! Неверное значение популярности!");
                            break;
                        }
                        case 2291:{
                            error.setText("Ошибка добваления записи! Нверный Id издания!");
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

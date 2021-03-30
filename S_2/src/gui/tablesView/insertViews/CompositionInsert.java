package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompositionInsert extends JDialog implements InsertFrame{
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public CompositionInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Добавление нового произведения");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные для новго проивзедения");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idEditionLabel = new JLabel("Идентификатор издания");
        idEditionLabel.setFont(new Font(idEditionLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idEditionLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idEditionLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEditionLabel);

        JTextField idEditionField = new JTextField(10);
        idEditionField.setFont(new Font(idEditionField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idEditionField, 10, SpringLayout.SOUTH, idEditionLabel);
        layout.putConstraint(SpringLayout.WEST, idEditionField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEditionField);


        JLabel authorLabel = new JLabel("Автор (Фамилия и инфициалы)");
        authorLabel.setFont(new Font(authorLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, authorLabel, 20, SpringLayout.SOUTH, idEditionField);
        layout.putConstraint(SpringLayout.WEST, authorLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(authorLabel);

        JTextField authorField = new JTextField(10);
        authorField.setFont(new Font(authorField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, authorField, 10, SpringLayout.SOUTH, authorLabel);
        layout.putConstraint(SpringLayout.WEST, authorField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(authorField);

        JLabel titleLabel = new JLabel("Название произведения");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 20, SpringLayout.SOUTH, authorField);
        layout.putConstraint(SpringLayout.WEST, titleLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(titleLabel);

        JTextField titleField = new JTextField(10);
        titleField.setFont(new Font(titleField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, titleField, 10, SpringLayout.SOUTH, titleLabel);
        layout.putConstraint(SpringLayout.WEST, titleField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(titleField);

        JLabel popularityLabel = new JLabel("Популярность. (Значение в пределах от 0 до 1)");
        popularityLabel.setFont(new Font(popularityLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, popularityLabel, 20, SpringLayout.SOUTH, titleField);
        layout.putConstraint(SpringLayout.WEST, popularityLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(popularityLabel);

        JTextField popularityField = new JTextField(10);
        popularityField.setFont(new Font(popularityField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, popularityField, 10, SpringLayout.SOUTH, popularityLabel);
        layout.putConstraint(SpringLayout.WEST, popularityField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(popularityField);

        JLabel genreLabel = new JLabel("Жанр произведения");
        genreLabel.setFont(new Font(genreLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, genreLabel, 20, SpringLayout.SOUTH, popularityField);
        layout.putConstraint(SpringLayout.WEST, genreLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(genreLabel);

        //JTextField genreField = new JTextField(10);

        String[] items = {
                "Прочие",
                "Художественное произведение",
                "Роман",
                "Учебная литература",
                "Методическое пособие",
                "Сказки",
                "Сборник стихов",
                "Научно-популярная статья",
                "Журнал",
                "Детектив",
                "Энциклопедии"
        };

        JComboBox<String> genreField = new JComboBox<>(items);
        genreField.setFont(new Font(genreField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, genreField, 10, SpringLayout.SOUTH, genreLabel);
        layout.putConstraint(SpringLayout.WEST, genreField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(genreField);


        JButton confirm = new JButton("Добавить произведение");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idEditionField.getText());
            currValues.add(authorField.getText());
            currValues.add(titleField.getText());
            currValues.add(popularityField.getText());
            currValues.add(genreField.getSelectedItem().toString());

            String sql = "insert into COMPOSITIONS(ID_EDITION, AUTHOR, TITLE, POPULARITY, GENRE) values (" + currValues.get(0) + ",'" + currValues.get(1) + "','" +
                    currValues.get(2) + "'," + currValues.get(3) + ",'"+ currValues.get(4) + "')";
            try {
                performInsertOperation(sql);
                idEditionField.setText("");
                authorField.setText("");
                titleField.setText("");
                popularityField.setText("");
                genreField.setSelectedItem("Прочие");

                Object[] values = new Object[]{
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 0),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 1),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 2),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 3),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 4),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 5)
                };
                tableModel.addRow(values);
                JLabel success = new JLabel("Запись добавлена успешно!");
                success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, success, "INSERT", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException exception) {
                JLabel error = new JLabel();
                //System.out.println(exception.getErrorCode());
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
        });
        jPanel.add(confirm);


        JButton cleanValues = new JButton("Очистить поля");
        cleanValues.setFont(new Font(cleanValues.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, cleanValues, 50, SpringLayout.SOUTH, genreField);
        layout.putConstraint(SpringLayout.WEST, cleanValues, 20, SpringLayout.WEST, jPanel);
        cleanValues.addActionListener(e -> {
            idEditionField.setText("");
            authorField.setText("");
            titleField.setText("");
            popularityField.setText("");
            genreField.setSelectedItem("Прочие");
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

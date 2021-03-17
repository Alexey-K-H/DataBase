package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryInsert extends JFrame implements InsertFrame {
    private TableController tableController;

    public LibraryInsert(TableController tableController){
        this.tableController = tableController;
    }


    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 200, 500, 400);
        this.setTitle("Добавление новой бибилиотеки");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные для добавления новой библиотеки");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idLabel = new JLabel("Идентификатор бибилиотеки:");
        idLabel.setFont(new Font(idLabel.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLabel, 50, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLabel);

        JTextField idLibTextField = new JTextField(10);
        idLibTextField.setFont(new Font(idLibTextField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibTextField, 10, SpringLayout.SOUTH, idLabel);
        layout.putConstraint(SpringLayout.WEST, idLibTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibTextField);


        JLabel quantityBooksLabel = new JLabel("Количество книг в фонде бибилиотеки:");
        quantityBooksLabel.setFont(new Font(quantityBooksLabel.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, quantityBooksLabel, 20, SpringLayout.SOUTH, idLibTextField);
        layout.putConstraint(SpringLayout.WEST, quantityBooksLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(quantityBooksLabel);

        JTextField quantityBooksTextField = new JTextField(10);
        quantityBooksTextField.setFont(new Font(quantityBooksTextField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, quantityBooksTextField, 10, SpringLayout.SOUTH, quantityBooksLabel);
        layout.putConstraint(SpringLayout.WEST, quantityBooksTextField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(quantityBooksTextField);


        JButton confirmInsert = new JButton("Добавить запись");
        confirmInsert.setFont(new Font(confirmInsert.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, confirmInsert, 50, SpringLayout.SOUTH, quantityBooksTextField);
        layout.putConstraint(SpringLayout.EAST, confirmInsert, -20, SpringLayout.EAST, jPanel);
        confirmInsert.addActionListener(e -> {
            ArrayList<String> currValues = new ArrayList<>();
            currValues.add(idLibTextField.getText());
            currValues.add(quantityBooksTextField.getText());
            try {
                performInsertOperation(currValues);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        jPanel.add(confirmInsert);


        JButton cleanValues = new JButton("Очистить поля");
        cleanValues.setFont(new Font(cleanValues.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, cleanValues, 50, SpringLayout.SOUTH, quantityBooksTextField);
        layout.putConstraint(SpringLayout.WEST, cleanValues, 20, SpringLayout.WEST, jPanel);
        cleanValues.addActionListener(e -> {
            idLibTextField.setText("");
            quantityBooksTextField.setText("");
        });
        jPanel.add(cleanValues);


        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void performInsertOperation(ArrayList<String> values) throws SQLException {
        tableController.insertNewRecord(values);
    }
}

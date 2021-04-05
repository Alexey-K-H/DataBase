package gui.tablesView.insertViews;

import controllers.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class IssuedBooksInsert extends JDialog implements InsertFrame{
    private final TableController tableController;
    private ArrayList<String> currValues;
    private final DefaultTableModel tableModel;

    public IssuedBooksInsert(TableController tableController, DefaultTableModel tableModel) {
        this.tableController = tableController;
        this.tableModel = tableModel;
    }

    @Override
    public void openInsertWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Регистрация выданной книги");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Введите данные выдаваемой книги");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel idLibrarianLabel = new JLabel("Идентификатор библиотекаря, выдавшего книгу");
        idLibrarianLabel.setFont(new Font(idLibrarianLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibrarianLabel, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, idLibrarianLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibrarianLabel);

        JTextField idLibrarianField = new JTextField(10);
        idLibrarianField.setFont(new Font(idLibrarianField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, idLibrarianField, 10, SpringLayout.SOUTH, idLibrarianLabel);
        layout.putConstraint(SpringLayout.WEST, idLibrarianField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idLibrarianField);

        JLabel idEditionLabel = new JLabel("Идентификатор издания");
        idEditionLabel.setFont(new Font(idEditionLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idEditionLabel, 20, SpringLayout.SOUTH, idLibrarianField);
        layout.putConstraint(SpringLayout.WEST, idEditionLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idEditionLabel);

        JTextField editionField = new JTextField(10);
        editionField.setFont(new Font(editionField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, editionField, 10, SpringLayout.SOUTH, idEditionLabel);
        layout.putConstraint(SpringLayout.WEST, editionField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(editionField);

        JLabel idCompositionLabel = new JLabel("Идентификатор произведения");
        idCompositionLabel.setFont(new Font(idCompositionLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idCompositionLabel, 20, SpringLayout.SOUTH, editionField);
        layout.putConstraint(SpringLayout.WEST, idCompositionLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idCompositionLabel);

        JTextField compositionField = new JTextField(10);
        compositionField.setFont(new Font(compositionField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, compositionField, 10, SpringLayout.SOUTH, idCompositionLabel);
        layout.putConstraint(SpringLayout.WEST, compositionField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(compositionField);

        JLabel idReaderLabel = new JLabel("Идентификатор читателя");
        idReaderLabel.setFont(new Font(idReaderLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, idReaderLabel, 20, SpringLayout.SOUTH, compositionField);
        layout.putConstraint(SpringLayout.WEST, idReaderLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(idReaderLabel);

        JTextField readerField = new JTextField(10);
        readerField.setFont(new Font(readerField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, readerField, 10, SpringLayout.SOUTH, idReaderLabel);
        layout.putConstraint(SpringLayout.WEST, readerField, 20, SpringLayout.WEST, jPanel);
        jPanel.add(readerField);


        JLabel terms = new JLabel("<html>Дата выдачи/Дата возврата<br>В формате дд.мм.гггг</html>");
        terms.setFont(new Font(terms.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, terms, 20, SpringLayout.SOUTH, readerField);
        layout.putConstraint(SpringLayout.WEST, terms, 20, SpringLayout.WEST, jPanel);
        jPanel.add(terms);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }

        JFormattedTextField date_of_issue = new JFormattedTextField(maskFormatter);
        date_of_issue.setColumns(7);
        JFormattedTextField date_of_return = new JFormattedTextField(maskFormatter);
        date_of_return.setColumns(7);

        date_of_issue.setFont(new Font(date_of_issue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, date_of_issue, 10, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, date_of_issue, 20, SpringLayout.WEST, jPanel);
        jPanel.add(date_of_issue);

        date_of_return.setFont(new Font(date_of_return.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, date_of_return, 10, SpringLayout.SOUTH, terms);
        layout.putConstraint(SpringLayout.WEST, date_of_return, 10, SpringLayout.EAST, date_of_issue);
        jPanel.add(date_of_return);


        JButton confirm = new JButton("Добавить книгу в список выданных");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, confirm, -20, SpringLayout.EAST, jPanel);
        confirm.addActionListener(e->{
            currValues = new ArrayList<>();
            currValues.add(idLibrarianField.getText());
            currValues.add(editionField.getText());
            currValues.add(compositionField.getText());
            currValues.add(readerField.getText());
            currValues.add(date_of_issue.getText());
            currValues.add(date_of_return.getText());

            String sql = "insert into ISSUED_BOOKS(ID_LIBRARIAN, ID_EDITION, id_composition, ID_READER,  DATE_OF_ISSUE, RETURN_DATE)" +
                    " values (" + currValues.get(0) + "," + currValues.get(1) + "," +
                    currValues.get(2) + "," + currValues.get(3) + "," + "to_date('" + currValues.get(4) + "','dd.mm.yyyy'),to_date('"+ currValues.get(5) + "', 'dd.mm.yyyy'))";
            try {
                performInsertOperation(sql);
                idLibrarianField.setText("");
                editionField.setText("");
                readerField.setText("");
                compositionField.setText("");
                date_of_issue.setValue(null);
                date_of_return.setValue(null);


                Object[] values = new Object[]{
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 0),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 1),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 2),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 3),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 4),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 5),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 6),
                        tableController.getTableSet().getValueAt(tableController.getTableSet().getRowCount() - 1, 7)
                };
                tableModel.addRow(values);
                JLabel success = new JLabel("Запись добавлена успешно!");
                tableController.getConnection().getConn().createStatement().executeUpdate("COMMIT ");
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
                try {
                    tableController.getConnection().getConn().createStatement().executeUpdate("ROLLBACK ");
                } catch (SQLException sqlException) {
                    error.setText(sqlException.getMessage());
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jPanel.add(confirm);

        JButton cleanValues = new JButton("Очистить поля");
        cleanValues.setFont(new Font(cleanValues.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, cleanValues, 20, SpringLayout.SOUTH, date_of_return);
        layout.putConstraint(SpringLayout.EAST, cleanValues, -20, SpringLayout.EAST, jPanel);
        cleanValues.addActionListener(e -> {
            idLibrarianField.setText("");
            editionField.setText("");
            compositionField.setText("");
            date_of_issue.setValue(null);
            date_of_return.setValue(null);
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

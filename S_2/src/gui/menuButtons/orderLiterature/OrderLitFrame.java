package gui.menuButtons.orderLiterature;

import controllers.QueryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderLitFrame extends JDialog {
    private final String userId;
    private final QueryController queryController;

    public OrderLitFrame(String userId, QueryController queryController){
        this.userId = userId;
        this.queryController = queryController;
    }

    public void makeOrder(String sql) throws SQLException {
        Statement statement = queryController.getConnection().getConn().createStatement();
        statement.executeUpdate(sql);
        statement.executeUpdate("commit ");
    }

    public void openOrderForm(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Заказать литературу");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel orderLabel = new JLabel("<html>Чтобы заказать литературу, выберите атвора,<br>" +
                "затем выберите произведение в таблице<br>и нажмите \"Заказать\"</html>");
        orderLabel.setFont(new Font(orderLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, orderLabel, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, orderLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(orderLabel);

        JLabel info = new JLabel("<html>Выберите автора из списка<br>зарегистрированных в фонде</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.SOUTH, orderLabel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        ArrayList<String> authorsNames = new ArrayList<>();
        try{
            String sql = "select distinct author from compositions";
            queryController.performSQLQuery(sql);
            while (queryController.getCurrResultSet().next()){
                authorsNames.add(queryController.getCurrResultSet().getString("author"));
            }
            queryController.closeSQLSet();
        }
        catch (SQLException exception){
                JLabel error = new JLabel();
                error.setText(exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        JComboBox<String> authorChoose = new JComboBox(authorsNames.toArray());
        authorChoose.setFont(new Font(authorChoose.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, authorChoose, 10, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, authorChoose, 20, SpringLayout.WEST, jPanel);
        jPanel.add(authorChoose);

        JButton confirm = new JButton("Найти литературу");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, confirm, 20, SpringLayout.SOUTH, authorChoose);
        layout.putConstraint(SpringLayout.WEST, confirm, 20, SpringLayout.WEST, jPanel);


        JTable table = new JTable();
        table.setRowHeight(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        int horizontalPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        int verticalPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        scrollPane.setHorizontalScrollBarPolicy(horizontalPolicy);
        scrollPane.setVerticalScrollBarPolicy(verticalPolicy);

        layout.putConstraint(SpringLayout.NORTH, scrollPane, 40, SpringLayout.SOUTH, confirm);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, jPanel);
        scrollPane.setPreferredSize(new Dimension(439, 400));
        scrollPane.setVisible(false);
        jPanel.add(scrollPane);

        JButton makeOrder = new JButton("Заказать");
        makeOrder.setFont(new Font(makeOrder.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, makeOrder, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, makeOrder, -10, SpringLayout.EAST, jPanel);
        makeOrder.setVisible(false);
        jPanel.add(makeOrder);


        confirm.addActionListener(e -> {
            String queryStr = "with t1 as (\n" +
                    "    --Выбор изданий, которые относятся к произведению автора\n" +
                    "    select ID_RECORD, ID_EDITION, TITLE\n" +
                    "    from COMPOSITIONS\n" +
                    "    where AUTHOR = '" + authorChoose.getSelectedItem() + "'\n" +
                    "),\n" +
                    "     t2 as (\n" +
                    "         --Выбор произвдений, не выданных на руки\n" +
                    "         select t1.ID_RECORD, t1.ID_EDITION, t1.TITLE, IS_RETURNED from ISSUED_BOOKS\n" +
                    "                right join t1 on t1.ID_EDITION = ISSUED_BOOKS.ID_EDITION\n" +
                    "     ),\n" +
                    "     t3 as (\n" +
                    "         --Получить инвентарные номера (номера полок, где лежит книга)\n" +
                    "         select t2.ID_RECORD as \"ID-произведения\", ID_LIBRARY as \"Библиотека\", t2.ID_EDITION as \"Издание\", TITLE as \"Название\" from EDITIONS\n" +
                    "                inner join t2 on t2.ID_EDITION = EDITIONS.ID_EDITION\n" +
                    "         where IS_RETURNED = 'да' or IS_RETURNED IS NULL\n" +
                    "     ),\n" +
                    "     t4 as(\n" +
                    "         --Проверить что этой книги уже нет в списке заказанных пользователем\n" +
                    "         select t3.\"ID-произведения\", t3.\"Библиотека\", t3.\"Издание\", t3.\"Название\" from ORDERS\n" +
                    "         right join t3 on t3.\"ID-произведения\" = ORDERS.ID_COMPOSITION\n" +
                    "         where (ORDERS.ID_READER is null) \n" +
                    "     )\n" +
                    "select *\n" +
                    "from t4";


            authorChoose.addItemListener(e1 -> {
                if (e1.getStateChange() == ItemEvent.SELECTED) {
                    scrollPane.setVisible(false);
                    makeOrder.setVisible(false);
                    for (ActionListener al : makeOrder.getActionListeners()) {
                        makeOrder.removeActionListener(al);
                    }
                }
            });

            try {
                queryController.performSQLQuery(queryStr);
            } catch (SQLException exception) {
                JLabel error = new JLabel();
                error.setText(exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            ResultSet resultSet = queryController.getCurrResultSet();

            ResultSetMetaData resultSetMetaData;
            ArrayList<String> list = new ArrayList<>();
            int columnsCount = 0;
            try{
                resultSetMetaData = resultSet.getMetaData();
                columnsCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columnsCount; i++) {
                    list.add(resultSetMetaData.getColumnLabel(i));
                }
            }
            catch (SQLException exception) {
                JLabel error = new JLabel();
                error.setText(exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            String[] columnsHeaders = list.toArray(new String[0]);
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tableModel.setColumnIdentifiers(columnsHeaders);

            ArrayList<String> listSearch = new ArrayList<>();
            try{
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsCount; i++) {
                        listSearch.add(resultSet.getObject(i).toString());
                    }
                    tableModel.addRow(listSearch.toArray());
                }
            }
            catch (SQLException exception) {
                JLabel error = new JLabel();
                error.setText(exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            table.setModel(tableModel);
            scrollPane.repaint();
            makeOrder.setVisible(true);

            int prefSize = scrollPane.getPreferredSize().width / table.getColumnCount();
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(prefSize);
            }
            scrollPane.setVisible(true);

            try {
                queryController.closeSQLSet();
            }catch (SQLException exception) {
                JLabel error = new JLabel();
                error.setText(exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            makeOrder.addActionListener(e1 -> {
                int i = table.getSelectedRow();
                if (i == -1) {
                    JLabel error = new JLabel("Перед заказом необходимо выбрать запись в таблице!");
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    ArrayList<String> currValues = new ArrayList<>();
                    for (int k = 0; k < table.getColumnCount(); k++) {
                        currValues.add(table.getValueAt(i, k).toString());
                    }

                    String id_edition = currValues.get(2);
                    String id_composition = currValues.get(0);

                    String sql_insert = "insert into ORDERS(ID_READER, ID_EDITION, ID_COMPOSITION, IS_PERFORMED) " +
                            "values (" + userId + ", " + id_edition + ", " + id_composition + ", 'нет')";

                    try {
                        makeOrder(sql_insert);
                        JLabel success = new JLabel("<html>Ваша заявка отправлена успешно.<br>Заказ будет рассмотрен в течение суток</html>");
                        success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, success, "Заказ литературы", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.removeRow(i);
                    } catch (SQLException exception) {
                        try {
                            queryController.getConnection().getConn().prepareStatement("rollback ").executeUpdate();
                        } catch (SQLException exception1) {
                            JLabel error = new JLabel();
                            error.setText(exception1.getMessage());
                            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                        JLabel error = new JLabel();
                        error.setText(exception.getMessage());
                        error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        });
        jPanel.add(confirm);
        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

package gui.menuButtons.orderLiterature;

import controllers.QueryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderServiceFrame extends JDialog {
    private final String librarianId;
    private final QueryController queryController;

    public OrderServiceFrame(String librarianId, QueryController queryController) {
        this.librarianId = librarianId;
        this.queryController = queryController;
    }

    public void openWindowFrame(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Список входящих заказов");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel orderLabel = new JLabel("<html>Ниже представлен список новых заказов<br>" +
                "на литературу, имеющуюся в вашей библиотеке.<br>Выберите заказ, чтобы оформить его.</html>");
        orderLabel.setFont(new Font(orderLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, orderLabel, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, orderLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(orderLabel);

        JTable table = new JTable();
        table.setRowHeight(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        int horizontalPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        int verticalPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        scrollPane.setHorizontalScrollBarPolicy(horizontalPolicy);
        scrollPane.setVerticalScrollBarPolicy(verticalPolicy);

        layout.putConstraint(SpringLayout.NORTH, scrollPane, 40, SpringLayout.SOUTH, orderLabel);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, jPanel);
        scrollPane.setPreferredSize(new Dimension(439, 400));
        jPanel.add(scrollPane);

        String querySql = "with t1 as (\n" +
                "    --Библиотеки зказанных книг\n" +
                "    select ID_READER, ORDERS.ID_EDITION, ID_COMPOSITION, ID_LIBRARY\n" +
                "    from ORDERS inner join EDITIONS on ORDERS.ID_EDITION = EDITIONS.ID_EDITION\n" +
                "    where ID_LIBRARIAN is null and IS_PERFORMED = 'нет'\n" +
                "),\n" +
                "     t2 as (\n" +
                "         --Библиотека конкретного библиотекаря\n" +
                "         select ID_LIBRARY\n" +
                "         from LIBRARIANS\n" +
                "         where ID_LIBRARIAN = "+librarianId+"\n" +
                "     ),\n" +
                "     t3 as(\n" +
                "       --Названия заказанных книг\n" +
                "         select ID_READER, t1.ID_EDITION, ID_COMPOSITION, ID_LIBRARY, TITLE\n" +
                "         from t1 inner join COMPOSITIONS on (t1.ID_EDITION = COMPOSITIONS.ID_EDITION\n" +
                "             and t1.ID_COMPOSITION = COMPOSITIONS.ID_RECORD)\n" +
                "     ),\n" +
                "     t4 as (\n" +
                "         --Заказы, которые сможет обработать данный библиотекарь\n" +
                "         select ID_READER as \"Читатель ID\", ID_EDITION as \"Издание\", ID_COMPOSITION \"Произведение ID\", TITLE" +
                " as \"Название\"\n" +
                "         from t3 where ID_LIBRARY = (select * from t2)\n" +
                "     )\n" +
                "select *\n" +
                "from t4";

        try {
            queryController.performSQLQuery(querySql);
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
                listSearch.clear();
            }
        }
        catch (SQLException exception) {
            JLabel error = new JLabel();
            error.setText(exception.getMessage());
            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        table.setModel(tableModel);

        int prefSize = scrollPane.getPreferredSize().width / table.getColumnCount();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(prefSize);
        }

        JButton performOrder = new JButton("Обработать заказ");
        performOrder.setFont(new Font(performOrder.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, performOrder, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, performOrder, -10, SpringLayout.EAST, jPanel);
        performOrder.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i == -1) {
                JLabel error = new JLabel("Перед обработкой заказа его необходимо выбрать в списке!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                ArrayList<String> currValues = new ArrayList<>();
                for (int k = 0; k < table.getColumnCount(); k++) {
                    currValues.add(table.getValueAt(i, k).toString());
                }

                String composition = currValues.get(2);
                String edition = currValues.get(1);
                String reader = currValues.get(0);

                OrderMakeFrame orderMakeFrame = new OrderMakeFrame(
                        librarianId,
                        edition,
                        composition,
                        reader,
                        queryController,
                        tableModel,
                        i);
                orderMakeFrame.openMakeOrderWindow();
            }
        });
        jPanel.add(performOrder);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);

    }
}

package gui.menuButtons.orderLiterature;

import controllers.QueryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserOrders extends JDialog {
    private final QueryController queryController;
    private final String userId;

    public UserOrders(QueryController queryController, String userId) {
        this.queryController = queryController;
        this.userId = userId;
    }

    public void showOrders(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 400, 500, 600);
        this.setTitle("Список ваших заказов");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel orderLabel = new JLabel("<html>Ниже представлен список важих заказов<br>" +
                "и статус их обработки. Если заказ обработан,<br>вам следует явиться в вашу библиотеку<br>для получения литературы</html>");
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
                "    --Заказы конкретного читателя\n" +
                "    select * from ORDERS\n" +
                "    where ID_READER = "+userId+"\n" +
                "),\n" +
                "     t2 as(\n" +
                "         --Уточнение информации о заказе\n" +
                "         select t1.ID_EDITION as \"Издание\", TITLE as \"Название\", t1.IS_PERFORMED as \"Заказ обработан\", t1.DATE_ORDER as \"Дата заказа\" from COMPOSITIONS\n" +
                "         inner join t1 on t1.ID_EDITION = COMPOSITIONS.ID_EDITION\n" +
                "     )\n" +
                "select *\n" +
                "from t2 " +
                "order by \"Дата заказа\" desc";

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


        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

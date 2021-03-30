package gui.queryWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultQueryView extends JDialog {
    public ResultQueryView(ResultSet resultSet, int columnsCount, String[] columnsHeaders) throws SQLException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 450, dimension.height/2 - 400, 900, 800);
        this.setTitle("Результат поиска");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("Результаты поиска");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, this.getWidth()/2 - info.getPreferredSize().width/2, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnsHeaders);

        while (resultSet.next()){
            ArrayList<String> list = new ArrayList<>();
            for(int i = 1; i <= columnsCount; i++){
                list.add(resultSet.getObject(i).toString());
            }
            tableModel.addRow(list.toArray());
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        int horizontalPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        int verticalPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        scrollPane.setHorizontalScrollBarPolicy(horizontalPolicy);
        scrollPane.setVerticalScrollBarPolicy(verticalPolicy);

        layout.putConstraint(SpringLayout.NORTH, scrollPane, 40, SpringLayout.NORTH, info);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, jPanel);
        scrollPane.setPreferredSize(new Dimension(845, 700));

        int prefSize = scrollPane.getPreferredSize().width/table.getColumnCount();
        for(int i = 0; i < table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(prefSize);
        }

        jPanel.add(scrollPane);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

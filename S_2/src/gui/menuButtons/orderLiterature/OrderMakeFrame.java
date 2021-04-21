package gui.menuButtons.orderLiterature;

import controllers.QueryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class OrderMakeFrame extends JDialog {
    private final QueryController queryController;
    private final String librarian;
    private final String edition;
    private final String composition;
    private final String reader;
    private final DefaultTableModel tableModel;
    private final int indexOrder;

    public OrderMakeFrame(String librarian,
                          String edition,
                          String composition,
                          String reader,
                          QueryController queryController,
                          DefaultTableModel tableModel,
                          int indexOrder) {
        this.librarian = librarian;
        this.edition = edition;
        this.composition = composition;
        this.reader = reader;
        this.queryController = queryController;
        this.tableModel = tableModel;
        this.indexOrder = indexOrder;
    }

    public void openMakeOrderWindow(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 200, dimension.height/2 - 130, 400, 260);
        this.setTitle("Выполнить заказ");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>Произведение будет закреплено<br>за текущим читателем.<br>" +
                "Он получит уведомление, когда книга<br>дойдет до библиотеки, где он прикреплен</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JButton confirm = new JButton("Подтвердить");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, confirm, 40, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, confirm, this.getWidth()/2 - confirm.getPreferredSize().width/2, SpringLayout.WEST, jPanel);
        confirm.addActionListener(e -> {
            try {
                this.setVisible(false);
                queryController.getConnection().getConn().prepareStatement(
                        "update ORDERS set IS_PERFORMED = 'да' where (ID_READER = "+ reader +" " +
                                "and ID_COMPOSITION = "+composition+" and ID_EDITION = "+edition+")"
                ).executeUpdate();

                queryController.getConnection().getConn().prepareStatement(
                        "insert into ISSUED_BOOKS(ID_LIBRARIAN, ID_EDITION, ID_COMPOSITION,ID_READER)" +
                                "values ("+librarian+", "+edition+", "+composition+", " +reader+")"
                ).executeUpdate();

                queryController.getConnection().getConn().prepareStatement("commit ").executeUpdate();

                JLabel success = new JLabel("<html>Заказ выполнен. Пользователь получит уведомление в своем личном кабинете</html>");
                success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, success, "Заказ литературы", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(indexOrder);
            }
            catch (SQLException exception){
                try {
                    queryController.getConnection().getConn().prepareStatement("rollback").executeUpdate();
                }
                catch (SQLException exception1){
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
        });
        jPanel.add(confirm);

        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

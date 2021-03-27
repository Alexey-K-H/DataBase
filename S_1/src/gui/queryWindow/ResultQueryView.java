package gui.queryWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultQueryView extends JDialog {
    public ResultQueryView(ResultSet resultSet) throws SQLException {
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

        while (resultSet.next()){
            System.out.println(resultSet.getObject(1).toString() + ", " + resultSet.getObject(2).toString() + ", " + resultSet.getObject(3).toString());
        }
    }
}

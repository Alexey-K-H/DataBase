package gui.queryWindow;

import controllers.QueryController;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstQuery extends JDialog implements QueryFrame {
    private QueryController queryController;

    public FirstQuery(QueryController queryController) {
        this.queryController = queryController;
    }

    @Override
    public void openQueryConfig() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 250, 600, 500);
        this.setTitle("Поиск читателей");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);



        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void performQuery(String sql) throws SQLException {

    }

    @Override
    public void showQueryResult(ResultSet resultSet) {

    }
}

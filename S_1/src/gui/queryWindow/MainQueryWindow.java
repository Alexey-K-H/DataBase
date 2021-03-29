package gui.queryWindow;

import controllers.QueryController;
import gui.queryWindow.query1.FirstQuery;

import javax.swing.*;
import java.awt.*;

public class MainQueryWindow extends JDialog {
    private final QueryController queryController;

    public MainQueryWindow(QueryController controller){
        this.queryController = controller;
    }

    public void openQueryConsole(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 450, dimension.height/2 - 400, 900, 800);
        this.setTitle("Отчеты по пользователям библиотечного фонда");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel firstQueryLabel = new JLabel("Отчет №1");
        firstQueryLabel.setFont(new Font(firstQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, firstQueryLabel, 10, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, firstQueryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(firstQueryLabel);

        JButton firstQuery = new JButton("<html>Список читателей с заданными характеристиками</html>");
        firstQuery.setFont(new Font(firstQuery.getFont().getName(), Font.BOLD, 14));
        firstQuery.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 30));
        layout.putConstraint(SpringLayout.NORTH, firstQuery, 10, SpringLayout.SOUTH, firstQueryLabel);
        layout.putConstraint(SpringLayout.WEST, firstQuery, 20, SpringLayout.WEST, jPanel);
        firstQuery.addActionListener(e->{
            FirstQuery firstQueryFrame = new FirstQuery(queryController);
            firstQueryFrame.openQueryConfig();
        });
        jPanel.add(firstQuery);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

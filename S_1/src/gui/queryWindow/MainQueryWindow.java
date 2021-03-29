package gui.queryWindow;

import controllers.QueryController;
import gui.queryWindow.query1.FirstQuery;
import gui.queryWindow.query2.SecondQuery;
import gui.queryWindow.query3.ThirdQuery;

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


        JLabel secondQueryLabel = new JLabel("Отчет №2");
        secondQueryLabel.setFont(new Font(secondQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, secondQueryLabel, 10, SpringLayout.SOUTH, firstQuery);
        layout.putConstraint(SpringLayout.WEST, secondQueryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(secondQueryLabel);

        JButton secondQuery = new JButton("<html>Читатели, на руках у которых находится указанное произведение</html>");
        secondQuery.setFont(new Font(secondQuery.getFont().getName(), Font.BOLD, 14));
        secondQuery.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 40));
        layout.putConstraint(SpringLayout.NORTH, secondQuery, 10, SpringLayout.SOUTH, secondQueryLabel);
        layout.putConstraint(SpringLayout.WEST, secondQuery, 20, SpringLayout.WEST, jPanel);
        secondQuery.addActionListener(e->{
            SecondQuery secondQueryFrame = new SecondQuery(queryController);
            secondQueryFrame.openQueryConfig();
        });
        jPanel.add(secondQuery);

        JLabel thirdQueryLabel = new JLabel("Отчет №3");
        thirdQueryLabel.setFont(new Font(thirdQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, thirdQueryLabel, 10, SpringLayout.SOUTH, secondQuery);
        layout.putConstraint(SpringLayout.WEST, thirdQueryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(thirdQueryLabel);

        JButton thirdQuery = new JButton("<html>Читатели, на руках у которых находится указанное издание</html>");
        thirdQuery.setFont(new Font(thirdQuery.getFont().getName(), Font.BOLD, 14));
        thirdQuery.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 40));
        layout.putConstraint(SpringLayout.NORTH, thirdQuery, 10, SpringLayout.SOUTH, thirdQueryLabel);
        layout.putConstraint(SpringLayout.WEST, thirdQuery, 20, SpringLayout.WEST, jPanel);
        thirdQuery.addActionListener(e->{
            ThirdQuery thirdQueryFrame = new ThirdQuery(queryController);
            thirdQueryFrame.openQueryConfig();
        });
        jPanel.add(thirdQuery);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

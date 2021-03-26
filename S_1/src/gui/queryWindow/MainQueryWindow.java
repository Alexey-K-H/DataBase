package gui.queryWindow;

import controllers.QueryController;

import javax.swing.*;
import java.awt.*;

public class MainQueryWindow extends JDialog {
    private QueryController queryController;

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

        JButton firstQuery = new JButton("<html>Получить список читателей с заданными характеристиками:<br>" +
                "студентов указанного учебного заведения, " +
                "факультета<br>" +
                "научных работников по определенной тематике и т.д." +
                "</html>");
        firstQuery.setFont(new Font(firstQuery.getFont().getName(), Font.BOLD, 12));
        layout.putConstraint(SpringLayout.NORTH, firstQuery, 20, SpringLayout.NORTH, jPanel);
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

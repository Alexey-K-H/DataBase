package gui.queryWindow;

import controllers.QueryController;
import gui.queryWindow.query1.FirstQuery;
import gui.queryWindow.query12.TwelveQuery;
import gui.queryWindow.query16.SixteenthQuery;
import gui.queryWindow.query2.SecondQuery;
import gui.queryWindow.query3.ThirdQuery;
import gui.queryWindow.query4.ForthQuery;
import gui.queryWindow.query5.FifthQuery;

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

        JLabel twelveQuery = new JLabel("Отчет №12");
        twelveQuery.setFont(new Font(twelveQuery.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, twelveQuery, 10, SpringLayout.SOUTH, thirdQuery);
        layout.putConstraint(SpringLayout.WEST, twelveQuery, 20, SpringLayout.WEST, jPanel);
        jPanel.add(twelveQuery);

        JButton twelveQueryButton = new JButton("<html>Библиотекари, работающие в указанном читальном зале некоторой библиотеки</html>");
        twelveQueryButton.setFont(new Font(twelveQueryButton.getFont().getName(), Font.BOLD, 14));
        twelveQueryButton.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 40));
        layout.putConstraint(SpringLayout.NORTH, twelveQueryButton, 10, SpringLayout.SOUTH, twelveQuery);
        layout.putConstraint(SpringLayout.WEST, twelveQueryButton, 20, SpringLayout.WEST, jPanel);
        twelveQueryButton.addActionListener(e->{
            TwelveQuery twelveQueryFrame = new TwelveQuery(queryController);
            twelveQueryFrame.openQueryConfig();
        });
        jPanel.add(twelveQueryButton);

        JLabel sixteenthQuery = new JLabel("Отчет №16");
        sixteenthQuery.setFont(new Font(sixteenthQuery.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, sixteenthQuery, 10, SpringLayout.SOUTH, twelveQueryButton);
        layout.putConstraint(SpringLayout.WEST, sixteenthQuery, 20, SpringLayout.WEST, jPanel);
        jPanel.add(sixteenthQuery);

        JButton sixteenthQueryButton = new JButton("<html>Список самых популярных произведений</html>");
        sixteenthQueryButton.setFont(new Font(sixteenthQueryButton.getFont().getName(), Font.BOLD, 14));
        sixteenthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 40));
        layout.putConstraint(SpringLayout.NORTH, sixteenthQueryButton, 10, SpringLayout.SOUTH, sixteenthQuery);
        layout.putConstraint(SpringLayout.WEST, sixteenthQueryButton, 20, SpringLayout.WEST, jPanel);
        sixteenthQueryButton.addActionListener(e->{
            SixteenthQuery sixteenthQueryFrame = new SixteenthQuery(queryController);
            sixteenthQueryFrame.openQueryConfig();
        });
        jPanel.add(sixteenthQueryButton);

        JLabel forthQuery = new JLabel("Отчет №4");
        forthQuery.setFont(new Font(forthQuery.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, forthQuery, 10, SpringLayout.SOUTH, sixteenthQueryButton);
        layout.putConstraint(SpringLayout.WEST, forthQuery, 20, SpringLayout.WEST, jPanel);
        jPanel.add(forthQuery);

        JButton forthQueryButton = new JButton("<html>Перечень читателей, которые в течение указанного промежутка" +
                " времени получали<br>издание с некоторым произведением</html>");
        forthQueryButton.setFont(new Font(forthQueryButton.getFont().getName(), Font.BOLD, 14));
        forthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 70));
        layout.putConstraint(SpringLayout.NORTH, forthQueryButton, 10, SpringLayout.SOUTH, forthQuery);
        layout.putConstraint(SpringLayout.WEST, forthQueryButton, 20, SpringLayout.WEST, jPanel);
        forthQueryButton.addActionListener(e -> {
            ForthQuery forthQueryFrame = new ForthQuery(queryController);
            forthQueryFrame.openQueryConfig();
        });
        jPanel.add(forthQueryButton);

        JLabel fifthQueryLabel = new JLabel("Отчет №5");
        fifthQueryLabel.setFont(new Font(fifthQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, fifthQueryLabel, 10, SpringLayout.SOUTH, forthQueryButton);
        layout.putConstraint(SpringLayout.WEST, fifthQueryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(fifthQueryLabel);

        JButton fifthQueryButton = new JButton("<html>Список изданий, которые в течение некоторого времени получал" +
                "указанный читатель из фонда библиотеки," +
                "где он зарегистрирован</html>");
        fifthQueryButton.setFont(new Font(fifthQueryButton.getFont().getName(), Font.BOLD, 14));
        fifthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 75));
        layout.putConstraint(SpringLayout.NORTH, fifthQueryButton, 10, SpringLayout.SOUTH, fifthQueryLabel);
        layout.putConstraint(SpringLayout.WEST, fifthQueryButton, 20, SpringLayout.WEST, jPanel);
        fifthQueryButton.addActionListener(e -> {
            FifthQuery fifthQueryFrame = new FifthQuery(queryController);
            fifthQueryFrame.openQueryConfig();
        });
        jPanel.add(fifthQueryButton);


        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

package gui.queryWindow;

import controllers.QueryController;
import gui.queryWindow.query1.FirstQuery;
import gui.queryWindow.query10.TenthQuery;
import gui.queryWindow.query12.TwelveQuery;
import gui.queryWindow.query16.SixteenthQuery;
import gui.queryWindow.query2.SecondQuery;
import gui.queryWindow.query3.ThirdQuery;
import gui.queryWindow.query4.ForthQuery;
import gui.queryWindow.query5.FifthQuery;
import gui.queryWindow.query6.SixthQuery;
import gui.queryWindow.query7.SeventhQuery;
import gui.queryWindow.query8.EighthQuery;
import gui.queryWindow.query9.NinthQuery;

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
        firstQuery.setFont(new Font(firstQuery.getFont().getName(), Font.PLAIN, 14));
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
        secondQuery.setFont(new Font(secondQuery.getFont().getName(), Font.PLAIN, 14));
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
        thirdQuery.setFont(new Font(thirdQuery.getFont().getName(), Font.PLAIN, 14));
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
        twelveQueryButton.setFont(new Font(twelveQueryButton.getFont().getName(), Font.PLAIN, 14));
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
        sixteenthQueryButton.setFont(new Font(sixteenthQueryButton.getFont().getName(), Font.PLAIN, 14));
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
        forthQueryButton.setFont(new Font(forthQueryButton.getFont().getName(), Font.PLAIN, 14));
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
                " где он зарегистрирован</html>");
        fifthQueryButton.setFont(new Font(fifthQueryButton.getFont().getName(), Font.PLAIN, 14));
        fifthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 75));
        layout.putConstraint(SpringLayout.NORTH, fifthQueryButton, 10, SpringLayout.SOUTH, fifthQueryLabel);
        layout.putConstraint(SpringLayout.WEST, fifthQueryButton, 20, SpringLayout.WEST, jPanel);
        fifthQueryButton.addActionListener(e -> {
            FifthQuery fifthQueryFrame = new FifthQuery(queryController);
            fifthQueryFrame.openQueryConfig();
        });
        jPanel.add(fifthQueryButton);

        JLabel sixthQueryLabel = new JLabel("Отчет №6");
        sixthQueryLabel.setFont(new Font(sixthQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, sixthQueryLabel, 10, SpringLayout.SOUTH, fifthQueryButton);
        layout.putConstraint(SpringLayout.WEST, sixthQueryLabel, 20, SpringLayout.WEST, jPanel);
        jPanel.add(sixthQueryLabel);

        JButton sixthQueryButton = new JButton("<html>Список изданий, которые в течение некоторого времени получал" +
                "указанный читатель из фонда библиотеки, " +
                "где он <b>не зарегистрирован</b></html>");
        sixthQueryButton.setFont(new Font(sixthQueryButton.getFont().getName(), Font.PLAIN, 14));
        sixthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2 - 20, 75));
        layout.putConstraint(SpringLayout.NORTH, sixthQueryButton, 10, SpringLayout.SOUTH, sixthQueryLabel);
        layout.putConstraint(SpringLayout.WEST, sixthQueryButton, 20, SpringLayout.WEST, jPanel);
        sixthQueryButton.addActionListener(e -> {
            SixthQuery sixthQueryFrame = new SixthQuery(queryController);
            sixthQueryFrame.openQueryConfig();
        });
        jPanel.add(sixthQueryButton);


        //Второй столбец отчетов
        JLabel tenthQueryLabel = new JLabel("Отчет №10");
        tenthQueryLabel.setFont(new Font(tenthQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, tenthQueryLabel, 10, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, tenthQueryLabel, 10, SpringLayout.EAST, firstQuery);
        jPanel.add(tenthQueryLabel);

        JButton tenthQueryButton = new JButton("<html>Список читателей с просроченным сроком литературы</html>");
        tenthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2-40, 30));
        tenthQueryButton.setFont(new Font(tenthQueryButton.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.NORTH, tenthQueryButton, 10, SpringLayout.SOUTH, tenthQueryLabel);
        layout.putConstraint(SpringLayout.WEST, tenthQueryButton, 10, SpringLayout.EAST, firstQuery);
        tenthQueryButton.addActionListener(e -> {
            TenthQuery tenthQueryFrame = new TenthQuery(queryController);
            tenthQueryFrame.openQueryConfig();
        });
        jPanel.add(tenthQueryButton);


        JLabel seventhQueryLabel = new JLabel("Отчет №7");
        seventhQueryLabel.setFont(new Font(seventhQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, seventhQueryLabel, 10, SpringLayout.SOUTH, tenthQueryButton);
        layout.putConstraint(SpringLayout.WEST, seventhQueryLabel, 10, SpringLayout.EAST, firstQuery);
        jPanel.add(seventhQueryLabel);

        JButton seventhQueryButton = new JButton("<html>Список литературы, которая в настоящий момент выдана с определенной полки некоторой библиотеки</html>");
        seventhQueryButton.setPreferredSize(new Dimension(this.getWidth()/2-40, 40));
        seventhQueryButton.setFont(new Font(seventhQueryButton.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.NORTH, seventhQueryButton, 10, SpringLayout.SOUTH, seventhQueryLabel);
        layout.putConstraint(SpringLayout.WEST, seventhQueryButton, 10, SpringLayout.EAST, firstQuery);
        seventhQueryButton.addActionListener(e->{
            SeventhQuery seventhQueryFrame = new SeventhQuery(queryController);
            seventhQueryFrame.openQueryConfig();
        });
        jPanel.add(seventhQueryButton);


        JLabel eighthQueryLabel = new JLabel("Отчет №8");
        eighthQueryLabel.setFont(new Font(eighthQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, eighthQueryLabel, 10, SpringLayout.SOUTH, seventhQueryButton);
        layout.putConstraint(SpringLayout.WEST, eighthQueryLabel, 10, SpringLayout.EAST, firstQuery);
        jPanel.add(eighthQueryLabel);

        JButton eighthQueryButton = new JButton("<html>Список читателей, которые в течение обозначенного периода были обслужены указанным библиотекарем</html>");
        eighthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2-40, 40));
        eighthQueryButton.setFont(new Font(eighthQueryButton.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.NORTH, eighthQueryButton, 10, SpringLayout.SOUTH, eighthQueryLabel);
        layout.putConstraint(SpringLayout.WEST, eighthQueryButton, 10, SpringLayout.EAST, firstQuery);
        eighthQueryButton.addActionListener(e->{
            EighthQuery eighthQueryFrame = new EighthQuery(queryController);
            eighthQueryFrame.openQueryConfig();
        });
        jPanel.add(eighthQueryButton);


        JLabel ninthQueryLabel = new JLabel("Отчет №9");
        ninthQueryLabel.setFont(new Font(ninthQueryLabel.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.NORTH, ninthQueryLabel, 10, SpringLayout.SOUTH, eighthQueryButton);
        layout.putConstraint(SpringLayout.WEST, ninthQueryLabel, 10, SpringLayout.EAST, firstQuery);
        jPanel.add(ninthQueryLabel);

        JButton ninthQueryButton = new JButton("<html>Данные о выработке библиотекарей (число обслуженных читателей в указанный период времени)</html>");
        ninthQueryButton.setPreferredSize(new Dimension(this.getWidth()/2-40, 40));
        ninthQueryButton.setFont(new Font(ninthQueryButton.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.NORTH, ninthQueryButton, 10, SpringLayout.SOUTH, ninthQueryLabel);
        layout.putConstraint(SpringLayout.WEST, ninthQueryButton, 10, SpringLayout.EAST, firstQuery);
        ninthQueryButton.addActionListener(e->{
            NinthQuery ninthQueryFrame = new NinthQuery(queryController);
            ninthQueryFrame.openQueryConfig();
        });
        jPanel.add(ninthQueryButton);


        JButton exit = new JButton("Выйти");
        exit.setFont(new Font(exit.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, exit, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, exit, -10, SpringLayout.EAST, jPanel);
        exit.addActionListener(e-> this.setVisible(false));
        jPanel.add(exit);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

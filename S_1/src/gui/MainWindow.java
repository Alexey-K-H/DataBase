package gui;

import connection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    DBConnection connection;
    String url;

    public MainWindow(DBConnection connection, String url){
        this.connection = connection;
        this.url = url;
    }

    public void run(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    connection.initSchema();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                ConnectionFrame connectionFrame = new ConnectionFrame();
                connectionFrame.singIn();
            }
        });


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 500, dimension.height/2 - 400, 1000, 800);
        this.setTitle(url);

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        this.add(panel);

        JLabel info = new JLabel("<html>Схема базы данных библиотечного фонда</html>");
        Font labelFont = info.getFont();
        info.setFont(new Font(labelFont.getName(), Font.BOLD, 24));
        layout.putConstraint(SpringLayout.WEST, info, this.getWidth()/2 - info.getPreferredSize().width/2, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, panel);
        panel.add(info);

        //Кнопка-таблица
        JButton librariesButton = new JButton("Библиотеки");
        librariesButton.setFont(new Font(librariesButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.WEST, librariesButton, this.getWidth()/2 - librariesButton.getPreferredSize().width/2, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, librariesButton, 40, SpringLayout.SOUTH, info);
        panel.add(librariesButton);


        JButton librariansButton = new JButton("Библиотекари");
        librariansButton.setFont(new Font(librariansButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.WEST, librariansButton, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, librariansButton, 40, SpringLayout.SOUTH, librariesButton);
        panel.add(librariansButton);


        JButton productionsButton = new JButton("Произведения");
        productionsButton.setFont(new Font(productionsButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, productionsButton, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, productionsButton, 40, SpringLayout.SOUTH, librariesButton);
        panel.add(productionsButton);


        JButton readersButton = new JButton("Читатели");
        readersButton.setFont(new Font(readersButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.WEST, readersButton, 50, SpringLayout.EAST, librariansButton);
        layout.putConstraint(SpringLayout.NORTH, readersButton, 40, SpringLayout.SOUTH, librariansButton);
        panel.add(readersButton);

        JLabel readersCategory = new JLabel("<html>Выбрать категорию читателей:</html>");
        readersCategory.setFont(new Font(readersCategory.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, readersCategory, 10, SpringLayout.SOUTH, readersButton);
        layout.putConstraint(SpringLayout.WEST, readersCategory, 50, SpringLayout.EAST, librariansButton);
        panel.add(readersCategory);

        //Кнопки-категории
        JButton teacherButton = new JButton("Учителя");
        teacherButton.setFont(new Font(teacherButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, teacherButton, 10, SpringLayout.SOUTH, readersCategory);
        layout.putConstraint(SpringLayout.WEST, teacherButton, 50, SpringLayout.EAST, librariansButton);
        panel.add(teacherButton);


        JButton researchersButton = new JButton("Научные сотрудники");
        researchersButton.setFont(new Font(researchersButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, researchersButton, 10, SpringLayout.SOUTH, teacherButton);
        layout.putConstraint(SpringLayout.WEST, researchersButton, 50, SpringLayout.EAST, librariansButton);
        panel.add(researchersButton);


        JButton schoolchildButton = new JButton("Школьники");
        schoolchildButton.setFont(new Font(schoolchildButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, schoolchildButton, 10, SpringLayout.SOUTH, researchersButton);
        layout.putConstraint(SpringLayout.WEST, schoolchildButton, 50, SpringLayout.EAST, librariansButton);
        panel.add(schoolchildButton);


        JButton studentButton = new JButton("Студенты");
        studentButton.setFont(new Font(studentButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, studentButton, 10, SpringLayout.SOUTH, schoolchildButton);
        layout.putConstraint(SpringLayout.WEST, studentButton, 50, SpringLayout.EAST, librariansButton);
        panel.add(studentButton);


        JButton pensionersButton = new JButton("Пенсионеры");
        pensionersButton.setFont(new Font(pensionersButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, pensionersButton, 10, SpringLayout.SOUTH, studentButton);
        layout.putConstraint(SpringLayout.WEST, pensionersButton, 50, SpringLayout.EAST, librariansButton);
        panel.add(pensionersButton);


        JButton workersButton = new JButton("Работники");
        workersButton.setFont(new Font(workersButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, workersButton, 10, SpringLayout.SOUTH, pensionersButton);
        layout.putConstraint(SpringLayout.WEST, workersButton, 50, SpringLayout.EAST, librariansButton);
        panel.add(workersButton);


        JButton editionsButton = new JButton("Издания");
        editionsButton.setFont(new Font(editionsButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, editionsButton, -50, SpringLayout.WEST, productionsButton);
        layout.putConstraint(SpringLayout.NORTH, editionsButton, 40, SpringLayout.SOUTH, librariansButton);
        panel.add(editionsButton);

        JButton issuedBooks = new JButton("Выданные книги");
        issuedBooks.setFont(new Font(issuedBooks.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.WEST, issuedBooks, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, issuedBooks, 40, SpringLayout.SOUTH, workersButton);
        panel.add(issuedBooks);

        JButton rulesButton = new JButton("<html>Правила пользования<br>кнгиами</html>");
        rulesButton.setFont(new Font(rulesButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, rulesButton, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, rulesButton, 30, SpringLayout.SOUTH, workersButton);
        panel.add(rulesButton);

        JButton exit = new JButton("Выйти из фонда");
        exit.setFont(new Font(exit.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, exit, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, exit, 20, SpringLayout.SOUTH, rulesButton);
        panel.add(exit);

        exit.addActionListener(e -> {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
            ConnectionFrame connectionFrame = new ConnectionFrame();
            connectionFrame.singIn();
        });

        this.setResizable(false);
        this.setVisible(true);
    }
}

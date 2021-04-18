package gui;

import connection.DBConnection;
import controllers.QueryController;
import controllers.TableController;
import gui.menuButtons.*;
import gui.menuButtons.orderLiterature.OrderLitFrame;
import gui.queryWindow.MainQueryWindow;
import gui.signIn.ConnectionFrame;
import gui.tablesView.TableFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    private final DBConnection connection;
    private final String url;
    private final UserMods userMod;
    private String userId;
    private final boolean debug;

    public MainWindow(DBConnection connection, String url, UserMods userMod, boolean debug){
        this.connection = connection;
        this.url = url;
        this.userMod = userMod;
        this.debug = debug;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void run(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if(userMod == UserMods.ADMINISTRATOR && debug){
                    try {
                        connection.initSchema();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    if(userMod == UserMods.ADMINISTRATOR){
                        JLabel beforeExit = new JLabel("Очистить схему бд перед выходом? Используется в случае отладки");
                        beforeExit.setFont(new Font(beforeExit.getFont().getName(), Font.BOLD, 16));
                        int result = JOptionPane.showConfirmDialog(null, beforeExit, "Выход", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(result == JOptionPane.YES_OPTION){
                            connection.close();
                        }
                        else {
                            connection.getConn().close();
                        }
                    }else{
                        connection.getConn().close();
                    }
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
        if(userMod != UserMods.USER){
            this.setBounds(dimension.width/2 - 500, dimension.height/2 - 450, 1000, 900);
        }
        else{
            this.setBounds(dimension.width/2 - 500, dimension.height/2 - 150, 1000, 300);
        }

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

        String userModeStr = "";
        switch (userMod){
            case ADMINISTRATOR:{
                userModeStr = userModeStr.concat("Администратор");
                break;
            }
            case LIBRARIAN:{
                userModeStr = userModeStr.concat("Библиотекарь");
                break;
            }
            case USER:{
                userModeStr = userModeStr.concat("Читатель");
                break;
            }
        }

        JLabel userMode = new JLabel("<html>Вы вошли как:<br>"+ userModeStr + "</html>");
        userMode.setFont(new Font(userMode.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.WEST, userMode, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, userMode, 10, SpringLayout.NORTH, panel);
        panel.add(userMode);

        //Библиотеки
        Libraries lib = new Libraries();
        layout.putConstraint(SpringLayout.WEST, lib, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lib, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.EAST, lib, -this.getWidth()/2, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, lib, 90, SpringLayout.NORTH, lib);
        lib.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Libraries", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod == UserMods.ADMINISTRATOR){
            panel.add(lib);
        }


        //Залы бибилиотек
        Halls halls = new Halls();
        layout.putConstraint(SpringLayout.WEST, halls, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, halls, 20, SpringLayout.SOUTH, lib);
        layout.putConstraint(SpringLayout.EAST, halls, -this.getWidth()/2, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, halls, 90, SpringLayout.NORTH, halls);
        halls.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Halls", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            }catch (SQLException exception){
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod == UserMods.ADMINISTRATOR) {
            panel.add(halls);
        }

        //Библиотекари
        Librarians librarians = new Librarians();
        layout.putConstraint(SpringLayout.WEST, librarians, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, librarians, 10, SpringLayout.SOUTH, halls);
        layout.putConstraint(SpringLayout.EAST, librarians, -this.getWidth()/2, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, librarians, 90, SpringLayout.NORTH, librarians);
        librarians.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Librarians", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod == UserMods.ADMINISTRATOR) {
            panel.add(librarians);
        }

        //Читатели
        Readers readers = new Readers();
        layout.putConstraint(SpringLayout.WEST, readers, 5, SpringLayout.WEST, panel);
        if(userMod != UserMods.ADMINISTRATOR){
            layout.putConstraint(SpringLayout.NORTH, readers, 20, SpringLayout.SOUTH, info);
        }
        else{
            layout.putConstraint(SpringLayout.NORTH, readers, 10, SpringLayout.SOUTH, librarians);
        }
        layout.putConstraint(SpringLayout.EAST, readers, -this.getWidth()/2, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, readers, 100, SpringLayout.NORTH, readers);
        readers.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Readers", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod != UserMods.USER){
            panel.add(readers);
        }

        //Кнопки-категории
        CategoryReaders categoryReaders = new CategoryReaders(connection);
        layout.putConstraint(SpringLayout.NORTH, categoryReaders, 10, SpringLayout.SOUTH, readers);
        layout.putConstraint(SpringLayout.WEST, categoryReaders, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, categoryReaders, -this.getWidth()/2, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, categoryReaders, 320, SpringLayout.NORTH, categoryReaders);
        if(userMod != UserMods.USER) {
            panel.add(categoryReaders);
        }

        Productions productions = new Productions();
        layout.putConstraint(SpringLayout.EAST, productions, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, productions, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, productions, -this.getWidth()/2 + 20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, productions, 180, SpringLayout.NORTH, productions);
        productions.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Compositions", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod != UserMods.USER) {
            panel.add(productions);
        }

        Editions editions = new Editions();
        layout.putConstraint(SpringLayout.EAST, editions, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, editions, 10, SpringLayout.SOUTH, productions);
        layout.putConstraint(SpringLayout.WEST, editions, -this.getWidth()/2 + 20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, editions, 180, SpringLayout.NORTH, editions);
        editions.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Editions", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod != UserMods.USER) {
            panel.add(editions);
        }

        IssuedBooksAndTerms issuedBooksAndTerms = new IssuedBooksAndTerms();
        if(userMod == UserMods.USER){
            layout.putConstraint(SpringLayout.WEST, issuedBooksAndTerms, 20, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, issuedBooksAndTerms, 10, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.EAST, issuedBooksAndTerms, this.getWidth()/2 - 20, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.SOUTH, issuedBooksAndTerms, 130, SpringLayout.NORTH, issuedBooksAndTerms);
        }else {
            layout.putConstraint(SpringLayout.EAST, issuedBooksAndTerms, -20, SpringLayout.EAST, panel);
            layout.putConstraint(SpringLayout.NORTH, issuedBooksAndTerms, 10, SpringLayout.SOUTH, editions);
            layout.putConstraint(SpringLayout.WEST, issuedBooksAndTerms, -this.getWidth()/2 + 20, SpringLayout.EAST, panel);
            layout.putConstraint(SpringLayout.SOUTH, issuedBooksAndTerms, 210, SpringLayout.NORTH, issuedBooksAndTerms);
        }

        issuedBooksAndTerms.getOpenButton().addActionListener(e -> {
            TableController tableController = new TableController("Issued_Books", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            if(userMod == UserMods.USER){
                tableFrame.setUserId(userId);
            }
            try {
                tableFrame.openTable(userMod == UserMods.USER);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка! " + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        issuedBooksAndTerms.getRulesButton().addActionListener(e -> {
            TableController tableController = new TableController("Rules", connection);
            TableFrame tableFrame = new TableFrame(tableController);
            try {
                tableFrame.openTable(false);
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка!" + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        if(userMod == UserMods.USER){
            issuedBooksAndTerms.removeRules();
        }
        panel.add(issuedBooksAndTerms);

        if(userMod == UserMods.USER){
            OrderingLiterature orderingLiterature = new OrderingLiterature();
            layout.putConstraint(SpringLayout.NORTH, orderingLiterature, 10, SpringLayout.SOUTH, info);
            layout.putConstraint(SpringLayout.EAST, orderingLiterature, -20, SpringLayout.EAST, panel);
            layout.putConstraint(SpringLayout.WEST, orderingLiterature, -this.getWidth()/2 + 20, SpringLayout.EAST, panel);
            layout.putConstraint(SpringLayout.SOUTH, orderingLiterature, 130, SpringLayout.NORTH, orderingLiterature);

            orderingLiterature.getOpenButton().addActionListener(e -> {
                QueryController queryController = new QueryController(connection);
                OrderLitFrame orderLitFrame = new OrderLitFrame(userId, queryController);
                orderLitFrame.openOrderForm();
            });

            panel.add(orderingLiterature);
        }

        JButton select = new JButton("Отчеты по пользователям");
        select.setFont(new Font(select.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, select, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, select, 20, SpringLayout.SOUTH, issuedBooksAndTerms);
        select.addActionListener(e -> {
            QueryController queryController = new QueryController(connection);
            MainQueryWindow mainQueryWindow = new MainQueryWindow(queryController);
            mainQueryWindow.openQueryConsole();
        });
        if(userMod != UserMods.USER) {
            panel.add(select);
        }

        JButton exit = new JButton("Выйти из фонда");
        exit.setFont(new Font(exit.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, exit, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, exit, -20, SpringLayout.SOUTH, panel);
        panel.add(exit);

        exit.addActionListener(e -> {
            try {
                if(userMod == UserMods.ADMINISTRATOR){
                    JLabel beforeExit = new JLabel("Очистить схему бд перед выходом? Используется в случае отладки");
                    beforeExit.setFont(new Font(beforeExit.getFont().getName(), Font.BOLD, 16));
                    int result = JOptionPane.showConfirmDialog(null, beforeExit, "Выход", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        connection.close();
                    }
                    else {
                        connection.getConn().close();
                    }
                }else{
                    connection.getConn().close();
                }
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

package gui.signIn;

import connection.DBConnection;
import gui.MainWindow;
import gui.UserMods;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFrame extends JFrame {
    public void singIn(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 150, dimension.height/2 - 300, 300, 600);
        this.setTitle("Вход");

        SpringLayout layout = new SpringLayout();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel connectionInfo = new JLabel("Список доступных соединений");
        Font labelFont = connectionInfo.getFont();
        connectionInfo.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, connectionInfo, 10, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, connectionInfo, 15, SpringLayout.WEST, jPanel);
        jPanel.add(connectionInfo);

        JButton nsu = new JButton("NSU server:@84.237.50.81");
        nsu.setFont(new Font(nsu.getFont().getName(), Font.BOLD, 16));
        nsu.addActionListener(e -> {
            String url = "jdbc:oracle:thin:@84.237.50.81:1521:XE";
            Properties props = new Properties();
            props.setProperty("user", "18204_KHOROSHAVIN");
            props.setProperty("password", "442768");
            this.setVisible(false);
            UserModeSelection userModeSelection = new UserModeSelection(nsu.getText(), props, url);
            userModeSelection.openSelectionPane();
        });
        layout.putConstraint(SpringLayout.NORTH, nsu, 10, SpringLayout.SOUTH, connectionInfo);
        layout.putConstraint(SpringLayout.WEST, nsu, 10, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.EAST, nsu, -10, SpringLayout.EAST, jPanel);
        jPanel.add(nsu);

        JButton localhost = new JButton("localhost server:@127.0.0.1");
        localhost.setFont(new Font(localhost.getFont().getName(), Font.BOLD, 16));
        localhost.addActionListener(e -> {
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            Properties props = new Properties();
            props.setProperty("user", "c##alexey");
            props.setProperty("password", "nsu");
            this.setVisible(false);
            UserModeSelection userModeSelection = new UserModeSelection(localhost.getText(), props, url);
            userModeSelection.openSelectionPane();
        });

        layout.putConstraint(SpringLayout.NORTH, localhost, 10, SpringLayout.SOUTH, nsu);
        layout.putConstraint(SpringLayout.WEST, localhost, 10, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.EAST, localhost, -10, SpringLayout.EAST, jPanel);
        jPanel.add(localhost);

        JLabel userConnectInfo = new JLabel("<html>Персональные настройки<br>пользователя:</html>");
        userConnectInfo.setFont(new Font(userConnectInfo.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, userConnectInfo, 20, SpringLayout.SOUTH, localhost);
        layout.putConstraint(SpringLayout.WEST, userConnectInfo, 15, SpringLayout.WEST, jPanel);
        jPanel.add(userConnectInfo);

        JLabel ipAddress = new JLabel("IP адрес сервера");
        ipAddress.setFont(new Font(ipAddress.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, ipAddress, 10, SpringLayout.SOUTH, userConnectInfo);
        layout.putConstraint(SpringLayout.WEST, ipAddress, 15, SpringLayout.WEST, jPanel);
        jPanel.add(ipAddress);

        JTextField ipValue = new JTextField(15);
        ipValue.setFont(new Font(ipValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, ipValue, 10, SpringLayout.SOUTH, ipAddress);
        layout.putConstraint(SpringLayout.WEST, ipValue, 20, SpringLayout.WEST, jPanel);
        jPanel.add(ipValue);

        JLabel sidLabel = new JLabel("SID");
        sidLabel.setFont(new Font(sidLabel.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, sidLabel, 10, SpringLayout.SOUTH, ipValue);
        layout.putConstraint(SpringLayout.WEST, sidLabel, 15, SpringLayout.WEST, jPanel);
        jPanel.add(sidLabel);

        JTextField sidValue = new JTextField(5);
        sidValue.setText("XE");
        sidValue.setFont(new Font(sidValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, sidValue, 10, SpringLayout.SOUTH, sidLabel);
        layout.putConstraint(SpringLayout.WEST, sidValue, 20, SpringLayout.WEST, jPanel);
        jPanel.add(sidValue);

        JLabel login = new JLabel("Логин");
        login.setFont(new Font(login.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, login, 10, SpringLayout.SOUTH, sidValue);
        layout.putConstraint(SpringLayout.WEST, login, 15, SpringLayout.WEST, jPanel);
        jPanel.add(login);

        JTextField loginValue = new JTextField(15);
        loginValue.setFont(new Font(loginValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, loginValue, 10, SpringLayout.SOUTH, login);
        layout.putConstraint(SpringLayout.WEST, loginValue, 20, SpringLayout.WEST, jPanel);
        jPanel.add(loginValue);

        JLabel password = new JLabel("Пароль");
        password.setFont(new Font(password.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, password, 10, SpringLayout.SOUTH, loginValue);
        layout.putConstraint(SpringLayout.WEST, password, 15, SpringLayout.WEST, jPanel);
        jPanel.add(password);

        JPasswordField passwordValue = new JPasswordField(15);
        passwordValue.setFont(new Font(passwordValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, passwordValue, 10, SpringLayout.SOUTH, password);
        layout.putConstraint(SpringLayout.WEST, passwordValue, 20, SpringLayout.WEST, jPanel);
        jPanel.add(passwordValue);

        JButton singIn = new JButton("<html>Войти<br>с пользовательскими<br>настройками</html>");
        singIn.setFont(new Font(singIn.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.WEST, singIn, 30, SpringLayout.WEST, jPanel);
        layout.putConstraint(SpringLayout.EAST, singIn, -30, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.NORTH, singIn, 20, SpringLayout.SOUTH, passwordValue);
        singIn.addActionListener(e -> {
            StringBuilder pwd = new StringBuilder();
            for(int i = 0; i < passwordValue.getPassword().length; i++){
                pwd.append(passwordValue.getPassword()[i]);
            }

            String url = "jdbc:oracle:thin:@" + ipValue.getText() + ":1521:" + sidValue.getText();
            Properties props = new Properties();
            props.setProperty("user", loginValue.getText());
            props.setProperty("password", pwd.toString());
            DBConnection connection;
            try {
                connection = new DBConnection(url, props);
                this.setVisible(false);
                MainWindow mainWindow = new MainWindow(connection, localhost.getText(), UserMods.ADMINISTRATOR);
                mainWindow.run();
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка подключения! " + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        jPanel.add(singIn);

        this.setResizable(false);
        this.setVisible(true);
    }
}

package gui;

import connection.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFrame extends JFrame {
    public void singIn(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 150, dimension.height/2 - 90, 300, 180);
        this.setTitle("Вход");

        JPanel jPanel = new JPanel();
        this.add(jPanel);

        JLabel connectionInfo = new JLabel("Список доступных соединений");
        Font labelFont = connectionInfo.getFont();
        connectionInfo.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        jPanel.add(connectionInfo);

        JButton nsu = new JButton("NSU server:@84.237.50.81");
        nsu.setFont(new Font(nsu.getFont().getName(), Font.BOLD, 16));
        nsu.addActionListener(e -> {
            try {
                String url = "jdbc:oracle:thin:@84.237.50.81:1521:XE";
                Properties props = new Properties();
                props.setProperty("user", "18204_KHOROSHAVIN");
                props.setProperty("password", "442768");
                DBConnection connection = new DBConnection(url, props);
                this.setVisible(false);
                Window window = new Window(connection, nsu.getText());
                window.run();
            } catch (SQLException ex) {
                JLabel error = new JLabel("Ошибка подключения! " + ex.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        jPanel.add(nsu);

        JButton localhost = new JButton("localhost server:@127.0.0.1");
        localhost.setFont(new Font(localhost.getFont().getName(), Font.BOLD, 16));
        localhost.addActionListener(e -> {
            try {
                String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
                Properties props = new Properties();
                props.setProperty("user", "c##alexey");
                props.setProperty("password", "nsu");
                DBConnection connection = new DBConnection(url, props);
                this.setVisible(false);
                Window window = new Window(connection, localhost.getText());
                window.run();
            } catch (SQLException ex) {
                JLabel error = new JLabel("Ошибка подключения! " + ex.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        jPanel.add(localhost);

        this.setVisible(true);
    }
}

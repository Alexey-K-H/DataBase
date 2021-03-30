package gui;

import connection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

public class Window extends JFrame {
    DBConnection connection;
    String url;

    public Window(DBConnection connection, String url){
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
        this.setBounds(dimension.width/2 - 250, dimension.height/2 - 90, 500, 180);
        this.setTitle(url);

        JPanel panel = new JPanel();
        this.add(panel);
        JLabel info = new JLabel("<html>Нажмите кнопку, <br>чтобы увидеть число записей в таблице: \"Libraries\" </html>");
        Font labelFont = info.getFont();
        info.setFont(new Font(labelFont.getName(), Font.BOLD, 16));


        JButton button = new JButton("Чило записей");
        button.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        button.addActionListener(e -> {
            try {
                JLabel result = new JLabel("Число записей в таблице: " + connection.performSQLQuery());
                result.setFont(new Font(result.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, result, "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLSyntaxErrorException ex){
                JLabel error = new JLabel("Ошибка! " + ex.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(info);
        panel.add(button);

        this.setVisible(true);
    }
}

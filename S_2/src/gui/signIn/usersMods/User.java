package gui.signIn.usersMods;

import connection.DBConnection;
import gui.MainWindow;
import gui.UserMods;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class User extends UserMod{

    public User(String nameServer, Properties properties, String url) {
        super(nameServer, properties, url);
    }

    @Override
    public boolean checkSecurity(String identity, String key) {
        return false;
    }

    @Override
    public void openSecurityCheckWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 150, dimension.height/2 - 120, 300, 240);
        this.setTitle("Вход как читатель");

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        this.add(panel);

        JLabel info = new JLabel("<html>Введите идентификатор<br>читателя.<br>ID-числовая последовательность</html>");
        Font labelFont = info.getFont();
        info.setFont(new Font(labelFont.getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, info, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, panel);
        panel.add(info);

        JPasswordField passwordValue = new JPasswordField(15);
        passwordValue.setFont(new Font(passwordValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, passwordValue, 10, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, passwordValue, 20, SpringLayout.WEST, panel);
        panel.add(passwordValue);

        JButton confirm = new JButton("Подтвердить");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, confirm, 20, SpringLayout.SOUTH, passwordValue);
        layout.putConstraint(SpringLayout.WEST, confirm, 30, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, confirm, -30, SpringLayout.EAST, panel);
        confirm.addActionListener(e -> {
            StringBuilder pwd = new StringBuilder();
            for(int i = 0; i < passwordValue.getPassword().length; i++){
                pwd.append(passwordValue.getPassword()[i]);
            }

            try {
                DBConnection connection = new DBConnection(getUrl(), getProperties());
                PreparedStatement preStatement = connection.getConn().prepareStatement("select count(*) from READERS where ID_READER = " + pwd.toString());
                ResultSet resultSet = preStatement.executeQuery();

                int count = 0;
                while (resultSet.next()){
                    count = resultSet.getInt(1);
                }

                resultSet.close();

                if(count > 0){
                    System.out.println("Success!");
                    this.setVisible(false);
                    connection.getConn().setReadOnly(true);
                    MainWindow mainWindow = new MainWindow(connection, getNameServer(), UserMods.USER, false);
                    mainWindow.setUserId(pwd.toString());
                    mainWindow.run();
                }
                else {
                    connection.getConn().close();
                    throw new SQLException("Неверный идентификатор читателя!");
                }
            } catch (SQLException exception) {
                JLabel error = new JLabel("Ошибка подключения! " + exception.getMessage());
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(confirm);

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

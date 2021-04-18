package gui.signIn.usersMods;

import connection.DBConnection;
import gui.MainWindow;
import gui.UserMods;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Properties;

public class Admin extends UserMod{
    public Admin(String nameServer, Properties properties, String url) {
        super(nameServer, properties, url);
    }

    @Override
    public boolean checkSecurity(String identity, String key){
        return identity.equals(key);
    }

    @Override
    public void openSecurityCheckWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 150, dimension.height/2 - 120, 300, 240);
        this.setTitle("Вход как администратор");

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        this.add(panel);


        JLabel info = new JLabel("<html>Введите пароль<br>администратора</html>");
        Font labelFont = info.getFont();
        info.setFont(new Font(labelFont.getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, info, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, panel);
        panel.add(info);

        JPasswordField passwordValue = new JPasswordField(15);
        passwordValue.setText(getProperties().getProperty("password"));
        passwordValue.setFont(new Font(passwordValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, passwordValue, 10, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, passwordValue, 20, SpringLayout.WEST, panel);
        panel.add(passwordValue);

        JCheckBox initSchema = new JCheckBox("<html>Создать начальную схему<br>(Использовать в случае отладки!)</html>");
        layout.putConstraint(SpringLayout.NORTH, initSchema, 10, SpringLayout.SOUTH, passwordValue);
        layout.putConstraint(SpringLayout.WEST, initSchema, 20, SpringLayout.WEST, panel);
        panel.add(initSchema);

        JButton confirm = new JButton("Подтвердить");
        confirm.setFont(new Font(confirm.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, confirm, -10, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.WEST, confirm, 30, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, confirm, -30, SpringLayout.EAST, panel);
        confirm.addActionListener(e -> {
            StringBuilder pwd = new StringBuilder();
            for(int i = 0; i < passwordValue.getPassword().length; i++){
                pwd.append(passwordValue.getPassword()[i]);
            }

            try {
                if(checkSecurity(pwd.toString(), getProperties().getProperty("password"))){
                    System.out.println("Success!");
                    this.setVisible(false);
                    boolean debug;
                    if(initSchema.isSelected()){
                        System.out.println("Checked");
                        debug = true;
                    }
                    else {
                        System.out.println("Unchecked");
                        debug = false;
                    }
                    DBConnection connection = new DBConnection(getUrl(), getProperties());
                    MainWindow mainWindow = new MainWindow(connection, getNameServer(), UserMods.ADMINISTRATOR, debug);
                    mainWindow.run();
                }
                else {
                    throw new SQLException("Неверный пароль администратора!");
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

package gui.signIn;

import gui.signIn.usersMods.Admin;
import gui.signIn.usersMods.Librarian;
import gui.signIn.usersMods.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Properties;

public class UserModeSelection extends JDialog {
    private final String nameServer;
    private final Properties properties;
    private final String url;

    public UserModeSelection(String nameServer, Properties properties, String url) {
        this.properties = properties;
        this.nameServer = nameServer;
        this.url = url;
    }

    public void openSelectionPane(){
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                setVisible(false);
                ConnectionFrame connectionFrame = new ConnectionFrame();
                connectionFrame.singIn();
            }
        });

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 150, dimension.height/2 - 120, 300, 240);
        this.setTitle(nameServer);

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        this.add(panel);

        JLabel info = new JLabel("<html>Войти как:</html>");
        Font labelFont = info.getFont();
        info.setFont(new Font(labelFont.getName(), Font.PLAIN, 24));
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, info, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, panel);
        panel.add(info);

        String[] items = {
                "Администратор",
                "Библиотекарь",
                "Читатель"
        };

        JComboBox<String> userModeField = new JComboBox<>(items);
        userModeField.setFont(new Font(userModeField.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, userModeField, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, userModeField, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, userModeField, -20, SpringLayout.EAST, panel);
        panel.add(userModeField);

        JButton singIn = new JButton("Войти");
        singIn.setFont(new Font(singIn.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, singIn, 40, SpringLayout.SOUTH, userModeField);
        layout.putConstraint(SpringLayout.WEST, singIn, 40, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, singIn, -40, SpringLayout.EAST, panel);
        panel.add(singIn);

        singIn.addActionListener(e -> {
            switch (Objects.requireNonNull(userModeField.getSelectedItem()).toString()){
                case "Администратор":{
                    this.setVisible(false);
                    Admin admin = new Admin(nameServer, properties, url);
                    admin.openSecurityCheckWindow();
                    break;
                }
                case "Библиотекарь":{
                    this.setVisible(false);
                    Librarian librarian = new Librarian(nameServer, properties, url);
                    librarian.openSecurityCheckWindow();
                    break;
                }
                case "Читатель":{
                    this.setVisible(false);
                    User user = new User(nameServer, properties, url);
                    user.openSecurityCheckWindow();
                    break;
                }
            }
        });

        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

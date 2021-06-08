package gui.signIn.usersMods;
import gui.signIn.ConnectionFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

public abstract class UserMod extends JDialog {
    private final String nameServer;
    private final Properties properties;
    private final String url;

    protected UserMod(String nameServer, Properties properties, String url) {
        this.nameServer = nameServer;
        this.properties = properties;
        this.url = url;

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                setVisible(false);
                ConnectionFrame connectionFrame = new ConnectionFrame();
                connectionFrame.singIn();
            }
        });
    }

    public String getNameServer() {
        return nameServer;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getUrl() {
        return url;
    }

    public abstract void openSecurityCheckWindow();
}

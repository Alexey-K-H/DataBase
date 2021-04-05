package gui.signIn.usersMods;

import gui.signIn.UserModeSelection;

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
                UserModeSelection userModeSelection = new UserModeSelection(nameServer, properties, url);
                userModeSelection.openSelectionPane();
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

    public abstract boolean checkSecurity(String identity, String key);

    public abstract void openSecurityCheckWindow();
}

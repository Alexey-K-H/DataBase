import gui.signIn.ConnectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        ConnectionFrame connectionFrame = new ConnectionFrame();
        connectionFrame.singIn();
    }
}

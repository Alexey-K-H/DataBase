package gui.menuButtons;

import javax.swing.*;
import java.awt.*;

public class Librarians extends JPanel{
    private final JButton openButton;

    public Librarians() {
        SpringLayout librariansLayout = new SpringLayout();
        setLayout(librariansLayout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        openButton = new JButton("Открыть");
        openButton.setFont(new Font(openButton.getFont().getName(), Font.BOLD, 20));
        librariansLayout.putConstraint(SpringLayout.SOUTH, openButton, -5, SpringLayout.SOUTH, this);
        librariansLayout.putConstraint(SpringLayout.EAST, openButton, -10, SpringLayout.EAST, this);

        JLabel libsInfo = new JLabel("<html><b>Библиотекари</b>.Общая информация:<br>Место прикрепления<br>Номер зала библиотеки</html>");
        libsInfo.setFont(new Font(libsInfo.getFont().getName(), Font.PLAIN, 16));
        librariansLayout.putConstraint(SpringLayout.NORTH, libsInfo, 10, SpringLayout.NORTH, this);
        librariansLayout.putConstraint(SpringLayout.WEST, libsInfo, 20, SpringLayout.WEST, this);

        add(libsInfo);
        add(openButton);
    }

    public JButton getOpenButton() {
        return openButton;
    }
}

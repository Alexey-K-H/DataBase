package gui.menuButtons;

import javax.swing.*;
import java.awt.*;

public class Libraries extends JPanel {
    private final JButton openButton;
    public Libraries() {
        SpringLayout libLayout = new SpringLayout();
        setLayout(libLayout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        openButton = new JButton("Открыть");
        openButton.setFont(new Font(openButton.getFont().getName(), Font.BOLD, 20));
        libLayout.putConstraint(SpringLayout.EAST, openButton, -10, SpringLayout.EAST, this);
        libLayout.putConstraint(SpringLayout.SOUTH, openButton, -5, SpringLayout.SOUTH, this);

        JLabel libText = new JLabel("<html><b>Библиотеки</b>.Общая информация:<br>Объем фонда в каждой</html>");
        libText.setFont(new Font(libText.getFont().getName(), Font.PLAIN, 16));
        libLayout.putConstraint(SpringLayout.NORTH, libText, 10, SpringLayout.NORTH, this);
        libLayout.putConstraint(SpringLayout.WEST, libText, 20, SpringLayout.WEST, this);

        add(libText);
        add(openButton);
    }

    public JButton getOpenButton() {
        return openButton;
    }
}

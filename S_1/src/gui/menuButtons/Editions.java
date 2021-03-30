package gui.menuButtons;

import javax.swing.*;
import java.awt.*;

public class Editions extends JPanel {
    private final JButton openButton;

    public Editions() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        openButton = new JButton("Открыть");
        openButton.setFont(new Font(openButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, this.openButton, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, this.openButton, -5, SpringLayout.SOUTH, this);

        JLabel infoText = new JLabel("<html><b>Издания</b>.Общая информация:<br>Библиотека<br>Номер зала<br>Номер стеллажа<br>Номер полки<br>" +
                "Дата поступления<br>Дата списания</html>");
        infoText.setFont(new Font(infoText.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, infoText, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, infoText, 20, SpringLayout.WEST, this);

        add(this.openButton);
        add(infoText);
    }

    public JButton getOpenButton() {
        return openButton;
    }
}

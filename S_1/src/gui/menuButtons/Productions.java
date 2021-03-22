package gui.menuButtons;

import javax.swing.*;
import java.awt.*;

public class Productions extends JPanel {
    private final JButton openButton;

    public Productions() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        openButton = new JButton("Открыть");
        openButton.setFont(new Font(openButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, this.openButton, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, this.openButton, -5, SpringLayout.SOUTH, this);

        JLabel infoText = new JLabel("<html><b>Произведения</b>.Общая информация:<br>ФИО<br>Номер издания<br>Автор<br>Название<br>" +
                "Популярность<br>Жанр</html>");
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

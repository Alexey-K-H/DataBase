package gui.menuButtons;

import javax.swing.*;
import java.awt.*;

public class OrderingLiterature extends JPanel {
    private final JButton openButton;

    public OrderingLiterature(){
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel infoText = new JLabel("<html><b>Заказ литературы</b><br>Оформить заказ на получение журналов и книг из" +
                "<br>других библиотек</html>");
        infoText.setFont(new Font(infoText.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, infoText, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, infoText, 20, SpringLayout.WEST, this);

        openButton = new JButton("Заказать");
        openButton.setFont(new Font(openButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, this.openButton, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, this.openButton, 5, SpringLayout.SOUTH, infoText);

        add(openButton);
        add(infoText);
    }

    public JButton getOpenButton() {
        return openButton;
    }
}

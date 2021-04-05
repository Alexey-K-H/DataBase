package gui.menuButtons;

import javax.swing.*;
import java.awt.*;

public class IssuedBooksAndTerms extends JPanel {
    private final JButton openButton;
    private final JButton rulesButton;

    public IssuedBooksAndTerms() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel infoText = new JLabel("<html><b>Выданные книги</b>.Общая информация:<br>Сроки выдачи и возврата</html>");
        infoText.setFont(new Font(infoText.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, infoText, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, infoText, 20, SpringLayout.WEST, this);

        openButton = new JButton("Открыть");
        openButton.setFont(new Font(openButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, this.openButton, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, this.openButton, 5, SpringLayout.SOUTH, infoText);

        rulesButton = new JButton("<html>Правила пользования<br>кнгиами</html>");
        rulesButton.setFont(new Font(rulesButton.getFont().getName(), Font.BOLD, 20));
        layout.putConstraint(SpringLayout.EAST, this.rulesButton, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, this.rulesButton, -10, SpringLayout.SOUTH, this);

        add(openButton);
        add(rulesButton);
        add(infoText);
    }

    public JButton getOpenButton() {
        return openButton;
    }

    public JButton getRulesButton() {
        return rulesButton;
    }

    public void removeRules(){
        this.remove(rulesButton);
    }
}

package gui.queryWindow.query12;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;

import javax.swing.*;
import java.awt.*;

public class TwelveQuery extends QueryFrame {

    protected TwelveQuery(QueryController queryController) {
        super(queryController);
    }

    public void openQueryConfig(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск библиотекарей");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>Выберите библиотеку и зал из списка фонда<br><br>" +
                "Список имеет формат:<br>" +
                "[бибилиотека_№][зал_№]</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        String sql = "select id_edition, title from Compositions";


    }
}

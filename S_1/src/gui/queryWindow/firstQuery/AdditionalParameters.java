package gui.queryWindow.firstQuery;

import javax.swing.*;
import java.awt.*;

public class AdditionalParameters extends JPanel {
    private String categoryName;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public AdditionalParameters(){
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel addList = new JLabel("<html>Список дополнительных<br>параметров категории<br>\""+ categoryName + "\"</html>");
        addList.setFont(new Font(addList.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, addList, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, addList, 10, SpringLayout.WEST, this);
        this.add(addList);

        switch (categoryName){
            case "ученик":{
                //название школы, класс
                JLabel nameSchool = new JLabel("Название школы");
            }
        }
    }

}

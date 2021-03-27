package gui.queryWindow.firstQuery;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstQuery extends JDialog implements QueryFrame {
    private QueryController queryController;

    public FirstQuery(QueryController queryController) {
        this.queryController = queryController;
    }

    @Override
    public void openQueryConfig() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 300, dimension.height/2 - 150, 600, 300);
        this.setTitle("Поиск читателей");

        JPanel jPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel info = new JLabel("<html>Выберите параметры поиска читателей:</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
        jPanel.add(info);

        JLabel category = new JLabel("Категория");
        category.setFont(new Font(category.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, category, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, category, 20, SpringLayout.WEST, jPanel);
        jPanel.add(category);

        String[] items = {
                "прочие",
                "ученик",
                "учитель",
                "студент",
                "научный сотрудник",
                "работник",
                "пенсионер"
        };
        JComboBox<String> categoryChose = new JComboBox<>(items);
        categoryChose.setFont(new Font(categoryChose.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, categoryChose, 20, SpringLayout.SOUTH, category);
        layout.putConstraint(SpringLayout.WEST, categoryChose, 20, SpringLayout.WEST, jPanel);
        jPanel.add(categoryChose);

        JButton continueButton = new JButton("Задать параметры выбранной категории");
        continueButton.setFont(new Font(continueButton.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, continueButton, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.WEST, continueButton, 20, SpringLayout.WEST, jPanel);
        jPanel.add(continueButton);

        JLabel additionalParametersInfo = new JLabel("<html>Выбрав категорию,<br>можно задать<br>дополнительные параметры поиска<br>" +
                "по нажаитю кнопки под значением<br>выбранной категории.<br>Если параметры не указываются,<br>то будет показна вся категория</html>");

        additionalParametersInfo.setFont(new Font(additionalParametersInfo.getFont().getName(), Font.ITALIC, 16));
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        additionalParametersInfo.setBorder(solidBorder);
        additionalParametersInfo.setIcon(icon);
        layout.putConstraint(SpringLayout.NORTH, additionalParametersInfo, 10, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.EAST, additionalParametersInfo, -10, SpringLayout.EAST, jPanel);
        layout.putConstraint(SpringLayout.WEST, additionalParametersInfo, -360, SpringLayout.EAST, additionalParametersInfo);
        jPanel.add(additionalParametersInfo);

        JButton performQuery = new JButton("Найти");
        performQuery.setFont(new Font(performQuery.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.SOUTH, performQuery, -10, SpringLayout.SOUTH, jPanel);
        layout.putConstraint(SpringLayout.EAST, performQuery, -10, SpringLayout.EAST, jPanel);
        jPanel.add(performQuery);

        continueButton.addActionListener(e ->{
            performQuery.setVisible(true);
        });

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }

    @Override
    public void performQuery(String sql) throws SQLException {

    }

    @Override
    public void showQueryResult(ResultSet resultSet) {

    }
}

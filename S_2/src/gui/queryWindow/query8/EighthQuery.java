package gui.queryWindow.query8;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class EighthQuery extends QueryFrame {
    public EighthQuery(QueryController queryController) {
        super(queryController);
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

        try {
            String sql = "select id_librarian from librarians";
            getQueryController().performSQLQuery(sql);
            ArrayList<Integer> librariansIds = new ArrayList<>();
            while (getQueryController().getCurrResultSet().next()){
                librariansIds.add(getQueryController().getCurrResultSet().getInt("id_librarian"));
            }
            getQueryController().closeSQLSet();

            JLabel info = new JLabel("<html>Укажите период вермени для поиска</html>");
            info.setFont(new Font(info.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, jPanel);
            layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, jPanel);
            jPanel.add(info);

            JLabel begin = new JLabel("Начало периода в формате (дд.мм.гггг)");
            begin.setFont(new Font(begin.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, begin, 30, SpringLayout.NORTH, info);
            layout.putConstraint(SpringLayout.WEST, begin, 30, SpringLayout.WEST, jPanel);
            jPanel.add(begin);

            MaskFormatter maskFormatter = null;
            try {
                maskFormatter = new MaskFormatter("##.##.####");
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            JFormattedTextField beginValue = new JFormattedTextField(maskFormatter);
            beginValue.setColumns(7);
            beginValue.setFont(new Font(beginValue.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, beginValue, 10, SpringLayout.SOUTH, begin);
            layout.putConstraint(SpringLayout.WEST, beginValue, 30, SpringLayout.WEST, jPanel);
            jPanel.add(beginValue);

            JLabel end = new JLabel("Конец периода в формате (дд.мм.гггг)");
            end.setFont(new Font(end.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, end, 30, SpringLayout.NORTH, beginValue);
            layout.putConstraint(SpringLayout.WEST, end, 30, SpringLayout.WEST, jPanel);
            jPanel.add(end);

            JFormattedTextField endValue = new JFormattedTextField(maskFormatter);
            endValue.setColumns(7);
            endValue.setFont(new Font(endValue.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, endValue, 10, SpringLayout.SOUTH, end);
            layout.putConstraint(SpringLayout.WEST, endValue, 30, SpringLayout.WEST, jPanel);
            jPanel.add(endValue);

            JLabel idLibrarianLabel = new JLabel("Идентификатор библиотекаря");
            idLibrarianLabel.setFont(new Font(idLibrarianLabel.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.NORTH, idLibrarianLabel, 20, SpringLayout.SOUTH, endValue);
            layout.putConstraint(SpringLayout.WEST, idLibrarianLabel, 30, SpringLayout.WEST, jPanel);
            jPanel.add(idLibrarianLabel);

            JComboBox<String> idLibrarianChoose = new JComboBox(librariansIds.toArray());
            idLibrarianChoose.setFont(new Font(idLibrarianChoose.getFont().getName(), Font.PLAIN, 16));
            layout.putConstraint(SpringLayout.NORTH, idLibrarianChoose, 10, SpringLayout.SOUTH, idLibrarianLabel);
            layout.putConstraint(SpringLayout.WEST, idLibrarianChoose, 30, SpringLayout.WEST, jPanel);
            jPanel.add(idLibrarianChoose);

            JButton find = new JButton("Найти читателей");
            find.setFont(new Font(find.getFont().getName(), Font.BOLD, 16));
            layout.putConstraint(SpringLayout.EAST, find, -10, SpringLayout.EAST, jPanel);
            layout.putConstraint(SpringLayout.SOUTH, find, -10, SpringLayout.SOUTH, jPanel);
            find.addActionListener(e->{
                String id = Objects.requireNonNull(idLibrarianChoose.getSelectedItem()).toString();
                String beginDate = beginValue.getText();
                String endDate = endValue.getText();
                String querySql = "with t1 as (\n" +
                        "    --Выбрать читателей которым выдавали книгу в указанный промежуток времени\n" +
                        "    select ID_READER from ISSUED_BOOKS\n" +
                        "    where ID_LIBRARIAN = "+ id +"\n" +
                        "      and DATE_OF_ISSUE >= TO_DATE('"+ beginDate +"','dd.mm.yyyy') and DATE_OF_ISSUE <= TO_DATE('"+ endDate +"', 'dd.mm.yyyy')\n" +
                        "),\n" +
                        "     t2 as (\n" +
                        "         --Получить дпоплнительную информацию о найденых читателях\n" +
                        "         select READERS.ID_READER as \"Идентификатор\", SURNAME as \"Фамлиия\", NAME as \"Имя\", PATRONYMIC as \"Отчество\" from READERS\n" +
                        "                                                                                                                             inner join t1 on t1.ID_READER = READERS.ID_READER\n" +
                        "     )\n" +
                        "select * from t2";
                try {
                    getQueryController().performSQLQuery(querySql);
                    ResultSet resultSet = getQueryController().getCurrResultSet();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int columnsCount = resultSetMetaData.getColumnCount();
                    ArrayList<String> list = new ArrayList<>();
                    for(int i = 1; i <= columnsCount; i++){
                        list.add(resultSetMetaData.getColumnLabel(i));
                    }
                    String[] columnsHeaders = list.toArray(new String[0]);
                    ResultQueryView queryView = new ResultQueryView(resultSet, columnsCount, columnsHeaders);
                    getQueryController().closeSQLSet();
                }
                catch (SQLException exception){
                    JLabel error = new JLabel();
                    error.setText(exception.getMessage());
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            });
            jPanel.add(find);
        }
        catch (SQLException exception){
            JLabel error = new JLabel();
            error.setText(exception.getMessage());
            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
    }
}

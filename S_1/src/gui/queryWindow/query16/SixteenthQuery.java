package gui.queryWindow.query16;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class SixteenthQuery extends QueryFrame {

    public SixteenthQuery(QueryController queryController) {
        super(queryController);
    }

    public void openQueryConfig(){
        String queryStr = "with t1 as (\n" +
                "    --Выбираем произведения и их популярность\n" +
                "    select TITLE, ID_EDITION, POPULARITY from COMPOSITIONS\n" +
                "),\n" +
                "t2 as (\n" +
                "    --Ищем максимальное значение популярности\n" +
                "    select max(POPULARITY)\n" +
                "    from t1\n" +
                ")\n" +
                "select TITLE as \"Название\", ID_EDITION as \"Издание\" from t1\n" +
                "where POPULARITY = (select * from t2)";

        try {
            getQueryController().performSQLQuery(queryStr);
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

        } catch (SQLException exception) {
            JLabel error = new JLabel();
            error.setText(exception.getMessage());
            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}

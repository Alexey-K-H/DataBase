package gui.queryWindow.query10;

import controllers.QueryController;
import gui.queryWindow.QueryFrame;
import gui.queryWindow.ResultQueryView;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class TenthQuery extends QueryFrame {

    public TenthQuery(QueryController queryController) {
        super(queryController);
    }

    @Override
    public void openQueryConfig() {
        String queryStr = "with t1 as (\n" +
                "    --Поиск читатаелей с просроченным сроком литературы\n" +
                "    select ID_READER from ISSUED_BOOKS where RETURN_DATE <= CURRENT_DATE and IS_RETURNED = 'нет'\n" +
                "),\n" +
                "     t2 as (\n" +
                "         --Подробная информация о должниках\n" +
                "         select t1.ID_READER as \"ID\", SURNAME as \"Фамилия\", NAME as \"Имя\", PATRONYMIC as \"Отчество\", ID_LIBRARY as \"Библиотека\"\n" +
                "         from READERS\n" +
                "                  inner join t1 on t1.ID_READER = READERS.ID_READER\n" +
                "     )\n" +
                "select * from t2";

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

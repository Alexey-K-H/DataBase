package controllers;

import connection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryController {
    private final DBConnection connection;
    private ResultSet currResultSet;

    public QueryController(DBConnection connection){
        this.connection = connection;
    }

    public void performSQLQuery(String sql) throws SQLException{
        PreparedStatement preStatement = connection.getConn().prepareStatement(sql);
        currResultSet = preStatement.executeQuery();
    }

    public ResultSet getCurrResultSet() {
        return currResultSet;
    }

    public void closeSQLSet() throws SQLException {
        currResultSet.close();
    }

    public DBConnection getConnection() {
        return connection;
    }
}

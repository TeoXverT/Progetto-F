package inputoutput;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnection {

    private java.sql.Connection conn;
    private Statement stmt;

    private final String url = "sql8.freemysqlhosting.net";
    private final String DbName = "sql8115909";
    private final String user = "sql8115909";
    private final String pass = "ifYmYwRJJS";
    //Attenzione si ricorda la Pass non e quella corretta, si prega di pushare solo file con pass sbagliata (per motivi di sicurezza).

    public SQLConnection() {
    }

    public ResultSet readingQuery(String query) throws SQLException {
        return stmt.executeQuery(query);
    }

    public void writingQuery(String query) throws SQLException {
        stmt.executeUpdate(query);
    }

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://" + url + ":3306/" + DbName + "?user=" + user + "&password=" + pass);
            stmt = conn.createStatement();
            return true;

        } catch (SQLException E) {
            System.err.println("SQLException: " + E.getMessage());
            System.err.println("SQLState:     " + E.getSQLState());
            System.err.println("VendorError:  " + E.getErrorCode());
            return false;
        } catch (Exception E) {
            System.err.println("Non trovo il driver da caricare.");
            E.printStackTrace();
            return false;
        }
    }

    public boolean closeConnection() {
        try {
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

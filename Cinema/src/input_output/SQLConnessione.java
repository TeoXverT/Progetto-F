package input_output;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnessione {

    private java.sql.Connection conn;
    private Statement stmt;

    private final String url = "sql8.freemysqlhosting.net";
    private final String nomeDatabase = "sql8115909A";
    private final String user = "sql8115909";
    private final String pass = "ifYmYwRJJS";

    public SQLConnessione() {

    }

    ResultSet eseguiQuery(String SQL) throws SQLException { //Necessita di stetmet try chatch esterno
        return stmt.executeQuery(SQL);
    }

    boolean creaConnessione() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://" + url + ":3306/" + nomeDatabase + "?user=" + user + "&password=" + pass);
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

    boolean chiudiConnessione() {
        try {
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnessione.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
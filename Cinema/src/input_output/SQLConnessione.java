package input_output;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnessione {

    private java.sql.Connection conn;
    private Statement stmt;

    private final String url = "sql8.freemysqlhosting.net";
    private final String nomeDatabase = "sql8115909";
    private final String user = "sql8115909"; 
    private final String pass = "ifYmYwRJJS";
    //Attenzione si ricorda la Pass non e quella corretta, si prega di pushare solo file con pass sbagliata (per motivi di sicurezza).

    public SQLConnessione() {

    }

   public ResultSet eseguiQueryLettura(String query) throws SQLException { //Necessita di stetmet try chatch esterno (si possono fare diagnosi con output a display vi spiego in classe)
//       System.out.println(query);
       return stmt.executeQuery(query);
    }
   
    public void eseguiQueryScrittura(String query) throws SQLException {  //Questo metodo lo di deve utilizzare ogni qual volta si fa una scittura su db
        stmt.executeUpdate(query);
    }
    public boolean creaConnessione() {
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

    public boolean chiudiConnessione() {
        try {
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnessione.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void esempioEsecuzioneQuery() {
        SQLConnessione SQL = new SQLConnessione();
        SQL.creaConnessione();

        try {
            ResultSet rsa = SQL.eseguiQueryLettura("SELECT * from Film where Film.titolo = 'The Termin'");
            while (rsa.next()) {
                System.out.println(rsa.getString("id_film") + " " + rsa.getString("titolo") + " " + rsa.getString("genere"));
            }
            rsa.close();
        } catch (SQLException ex) {
            System.out.println("Errore Di Lettura Dal Server.");
        }

        SQL.chiudiConnessione();
        // NOTABENE IL APRI E CHIUDI CONNESSIONE SONO DA FARE SOLO UNA VOLTA (ALL APERTURA DEL PROGRAMMA E ALLA CHIUSURA DEL SUDETO)
    }
}

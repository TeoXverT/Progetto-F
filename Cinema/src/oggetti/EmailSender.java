package oggetti;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailSender {

    public EmailSender() {

    }

    public boolean SendEmail(String email_destinatario, Film film, Proiezione proiezione, Prenotazione prenotazione) {
        try {
            String URL = "http://xsacniopanzax.altervista.org/email_sender_cinema.php";
            URL obj = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "email_destinatario=" + email_destinatario + "&acquisti=" + purchaseDescription(film, proiezione, prenotazione) + "&prezzo=" + Double.toString(prenotazione.getPrezzo()) + "&id_prenotazione=" + Integer.toString(prenotazione.getId_prenotazione());

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);

            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (Integer.parseInt(response.toString()) == 1) {
                return true;
            }
        } catch (IOException ex) {
        }
        return false;
    }

    private String purchaseDescription(Film film, Proiezione proiezione, Prenotazione prenotazione) {
        String messaggio;
        messaggio = "<center>";
        messaggio = messaggio + "<IMG SRC=\"        https://camo.githubusercontent.com/b98806da0d5c21996a009709acfe4f6256cd31c5/687474703a2f2f7333322e706f7374696d672e6f72672f686236796f3678736c2f6c6f676f5f74726173706172656e74655f6f6c645f30322e706e67\n"
                + "\" ALT=\"some text\" WIDTH=600 HEIGHT=400><br><br>";

        messaggio = messaggio + "<IMG SRC=\"" + film.getLink_copertina() + "\" ALT=\"some text\" WIDTH=400 HEIGHT=600><br><br>";
        messaggio = messaggio + "<p><font size=\"4\">Codice Prenotazione :" + prenotazione.getId_prenotazione() + "</p></font><br><br>";
        messaggio = messaggio + "<p>" + film.getTitolo_film() + " inizia il " + proiezione.getData_ora_friendly_2() + " nella Sala " + proiezione.getId_sala() + "</p><br>";
        messaggio = messaggio + "Prenotati i seguenti posti:<br>";
        for (Seat s : prenotazione.getPosti_prenotati()) {
            messaggio = messaggio + "Fila " + s.getx() + " Colonna " + s.gety() + "<br>";
        }
        if (prenotazione.getNumber_of_glasses() != 0) {
            messaggio = messaggio + "Hai in oltre comprato " + prenotazione.getNumber_of_glasses() + " occhiali 3D.<br><br>";
        }
        messaggio = messaggio + "Totale da pagare: " + prenotazione.getPrezzo() + " Euro<br><br><br>";
        messaggio = messaggio + "<i>*Fino a che non viene saldato il pagamento questa ricevuta non è valida per entrare al cinema.<br>**Se si è chiuso il programma questo ricevuta non è più valdida, riperete la procedura.</i><br><br><br><br>";
        messaggio = messaggio + "</center>";
        return messaggio;
    }
}

/* TeST....
 
 Film film = new Film(1, "ZOOTROPOLIS", "Azione", 120, "Piter parcer è un super eroe", "youtube.it/werewr", "http://pad.mymovies.it/filmclub/2013/08/079/locandina.jpg",null);
 Proiezione proiezione = new Proiezione(123, Calendar.getInstance(), 1, 2, 1, 6);
        
 ArrayList<Seat> posti_prenotati = new ArrayList<>();
 posti_prenotati.add(new Seat(340, 12, 13));
 posti_prenotati.add(new Seat(341, 14, 13));
 Prenotazione prenotazione = new Prenotazione(13, 18, posti_prenotati, null, 12);

 EmailSender email = new EmailSender();
 email.SendEmail("umeermohammad@gmail.com", film, proiezione, prenotazione);

 */

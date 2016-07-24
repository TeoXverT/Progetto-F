package Gestore;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import oggetti.Film;
import oggetti.Booking;
import oggetti.Screening;
import oggetti.Seat;

public class EmailSender {

    private String URL_SERVER = "http://xsacniopanzax.altervista.org/progettof/email_sender.php";
    private String NAME_SENDER = "Cinema F";
    private String EMAIL_SENDER = "umeer@outlook.it";
    private String NAME_RECIVER = "Gentile Cliente";
    private String OBJECT = "Richiesta di pagamento";

    private String PAYMENT_RECIVER = "umeer@outlook.it";

    public EmailSender() {

    }

    public boolean SendEmailRequest(String email_destinatario, Film film, Screening proiezione, Booking prenotazione) {
        try {
            URL obj = new URL(URL_SERVER);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "nome_mittente=" + NAME_SENDER + "&email_mittente=" + EMAIL_SENDER + "&nome_destinatario=" + NAME_RECIVER + "&email_destinatario=" + email_destinatario + "&oggetto=" + OBJECT + "&messaggio=" + purchaseDescription(film, proiezione, prenotazione);
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

    private String purchaseDescription(Film film, Screening proiezione, Booking prenotazione) {
        String messaggio;

        messaggio = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"><title>Pagamento</title></head><body><center>";
        messaggio = messaggio + "<IMG SRC=\" http://xsacniopanzax.altervista.org/progettof/logo.png \n"
                + "\" ALT=\"some text\" WIDTH=600 HEIGHT=400><br><br>";

        messaggio = messaggio + "<IMG SRC=\"" + film.getLink_copertina() + "\" ALT=\"some text\" WIDTH=400 HEIGHT=600><br><br>";
        messaggio = messaggio + "<p><font size=\"4\">Codice Prenotazione :" + prenotazione.getId_prenotazione() + "</p></font><br><br>";
        messaggio = messaggio + "<p>" + film.getTitolo_film() + " inizia il " + proiezione.getData_ora_friendly_2() + " nella Sala " + proiezione.getRoom().getId_sala() + "</p><br>";
        messaggio = messaggio + "Prenotati i seguenti posti:<br>";
        for (Seat s : prenotazione.getPosti_prenotati()) {
            messaggio = messaggio + "Fila " + s.getx() + " Colonna " + s.gety() + "<br>";
        }
        if (prenotazione.getNumber_of_glasses() != 0) {
            messaggio = messaggio + "Hai in oltre comprato " + prenotazione.getNumber_of_glasses() + " occhiali 3D.<br><br>";
        }
        messaggio = messaggio + "Totale da pagare: " + prenotazione.getPrezzo() + " Euro<br><br><br>";
        messaggio = messaggio + "<i>*Fino a che non viene saldato il pagamento questa ricevuta non è valida per entrare al cinema.<br>**Si prega di portare con se questo documento al cinema.</i><br><br><br><br>";
        messaggio = messaggio + "</center>";

        //ORA IN SANDBOX, per toglerlo usare https://www.paypal.com/cgi-bin/webscr\
        messaggio = messaggio + "<center>\n"
                + "   <form action=\"https://www.sandbox.paypal.com/cgi-bin/webscr\" method=\"post\">\n"
                + "       <input type=\"hidden\" name=\"cmd\" value=\"_xclick\">\n"
                + "       <input type=\"hidden\" name=\"currency_code\" value=\"EUR\" />\n"
                + "       <input type=\"hidden\" name=\"lc\" value=\"IT\" />\n"
                + "	   \n"
                + "       <input type=\"hidden\" name=\"item_name\" value=\"Prenotazione Cinema\" />\n"
                + "       <input type=\"hidden\" name=\"amount\" value=\"" + prenotazione.getPrezzo() + "\" />\n"
                + "	   <input type=\"hidden\" name=\"item_number\" value=\"" + prenotazione.getId_prenotazione() + "\">\n"
                + "\n"
                + "       <input type=\"hidden\" name=\"business\" value=\""+PAYMENT_RECIVER+"\" />\n"
                + "       <input type=\"hidden\" name=\"notify_url\" value=\"http://xsacniopanzax.altervista.org/progettof/conferma_paypal.php\" />\n"
                + "       <input type=\"hidden\" name=\"custom\" value=\""+prenotazione.getId_prenotazione()+"\" />\n"
                + "	   	  \n"
                + "	  <input type=\"image\" src=\"http://xsacniopanzax.altervista.org/progettof/pay_img.jpg\" border=\"0\" name=\"submit\" width=\"400\" height=\"150\" alt=\"Make payments with PayPal\">  \n"
                + "   </form> \n"
                + " </center>\n"
                + "   \n"
                + "<br>\n"
                + "<br><br><br><br><br><br><br><br>\n"
                + "   \n"
                + "</body>\n"
                + "</html>";
        return messaggio;
    }
}

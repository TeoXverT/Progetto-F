package customer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import obj.Film;
import obj.Booking;
import obj.Projection;
import obj.Seat;

public class EmailSender {

    private String URL_SERVER = "http://xsacniopanzax.altervista.org/progettof/email_sender.php";
    private String NAME_SENDER = "Cinema F";
    private String EMAIL_SENDER = "umeer@outlook.it";
    private String NAME_RECIVER = "Gentile Cliente";
    private String OBJECT = "Richiesta di pagamento";

    private String PAYMENT_RECIVER = "umeer@outlook.it";

    public EmailSender() {

    }

    public boolean sendEmailRequest(Booking booking) {

        Projection screening = booking.getProjection();
        Film film = screening.getFilm();

        try {
            URL obj = new URL(URL_SERVER);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "nome_mittente=" + NAME_SENDER + "&email_mittente=" + EMAIL_SENDER + "&nome_destinatario=" + NAME_RECIVER + "&email_destinatario=" + booking.getEmail() + "&oggetto=" + OBJECT + "&messaggio=" + purchaseDescription(film, screening, booking);
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

            JOptionPane.showMessageDialog(null,
                    "Error while sending email.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    private String purchaseDescription(Film film, Projection screening, Booking booking) {

        SimpleDateFormat giorno = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat ora = new SimpleDateFormat("HH:mm");

        String message;

        message = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"><title>Pagamento</title></head><body><center>";
        message = message + "<IMG SRC=\" http://xsacniopanzax.altervista.org/progettof/logo.png \n"
                + "\" ALT=\"some text\" WIDTH=600 HEIGHT=400><br><br>";

        message = message + "<IMG SRC=\"" + film.getLinkCover() + "\" ALT=\"some text\" WIDTH=400 HEIGHT=600><br><br>";
        message = message + "<p><font size=\"4\">Codice Prenotazione :" + booking.getIdBooking() + "</p></font><br><br>";
        message = message + "<p>" + film.getTitle() + " inizia il giorno: " + giorno.format(screening.getData_ora().getTime()) + " alle " + ora.format(screening.getData_ora().getTime()) + " nella Sala N° " + screening.getRoom().getIdHall() + "</p><br>";
        message = message + "Prenotati i seguenti posti:<br>";
        for (Seat s : booking.getBookedSeat()) {
            message = message + "Fila " + s.getx() + " Colonna " + s.gety() + "<br>";
        }
        if (booking.getNumberOfGlasses() != 0) {
            message = message + "Hai in oltre comprato " + booking.getNumberOfGlasses() + " occhiali 3D.<br><br>";
        }
        message = message + "Totale da pagare: " + booking.getPrice() + " Euro<br><br><br>";
        message = message + "<i>*Fino a che non viene saldato il pagamento questa ricevuta non è valida per entrare al cinema.<br>**Si prega di portare con se questo documento al cinema.</i><br><br><br><br>";
        message = message + "</center>";

        //ORA IN SANDBOX, per toglerlo usare https://www.paypal.com/cgi-bin/webscr\
        message = message + "<center>\n"
                + "   <form action=\"https://www.sandbox.paypal.com/cgi-bin/webscr\" method=\"post\">\n"
                + "       <input type=\"hidden\" name=\"cmd\" value=\"_xclick\">\n"
                + "       <input type=\"hidden\" name=\"currency_code\" value=\"EUR\" />\n"
                + "       <input type=\"hidden\" name=\"lc\" value=\"IT\" />\n"
                + "	   \n"
                + "       <input type=\"hidden\" name=\"item_name\" value=\"Prenotazione Cinema\" />\n"
                + "       <input type=\"hidden\" name=\"amount\" value=\"" + booking.getPrice() + "\" />\n"
                + "	   <input type=\"hidden\" name=\"item_number\" value=\"" + booking.getIdBooking() + "\">\n"
                + "\n"
                + "       <input type=\"hidden\" name=\"business\" value=\"" + PAYMENT_RECIVER + "\" />\n"
                + "       <input type=\"hidden\" name=\"notify_url\" value=\"http://xsacniopanzax.altervista.org/progettof/conferma_paypal.php\" />\n"
                + "       <input type=\"hidden\" name=\"custom\" value=\"" + booking.getIdBooking() + "\" />\n"
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
        return message;
    }
}

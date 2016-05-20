package oggetti;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailSender {

    private final String NAME_SENDER; 
    private final String EMAIL_SENDER;

    public EmailSender(String nome_mittente, String email_mittente) {
        this.NAME_SENDER = nome_mittente;
        this.EMAIL_SENDER = email_mittente;
    }
 
    public boolean SendEmail(String nome_destinatario, String email_destinatario, String oggetto, String messaggio) {
        try {
            String URL = "http://xsacniopanzax.altervista.org/email_sender.php";
            URL obj = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "nome_mittente=" + NAME_SENDER + "&email_mittente=" + EMAIL_SENDER + "&nome_destinatario=" + nome_destinatario + "&email_destinatario=" + email_destinatario + "&oggetto=" + oggetto + "&messaggio=" + messaggio;

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
}

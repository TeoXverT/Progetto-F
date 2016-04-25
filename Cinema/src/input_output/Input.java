package input_output;

import oggetti.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import oggetti.Prenotazione;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Input {

    String nomeFileConfig, nomeFileFilm, nomeFileSala, nomeFileProiezione, nomeFilePrenotazione;

    public Input(String nomeFileConfig, String nomeFileFilm, String nomeFileSala, String nomeFileProiezione, String nomeFilePrenotazione) {
        this.nomeFileConfig = nomeFileConfig;
        this.nomeFileFilm = nomeFileFilm;
        this.nomeFileSala = nomeFileSala;
        this.nomeFileProiezione = nomeFileProiezione;
        this.nomeFilePrenotazione = nomeFilePrenotazione;
    }

    public Config scaricaConfig( ) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(nomeFileConfig);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2;

        inputBufferizzato.readLine(); //PER SALTARE UNA RIGA COSÌ UMEER NON ROMPE

        riga = inputBufferizzato.readLine();
        StringTokenizer st = new StringTokenizer(riga, "\t");
        parte1 = st.nextToken();
        parte2 = st.nextToken();
        return new Config(Double.parseDouble(parte1), Double.parseDouble(parte2));
    }

    public ArrayList<Film> scaricaFilm( ) throws FileNotFoundException, IOException {
        ArrayList<Film> listaFilm = new ArrayList<>();

        FileReader input = new FileReader(nomeFileFilm);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4, parte5, parte6;
        inputBufferizzato.readLine(); //PER SALTARE UNA RIGA COSÌ UMEER NON ROMPE
        while ((riga = inputBufferizzato.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
            parte6 = st.nextToken();
            listaFilm.add(new Film(Integer.parseInt(parte1), parte2, parte3, Integer.parseInt(parte4), parte5, parte6));
        }
        return listaFilm;
    }

    public ArrayList<Sala> scaricaSala( ) throws FileNotFoundException, IOException {
        ArrayList<Sala> listaSala = new ArrayList<>();

        FileReader input = new FileReader(nomeFileSala);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4;
        inputBufferizzato.readLine();
        while ((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            listaSala.add(new Sala(Integer.parseInt(parte1), Integer.parseInt(parte2), Integer.parseInt(parte3), parseMatrice(parte4)));
        }
        return listaSala;
    }

    public ArrayList<Proiezione> scaricaProiezione( ) throws FileNotFoundException, IOException {
        ArrayList<Proiezione> listaProiezione = new ArrayList<>();

        FileReader input = new FileReader(nomeFileProiezione);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4, parte5, parte6, parte7;
        inputBufferizzato.readLine();
        while ((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
            parte6 = st.nextToken();
            parte7 = st.nextToken();
            listaProiezione.add(new Proiezione(Integer.parseInt(parte1), this.parseData_ora(parte2), Integer.parseInt(parte3), Integer.parseInt(parte4), parte5, Double.parseDouble(parte6)));
        }
        return listaProiezione;
    }

    public ArrayList<Prenotazione> scaricaPrenotazione( ) throws FileNotFoundException, IOException {
        ArrayList<Prenotazione> listaPrenotazione = new ArrayList<>();

        FileReader input = new FileReader(nomeFilePrenotazione);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4, parte5;

        inputBufferizzato.readLine(); //PER SALTARE UNA RIGA COSÌ UMEER NON ROMPE

        while ((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
//            System.out.println("p1 "+parte1+" p2 "+parte2+" p3 "+ parte3+" p4 "+ parte4 + " p5 "+ parte5);
            listaPrenotazione.add(new Prenotazione(Integer.parseInt(parte1), Integer.parseInt(parte2), parseMatrice(parte3), parseData_ora(parte4), Double.parseDouble(parte5)));
        }
        return listaPrenotazione;
    }

    private Calendar parseData_ora(String stringaCalendario) {
        int anno, mese, giorno, ora, min, sec;

        StringTokenizer st = new StringTokenizer(stringaCalendario);
        StringTokenizer rt = new StringTokenizer(st.nextToken(), "-");

        anno = Integer.parseInt(rt.nextToken());
        mese = Integer.parseInt(rt.nextToken());
        giorno = Integer.parseInt(rt.nextToken());

        rt = new StringTokenizer(st.nextToken(), ":");

        ora = Integer.parseInt(rt.nextToken());
        min = Integer.parseInt(rt.nextToken());
        sec = Integer.parseInt(rt.nextToken());
        
        Calendar fine = new GregorianCalendar(anno, mese, giorno, ora, min, sec);

        return fine;
    }

    private ArrayList<Posti> parseMatrice(String postiVip) {
        ArrayList<Posti> posti = new ArrayList<>();

        if (!postiVip.equals("0")) {
            StringTokenizer st = new StringTokenizer(postiVip, ",");
            StringTokenizer rt;
            while (st.hasMoreTokens()) {
                rt = new StringTokenizer(st.nextToken(), ":");
                posti.add(new Posti(Integer.parseInt(rt.nextToken()), Integer.parseInt(rt.nextToken())));
            }
        }

        return posti;
    }
}

package input_output;

import oggetti.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import oggetti.Prenotazione;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Input {

    private ArrayList<Film> listaFilm;
    private ArrayList<Sala> listaSala;
    private ArrayList<Proiezione> listaProiezione;
    private ArrayList<Prenotazione> listaPrenotazione;

    public Input() {
        listaFilm = new ArrayList<>();
        listaSala = new ArrayList<>();
        listaProiezione = new ArrayList<>();
        listaPrenotazione = new ArrayList<>();

    }

    public ArrayList<Film> caricaListaFilm(String nomeFile) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(nomeFile);
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

    public ArrayList<Sala> caricaListaSala(String nomeFile) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(nomeFile);
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

    public ArrayList<Proiezione> caricaListaProiezione(String nomeFile) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(nomeFile);
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
            listaProiezione.add(new Proiezione(Integer.parseInt(parte1), this.parseData_ora(parte2), Integer.parseInt(parte3), Integer.parseInt(parte4), parte5, Integer.parseInt(parte6), Integer.parseInt(parte7)));
        }
        return listaProiezione;
    }

    public Calendar parseData_ora(String stringaCalendario) {
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

    public int[][] parseMatrice(String postiVip) {
        int i = 0;
        int[][] matriceVip = new int[40][2];

        if (!postiVip.equals("0")) {
            //String parte1, parte2;
            StringTokenizer st = new StringTokenizer(postiVip, ",");
            StringTokenizer rt;
            while (st.hasMoreTokens()) {
                rt = new StringTokenizer(st.nextToken(), ":");
                matriceVip[i][0] = Integer.parseInt(rt.nextToken());
                matriceVip[i][1] = Integer.parseInt(rt.nextToken());
                i++;
            }
        }
        return matriceVip;
    }

    public ArrayList<Prenotazione> caricaListaPrenotazione(String nomeFile) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(nomeFile);
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

    public Config caricaConfig(String nomeFile) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(nomeFile);
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
}

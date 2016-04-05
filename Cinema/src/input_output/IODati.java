
package input_output;

import oggetti.Sala;
import oggetti.Film;
import oggetti.Proiezione;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import oggetti.Prenotazione;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class IODati {

    private ArrayList<Film> listaFilm;
    private ArrayList<Sala> listaSala;
    private ArrayList<Proiezione> listaProiezione;
    private ArrayList<Prenotazione> listaPrenotazione;

    public IODati() {

    }

    public ArrayList<Film> caricaListaFilm(String nomeFile) throws FileNotFoundException, IOException {
        Film variabileFilm;
        listaFilm = new ArrayList<>();
        FileReader input = new FileReader(nomeFile);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4, parte5, parte6;
        inputBufferizzato.readLine(); //PER SALTARE UNA RIGA COSÌ UMEER NON ROMPE
        
        while ((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            System.out.println("ciao1");
            parte2 = st.nextToken();
            System.out.println("ciao2");
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
            parte6 = st.nextToken();
            variabileFilm = new Film(Integer.parseInt(parte1), parte2, parte3, Integer.parseInt(parte4), parte5, parte6);
            listaFilm.add(variabileFilm);
        }
        return listaFilm;
    }
    
    public ArrayList<Sala> caricaListaSala(String nomeFile) throws FileNotFoundException, IOException {
        Sala variabileSala;
        listaSala = new ArrayList<>();
        FileReader input = new FileReader(nomeFile);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4;
        
        while ((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            int[][] matriceVip = new int[40][2];
            matriceVip = this.dividiParte4(parte4);
            variabileSala = new Sala(Integer.parseInt(parte1), Integer.parseInt(parte2), Integer.parseInt(parte3), matriceVip);
            listaSala.add(variabileSala);
        }
        return listaSala;
    }
    
    
    public ArrayList<Proiezione> caricaListaProiezione(String nomeFile) throws FileNotFoundException, IOException {
        Proiezione variabileProiezione;
        listaProiezione = new ArrayList<>();
        FileReader input = new FileReader(nomeFile);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1, parte2, parte3, parte4, parte5, parte6, parte7;
        
        while ((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
            parte6 = st.nextToken();
            parte7 = st.nextToken();
            variabileProiezione = new Proiezione(Integer.parseInt(parte1), this.parse_Data_ora(parte2), Integer.parseInt(parte3), Integer.parseInt(parte4), parte5, Integer.parseInt(parte6), Integer.parseInt(parte7));
            listaProiezione.add(variabileProiezione);
        }
        return listaProiezione;
    }

    public Calendar parse_Data_ora(String stringaCalendario) {
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

    public int[][] dividiParte4(String postiVip) {
        int i = 0;
        int[][] matriceVip = new int[40][2];

        //String parte1, parte2;
        StringTokenizer st = new StringTokenizer(postiVip, ",");
        StringTokenizer rt;
        while (st.hasMoreTokens()) {

            rt = new StringTokenizer(st.nextToken(), ":");

            matriceVip[i][0] = Integer.parseInt(rt.nextToken());
            matriceVip[i][1] = Integer.parseInt(rt.nextToken());

            i++;

        }

        return matriceVip;
    } 
    public ArrayList<Prenotazione> caricaListaPrenotazioni(String nomeFile) throws FileNotFoundException, IOException {
        Prenotazione variabilePrenotazione;
        listaPrenotazione = new ArrayList<>();
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
            int[][] matricePosti = new int[40][2];
            matricePosti = this.dividiParte4(parte3);
            variabilePrenotazione = new Prenotazione(Integer.parseInt(parte1), Integer.parseInt(parte2), matricePosti, this.parse_Data_ora(parte4), Double.parseDouble(parte5));
            listaPrenotazione.add(variabilePrenotazione);
        }
        return listaPrenotazione;
    } 
}


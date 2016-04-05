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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cri
 */
public class IODati {
    
    private ArrayList<Film> listaFilm;
    private ArrayList<Sala> listaSala;
    private ArrayList<Proiezione> listaProiezione;
    
    /*
    CRIIIIII LEGGIIII
    
    abbiamo rimesso idfilm nel costruttore!! quindi ri modifica tutto hahahahahaha
    
    
    */
    
    public IODati() {
        
     }
    
    public ArrayList<Film> caricaListaFilm(String nomeFile) throws FileNotFoundException, IOException {
        
        Film variabileFilm;
        listaFilm = new ArrayList<>();
        FileReader input = new FileReader(nomeFile);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        String parte1,parte2,parte3,parte4;
        while((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
           
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            
            //variabileFilm = new Film( parte1, parte2, Integer.parseInt(parte3));
           // listaFilm.add(variabileFilm);
    }
        
     return listaFilm;
    };
    
    
    public ArrayList<Sala> caricaListaSala(String nomeFile) throws FileNotFoundException, IOException {
        
        Sala variabileSala;
        listaSala = new ArrayList<>();
        FileReader input = new FileReader(nomeFile);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        
        String parte1,parte2,parte3,parte4,parte5;
        while((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
           
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
            
            variabileSala = new Sala(Integer.parseInt(parte1), Integer.parseInt(parte2), Integer.parseInt(parte3), Integer.parseInt(parte4), Integer.parseInt(parte5));
            listaSala.add(variabileSala);
            
    }
        
        return listaSala;
    };
    
    
    public ArrayList<Proiezione> caricaListaProiezione(String nomeFile) throws FileNotFoundException, IOException {
        
        Proiezione variabileProiezione;
        listaProiezione = new ArrayList<>();
        FileReader input = new FileReader(nomeFile);
        BufferedReader inputBufferizzato = new BufferedReader(input);
        String riga;
        
        String parte1,parte2,parte3,parte4,parte5;
        while((riga = inputBufferizzato.readLine()) != null) {          //AGGIUNGE ALL'ARRAY LE COSE
           
            StringTokenizer st = new StringTokenizer(riga, "\t");
            parte1 = st.nextToken();
            parte2 = st.nextToken();
            parte3 = st.nextToken();
            parte4 = st.nextToken();
            parte5 = st.nextToken();
            
            variabileProiezione = new Proiezione(Integer.parseInt(parte1), Integer.parseInt(parte2), Integer.parseInt(parte3), new DataOra(parte4), parte5);
            listaProiezione.add(variabileProiezione);
            
    }
        
        return listaProiezione;
    };
        
    
}
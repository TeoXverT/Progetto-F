/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Output {

    public Output() {
    }

    public boolean caricaConfig(Config config, String nomeFile) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFile, "UTF-8")) {
            writer.println("prezzo_poltrone_vip_cadauno\tsconto_prenotazioni_maggiori_di_3_in_%_applicato_al_totale_ad_esempio");
            writer.println(config.getPrezzo_poltrona_vip() + "\t" + config.getSconto_acquisti_maggiori_3());
            sucesso = true;
        }
        return sucesso;
    }

    public boolean caricaFilm(ArrayList<Film> listaFilm, String nomeFile) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFile, "UTF-8")) {
            writer.println("id_film\ttitolo_film\tgenere\tdurata\tdescizione\tlink_trailer");
            for (Film f : listaFilm) {
                writer.println(f.getId_film() + "\t" + f.getTitolo_film() + "\t" + f.getGenere() + "\t" + f.getDurata() + "\t" + f.getDescrizione() + "\t" + f.getLink());
            }
            sucesso = true;
        }
        return sucesso;
    }

    public boolean caricaSala(ArrayList<Sala> listaSala, String nomeFile) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFile, "UTF-8")) {
            writer.println("id_sala\tposti_X\tposti_Y\tposti_vip");
            for (Sala s : listaSala) {
                writer.println(s.getId_sala() + "\t" + s.getPosti_x() + "\t" + s.getPosti_y() + "\t" + deparseMatrice(s.getPosti()));
            }
            sucesso = true;
        }
        return sucesso;
    }
    
        public boolean caricaProiezione(ArrayList<Proiezione> listaProiezione, String nomeFile) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFile, "UTF-8")) {
            writer.println("id_proiezione\tdata_ora\tid_film\tid_sala\ttipo_proiezione\tprezzo_normale\tprezzo_3d");
            for (Proiezione p : listaProiezione) {
                writer.println(p.getId_protezione() + "\t" + p.getData_ora() + "\t" + p.getId_film() + "\t" + p.getId_sala() + "\t" + p.getTipo_proiezione() + "\t" + p.getPrezzo_normale()+"\t"+p.getPrezzo_3d());
            }
            sucesso = true;
        }
        return sucesso;
    }

    private String deparseMatrice(ArrayList<Posti> posti) {
        String linea_decodificata = "";
        if (posti.size() == 0) {
            linea_decodificata = "0";
        } else {
            for (Posti p : posti) {
                linea_decodificata = linea_decodificata + p.getX() + ":" + p.getY() + ",";
            }
        }
        return linea_decodificata;
    }
    
        private String deparseData_ora( Calendar data_ora) {
        int anno, mese, giorno, ora, min, sec;

        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input_output;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import oggetti.*;

/**
 *
 * @author Yoga
 */
public class Output {

       String nomeFileConfig, nomeFileFilm, nomeFileSala, nomeFileProiezione, nomeFilePrenotazione;

    public Output(String nomeFileConfig, String nomeFileFilm, String nomeFileSala, String nomeFileProiezione, String nomeFilePrenotazione) {
        this.nomeFileConfig = nomeFileConfig;
        this.nomeFileFilm = nomeFileFilm;
        this.nomeFileSala = nomeFileSala;
        this.nomeFileProiezione = nomeFileProiezione;
        this.nomeFilePrenotazione = nomeFilePrenotazione;
    }


    public boolean caricaConfig(Config config ) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFileConfig, "UTF-8")) {
            writer.println("prezzo_poltrone_vip_cadauno\tsconto_prenotazioni_maggiori_di_3_in_%_applicato_al_totale_ad_esempio");
            writer.println(config.getPrezzo_poltrona_vip() + "\t" + config.getSconto_acquisti_maggiori_3());
            sucesso = true;
        }
        return sucesso;
    }

    public boolean caricaFilm(ArrayList<Film> listaFilm ) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFileFilm, "UTF-8")) {
            writer.println("id_film\ttitolo_film\tgenere\tdurata\tdescizione\tlink_trailer");
            for (Film f : listaFilm) {
                writer.println(f.getId_film() + "\t" + f.getTitolo_film() + "\t" + f.getGenere() + "\t" + f.getDurata() + "\t" + f.getDescrizione() + "\t" + f.getLink());
            }
            sucesso = true;
        }
        return sucesso;
    }

    public boolean caricaSala(ArrayList<Sala> listaSala ) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFileSala, "UTF-8")) {
            writer.println("id_sala\tposti_X\tposti_Y\tposti_vip");
            for (Sala s : listaSala) {
                writer.println(s.getId_sala() + "\t" + s.getPosti_x() + "\t" + s.getPosti_y() + "\t" + deparseMatrice(s.getPosti()));
            }
            sucesso = true;
        }
        return sucesso;
    }

    public boolean caricaProiezione(ArrayList<Proiezione> listaProiezione) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFileProiezione, "UTF-8")) {
            writer.println("id_proiezione\tdata_ora\tid_film\tid_sala\ttipo_proiezione\tprezzo_normale\tprezzo_3d");
            for (Proiezione p : listaProiezione) {
                writer.println(p.getId_proiezione() + "\t" + deparseData_ora(p.getData_ora()) + "\t" + p.getId_film() + "\t" + p.getId_sala() + "\t" + p.getTipo_proiezione() + "\t" + p.getPrezzo());
            }
            sucesso = true;
        }
        return sucesso;
    }

    public boolean caricaPrenotazione(ArrayList<Prenotazione> listaPrenotazione) throws FileNotFoundException, IOException {

        boolean sucesso = false;

        try (PrintWriter writer = new PrintWriter(nomeFilePrenotazione, "UTF-8")) {
            writer.println("id_prenotazione\tid_proiezione\tposti_prenotati\tdata_ora\tprezzo");
            for (Prenotazione p : listaPrenotazione) {
                writer.println(p.getId_prenotazione() + "\t" + p.getId_proiezione() + "\t" + deparseMatrice(p.getPosti_prenotati()) + "\t" + deparseData_ora(p.getData_ora()) + "\t" + p.getPrezzo());
            }
            sucesso = true;
        }
        return sucesso;
    }

    private String deparseMatrice(ArrayList<Posto> posti) {
        String linea_decodificata = "";
        if (posti.size() == 0) {
            linea_decodificata = "0";
        } else {
            for (Posto p : posti) {
                linea_decodificata = linea_decodificata + p.getX() + ":" + p.getY() + ",";
            }
        }
        return linea_decodificata;
    }

    private String deparseData_ora(Calendar data_ora) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        dateFormat.setTimeZone(data_ora.getTimeZone());
        return dateFormat.format(data_ora.getTime());
    }
}

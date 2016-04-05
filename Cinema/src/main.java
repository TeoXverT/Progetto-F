
import input_output.IODati;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yoga
 */
public class main {

    public static void main(String[] args) throws IOException {
        System.out.println("Progetto Cinema V 0.00000000000000000000000000000000000000000000001/W la mozzarella");

        IODati IO = new IODati();
        IO.caricaListaFilm("file_db/film.txt");
    }

}

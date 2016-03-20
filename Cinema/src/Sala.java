/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cri
 */
public class Sala {
    
    int numeroSala, numeroPosti, fileVip, x, y;
    int[][] posti;
    
    //X E Y CORRISPONDONO AL MASSIMO NUMERO DI RIGHE E COLONNE CHE CI SONO IN UNA SALA
    
    public Sala(int numeroSala, int numeroPosti, int fileVip, int x, int y) {
        
        this.numeroSala = numeroSala;
        this.numeroPosti = numeroPosti;
        this.fileVip = fileVip;
        this.x = x;
        this.y = y;
        
        
    }
    
}

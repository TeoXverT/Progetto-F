
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
public class  DataOra {
    
    private int ora, minuti, secondi, giorno, mese, anno;
    
    public DataOra(String dataOra) {
        String ora;
        String data;
        
        StringTokenizer st = new StringTokenizer(dataOra);
        
        data = st.nextToken();
        ora = st.nextToken();
        
        st = new StringTokenizer(data, "-");
        this.anno = Integer.parseInt(st.nextToken());
        this.mese = Integer.parseInt(st.nextToken());
        this.giorno = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(ora, ":");
        this.ora = Integer.parseInt(st.nextToken());
        this.minuti = Integer.parseInt(st.nextToken());
        this.secondi = Integer.parseInt(st.nextToken());
        
    }
    
    
}

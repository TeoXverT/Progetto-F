package Gestore;

import input_output.Adapter_SQL;
import java.util.Timer;
import java.util.TimerTask;

public class DbCleanerThread extends TimerTask {

    Timer timer;
    Adapter_SQL adapter;



    public DbCleanerThread(Timer timer) {
        this.timer = timer;
        this.adapter =  Adapter_SQL.getInstance();
    }

    @Override
    public void run() {
        adapter.bookingCleaner();
    }
}

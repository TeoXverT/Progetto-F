package Gestore;

import input_output.Adapter_SQL;
import java.util.Timer;
import java.util.TimerTask;

public class DbCleanerThread extends TimerTask {

    Timer timer;
    Adapter_SQL adapter;

    public DbCleanerThread(Timer timer, Adapter_SQL adapter) {
        this.timer = timer;
        this.adapter = adapter;
    }

    @Override
    public void run() {
        adapter.bookingCleaner();
    }
}

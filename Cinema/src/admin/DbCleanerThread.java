package admin;

import inputoutput.AdapterSQLAdmin;
import java.util.Timer;
import java.util.TimerTask;

public class DbCleanerThread extends TimerTask {

    Timer timer;
    AdapterSQLAdmin adapter;

    public DbCleanerThread(Timer timer, AdapterSQLAdmin adapter) {
        this.timer = timer;
        this.adapter = adapter;
    }

    @Override
    public void run() {
        adapter.bookingCleaner();
    }
}

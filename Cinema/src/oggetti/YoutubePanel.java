package oggetti;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class YoutubePanel extends JPanel {

    public YoutubePanel(String youtube_link, int lunghezza, int altezza) {
        //Dimensioni del pannello restituito, molti borderLayout tendono a strechiare le dimensioni nonostante siano  definite
        this.setLayout(new BorderLayout());
        NativeInterface.open();

        createPlayer(youtube_link, lunghezza, altezza);
    }

    private void createPlayer(String youtube_link, int lunghezza, int altezza) {
        JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(youtube_link);
        webBrowser.setMaximumSize(new Dimension(lunghezza, altezza));
        this.add(webBrowser, BorderLayout.CENTER);
    }
}

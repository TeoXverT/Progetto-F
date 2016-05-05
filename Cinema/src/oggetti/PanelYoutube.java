package oggetti;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class PanelYoutube extends JPanel {

    public PanelYoutube(String youtube_link) {
        this.setLayout(new BorderLayout());
        NativeInterface.open();

        createPlayer(youtube_link);
    }

    private void createPlayer(String youtube_link) {
        JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(youtube_link);
        webBrowser.setMaximumSize(new Dimension(200, 200));
        this.add(webBrowser, BorderLayout.CENTER);
    }
}

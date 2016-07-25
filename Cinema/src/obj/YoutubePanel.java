package obj;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class YoutubePanel extends JPanel {

    public YoutubePanel(String youtube_link, int l, int h) {
        this.setLayout(new BorderLayout());
        NativeInterface.open();
        createPlayer(youtube_link, l, h); 
    }

    private void createPlayer(String youtube_link, int l, int h) {
        JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(youtube_link);
        webBrowser.setMaximumSize(new Dimension(l, h));
        this.add(webBrowser, BorderLayout.CENTER);
    }
}

package applet;

/**
 * Апплет воспрооивзодит музыку.
 * <BR> User: Zhiganov
 * <BR> Date: 17.10.2007
 * <BR> Time: 17:20:26
 */

import java.applet.*;

public class Audio extends Applet {
    AudioClip aClip;

    public void init() {
        aClip = getAudioClip(getDocumentBase(), "1.au");
        // можно также и *.wav
    }

    public void start() {
        if (aClip != null) aClip.loop();//aClip.play();
    }

    public void stop() {
        if (aClip != null) aClip.stop();
    }
}

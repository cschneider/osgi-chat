package net.lr.demo.chat.lcd;

import java.lang.annotation.Annotation;

import org.junit.Ignore;
import org.junit.Test;

import net.lr.demo.chat.lcd.LCDChatListener.TfConfig;

@TfConfig(host = "localhost", port = 4223)
public class LCDTest {

    @Ignore
    @Test
    public void testLCDChatListener() throws Exception {
        LCDChatListener lcd = new LCDChatListener();
        lcd.activate(new TfConfig() {
            
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
            
            @Override
            public int port() {
                return 4223;
            }
            
            @Override
            public String host() {
                return "localhost";
            }
        });
        // Thread.sleep(10000);
        lcd.deactivate();
    }
}

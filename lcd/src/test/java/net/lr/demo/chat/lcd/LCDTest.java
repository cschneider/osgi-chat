package net.lr.demo.chat.lcd;

import org.junit.Test;

public class LCDTest {

    @Test
    public void testLCDChatListener() throws Exception {
        LCDChatListener lcd = new LCDChatListener();
        lcd.activate();
        //Thread.sleep(10000);
        lcd.deactivate();
    }
}

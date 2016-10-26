package net.lr.demo.chat.lcd;

import java.text.DateFormat;
import java.util.Locale;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

@Component(property = //
{
 "service.exported.interfaces=*",
 "org.apache.cxf.ws.address=/lcd"
})
public class LCDWriter implements ChatListener {
    private BrickletLCD20x4 lcd;
    private DateFormat df;
    private ChatBuffer buffer;

    @Reference
    TinkerConnect tinkerConnect;

    @Activate
    public void activate() throws TimeoutException, NotConnectedException {
        this.df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        this.buffer = new ChatBuffer((message) -> printMessage(message));
        initlcd();
    }

    private void initlcd() throws TimeoutException, NotConnectedException {
        IPConnection ipcon = tinkerConnect.getConnection();
        lcd = new BrickletLCD20x4("rV1", ipcon);
        lcd.backlightOn();
        lcd.clearDisplay();
        lcd.addButtonPressedListener((button) -> buttonPressed(button));
    }
    
    public void buttonPressed(short button) {
        if (button == 0) {
            buffer.up();
        } else if (button == 1) {
            buffer.down();
        }

    }
    
    public void printMessage(ChatMessage message) {
        try {
            initlcd();
            lcd.clearDisplay();
            lcd.writeLine((short)0, (short)0, df.format(message.getTime()));
            lcd.writeLine((short)1, (short)0, message.getSender());
            lcd.writeLine((short)2, (short)0, message.getMessage());
            if (message.getMessage().length() > 20) {
                lcd.writeLine((short)3, (short)0, message.getMessage().substring(20));
            }
        } catch (Exception e) {
        }
    }

    public void onMessage(ChatMessage message) {
        this.buffer.onMessage(message);
    }
}

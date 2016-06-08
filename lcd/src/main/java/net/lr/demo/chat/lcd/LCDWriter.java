package net.lr.demo.chat.lcd;

import java.text.DateFormat;
import java.util.Locale;

import com.tinkerforge.BrickletLCD20x4;

import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

public class LCDWriter implements ChatListener {
    private BrickletLCD20x4 lcd;
    private DateFormat df;

    public LCDWriter(BrickletLCD20x4 lcd) {
        this.lcd = lcd;
        this.df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.ENGLISH);
    }

    public void onMessage(ChatMessage message) {
        try {
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
}

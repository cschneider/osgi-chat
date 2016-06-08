package net.lr.demo.chat.lcd;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;

import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

@Component(property = { //
                        "service.exported.interfaces=*"
}, immediate = true)
public class LCDChatListener implements ChatListener {
    private IPConnection ipcon;
    private BrickletLCD20x4 lcd;
    private ChatBuffer buffer;

    @Activate
    public void activate() throws Exception {
        ipcon = new IPConnection();
        lcd = new BrickletLCD20x4("rV1", ipcon);
        ipcon.connect("localhost", 4223);
        lcd.backlightOn();
        lcd.clearDisplay();

        buffer = new ChatBuffer(new LCDWriter(lcd));

        BrickletMotionDetector motion = new BrickletMotionDetector("sHt", ipcon);
        motion.addMotionDetectedListener(new BrickletMotionDetector.MotionDetectedListener() {

            public void motionDetected() {
                buffer.onMessage(new ChatMessage("sensor", "Motion detected"));
            }
        });

        lcd.addButtonPressedListener(new BrickletLCD20x4.ButtonPressedListener() {

            public void buttonPressed(short button) {
                if (button == 0) {
                    buffer.up();
                } else if (button == 1) {
                    buffer.down();
                }

            }
        });
    }

    @Deactivate
    public void deactivate() throws NotConnectedException {
        ipcon.disconnect();
    }

    @Override
    public void onMessage(ChatMessage message) {
        buffer.onMessage(message);
    }
}

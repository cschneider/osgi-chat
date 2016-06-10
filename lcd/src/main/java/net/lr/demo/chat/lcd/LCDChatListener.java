package net.lr.demo.chat.lcd;

import java.util.UUID;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;

import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

@Component(property = //
{
 "service.exported.interfaces=*"
}, name = "lcd", immediate = true)
@Designate(ocd = LCDChatListener.TfConfig.class)
public class LCDChatListener implements ChatListener {
    private IPConnection ipcon;
    private BrickletLCD20x4 lcd;
    private ChatBuffer buffer;
    private String id = UUID.randomUUID().toString();
    
    @Reference
    ChatBroker broker;

    @ObjectClassDefinition(name = "Tinkerforge config")
    @interface TfConfig {
        String host() default "localhost";
        int port() default 4223;
    }

    @Activate
    public void activate(TfConfig config) throws Exception {
        ipcon = new IPConnection();
        lcd = new BrickletLCD20x4("rV1", ipcon);
        ipcon.connect(config.host(), config.port());
        lcd.backlightOn();
        lcd.clearDisplay();

        buffer = new ChatBuffer(new LCDWriter(lcd));

        BrickletMotionDetector motion = new BrickletMotionDetector("sHt", ipcon);
        motion.addMotionDetectedListener(new BrickletMotionDetector.MotionDetectedListener() {

            public void motionDetected() {
                broker.onMessage(new ChatMessage(id, "sensor", "Motion detected"));
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

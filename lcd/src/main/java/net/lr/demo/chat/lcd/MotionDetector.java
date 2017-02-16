package net.lr.demo.chat.lcd;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetector.MotionDetectedListener;
import com.tinkerforge.IPConnection;

import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatMessage;

@Component(immediate = true, service=MotionDetector.class)
public class MotionDetector {

    @Reference
    TinkerConnect tinkerConnect;

    @Reference
    ChatBroker broker;

    private BrickletMotionDetector motion;
    private MotionDetectedListener listener;

    @Activate
    public void activate() throws Exception {
        IPConnection ipcon = tinkerConnect.getConnection();
        motion = new BrickletMotionDetector("sHt", ipcon);
        listener = () -> {
            broker.onMessage(new ChatMessage("sensor", "sensor", "Motion detected"));
        };
        motion.addMotionDetectedListener(listener);
    }
    
    @Deactivate
    public void deActivate() throws Exception {
        motion.removeMotionDetectedListener(listener);
    }

}

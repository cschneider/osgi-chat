package net.lr.demo.chat.lcd;

import java.util.UUID;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.IPConnection;

import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatMessage;

@Component(immediate = true, service=MotionDetector.class)
public class MotionDetector {
    private static Logger LOG = LoggerFactory.getLogger(MotionDetector.class);

    @Reference
    TinkerConnect tinkerConnect;

    @Reference
    ChatBroker broker;

    private String id = UUID.randomUUID().toString();

    @Activate
    public void activate() throws Exception {
        IPConnection ipcon = tinkerConnect.getConnection();
        BrickletMotionDetector motion = new BrickletMotionDetector("sHt", ipcon);
        motion.addMotionDetectedListener(() -> broker.onMessage(new ChatMessage(id, "sensor", "Motion detected")));
    }
}

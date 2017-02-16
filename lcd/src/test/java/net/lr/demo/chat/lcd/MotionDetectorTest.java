package net.lr.demo.chat.lcd;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import net.lr.demo.chat.lcd.TinkerConnect.TfConfig;
import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatMessage;

public class MotionDetectorTest {

    /**
     * To actually test this you need the motion detector to be attached and cause a motion during the test  
     */
    @Ignore
    @Test
    public void testMotion() throws Exception {
        TinkerConnect connect = new TinkerConnect();
        connect.activate(dummyConfig());
        
        ChatBroker broker = mock(ChatBroker.class);
        ArgumentCaptor<ChatMessage> messageCap = forClass(ChatMessage.class);
        doNothing().when(broker).onMessage(messageCap.capture());
        
        MotionDetector detector = new MotionDetector();
        detector.broker = broker;
        detector.tinkerConnect = connect;
        detector.activate();
        await().until(() -> messageCap.getAllValues().size(), greaterThan(0));
        ChatMessage message = messageCap.getValue();
        assertThat(message.getMessage(), equalTo("Motion detected"));
        detector.deActivate();
    }

    private TfConfig dummyConfig() {
        TfConfig tfconfig = Mockito.mock(TfConfig.class);
        when(tfconfig.host()).thenReturn("localhost");
        when(tfconfig.port()).thenReturn(4223);
        return tfconfig;
    }
}

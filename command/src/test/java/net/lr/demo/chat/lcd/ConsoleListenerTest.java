package net.lr.demo.chat.lcd;

import org.junit.Test;

import net.lr.demo.chat.service.ChatMessage;

public class ConsoleListenerTest {

    @Test
    public void onMessage() {
        SendCommand listener = new SendCommand();
        ChatMessage message = new ChatMessage("1", "chris", "Test");
        listener.onMessage(message);
    }
    
}

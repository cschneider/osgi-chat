package net.lr.demo.chat.lcd;

import org.junit.Test;

import net.lr.demo.chat.command.ShellListener;
import net.lr.demo.chat.service.ChatMessage;

public class ConsoleListenerTest {

    @Test
    public void onMessage() {
        ShellListener listener = new ShellListener();
        ChatMessage message = new ChatMessage("1", "chris", "Test");
        listener.onMessage(message);
    }
    
}

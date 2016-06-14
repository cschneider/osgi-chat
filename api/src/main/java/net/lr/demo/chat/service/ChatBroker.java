package net.lr.demo.chat.service;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ChatBroker.class, immediate = true)
public class ChatBroker {
    @Reference
    volatile List<ChatListener> listeners;

    public void onMessage(ChatMessage message) {
        listeners.parallelStream().forEach((listener)->send(message, listener)); 
    }

    private static void send(ChatMessage message, ChatListener listener) {
        try {
            listener.onMessage(message);
        } catch (Exception e) {
            // Ignore
        }
    }
    
}

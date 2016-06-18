package net.lr.demo.chat.service;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ChatBroker.class, immediate = true)
public class ChatBroker {
    private static Logger LOG = LoggerFactory.getLogger(ChatBroker.class);

    @Reference
    volatile List<ChatListener> listeners;

    public void onMessage(ChatMessage message) {
        listeners.parallelStream().forEach((listener)->send(message, listener)); 
    }

    private static void send(ChatMessage message, ChatListener listener) {
        try {
            listener.onMessage(message);
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }
    
}

package net.lr.demo.chat.service;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component(service=ChatBroker.class)
public class ChatBroker {
    @Reference(cardinality = ReferenceCardinality.MULTIPLE)
    volatile List<ChatListener> listeners;

    public void onMessage(ChatMessage message) {
        for (ChatListener listener : listeners) {
            try {
            listener.onMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
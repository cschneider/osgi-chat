package net.lr.demo.chat.lcd;

import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

@Component(service = ChatListener.class, //
    property = {
                "osgi.command.scope=chat", //
                "osgi.command.function=send"
    })
public class SendCommand implements ChatListener {
    @Reference
    ChatBroker broker;
    private String id = UUID.randomUUID().toString();

    public void send(String message) {
        broker.onMessage(new ChatMessage(id, "shell", message));
    }
    
    @Override
    public void onMessage(ChatMessage message) {
        if (!id.equals(message.getSenderId())) {
            System.out.println(String.format("%tT %s: %s", message.getTime(), message.getSender(),
                                         message.getMessage()));
        }
    }
}

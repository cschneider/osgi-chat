package net.lr.demo.chat.lcd;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatMessage;

@Component(service = SendCommand.class, //
    property = {
                "osgi.command.scope=chat", //
                "osgi.command.function=send"
    })
public class SendCommand {
    @Reference
    ChatBroker broker;

    public void send(String message) {
        broker.onMessage(new ChatMessage("shell", message));
    }
}

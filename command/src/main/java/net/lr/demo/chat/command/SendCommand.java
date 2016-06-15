package net.lr.demo.chat.command;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
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
    
    private String id;
    
    @Activate
    public void activate(BundleContext context) {
        this.id = "shell" + context.getProperty(Constants.FRAMEWORK_UUID);
    }

    public void send(String message) {
        broker.onMessage(new ChatMessage(id, "shell", message));
    }
    
}

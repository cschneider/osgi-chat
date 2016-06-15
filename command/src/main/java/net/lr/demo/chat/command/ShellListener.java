package net.lr.demo.chat.command;

import org.osgi.service.component.annotations.Component;

import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

@Component(property = //
{
 "service.exported.interfaces=*",
})
public class ShellListener implements ChatListener {

    @Override
    public void onMessage(ChatMessage message) {
        System.out.println(String.format("%tT %s: %s", message.getTime(), message.getSender(),
                                         message.getMessage()));
    }
}

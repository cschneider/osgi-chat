package net.lr.demo.chat.lcd;

import java.util.ArrayList;
import java.util.List;

import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

public class ChatBuffer implements ChatListener {
    private int pos;
    private List<ChatMessage> history;
    private ChatListener listener;

    public ChatBuffer(ChatListener listener) {
        this.listener = listener;
        this.history = new ArrayList<ChatMessage>();
        this.pos = -1;
    }

    public void onMessage(ChatMessage message) {
        if (pos == history.size() - 1) {
            pos ++;
            listener.onMessage(message);
        }
        history.add(message);
    }

    public void down() {
        if (pos < history.size() - 1) {
            pos++;
        }
        listener.onMessage(history.get(pos));
    }

    public void up() {
        if (pos > 0) {
            pos--;
        }
        listener.onMessage(history.get(pos));
    }
 
}

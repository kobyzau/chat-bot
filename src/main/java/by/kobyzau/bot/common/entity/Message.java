package by.kobyzau.bot.common.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long chatId;
    private int messageId;
    private LocalDateTime time;

    public Message(long id, long chatId, int messageId, LocalDateTime time) {
        this.id = id;
        this.chatId = chatId;
        this.messageId = messageId;
        this.time = time;
    }

    public Message(long chatId, int messageId, LocalDateTime time) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.time = time;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

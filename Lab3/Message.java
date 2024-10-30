import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Message {
    private Person sender;
    private Person receiver;
    private String message;
    private Timestamp whenSent;

    public Message(Person sender, Person receiver, String message){
        this.sender = Objects.requireNonNull(sender, "Sender cannot be null");
        this.receiver = Objects.requireNonNull(receiver, "reciever cannot be null");
        this.message = Objects.requireNonNull(message, "message cannot be null");
        this.whenSent = new Timestamp(new Date().getTime()); //auto generated
    }
    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = Objects.requireNonNull(sender, "Sender cannot be null");
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = Objects.requireNonNull(receiver, "reciever cannot be null");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = Objects.requireNonNull(message, "message cannot be null");
    }

    public Timestamp getWhenSent() {
        return whenSent;
    }

    public void setWhenSent(Timestamp whenSent) {
        this.whenSent = whenSent;
    }

    @Override
    public String toString(){
        return String.format("At %s, %s %s said: '%s'", whenSent, sender.getFirstName(),sender.getLastName(), message);
    }
    
}

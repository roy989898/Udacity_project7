package pom.poly.com.tabatatimer.Firebase;

/**
 * Created by User on 6/7/2016.
 */
public class Message {
    public String fromID;
    public String toID;
    public long dateTime;
    public String message;

    public Message(long dateTime, String fromID, String message, String toID) {
        this.dateTime = dateTime;//the datetime to send
        this.fromID = fromID;
        this.message = message;
        this.toID = toID;
    }

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Message) {
            Message oMessage = (Message) o;
            if (oMessage.fromID.equals(this.fromID) && oMessage.toID.equals(this.toID) && oMessage.dateTime == this.dateTime && oMessage.message.equals(this.message)) {
                return true;
            } else {
                return false;
            }
        } else
            return super.equals(o);

    }
}

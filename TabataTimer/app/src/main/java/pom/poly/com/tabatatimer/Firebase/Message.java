package pom.poly.com.tabatatimer.Firebase;


public class Message {
    public String fromID;
    public String toID;
    public long dateTime;
    public String message;
    public String firebaseKey;

    public String fromuserName;

    public Message(long dateTime, String fromID, String message, String toID,String fromuserName) {
        this.dateTime = dateTime;//the datetime to send
        this.fromID = fromID;
        this.message = message;
        this.toID = toID;
        this.fromuserName=fromuserName;
    }

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Message) {
            Message oMessage = (Message) o;
            return oMessage.fromID.equals(this.fromID) && oMessage.toID.equals(this.toID) && oMessage.dateTime == this.dateTime && oMessage.message.equals(this.message);
        } else
            return super.equals(o);

    }
}

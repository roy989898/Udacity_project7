package pom.poly.com.tabatatimer.Firebase;

/**
 * Created by User on 29/6/2016.
 */
public class User {
    public int likeNumber;
    public String userID;
    public String email;
    public String userName;
    public String profileLink;
    public long totaltime;
    public long lastupdateTime_totalTime;


    public User(String email, int likeNumber, String profileLink, long totaltime, String userID, String userName, long lastupdateTime_totalTime) {
        this.email = email;
        this.likeNumber = likeNumber;
        this.profileLink = profileLink;
        this.totaltime = totaltime;
        this.userID = userID;
        this.userName = userName;
        this.lastupdateTime_totalTime = lastupdateTime_totalTime;
    }

    public User() {
    }

}

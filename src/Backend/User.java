package Backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String dateOfBirth;
    private String status;
    private String profilePicture;
    private String CoverPicture;
    private ArrayList<User> friends;
    private static final AtomicInteger counter = new AtomicInteger(0);

    public User(String id, String username, String password, String email, LocalDate dateOfBirth, String status, String profilePicture, String CoverPicture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = String.valueOf(dateOfBirth);
        this.status = status;
        this.profilePicture = profilePicture;
        this.CoverPicture = CoverPicture;
        this.friends = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public String getId() {
        return id;
    }
    public String getprofilePicture() {
        return profilePicture;
    }
    public String getCoverPicture() {
        return CoverPicture;
    }
    public void setCoverPicture(String coverPicture) {
        CoverPicture = coverPicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public void removeFriend(User user) {
        for (User friend : friends) {
            if (friend.getId().equals(user.getId())) {
                friends.remove(friend);
                return;
            }
        }
    }

    public static String generateID() {
        return "CID-" + counter.incrementAndGet();
    }
    public Content makePost(String username,String text, String picPath){
       Content cc = new Post(generateID(),getId(),text, LocalDateTime.now(),picPath,"post");
        return cc;
    }
}

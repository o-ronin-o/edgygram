package Backend;


import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String dateOfBirth;
    private String status;
    private String profilePicture ;
    private String coverPicture ;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private ArrayList<String> notfications=new ArrayList<>();


//    public User(String id, String username, String password, String email, String dateOfBirth, String status, String profilePicture, String CoverPicture, String bio) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.dateOfBirth = String.valueOf(dateOfBirth);
//        this.status = status;
//        this.profilePicture = profilePicture;
//        this.coverPicture = CoverPicture;
//        this.bio = bio;
//        }

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.dateOfBirth = builder.dateOfBirth;
        this.status = builder.status;
        this.profilePicture = builder.profilePicture;
        this.coverPicture = builder.coverPicture;
        this.bio = builder.bio;
    }
    public String getUsername() {
        return username;
    }

    public String getBio() {
        return this.bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
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

    public String getId() {
        return this.id;
    }
    public String getprofilePicture() {
        return this.profilePicture;
    }
    public String getCoverPicture() {
        return coverPicture;
    }
    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<String> getNotfications() {
        return notfications;
    }

    public void addtNotfications(String notfication) {
        notfications.add(notfication);
    }
    public void removeNotfications(String notification) {
        System.out.println("the sent notfication is "+notification);
        for (int i = 0; i < notfications.size(); i++) {
            System.out.println("comparing with"+notfications.get(i));
            if (notfications.get(i).equals(notification)) {
                notfications.remove(i);
                break;
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
    public static class UserBuilder {
        private String id;
        private String username;
        private String password;
        private String email;
        private String bio;
        private String dateOfBirth;
        private String status="offline";
        private String profilePicture="defaultProfile.jpeg" ;
        private String coverPicture="defaultCover.jpeg" ;
        private ArrayList<String> notfications=new ArrayList<>();

        public UserBuilder Id(String id) {
            this.id = id;
            return this;
        }
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }
        public UserBuilder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        public UserBuilder status(String status) {
            this.status = status;
            return this;
        }
        public UserBuilder profilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }
        public UserBuilder coverPicture(String coverPicture) {
            this.coverPicture = coverPicture;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}

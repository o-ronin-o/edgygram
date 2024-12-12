package Backend;

import Backend.Friends.FriendRequest;
import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.FriendsManagement;

import java.util.ArrayList;

public class SearchEngine {
    private UserDatabase userDatabase;
    public SearchEngine() {
        this.userDatabase = new UserDatabase();
    }
    public ArrayList<User> searchUser(User user,String query ) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> allUsers = userDatabase.getAll();
        FriendRequestManagement friendRequestManagement= new FriendRequestManagement(userDatabase,new FriendsDatabase());

        for (User userr : allUsers) {
            //check if the username of the user contain the query and check that on one blocked the other
            if (userr.getUsername().toLowerCase().contains(query.toLowerCase()) && !friendRequestManagement.isBlocked(userr.getId(),user.getId()) && !friendRequestManagement.isBlocked(user.getId(),userr.getId()) ) {
                users.add(userr);
            }
        }
        return users;
    }

    public ArrayList<String> getResultsInFormat(ArrayList<User> users) {
        ArrayList<String> results = new ArrayList<>();
        for (User user : users) {
            results.add(user.getprofilePicture()+","+user.getUsername());
        }
        return results;
    }
}

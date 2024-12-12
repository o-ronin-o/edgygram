package Backend;

import Backend.Friends.FriendsManagement;

import java.util.ArrayList;

public class SearchEngine {
    private UserDatabase userDatabase;
    public SearchEngine() {
        this.userDatabase = new UserDatabase();
    }
    public ArrayList<User> searchUser(String query ) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> allUsers = userDatabase.getAll();
        for (User user : allUsers) {
            if (user.getUsername().toLowerCase().contains(query.toLowerCase())) {
                users.add(user);
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

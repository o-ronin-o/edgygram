package Backend;

import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Groups.GroupData;
import Backend.Groups.GroupsDatabase;

import javax.swing.*;
import java.util.ArrayList;

public class SearchEngine {
    private static SearchEngine instance; //Singleton instance
    private UserDatabase userDatabase;

    private SearchEngine() {
        this.userDatabase = new UserDatabase();
    }

    //public method to provide access to the singleton instance
    public static synchronized SearchEngine getInstance() {
        if (instance == null) {
            synchronized (SearchEngine.class) {
                if (instance == null) {
                    instance = new SearchEngine();
                }
            }
        }
        return instance;
    }

    public ArrayList<User> searchUser(User user, String query) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> allUsers = userDatabase.getAll();
        FriendRequestManagement friendRequestManagement = new FriendRequestManagement(userDatabase, new FriendsDatabase());

        for (User userr : allUsers) {
            // Check if the username of the user contains the query and ensure no blocking exists between users
            if (userr.getUsername().toLowerCase().contains(query.toLowerCase()) &&
                    !friendRequestManagement.isBlocked(userr.getId(), user.getId()) &&
                    !friendRequestManagement.isBlocked(user.getId(), userr.getId())) {
                users.add(userr);
            }
        }
        return users;
    }
    public ArrayList<GroupData> searchGroup(String query) {
        ArrayList<GroupData> groups = new ArrayList<>();
        GroupsDatabase groupsDatabase = new GroupsDatabase();
        ArrayList<GroupData> allGroups = groupsDatabase.getAll();
        for (GroupData groupr : allGroups) {
            if(groupr.getGroupName().toLowerCase().contains(query.toLowerCase())) {
                groups.add(groupr);
            }
        }
        return groups;
    }
    public ArrayList<String> getUserResultsInFormat(ArrayList<User> users) {
        ArrayList<String> results = new ArrayList<>();
        for (User user : users) {
            results.add(user.getprofilePicture() + "," + user.getUsername());
        }
        return results;
    }
    public ArrayList<String> getGroupResultsInFormat(ArrayList<GroupData> groups) {
        ArrayList<String> results = new ArrayList<>();
        for(GroupData group : groups) {
            results.add(group.getGroupPicture() + "," + group.getGroupName());
        }
        return results;
    }
}
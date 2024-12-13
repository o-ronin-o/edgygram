package Backend;

import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.FriendsManagement;
import Backend.Friends.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class testFriend {
    public static void main(String[] args) {
        // Initialize databases
        UserDatabase userDatabase =  UserDatabase.getInstance();
        FriendsDatabase friendsDatabase = new FriendsDatabase();

        // Initialize Friend Management and Request Management
        FriendsManagement friendsManagement = new FriendsManagement(userDatabase, friendsDatabase);
        FriendRequestManagement friendRequestManagement = new FriendRequestManagement(userDatabase, friendsDatabase);
        ArrayList<User> users= userDatabase.getAll();
        ArrayList<User> suggest=friendsManagement.suggestFriends(users.get(0));
        System.out.println("Suggested "+ suggest.get(0).getUsername());
        friendRequestManagement.sendFriendRequest(users.get(1),users.get(3));
        ArrayList<FriendRequest> pending =friendRequestManagement.getFriendRequests(users.get(3));
        System.out.println("pending "+pending.get(0).getSenderId());


    }
}

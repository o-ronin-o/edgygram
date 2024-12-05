package Backend.Friends;

import java.util.ArrayList;

public class FriendData {
    private ArrayList<String> friends;
    private ArrayList<String> blockedUsers;
    private ArrayList<FriendRequest> PendingRequests;

    public FriendData() {
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
        PendingRequests = new ArrayList<>();
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public ArrayList<FriendRequest> getPendingRequests() {
        return PendingRequests;
    }

    public void setPendingRequests(ArrayList<FriendRequest> pendingRequests) {
        PendingRequests = pendingRequests;
    }
}
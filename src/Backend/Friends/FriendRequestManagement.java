package Backend.Friends;

import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendRequestManagement {
    private UserDatabase userDatabase;
    private FriendsDatabase friendsDatabase;
    private HashMap<String,FriendData> friendsMap;

    public FriendRequestManagement(UserDatabase userDatabase, FriendsDatabase friendsDatabase) {
        this.userDatabase = userDatabase;
        this.friendsDatabase = friendsDatabase;
        this.friendsMap = friendsDatabase.loadFriendData();
    }

    //send friend request
    public void sendFriendRequest(User sender, User receiver){
        //check that both of the users have a part in the the json file
        friendsMap.putIfAbsent(sender.getId(), new FriendData());
        friendsMap.putIfAbsent(receiver.getId(), new FriendData());
        //check if someone blocks the other
        if(isBlocked(sender.getId(),receiver.getId())){
            JOptionPane.showMessageDialog(null, "Cannot send friend request. You have blocked this user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isBlocked(receiver.getId(), sender.getId())) {
            JOptionPane.showMessageDialog(null, "Cannot send friend request. You are blocked by this user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        FriendData receiverFriendData=friendsMap.getOrDefault(receiver.getId(),new FriendData());
        if(receiverFriendData.getFriends().contains(sender.getId())){
            JOptionPane.showMessageDialog(null, receiver.getUsername()+" is already friend", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //check if the request already exist
        for(FriendRequest request:receiverFriendData.getPendingRequests()){
            if(request.getSenderId().equals(sender.getId())){
                JOptionPane.showMessageDialog(null, "Friend request already sent.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        //Add the request
        FriendRequest newRequest=new FriendRequest(sender.getId(),receiver.getId());
        receiverFriendData.getPendingRequests().add(newRequest);
        friendsMap.put(receiver.getId(),receiverFriendData);
        friendsDatabase.saveAll(friendsMap);
        JOptionPane.showMessageDialog(null, "Friend request sent.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Accept a friend request
    public void acceptFriendRequest(User sender, User receiver){
        FriendData recieverFriendData=friendsMap.getOrDefault(receiver.getId(),new FriendData());
        FriendRequest acceptedRequest=null;
        // find and remove the request
        for(FriendRequest request:recieverFriendData.getPendingRequests()){
            if(request.getSenderId().equals(sender.getId())){
                acceptedRequest=request;
                break;
            }
        }
        if(acceptedRequest!=null){
            // update friends data
            recieverFriendData.getPendingRequests().remove(acceptedRequest);
            recieverFriendData.getFriends().add(sender.getId());
            FriendData senderFriendData=friendsMap.getOrDefault(sender.getId(),new FriendData());
            senderFriendData.getFriends().add(receiver.getId());
            friendsMap.put(receiver.getId(), recieverFriendData);
            friendsMap.put(sender.getId(), senderFriendData);
            friendsDatabase.saveAll(friendsMap);
            JOptionPane.showMessageDialog(null, "Friend request accepted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Friend request not accepted.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Decline a friend request
    public boolean declineFriendRequest(User sender, User receiver){
        FriendData receiverFriendData=friendsMap.getOrDefault(receiver.getId(),new FriendData());
        FriendRequest declinedRequest=null;
        // Find and remove the request
        for(FriendRequest request:receiverFriendData.getPendingRequests()){
            if(request.getSenderId().equals(sender.getId())){
                declinedRequest=request;
                break;
            }
        }
        if(declinedRequest!=null){
            receiverFriendData.getPendingRequests().remove(declinedRequest);
            friendsMap.put(receiver.getId(),receiverFriendData);
            friendsDatabase.saveAll(friendsMap);
            JOptionPane.showMessageDialog(null, "Friend request declined.", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    private boolean isBlocked(String blockerId, String toCheckId) {
        FriendData blocked = friendsMap.getOrDefault(blockerId, new FriendData());
        return blocked.getBlockedUsers().contains(toCheckId);
    }

    public ArrayList<FriendRequest> getFriendRequests(User user) {
        FriendData userFriendData = friendsMap.getOrDefault(user.getId(), new FriendData());
        return userFriendData.getPendingRequests();
    }
}

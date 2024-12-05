package Backend.Friends;

import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsManagement {
    private UserDatabase userDatabase;
    private FriendsDatabase friendsDatabase;
    private HashMap<String,FriendData> friendsMap;
    public FriendsManagement(UserDatabase userDatabase, FriendsDatabase friendsDatabase) {
        this.userDatabase = userDatabase;
        this.friendsDatabase = friendsDatabase;
        friendsMap = friendsDatabase.loadFriendData();

    }
    public ArrayList<User> suggestFriends(User user){
        ArrayList<User> allUsers= userDatabase.getAll();
        ArrayList<User> suggestedUsers = new ArrayList<>();
        FriendData friendData=friendsMap.getOrDefault(user.getId(),new FriendData());
        ArrayList<String> userFriends = friendData.getFriends();
        //هنلف علي كل اليوزرز
        for(User suggestion:allUsers){
            String suggestionId = suggestion.getId();
            //هنسكيب لو هو هو نفس اليوزر او فريند عنده او عامله بلوك
            if(user.getId().equals(suggestionId)|| userFriends.contains(suggestionId)||friendData.getBlockedUsers().contains(suggestionId)){
                continue;
            }
            // هتجيب الفريندس داتا بتاعت اليوزر المقتلاح دلوقتي
            FriendData suggestionFriendData=friendsMap.getOrDefault(suggestionId,new FriendData());
            ArrayList<String> suggestionFriends = suggestionFriendData.getFriends();
            //هنعمل ليست نحط فيهاالفريندس المشتركة
            ArrayList<String> mutualFriends = new ArrayList<>(userFriends);
            mutualFriends.retainAll(suggestionFriends);
            if(!mutualFriends.isEmpty()){ //لو في فريندس مشتركة هريترن الليست
                suggestedUsers.add(suggestion);
            }
        }
        return suggestedUsers;
    }

    //send friend request
    public void sendFriendRequest(User sender, User receiver){

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
            friendsMap.put(receiver.getId(),senderFriendData);
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

    // Remove a friend
    public boolean removeFriend(User user, User friend){
        FriendData userFriendData=friendsMap.getOrDefault(user.getId(),new FriendData());
        FriendData friendFriendData=friendsMap.getOrDefault(friend.getId(),new FriendData());
        if (userFriendData.getFriends().contains(friend.getId())) {
        userFriendData.getFriends().remove(friend.getId());
        friendFriendData.getFriends().remove(user.getId());
        friendsMap.put(user.getId(),userFriendData);
        friendsMap.put(friend.getId(),friendFriendData);
        friendsDatabase.saveAll(friendsMap);
        JOptionPane.showMessageDialog(null, "Friend removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        return true;
        }
        return false;
    }

    //Block a friend
    public void block(User user, User friend){
      FriendData userFriendData=friendsMap.getOrDefault(user.getId(),new FriendData());
      userFriendData.getBlockedUsers().add(friend.getId());
      //remove from friend list and pending request if exist
      removeFriend(user,friend);
      declineFriendRequest(user,friend);
      JOptionPane.showMessageDialog(null, "Friend request blocked.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
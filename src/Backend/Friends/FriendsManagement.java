package Backend.Friends;

import Backend.User;
import Backend.UserDatabase;
import Backend.Friends.FriendRequestManagement;
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
        FriendData friendFriendData = friendsMap.getOrDefault(friend.getId(), new FriendData());
      userFriendData.getBlockedUsers().add(friend.getId());

      //remove friendship if they are friends
        if (userFriendData.getFriends().contains(friend.getId())) {
            userFriendData.getFriends().remove(friend.getId());
            friendFriendData.getFriends().remove(user.getId());
        }
        // Remove friend request if added
      userFriendData.getPendingRequests().removeIf(friendRequest -> friendRequest.getSenderId().equals(user.getId()));
        friendFriendData.getPendingRequests().removeIf(friendRequest -> friendRequest.getReceiverId().equals(user.getId()));
        //save new data
      friendsMap.put(user.getId(),userFriendData);
      friendsMap.put(friend.getId(),friendFriendData);
      friendsDatabase.saveAll(friendsMap);
      JOptionPane.showMessageDialog(null, "User blocked.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isFriend(User user, User friend){
        FriendData userFriendData=friendsMap.getOrDefault(user.getId(),new FriendData());
        if(userFriendData.getFriends().contains(friend.getId())){
            return true;
        }
        return false;
    }


}

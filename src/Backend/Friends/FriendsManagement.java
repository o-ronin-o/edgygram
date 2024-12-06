package Backend.Friends;

import Backend.*;
import Backend.Friends.FriendRequestManagement;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsManagement {
    private Database<User> userDatabase;
    private FriendsDatabase friendsDatabase;
    private Database<Content> contentDatabase;
    private HashMap<String,FriendData> friendsMap;

    public FriendsManagement(Database<User> userDatabase, FriendsDatabase friendsDatabase) {
        this.userDatabase = userDatabase;
        this.friendsDatabase = friendsDatabase;
        friendsMap = friendsDatabase.loadFriendData();
        contentDatabase= new ContentDatabase();
    }

    public ArrayList<User> suggestFriends(User user){
        ArrayList<User> allUsers= userDatabase.getAll();
        ArrayList<User> suggestedUsers = new ArrayList<>();
        FriendData friendData=friendsMap.getOrDefault(user.getId(),new FriendData());
        ArrayList<String> userFriends = friendData.getFriends();
        FriendRequestManagement friendRequestManagement= new FriendRequestManagement(userDatabase,friendsDatabase);
        // هنجيب الفريند ريكويست لليوزر
        ArrayList<FriendRequest> pendingRequests = friendRequestManagement.getFriendRequests(user);
        ArrayList<String> pendingRequestIds = new ArrayList<>();
        System.out.println(pendingRequests.size());
        for (FriendRequest request : pendingRequests) {
            // هنحط الايدي بتاع الي باعت او الي جاله فريند ريكويست
            System.out.println("user id "+user.getId()+" request sender id "+request.getSenderId());
            if (request.getSenderId().equals(user.getId())) {
                pendingRequestIds.add(request.getReceiverId());
                System.out.println("request sender found ");
            } else if (request.getReceiverId().equals(user.getId())) {
                pendingRequestIds.add(request.getSenderId());
                System.out.println("request receiver found ");
            }
        }
        //هنلف علي كل اليوزرز
        for(User suggestion:allUsers){
            String suggestionId = suggestion.getId();
            //هنسكيب لو هو هو نفس اليوزر او فريند عنده او عامله بلوك
            if(user.getId().equals(suggestionId)|| userFriends.contains(suggestionId)||friendData.getBlockedUsers().contains(suggestionId)|| pendingRequestIds.contains(suggestionId)){
                continue;
            }
            ArrayList<FriendRequest> suggestionRequests = friendRequestManagement.getFriendRequests(suggestion);
            for (FriendRequest request : pendingRequests) {
                // هنحط الايدي بتاع الي باعت او الي جاله فريند ريكويست
                System.out.println("user id "+user.getId()+" request sender id "+request.getSenderId());
                if (request.getSenderId().equals(user.getId())) {
                    System.out.println("request sender found ");
                    continue;

                } else if (request.getReceiverId().equals(user.getId())) {
                    System.out.println("request receiver found ");
                    continue;
                }
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

    public ArrayList<User> getFriends(User user){
        //returns all friend of user
        ArrayList<User> friends = new ArrayList<>();
        FriendData friendData=friendsMap.getOrDefault(user.getId(),new FriendData());
        ArrayList<String> friendsId=friendData.getFriends();
        ArrayList<User> allUsers= userDatabase.getAll();
        for(User friend:allUsers){
            if(friendsId.contains(friend.getId())){
                friends.add(friend);

            }
        }
        return friends;
    }
    public ArrayList<String> displayList(ArrayList<User> friends){
        //puts every user in the arraylist in the needed format
        ArrayList<String> friendsList = new ArrayList<>();
        for(User user:friends){
            friendsList.add(user.getprofilePicture()+","+user.getUsername()+","+user.getStatus());
        }
        return friendsList;
    }

    public ArrayList<Content> getFriendsPosts(User user){
        ArrayList<User> friends=getFriends(user);
        if (friends.isEmpty())  // if user Has no friends
            return new ArrayList<>();
            ArrayList<String> friendsId=new ArrayList<>();
        //get friends ids
        for(User userFriend:friends){
            friendsId.add(userFriend.getId());
        }
        ContentDatabase database=(ContentDatabase) contentDatabase;
        ArrayList<Content> allPosts= database.getAllPosts();
        ArrayList<Content> friendsPosts=new ArrayList();
        //iterate over all posts if the author id is in the list of friends id add post
        for(Content post:allPosts){
            if(friendsId.contains(post.getAuthorId()))
                friendsPosts.add(post);
        }
        return friendsPosts;
    }

    public ArrayList<Content> getFriendsStories(User user ){
        ArrayList<User> friends=getFriends(user);
        if (friends.isEmpty())  // if user Has no friends
            return new ArrayList<>();
        ArrayList<String> friendsId=new ArrayList<>();
        //get friends ids
        for(User userFriend:friends){
            friendsId.add(userFriend.getId());
        }
        ContentDatabase database=(ContentDatabase) contentDatabase;
        ArrayList<Content> allStories= database.getAllStories();
        ArrayList<Content> friendsStories=new ArrayList();
        //iterate over all posts if the author id is in the list of friends id add post
        for(Content storie:allStories){
            if(friendsId.contains(storie.getAuthorId()))
                friendsStories.add(storie);
        }
        return friendsStories;
    }
}

package Backend.Groups;

import Backend.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Group {
    private String groupId;
    private String groupName;
    private String groupDescription;
    private String groupPicture;
    private ArrayList<String> groupMembersId= new ArrayList<>();;
    private String primaryAdmin;
    private ArrayList<String> Admins= new ArrayList<>();;
    private ArrayList<String> joinRequests= new ArrayList<>();

    public Group( String groupName, String groupDescription, String groupPicture, String primaryAdmin) {
        this.groupId = generateGroupId();
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupPicture = groupPicture;
        this.primaryAdmin = primaryAdmin;
        this.Admins.add(primaryAdmin);
        this.groupMembersId.add(primaryAdmin);
    }
    private String generateGroupId() {
        // Define the characters to use in the ID
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder groupId = new StringBuilder();

        // Generate 9 random characters
        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(characters.length());
            groupId.append(characters.charAt(index));
        }

        return groupId.toString();
    }
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPicture() {
        return groupPicture;
    }

    public void setGroupPicture(String groupPicture) {
        this.groupPicture = groupPicture;
    }

    public ArrayList<String> getGroupMembers() {
        return groupMembersId;
    }

    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembersId = groupMembers;
    }

    public String getPrimaryAdminId() {
        return primaryAdmin;
    }

    public void setPrimaryAdminId(String newAdmin) {
        this.primaryAdmin = newAdmin;
    }

    public ArrayList<String> getAdmins() {
        return Admins;
    }

    public void setAdmins(ArrayList<String> admins) {
        Admins = admins;
    }

    public ArrayList<String> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(ArrayList<String> joinRequests) {
        this.joinRequests = joinRequests;
    }

    public boolean addMember(User user) {
        if (groupMembersId.isEmpty()) {
            groupMembersId = new ArrayList<>();
        }
        if (!groupMembersId.contains(user.getId())) {
            groupMembersId.add(user.getId());
            return true;
        }
        return false;
    }
    public boolean removeMember(User user) {
        if (groupMembersId.contains(user.getId()) && !user.getId().equals(primaryAdmin)) {
            groupMembersId.remove(user.getId());
            if(Admins.contains(user.getId())) {
                Admins.remove(user.getId());
            }
            return true;
        }
        return false;
    }
    // Promote a user to admin
    public boolean promoteToAdmin(User user) {
        System.out.println("group members: "+groupMembersId +"and the user: "+user.getId());
        System.out.println("user is in members : "+groupMembersId.contains(user));
        if (!Admins.contains(user.getId()) && groupMembersId.contains(user.getId())) {
            System.out.println("added to admin");
            Admins.add(user.getId());
            return true;
        }
        return false;
    }

    // Demote an admin to member
    public boolean demoteFromAdmin(User user) {
        if (Admins.contains(user.getId()) && !user.getId().equals(primaryAdmin)) {
            Admins.remove(user.getId());
            return true;
        }
        return false;
    }

    // Check if a user is a primary admin
    public boolean isPrimaryAdmin(String userId) {
        return primaryAdmin.equals(userId);
    }

    // Check if a user is an admin
    public boolean isAdmin(User user) {
        return Admins.contains(user.getId());
    }

}

package Backend.Groups;

import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupJoinRequestsManagement {
    private  Group group;
    private  UserDatabase userDatabase;
    private GroupDatabase groupDatabase;
    public GroupJoinRequestsManagement(Group group) {
        this.group = group;
        this.userDatabase =  UserDatabase.getInstance();
        this.groupDatabase = new GroupDatabase();
    }
    // send a join request
    public boolean sendJoinRequest(User user) {
        GroupManagement groupManagement= GroupManagement.getInstance();
        if(!groupManagement.isMember(group.getGroupId(), user)) {
            group.getJoinRequests().add(user.getId());
            saveGroupData();
            return true;
        }
            JOptionPane.showMessageDialog(null, "You are already a member in the group", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    // Approve a join request
    // Approve a join request
    public boolean approveJoinRequest(String userId) {
        System.out.println("Attempting to approve join request for userId: " + userId);

        if (group.getJoinRequests().contains(userId)) {
            group.getJoinRequests().remove(userId);
            User user = userDatabase.getById(userId);
            if (user != null) {
                group.addMember(user);
                saveGroupData();
                System.out.println("Join request approved. Current members: " + group.getGroupMembers());
                return true;
            } else {
                System.out.println("User not found in database for userId: " + userId);
            }
        } else {
            System.out.println("UserId not found in join requests: " + userId);
        }
        return false;
    }

    // Reject a join request
    public boolean rejectJoinRequest(String userId) {
        System.out.println("Attempting to reject join request for userId: " + userId);

        if (group.getJoinRequests().remove(userId)) {
            saveGroupData();
            System.out.println("Join request rejected for userId: " + userId);
            return true;
        } else {
            System.out.println("UserId not found in join requests: " + userId);
        }
        return false;
    }

    // View all join requests
    public ArrayList<String> viewJoinRequests() {
        return group.getJoinRequests();
    }
    // Save group data to the database
    private void saveGroupData() {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        groups.put(group.getGroupId(), group);
        groupDatabase.saveAll(groups);
    }
}

package Backend.Groups;

import Backend.User;
import Backend.UserDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupJoinRequestsManagement {
    private  Group group;
    private  UserDatabase userDatabase;
    private GroupDatabase groupDatabase;
    public GroupJoinRequestsManagement(Group group) {
        this.group = group;
        this.userDatabase = new UserDatabase();
    }
    // send a join request
    public void sendJoinRequest(User user) {
        group.getJoinRequests().add(user.getId());
        saveGroupData();
    }
    // Approve a join request
    public boolean approveJoinRequest(String userId) {
        if (group.getJoinRequests().contains(userId)) {
            group.getJoinRequests().remove(userId);
            User user = userDatabase.getById(userId);
            if (user != null) {
                group.addMember(user);
                saveGroupData();
                return true;
            }
        }
        return false;
    }

    // Reject a join request
    public boolean rejectJoinRequest(String userId) {
        if (group.getJoinRequests().remove(userId)) {
            saveGroupData();
            return true;
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

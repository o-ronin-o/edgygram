package Backend.Groups;

import Backend.Database;
import Backend.User;
import Backend.UserDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupManagement {
    private GroupDatabase groupDatabase;
    private static GroupManagement instance;

    private GroupManagement() {
        this.groupDatabase = new GroupDatabase();
    }

    public static GroupManagement getInstance() {
        if (instance == null) {
            synchronized (GroupManagement.class) {
                if (instance == null) {
                    instance = new GroupManagement();
                }
            }
        }
        return instance;
    }
    public Group CreateGroup(String groupName, String groupDescription, String groupPicture, User primaryAdmin) {
        //create a new group
        Group newGroup=new Group(groupName,groupDescription,groupPicture,primaryAdmin.getId());
        //load the groups data and add our group
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        groups.put(newGroup.getGroupId(), newGroup);
        groupDatabase.saveAll(groups);
        return newGroup;
    }
    // Add a user to a group
    public boolean addUsertoGroup(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group!=null) {
            group.addMember(user);
            groupDatabase.saveAll(groups);
            return true;
        }
        return false;
    }
    // Remove a user from a group
    public boolean removeUserFromGroup(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group!=null) {
            //if the user is the primary admin make another admin the primary admin
            if(group.isPrimaryAdmin(user.getId())){
                group.setPrimaryAdminId(group.getAdmins().get(0));
            }
            //if the user is an admin delete him from admin list
            else if(group.isAdmin(user)) {
                group.demoteFromAdmin(user);
            }
            group.removeMember(user);
            groupDatabase.saveAll(groups);
            return true;
        }
        return false;
    }
    //make user admin
    public void makeAdmin(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group!=null) {
            group.promoteToAdmin(user);
            groupDatabase.saveAll(groups);
        }
    }
    // demote and admin to a regular user
    public void demoteAdmin(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group!=null) {
            group.demoteFromAdmin(user);
            groupDatabase.saveAll(groups);
        }
    }
    public Group getGroupById(String groupId) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        return groups.get(groupId);
    }
    public HashMap<String, Group> listAllGroups() {
        return groupDatabase.loadGroupData();
    }
    public ArrayList<User> getMembersAsObject(Group group) {
        ArrayList<String> membersId= group.getGroupMembers();
        ArrayList<User> members = new ArrayList<>();
        Database<User> userDatabase= new UserDatabase();
        for (String memberId : membersId) {
            User user = userDatabase.getById(memberId);  // get user by id
            if (user != null) {
                members.add(user);  // Add the User object to the list
            }
        }
        return members;
    }
    public boolean isMember(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group!=null) {
            if(group.getGroupMembers().contains(user.getId())) {
                return true;
            }
        }
        return false;
    }
}

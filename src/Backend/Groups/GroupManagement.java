package Backend.Groups;

import Backend.Database;
import Backend.User;
import Backend.UserDatabase;

import java.util.ArrayList;
import java.util.Collections;
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
    public Group createGroup(String groupName, String groupDescription, String groupPicture, User primaryAdmin) {
        Group newGroup = new Group(groupName, groupDescription, groupPicture, primaryAdmin.getId());
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        groups.put(newGroup.getGroupId(), newGroup);
        groupDatabase.saveAll(groups);
        return newGroup;
    }
    public boolean addUsertoGroup(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group != null) {
            group.addMember(user);
            groupDatabase.saveAll(groups);
            return true;
        }
        return false;
    }
    public boolean removeUserFromGroup(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group != null) {
            if(group.isPrimaryAdmin(user.getId())){
                group.setPrimaryAdminId(group.getAdmins().get(0));
            }
            else if(group.isAdmin(user)) {
                group.demoteFromAdmin(user);
            }
            group.removeMember(user);
            groupDatabase.saveAll(groups);
            return true;
        }
        return false;
    }
    public void makeAdmin(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group != null) {
            group.promoteToAdmin(user);
            groupDatabase.saveAll(groups);
        }
    }
    public void demoteAdmin(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        if(group != null) {
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
        ArrayList<String> membersId = group.getGroupMembers();
        ArrayList<User> members = new ArrayList<>();
        Database<User> userDatabase = new UserDatabase();
        for (String memberId : membersId) {
            User user = userDatabase.getById(memberId);
            if (user != null) {
                members.add(user);
            }
        }
        return members;
    }
    public boolean isMember(String groupId, User user) {
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);
        return group != null && group.getGroupMembers().contains(user.getId());
    }
    public ArrayList<String> convertToDisplayFormat(ArrayList<Group> groups) {
        ArrayList<String> displayFormat = new ArrayList<>();
        for (Group group : groups) {
            displayFormat.add(group.getGroupPicture() + "," + group.getGroupName());
        }
        return displayFormat;
    }
    public ArrayList<Group> suggestGroups(User user) {
        HashMap<String, Group> allGroups = groupDatabase.loadGroupData();
        ArrayList<Group> groupList = new ArrayList<>(allGroups.values());
        ArrayList<Group> groupsToRemove = new ArrayList<>();

        for (Group group : groupList) {
            if (group.getGroupMembers().contains(user.getId()) || group.getJoinRequests().contains(user.getId())) {
                groupsToRemove.add(group);
            }
        }
        groupList.removeAll(groupsToRemove);
        Collections.shuffle(groupList);
        int suggestionsCount = Math.min(4, groupList.size());
        return new ArrayList<>(groupList.subList(0, suggestionsCount));
    }

    // Added Methods
    public ArrayList<Group> getUserGroups(User user) {
        ArrayList<Group> userGroups = new ArrayList<>();
        HashMap<String, Group> allGroups = groupDatabase.loadGroupData();
        for (Group group : allGroups.values()) {
            if (group.getGroupMembers().contains(user.getId())) {
                userGroups.add(group);
            }
        }
        return userGroups;
    }

    public ArrayList<User> getNewMembers(Group groupId, User user) {
        ArrayList<User> newMembers = new ArrayList<>();
        HashMap<String, Group> groups = groupDatabase.loadGroupData();
        Group group = groups.get(groupId);

        if (group != null) {
            ArrayList<String> groupMembers = group.getGroupMembers();
            ArrayList<User> allMembers = getMembersAsObject(group);
            boolean isUserFound = false;
            for (User member : allMembers) {
                if (member.getId().equals(user.getId())) {
                    isUserFound = true;
                } else if (isUserFound && groupMembers.contains(member.getId())) {
                    newMembers.add(member);
                }
            }
        }
        return newMembers;
    }

    public Group getGroupByName(String groupName) {
        HashMap<String, Group> allGroups = groupDatabase.loadGroupData();
        for (Group group : allGroups.values()) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }
}
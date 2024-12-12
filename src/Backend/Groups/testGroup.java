package Backend.Groups;

import Backend.User;
import Backend.UserDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class testGroup {
    public static void main(String[] args) {
        // Step 1: Create users
        UserDatabase userDatabase = new UserDatabase();
        ArrayList<User> allUsers = userDatabase.getAll();
        User adminUser = allUsers.get(0);
        User regularUser = allUsers.get(1);
        User anotherUser = allUsers.get(2);

//        // Step 2: Create an instance of GroupManagement (Singleton)
       GroupManagement groupManagement = GroupManagement.getInstance();
//        // Step 3: Create a group with adminUser as the primary admin
 //       Group newGroup = groupManagement.CreateGroup("testGroup", "This is a test group", "group1.jpg", adminUser);
//
//        System.out.println("Created Group: " + newGroup.getGroupName());
//        System.out.println("id "+newGroup.getGroupId()+" desc "+newGroup.getGroupDescription());
//        // Step 4: Add regularUser and anotherUser to the group
 //      groupManagement.addUsertoGroup("group1", regularUser);
 //       groupManagement.addUsertoGroup("group1", anotherUser);
//        System.out.println(regularUser.getUsername()+" "+anotherUser.getUsername());
//        // Step 5: Print all members of the group
//        System.out.println("Members after addition:");
//        printGroupMembers(newGroup);
//
//        // Step 6: Make regularUser an admin
 //      groupManagement.makeAdmin("group1", regularUser);
//
//        // Step 7: Print all admins after promoting
//        System.out.println("Admins after promotion:");
//        printGroupAdmins(newGroup);
//
//        // Step 8: Demote regularUser from admin
  //     groupManagement.demoteAdmin("group1", regularUser);
//
//        // Step 9: Print all admins after demotion
//        System.out.println("Admins after demotion:");
//        printGroupAdmins(newGroup);
//
//        // Step 10: Remove anotherUser from the group
 //       groupManagement.removeUserFromGroup("group1", anotherUser);
//
//        // Step 11: Print all members after removal
//        System.out.println("Members after removal:");
//        printGroupMembers(newGroup);
    }

    private static void printGroupMembers(Group group) {
        for (String user : group.getGroupMembers()) {
            System.out.println(" - " + user + " (ID: " + user + ")");
        }
    }

    private static void printGroupAdmins(Group group) {
        for (String admin : group.getAdmins()) {
            System.out.println(" (ID: " + admin + ")");
        }
    }
}

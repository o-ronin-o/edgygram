package Backend.Groups;

import Backend.User;
import Backend.UserDatabase;
import Backend.ContentDatabase;
import Backend.Post;

import java.time.LocalDateTime;
import java.util.Scanner;

public class testGroup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize databases and managers
        UserDatabase userDatabase = new UserDatabase();
        GroupManagement groupManagement = GroupManagement.getInstance();
        ContentDatabase contentDatabase = new ContentDatabase();

        System.out.print("Enter your user ID to log in: ");
        String userId = scanner.nextLine();
        User user = userDatabase.getById(userId);

        if (user == null) {
            System.out.println("User not found. Please register first.");
            return;
        }

        System.out.println("Welcome, " + user.getUsername() + "!");
        System.out.println("What do you want to do?");
        System.out.println("1. Create a new group");
        System.out.println("2. Open an existing group");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Create a new group
            System.out.print("Enter group name: ");
            String groupName = scanner.nextLine();
            System.out.print("Enter group description: ");
            String groupDescription = scanner.nextLine();
            System.out.print("Enter group picture (optional): ");
            String groupPicture = scanner.nextLine();

            Group newGroup = groupManagement.createGroup(groupName, groupDescription, groupPicture, user);
            System.out.println("Group created successfully! Group ID: " + newGroup.getGroupId());

        } else if (choice == 2) {
            // Open an existing group
            System.out.print("Enter the Group ID: ");
            String groupId = scanner.nextLine();
            Group group = groupManagement.getGroupById(groupId);

            if (group == null) {
                System.out.println("Group not found. Please check the ID.");
                return;
            }

            System.out.println("Group Name: " + group.getGroupName());
            System.out.println("Group Description: " + group.getGroupDescription());

            System.out.println("What do you want to do?");
            System.out.println("1. Post in the group");
            System.out.println("2. Exit");
            int groupChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (groupChoice == 1) {
                System.out.print("Enter your post content: ");
                String content = scanner.nextLine();
                System.out.print("Enter picture path (optional): ");
                String picPath = scanner.nextLine();

                // Create and add a new post
                Post post = new Post(
                        "post-" + System.currentTimeMillis(),
                        user.getId(),
                        content,
                        LocalDateTime.now(),
                        picPath,
                        groupId,
                        user.getUsername(),
                        "Post"
                );
                GroupPostsDatabase groupPostsDatabase = GroupPostsDatabase.getInstance();
                groupPostsDatabase.add(post);
                System.out.println("Post added successfully to the group!");
            } else {
                System.out.println("Exiting the group.");
            }
        } else {
            System.out.println("Invalid choice. Exiting.");
        }

        scanner.close();
    }
}

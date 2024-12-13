package Frontend;

import Backend.Groups.Group;
import Backend.Groups.GroupManagement;
import Backend.Groups.GroupPostsDatabase;
import Backend.User;
import Backend.Post;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GroupNotifications extends JFrame {

    private JScrollPane GroupNotificationsScroll;
    private JPanel Container;
    private JScrollPane NotificationsScroll;
    private JList<JPanel> notificationsList;

    public GroupNotifications(User user, NewsFeedWindow newsFeedWindow) {
        setContentPane(Container);
        setTitle("Group Notifications");
        setVisible(true);
        setLocationRelativeTo(newsFeedWindow);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Using GroupManagement to get user groups and their notifications
        GroupManagement groupManagement = GroupManagement.getInstance();
        ArrayList<Group> userGroups = groupManagement.getUserGroups(user);

        ArrayList<String> notifications = new ArrayList<>();
        for (Group group : userGroups) {
            notifications.addAll(generateGroupNotifications(group, user, groupManagement));
        }

        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();

        for (String notification : notifications) {
            String[] notificationData = notification.split(",", 2);
            JPanel notificationPanel = newsFeedWindow.createfriendPanel(notificationData[0], notificationData[1], "");
            panelModel.addElement(notificationPanel);
        }

        notificationsList = new JList<>(panelModel);
        notificationsList.setCellRenderer(new CustomRender());
        notificationsList.setBackground(new Color(34, 34, 34));
        notificationsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = notificationsList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    String notification = notifications.get(index);
                    handleNotificationClick(notification, user, groupManagement);
                }
            }
        });

        NotificationsScroll.setViewportView(notificationsList);
        NotificationsScroll.revalidate();
        NotificationsScroll.repaint();
    }
    private ArrayList<String> generateGroupNotifications(Group group, User user, GroupManagement groupManagement) {
        ArrayList<String> notifications = new ArrayList<>();

        // Notify about new users added to the group
        ArrayList<User> newMembers = groupManagement.getNewMembers(group, user);
        for (User member : newMembers) {
            if (!member.equals(user)) {
                notifications.add(group.getGroupName() + "," + member.getUsername() + " was added to the group.");
            }
        }

        // Notify about status changes (if user is admin or status has changed)
        if (group.isAdmin(user)) {
            notifications.add(group.getGroupName() + ",Your status has been updated to Admin in the group.");
        }

//        // Notify about new posts in the group
//        GroupPostsDatabase groupPostsDatabase = GroupPostsDatabase.getInstance();
//        ArrayList<Post> allPosts = groupPostsDatabase.getPostsForGroup(group.getGroupId());
//        for (Post post : allPosts) {
//            notifications.add(group.getGroupName() + ",A new post was added: " + post.getContentId());
//        }

        return notifications;
    }
    private void handleNotificationClick(String notification, User user, GroupManagement groupManagement) {
        String[] notificationData = notification.split(",", 2);
        String groupName = notificationData[0];
        String notificationMessage = notificationData[1];
        // Find the group using GroupManagement
        Group group = groupManagement.getGroupByName(groupName);
        if (group == null) {
            JOptionPane.showMessageDialog(null, "Group not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, notificationMessage, "Notification Details", JOptionPane.INFORMATION_MESSAGE);
    }
}


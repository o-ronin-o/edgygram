package Frontend;

import Backend.Groups.GroupData;
import Backend.Groups.GroupsDatabase;
import Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GroupNotifications extends JFrame {

    private JPanel Container;
    private JScrollPane GroupNotificationsScroll;
    private JScrollPane NotificationsScroll;
    private JList<JPanel> notificationsList;

    public GroupNotifications(User user, NewsFeedWindow newsFeedWindow) {
        setContentPane(Container);
        setTitle("Group Notifications");
        setVisible(true);
        setLocationRelativeTo(newsFeedWindow);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        GroupsDatabase groupsDatabase = new GroupsDatabase();
        ArrayList<GroupData> userGroups = getUserGroups(user, groupsDatabase);

        ArrayList<String> notifications = new ArrayList<>();
        for (GroupData group : userGroups) {
            notifications.addAll(generateGroupNotifications(group, user));
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
                    handleNotificationClick(notification, user, groupsDatabase);
                }
            }
        });
        NotificationsScroll.setViewportView(notificationsList);
        NotificationsScroll.revalidate();
        NotificationsScroll.repaint();
    }
    private ArrayList<GroupData> getUserGroups(User user, GroupsDatabase groupsDatabase) {
        ArrayList<GroupData> allGroups = groupsDatabase.getAll();
        ArrayList<GroupData> userGroups = new ArrayList<>();

        for (GroupData group : allGroups) {
            if (group.getGroupMembers().contains(user)) {
                userGroups.add(group);
            }
        }
        return userGroups;
    }
    private ArrayList<String> generateGroupNotifications(GroupData group, User user) {
        ArrayList<String> notifications = new ArrayList<>();
        // Notify about new users added to the group
        for (User member : group.getGroupMembers()) {
            if (!member.equals(user)) {
                notifications.add(group.getGroupName() + "," + member.getUsername() + " was added to the group.");
            }
        }
        // Notify about status changes
        if (group.isAdmin(user)) {
            notifications.add(group.getGroupName() + ",Your status has been updated to Admin in the group.");
        }
        // Notify about new posts in the group
        group.getAllPosts().forEach(post ->
                notifications.add(group.getGroupName() + ",A new post was added: " + post.getContentId())
        );
        return notifications;
    }
    private void handleNotificationClick(String notification, User user, GroupsDatabase groupsDatabase) {
        String[] notificationData = notification.split(",", 2);
        String groupName = notificationData[0];
        String notificationMessage = notificationData[1];

        GroupData group = groupsDatabase.getAll().stream()
                .filter(g -> g.getGroupName().equals(groupName))
                .findFirst()
                .orElse(null);

        if (group == null) {
            JOptionPane.showMessageDialog(null, "Group not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, notificationMessage, "Notification Details", JOptionPane.INFORMATION_MESSAGE);
    }
}


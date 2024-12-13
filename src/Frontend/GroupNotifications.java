package Frontend;

import Backend.Groups.Group;
import Backend.Groups.GroupManagement;
import Backend.User;

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

        // هنا بنجيب كل الجروبات اللي المستخدم مشترك فيها باستخدام GroupManagement
        GroupManagement groupManagement = GroupManagement.getInstance();
        ArrayList<Group> userGroups = groupManagement.getUserGroups(user);

        ArrayList<String> notifications = new ArrayList<>();
        for (Group group : userGroups) {
            notifications.addAll(generateGroupNotifications(group, user, groupManagement));
        }

        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();

        // هنا بنضيف كل الإشعارات اللي اتجمعت للقائمة عشان تظهر للمستخدم
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

        // هنا بنعمل إشعار لو في أعضاء جداد انضموا للجروب
        ArrayList<User> newMembers = groupManagement.getNewMembers(group, user);
        for (User member : newMembers) {
            if (!member.equals(user)) {
                notifications.add(group.getGroupName() + "," + member.getUsername() + " تم إضافته للجروب.");
            }
        }

        // لو المستخدم ده أدمن في الجروب، بنعمل إشعار بتحديث حالته
        if (group.isAdmin(user)) {
            notifications.add(group.getGroupName() + ",تم تحديث حالتك إلى أدمن في الجروب.");
        }

        // ممكن تضيف إشعارات تانية هنا زي مثلاً لو في بوست جديد

        return notifications;
    }

    private void handleNotificationClick(String notification, User user, GroupManagement groupManagement) {
        String[] notificationData = notification.split(",", 2);
        String groupName = notificationData[0];
        String notificationMessage = notificationData[1];

        // هنا بندور على الجروب اللي جت منه الإشعار
        Group group = groupManagement.getGroupByName(groupName);
        if (group == null) {
            JOptionPane.showMessageDialog(null, "الجروب مش موجود!", "خطأ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // بنعرض رسالة فيها تفاصيل الإشعار للمستخدم
        JOptionPane.showMessageDialog(null, notificationMessage, "تفاصيل الإشعار", JOptionPane.INFORMATION_MESSAGE);
    }
}

package Frontend;

import Backend.User;

import javax.swing.*;

public class NotificationsWindow extends JFrame{


    private JPanel NotificationPanel;
    private JScrollPane NotificationsScroll;

    public NotificationsWindow(User user) {
        setContentPane(NotificationPanel);
        setTitle("Notifications");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        pack();

    }
}

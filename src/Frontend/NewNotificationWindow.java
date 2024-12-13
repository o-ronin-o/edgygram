package Frontend;

import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.util.ArrayList;

public class NewNotificationWindow extends JFrame {
    private JPanel container;
    private JScrollPane scrollPanel;
    private JList<String> list;

    public NewNotificationWindow(User user) {
        // Set up the frame's properties
        setTitle("Notifications");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 400);
        setResizable(false);

        // Create a DefaultListModel to hold notifications
        ArrayList<String> notifications = user.getNotfications();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String notification : notifications) {
            listModel.addElement(notification);
        }

        // Create the JList and the JScrollPane
        list = new JList<>(listModel);
        list.setBackground(new java.awt.Color(34, 34, 34)); // Dark background for the list
        list.setForeground(java.awt.Color.WHITE); // White text for notifications
        scrollPanel = new JScrollPane(list);

        list.addListSelectionListener(e -> {

                int index = list.getSelectedIndex();
                if (index >=0) {
                    // Ask the user if they want to delete the notification
                    int choice = JOptionPane.showConfirmDialog(
                            this,
                            "Do you want to delete this notification?",
                            "Delete Notification",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        // Delete the selected notification
                        System.out.println("notfication is "+notifications.get(index));
                        String needed = notifications.get(index);
                        UserDatabase userDatabase= UserDatabase.getInstance();
                        ArrayList<User> allusers = userDatabase.getAll();
                        for(User userr: allusers){
                            System.out.println("comparing "+userr.getId()+" with"+user.getId());
                            if(userr.getId().equals(user.getId())){
                                userr.removeNotfications(needed);
                                userDatabase.save(allusers);
                                break;
                            }
                        }
                    }
                }
        });
        // Set the scroll panel as the content pane
        setContentPane(scrollPanel);

        // Display the window
        setVisible(true);
    }

    public static void main(String[] args) {

    }
}

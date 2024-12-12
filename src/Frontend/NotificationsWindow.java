package Frontend;

import Backend.Database;
import Backend.Friends.FriendRequest;
import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.FriendsManagement;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NotificationsWindow extends JFrame{


    private JPanel NotificationPanel;
    private JScrollPane NotificationsScroll;
    private JList<JPanel> requestsList;

    public NotificationsWindow(User user,NewsFeedWindow newsFeedWindow) {
        setContentPane(NotificationPanel);
        setTitle("Notifications");
        setVisible(true);
        setLocationRelativeTo(newsFeedWindow);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,600);
        Database<User> userDatabase=new UserDatabase();
        FriendRequestManagement friendRequestManagement=new FriendRequestManagement(userDatabase,new FriendsDatabase());
        ArrayList<FriendRequest> requests= friendRequestManagement.getFriendRequests(user);
        ArrayList<User> requestSendersUsers= new ArrayList<>();
        ArrayList<User> allUsers=userDatabase.getAll();
        for(FriendRequest friendRequest: requests){
            String senderId = friendRequest.getSenderId();
            // Find the user with senderId
            for (User sender : allUsers) {
                if (sender.getId().equals(senderId)) {
                    //when found at the sender to the arraylist of senders and the id in the array list of sender ids
                    requestSendersUsers.add(sender);
                    break;
                }
            }
        }
        FriendsManagement friendsManagement=new FriendsManagement(userDatabase,new FriendsDatabase());
        ArrayList<String> sendersData= friendsManagement.displayList(requestSendersUsers);
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        //layout to appear in notification
        for (String data : sendersData) {
            String[] friendDataArray = data.split(",");
            JPanel friendPanel = newsFeedWindow.createfriendPanel(friendDataArray[0], friendDataArray[1]+" sent you a friend request.","");
            panelModel.addElement(friendPanel);
        }

        requestsList=new JList<>(panelModel);
        requestsList.setCellRenderer(new CustomRender());
        requestsList.setBackground(new Color(34, 34, 34));
        requestsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = requestsList.locationToIndex(e.getPoint()); // Get the clicked item's index
                if (index >= 0) {
                    String friendData = sendersData.get(index);
                    String[] friendDataArray = friendData.split(",");
                    String friendUsername = friendDataArray[1]; // Extract friend's username
                    User sender = null ;
                    for (User senderr : requestSendersUsers) {
                        if (senderr.getUsername().equals(friendUsername)) {
                            sender = senderr;
                            break;
                        }
                    }
                    if(sender ==null){
                        JOptionPane.showMessageDialog(null, "Error","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    // ask user if he wants to accept the friend request or not
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to accept the friend request from " + sender.getUsername() + "?", "Friend Request", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        // Accept the friend request
                        friendRequestManagement.acceptFriendRequest(sender, user);
                    } else if (choice == JOptionPane.NO_OPTION) {
                        // Decline the friend request
                        friendRequestManagement.declineFriendRequest(sender, user);
                    }
                }   
            }
        });

        NotificationsScroll.setViewportView(requestsList);
        NotificationsScroll.revalidate();
        NotificationsScroll.repaint();

    }
}

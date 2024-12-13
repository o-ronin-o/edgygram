package Frontend;

import Backend.Friends.*;
import Backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ViewRequestWindow extends JFrame {

    private JPanel container;
    private JScrollPane requestsScrollPanel;
    private JList<JPanel> requestsList;
    public ViewRequestWindow(User user,NewsFeedWindow newsFeedWindow) {
        setTitle("View Friend Requests");
        setContentPane(container);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(newsFeedWindow);
        setSize(400,400);
        setResizable(false);
        //initialize main backend components
        Database<User> userDatabase= UserDatabase.getInstance();
        FriendRequestManagement requestManagement= new FriendRequestManagement((UserDatabase) userDatabase,new FriendsDatabase());
        ArrayList<FriendRequest> requests= requestManagement.getFriendRequests(user);//get all friend requests
        ArrayList<User> requestSenders=new ArrayList<>();
        ArrayList<User> allUsers=userDatabase.getAll();//get all users in user database
        for(FriendRequest friendRequest: requests){
            String senderId = friendRequest.getSenderId();
            // Find the user with senderId
            for (User sender : allUsers) {
                if (sender.getId().equals(senderId)) {
                    //when found at the sender to the arraylist of senders and the id in the array list of sender ids
                    requestSenders.add(sender);
                    break;
                }
            }
        }
        FriendsManagement friendsManagement=new FriendsManagement((UserDatabase) userDatabase,new FriendsDatabase());
        ArrayList<String> sendersData= friendsManagement.displayList(requestSenders);
        // Create the list of panels
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        for (String data : sendersData) {
            String[] friendDataArray = data.split(",");
            JPanel friendPanel = newsFeedWindow.createfriendPanel(friendDataArray[0], friendDataArray[1], friendDataArray[2]);
            panelModel.addElement(friendPanel);
        }
        // Create the JList and set custom render to handle displaying JPanel
        requestsList=new JList<>(panelModel);
        requestsList.setCellRenderer(new CustomRender());
        requestsList.setBackground(new Color(34, 34, 34));
        // Add MouseListener to handle item clicks
        requestsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = requestsList.locationToIndex(e.getPoint()); // Get the clicked item's index
                if (index >= 0) {
                    String friendData = sendersData.get(index);
                    String[] friendDataArray = friendData.split(",");
                    String friendUsername = friendDataArray[1]; // Extract friend's username
                    User sender = null ;
                    for (User senderr : requestSenders) {
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
                        requestManagement.acceptFriendRequest(sender, user);
                    } else if (choice == JOptionPane.NO_OPTION) {
                        // Decline the friend request
                        requestManagement.declineFriendRequest(sender, user);
                    }
                    new ViewRequestWindow(user,newsFeedWindow);
                    dispose();
                }
            }
        });
        // Set the JList inside the JScrollPane
        requestsScrollPanel.setViewportView(requestsList);
        requestsScrollPanel.revalidate();
        requestsScrollPanel.repaint();
    }

    public static void main(String[] args) {
        UserDatabase userDatabase=  UserDatabase.getInstance();
        ArrayList<User> users=userDatabase.getAll();
        new ViewRequestWindow(users.get(5),new NewsFeedWindow(users.get(5)));
    }
}

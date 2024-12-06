package Frontend;

import Backend.*;
import Backend.Friends.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SuggestedFriendsWindow extends JFrame{
    private JPanel container;
    private JScrollPane friendsScrollPane;
    private JList suggestedFriendsList;
    public SuggestedFriendsWindow(User user, NewsFeedWindow newsFeedWindow) {
        setTitle("Suggested Friends");
        setContentPane(container);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(newsFeedWindow);
        setSize(400,400);
        setResizable(false);

        //initialize main backend components
        Database<User> userDatabase= new UserDatabase();
        FriendsManagement friendsManagement= new FriendsManagement(new UserDatabase(),new FriendsDatabase());
        System.out.println("getting suggested ");
        ArrayList<User> suggestedFriends= friendsManagement.suggestFriends(user);
        ArrayList<String> suggestedFriendsData= friendsManagement.displayList(suggestedFriends);
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        for (String data : suggestedFriendsData) {
            String[] friendDataArray = data.split(",");
            JPanel friendPanel = newsFeedWindow.createfriendPanel(friendDataArray[0], friendDataArray[1], friendDataArray[2]);
            panelModel.addElement(friendPanel);
        }
        suggestedFriendsList = new JList<>(panelModel);
        suggestedFriendsList.setCellRenderer(new CustomRender());
        suggestedFriendsList.setBackground(new Color(34, 34, 34));
        //Add mouse listner to handle mouse click
        suggestedFriendsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = suggestedFriendsList.locationToIndex(e.getPoint());
                if(index>=0){
                    String suggestionData=suggestedFriendsData.get(index);
                    String[] suggestionDataArray = suggestionData.split(",");
                    String suggestionUsername=suggestionDataArray[1]; //get suggestion username
                    //show confirmation dialog
                    int choice=JOptionPane.showConfirmDialog(SuggestedFriendsWindow.this,"Do you want to send a friend request to "+suggestionUsername+"?","Send a friend request",JOptionPane.YES_NO_OPTION);
                    if(choice==JOptionPane.YES_OPTION){
                        //get suggestion user object
                        Database<User> userDatabase= new UserDatabase();
                        ArrayList<User> allUsers=userDatabase.getAll();
                        User suggestion=null;
                        for(User user : allUsers){
                            if(user.getUsername().equals(suggestionUsername)){
                                suggestion=user;
                                break;
                            }
                        }
                        FriendRequestManagement friendRequestManagement= new FriendRequestManagement(new UserDatabase(),new FriendsDatabase());
                        friendRequestManagement.sendFriendRequest(user,suggestion);
                    }
                    new SuggestedFriendsWindow(user,newsFeedWindow);
                    dispose();
                }
            }
        });
        // Set the JList inside the JScrollPane
        friendsScrollPane.setViewportView(suggestedFriendsList);
        friendsScrollPane.revalidate();
        friendsScrollPane.repaint();

    }
}

package Frontend;

import Backend.Database;
import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.FriendsManagement;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManageFriendsWindow extends JFrame {
    private JPanel container;
    private JButton viewFriendRequestButton;
    private JButton suggestedFriendsButton;
    private JButton blockAFriendButton;
    private JButton removeAFriendButton;
    private JButton addFriendButton;

    public ManageFriendsWindow(NewsFeedWindow newsFeedWindow , User user) {
        setTitle("Manage Friends");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350,300);
        setLocationRelativeTo(newsFeedWindow);
        setVisible(true);
        setResizable(false);
        setContentPane(container);
        viewFriendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewRequestWindow(user,newsFeedWindow);
            }
        });
        removeAFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveFriendWindow(user,newsFeedWindow);
            }
        });
        blockAFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BlockFriendWindow(user,newsFeedWindow);
            }
        });
        suggestedFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SuggestedFriendsWindow(user,newsFeedWindow);
            }
        });
        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=JOptionPane.showInputDialog(newsFeedWindow, "Enter Username", "Add friend", JOptionPane.PLAIN_MESSAGE);
                if(username.equals("null"))
                    return;
                Database<User> userDatabase= new UserDatabase();
                ArrayList<User> allUsers= userDatabase.getAll();
                for(User userr:allUsers){
                    if(userr.getUsername().equals(username)){
                        FriendRequestManagement friendRequestManagement= new FriendRequestManagement((UserDatabase)userDatabase,new FriendsDatabase());
                        friendRequestManagement.sendFriendRequest(user,userr);
                        FriendsManagement friendsManagement= new FriendsManagement((UserDatabase)userDatabase,new FriendsDatabase());
                        if(friendsManagement.isFriend(user,userr)){
                            return;
                        }
                        JOptionPane.showMessageDialog(newsFeedWindow,"Friend request sent","Success",JOptionPane.INFORMATION_MESSAGE );
                        return;
                    }
                }
                JOptionPane.showMessageDialog(newsFeedWindow,username+" doesn't exist","Error",JOptionPane.ERROR_MESSAGE );
            }
        });
    }
}

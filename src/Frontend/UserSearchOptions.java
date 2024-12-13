package Frontend;

import javax.swing.*;
import Backend.*;
import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.FriendsManagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserSearchOptions extends JFrame {
    private JPanel container;
    private JButton sendFriendRequestButton;
    private JButton removeFriendButton;
    private JButton blockUserButton;
    private JButton viewProfieButton;

    public UserSearchOptions(User user,User suggestion) {

        setTitle("User Search");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(container);
        setVisible(true);
        setSize(400,400);
        Database<User> userDatabase= UserDatabase.getInstance();
        FriendsDatabase friendsDatabase = new FriendsDatabase();
        FriendsManagement friendsManagement=new FriendsManagement(userDatabase,friendsDatabase);
        sendFriendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendRequestManagement friendRequestManagement=new FriendRequestManagement(userDatabase,friendsDatabase);
                friendRequestManagement.sendFriendRequest(user,suggestion);
                ArrayList<User> allUsers= userDatabase.getAll();
                String notfication= user.getUsername()+" sent you a friend request";
                for(User userr: allUsers){
                    if(userr.getUsername().equals(suggestion.getUsername())){
                        userr.addtNotfications(notfication);
                        userDatabase.save(allUsers);
                        break;
                    }
                }
                dispose();
            }
        });
        removeFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friendsManagement.removeFriend(user,suggestion);
                dispose();
            }
        });
        blockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friendsManagement.block(user,suggestion);
                dispose();
            }

        });
        viewProfieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileWindow(suggestion,false);
                dispose();
            }
        });
    }
    public static void main(String[] args) {
        UserDatabase userDatabase=  UserDatabase.getInstance();
        ArrayList<User> users=userDatabase.getAll();
        System.out.println(users.get(0).getId()+",,," + users.get(1).getId());
        new UserSearchOptions(users.get(0),users.get(1));
    }
}

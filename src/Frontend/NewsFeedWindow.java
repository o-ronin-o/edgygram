package Frontend;

import Backend.*;
import Backend.Friends.FriendData;
import Backend.Friends.FriendsDatabase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsFeedWindow extends JFrame {

    private JPanel Container;
    private JButton refreshButton;
    private JTextField WriteAPost;
    private JLabel Posts;
    private JLabel Stories;
    private JScrollPane Friends;
    private JScrollPane postScrollPane;
    private JScrollPane FriendsSuggestions;
    private JButton LogOut;
    private JButton Profile;
    private JTextField SearchTextField;
    private JList<String> FriendsList;
    private JList<String> postList;
    private JList<String>  FriendsSuggestionsList;

    public NewsFeedWindow(){

//          ImageIcon img2=new ImageIcon("C:\\Users\\Hazem\\Desktop\\Edgygram\\Zamalek_SC_logo.svg.png");
//          Image ScaledImage2 =img2.getImage().getScaledInstance(100,200,Image.SCALE_SMOOTH);
//          Stories.setIcon(new ImageIcon(ScaledImage2));
//          Stories.setText("Zamalek");

          // Implementation of friends
        String[] friendsData = {"Friend 1", "Friend 2", "Friend 3", "Friend 4", "Friend 5"};
        FriendsList = new JList<>(friendsData);
        Friends.setViewportView(FriendsList);
        Friends.revalidate();
        Friends.repaint();

        // Implementation of friends Sugguestions
        String[] FriendsSuggestionsData = {"friend 1","friend 2","friend 3"};
        FriendsSuggestionsList = new JList<>(FriendsSuggestionsData);
        FriendsSuggestions.setViewportView(FriendsSuggestionsList);
        FriendsSuggestions.revalidate();
        FriendsSuggestions.repaint();

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFriendsList();
            }
        });

        // profile button action
        Profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // logout button action
        User u= new User();
        LogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 u.setStatus("Offline");
                JOptionPane.showMessageDialog(null, "You have been logged out!");
                System.exit(0);
            }
        });
    }
    public void display()
    {
        setTitle("News Feed");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setContentPane(Container);
        setVisible(true);
    }
private void refreshFriendsList() {
    try {
        // Fetch the latest list of friends from the backend
        FriendsDatabase friendsdatabase = new FriendsDatabase();
        ArrayList<HashMap<String, FriendData>> friendsDataList = friendsdatabase.getAll();
        // Prepare the data for the UI
        DefaultListModel<String> friendsModel = new DefaultListModel<>();
        // Iterate over the ArrayList of HashMaps
        for (HashMap<String, FriendData> friendMap : friendsDataList) {
            // For each HashMap, iterate over its entries
            for (Map.Entry<String, FriendData> entry : friendMap.entrySet()) {
                // Access the key and the FriendData object
                FriendData friend = entry.getValue(); // The FriendData object
                // Assuming FriendData has a getName() method to fetch friend's name
                String friendName = friend.getName();
                friendsModel.addElement(friendName); // Add friend's name to the list model
            }
        }
        // Update the JList for friends
        FriendsList.setModel(friendsModel);
        Friends.revalidate();
        Friends.repaint();
        JOptionPane.showMessageDialog(null, "Friends list refreshed!");
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to refresh friends list!");
    }
}
    public static void main(String[] args) {
      NewsFeedWindow n=new NewsFeedWindow();
      n.display();
    }
}

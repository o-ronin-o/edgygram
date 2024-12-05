package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JList<String> FriendsList; // Declare JList for friends
    private JList<String> postList;
    private JList<String>  FriendsSuggestionsList;

    public NewsFeedWindow(){

        //  Content c = new Post("meme","momo"," mama mama mamamamamamamamamamamama",LocalDateTime.now(),"baba","post");

          ImageIcon img2=new ImageIcon("C:\\Users\\Hazem\\Desktop\\Edgygram\\Zamalek_SC_logo.svg.png");
          Image ScaledImage2 =img2.getImage().getScaledInstance(100,200,Image.SCALE_SMOOTH);
          Stories.setIcon(new ImageIcon(ScaledImage2));
          Stories.setText("Zamalek");

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

        //   String[] content = {c.getPostString(c.getTimeStamp(),c.getContent())};
        //   postList = new JList<>(content);
        //   Wrap JList in JScrollPane
        //   postScrollPane.setViewportView(postList);
        //   System.out.println(c.getPostString(c.getTimeStamp(),c.getContent()));
        //   Set the JList as the viewport view of the JScrollPane (FriendsPane)

        // refresh button action
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // profile button action
        Profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // logout button action
        LogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

    public static void main(String[] args) {
      NewsFeedWindow n=new NewsFeedWindow();
      n.display();
    }
}

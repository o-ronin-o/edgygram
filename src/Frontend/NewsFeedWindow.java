package Frontend;
import Backend.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class NewsFeedWindow extends JFrame {

    private JPanel Container;
    private JButton Search;
    private JButton refreshButton;
    private JTextField WriteAPost;
    private JLabel Posts;
    private JLabel Stories;
    private JLabel Friends_Suggestions;
    private JScrollPane lolPane;
    private JScrollPane postScrollPane;
    private JList<String> FriendsList; // Declare JList for friends
    private JScrollPane friendsScrollPane;
    private JPanel friendsPanel;
    private JList<String> postList;

    public NewsFeedWindow(){


          Content c = new Post("meme","momo"," mama mama mamamamamamamamamamamama",LocalDateTime.now(),"baba","post");
          setTitle("News Feed");
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(700,700);
          setVisible(true);
          setContentPane(Container);
          ImageIcon img1=new ImageIcon("C:\\Users\\Hazem\\Desktop\\Edgygram\\Zamalek_SC_logo.svg.png");
          Image ScaledImage1 =img1.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH);
          //Posts.setIcon(new ImageIcon(ScaledImage1));
          //Posts.setText("ay haga");

          ImageIcon img2=new ImageIcon("C:\\Users\\Hazem\\Desktop\\Edgygram\\Zamalek_SC_logo.svg.png");
          Image ScaledImage2 =img2.getImage().getScaledInstance(100,200,Image.SCALE_SMOOTH);
          Stories.setIcon(new ImageIcon(ScaledImage2));
          Stories.setText("Zamalek");

        String[] friendsData = {"Friend 1", "Friend 2", "Friend 3", "Friend 4", "Friend 5"};
        FriendsList = new JList<>(friendsData);
        String[] content = {c.getPostString(c.getTimeStamp(),c.getContent())};
        postList = new JList<>(content);
        // Wrap JList in JScrollPane
        postScrollPane.setViewportView(postList);
        System.out.println(c.getPostString(c.getTimeStamp(),c.getContent()));
        // Set the JList as the viewport view of the JScrollPane (lolPane)
        lolPane.setViewportView(FriendsList);

        // Revalidate and repaint the container to ensure changes are visible
        lolPane.revalidate();
        lolPane.repaint();
//        friendsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        friendsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        friendsScrollPane.setPreferredSize(new Dimension(200, 300));

        // Add the JScrollPane containing JList to the Container panel

        setVisible(true);
    }

    public static void main(String[] args) {
        new NewsFeedWindow();
    }
}

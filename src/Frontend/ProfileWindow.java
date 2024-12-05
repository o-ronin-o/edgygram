package Frontend;

import Backend.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;

public class ProfileWindow extends JFrame {
    private JPanel panel1;
    private JLabel coverPhoto;
    private JLabel profilePhoto;
    private JButton editProfile;
    private JTextArea bio;
    private JTextArea posts;
    private JScrollPane postScrollPane;
    private JList postList;
    ProfileWindow() {

        //setting up the databases
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        Database<Content> content = new ContentDatabase();
       // JOptionPane.showConfirmDialog(null, panel1, "Date of Birth", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //User omar = new User("4343","Ronin","yomama","omar@gmail.com",dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),"online","\\Downloads\\download.jpg","\\Downloads\\cat.jpg");
        //setting up the main frame
        setTitle("Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        panel1.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
        panel1.setBackground(Color.decode("#24292e"));
        panel1.setForeground(Color.white);
        setVisible(true);
        setContentPane(panel1);
        ImageIcon img = new ImageIcon("\\Downloads\\download.jpg");
        Image scaledImage = img.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);


        //setting up the profile photo
        profilePhoto.setIcon(new ImageIcon(scaledImage));
        profilePhoto.setText("wawa");
        setForeground(Color.WHITE);


        profilePhoto.setHorizontalAlignment(SwingConstants.LEFT);
        profilePhoto.setVerticalAlignment(SwingConstants.TOP);

        //setting up cover photo
        ImageIcon ProfileImg = new ImageIcon("\\Downloads\\cat.jpg");
        Image scaledImage2 = ProfileImg.getImage().getScaledInstance(1000, 200, Image.SCALE_SMOOTH);
        coverPhoto.setIcon(new ImageIcon(scaledImage2));

        //setting up the Bio
        bio.setText("Threesome? No thanks… If I wanted to disappoint two people in the same room, I’d have dinner with my parents.");
        bio.setEditable(false);
        bio.setLineWrap(true);
        bio.setWrapStyleWord(true);
        bio.setBackground(Color.decode("#24292e"));
        bio.setForeground(Color.white);

        //setting up the button
        editProfile.setBackground(Color.decode("#2b3137"));
        editProfile.setForeground(Color.white);
        editProfile.setText("Edit Profile");
        editProfile.setBorder(new RoundedBorder(20));
        //setting up the Posts thread
        Content c = new Post("meme","momo"," mama mama mamamamamamamamamamamama", LocalDateTime.now(),"baba","post");

        // we create a panel for posts then add it to the scroll panel
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.decode("#24292e"));
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        addPost(postPanel,c.getPostString(c.getTimeStamp(),c.getContent()),"\\Downloads\\cat.jpg");
        addPost(postPanel,c.getPostString(c.getTimeStamp(),c.getContent()),"\\Downloads\\cat.jpg");
        addPost(postPanel,c.getPostString(c.getTimeStamp(),c.getContent()),"\\Downloads\\cat.jpg");

        postScrollPane.setViewportView(postPanel);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        postScrollPane.setBackground(Color.decode("#24292e"));


        // {c.getPostString(c.getTimeStamp(),c.getContent())};
//        ArrayList<String> content = new ArrayList<>();
//        content.add(c.getPostString(c.getTimeStamp(),c.getContent()));
//        posts = new JTextArea();
//        for(String line : content){
//            posts.append(line + "\n");
//        }
//        //postList = new JList<>(content);
//        postScrollPane.setViewportView(posts);
//        postScrollPane.revalidate();
//        postScrollPane.repaint();


         }
         public void addPost(JPanel postPanel, String text , String imagePath){
                // a jpanel for each post
                JPanel singlePostPanel = new JPanel();
                singlePostPanel.setLayout(new BorderLayout());
                singlePostPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                // a jtextarea for text
                JTextArea textArea = new JTextArea(text);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFont(new Font("Arial", Font.PLAIN, 14));
                textArea.setBackground(Color.decode("#24292e"));
                textArea.setForeground(Color.white);

                // creating a jlabel for images
                ImageIcon Img = new ImageIcon(imagePath);
                Image scaledImage = Img.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaledImage));

                // now we add the component for the main singlePostPanel
             singlePostPanel.add(textArea, BorderLayout.NORTH);
             singlePostPanel.add(imgLabel, BorderLayout.CENTER);
             singlePostPanel.setBackground(Color.decode("#24292e"));

             //now we add it to the post panel

             postPanel.add(singlePostPanel);
    }
    public static void main(String[] args) {
        new ProfileWindow();
    }
}



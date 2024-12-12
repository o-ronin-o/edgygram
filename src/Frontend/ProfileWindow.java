package Frontend;

import Backend.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;

public class ProfileWindow extends JFrame {
    private JPanel panel1;
    private JLabel coverPhoto;
    private JLabel profilePhoto;
    private JButton editProfile;
    private JTextArea bio;
    private JTextArea posts;
    private JScrollPane postScrollPane;
    private JList postList;
    ProfileWindow(User user,boolean displayEditButton) {
        //setting up the databases
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        Database<Content> content = new ContentDatabase();
       // JOptionPane.showConfirmDialog(null, panel1, "Date of Birth", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //User omar = new User("4343","Ronin","yomama","omar@gmail.com",dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),"online","\\Downloads\\download.jpg","\\Downloads\\cat.jpg");
        //setting up the main frame
        setTitle("Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        panel1.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
        panel1.setBackground(Color.decode("#24292e"));
        panel1.setForeground(Color.white);
        setVisible(true);
        setContentPane(panel1);
        revalidate();
        repaint();

        //setting up the profile photo
        if (user.getprofilePicture() != null && !user.getprofilePicture().isEmpty()) {
            ImageIcon img = new ImageIcon(user.getprofilePicture());
            System.out.println(user.getprofilePicture());
            System.out.println(user.getBio());

            Image scaledImage = img.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
            profilePhoto.setIcon(new ImageIcon(scaledImage));
        }
        else{
            System.out.println("not found");
        }
        profilePhoto.setText(user.getUsername());
        setForeground(Color.WHITE);
        profilePhoto.setHorizontalAlignment(SwingConstants.LEFT);
        profilePhoto.setVerticalAlignment(SwingConstants.TOP);
        profilePhoto.setBorder(new RoundedBorder(20));
        revalidate();
        repaint();
        //setting up cover photo
        ImageIcon ProfileImg = new ImageIcon(user.getCoverPicture());
        Image scaledImage2 = ProfileImg.getImage().getScaledInstance(1000, 200, Image.SCALE_SMOOTH);
        coverPhoto.setIcon(new ImageIcon(scaledImage2));
        revalidate();
        repaint();
        //setting up the Bio
        System.out.println(user.getBio());
        System.out.println("User bio: " + user.getBio());
        if (user.getBio() == null || user.getBio().isEmpty()) {
            System.out.println("Bio is empty or null!");
        } else {
            bio.setText(user.getBio());
        }

        bio.setText(user.getBio());
        bio.setEditable(false);
        bio.setLineWrap(true);
        bio.setWrapStyleWord(true);
        bio.setBackground(Color.decode("#24292e"));
        bio.setForeground(Color.white);
        revalidate();
        repaint();
        //setting up the button
        editProfile.setBackground(Color.decode("#2b3137"));
        editProfile.setForeground(Color.white);
        editProfile.setText("Edit Profile");
        editProfile.setBorder(new RoundedBorder(20));
        revalidate();
        repaint();
        //setting up the Posts thread
        Content c = new Post("meme","momo"," mama mama mamamamamamamamamamamama", LocalDateTime.now(),"baba","post");

        // we create a panel for posts then add it to the scroll panel
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.decode("#24292e"));
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        ContentDatabase newContentDatabase= (ContentDatabase) content;
        ArrayList<Content> allcontent=newContentDatabase.getAllPosts();
        for(Content post : allcontent){
            if(post.getAuthorId().equals(user.getId())){
                addPost(postPanel, user.getUsername()+post.getPostString(post.getTimeStamp(),post.getContent()),post.getPicPath());
            }
        }

        postScrollPane.setViewportView(postPanel);
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        postScrollPane.setBackground(Color.decode("#24292e"));
        postPanel.setBorder(new RoundedBorder(20));
        postScrollPane.setBorder(new RoundedBorder(20));
        revalidate();
        repaint();
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
        editProfile.setVisible(false);
        if(displayEditButton) {
            editProfile.setVisible(true);
            editProfile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new EditProfileWindow(user,ProfileWindow.this);
                }
            });
        }
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
//        User u = new User("omk", "omk", "ommmk", "ommmk@gmail.com", "06/10/2024", "offline", "D:\\Downloads\\cat.jpg", "D:\\Downloads\\cat.jpg","debug");
//        new ProfileWindow(u);
    }
}



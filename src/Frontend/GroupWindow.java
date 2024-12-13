package Frontend;

import Backend.*;
import Backend.Groups.GroupData;
import Backend.Groups.GroupsDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupWindow extends JFrame {
    private JPanel panel1;
    private JButton manageAdminsButton;
    private JButton manageRequestsButton;
    private JButton removeMemberButton;
    private JButton deleteGroupButton;
    private JPanel groupCover;
    private JScrollPane postScrollPane;
    private JTextArea postTextArea;
    private String primaryAdminId;
    private static final AtomicInteger counter = new AtomicInteger(0);
// المشكله في حوار البرايفسي ده
    // المشكله ان الاكسبوز هحتاج اعملها بردو لليوزر عشان
    public GroupWindow(GroupData group,User user){


        ContentDatabase db = ContentDatabase.getInstance();
        panel1 = setupMainPanel(panel1);

        postScrollPane = setupScrollPane(postScrollPane, group);

        // setting up the buttons
        manageAdminsButton = setupButtons(manageAdminsButton);
        manageRequestsButton = setupButtons(manageRequestsButton);
        removeMemberButton = setupButtons(removeMemberButton);
        deleteGroupButton = setupButtons(deleteGroupButton);

        System.out.println(user.getId());

        //checking the role of the user

        if(group.isAdmin(user)){
            if(group.isPrimaryAdmin(user)){
                manageAdminsButton.setVisible(true);
                deleteGroupButton.setVisible(true);
            }
            manageRequestsButton.setVisible(true);
            removeMemberButton.setVisible(true);
             }
        else{
            System.out.println("not and admin");
            manageAdminsButton.setVisible(false);
            manageRequestsButton.setVisible(false);
            removeMemberButton.setVisible(false);
            deleteGroupButton.setVisible(false);
        }
        JFileChooser fileChooser = new JFileChooser();
        JPanel postPanel = new JPanel();

        // setting up the add post panel



        postTextArea.setBackground(Color.decode("#24292e"));
        postTextArea.setBorder(new RoundedBorder(20));
        postTextArea.setText("Add a Post...");
        postTextArea.setForeground(Color.decode("#999999"));

        postTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
                    postTextArea.append("\n");
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    e.consume();
                    Content posty = new Post("CID-" + counter.incrementAndGet(),user.getId(),postTextArea.getText(), LocalDateTime.now(),null,group.getGroupId(), user.getUsername(),"Post" );
                    int i =JOptionPane.showConfirmDialog(panel1,"Sometimes picture spices up the post ya know,\nWanna add a pic?",
                            "adding wa pic",
                            JOptionPane.YES_NO_OPTION ,
                            JOptionPane.QUESTION_MESSAGE);
                    if(i == JOptionPane.YES_OPTION){
                        fileChooser.setDialogTitle("Select File");
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                            public boolean accept(File file) {

                                return file.isDirectory() || file.getName().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$");
                            }

                            @Override
                            public String getDescription() {
                                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
                            }
                        });
                        int result = fileChooser.showOpenDialog(panel1);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            System.out.println("file selected");
                            addPost(postPanel,
                                    user.getUsername()+" Posted At: "+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) +"\n"+postTextArea.getText(),
                                    fileChooser.getSelectedFile().getAbsolutePath());
                            posty.setPicPath(fileChooser.getSelectedFile().getAbsolutePath());
                        }
                        else{
                            System.out.println("no file selected");
                            addPost(postPanel, user.getUsername()+" Posted At: "+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) +"\n"+postTextArea.getText(),null);
                        }
                    }
                    else if (i == JOptionPane.NO_OPTION) {
                        System.out.println("no pic selected");
                        addPost(postPanel, user.getUsername()+" Posted At: "+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) +"\n"+postTextArea.getText(),null);
                    }

                    JOptionPane.showMessageDialog(panel1,"Post added yummy ;-)");
                    ArrayList<Content> posts=db.getAllPosts();
                    posts.add(posty);
                    db.saveAllPosts(posts);

                }
            }
        });
        postTextArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // When the text area gains focus, clear the placeholder text
                if (postTextArea.getText().equals("Add a Post...")) {
                    postTextArea.setText("");
                    postTextArea.setForeground(Color.WHITE); // Set the text color back to black
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // When the text area loses focus, if the text is empty, set the placeholder text again
                if (postTextArea.getText().isEmpty()) {
                    postTextArea.setText("Add a Post...");
                    postTextArea.setForeground(Color.decode("#999999"));
                }
            }
        });



    }
    public JButton setupButtons(JButton myButton) {
        myButton.setBackground(Color.decode("#24292e"));
        myButton.setForeground(Color.decode("#FFFFFF"));
        myButton.setBorder(new RoundedBorder(10));
        return myButton;
    }
    public JPanel setupMainPanel(JPanel myPanel) {
        setContentPane(myPanel);
        setTitle("Group");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        myPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        myPanel.setBorder(new RoundedBorder(20));
        pack();
        myPanel.setBackground(Color.decode("#24292e"));
        return myPanel;
    }
    public JScrollPane setupScrollPane(JScrollPane myScrollPane, GroupData group) {
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.decode("#24292e"));
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        for(Content post : group.getAllPosts()){
                Content c = group.getById(post.getGroupId());
                addPost(postPanel, c.getAuthorName()+" "+c.getPostString(post.getTimeStamp(),c.getContent()),c.getPicPath());
        }

        myScrollPane.setViewportView(postPanel);
        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollPane.setBackground(Color.decode("#24292e"));
        postPanel.setBorder(new RoundedBorder(20));
        myScrollPane.setBorder(new RoundedBorder(5));
        myScrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");
                trackHighlightColor = Color.decode("#24292e");
            }

        });
        myScrollPane.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");

            }
        });
        return myScrollPane;
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
        GroupsDatabase groupsDatabase = new GroupsDatabase();
        ArrayList<GroupData> groups = groupsDatabase.getAll();
        System.out.println(groups.get(0).getGroupName());

        if (groups.isEmpty()) {
            System.out.println("No groups found in the database.");
            return;
        }

//        GroupData group = groupsDatabase.getById("GID-1");
//        if (group == null) {
//            System.out.println("Group with ID 'GID-1' not found.");
//            return;
//        }

        UserDatabase userDatabase =  UserDatabase.getInstance();
        ArrayList<User> users = userDatabase.getAll();

        if (users.size() <= 12) {
            System.out.println("Not enough users in the database.");
            return;
        }

        new GroupWindow(groups.get(0), users.get(12));

    }
}

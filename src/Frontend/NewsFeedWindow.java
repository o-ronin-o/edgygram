package Frontend;

import Backend.*;
import Backend.Friends.FriendData;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class NewsFeedWindow extends JFrame {

    private JPanel Container;
    private JButton refreshButton;
    private JLabel Posts;
    private JScrollPane Friends;
    private JButton LogOut;
    private JButton Profile;
    private JTextField SearchTextField;
    private JButton manageFriendsButton;
    private JTextArea postTextArea;
    private JScrollPane postsScrollPane;
    private JScrollPane storiesScrollPane;
    private JButton addStoryButton;
    private JButton Notificationsbutton;
    private JButton searchUsersButton;
    private JButton searchGroupsButton;
    private JList<JPanel> friendsList;
    private JList<String> postList;
    private JList<String>  FriendsSuggestionsList;

    private static final AtomicInteger counter = new AtomicInteger(0);

    public NewsFeedWindow(User user){


//        UIManager.put("ScrollBar.thumb", new ColorUIResource(Color.decode("#24292e"))); // Scrollbar thumb
//        UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(Color.decode("#24292e"))); // Thumb highlight
//        UIManager.put("ScrollBar.thumbDarkShadow", new ColorUIResource(Color.decode("#5b5b5b"))); // Thumb shadow


        SwingUtilities.updateComponentTreeUI(this);
        //Setting up the databases
        Database<User> userDatabase =  UserDatabase.getInstance();
        FriendsManagement friendsManagement = new FriendsManagement(userDatabase, new FriendsDatabase());
        ContentDatabase contentDatabase = ContentDatabase.getInstance();
        ArrayList<Content> posts = friendsManagement.getFriendsPosts(user);
        ArrayList<Content> stories = friendsManagement.getFriendsStories(user);
        setContentPane(Container);
        setTitle("NewsFeed");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        Container.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        Container.setBorder(new RoundedBorder(20));
        pack();
        Container.setBackground(Color.decode("#24292e"));


        // Implementation of friends

        ArrayList<User> friend = friendsManagement.getFriends(user);
        ArrayList<String> friendsData = friendsManagement.displayList(friend);
        //create list of panels
        DefaultListModel<JPanel> friendsListModel = new DefaultListModel<>();
        for(String data : friendsData){
            String[] friendsArray=data.split(",");
            JPanel friendPanel = createfriendPanel(friendsArray[0],friendsArray[1],friendsArray[2]);
            friendsListModel.addElement(friendPanel);
        }
        // Create the JList and set custom render to handle displaying Jpanel
        friendsList = new JList<>(friendsListModel);
        friendsList.setCellRenderer(new CustomRender());
        friendsList.setBackground(Color.decode("#24292e"));
        Friends.setViewportView(friendsList);
        Friends.setBackground(Color.decode("#24292e"));
        Friends.setBorder(new RoundedBorder(20));
       Friends.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");
                trackHighlightColor = Color.decode("#24292e");
                thumbDarkShadowColor = Color.decode("#5b5b5b");
            }
        });
        Friends.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");
                trackHighlightColor = Color.decode("#24292e");
                thumbDarkShadowColor = Color.decode("#5b5b5b");
            }
        });
        Friends.revalidate();
        Friends.repaint();

        //setting up the manage friends button

        manageFriendsButton.setBackground(Color.decode("#24292e"));
        manageFriendsButton.setForeground(Color.decode("#FFFFFF"));
        manageFriendsButton.setBorder(new RoundedBorder(10));

        //setting up the Add Story button

        addStoryButton.setBackground(Color.decode("#24292e"));
        addStoryButton.setForeground(Color.decode("#FFFFFF"));
        addStoryButton.setBorder(new RoundedBorder(10));

        //setting up the Logout button

        LogOut.setBackground(Color.decode("#24292e"));
        LogOut.setForeground(Color.decode("#FFFFFF"));
        LogOut.setBorder(new RoundedBorder(10));

        //setting up the Refresh button

        refreshButton.setBackground(Color.decode("#24292e"));
        refreshButton.setForeground(Color.decode("#FFFFFF"));
        refreshButton.setBorder(new RoundedBorder(10));

        //setting up the Profile button

        Profile.setBackground(Color.decode("#24292e"));
        Profile.setForeground(Color.decode("#FFFFFF"));
        Profile.setBorder(new RoundedBorder(10));

        //setting up the Search bar button

        addStoryButton.setBackground(Color.decode("#24292e"));
        addStoryButton.setForeground(Color.decode("#FFFFFF"));
        addStoryButton.setBorder(new RoundedBorder(10));

        //setting up notfication button
        Notificationsbutton.setBackground(Color.decode("#24292e"));
        Notificationsbutton.setForeground(Color.decode("#FFFFFF"));
        Notificationsbutton.setBorder(new RoundedBorder(10));

        //setting up search buttons
        searchUsersButton.setBackground(Color.decode("#24292e"));
        searchUsersButton.setForeground(Color.decode("#FFFFFF"));
        searchUsersButton.setBorder(new RoundedBorder(10));
        searchGroupsButton.setBackground(Color.decode("#24292e"));
        searchGroupsButton.setForeground(Color.decode("#FFFFFF"));
        searchGroupsButton.setBorder(new RoundedBorder(10));
        //setting up the posts scrollpane
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.decode("#24292e"));
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        for(Content post : posts){
            if(post instanceof Post){
                User author =userDatabase.getById(post.getAuthorId());
                addPost(postPanel, author.getUsername()+" "+post.getPostString(post.getTimeStamp(),post.getContent()),post.getPicPath());
            }
        }

        postsScrollPane.setViewportView(postPanel);
        postsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        postsScrollPane.setBackground(Color.decode("#24292e"));
        postPanel.setBorder(new RoundedBorder(20));
        postsScrollPane.setBorder(new RoundedBorder(5));
        postsScrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");
                trackHighlightColor = Color.decode("#24292e");
            }

        });
        postsScrollPane.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");

            }
        });



        revalidate();
        repaint();

        //setting up the Stories scrollpane
        JPanel StoryPanel = new JPanel();
        StoryPanel.setBackground(Color.decode("#24292e"));
        StoryPanel.setLayout(new BoxLayout(StoryPanel, BoxLayout.X_AXIS));
        for(Content story : stories){
            if(story instanceof Story){
                User author =userDatabase.getById(story.getAuthorId());
                addStory(StoryPanel, author.getUsername()+" "+story.getStoryString(story.getContent()),story.getPicPath());
            }
        }

        storiesScrollPane.setViewportView(StoryPanel);
        storiesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        storiesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        storiesScrollPane.setBackground(Color.decode("#24292e"));
        StoryPanel.setBorder(new RoundedBorder(20));
        storiesScrollPane.setBorder(new RoundedBorder(20));
        storiesScrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");
                trackHighlightColor = Color.decode("#24292e");
                thumbDarkShadowColor = Color.decode("#5b5b5b");
                }
        });
        storiesScrollPane.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#5b5b5b");
                trackColor = Color.decode("#24292e");

            }
        });

        revalidate();
        repaint();


        //setting up post text area
        JFileChooser fileChooser = new JFileChooser();
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
                    Content posty = new Post("CID-" + counter.incrementAndGet(),user.getId(),postTextArea.getText(),LocalDateTime.now(),null,"Post");
                    int i =JOptionPane.showConfirmDialog(Container,"Sometimes picture spices up the post ya know,\nWanna add a pic?",
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
                       int result = fileChooser.showOpenDialog(Container);
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

                    JOptionPane.showMessageDialog(Container,"Post added yummy ;-)");
                    ArrayList<Content> posts=contentDatabase.getAllPosts();
                    posts.add(posty);
                    contentDatabase.saveAllPosts(posts);

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
        // Setting up the search text field
        SearchTextField.setBackground(Color.decode("#24292e"));
        SearchTextField.setForeground(Color.decode("#999999"));
        SearchTextField.setBorder(new RoundedBorder(20));
        SearchTextField.setText("Search...");

        SearchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear placeholder text when the field gains focus
                if (SearchTextField.getText().equals("Search...")) {
                    SearchTextField.setText("");
                    SearchTextField.setForeground(Color.WHITE); // Normal text color
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore placeholder text if the field is empty when losing focus
                if (SearchTextField.getText().isEmpty()) {
                    SearchTextField.setText("Search...");
                    SearchTextField.setForeground(Color.decode("#999999")); // Placeholder text color
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewsFeedWindow(user);
                dispose();
            }
        });

        // profile button action
        Profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileWindow(user,true);
            }
        });

        // logout button action
        LogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(NewsFeedWindow.this,"Logging Out");
                user.setStatus("offline");
            ArrayList<User> users= userDatabase.getAll();
        for (User userr : users) {
                if (userr.getId().equals(user.getId())) {
                    userr.setStatus(user.getStatus());
                    break;
                }
            }
        userDatabase.save(users);
        System.exit(1);
            }
        });
        manageFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageFriendsWindow(NewsFeedWindow.this, user);
            }
        });
        addStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog(Container, "Enter the text of the story:", "Text Input", JOptionPane.PLAIN_MESSAGE);
                Content Storyy = new Story("CID-" + counter.incrementAndGet(),user.getId(),"",LocalDateTime.now(),null,"Story");

                if (userInput != null && !userInput.isEmpty()) {
                    System.out.println("User input: " + userInput);
                    Storyy.setPicPath(userInput);
                } else {
                    System.out.println("User canceled the input or entered nothing.");
                }

                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        // Accept only directories or image files
                        return file.isDirectory() || file.getName().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$");
                    }

                    @Override
                    public String getDescription() {
                        return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
                    }
                });

                int result = fileChooser.showOpenDialog(null); // `null` means no parent frame

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    Storyy.setPicPath(selectedFile.getAbsolutePath());
                  } else {
                    System.out.println("No file selected.");
                }
                addStory(StoryPanel,Storyy.getContent(),Storyy.getPicPath());
                stories.add(Storyy);

                ArrayList<Content> allStories=contentDatabase.getAllStories();
                stories.addAll(allStories);
                contentDatabase.saveAllStories(stories);
            }
        });

        Notificationsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationsWindow notificationsWindow= new NotificationsWindow(user,NewsFeedWindow.this);
            }
        });
        searchUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchWindow(user,SearchTextField.getText(),NewsFeedWindow.this);
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
            //  friendsList.setModel(friendsModel);
            Friends.revalidate();
            Friends.repaint();
            JOptionPane.showMessageDialog(null, "Friends list refreshed!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to refresh friends list!");
        }
    }
    public static void main(String[] args) {
        UserDatabase userDatabase= UserDatabase.getInstance();
        ArrayList<User> users=userDatabase.getAll();
        new NewsFeedWindow(users.get(1));
    }
    public JPanel createfriendPanel(String photoPath,String username ,String state){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ImageIcon image = new ImageIcon(photoPath);
        Image img=image.getImage().getScaledInstance(25,40,Image.SCALE_SMOOTH);
        image=new ImageIcon(img);
        JLabel imageLabel = new JLabel(image);
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setForeground(Color.white);
        JLabel stateLabel = new JLabel(state);
        stateLabel.setForeground(state.toLowerCase().equals("online")?Color.GREEN:Color.red);
        panel.add(imageLabel);
        panel.add(usernameLabel);
        panel.add(stateLabel);

        return panel;
    }
    public void addStory(JPanel postPanel, String text , String imagePath){
        // a jpanel for each post
        JPanel singleStoryPanel = new JPanel();
        singleStoryPanel.setLayout(new BorderLayout());
        singleStoryPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // a Jtextarea for text
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(Color.decode("#24292e"));
        textArea.setForeground(Color.white);

        // creating a Jlabel for images
        ImageIcon Img = new ImageIcon(imagePath);
        Image scaledImage = Img.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaledImage));

        // now we add the component for the main singleStoryPanel
        singleStoryPanel.add(textArea, BorderLayout.SOUTH);
        singleStoryPanel.add(imgLabel, BorderLayout.NORTH);
        singleStoryPanel.setBackground(Color.decode("#24292e"));

        //now we add it to the post panel

        postPanel.add(singleStoryPanel);
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
}

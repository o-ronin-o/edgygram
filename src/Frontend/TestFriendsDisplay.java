package Frontend;

import Backend.*;
import Backend.Friends.FriendsDatabase;
import Backend.Friends.FriendsManagement;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TestFriendsDisplay extends JFrame {
    private JPanel container;
    private JScrollPane friends;
    private JList<JPanel> friendsList;
    public TestFriendsDisplay(User user) {

        setContentPane(container);
        setVisible(true);
        setSize(300,400);
        FriendsManagement friendsManagement= new FriendsManagement( UserDatabase.getInstance(),new FriendsDatabase());
        ArrayList<User> friend= friendsManagement.getFriends(user);
        ArrayList<String> friendsData=friendsManagement.displayList(friend);
        // Create the list of panels
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        for (String data : friendsData) {
            String[] friendDataArray = data.split(",");
            JPanel friendPanel = createfriendPanel(friendDataArray[0], friendDataArray[1], friendDataArray[2]);
            panelModel.addElement(friendPanel);
        }
        // Create the JList and set custom render to handle displaying Jpanel
        friendsList = new JList<>(panelModel);
        friendsList.setCellRenderer(new CustomRender());
        friendsList.setBackground(new Color(34, 34, 34));
        // Set the JList inside the JScrollPane
        friends.setViewportView(friendsList);
        friends.revalidate();
        friends.repaint();
    }



    private JPanel createfriendPanel(String photoPath,String username ,String state){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ImageIcon image = new ImageIcon(photoPath);
        Image img=image.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
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

    public static void main(String[] args) {
        UserDatabase userDatabase=  UserDatabase.getInstance();
        ArrayList<User> users=userDatabase.getAll();
        new TestFriendsDisplay(users.get(1));
    }
}

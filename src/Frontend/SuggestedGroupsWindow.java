package Frontend;

import Backend.Database;
import Backend.Friends.FriendRequestManagement;
import Backend.Friends.FriendsDatabase;
import Backend.Groups.Group;
import Backend.Groups.GroupManagement;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SuggestedGroupsWindow extends JFrame {
    private JPanel container;
    private JScrollPane suggestedScroll;
    private JList<JPanel> suggestedList;

    public SuggestedGroupsWindow(User user, NewsFeedWindow newsFeedWindow) {
        setTitle("Suggested Groups");
        setLocationRelativeTo(newsFeedWindow);
        setResizable(false);
        setContentPane(container);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(300,300);
        GroupManagement groupManagement= GroupManagement.getInstance();
        ArrayList<Group> suggestedGroups= groupManagement.suggestGroups(user);
       ArrayList<String> suggested= groupManagement.convertToDisplayFormat(suggestedGroups);
       DefaultListModel<JPanel> suggestedListModel = new DefaultListModel<>();
        for(String group: suggested ){
            String[] parts=group.split(",");
            JPanel panel = createSuggestionPanel(parts[0],parts[1]);
            suggestedListModel.addElement(panel);
        }
        suggestedList=new JList<>(suggestedListModel);
        suggestedList.setCellRenderer(new CustomRender());
        suggestedList.setBackground(new Color(34, 34, 34));
        //Add mouse listner to handle mouse click
        suggestedList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = suggestedList.locationToIndex(e.getPoint());
                Group selectedGroup;
                if(index>=0){
                    String suggestionData=suggested.get(index);
                    String[] suggestionDataArray = suggestionData.split(",");
                    String suggestionname=suggestionDataArray[1];
                    for(Group group:suggestedGroups){
                        if(group.getGroupName().equals(suggestionname)){
                            selectedGroup=group;
                            new GroupOptionsWindow(user,selectedGroup,newsFeedWindow);
                            break;
                        }
                    }
                }
            }
        });
        // Set the JList inside the JScrollPane
        suggestedScroll.setViewportView(suggestedList);
        suggestedScroll.revalidate();
        suggestedScroll.repaint();

    }
    public JPanel createSuggestionPanel(String photoPath,String username){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ImageIcon image = new ImageIcon(photoPath);
        Image img=image.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        image=new ImageIcon(img);
        JLabel imageLabel = new JLabel(image);
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setForeground(Color.white);
        panel.add(imageLabel);
        panel.add(usernameLabel);
        return panel;
    }
}

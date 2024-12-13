package Frontend;

import Backend.Groups.Group;
import Backend.Groups.*;
import Backend.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewGroupsWindow extends JFrame {
    private JPanel container;
    private JScrollPane GroupsScroll;
    private JList<JPanel> groupsList;
    public ViewGroupsWindow(User user, NewsFeedWindow newsFeedWindow) {
        setTitle("Group Management");
        setLocationRelativeTo(newsFeedWindow);
        setResizable(false);
        setContentPane(container);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(300,300);
        GroupManagement groupManagement= GroupManagement.getInstance();
        GroupDatabase groupDatabase = new GroupDatabase();
        HashMap<String,Group> allGroups = groupDatabase.loadGroupData();
        ArrayList<Group> groupList = new ArrayList<>(allGroups.values());
        ArrayList<Group> myGroups = new ArrayList<>();
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        if (groupList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No groups found");
            return;
        }
        //find groups that user is a member in
        for (Group group : groupList) {
            if(groupManagement.isMember(group.getGroupId(), user)) {
                myGroups.add(group);
            }
        }
        //convert the groups to displaying format and displaying them
        ArrayList<String> myGroupsOnForamt=groupManagement.convertToDisplayFormat(myGroups);
        for(String data:myGroupsOnForamt){
            String[] parts = data.split(",");
            JPanel panel = createSuggestionPanel(parts[0],parts[1]);
            panelModel.addElement(panel);
        }
        groupsList=new JList<>(panelModel);
        groupsList.setCellRenderer(new CustomRender());
        groupsList.setBackground(new Color(34, 34, 34));
        // Set the JList inside the JScrollPane
        GroupsScroll.setViewportView(groupsList);
        GroupsScroll.revalidate();
        GroupsScroll.repaint();
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

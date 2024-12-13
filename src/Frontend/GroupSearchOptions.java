package Frontend;

import Backend.*;
import Backend.Groups.Group;
import Backend.Groups.GroupJoinRequestsManagement;
import Backend.Groups.GroupManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupSearchOptions extends JFrame {
    private JPanel container;
    private JButton sendJoinRequestButton;
    private JButton leaveGroupButton;
    private JButton viewGroupButton;
    public GroupSearchOptions(User user, Group group,NewsFeedWindow newsFeedWindow) {
        setTitle(group.getGroupName()+" Group options");
        setLocationRelativeTo(newsFeedWindow);
        setResizable(false);
        setContentPane(container);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(350,200);

        //setting up the buttons
        sendJoinRequestButton.setBackground(Color.decode("#24292e"));
        sendJoinRequestButton.setForeground(Color.decode("#FFFFFF"));
        sendJoinRequestButton.setBorder(new RoundedBorder(10));

        viewGroupButton.setBackground(Color.decode("#24292e"));
        viewGroupButton.setForeground(Color.decode("#FFFFFF"));
        viewGroupButton.setBorder(new RoundedBorder(10));

        leaveGroupButton.setBackground(Color.decode("#24292e"));
        leaveGroupButton.setForeground(Color.decode("#FFFFFF"));
        leaveGroupButton.setBorder(new RoundedBorder(10));

        sendJoinRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupJoinRequestsManagement requestsManagement= new GroupJoinRequestsManagement(group);
               if( requestsManagement.sendJoinRequest(user))
                JOptionPane.showMessageDialog(newsFeedWindow, "Join Request Sent");
            }
        });
        viewGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new GroupWindow(group,user);
                dispose();
            }
        });
        leaveGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupManagement groupManagement= GroupManagement.getInstance();
                if(groupManagement.isMember(group.getGroupId(), user)) {
                    groupManagement.removeUserFromGroup(group.getGroupId(), user);
                    JOptionPane.showMessageDialog(newsFeedWindow, "Group leaved");
                }
                else {
                    JOptionPane.showMessageDialog(newsFeedWindow, "You are not a member in the group","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

package Frontend;

import Backend.Groups.Group;
import Backend.Groups.GroupJoinRequestsManagement;
import Backend.RoundedBorder;
import Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupOptionsWindow extends JFrame {
    private JPanel container;
    private JButton sendJoinRequestButton;
    private JButton viewGroupButton;
    public GroupOptionsWindow(User user,Group group, NewsFeedWindow newsFeedWindow) {
        setTitle(group.getGroupName()+" Group options");
        setLocationRelativeTo(newsFeedWindow);
        setResizable(false);
        setContentPane(container);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(350,200);

        sendJoinRequestButton.setBackground(Color.decode("#24292e"));
        sendJoinRequestButton.setForeground(Color.decode("#FFFFFF"));
        sendJoinRequestButton.setBorder(new RoundedBorder(10));

        viewGroupButton.setBackground(Color.decode("#24292e"));
        viewGroupButton.setForeground(Color.decode("#FFFFFF"));
        viewGroupButton.setBorder(new RoundedBorder(10));
        sendJoinRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupJoinRequestsManagement requestsManagement= new GroupJoinRequestsManagement(group);
                requestsManagement.sendJoinRequest(user);
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
    }
}

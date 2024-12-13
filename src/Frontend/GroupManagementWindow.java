package Frontend;

import Backend.Groups.GroupManagement;
import Backend.RoundedBorder;
import Backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupManagementWindow extends javax.swing.JFrame {

    private JPanel container;
    private JButton createGroupButton;
    private JButton viewGroupsButton;
    private JButton viewSuggestedGroupsButton;

    public GroupManagementWindow(User user, NewsFeedWindow newsFeedWindow) {
        setTitle("Group Management");
        setLocationRelativeTo(newsFeedWindow);
        setResizable(false);
        setContentPane(container);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(450,400);
        GroupManagement groupManagement=GroupManagement.getInstance();
        //set create group button
        createGroupButton.setBackground(Color.decode("#24292e"));
        createGroupButton.setForeground(Color.decode("#FFFFFF"));
        createGroupButton.setBorder(new RoundedBorder(10));
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String groupName= JOptionPane.showInputDialog(newsFeedWindow, "Enter Group Name", "Create Group", JOptionPane.PLAIN_MESSAGE);
                String groupDescription= JOptionPane.showInputDialog(newsFeedWindow, "Enter Group description", "Create Group", JOptionPane.PLAIN_MESSAGE);
                int respond= JOptionPane.showConfirmDialog(newsFeedWindow,"do you want to set a photo","Create Group", JOptionPane.YES_NO_OPTION);
                if (respond == JOptionPane.YES_OPTION) {
                    JFileChooser fileChooser = new JFileChooser();
                    //set the file filter
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));
                    int result = fileChooser.showOpenDialog(newsFeedWindow);
                    // If a file is selected continue
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
                        groupManagement.createGroup(groupName,groupDescription,selectedFile,user);

                    }
                }
                if(!groupName.isEmpty()||!groupDescription.isEmpty()) {
                    groupManagement.createGroup(groupName,groupDescription,"defaultProfile.jpeg",user);
                    JOptionPane.showMessageDialog(newsFeedWindow, "Group Created Successfully");
                }
            }
        });
        //set create group button
        viewGroupsButton.setBackground(Color.decode("#24292e"));
        viewGroupsButton.setForeground(Color.decode("#FFFFFF"));
        viewGroupsButton.setBorder(new RoundedBorder(10));
        viewGroupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewGroupsWindow(user,newsFeedWindow);
            }
        });
        viewSuggestedGroupsButton.setBackground(Color.decode("#24292e"));
        viewSuggestedGroupsButton.setForeground(Color.decode("#FFFFFF"));
        viewSuggestedGroupsButton.setBorder(new RoundedBorder(10));
        viewSuggestedGroupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SuggestedGroupsWindow(user,newsFeedWindow);
            }
        });
    }
}

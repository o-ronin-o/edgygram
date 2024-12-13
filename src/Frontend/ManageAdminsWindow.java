package Frontend;

import Backend.Groups.Group;
import Backend.Groups.GroupDatabase;
import Backend.Groups.GroupManagement;
import Backend.RoundedBorder;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageAdminsWindow extends JFrame {
    private JButton demoteAdmin;
    private JPanel panel1;
    private JButton makeAdminButton;

    public ManageAdminsWindow(Group group) {

        GroupManagement g = GroupManagement.getInstance();
        UserDatabase userDatabase = new UserDatabase();
        ArrayList<User> users = userDatabase.getAll();



        setContentPane(panel1);
        setTitle("Group");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(500,250,900, 600);
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel1.setBorder(new RoundedBorder(20));

        panel1.setBackground(Color.decode("#24292e"));


        demoteAdmin = setupButtons(demoteAdmin);
        makeAdminButton = setupButtons(makeAdminButton);
        demoteAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = JOptionPane.showInputDialog(
                        null,
                        "Enter Member ID:",
                        "Member ID Input",
                        JOptionPane.QUESTION_MESSAGE
                );


                if (memberId != null && !memberId.trim().isEmpty()) {
                    System.out.println("Member ID entered: " + memberId);
                    g.demoteAdmin(group.getGroupId(),userDatabase.getById(memberId));
                } else {
                    System.out.println("No member ID entered or canceled.");
                }
            }
        });

        makeAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = JOptionPane.showInputDialog(
                        null,
                        "Enter Member ID:",
                        "Member ID Input",
                        JOptionPane.QUESTION_MESSAGE
                );


                if (memberId != null && !memberId.trim().isEmpty()) {
                    System.out.println("Member ID entered: " + memberId);
                    g.makeAdmin(group.getGroupId(),userDatabase.getById(memberId));
                } else {
                    System.out.println("No member ID entered or canceled.");
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
    public static void main(String[] args) {
        GroupDatabase groupsDatabase = new GroupDatabase();
        HashMap<String, Group> groups = groupsDatabase.loadGroupData();
        ArrayList<Group> allGroups = new ArrayList<>(groups.values());

        new ManageAdminsWindow(allGroups.get(0));
    }
}

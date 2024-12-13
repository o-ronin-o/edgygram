package Frontend;

import Backend.Database;
import Backend.RoundedBorder;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EditProfileWindow extends JFrame {
    private JPanel mainPanel;
    private JButton editCoverButton;
    private JButton editPfpButton;
    private JButton changeBioButton;
    private JButton changePasswordButton;
    private JButton doneButton;

    public EditProfileWindow(User user,ProfileWindow profileWindow) {
        //setting up the main frame
        setTitle("Edit Profile");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
        mainPanel.setBackground(Color.decode("#24292e"));
        mainPanel.setForeground(Color.white);
        setVisible(true);
        setContentPane(mainPanel);


        //editCoverButton setting up
        editCoverButton.setBackground(Color.decode("#2b3137"));
        editCoverButton.setForeground(Color.white);
        editCoverButton.setBorder(new RoundedBorder(20));
        //editPfpButton setting up
        editPfpButton.setBackground(Color.decode("#2b3137"));
        editPfpButton.setForeground(Color.white);
        editPfpButton.setBorder(new RoundedBorder(20));
        //ChangePasswordButton setting up
        changePasswordButton.setBackground(Color.decode("#2b3137"));
        changePasswordButton.setForeground(Color.white);
        changePasswordButton.setBorder(new RoundedBorder(20));
        //changeBioButton setting up
        changeBioButton.setBackground(Color.decode("#2b3137"));
        changeBioButton.setForeground(Color.white);
        changeBioButton.setBorder(new RoundedBorder(20));

        // doneButton setting up
        doneButton.setBackground(Color.decode("#2b3137"));
        doneButton.setForeground(Color.white);
        doneButton.setBorder(new RoundedBorder(20));
        // SETTING UP A FILE CHOOSER
        JFileChooser fileChooser = new JFileChooser();
        editCoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Select Cover File");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File file) {

                        return file.isDirectory() || file.getName().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$");
                    }

                    @Override
                    public String getDescription() {
                        return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
                    }
                });
                int result = fileChooser.showOpenDialog(mainPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    System.out.println("file selected");
                       }
                else{
                    System.out.println("no file selected");
                }
                user.setCoverPicture(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        editPfpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Select Cover File");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File file) {

                        return file.isDirectory() || file.getName().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$");
                    }

                    @Override
                    public String getDescription() {
                        return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
                    }
                });
                int result = fileChooser.showOpenDialog(mainPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    System.out.println("file selected");
                    user.setProfilePicture(fileChooser.getSelectedFile().getAbsolutePath());

                }
                else{
                    System.out.println("no file selected");
                }
                user.setProfilePicture(fileChooser.getSelectedFile().getAbsolutePath());

            }
        });
        changeBioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String newBio = JOptionPane.showInputDialog(mainPanel,"Enter your new bio","Input Dialog" ,JOptionPane.QUESTION_MESSAGE);
               user.setBio(newBio);
            }
        });
        JPasswordField passwordField = new JPasswordField();
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] message = {
                        "Enter your new password", passwordField
                };

                int option = JOptionPane.showConfirmDialog(mainPanel, message, "New Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                String newPassword = null;
                if (option == JOptionPane.OK_OPTION) {
                    newPassword = new String(passwordField.getPassword());
                    if (!newPassword.isEmpty()) {
                        System.out.println("New password set: " + newPassword);
                    } else {
                        System.out.println("Password cannot be empty!");
                    }
                } else {
                    System.out.println("Password change canceled.");
                }

                String pass = newPassword;
                try {
                    MessageDigest hashalgo = MessageDigest.getInstance("SHA-256");
                    byte[] hashbytes = hashalgo.digest(pass.getBytes(StandardCharsets.UTF_8));
                    StringBuilder stringBuilder = new StringBuilder();
                    for (byte bytes : hashbytes) {
                        stringBuilder.append(String.format("%02x", bytes));
                    }
                    pass = stringBuilder.toString();
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
                user.setPassword(pass);
            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database<User> userDatabase =  UserDatabase.getInstance();
                userDatabase.remove(user);
                userDatabase.add(user);
                new ProfileWindow(user,true);
                profileWindow.dispose();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        //new EditProfileWindow();
    }
}

package Frontend;

import Backend.RoundedBorder;
import Backend.User;
import Backend.login;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginWindow extends JFrame {
    private JPanel loginpanel;
    private JTextField Usertext;
    private JButton signUpButton;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JTextPane usernameTextPane;
    private JTextPane passwordTextPane;
    private JTextPane welcomeToEdgyGramTextPane;

    public LoginWindow(){

        setVisible(true);
        setContentPane(loginpanel);
        setTitle("Login");
        setSize(600,250);
        setLocationRelativeTo(null);
        login l= new login();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeToEdgyGramTextPane.setFont(new Font("Times New Roman", Font.BOLD, 23));
        loginButton.addActionListener(e -> {
            String pass = new String(passwordField.getPassword());
            try {
                MessageDigest hashalgo= MessageDigest.getInstance("SHA-256");
                byte[]  hashbytes= hashalgo.digest(pass.getBytes(StandardCharsets.UTF_8));
                StringBuilder stringBuilder=new StringBuilder();
                for(byte bytes:hashbytes){
                    stringBuilder.append(String.format("%02x", bytes));
                }
                pass=stringBuilder.toString();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
            if(l.checkEmailandPassword(Usertext.getText(), pass, Usertext.getText())){
               User user= l.getUser(Usertext.getText());
               l.updateStatus(user,1);
                new NewsFeedWindow(user);
                dispose();


            }
            else {
                JOptionPane.showMessageDialog(loginpanel,"Invalid login credentials");
            }
        });

        signUpButton.addActionListener(e -> {
            setVisible(false);
            SignupWindow s1= new SignupWindow();
        });

        loginButton.setBackground(Color.decode("#24292e"));
        loginButton.setForeground(Color.decode("#FFFFFF"));
        loginButton.setBorder(new RoundedBorder(10));

        signUpButton.setBackground(Color.decode("#24292e"));
        signUpButton.setForeground(Color.decode("#FFFFFF"));
        signUpButton.setBorder(new RoundedBorder(10));

        usernameTextPane.setBorder(new RoundedBorder(4));
        usernameTextPane.setBackground(Color.decode("#24292e"));
        usernameTextPane.setForeground(Color.decode("#FFFFFF"));
        usernameTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centering text horizontally
        usernameTextPane.setAlignmentY(Component.CENTER_ALIGNMENT);

        passwordTextPane.setBorder(new RoundedBorder(4));
        passwordTextPane.setBackground(Color.decode("#24292e"));
        passwordTextPane.setForeground(Color.decode("#FFFFFF"));
        passwordTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centering text horizontally
        passwordTextPane.setAlignmentY(Component.CENTER_ALIGNMENT);
    }


    public static void main(String[] args){
        LoginWindow l=new LoginWindow();
    }
}
//مظبوط


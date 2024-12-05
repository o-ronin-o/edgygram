package Frontend;

import Backend.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginWindow extends JFrame {
    private JPanel loginpanel;
    private JTextField Usertext;
    private JButton signUpButton;
    private JButton loginButton;
    private JPasswordField passwordField;

    public LoginWindow(){
        setVisible(true);
        setContentPane(loginpanel);
        setTitle("Login");
        setSize(600,300);
        setLocationRelativeTo(null);
        login l= new login();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(loginpanel,"Invalid login credentials");
            }
        });

        signUpButton.addActionListener(e -> {
            setVisible(false);
            SignupWindow s1= new SignupWindow();
        });

    }


    public static void main(String[] args){
        LoginWindow l=new LoginWindow();
    }
}




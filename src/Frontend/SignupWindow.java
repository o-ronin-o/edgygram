package Frontend;

import Backend.User;
import Backend.UserDatabase;
import Backend.Validation;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupWindow extends JFrame {
    private JTextField UsernameField;
    private JTextField emailField;
    private JTextField DOBField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JPasswordField passwordField1;
    private JPanel SignupPanel;
    private JDateChooser datechooser1;
    boolean flag1, flag2, flag3, flag4, flag5;

    public SignupWindow() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(SignupPanel);
        setTitle("Signup");
        setSize(380, 250);
        setLocationRelativeTo(null);
        Validation v = new Validation();
        datechooser1.setPreferredSize(new Dimension(200,10));
        UsernameField.addActionListener(e -> {
            if (!v.usernameValid(UsernameField.getText())) {
                JOptionPane.showMessageDialog(SignupPanel, "Invalid Length! Username length should be between 4 and 10");
            } else {
                flag1 = true;
            }
        });

        emailField.addActionListener(e -> {
            if (!v.emailValid(emailField.getText())) {
                JOptionPane.showMessageDialog(SignupPanel, "Invalid Email! Try again");
            } else {
                flag2 = true;
            }
        });

        passwordField.addActionListener(e -> {
            String pass = new String(passwordField.getPassword());
            if (!v.passwordValid(pass)) {
                JOptionPane.showMessageDialog(SignupPanel, "Invalid Password! Password length should be between 6 and 15");
            } else {
                flag3 = true;
            }
        });

        passwordField1.addActionListener(e -> {
            String pass1 = new String(passwordField.getPassword());
            String pass2 = new String(passwordField1.getPassword());
            if (!pass2.equals(pass1)) {
                JOptionPane.showMessageDialog(SignupPanel, "Password are not the not same!Try Again");
            } else {
                flag4 = true;
            }
        });


        signUpButton.addActionListener(e -> {
            if (!flag1 || !flag2 || !flag3 || !flag4) {
                JOptionPane.showMessageDialog(SignupPanel, "Invalid Field/s! Try again");
            } else {
                UserDatabase UDB = new UserDatabase();

                ArrayList<User> list = UDB.getAll();

                Integer intUIDmax = Integer.MAX_VALUE;
                Random random = new Random();
                Integer intUID = random.nextInt(intUIDmax);
                if (list == null || list.isEmpty()) {
                    intUID = random.nextInt(intUIDmax);
                }
                String UID = Integer.toString(intUID);
                Date selectedDate = datechooser1.getDate();
                String Dob = "";
                if (selectedDate != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Dob = dateFormat.format(selectedDate);
                } else {
                    JOptionPane.showMessageDialog(SignupPanel, "Please select a valid date of birth.");
                    return;
                }

                String pass = new String(passwordField.getPassword());
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

                User u = new User(UID, UsernameField.getText(), pass, emailField.getText(), Dob, "offline", "D:\\Downloads\\cat.jpg", "D:\\Downloads\\cat.jpg","debug");

                if (UDB.add(u)) {

                    LoginWindow lW = new LoginWindow();
                    dispose();
                }


            }
        });
    }


    private void createUIComponents() {

        datechooser1 = new JDateChooser();
    }

}

package Frontend;

import Backend.Groups.Group;
import Backend.Groups.GroupJoinRequestsManagement;
import Backend.Groups.GroupManagement;
import Backend.SearchEngine;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ManageJoinRequestWindow extends JFrame {
    private JPanel container;
    private JScrollPane requestScroll;
    private JList<JPanel> requestList;
    public ManageJoinRequestWindow(User user, Group group){
        setTitle("Manage Join Requests");
        setContentPane(container);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(400,400);
        setResizable(false);
        GroupJoinRequestsManagement requestsManagement= new GroupJoinRequestsManagement(group);
        UserDatabase userDatabase= UserDatabase.getInstance();
        SearchEngine searchEngine= SearchEngine.getInstance();
        ArrayList<String> requestsId= requestsManagement.viewJoinRequests();
        ArrayList<User> users=new ArrayList<>();
        for (String requestId: requestsId){
            users.add(userDatabase.getById(requestId));
        }
        DefaultListModel<JPanel> model= new DefaultListModel<>();
        ArrayList<String> requestsOnFormat=searchEngine.getUserResultsInFormat(users);
        for(String requeststr: requestsOnFormat){
            String[] parts=requeststr.split(",");
            JPanel panel=createSuggestionPanel(parts[0],parts[1]);
            model.addElement(panel);
        }
        requestList=new JList<>(model);
        requestList.setCellRenderer(new CustomRender());
        requestList.setBackground(new Color(34, 34, 34));

        requestList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index=requestList.locationToIndex(e.getPoint());
                if(index>=0){
                    String data= requestsOnFormat.get(index);
                    String[] dataParts=data.split(",");
                    String username=dataParts[1];
                    User sender=null;
                    for(User user: users){
                        if(user.getUsername().equals(username)){
                            sender=user;
                        }
                    }
                    if(sender ==null){
                        JOptionPane.showMessageDialog(null, "Error","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to accept the request from " + sender.getUsername() + "?", "Join Request", JOptionPane.YES_NO_OPTION);
                    if(choice==JOptionPane.YES_OPTION){
                        requestsManagement.approveJoinRequest(user.getId());
                        JOptionPane.showMessageDialog(ManageJoinRequestWindow.this,"Request Accepted","Success",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(choice==JOptionPane.NO_OPTION){
                        requestsManagement.rejectJoinRequest(user.getId());
                        JOptionPane.showMessageDialog(ManageJoinRequestWindow.this,"Request Rejected");

                    }
                }
            }
            });
        requestScroll.setViewportView(requestList);
        requestScroll.revalidate();
        requestScroll.repaint();
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

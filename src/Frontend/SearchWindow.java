package Frontend;

import Backend.Database;
import Backend.SearchEngine;
import Backend.User;
import Backend.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SearchWindow extends JFrame {
    private JPanel container;
    private JScrollPane suggestionScroll;
    private JList<JPanel> suggestionList;

    public SearchWindow(User user, String searchInput, NewsFeedWindow newsFeedWindow) {
        System.out.println("the search is "+searchInput);
        setTitle("Search Result");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,300);
        setLocationRelativeTo(newsFeedWindow);
        setResizable(false);
        setVisible(true);
        setContentPane(container);
        SearchEngine searchEngine= SearchEngine.getInstance();
        ArrayList<User> suggested= searchEngine.searchUser(user,searchInput);
        ArrayList<String> suggestedUsers = searchEngine.getUserResultsInFormat(suggested);
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        System.out.println(suggestedUsers);
        if(suggestedUsers.isEmpty()){
            JOptionPane.showMessageDialog(null, "No search result");
            return;
        }
        for(String data: suggestedUsers) {
            String[] parts = data.split(",");
            System.out.println(parts[0]+" "+parts[1]);
            JPanel suggestionPanel= createSuggestionPanel(parts[0],parts[1]);
            panelModel.addElement(suggestionPanel);
        }
        // Create the JList and set custom render to handle displaying Jpanel
        suggestionList=new JList<>(panelModel);
        suggestionList.setCellRenderer(new CustomRender());
        suggestionList.setBackground(new Color(34, 34, 34));
        suggestionList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = suggestionList.getSelectedIndex();
                if(index>=0){
                    Database<User> userDatabase=new UserDatabase();
                    ArrayList<User> allUsers= userDatabase.getAll();
                    for(User userr: allUsers){
                        if(userr.getUsername().equals(suggested.get(index).getUsername())){
                            new UserSearchOptions(user,userr);
                            dispose();
                            return;
                        }
                    }
                }
            }
        });
        // Set the JList inside the JScrollPane
        suggestionScroll.setViewportView(suggestionList);
        suggestionScroll.revalidate();
        suggestionScroll.repaint();

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
    public static void main(String[] args) {
        UserDatabase userDatabase= new UserDatabase();
        ArrayList<User> users=userDatabase.getAll();
        new SearchWindow(users.get(0),"ank",new NewsFeedWindow(users.get(0)));
    }
}

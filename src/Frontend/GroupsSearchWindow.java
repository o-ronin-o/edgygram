package Frontend;

import Backend.*;
import Backend.Groups.GroupData;
import Backend.Groups.GroupsDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GroupsSearchWindow extends JFrame {
    private JPanel container;
    private JScrollPane groupsScrollPane;
    private JList<JPanel> suggestionList;
    public GroupsSearchWindow(User user,String searchInput,NewsFeedWindow newsFeedWindow) {
        setTitle("Groups Search");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(newsFeedWindow);
        setSize(300,400);
        setResizable(false);
        setVisible(true);
        setContentPane(container);
        SearchEngine searchEngine= SearchEngine.getInstance();
        ArrayList<GroupData> suggested= searchEngine.searchGroup(searchInput);
        ArrayList<String> suggestedGroups= searchEngine.getGroupResultsInFormat(suggested);
        DefaultListModel<JPanel> panelModel = new DefaultListModel<>();
        System.out.println(suggestedGroups);
        if(suggestedGroups.isEmpty()){
            JOptionPane.showMessageDialog(null, "No search result");
            return;
        }
        for(String data : suggestedGroups){
            String[] parts = data.split(",");
            JPanel suggestionPanel= createSuggestionPanel(parts[0],parts[1]);
            panelModel.addElement(suggestionPanel);
        }
        suggestionList=new JList<>(panelModel);
        suggestionList.setCellRenderer(new CustomRender());
        suggestionList.setBackground(new Color(34, 34, 34));
        suggestionList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = suggestionList.getSelectedIndex();
                if(index>=0){
                    GroupsDatabase groupsDatabase=new GroupsDatabase();
                    ArrayList<GroupData> allGroups= groupsDatabase.getAll();
                    for(GroupData group : allGroups){
                        if(group.getGroupName().equals(suggested.get(index).getGroupName())){
                            new GroupSearchOptions();
                            dispose();
                            return;
                        }
                    }
                }

                }
        });
        groupsScrollPane.setViewportView(suggestionList);
        groupsScrollPane.revalidate();
        groupsScrollPane.repaint();
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

package Backend.Groups;

import Backend.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupData extends Database<Content> {
    @Expose
    private String groupId;
    @Expose
    private String groupName;
    @Expose
    private String groupDescription;
    @Expose
    private String groupPicture;
    @Expose
    private ArrayList<User> groupMembers;
    @Expose
    private String primaryAdminId;
    @Expose
    private ArrayList<User> Admins;
    @Expose
    private final String postsFileName = "GroupsPosts.json";
    @Expose
    private static final AtomicInteger counter = new AtomicInteger(0);


    public GroupData(String groupName, String groupDescription, String groupPicture, ArrayList<User> groupMembers,String groupId,User user) {
        super(null);
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupPicture = groupPicture;
        this.groupMembers = groupMembers;
        this.groupId = groupId;
        this.primaryAdminId = user.getId();
        this.Admins = new ArrayList<User>();
        addAdmin(user);
    }

    public String getGroupId() {

        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public String getGroupPicture() {
        return groupPicture;
    }

    public ArrayList<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public void setGroupPicture(String groupPicture) {
        this.groupPicture = groupPicture;
    }

    public void setGroupMembers(ArrayList<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public boolean isPrimaryAdmin(User user) {
        return this.primaryAdminId.equals(user.getId());
    }

    public String getPrimaryAdminId() {
        return primaryAdminId;
    }

    public void setPrimaryAdminId(String primaryAdminId) {
        this.primaryAdminId = primaryAdminId;
    }

    public ArrayList<User> getAdmins() {
        return Admins;
    }

    public void setAdmins(ArrayList<User> admins) {
        this.Admins = admins;
    }

    public void addAdmin(User user) {
        Admins.add(user);
    }

    public static String generateID() {
        return "CID-" + counter.incrementAndGet();
    }




    public ArrayList<Content> getAllPosts() {
        fileName = postsFileName;
        return load(new TypeToken<ArrayList<Post>>() {
        }.getType());

    }


    public ArrayList<Content> getAllStories() {
        return null;
    }


    public void saveAllPosts(ArrayList<Content> list) {
        fileName = postsFileName;
        save(list);
    }


    public void saveAllStories(ArrayList<Content> list) {

    }

    @Override
    public boolean add(Content item) {

        fileName = postsFileName;
        ArrayList<Content> posts = load(new TypeToken<ArrayList<Post>>() {
        }.getType());
        posts.add(item);
        save(posts);
        System.out.println("Added Post: " + item.getContentId());
        return true;
    }


    @Override
    public void remove(Content item) {
        fileName = postsFileName;
        ArrayList<Content> posts = load(new TypeToken<ArrayList<Post>>() {
        }.getType());
        posts.removeIf(c -> c.getContentId().equals(item.getContentId()));
        System.out.println("Removing Post: " + item.getContentId());
        save(posts);
    }

    @Override
    public ArrayList<Content> getAll() {
        fileName = postsFileName;
        ArrayList<Content> allContent = new ArrayList<>(load(new TypeToken<ArrayList<Post>>() {
        }.getType()));
        return allContent;
    }

    @Override
    public Content getById(String id) {
        ArrayList<Content> allContent = getAll();
        for (Content item : allContent) {
            if (item.getContentId().equals(id)) {
                return item;
            }
        }
        return null;
    }
    public boolean isAdmin(User user) {
        System.out.println("Group ID: " + this.getGroupId());
        if (this.getGroupId() == null) {
            System.out.println("Group ID is null!");
        }

        GroupsDatabase groupsDB = new GroupsDatabase();
        ArrayList<GroupData> groups = groupsDB.getAll();
        for (GroupData group : groups) {
            if (group.getGroupId().matches(this.getGroupId())) {
                for(User admins : group.getAdmins()) {
                    if (admins.getId().equals(user.getId())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

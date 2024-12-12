package Backend;

import java.util.ArrayList;

public interface ContentHandling {
    public ArrayList<Content> getAllPosts();
    public ArrayList<Content> getAllStories();
    public void saveAllPosts(ArrayList<Content> list);
    public void saveAllStories(ArrayList<Content> list);
}

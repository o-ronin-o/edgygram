package Backend.Groups;

import Backend.Database;
import Backend.Post;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class GroupPost extends Database<Post> {
    private static GroupPost instance;
    private GroupPost() {
        super("groupPosts.json");
    }
    @Override
    public boolean add(Post post) {
        ArrayList<Post> allPosts=getAll();
        allPosts.add(post);
        save(allPosts);
        return true;
    }

    @Override
    public void remove(Post item) {
        ArrayList<Post> allPosts=getAll();
        allPosts.removeIf(post -> post.getContentId().equals(item.getContentId()));
        save(allPosts);
    }

    @Override
    public ArrayList<Post> getAll() {
        return load(new TypeToken<ArrayList<Post>>() {}.getType());
    }

    @Override
    public Post getById(String id) {
        ArrayList<Post> allPosts=getAll();
        for (Post post : allPosts) {
            if (post.getContentId().equals(id)) {
                return post;
            }
        }
        return null;
    }
    public static GroupPost getInstance() {
        if (instance == null) {
            instance = new GroupPost();
        }
        return instance;
    }
}

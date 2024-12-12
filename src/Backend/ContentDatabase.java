package Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContentDatabase extends Database<Content> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String postsFileName = "Posts.json";
    private final String storiesFileName = "Stories.json";

    public ContentDatabase() {
        super(null); // File name is not used directly
    }

    @Override
    public boolean add(Content item) {
        if (item instanceof Post) {
            fileName=postsFileName;
            ArrayList<Content> posts=load(new TypeToken<ArrayList<Post>>() {}.getType());
            posts.add(item);
            save(posts);
            System.out.println("Added Post: " + item.getContentId());
        } else if (item instanceof Story) {
            fileName=storiesFileName;
            ArrayList<Content> stories = load(new TypeToken<ArrayList<Post>>() {}.getType());
            stories.add( item);
            save(stories);
            scheduleRemoval((Story) item, 24); // Schedule removal in 24 hours
            System.out.println("Added Story: " + item.getContentId());
        }
        return true;
    }

    @Override
    public void remove(Content item) {
        if (item instanceof Post) {
            fileName=postsFileName;
            ArrayList<Content> posts = load(new TypeToken<ArrayList<Post>>() {}.getType());
            posts.removeIf(c -> c.getContentId().equals(item.getContentId()));
            System.out.println("Removing Post: " + item.getContentId());
            save(posts);
           } else if (item instanceof Story) {
            fileName=storiesFileName;
            ArrayList<Content> stories = load( new TypeToken<ArrayList<Story>>() {}.getType());
            stories.removeIf(c -> c.getContentId().equals(item.getContentId()));
            save(stories);
            System.out.println("Removed Story: " + item.getContentId());
        }
    }

    @Override
    public ArrayList<Content> getAll() {
        fileName=postsFileName;
        ArrayList<Content> allContent = new ArrayList<>(load(new TypeToken<ArrayList<Post>>() {
        }.getType()));
        fileName=storiesFileName;
        allContent.addAll(load( new TypeToken<ArrayList<Story>>() {}.getType()));
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
    public ArrayList<Content> getAllPosts() {
        fileName = postsFileName;
        return load(new TypeToken<ArrayList<Post>>() {}.getType());
    }

    public ArrayList<Content> getAllStories() {
        fileName=storiesFileName;
        return load(new TypeToken<ArrayList<Story>>() {}.getType());
    }
    public void saveAllPosts(ArrayList<Content> list){
        fileName = "Posts.json";
        save(list);
    }
    public void saveAllStories(ArrayList<Content> list){
        fileName = "Stories.json";
        save(list);
    }
// بتمسح بعد المده اللي انت عايزها كل اللي عليك تديلها اوبجيكت الستوري
    public void scheduleRemoval(Story story, int delayInHours) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            fileName=storiesFileName;
            ArrayList<Content> stories = load( new TypeToken<ArrayList<Content>>() {}.getType());
            if (stories.removeIf(s -> s.getContentId().equals(story.getContentId()))) {
                save(stories);
                System.out.println("Removed Story: " + story.getContentId());
            }
        }, delayInHours, TimeUnit.HOURS);
        scheduler.shutdown();
    }

}

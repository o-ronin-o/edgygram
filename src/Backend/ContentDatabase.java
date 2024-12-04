package Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
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
            ArrayList<Content> posts = loadSpecific(postsFileName, new TypeToken<ArrayList<Post>>() {}.getType());
            posts.add(  item);
            saveSpecific(posts, postsFileName);
            System.out.println("Added Post: " + item.getContentId());
        } else if (item instanceof Story) {
            ArrayList<Content> stories = loadSpecific(storiesFileName, new TypeToken<ArrayList<Story>>() {}.getType());
            stories.add( item);
            saveSpecific(stories, storiesFileName);
            scheduleRemoval((Story) item, 24); // Schedule removal in 24 hours
            System.out.println("Added Story: " + item.getContentId());
        }
        return true;
    }

    @Override
    public void remove(Content item) {
        if (item instanceof Post) {
            ArrayList<Content> posts = loadSpecific(postsFileName, new TypeToken<ArrayList<Post>>() {}.getType());
            posts.removeIf(c -> c.getContentId().equals(item.getContentId()));
            System.out.println("Removing Post: " + item.getContentId());
            saveSpecific(posts, postsFileName);
           } else if (item instanceof Story) {
            ArrayList<Content> stories = loadSpecific(storiesFileName, new TypeToken<ArrayList<Story>>() {}.getType());
            stories.removeIf(c -> c.getContentId().equals(item.getContentId()));
            saveSpecific(stories, storiesFileName);
            System.out.println("Removed Story: " + item.getContentId());
        }
    }

    @Override
    public ArrayList<Content> getAll() {
        ArrayList<Content> allContent = new ArrayList<>();
        allContent.addAll(loadSpecific(postsFileName, new TypeToken<ArrayList<Post>>() {}.getType()));
        allContent.addAll(loadSpecific(storiesFileName, new TypeToken<ArrayList<Story>>() {}.getType()));
        return allContent;
    }

    public ArrayList<Post> getAllPosts() {
        return loadSpecific(postsFileName, new TypeToken<ArrayList<Post>>() {}.getType());
    }

    public ArrayList<Story> getAllStories() {
        return loadSpecific(storiesFileName, new TypeToken<ArrayList<Story>>() {}.getType());
    }
// بتمسح بعد المده اللي انت عايزها كل اللي عليك تديلها اوبجيكت الستوري
    public void scheduleRemoval(Story story, int delayInHours) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            ArrayList<Story> stories = loadSpecific(storiesFileName, new TypeToken<ArrayList<Story>>() {}.getType());
            if (stories.removeIf(s -> s.getContentId().equals(story.getContentId()))) {
                saveSpecific(stories, storiesFileName);
                System.out.println("Removed Story: " + story.getContentId());
            }
        }, delayInHours, TimeUnit.HOURS);
        scheduler.shutdown();
    }
//اضطريت اعمل لود وسيف مختلفين عن بتوع السوبر كلاس 
    private <T> ArrayList<T> loadSpecific(String fileName, Type type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<T> list = gson.fromJson(reader, type);
            return (list == null) ? new ArrayList<>() : list;
        } catch (IOException e) {
            System.out.println("File not found: " + fileName);
            return new ArrayList<>();
        }
    }

    private <T> void saveSpecific(ArrayList<T> list, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + fileName);
        }
    }
}

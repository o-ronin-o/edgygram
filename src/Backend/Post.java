package Backend;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class Post extends Content {

    @Expose
    private String contentType;
    public Post( String contentId ,String authorId, String content, LocalDateTime timeStamp,String picPath, String contentType) {
        super( contentId ,authorId, content, timeStamp,picPath);
        this.contentType = "Post";
    }
    public Post( String contentId ,String authorId, String content, LocalDateTime timeStamp,String picPath,String groupId,String authorName, String contentType) {
        super( contentId ,authorId, content, timeStamp,picPath,groupId,authorName);
        this.contentType = "Post";
    }


    public String getContentType() {
        return contentType;
    }



}

package Backend;

import java.time.LocalDateTime;

public class Post extends Content {

    private String contentType;

    public Post( String contentId ,String authorId, String content, LocalDateTime timeStamp,String picPath, String contentType) {
        super( contentId ,authorId, content, timeStamp,picPath);
        this.contentType = "Post";
    }

    public String getContentType() {
        return contentType;
    }



}

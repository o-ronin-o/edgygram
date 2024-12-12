package Backend;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class Story extends Content{

    @Expose
    private String ContentType;

    public Story(String contentId ,String authorId, String content, LocalDateTime timeStamp,String picPath, String contentType) {
        super(contentId ,authorId, content, timeStamp,picPath);
        this.ContentType = "Story";
    }
    public String getContentType() {
        return ContentType;
    }

}

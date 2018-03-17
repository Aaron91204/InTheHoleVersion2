package com.example.aaron.inthehole;

import java.util.Date;

/**
 * Created by Aaron on 15/03/2018.
 */

public class Message {
    private String content,username;
    private String timeStamp;
    public Message()
    {

    }
    public Message(String content,String username)
    {
        this.content=content;
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent()
    {
      return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTime() {
        return timeStamp;
    }

    public void setTime(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

package com.example.aaron.inthehole;
public class Message { // Get and Set for messages for discussion board
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

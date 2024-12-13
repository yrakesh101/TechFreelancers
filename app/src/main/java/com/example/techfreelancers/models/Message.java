package com.example.techfreelancers.models;


public class Message {
    private String username;
    private String dateReceived;
    private String lastMessage;
    private int profilePic;

    public Message(String username, String dateReceived, String lastMessage, int profilePic) {
        this.username = username;
        this.dateReceived = dateReceived;
        this.lastMessage = lastMessage;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getProfilePic() {
        return profilePic;
    }
}

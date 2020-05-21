package com.example.zing;

public class Song {
    private String songName;
    private int fileId;

    public Song(String songName, int fileId) {
        this.songName = songName;
        this.fileId = fileId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}

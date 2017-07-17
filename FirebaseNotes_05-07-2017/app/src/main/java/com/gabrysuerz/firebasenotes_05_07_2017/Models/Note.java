package com.gabrysuerz.firebasenotes_05_07_2017.Models;

/**
 * Created by gabrysuerz on 20/06/17.
 */

public class Note {

    private String noteId;
    private String title;
    private String content;
    private String audioPath;
    private String imagePath;
    private String sketchPath;
    private String noteType;
    private long dateCreated;
    private long nextReminder;
    private String category;
    private boolean cloudAudioExist;
    private boolean cloudImageExist;
    private boolean cloudSketchExist;

    public Note() {
    }

    public Note(String UID) {
        this.noteId = UID;
        this.dateCreated = System.currentTimeMillis();
    }

    public String getNoteId() {
        return noteId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSketchPath() {
        return sketchPath;
    }

    public void setSketchPath(String sketchPath) {
        this.sketchPath = sketchPath;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public long getNextReminder() {
        return nextReminder;
    }

    public void setNextReminder(long nextReminder) {
        this.nextReminder = nextReminder;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == "") {
            this.category = null;
        } else {
            this.category = category;
        }
    }

    public boolean isCloudAudioExist() {
        return cloudAudioExist;
    }

    public void setCloudAudioExist(boolean cloudAudioExist) {
        this.cloudAudioExist = cloudAudioExist;
    }

    public boolean isCloudImageExist() {
        return cloudImageExist;
    }

    public void setCloudImageExist(boolean cloudImageExist) {
        this.cloudImageExist = cloudImageExist;
    }

    public boolean isCloudSketchExist() {
        return cloudSketchExist;
    }

    public void setCloudSketchExist(boolean cloudASketchExist) {
        this.cloudSketchExist = cloudASketchExist;
    }
}

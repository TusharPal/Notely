package com.a1kesamose.notely.objects;

public class Note
{
    public long NOTE_ID;
    public String NOTE_TITLE;
    public String NOTE_CONTENT;
    public String NOTE_TIMESTAMP;

    public Note()
    {
        this.NOTE_ID = 0;
        this.NOTE_TITLE = "";
        this.NOTE_CONTENT = "";
        this.NOTE_TIMESTAMP = "";
    }

    public Note(long id, String title, String content, String timeStamp)
    {
        this.NOTE_ID = id;
        this.NOTE_TITLE = title;
        this.NOTE_CONTENT = content;
        this.NOTE_TIMESTAMP = timeStamp;
    }
}

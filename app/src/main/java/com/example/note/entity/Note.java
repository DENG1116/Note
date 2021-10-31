package com.example.note.entity;

public class Note {
    private long id;//主键
    private String content;//笔记内容
    private String time;//笔记时间
    private int tog;//笔记标签

    public Note() {
    }

    public Note(String content, String time, int tog) {
        this.content = content;
        this.time = time;
        this.tog = tog;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTog() {
        return tog;
    }

    public void setTog(int tog) {
        this.tog = tog;
    }

    @Override
    public String toString() {
        return content + "\n" + time.substring(5, 16) + " " + id;
    }
}

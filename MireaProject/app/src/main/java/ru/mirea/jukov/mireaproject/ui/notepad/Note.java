package ru.mirea.jukov.mireaproject.ui.notepad;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String name;
    private String text;
    private String date;

    public Note(String name, String date, String text) {
        this.name = name;
        this.date = date;
        this.text = text;
    }

    public Note() { }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
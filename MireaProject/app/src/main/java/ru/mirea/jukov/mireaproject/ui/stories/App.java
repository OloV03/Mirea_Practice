package ru.mirea.jukov.mireaproject.ui.stories;

import android.app.Application;

import androidx.room.Room;

import ru.mirea.jukov.mireaproject.ui.notepad.NoteDatabase;

public class App extends Application {
    public static App instance;
    private AppDatabase database;
    private NoteDatabase databaseNote;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries().build();
        databaseNote = Room.databaseBuilder(this, NoteDatabase.class, "databaseNote")
                .allowMainThreadQueries().build();
    }

    public static App getInstance(){ return instance; }

    public AppDatabase getDatabase() { return database; }

    public NoteDatabase getDatabaseNote() { return databaseNote; }
}

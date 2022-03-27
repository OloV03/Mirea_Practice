package ru.mirea.jukov.mireaproject.ui.stories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query ("SELECT * FROM item WHERE id = :id")
    Item getById(long id);

    @Insert
    void insert(Item item);
    @Update
    void update(Item item);
    @Delete
    void delete(Item item);
}

package ru.mirea.jukov.mireaproject.ui.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ru.mirea.jukov.mireaproject.MainActivity;
import ru.mirea.jukov.mireaproject.R;
import ru.mirea.jukov.mireaproject.ui.stories.App;
import ru.mirea.jukov.mireaproject.ui.stories.AppDatabase;
import ru.mirea.jukov.mireaproject.ui.stories.Item;
import ru.mirea.jukov.mireaproject.ui.stories.ItemDao;

public class StoryView extends AppCompatActivity {

    private EditText nameEditStory;
    private EditText dateEditStory;
    private EditText wordEditStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_view);

        nameEditStory = findViewById(R.id.nameEditStory);
        dateEditStory = findViewById(R.id.dateEditStory);
        wordEditStory = findViewById(R.id.wordEditStory);
    }

    public void saveBtn(View view) {
        AppDatabase db = App.getInstance().getDatabase();
        ItemDao itemDao = db.itemDao();

        itemDao.insert(new Item(nameEditStory.getText().toString(),
                                dateEditStory.getText().toString(),
                                wordEditStory.getText().toString()));

        nameEditStory.setText("");
        dateEditStory.setText("");
        wordEditStory.setText("");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
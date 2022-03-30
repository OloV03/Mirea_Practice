package ru.mirea.jukov.mireaproject.ui.stories;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.jukov.mireaproject.R;

public class StoriesFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private Button newStory;
    private List<Item> items = new ArrayList<Item>();
    private ItemDao itemDao;
    private AppDatabase db;

    private String mParam1;
    private String mParam2;

    public StoriesFragment() {
        // Required empty public constructor
    }

    public static StoriesFragment newInstance(String param1, String param2) {
        StoriesFragment fragment = new StoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);

        newStory = view.findViewById(R.id.newStorybtn);
        newStory.setOnClickListener(this);

        db = App.getInstance().getDatabase();
        itemDao = db.itemDao();
        setInitialData();

        recyclerView = view.findViewById(R.id.recyclerView);
        ItemAdapter adapter = new ItemAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    public void setInitialData(){ items = itemDao.getAll(); }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), StoryView.class);
        startActivity(intent);
    }
}
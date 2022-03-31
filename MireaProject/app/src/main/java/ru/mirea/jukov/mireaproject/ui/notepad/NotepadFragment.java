package ru.mirea.jukov.mireaproject.ui.notepad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.jukov.mireaproject.R;
import ru.mirea.jukov.mireaproject.ui.stories.App;

public class NotepadFragment extends Fragment implements View.OnClickListener, Observer<String> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText titleEdit;
    private TextView title;

    private RecyclerView recyclerView;
    private static List<Note> notes = new ArrayList<Note>();
    public static NoteDao noteDao;
    public static NoteDatabase db;
    private LivaDataConnetced data;

    public NotepadFragment() {
        // Required empty public constructor
    }

    public static NotepadFragment newInstance(String param1, String param2) {
        NotepadFragment fragment = new NotepadFragment();
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
        View view = inflater.inflate(R.layout.fragment_notepad, container, false);

        data = new LivaDataConnetced();
        data.observe(getActivity(),this);

        titleEdit = view.findViewById(R.id.newTitleEdit);
        title = view.findViewById(R.id.textTitle);
        Button btn = view.findViewById(R.id.btnSaveTitle);
        btn.setOnClickListener(this);

        db = App.getInstance().getDatabaseNote();
        noteDao = db.noteDao();
        setInitialData();

        recyclerView = view.findViewById(R.id.noteRecycle);
        NoteAdapter adapter = new NoteAdapter(getContext(), notes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    public static void setInitialData(){ notes = noteDao.getAll(); }

    @Override
    public void onClick(View view) {
        data.setValue(titleEdit.getText().toString());
        Log.d("Title", data.getValue());
    }

    @Override
    public void onChanged(String s) {
        title.setText(data.getValue());
    }
}
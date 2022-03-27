package ru.mirea.jukov.mireaproject.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.jukov.mireaproject.R;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText nameEdit;
    private EditText ageEdit;
    private EditText salaryEdit;
    private TextView nameView;
    private TextView ageView;
    private TextView salaryView;
    private SharedPreferences preferences;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        nameEdit = view.findViewById(R.id.nameEdit);
        ageEdit = view.findViewById(R.id.ageEdit);
        salaryEdit = view.findViewById(R.id.salaryEdit);

        nameView = view.findViewById(R.id.textViewName);
        ageView = view.findViewById(R.id.textViewAge);
        salaryView = view.findViewById(R.id.textViewSalary);

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        setPersonDate();

        // Inflate the layout for this fragment
        return view;
    }

    private void btnUpdateClick(View view){
        Log.d("TAG", "button clicked");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", nameEdit.getText().toString());
        editor.putString("age", ageEdit.getText().toString());
        editor.putString("salary", salaryEdit.getText().toString());
        editor.apply();

        nameEdit.setText("");
        ageEdit.setText("");
        salaryEdit.setText("");

        setPersonDate();
    }

    private void setPersonDate(){
        if (preferences.getString("name", "root").equals("root")) nameView.setText("root");
        else nameView.setText(preferences.getString("name", "root"));

        if (preferences.getString("age", "0").equals("0")) ageView.setText("0");
        else ageView.setText(preferences.getString("age", "0"));

        if (preferences.getString("salary", "0").equals("0")) salaryView.setText("0");
        else salaryView.setText(preferences.getString("salary", "0"));
    }

    @Override
    public void onClick(View view) {
        btnUpdateClick(view);
    }
}
package ru.mirea.jukov.mireaproject.ui.webRes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.mirea.jukov.mireaproject.R;

public class WebResFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MIREA";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final OkHttpClient httpClient = new OkHttpClient();

    private TextView timeView;
    private TextView weatherView;
    private String host = "time-b.nist.gov";
    private int port = 13;


    private String mParam1;
    private String mParam2;

    public WebResFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_web_res, container, false);
        timeView = view.findViewById(R.id.timeView);
        weatherView = view.findViewById(R.id.weatherView);

        view.findViewById(R.id.btnUpdateInfo).setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    public static WebResFragment newInstance(String param1, String param2) {
        WebResFragment fragment = new WebResFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateInfo(View view){
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String time = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                time = reader.readLine();
                Log.d(TAG, time);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            timeView.setText(time);
        }
    }

    @Override
    public void onClick(View view) {
        updateInfo(view);
    }
}
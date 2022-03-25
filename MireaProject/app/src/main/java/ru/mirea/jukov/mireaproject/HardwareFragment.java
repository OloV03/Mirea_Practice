package ru.mirea.jukov.mireaproject;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HardwareFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE_PERMISSION = 100;
    final String TAG = MainActivity.class.getSimpleName();
    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    // sensors
    private ListView listCountSensor;
    private SensorManager sensorManager;
    private Button takePhotoBut;

    // camera
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 0;
    private boolean isWork = false;
    private Uri imageUri;
    private Button startRecBut;
    private Button stopRecBut;
    private Button startPlayBut;
    private Button stopPlayBut;

    private MediaRecorder mediaRecorder;
    private File audioFile;

    private String mParam1;
    private String mParam2;

    public HardwareFragment() {
        // Required empty public constructor
    }

    public static HardwareFragment newInstance(String param1, String param2) {
        HardwareFragment fragment = new HardwareFragment();
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
        View view = inflater.inflate(R.layout.fragment_hardware, container, false);

        takePhotoBut = view.findViewById(R.id.takePhotoButton);
        startRecBut = view.findViewById(R.id.startRecBut);
        stopRecBut = view.findViewById(R.id.stopRecBut);
        startPlayBut = view.findViewById(R.id.startPlayBut);
        stopPlayBut = view.findViewById(R.id.stopPlayBut);

        // проверка наличия разрешений на выполнение аудиозаписи и сохранения на карту памяти
        isWork = hasPermissions(view.getContext(), PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }
        mediaRecorder = new MediaRecorder();

        // инициализация кнопок
        takePhotoBut.setOnClickListener(this);
        startRecBut.setOnClickListener(this);
        stopRecBut.setOnClickListener(this);
        startPlayBut.setOnClickListener(this);
        stopPlayBut.setOnClickListener(this);


        imageView = view.findViewById(R.id.userPhoto);
        //проверка на наличие разрешений на использование камеры и запись в память
        int cameraPermissionStatus = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA);
        String state = Environment.getExternalStorageState();
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }


        // list sensors
        listCountSensor = (ListView) view.findViewById(R.id.listView);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        HashMap<String, Object> sensorTypeList;
        for (int i = 0; i < 3; i++) {
            sensorTypeList = new HashMap<>();
            sensorTypeList.put("Name", sensors.get(i).getName());
            sensorTypeList.put("Value", sensors.get(i).getMaximumRange());
            arrayList.add(sensorTypeList);
        }

        SimpleAdapter mHistory =
                new SimpleAdapter(view.getContext(), arrayList, android.R.layout.simple_list_item_2,
                        new String[]{"Name", "Value"},
                        new int[]{android.R.id.text1, android.R.id.text2});
        listCountSensor.setAdapter(mHistory);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView.setImageURI(imageUri);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void takePhotoClick(View view) {
        Log.d("tag", "button clicked");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // проверка на наличие разрешений для камеры
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null && isWork == true)
        {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String authorities = getActivity().getApplicationContext().getPackageName() + ".fileprovider";
            imageUri = FileProvider.getUriForFile(view.getContext(), authorities, photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    public void startRecClick(View view){
        try {
            startRecBut.setEnabled(false);
            stopRecBut.setEnabled(true);
            stopRecBut.requestFocus();
            startRecording();
        } catch (Exception e) {
            Log.e(TAG, "Caught io exception " + e.getMessage());
        }
    }
    public void stopRecClick(View view){
        startRecBut.setEnabled(true);
        stopRecBut.setEnabled(false);
        startRecBut.requestFocus();
        stopRecording();
        processAudioFile();
    }

    public void startPlayClick(View view){
        getActivity().startService(
                new Intent(getContext(), HardwareService.class));
        Log.d("PlayerState", "Playing start");
    }
    public void stopPlayClick(View view){
        getActivity().stopService(
                new Intent(getContext(), HardwareService.class));
        Log.d("PlayerState", "Playing stop");
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                isWork = true;
            } else {
                isWork = false;
            }
        }
    }

    private void startRecording() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                audioFile = new File(getActivity().getExternalFilesDir(
                        Environment.DIRECTORY_MUSIC), "rec_audio.mp3");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getContext(), "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(getContext(), "You are not recording right now!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();

        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = getActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.getAbsolutePath());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);

        // оповещение системы о новом файле
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhotoButton:
                takePhotoClick(view);
                break;
            case R.id.startRecBut:
                startRecClick(view);
                break;
            case R.id.stopRecBut:
                stopRecClick(view);
                break;
            case R.id.startPlayBut:
                startPlayClick(view);
                break;
            case R.id.stopPlayBut:
                stopPlayClick(view);
                break;
        }
    }
}
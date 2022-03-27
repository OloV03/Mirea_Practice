package ru.mirea.jukov.mireaproject.ui.hardware;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class HardwareService extends Service {
    private MediaPlayer mediaPlayer;

    public HardwareService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            File sdPath = Environment.getExternalStorageDirectory();
            sdPath = new File(sdPath.getAbsolutePath() + "/Android/data/ru.mirea.jukov.mireaproject/files/Music");
            File audioFile = new File(sdPath, "rec_audio.mp3");
            mediaPlayer= MediaPlayer.create(this, Uri.fromFile(audioFile));
            mediaPlayer.setLooping(true);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}
package ru.mirea.jukov.livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;

public class TimeController {
    private static MutableLiveData<Long> data = new MutableLiveData<Long>();

    static LiveData<Long> getTime(){
        data.setValue(new Date().getTime());
        return data;
    }

    static void setTime(){
        data.setValue(new Date().getTime());
    }
}

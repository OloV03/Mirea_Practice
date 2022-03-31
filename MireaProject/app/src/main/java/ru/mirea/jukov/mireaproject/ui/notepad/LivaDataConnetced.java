package ru.mirea.jukov.mireaproject.ui.notepad;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class LivaDataConnetced extends LiveData<String> {

    @Override
    protected void setValue(String value) {
        super.setValue(value);
    }

    @Nullable
    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d("STATUS", "onActive");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d("STATUS", "onInactive");
    }


}

package dev.z3t4.doki.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

@SuppressLint("Registered")
public class GenericContext extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}

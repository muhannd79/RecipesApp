package com.android.foosh.mvvmpro1;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors get(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return mNetworkIO;
    }
}

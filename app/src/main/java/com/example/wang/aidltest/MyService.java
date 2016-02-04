package com.example.wang.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wang on 2015/9/9.
 */
public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.i(MyService.class.getSimpleName(), "onBind process id is " + android.os.Process.myPid());
        return mBinder;
    }

    MyAIDLService.Stub mBinder = new MyAIDLService.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            Log.i(MyAIDLService.Stub.class.getSimpleName(), "process id is " + android.os.Process.myPid());
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            if(str != null) {
                return str.toUpperCase();
            } else {
                return null;
            }
        }

    };

    @Override
    public void onCreate() {
        Log.d("TAG", "Service onCreate");
        Log.i(MyService.class.getSimpleName(), "process id is " + android.os.Process.myPid());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
//        return Service.START_NOT_STICKY;

    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("MyService", " onRebind called");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyService", " onUnbind called");
        Log.d("MyService", super.onUnbind(intent) + "");
//        return super.onUnbind(intent);
        return true;
    }

    @Override
    public void onDestroy() {
        Log.d("TAG", "Service onDestroy");
        super.onDestroy();
    }

}

package com.example.wang.aidltest;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.start_service_btn)
    Button startServiceBtn;
    @Bind(R.id.stop_service_btn)
    Button stopServiceBtn;
    @Bind(R.id.bind_service_btn)
    Button bindServiceBtn;
    @Bind(R.id.unbind_service_btn)
    Button unbindServiceBtn;
    @Bind(R.id.check_service_running)
    Button checkServiceRunBtn;

    private MyAIDLService myAIDLService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAIDLService = MyAIDLService.Stub.asInterface(service);
            try {
                Log.i(MainActivity.class.getSimpleName() + "onServiceConnected", "process id is " + android.os.Process.myPid());
                int result = myAIDLService.plus(3, 5);
                String upstr = myAIDLService.toUpperCase("hello world");
                Log.d("TAG", "result is " + result);
                Log.d("TAG", "upString is " + upstr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("TAG", "MyService disconnect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i(MainActivity.class.getSimpleName(), "process id is " + android.os.Process.myPid());
    }

    @OnClick (R.id.start_service_btn)
    void startServiceFun() {
        Intent sIntent = new Intent(this, MyService.class);
        startService(sIntent);
    }

    @OnClick (R.id.stop_service_btn)
    void stopServiceFun() {
        Intent sIntent = new Intent(this, MyService.class);
        stopService(sIntent);
    }

    @OnClick (R.id.bind_service_btn)
    void bindServiceFun() {
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    @OnClick (R.id.unbind_service_btn)
    void unBindServiceFun() {
        unbindService(connection);
    }

    @OnClick(R.id.check_service_running)
    void checkServiceRunning() {
        boolean isRunning = isMyServiceRunning();
        Log.d("MyService is Running ?", isRunning + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

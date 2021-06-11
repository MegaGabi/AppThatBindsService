package com.example.serviceone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private IMyInterface mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("MyTAG", "trying to connect to " + name.getClassName());
            if(service!= null)
            {
                Log.i("MyTAG", "connected to " + name.getClassName());
                mService = IMyInterface.Stub.asInterface(service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MyTAG", "Created");

        final EditText edtX = (EditText) findViewById(R.id.editX);
        final EditText edtY = (EditText) findViewById(R.id.editY);
        final EditText res = (EditText) findViewById(R.id.teCalcResult);
        final Button btnAdd = (Button) findViewById(R.id.bCalc);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sx = edtX.getText().toString();
                String sy = edtY.getText().toString();

                try
                {
                    int x = Integer.parseInt(sx);
                    int y = Integer.parseInt(sy);

                    Log.i("MyTAG", edtX.getText().toString() + " + " + edtY.getText().toString());

                    int r = mService.add(x, y);

                    res.setText("" + r);
                }
                catch(NullPointerException| RemoteException|NumberFormatException e)
                {
                    Log.i("MyTAG", e.toString());
                }
            }
        });

        Intent intent = new Intent();
        intent.setClassName("com.example.serviceone", "com.example.serviceone.ServiceOne");
        if(!bindService(intent, mConnection, BIND_AUTO_CREATE))
        {
            Log.i("MyTAG", "fail");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MyTAG", "Started");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MyTAG", "Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MyTAG", "Resumed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MyTAG", "Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MyTAG", "Destroyed");
    }
}
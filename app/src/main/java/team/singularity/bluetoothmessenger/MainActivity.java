package team.singularity.bluetoothmessenger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import team.singularity.bluetoothmessenger.Bluetooth.BluetoothHandler;

public class MainActivity extends AppCompatActivity {

    TextView bluetoothStatusTv, pairedTv;
    ImageView bluetoothStatusIv;
    Button onBtn, offBtn, discoverBtn, pairedBtn, connectBtn;
    final String TAG = "MainActivity";
    Context context;
    BluetoothHandler bt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        bt = new BluetoothHandler(context);

        bluetoothStatusTv = findViewById(R.id.bluetoothStatusTv);
        pairedTv          = findViewById(R.id.pairedTv);
        bluetoothStatusIv = findViewById(R.id.bluetoothStatusIv);
        onBtn             = findViewById(R.id.onBtn);
        offBtn            = findViewById(R.id.offBtn);
        discoverBtn       = findViewById(R.id.discoverableBtn);
        pairedBtn         = findViewById(R.id.pairedBtn);
        connectBtn           = findViewById(R.id.connectBtn);

        if (bt.isAvailable()) {
            bluetoothStatusTv.setText("Bluetooth is available");
        } else {
            bluetoothStatusTv.setText("Bluetooth is not available");
        }

        if (bt.isEnabled()) {
            bluetoothStatusIv.setImageResource(R.drawable.ic_action_on);
        } else {
            bluetoothStatusIv.setImageResource(R.drawable.ic_action_off);
        }

        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "On button pressed");
                if (!bt.isEnabled()) {
                    bt.turnOn();
                }
                if (bt.isEnabled()) {
                    bluetoothStatusIv.setImageResource(R.drawable.ic_action_on);
                }
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Off button pressed");
                bt.turnOff();
                if (!bt.isEnabled()) {
                    bluetoothStatusIv.setImageResource(R.drawable.ic_action_off);
                }
            }
        });

        discoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Discoverable button pressed");
                bt.makeDiscoverable();
            }
        });

        pairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Show paired devices button pressed");
                String s = "Devices:\n";
                for (int i = 0; i < bt.getPaired().length; i++) {
                    s += bt.getPairedNames()[i] + "\n" + bt.getPairedAddresses()[i] + "\n";
                }
                pairedTv.setText(s);
            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Connect to a device button pressed");
                //setContentView(R.layout.activity_connect_device);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case BluetoothHandler.REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    // bluetooth is on, TODO: should find a way to put this in BluetoothHandler
                } else {
                    Log.e(TAG, "User denied access to bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

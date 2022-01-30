package team.singularity.bluetoothmessenger.Bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;
import java.util.Set;

import team.singularity.bluetoothmessenger.MainActivity;

/** Made by Edison Bregger 1-29-2022 for FRC team 5066 Singularity **/

// TODO: don't put context in a variable

public class BluetoothHandler {

    final String TAG = "BluetoothHandler";

    Activity activity;
    Context context;
    BluetoothAdapter btAdapter;
    //String macAddress; //see if there's some way of getting this

    public static final int REQUEST_ENABLE_BT = 0;
    public static final int REQUEST_DISCOVER_BT = 1;

    public BluetoothHandler(Context c) {
        context = c;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        activity = (Activity)context;
    }

    public boolean isAvailable() {
        Log.e(TAG, "DEVICE DOES NOT SUPPORT BLUETOOTH");
        return btAdapter != null; //if device supports bluetooth
    }

    public boolean isEnabled() {
        return btAdapter.isEnabled();
    }

    public void turnOn() {
        if (this.isEnabled()) {
            Log.i(TAG, "Bluetooth is already enabled!");
            return;
        }
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH_CONNECT}, 1);
            Log.e(TAG, "MISSING PERMISSION: BLUETOOTH_CONNECT");
            return;
        }
        activity.startActivityForResult(intent, REQUEST_ENABLE_BT);
        if (this.isEnabled()) {
            Log.i(TAG, "Bluetooth enabled successfully");
        } else {
            Log.e(TAG, "Failed to enable bluetooth");
        }
    }

    public void turnOff() {
        if (!this.isEnabled()) {
            Log.i(TAG, "Bluetooth is already disabled!");
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH_CONNECT}, 1);
            Log.e(TAG, "MISSING PERMISSION: BLUETOOTH_CONNECT");
            return;
        }
        btAdapter.disable();
        if (this.isEnabled()) {
            Log.e(TAG, "FAILED TO DISABLE BLUETOOTH");
        } else {
            Log.i(TAG, "Disabled bluetooth");
        }
    }

    public boolean isDiscovering() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH_SCAN}, 1);
            Log.e(TAG, "MISSING PERMISSION: BLUETOOTH_SCAN");
            return false;
        }
        return btAdapter.isDiscovering();
    }

    public void makeDiscoverable() {
        if (this.isDiscovering()) {
            return;
        }
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH_ADVERTISE}, 1);
            Log.e(TAG, "MISSING PERMISSION: BLUETOOTH_ADVERTISE");
            return;
        }
        activity.startActivityForResult(intent, REQUEST_DISCOVER_BT);
    }

    public BluetoothDevice[] getPaired() {
        if (!this.isEnabled()) {
            Log.e(TAG, "Bluetooth is not enabled");
            return new BluetoothDevice[0];
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH_CONNECT}, 1);
            Log.e(TAG, "MISSING PERMISSION: BLUETOOTH_CONNECT");
            return new BluetoothDevice[0];
        }
        return btAdapter.getBondedDevices().toArray(new BluetoothDevice[0]);
    }

    public String[] getPairedAddresses() {
        String[] s = new String[this.getPaired().length];
        for (int i = 0; i < s.length; i++) {
            s[i] = this.getPaired()[i].getAddress();
        }
        return s;
    }

    public String[] getPairedNames() {
        String[] s = new String[this.getPaired().length];
        for (int i = 0; i < s.length; i++) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH_CONNECT}, 1);
                Log.e(TAG, "MISSING PERMISSION: BLUETOOTH_CONNECT");
                return new String[] {""};
            }
            s[i] = this.getPaired()[i].getName();
        }
        return s;
    }

    public void connectTo(String address) {

    }

    public void disconnect() {

    }
}

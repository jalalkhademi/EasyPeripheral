package ble.easy.example;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import easy.ble.sdk.EasyBle;
import easy.ble.sdk.PeripheralDiscovery;

public class MainActivity extends Activity {

    private final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 66;
    private static final long SCAN_PERIOD = 6000;

    BleDeviceListViewAdapter bleDeviceListViewAdapter;
    PeripheralDiscovery peripheralDiscovery;
    Button btnScan;
    Handler mHandler;

    ListView bleDevicesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btn_scan);


        try {
            // Ensures Bluetooth is available on the device and it is enabled. If not,
            // displays a dialog requesting user permission to enable Bluetooth.
            if (EasyBle.getInstance(this).isBluetoothExistAndEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            peripheralDiscovery = EasyBle.getInstance(this).getPeripheralDiscovery();
            bleDevicesListView = findViewById(R.id.lv_ble_devices);
            bleDeviceListViewAdapter = new BleDeviceListViewAdapter(getApplicationContext());
            bleDevicesListView.setAdapter(bleDeviceListViewAdapter);
            peripheralDiscovery.registerForPeripherals(bleDeviceListViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mHandler = new Handler();

        btnScan.setOnClickListener(v -> {
            try {
                doScan();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void doScan() throws Exception {
        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        } else {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(() -> {
                try {
                    peripheralDiscovery.stopDiscovery();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, SCAN_PERIOD);
            peripheralDiscovery.startDiscovery(null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        doScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                }
            }
        }
    }
}

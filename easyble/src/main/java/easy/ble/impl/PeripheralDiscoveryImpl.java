package easy.ble.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import easy.ble.sdk.Peripheral;
import easy.ble.sdk.PeripheralDiscovery;
import easy.ble.sdk.PeripheralObserver;

public class PeripheralDiscoveryImpl implements PeripheralDiscovery {

    private static final String TAG = "BLE Scanner";

    private boolean mScanning;

    private ArrayList<Peripheral> peripherals;

    private ArrayList<PeripheralObserver> peripheralObservers;

    private BluetoothAdapter bluetoothAdapter;

    public PeripheralDiscoveryImpl(BluetoothAdapter bluetoothAdapter){

        this.bluetoothAdapter = bluetoothAdapter;

        peripherals = new ArrayList<>();

        peripheralObservers = new ArrayList<>();
    }

    public void startDiscovery(UUID[] serviceUuids) throws Exception {
        if(isDiscovering()){
            throw new Exception("Scanning is already in progress.");
        }
        mScanning = true;
        peripherals.clear();
        if (serviceUuids != null) {
            bluetoothAdapter.startLeScan(serviceUuids, mLeScanCallback);
        } else {
            bluetoothAdapter.startLeScan(mLeScanCallback);
        }
    }

    public ArrayList<Peripheral> getSortedPeripherals(){
        return peripherals;
    }

    public void unregisterForPeripherals(PeripheralObserver peripheralObserver) {
        peripheralObservers.remove(peripheralObserver);
    }

    public void registerForPeripherals(PeripheralObserver peripheralObserver) {
        peripheralObservers.add(peripheralObserver);
    }

    public void notifyNewPeripheral() {
        for(PeripheralObserver peripheralObserver: peripheralObservers){
            peripheralObserver.notifyNewPeripheral(peripherals);
        }
    }

    public void stopDiscovery() throws Exception {
        if(!isDiscovering()){
            throw new Exception("Scanning is not started yet.");
        }
        mScanning = false;
        bluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    public boolean isDiscovering(){
        return mScanning;
    }

    private void sortPeripheralsBasedOnRssi(){

        Collections.sort(peripherals,
                (o1, o2) -> o2.getRssi() - o1.getRssi());
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Peripheral scannedDevice = new PeripheralImple(device,rssi,scanRecord);
            Log.d(TAG, device.getAddress());
            if(!peripherals.contains(scannedDevice)){
                peripherals.add(scannedDevice);
                sortPeripheralsBasedOnRssi();
                notifyNewPeripheral();
            }
        }
    };
}

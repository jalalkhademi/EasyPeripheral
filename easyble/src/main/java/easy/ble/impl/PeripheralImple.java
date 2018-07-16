package easy.ble.impl;

import android.bluetooth.BluetoothDevice;
import easy.ble.sdk.Peripheral;

public class PeripheralImple implements Peripheral {

    private BluetoothDevice bleDevice;
    private int rssi;
    private byte[] scanRecord;

    public PeripheralImple(BluetoothDevice bleDevice, int rssi, byte[] scanRecord) {
        this.bleDevice = bleDevice;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
    }

    public BluetoothDevice getBleDevice() {
        return bleDevice;
    }

    public int getRssi() {
        return rssi;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!PeripheralImple.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final PeripheralImple other = (PeripheralImple) obj;
        if ((this.bleDevice == null) ? (other.bleDevice != null) : !this.bleDevice.equals(other.bleDevice)) {
            return false;
        }
        return true;
    }
}

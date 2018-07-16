package easy.ble.sdk;

import android.bluetooth.BluetoothDevice;

/**
 * The implementation of this class is a model of bluetooth low energy peripheral
 * which scan record and rssi values if discovery process are added.
 */
public interface Peripheral {

    /**
     *
     * @return the classic blue tooth device object of discovered bluetooth low energy peripheral
     */
    BluetoothDevice getBleDevice();

    /**
     *
     * @return the rssi of discovered bluetooth low energy peripheral
     */
    int getRssi();

    /**
     *
     * @return the scan record of discovered bluetooth low energy peripheral
     */
    byte[] getScanRecord();
}

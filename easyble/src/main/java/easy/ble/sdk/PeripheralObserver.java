package easy.ble.sdk;

import java.util.ArrayList;

/**
 * by implementing this interface you can be notified upon any changes of
 * peripheral discovery process. The architecture of this library is based on
 * observer design pattern and based on that you can consider this interface
 * the observer.
 */
public interface PeripheralObserver {

    /**
     * it will be trigerred on any changes of discovered peripherals.
     *
     * @param newPeripheral contains the sorted discovered peripherals based on their rssi value at the time of discovery.
     */
    void notifyNewPeripheral(ArrayList<Peripheral> newPeripheral);
}

package easy.ble.sdk;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The implementation of this interface will handle the discovery
 * process of surrounding bluetooth low energy peripherals.
 */
public interface PeripheralDiscovery {

    /**
     * The process of discovering for surrounding peripherals will start.
     *
     * @param serviceUuids Only peripherals with specified services will be discovered.
     *                     in case of null value it will discover all peripherals.
     * @throws Exception throws if the discovery process is already started.
     */
    void startDiscovery(UUID[] serviceUuids) throws Exception;

    /**
     * The process of discovering for surrounding peripherals will stop.
     *
     * @throws Exception thorws if the discovery process is not started before.
     */
    void stopDiscovery() throws Exception;

    /**
     * on each new discovery(the time between start and stop discovery) the peripherals will be reset.
     *
     * @retun The discovered peripherals which are sorted based on their rssi will return.
     */
    ArrayList<Peripheral> getSortedPeripherals();

    /**
     * Based on observer design pattern by registering a peripheral observer for discovery process you can be notified in case of discovery result.
     *
     * @param peripheralObserver the peripheral observer which will be notified of all discovered peripheral changes.
     */
    void registerForPeripherals(PeripheralObserver peripheralObserver);

    /**
     * Based on observer design pattern by unregistering a peripheral observer for discovery process your notifications for discovery update will be stopped.
     *
     * @param peripheralObserver the peripheral observer which will be notified of all discovered peripheral changes.
     */
    void unregisterForPeripherals(PeripheralObserver peripheralObserver);

    /**
     * Checks if a discovery attempt is in progress or not.
     *
     *  @return returns true if the discovery process is in progress, otherwise it will return false.
     */
    boolean isDiscovering();
}

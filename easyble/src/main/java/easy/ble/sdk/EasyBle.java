package easy.ble.sdk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import easy.ble.impl.PeripheralDiscoveryImpl;


/**
 * This class provides an easy approach of discovering bluetooth low energy peripherals in the
 * vicinity of mobile bluetooth sensor. this library uses observer design pattern to easily fulfill
 * any request.
 */
public class EasyBle {

    private static EasyBle instance;

    private volatile PeripheralDiscoveryImpl lazyPeripheralDiscovery;

    protected BluetoothAdapter mBluetoothAdapter;

    private Context context;

    /**
     * it provides a singleton instance of EasyBle class
     *
     * @param context context of application is required to initialize bluetooth manager.
     *
     * @return returns the singleton instance of EasyBle class.
     */
    public static EasyBle getInstance(Context context){
        if(instance == null){
            instance = new EasyBle(context);
        }
        return instance;
    }

    /**
     * private constructor makes the singleton architecture possible.
     *
     * @param context this context is used to initialize bluetooth manager.
     */
    private EasyBle(Context context){

        this.context = context;

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    /**
     *checks if the mobile cellphone has bluetooth sensor and if yes is the bluetooth sensor activated.
     *
     * @return returns true if the bluetooth sensor exist and activated. returns false otherwise.
     */
    public boolean isBluetoothExistAndEnabled(){
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    /**
     * provides an initialized peripheral scanner.
     *
     * @return provides an initialized peripheral scanner.
     */
    public PeripheralDiscovery getPeripheralDiscovery() {
        // double-checked idiom (Joshua Bloch variant)
        PeripheralDiscoveryImpl tmp = lazyPeripheralDiscovery;

        if (tmp == null) {
            synchronized (this) {
                tmp = lazyPeripheralDiscovery;
                if (tmp == null) {
                    lazyPeripheralDiscovery = tmp = new PeripheralDiscoveryImpl(mBluetoothAdapter);
                }
            }
        }

        return tmp;
    }
}

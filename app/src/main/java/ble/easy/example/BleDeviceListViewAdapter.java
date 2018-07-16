package ble.easy.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ble.easy.example.R;
import easy.ble.sdk.Peripheral;
import easy.ble.sdk.PeripheralObserver;

public class BleDeviceListViewAdapter extends BaseAdapter implements PeripheralObserver {

    Context context;
    private ArrayList<Peripheral> peripherals;

    public BleDeviceListViewAdapter(Context context){
        this.context = context;
        this.peripherals = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return peripherals.size();
    }

    @Override
    public Object getItem(int position) {
        return peripherals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ble_device_layout, parent, false);
        }

        if(peripherals.get(position).getBleDevice().getAddress() != null){
            ((TextView) convertView.findViewById(R.id.txt_name))
                    .setText(peripherals.get(position).getBleDevice().getAddress());
        }
        ((TextView) convertView.findViewById(R.id.txt_rssi))
                .setText(String.valueOf(peripherals.get(position).getRssi()));
        return convertView;
    }

    private void refreshListView(ArrayList<Peripheral> peripherals){
        this.peripherals = peripherals;
        notifyDataSetChanged();
    }

    @Override
    public void notifyNewPeripheral(ArrayList<Peripheral> newPeripherals) {
        peripherals = newPeripherals;
        refreshListView(peripherals);
    }
}

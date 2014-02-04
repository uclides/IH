package info.androidhive.tabsswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DeviceFragment extends Fragment {
    String[] IDevice= {"Model","Manufacturer","Chipset","BaseBand Version","RIL Version","Build Number","Build Fingerprint","OS Version","SDK","Bootloader"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_battery, container, false);
        ListView listView=(ListView)rootView.findViewById(R.id.listView);
        SetParamList(IDevice);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,SetParamList(IDevice));
        listView.setAdapter(adapter);
		return rootView;
	}

    public ArrayList<String> SetParamList(String[] array){
        int x=0;
        ArrayList<String> values= new ArrayList<String>();

        while (x<array.length){
            values.add(array[x]);
            x++;
        }
        return values;
    }
}

package org.androidanalyzer.gui;

import java.util.ArrayList;

import org.androidanalyzer.R;
import org.androidanalyzer.core.PluginStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * This class represents custom list adapter, used to display list of the available plugins, 
 * along with short description and means to enable/disable plugin for/from execution. 
 *
 */
class AnalyzerConfigAdapter extends BaseAdapter implements ListAdapter {
	
	ArrayList<PluginStatus> listItems;
	Context ctx;
	
	public AnalyzerConfigAdapter(Context ctx, ArrayList<PluginStatus> items) {
		this.ctx = ctx;
		listItems = items;
	}

	public int getCount() {
		return listItems != null ? listItems.size() : 0;
	}

	public Object getItem(int position) {
		return position < getCount() ? listItems.get(position) : null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (position < getCount()) {
			final PluginStatus pStatus = listItems.get(position);
			RelativeLayout rowLayout;
			if (convertView == null) {
				rowLayout = (RelativeLayout)LayoutInflater.from(ctx).inflate(R.layout.plugin_config, parent, false);
			} else {
				rowLayout = (RelativeLayout)convertView;
			}
      String pluginName = pStatus.getPluginName();
      boolean selected = pStatus.isEnabled();
			CheckBox checkBox = (CheckBox)rowLayout.findViewById(R.id.plugin_status_checkbox);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        
        /*
         * (non-Javadoc)
         * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
         */
				@Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          pStatus.setEnabled(isChecked);
        }
      });
			checkBox.setChecked(selected);
			TextView name = (TextView)rowLayout.findViewById(R.id.firstLine2);
			
			name.setText(pluginName);
			TextView number = (TextView)rowLayout.findViewById(R.id.secondLine2);
			String pDescription = pStatus.getPluginDescription();
			number.setText(pDescription);
			return rowLayout;
		}
		return null;
	}
	
}
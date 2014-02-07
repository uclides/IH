package com.example.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by inv.Desarrollo2 on 03/02/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int item) {
        switch(item){
            case 0:
                return new DeviceFragment();
            case 1:
                return new SystemFragment();
            case 2:
                return new MemoryFragment();
            case 3:
                return new CameraFragment();
            case 4:
                return new BatteryFragment();
            case 5:
                return new SensorsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }
}

package com.milkteaking.ec.main.detail.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ec.main.detail.image.ImageFragment;

import java.util.ArrayList;

/**
 * @author TanJJ
 * @time 2018/6/17 20:07
 * @des Tab的适配器
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<ArrayList<String>> mPictures = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        init(data);
    }

    private void init(JSONObject data) {
        JSONArray tabs = data.getJSONArray("tabs");
        int size = tabs.size();
        for (int i = 0; i < size; i++) {
            ArrayList<String> innerPictures = new ArrayList<>();
            JSONObject jsonObject = tabs.getJSONObject(i);
            String name = jsonObject.getString("name");
            JSONArray pictures = jsonObject.getJSONArray("pictures");
            int pictureSize = pictures.size();
            for (int m = 0; m < pictureSize; m++) {
                String picture = pictures.getString(m);
                innerPictures.add(picture);
            }
            mNames.add(name);
            mPictures.add(innerPictures);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.create(mPictures.get(position));
    }

    @Override
    public int getCount() {
        return mNames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNames.get(position);
    }
}

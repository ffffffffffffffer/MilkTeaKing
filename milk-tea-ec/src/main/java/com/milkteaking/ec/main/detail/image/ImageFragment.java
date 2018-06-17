package com.milkteaking.ec.main.detail.image;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ui.recycler.ItemType;
import com.milkteaking.ui.recycler.MultipleField;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author TanJJ
 * @time 2018/6/17 20:16
 * @des ViewPager的图片Fragment
 */

public class ImageFragment extends MilkTeaFragment {

    private static final String IMAGE_FRAGMENT_TAB = "image_fragment_tab";
    @BindView(R2.id.rv_image_container)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.fragment_image;
    }

    public static ImageFragment create(ArrayList<String> pictures) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IMAGE_FRAGMENT_TAB, pictures);
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initAdapter();
    }

    private void initAdapter() {
        ArrayList<MultipleItemBean> beans = new ArrayList<>();
        Bundle arguments = getArguments();
        if (arguments != null) {
            ArrayList<String> pictures = arguments.getStringArrayList(IMAGE_FRAGMENT_TAB);
            int size = pictures.size();
            for (int i = 0; i < size; i++) {
                String picture = pictures.get(i);
                MultipleItemBean build = MultipleItemBean.builder()
                        .setItemType(ItemType.SINGLE_BIG_IMAGE)
                        .setField(MultipleField.IMAGE_URL.name(), picture)
                        .build();
                beans.add(build);
            }
        }
        // 加载adapter
        ImageRecyclerAdapter imageRecyclerAdapter = new ImageRecyclerAdapter(beans);
        mRecyclerView.setAdapter(imageRecyclerAdapter);
    }
}

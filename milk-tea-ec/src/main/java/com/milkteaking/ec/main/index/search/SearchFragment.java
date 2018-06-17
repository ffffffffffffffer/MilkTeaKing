package com.milkteaking.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.util.storage.Preference;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ui.recycler.MultipleItemBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TanJJ
 * @time 2018/6/17 13:38
 * @des 搜索Fragment
 */

public class SearchFragment extends MilkTeaFragment {

    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView;

    @OnClick(R2.id.icon_top_search_back)
    public void clickTopSearchBack() {
        getSupportDelegate().pop();
    }

    @OnClick(R2.id.tv_top_search)
    public void clickTopSearch() {
        // 储存用户输入的数据
        String inputText = mSearchView.getText().toString();
        // 取出储存的历史记录
        String historyInfo = Preference.getCustomAppProfile(SearchDataConvert.TAG_SEARCH_HISTORY);
        if (!StringUtils.isEmpty(inputText)) {
            // 储存数据
            saveText(inputText, historyInfo);
        } else {
            ToastUtils.showShort("内容为空,请输入有效字段.");
        }
        // 请求服务器
        requestService(inputText);
    }

    @SuppressWarnings("unchecked")
    private void saveText(String inputText, String historyInfo) {
        List<String> infos;
        // 判断之前是否有历史记录,有就叠加,无就重新创建
        if (!StringUtils.isEmpty(historyInfo)) {
            //有就叠加
            infos = JSON.parseObject(historyInfo, ArrayList.class);
        } else {
            infos = new ArrayList<>();
        }
        // 储存当前的数据
        infos.add(inputText);
        // 转换成json再储存
        String jsonStringInfos = JSON.toJSONString(infos);
        Preference.addCustomAppProfile(SearchDataConvert.TAG_SEARCH_HISTORY, jsonStringInfos);
    }

    private void requestService(String inputText) {
        RestClient.builder()
                .url(Constant.INDEX + "?key=" + inputText)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        // 隐藏loader
//                        MilkTeaLoader.stopLoader();
                        // 将EditView置为空
                        mSearchView.setText("");
                        // 展示一些东西
                        // 弹出一段话
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        ToastUtils.showShort("请求失败,请重试!");
                    }
                })
                .build()
                .get();
    }

    @OnClick(R2.id.et_search_view)
    public void clickEtSearchView() {

    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initLayout();
        initDecoration();
        initData();
    }

    private void initData() {
        LinkedList<MultipleItemBean> convert = new SearchDataConvert().convert();
        SearchAdapter adapter = new SearchAdapter(convert);
        mRecyclerView.setAdapter(adapter);
    }

    private void initDecoration() {
        DividerItemDecoration decoration = new DividerItemDecoration();
        decoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20, 20)
                        .color(Color.GRAY)
                        .build();
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return null;
            }
        });
    }

    private void initLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}

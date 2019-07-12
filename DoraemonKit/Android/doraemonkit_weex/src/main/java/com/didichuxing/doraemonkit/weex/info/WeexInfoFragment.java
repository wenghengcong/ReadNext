package com.didichuxing.doraemonkit.weex.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.didichuxing.doraemonkit.ui.base.BaseFragment;
import com.didichuxing.doraemonkit.ui.widget.titlebar.HomeTitleBar;
import com.didichuxing.doraemonkit.weex.R;

/**
 * @author haojianglong
 * @date 2019-06-18
 */
public class WeexInfoFragment extends BaseFragment {

    private WeexInfoAdapter mAdapter;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_info;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        HomeTitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });
        ListView listView = findViewById(R.id.info_list);
        mAdapter = new WeexInfoAdapter(getContext());
        mAdapter.setWeexInfos(WeexInfoHacker.getWeexInfos());
        listView.setAdapter(mAdapter);
    }

}

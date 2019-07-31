package com.sup.itg.itgt;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class MainAdapter extends BaseQuickAdapter<HomeData, BaseViewHolder> {
    public MainAdapter() {

        super(R.layout.main_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeData item) {
        helper.setText(R.id.tvMatrix, item.name);
        helper.setImageResource(R.id.ivImg,item.resId);
    }
}

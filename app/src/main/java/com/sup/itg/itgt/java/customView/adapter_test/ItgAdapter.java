package com.sup.itg.itgt.java.customView.adapter_test;

import android.widget.TextView;

import com.sup.itg.itgr.ItgRecyclerAdapter;
import com.sup.itg.itgr.holder.ItgHolder;
import com.sup.itg.itgt.R;

public class ItgAdapter extends ItgRecyclerAdapter<String> {

    ItgAdapter() {
        super(R.layout.itgr_item);
    }

    @Override
    public void bind(String data, ItgHolder help) {
        TextView tv = help.getView(R.id.text);
        tv.setText(data);

    }

    @Override
    public int getSlideView() {
        return R.layout.itgr_slide_view;
    }
}

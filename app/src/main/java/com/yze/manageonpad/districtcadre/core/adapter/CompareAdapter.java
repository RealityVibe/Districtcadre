package com.yze.manageonpad.districtcadre.core.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.model.CompareRow;

import java.util.List;

public class CompareAdapter extends BaseQuickAdapter<CompareRow, BaseViewHolder> {

    public CompareAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CompareRow item) {
        holder.setText(R.id.left_data, item.getLeftData())
                .setText(R.id.right_data, item.getRightData())
                .setText(R.id.compare_item, item.getCompareItem());
    }
}

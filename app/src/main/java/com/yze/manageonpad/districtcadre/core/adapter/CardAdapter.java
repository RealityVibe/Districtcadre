package com.yze.manageonpad.districtcadre.core.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.model.Cadre;

import java.util.List;

public class CardAdapter extends BaseQuickAdapter<Cadre, BaseViewHolder> {
    public CardAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Cadre cadre) {
        holder.setText(R.id.card_name, "姓名：" + cadre.getXm())
                .setText(R.id.card_fg, "分管：" + cadre.getFg())
                .setText(R.id.card_xrzw, "现任职务：" + cadre.getXrzw())
                .setText(R.id.card_csny, "出生年月：" + cadre.getCsny())
                .setText(R.id.card_rdsj, "入党时间：" + cadre.getRdsj())
                .setText(R.id.card_rxzsj, "任现职时间：" + cadre.getRxzsj())
                .setText(R.id.card_cjgzsj, "参加工作时间：" + cadre.getCjgzsj());
    }
}
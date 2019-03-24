package com.yze.manageonpad.districtcadre.core.adapter;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daivd.chart.component.ChartTitle;
import com.daivd.chart.component.base.ILegend;
import com.daivd.chart.core.PieChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.ScaleData;
import com.yze.manageonpad.districtcadre.R;

import java.util.ArrayList;
import java.util.List;

public class GraphAdapter  extends BaseQuickAdapter<ChartData<PieData>, GraphAdapter.PieHolder> {

    public GraphAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(PieHolder holder, ChartData<PieData> pieDatas) {
        holder.setChartData(R.id.pie_chart, pieDatas);
    }


    class PieHolder extends BaseViewHolder{
        private PieChart pieChart;

        public PieHolder(View view) {
            super(view);
        }

        public PieHolder setChartData(@IdRes int viewId, ChartData<PieData> pieDatas){
            pieChart = getView(viewId);
            pieChart.setChartData(pieDatas);
            pieChart.getChartData().setScaleData(new ScaleData());
            pieChart.getLegend().setDirection(ILegend.RIGHT);
            pieChart.getChartTitle().setDirection(ChartTitle.TOP);
            // 标题
            pieChart.getChartTitle().getFontStyle().setTextSize(40);
            pieChart.getChartTitle().getFontStyle().setTextColor(Color.WHITE);
            // 图例
            pieChart.getLegend().getFontStyle().setTextSize(30);
            pieChart.getLegend().getFontStyle().setTextColor(Color.WHITE);
            pieChart.startChartAnim(600);
            pieChart.setZoom(true);
            return this;
        }
    }
}

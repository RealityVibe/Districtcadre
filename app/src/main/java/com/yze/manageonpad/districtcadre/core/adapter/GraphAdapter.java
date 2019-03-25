package com.yze.manageonpad.districtcadre.core.adapter;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daivd.chart.component.ChartTitle;
import com.daivd.chart.component.base.ILegend;
import com.daivd.chart.core.BarChart;
import com.daivd.chart.core.PieChart;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.provider.pie.PieProvider;
import com.yze.manageonpad.districtcadre.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 图表RecyclerView适配器
 */
public class GraphAdapter extends BaseQuickAdapter<ChartData<PieData>, GraphAdapter.PieHolder> {

    public GraphAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(PieHolder holder, ChartData<PieData> pieDatas) {
        holder.setChartData(R.id.pie_chart, pieDatas);
    }


    class PieHolder extends BaseViewHolder {
        private PieChart pieChart;

        public PieHolder(View view) {
            super(view);
        }

        public PieHolder setChartData(@IdRes int viewId, ChartData<PieData> pieDatas) {
            pieChart = getView(viewId);
            // 反射修改pie图里的文字size
            Class<PieProvider> clazz = PieProvider.class;
            try {
                Field field = clazz.getDeclaredField("textStyle");
                field.setAccessible(true);
                FontStyle fontStyle = new FontStyle();
                fontStyle.setTextSize(40);
                field.set(pieChart.getProvider(), fontStyle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pieChart.setChartData(pieDatas);
            pieChart.getChartData().setScaleData(new ScaleData());
            pieChart.getChartTitle().setDirection(ChartTitle.TOP);
            // 标题
            pieChart.getChartTitle().getFontStyle().setTextSize(40);
            pieChart.getChartTitle().getFontStyle().setTextColor(Color.WHITE);
            // 图例
            pieChart.getLegend().setDirection(ILegend.RIGHT);
            pieChart.getLegend().getFontStyle().setTextSize(27);
            pieChart.getLegend().getFontStyle().setTextColor(Color.WHITE);
            pieChart.getLegend().setDisplay(true);
            pieChart.startChartAnim(600);
            pieChart.setZoom(true);
            pieChart.setScaleX(0.8f);
            pieChart.setScaleY(0.8f);

            return this;
        }
    }
}

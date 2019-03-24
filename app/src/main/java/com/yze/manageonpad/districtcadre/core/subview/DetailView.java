package com.yze.manageonpad.districtcadre.core.subview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daivd.chart.component.ChartTitle;
import com.daivd.chart.component.base.ILegend;
import com.daivd.chart.core.PieChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.ScaleData;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.CardAdapter;
import com.yze.manageonpad.districtcadre.core.adapter.GraphAdapter;
import com.yze.manageonpad.districtcadre.core.adapter.ResultAdapter;
import com.yze.manageonpad.districtcadre.core.enums.NumEnum;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.utils.CadreUtils;

import static com.yze.manageonpad.districtcadre.MainActivity.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yze.manageonpad.districtcadre.MainActivity.NavigationBarStatusBar;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class DetailView extends AppCompatActivity implements View.OnClickListener {
    private List<Cadre> cadreList = new ArrayList<Cadre>();

    @BindView(R.id.result_recycler_view)
    RecyclerView resultView;

    @BindView(R.id.result_row)
    LinearLayout resultTitle;

    @BindView(R.id.back_btn)
    TextView backBtn;

    @BindView(R.id.change_button)
    TextView changeBtn;

    @BindView(R.id.search_num)
    TextView numText;

    @BindView(R.id.graphica_data)
    TextView graphicaDataBtn;
    /**
     * 卡片视图
     */
    private final static int CARD_VIEW = 0;

    /**
     * 表格视图
     */
    private final static int BOOK_VIEW = 1;
    private List<ChartData<PieData>> pieLists = new ArrayList<>();
    private int[] colorIds;
    private int curViewType = 0;
    private RecyclerView.Adapter adapter;
    private float x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        ButterKnife.bind(this);

        NavigationBarStatusBar(this, true);
        LinearLayout mainContent = (LinearLayout) findViewById(R.id.detail_content);
        mainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationBarStatusBar(DetailView.this, true);
            }
        });

        //  初始化recyclerView
        initResultView(CARD_VIEW);
        //从intent获取上个Activity的数据
        getDataFromIntent(getIntent());

        colorIds = new int[]{R.color.right_cadre_color, R.color.left_cadre_color, R.color.colorAccent, R.color.unChecked, R.color.colorPrimary, R.color.graph_2
                , R.color.graph_3, R.color.graph_4, R.color.graph_5};
        buildGraphData();

        // 初始化按钮
        backBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        graphicaDataBtn.setOnClickListener(this);
    }

    private void buildGraphData() {

        int maleCount = 0;
        int femaleCount = 0;
        Map<String, Integer> xlMap = new HashMap<String, Integer>();
        for (Cadre cadre : cadreList) {

            if (cadre.getXb().equals("男")) {
                maleCount++;
            } else {
                femaleCount++;
            }

            // 学历
            if (xlMap.containsKey(cadre.getQrzxw())) {
                xlMap.put(cadre.getQrzxw(), xlMap.get(cadre.getQrzxw()) + 1);
            } else {
                xlMap.put(cadre.getQrzxw(), NumEnum.NUM_1.getValue());
            }

        }
        List<PieData> xbList = new ArrayList<>();
        xbList.add(new PieData("男", "个", getResources().getColor(colorIds[0]), maleCount * 1.0));
        xbList.add(new PieData("女", "个", getResources().getColor(colorIds[1]), femaleCount * 1.0));
        List<String> stringList = new ArrayList<String>();
        stringList.add("男1");
        stringList.add("女1");
        ChartData<PieData> pieDatas = new ChartData<PieData>("性别分布", stringList, xbList);
        pieLists.add(pieDatas);

        List<PieData> xlList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : xlMap.entrySet()) {
            xlList.add(new PieData(entry.getKey(), "", getResources().getColor(colorIds[index++]), entry.getValue() * 1.0));
        }
        List<String> sList2 = new ArrayList<String>();
        for (int i = 0; i < index; ++i) {
            sList2.add("男" + i);
        }
        ChartData<PieData> pie2Datas = new ChartData<PieData>("学历分布", sList2, xlList);
        pieLists.add(pie2Datas);
        pieLists.add(pie2Datas);
        pieLists.add(pie2Datas);
        pieLists.add(pie2Datas);
        pieLists.add(pie2Datas);
        pieLists.add(pie2Datas);
    }

    /**
     * 初始化RecyclerView && 切换视图时更新
     *
     * @param type 视图类型
     */
    private void initResultView(int type) {
        if (type == CARD_VIEW) {
            resultTitle.setVisibility(View.GONE);
            CardAdapter cardAdapter;
            cardAdapter = new CardAdapter(R.layout.card_item, cadreList);
            cardAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NumEnum.NUM_3.getValue(), StaggeredGridLayoutManager.VERTICAL);
            resultView.setLayoutManager(layoutManager);
            resultView.setAdapter(cardAdapter);
            adapter = cardAdapter;
        } else if (type == BOOK_VIEW) {
            resultTitle.setVisibility(View.VISIBLE);
            ResultAdapter resultAdapter;
            resultAdapter = new ResultAdapter(cadreList, param, DetailView.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            resultView.setLayoutManager(layoutManager);
            resultView.setAdapter(resultAdapter);
            adapter = resultAdapter;
        } else {
            resultTitle.setVisibility(View.GONE);
            GraphAdapter graphAdapter = new GraphAdapter(R.layout.graph_item, pieLists);
            graphAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NumEnum.NUM_3.getValue(), StaggeredGridLayoutManager.VERTICAL);
            resultView.setLayoutManager(layoutManager);
            resultView.setAdapter(graphAdapter);
            adapter = graphAdapter;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Log.d("move length", "onTouchEvent: x2" + String.valueOf(x2) + "  / x1" + String.valueOf(x1));
            if (x2 - x1 > 50) {
                DetailView.this.finish();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 从intent获取数据
     *
     * @param intent
     */
    private void getDataFromIntent(Intent intent) {
        String search_condition;
        TextView apartmentText;
        switch (intent.getStringExtra("intent_type")) {//点击单个干部
            case "single_cadre":
                Cadre tmp_cadre = (Cadre) intent.getSerializableExtra("cadre_object");
                apartmentText = (TextView) findViewById(R.id.search_item);
                apartmentText.setText(tmp_cadre.getXm());
                cadreList.clear();
                cadreList.add(tmp_cadre);
                adapter.notifyDataSetChanged();
                numText.setVisibility(View.GONE);
                break;
//                搜索视图
            case "search_result": //初始化recyclerView
                search_condition = intent.getStringExtra("search_condition");
                apartmentText = (TextView) findViewById(R.id.search_item);
                //更新条件值
                apartmentText.setText("搜索 \"" + search_condition + "\" 的结果");
                List<Cadre> tmpCadreList = CadreUtils.getCadresListByName(search_condition, param.getCadreMap());
                for (Cadre c : tmpCadreList)
                    cadreList.add(c);
                numText.setText("共" + String.valueOf(cadreList.size()) + "条记录");
                adapter.notifyDataSetChanged();
                break;
//                部门搜索视图
            case "search_by_apartment":
                search_condition = intent.getStringExtra("bmbh");
                apartmentText = (TextView) findViewById(R.id.search_item);
                //更新条件值
                apartmentText.setText(intent.getStringExtra("bmmz").toString());
                List<Cadre> tmpCadreList2 = CadreUtils.getCadreListByApartment(param, search_condition);
                for (Cadre c : tmpCadreList2)
                    cadreList.add(c);
                numText.setText("共" + String.valueOf(cadreList.size()) + "条记录");
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.change_button:
                curViewType = curViewType == CARD_VIEW ? BOOK_VIEW : CARD_VIEW;
                initResultView(curViewType);
                break;
            case R.id.graphica_data:
                initResultView(3);
                break;
            default:
                break;
        }
    }
}

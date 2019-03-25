package com.yze.manageonpad.districtcadre.core.subview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import java.util.TreeMap;

import butterknife.BindArray;
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
    //    @BindArray(R.array.colorList) String[] colorIds;
    int[] colorIds;
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

        colorIds = new int[]{R.color.graph_1, R.color.graph_2, R.color.graph_3, R.color.graph_4,
                R.color.graph_5, R.color.graph_6, R.color.graph_7, R.color.graph_8, R.color.graph_9,
                R.color.graph_10, R.color.graph_11, R.color.graph_12, R.color.graph_13, R.color.graph_14};
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
        Map<String, Integer> zgxlMap = new HashMap<String, Integer>();
        Map<Integer, Integer> ageMap = new TreeMap<Integer, Integer>();
        Map<Integer, Integer> rdsjMap = new TreeMap<Integer, Integer>();
        Map<Integer, Integer> xzMap = new TreeMap<Integer, Integer>();
        Map<Integer, Integer> gzMap = new TreeMap<Integer, Integer>();
        Map<String, Integer> jgMap = new TreeMap<String, Integer>();
        for (Cadre cadre : cadreList) {
            // 性别
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

            // 籍贯
            String jg = cadre.getJg().length() > 4 ? cadre.getJg().substring(0, 4) : cadre.getJg();
            if (jgMap.containsKey(cadre.getJg())) {
                jgMap.put(jg, jgMap.get(jg) + 1);
            } else {
                jgMap.put(jg, NumEnum.NUM_1.getValue());
            }

            // 最高学历
            if (zgxlMap.containsKey(cadre.getXw())) {
                zgxlMap.put(cadre.getXw(), zgxlMap.get(cadre.getXw()) + 1);
            } else {
                zgxlMap.put(cadre.getXw(), NumEnum.NUM_1.getValue());
            }

            // 年龄分布
            int age = (NumEnum.getAgeByBirth(cadre.getCsny()) + 4) / 5 * 5;
            if (ageMap.containsKey(age)) {
                ageMap.put(age, ageMap.get(age) + 1);
            } else {
                ageMap.put(age, 1);
            }

            // 入党时间分布
            int rd = (NumEnum.getAgeByBirth(cadre.getRdsj()) + 4) / 5 * 5;
            if (rdsjMap.containsKey(rd)) {
                rdsjMap.put(rd, rdsjMap.get(rd) + 1);
            } else {
                rdsjMap.put(rd, 1);
            }

            // 任现职时间分布
            int xz = (NumEnum.getAgeByBirth(cadre.getRxzsj()) + 4) / 5 * 5;
            if (xzMap.containsKey(xz)) {
                xzMap.put(xz, xzMap.get(xz) + 1);
            } else {
                xzMap.put(xz, 1);
            }

            // 参加工作时间分布
            int gz = (NumEnum.getAgeByBirth(cadre.getCjgzsj()) + 4) / 5 * 5;
            if (gzMap.containsKey(gz)) {
                gzMap.put(gz, gzMap.get(gz) + 1);
            } else {
                gzMap.put(gz, 1);
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


        // 年龄
        ChartData<PieData> agePieDatas = getPieDataChartDataDI(ageMap, "年龄分布（岁）");
        pieLists.add(agePieDatas);

        // 学历
        ChartData<PieData> xlPieDatas = getPieDataChartData(xlMap, "学历分布（全日制）");
        pieLists.add(xlPieDatas);

        // 最高学历
        ChartData<PieData> zgxlPieDatas = getPieDataChartData(zgxlMap, "最高学历分布");
        pieLists.add(zgxlPieDatas);


        // 入党时间
        ChartData<PieData> rdPieDatas = getPieDataChartDataDI(rdsjMap, "入党时间（年）");
        pieLists.add(rdPieDatas);

        // 现职时间
        ChartData<PieData> xzPieDatas = getPieDataChartDataDI(xzMap, "任现职时间（年）");
        pieLists.add(xzPieDatas);

        // 参加工作时间
        ChartData<PieData> gzPieDatas = getPieDataChartDataDI(gzMap, "参加工作年限（年）");
        pieLists.add(gzPieDatas);

        // 籍贯
        ChartData<PieData> jgPieDatas = getPieDataChartData(jgMap, "籍贯");
        pieLists.add(jgPieDatas);

    }

    /**
     * 根据已有的map构造pieChartData
     *
     * @param dataMap Integer类型,Integer类型
     * @return
     */
    @NonNull
    private ChartData<PieData> getPieDataChartDataDI(Map<Integer, Integer> dataMap, String chartName) {
        List<PieData> dataList = new ArrayList<>();
        ChartData<PieData> xzPieDatas;
        int iCount = 0;
        for (Map.Entry<Integer, Integer> entry : dataMap.entrySet()) {
            if (entry.getKey() == 0) {
                dataList.add(new PieData("其它", "", getResources().getColor(colorIds[iCount++]), entry.getValue() * 1.0));
            } else if (entry.getValue() != 0) {
                dataList.add(new PieData(entry.getKey() - 5 + "~" + entry.getKey(), "", getResources().getColor(colorIds[iCount++]), entry.getValue() * 1.0));
            }
        }
        List<String> sList = new ArrayList<String>();
        for (int i = 0; i < iCount; ++i) {
            sList.add("男" + i);
        }
        xzPieDatas = new ChartData<PieData>(chartName, sList, dataList);
        return xzPieDatas;
    }

    /**
     * 根据已有的map构造pieChartData
     *
     * @param dataMap   String,Integer类型
     * @param chartName
     * @return
     */
    @NonNull
    private ChartData<PieData> getPieDataChartData(Map<String, Integer> dataMap, String chartName) {
        List<PieData> zgxlList = new ArrayList<>();
        ChartData<PieData> zgxlPieDatas;
        int iCount = 0;
        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            zgxlList.add(new PieData(entry.getKey(), "", getResources().getColor(colorIds[iCount++]), entry.getValue() * 1.0));
        }
        List<String> sList = new ArrayList<String>();
        for (int i = 0; i < iCount; ++i) {
            sList.add("s" + i);
        }
        zgxlPieDatas = new ChartData<PieData>(chartName, sList, zgxlList);
        return zgxlPieDatas;
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
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NumEnum.NUM_2.getValue(), StaggeredGridLayoutManager.VERTICAL);
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

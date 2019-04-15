package com.yze.manageonpad.districtcadre.core.subview;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.CompareAdapter;
import com.yze.manageonpad.districtcadre.core.enums.NumEnum;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.model.CompareRow;
import com.yze.manageonpad.districtcadre.utils.JSONUtils;
import com.yze.manageonpad.districtcadre.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yze.manageonpad.districtcadre.MainActivity.leftName;
import static com.yze.manageonpad.districtcadre.MainActivity.param;
import static com.yze.manageonpad.districtcadre.MainActivity.rightName;

public class CompareView extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back_btn)
    TextView backBtn;

    @BindView(R.id.compare_recycler_view)
    RecyclerView compareView;

    @BindView(R.id.left_name)
    TextView leftNameTextView;

    @BindView(R.id.left_zw)
    TextView leftZwTextView;

    @BindView(R.id.right_zw)
    TextView rightZwTextView;

    @BindView(R.id.right_name)
    TextView rightNameTextView;

    @BindView(R.id.left_webview)
    WebView leftWebView;

    @BindView(R.id.right_webview)
    WebView rightWebView;

    @BindView(R.id.left_word_button)
    Button leftWordBtn;

    @BindView(R.id.right_word_button)
    Button rightWordBtn;

    @BindView(R.id.compare_word_left)
    LinearLayout leftLayout;

    @BindView(R.id.compare_word_right)
    LinearLayout rightLayout;

    @BindView(R.id.left_word_cancel)
    TextView leftShut;

    @BindView(R.id.right_word_cancel)
    TextView rightShut;

    public static int LEFT = 0;
    public static int RIGHT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_view);
        ButterKnife.bind(this);
        leftLayout.setVisibility(View.GONE);
        rightLayout.setVisibility(View.GONE);
        // 初始化按钮事件
        initButton();
        // 渲染对比项
        renderRecyclerView();
    }

    private void showWeb(final String content, final int cadreType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WebView webView;
                if (cadreType == LEFT) {
                    webView = leftWebView;
                } else {
                    webView = rightWebView;
                }
                webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.setWebChromeClient(new WebChromeClient());

            }
        });
    }

    public void sendWordRequest(final int cadreType) {
        String url = getResources().getString(R.string.serverUrl) + "cadre/getWordHtml";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    String content = response.body().string();
                    showWeb(content, cadreType);
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    public void renderRecyclerView() {
        List<CompareRow> compareRows = new ArrayList<CompareRow>();
        Map<String, Cadre> cadreMap = param.getCadreMap();
        int count = 0;
        Cadre left = new Cadre();
        Cadre right = new Cadre();
        for (Map.Entry<String, Cadre> cadreEntry : cadreMap.entrySet()) {
            if (leftName.equals(cadreEntry.getValue().getXm())) {
                left = cadreEntry.getValue();
                count++;
            }
            if (rightName.equals(cadreEntry.getValue().getXm())) {
                right = cadreEntry.getValue();
                count++;
            }
            if (count == 2) {
                break;
            }

        }
        leftNameTextView.setText("姓名：" + left.getXm());
        leftZwTextView.setText("职务：" + StringUtils.emptyIfNull(left.getXrzw()));
        rightNameTextView.setText("姓名：" + right.getXm());
        rightZwTextView.setText("职务：" + StringUtils.emptyIfNull(right.getXrzw()));
        compareRows.add(new CompareRow("分管", StringUtils.emptyIfNull(left.getFg()), StringUtils.emptyIfNull(right.getFg())));
        compareRows.add(new CompareRow("籍贯", StringUtils.emptyIfNull(left.getJg()), StringUtils.emptyIfNull(right.getJg())));
        compareRows.add(new CompareRow("出生年月", left.getCsny() + "(" + NumEnum.getAgeByBirth(left.getCsny()) + "岁)", right.getCsny() + "(" + NumEnum.getAgeByBirth(right.getCsny()) + "岁)"));
        compareRows.add(new CompareRow("全日制学历", left.getQrzxw(), right.getQrzxw()));
        compareRows.add(new CompareRow("毕业院校", left.getXl(), right.getXl()));
        compareRows.add(new CompareRow("最高学历", left.getXw(), right.getXw()));
        compareRows.add(new CompareRow("参加工作时间", left.getCjgzsj() + "(" + NumEnum.getAgeByBirth(left.getCjgzsj()) + "年)", right.getCjgzsj() + "(" + NumEnum.getAgeByBirth(right.getCjgzsj()) + "年)"));
        compareRows.add(new CompareRow("入党时间", left.getRdsj() + "(" + NumEnum.getAgeByBirth(left.getRdsj()) + "年)", right.getRdsj() + "(" + NumEnum.getAgeByBirth(right.getRdsj()) + "年)"));
        compareRows.add(new CompareRow("任现职时间", left.getRxzsj() + "(" + NumEnum.getAgeByBirth(left.getRxzsj()) + "年)", right.getRxzsj() + "(" + NumEnum.getAgeByBirth(right.getRxzsj()) + "年)"));
        CompareAdapter adapter = new CompareAdapter(R.layout.compare_row_item, compareRows);
        adapter.openLoadAnimation();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        compareView.setAdapter(adapter);
        compareView.setLayoutManager(layoutManager);
    }

    public void initButton() {
        backBtn.setOnClickListener(this);
        leftWordBtn.setOnClickListener(this);
        rightWordBtn.setOnClickListener(this);
        rightShut.setOnClickListener(this);
        leftShut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
            case R.id.left_word_button:
                leftLayout.setVisibility(View.VISIBLE);
                sendWordRequest(LEFT);
                break;
            case R.id.right_word_button:
                rightLayout.setVisibility(View.VISIBLE);
                sendWordRequest(RIGHT);
                break;
            case R.id.left_word_cancel:
                leftLayout.setVisibility(View.GONE);
                break;
            case R.id.right_word_cancel:
                rightLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}

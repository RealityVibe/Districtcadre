package com.yze.manageonpad.districtcadre.core.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.ResearchAdapter;
import com.yze.manageonpad.districtcadre.core.enums.ExceptionsEnum;
import com.yze.manageonpad.districtcadre.model.Apartment;
import com.yze.manageonpad.districtcadre.model.CadresParams;
import com.yze.manageonpad.districtcadre.utils.FileOperationUtils;
import com.yze.manageonpad.districtcadre.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class BackFragment extends Fragment {
    private View view;
    /*
     * 搜索条件
     * */
    private String sc;
    private ResearchAdapter mAdapter;
    private CadresParams cadresParams;
    private String type;
    // 调研员大表rv
    @BindView(R.id.county_fragment_recyclerview)
    RecyclerView mRecyclerView;
    private List<String> investList = new ArrayList<String>();
    private List<String> fullList = new ArrayList<String>();
    // 单位表
    private List<Apartment> apartments = new ArrayList<Apartment>();

    public BackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_researcher, container, false);
        ButterKnife.bind(this, view);
        /*注册广播*/
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("showPro");

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String key = intent.getStringExtra("search_condition");
                sc = key;
                // 打开干部word文件
//                search_condition(key);
                if (search_condition(key)) {
                    try {
                        FileOperationUtils.openDoc(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);

        initView();
        return view;
    }

    //初始化布局View
    public void initView() {
        //对干部List赋值
        cadresParams.getAllNameList().remove(0);
        try {
            investList = JSONUtils.getResearcherList("sourcedata.json", getActivity(), "bk_person");
            fullList.addAll(investList);
        } catch (Exception e) {
            Toast.makeText(getContext(), ExceptionsEnum.BACKUP_MISS_ERROR.getMsg(), Toast.LENGTH_SHORT).show();
        }
        // 初始化recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ResearchAdapter(investList, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean search_condition(String name) {
        if (name.trim().equals("") || name == null) {
            investList.addAll(fullList);
            return false;
        }
        boolean isNull = true;
        investList.clear();
        for (String a : fullList) {
            if (((a.split(",")[0]).split("\\(")[0]).equals(name)) {
                isNull = false;
                investList.add(a);
            }
        }
        mAdapter.notifyDataSetChanged();
        if (isNull)
            investList.addAll(fullList);
        return isNull;
    }


    public CadresParams getCadresParams() {
        return cadresParams;
    }

    public void setCadresParams(CadresParams cadresParams) {
        this.cadresParams = cadresParams;
    }

    public String getName() {
        return "BackFragment";
    }
}
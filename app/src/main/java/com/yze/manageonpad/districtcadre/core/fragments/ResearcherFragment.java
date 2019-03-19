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
import com.yze.manageonpad.districtcadre.utils.FileOperationUtils;
import com.yze.manageonpad.districtcadre.utils.JSONUtils;
import static com.yze.manageonpad.districtcadre.MainActivity.param;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yze
 *
 * 2019/2/27.
 */
public class ResearcherFragment extends Fragment {
    private View view;
    /*
     * 搜索条件
     * */
    private String sc;
    private String type;
    private List<String> cadreList = new ArrayList<String>();
    private List<String> fullList = new ArrayList<String>();
    private ResearchAdapter mAdapter; //这当然是适配器啦
    // 调研员大表rv
    @BindView(R.id.researcher_fragment_recyclerview) RecyclerView mRecyclerView;
    // 单位表
    private List<Apartment> apartments = new ArrayList<Apartment>();

    //
    public ResearcherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_researcher, container, false);
        ButterKnife.bind(this, view);

        /*注册广播*/
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("researcherDoc");

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String key = intent.getStringExtra("search_condition");
                sc = key;
                // 打开干部word文件
                try {
                    if (search_condition(key)) {
                        FileOperationUtils.openDoc(key);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, ExceptionsEnum.CADRE_FILE_MISS.getMsg(), Toast.LENGTH_SHORT).show();
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
        apartments.addAll(param.getCaNameList());
        apartments.addAll(param.getDaNameList());
        apartments.remove(0);
        try {
            cadreList = JSONUtils.getResearcherList("sourcedata.json", getActivity(), "invest");
            fullList.addAll(cadreList);
        } catch (Exception e) {
            Toast.makeText(getContext(), "调研员数据缺失", Toast.LENGTH_SHORT).show();
        }
        // 初始化recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.researcher_fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ResearchAdapter(cadreList, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean search_condition(String name) {
        if (name.trim().equals("") || name == null) {
            cadreList.addAll(fullList);
            return false;
        }
        boolean isNull = true;
        cadreList.clear();
        for (String a : fullList) {
            if (((a.split(",")[0]).split("\\(")[0]).equals(name)) {
                isNull = false;
                cadreList.add(a);
            }
        }
        mAdapter.notifyDataSetChanged();
        if (isNull)
            cadreList.addAll(fullList);
        return isNull;
    }
}

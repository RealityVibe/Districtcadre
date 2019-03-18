package com.yze.manageonpad.districtcadre.core.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.utils.FileOperationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class ResearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mNameList = new ArrayList<String>();
    private List<Integer> idList = new ArrayList<Integer>();
    private Context mContext;
    private String num;

    public ResearchAdapter(List<String> nameList, Context context) {
        mNameList = nameList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        // 0~16个干部
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.apartment_item_line1, parent, false);
            ViewHolder1 holder = new ViewHolder1(view);
            return holder;
        }
        //16~32个干部
        else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.apartment_layout_line2, parent, false);
            ViewHolder2 holder = new ViewHolder2(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final String[] persons = mNameList.get(position).split(",");
        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).departName.setText(persons[0]);
            for (TextView tv : ((ViewHolder1) holder).tvList) {
                tv.setText("");
            }
            for (TextView tv : ((ViewHolder1) holder).tvList) {
                tv.setVisibility(View.GONE);
            }
            for (int i = 1; i < (persons.length); ++i) {
                final String nm = persons[i];
                ((ViewHolder1) holder).tvList.get(i).setText(nm);
                //格子点击事件
                ((ViewHolder1) holder).tvList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 调用wps打开个人任免审批表
                        File docPath = new File(Environment.getExternalStorageDirectory(), "Android/docs");
                        File wordFile = new File(docPath, nm + ".doc");
                        File wordFile2 = new File(docPath, nm + "-" + persons[0] + ".doc");
                        File wordFile3 = new File(docPath, nm + ".docx");
                        File wordFile4 = new File(docPath, nm + "-" + persons[0] + ".docx");
                        try {
                            if (wordFile.exists()) {
                                // 调用函数使用外部程序打开
                                FileOperationUtils.openFile(mContext, wordFile);
                            } else if (wordFile3.exists()) {
                                // 调用函数使用外部程序打开
                                FileOperationUtils.openFile(mContext, wordFile3);
                            } else if (wordFile2.exists()) {
                                // 调用函数使用外部程序打开
                                FileOperationUtils.openFile(mContext, wordFile2);
                            } else if (wordFile4.exists()) {
                                // 调用函数使用外部程序打开
                                FileOperationUtils.openFile(mContext, wordFile4);
                            } else {
                                Toast.makeText(mContext, "该干部文件缺失，请补全材料后再试", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                ((ViewHolder1) holder).tvList.get(i).setVisibility(View.VISIBLE);
            }
        } else {
            ((ViewHolder2) holder).departName.setText(persons[0]);

            for (TextView tv : ((ViewHolder2) holder).tvList) {
                tv.setText("");
            }
            for (TextView tv : ((ViewHolder2) holder).tvList) {
                tv.setVisibility(View.GONE);
            }

            for (int i = 1; i < (persons.length - 1); ++i) {
                final String nm = persons[i];
                ((ViewHolder2) holder).tvList.get(i).setText(nm);
                ((ViewHolder2) holder).tvList.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNameList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView departName;
        List<TextView> tvList = new ArrayList<TextView>();

        public ViewHolder1(View view) {
            super(view);
            idList.clear();
            idList.add(R.id.cadre1);
            idList.add(R.id.cadre2);
            idList.add(R.id.cadre3);
            idList.add(R.id.cadre4);
            idList.add(R.id.cadre5);
            idList.add(R.id.cadre6);
            idList.add(R.id.cadre7);
            idList.add(R.id.cadre8);
            idList.add(R.id.cadre9);
            idList.add(R.id.cadre10);
            idList.add(R.id.cadre11);
            idList.add(R.id.cadre12);
            idList.add(R.id.cadre13);
            idList.add(R.id.cadre14);
            idList.add(R.id.cadre15);
            idList.add(R.id.cadre16);
            departName = (TextView) view.findViewById(R.id.depart_nm);

            for (int i = 0; i < 16; ++i) {
                TextView tv = (TextView) view.findViewById(idList.get(i));
                tvList.add(tv);
            }
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView departName;
        List<TextView> tvList = new ArrayList<TextView>();

        public ViewHolder2(View view) {
            super(view);
            idList.clear();
            idList.add(R.id.cadre1);
            idList.add(R.id.cadre2);
            idList.add(R.id.cadre3);
            idList.add(R.id.cadre4);
            idList.add(R.id.cadre5);
            idList.add(R.id.cadre6);
            idList.add(R.id.cadre7);
            idList.add(R.id.cadre8);
            idList.add(R.id.cadre9);
            idList.add(R.id.cadre10);
            idList.add(R.id.cadre11);
            idList.add(R.id.cadre12);
            idList.add(R.id.cadre13);
            idList.add(R.id.cadre14);
            idList.add(R.id.cadre15);
            idList.add(R.id.cadre16);
            idList.add(R.id.cadre17);
            idList.add(R.id.cadre18);
            idList.add(R.id.cadre19);
            idList.add(R.id.cadre20);
            idList.add(R.id.cadre21);
            idList.add(R.id.cadre22);
            idList.add(R.id.cadre23);
            idList.add(R.id.cadre24);
            idList.add(R.id.cadre25);
            idList.add(R.id.cadre26);
            idList.add(R.id.cadre27);
            idList.add(R.id.cadre28);
            idList.add(R.id.cadre29);
            idList.add(R.id.cadre30);
            idList.add(R.id.cadre31);
            idList.add(R.id.cadre32);
            departName = (TextView) view.findViewById(R.id.depart_nm);
            for (int i = 0; i < 32; ++i) {
                TextView tv = (TextView) view.findViewById(idList.get(i));
                tvList.add(tv);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        String str = mNameList.get(position);
        String[] strs = str.split(",");
        if (strs.length > 17) {
            return 2;
        }
        return 1;
    }
}

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
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.utils.FileOperationUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yze
 *
 * 2019/2/27.
 */

public class BackupAdapter extends RecyclerView.Adapter<BackupAdapter.ViewHolder> {
    private List<Cadre> cadreList;
    private Context mContext;

    public BackupAdapter(List<Cadre> cadres, Context context) {
        this.cadreList = cadres;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.backup_bh) TextView bh;

        @BindView(R.id.backup_xm) TextView xm;

        @BindView(R.id.backup_xb) TextView xb;

        @BindView(R.id.backup_csny) TextView csny;

        @BindView(R.id.backup_cjgzsj) TextView cjgzsj;

        @BindView(R.id.backup_xl) TextView xl;

        @BindView(R.id.backup_dp) TextView dp;

        @BindView(R.id.backup_sf) TextView sf;

        @BindView(R.id.backup_xrzw) TextView xrzw;

        @BindView(R.id.backup_scsfhb) TextView scsfhb;

        TextView cjrs;
        TextView dps;
        TextView dpl;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        final Cadre cadre = cadreList.get(pos);
        holder.bh.setText(String.valueOf(cadre.getBh()));
        holder.xm.setText(cadre.getXm());
        holder.xb.setText(cadre.getXb());
        holder.csny.setText(cadre.getCsny());
        holder.cjgzsj.setText(cadre.getCjgzsj());
        holder.xl.setText(cadre.getXl());
        holder.dp.setText(cadre.getDp());
        holder.sf.setText(cadre.getSf());
        holder.xrzw.setText(cadre.getXrzw());
        holder.scsfhb.setText(cadre.getScsfhb());

        holder.xm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File docPath = new File(Environment.getExternalStorageDirectory(), mContext.getResources().getString(R.string.filePath));

                File wordFile = new File(docPath, cadre.getXm() + ".doc");
                File wordFile2 = new File(docPath, cadre.getXm() + "-" + cadre.getXrzw().substring(0, 3) + ".doc");
                File wordFile3 = new File(docPath, cadre.getXm() + ".docx");
                File wordFile4 = new File(docPath, cadre.getXm() + "-" + cadre.getXrzw().substring(0, 3) + ".docx");

                try {
                    if (wordFile2.exists()) {
                        // 调用函数使用外部程序打开
                        FileOperationUtils.openFile(mContext, wordFile2);
                    } else if (wordFile.exists()) {
                        // 调用函数使用外部程序打开
                        FileOperationUtils.openFile(mContext, wordFile);
                    } else if (wordFile3.exists()) {
                        // 调用函数使用外部程序打开
                        FileOperationUtils.openFile(mContext, wordFile3);
                    } else if (wordFile4.exists()) {
                        // 调用函数使用外部程序打开
                        FileOperationUtils.openFile(mContext, wordFile4);
                    } else {
                        Toast.makeText(mContext, "该干部文件缺失，请补全材料后再试", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "找不到该文件或可以打开该文件的程序", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cadreList.size();
    }
}

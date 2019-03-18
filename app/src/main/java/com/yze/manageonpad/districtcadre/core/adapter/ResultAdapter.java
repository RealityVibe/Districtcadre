package com.yze.manageonpad.districtcadre.core.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.enums.ExceptionsEnum;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.utils.FileOperationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitHelper;


/**
 * @author yze
 *
 * 2019/2/27.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<Cadre> mCadreList;
    private Context mContext;
    private List<TextView> tvList = new ArrayList<TextView>();

    public ResultAdapter(List<Cadre> cadreList, Context context) {
        mCadreList = cadreList;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView xmText;//姓名
        TextView xrzwText;//现任职务
        TextView xbText;//性别
        TextView csnyText;//出生年月
        TextView jgText;//籍贯
        TextView xlText;//全日制教育
        TextView xwText;//在职教育
        TextView qrzxwText;//学位
        TextView gzsjText;//工作时间
        TextView rdsjText;//入党时间
        TextView xzsjText;//任现职时间
        TextView fgText;//分管
        TextView bzText;//备注

        public ViewHolder(View view) {
            super(view);
            xmText = (TextView) view.findViewById(R.id.result_xm);
            xrzwText = (TextView) view.findViewById(R.id.result_xrzw);
            xbText = (TextView) view.findViewById(R.id.result_xb);
            csnyText = (TextView) view.findViewById(R.id.result_csny);
            jgText = (TextView) view.findViewById(R.id.result_jg);
            xlText = (TextView) view.findViewById(R.id.result_xl);
            xwText = (TextView) view.findViewById(R.id.result_xw);
            qrzxwText = (TextView) view.findViewById(R.id.result_qrzxw);
            gzsjText = (TextView) view.findViewById(R.id.result_cjgzsj);
            rdsjText = (TextView) view.findViewById(R.id.result_rdsj);
            xzsjText = (TextView) view.findViewById(R.id.result_rxzsj);
            fgText = (TextView) view.findViewById(R.id.result_fg);
            bzText = (TextView) view.findViewById(R.id.result_bz);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultrow_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Cadre cadre = mCadreList.get(position);
        holder.xlText.getLayoutParams().height = 100;
        tvList.add(holder.xmText);
        tvList.add(holder.xrzwText);
        tvList.add(holder.xbText);
        tvList.add(holder.xlText);
        tvList.add(holder.csnyText);
        tvList.add(holder.jgText);
        tvList.add(holder.xwText);
        tvList.add(holder.qrzxwText);
        tvList.add(holder.gzsjText);
        tvList.add(holder.rdsjText);
        tvList.add(holder.xzsjText);
        tvList.add(holder.fgText);
        tvList.add(holder.bzText);
        initTextViewClass();
        String tmp_xm = cadre.getXm();
        if (tmp_xm.length() < 3) {
            holder.xmText.setText(tmp_xm.charAt(0) + "\u3000" + tmp_xm.charAt(1));
        } else
            holder.xmText.setText(cadre.getXm());
        AutofitHelper.create(holder.xmText);
//        打开该干部的个人审批材料
        holder.xmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File docPath = new File(Environment.getExternalStorageDirectory(), "Android/docs");
                File wordFile = new File(docPath, cadre.getXm() + ".doc");
                File wordFile2 = new File(docPath, cadre.getXm() + "-" + apartmentsList.get(Integer.valueOf(cadre.getBmbh())).getBmmz() + ".doc");
                File wordFile3 = new File(docPath, cadre.getXm() + ".docx");
                File wordFile4 = new File(docPath, cadre.getXm() + "-" + apartmentsList.get(Integer.valueOf(cadre.getBmbh())).getBmmz() + ".docx");
//                Toast.makeText(getContext(),cadre.getXm()+"-"+apartmentsList.get(Integer.valueOf(cadre.getBmbh())).getBmmz().toString() , Toast.LENGTH_LONG ).show();

                try {
                    // 调用函数使用外部程序打开
                    if (wordFile.exists()) {
                        FileOperationUtils.openFile(mContext, wordFile);
                    } else if (wordFile3.exists()) {
                        FileOperationUtils.openFile(mContext, wordFile3);
                    } else if (wordFile2.exists()) {
                        FileOperationUtils.openFile(mContext, wordFile2);
                    } else if (wordFile4.exists()) {
                        FileOperationUtils.openFile(mContext, wordFile4);
                    } else {
                        // 该干部文件丢失
                        Toast.makeText(mContext, ExceptionsEnum.CADRE_FILE_MISS.getMsg(), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, ExceptionsEnum.CADRE_FILE_MISS.getMsg(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        if (cadre.getXb() != null && (!cadre.getXb().equals("null")))
            holder.xbText.setText(cadre.getXb());
        else
            holder.xbText.setText("");
        if (cadre.getXrzw() != null && (!cadre.getXrzw().equals("null"))) {
            holder.xrzwText.setText(cadre.getXrzw());
        } else
            holder.xrzwText.setText("");
        if (cadre.getCsny() != null && (!cadre.getCsny().equals("null")))
            holder.csnyText.setText(cadre.getCsny().substring(0, 7));
        else
            holder.csnyText.setText("");
        if (cadre.getJg() != null && (!cadre.getJg().equals("null")))
            holder.jgText.setText(cadre.getJg());
        else
            holder.jgText.setText("");
        if (cadre.getXl() != null && (!cadre.getXl().equals("null")))
            holder.xlText.setText(cadre.getXl());
        else
            holder.xlText.setText("");
        if (cadre.getXw() != null && (!cadre.getXw().equals("null")))
            holder.xwText.setText(cadre.getXw());
        else
            holder.xwText.setText("");
        if (cadre.getQrzxw() != null && (!cadre.getQrzxw().equals("null"))) {
            holder.qrzxwText.setText(cadre.getQrzxw());
        } else {
            holder.qrzxwText.setText("");
        }
        if (cadre.getCjgzsj() != null && (!cadre.getCjgzsj().equals("null")))
            holder.gzsjText.setText(cadre.getCjgzsj().substring(0, 7));
        if (cadre.getRdsj() != null && (!cadre.getRdsj().equals("null"))) {
            if (cadre.getRdsj().length() > 7)
                holder.rdsjText.setText(cadre.getRdsj().substring(0, 7));
            else
                holder.rdsjText.setText(cadre.getRdsj());
        } else
            holder.rdsjText.setText("");

        if (cadre.getRxzsj() != null && (!cadre.getRxzsj().equals("null")))
            holder.xzsjText.setText(cadre.getRxzsj().substring(0, 7));
        else
            holder.xzsjText.setText("");
        if (cadre.getFg() != null && (!cadre.getFg().equals("null")))
            holder.fgText.setText(cadre.getFg());
        else
            holder.fgText.setText("");
        holder.bzText.setText(cadre.getBz());

    }

    private void initTextViewClass() {
        for (TextView tmp_tv : tvList) {
            tmp_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);
        }
    }

    //展开完整的内容
    public void AlertCompleteMsg(TextView textView) {
        if (!(textView.getText() == null || textView.getText().equals(""))) {
            AlertReport("", textView.getText().toString(), mContext);
        }
    }

    public void AlertReport(String title, String msg, Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (!title.equals(""))
            dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        //OK按钮
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

/*
    public List<Cadre> reSortList(List<Cadre> cadres) {
        List<Cadre> sortedList = new ArrayList<Cadre>();
        sortedList.addAll(cadres);
        sortedList.set(7, cadres.get(11));
        sortedList.set(10, cadres.get(12));
        sortedList.set(11, cadres.get(13));
        sortedList.set(12, cadres.get(14));
        sortedList.set(13, cadres.get(10));
        sortedList.set(14, cadres.get(7));
        return sortedList;
    }
*/

    @Override
    public int getItemCount() {
        return mCadreList.size();
    }
}

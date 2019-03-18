package com.yze.manageonpad.districtcadre.core.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.enums.CadreType;
import com.yze.manageonpad.districtcadre.core.subview.DetailView;
import com.yze.manageonpad.districtcadre.model.Apartment;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.model.CadresParams;
import com.yze.manageonpad.districtcadre.utils.CadreUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */

public class NewRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Apartment> mNameList = new ArrayList<Apartment>();
    private Context mContext;
    private List<Integer> idList = new ArrayList<Integer>();
    private List<Cadre> cadrelist = new ArrayList<Cadre>();
    private String[][] cadreMatrix;
    private int[] prescentNum;
    private Map<String, Cadre> cadreMap;
    private String num;

    public NewRvAdapter(Context context, CadresParams params, CadreType type) {
        mNameList = CadreType.COUNTY.equals(type) ? params.getCaNameList() : params.getDaNameList();
        mContext = context;
        this.cadreMatrix = params.getCadreMatrix();
        this.cadreMap = params.getCadreMap();
        this.prescentNum = params.getPrescentNum();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apartment_item_line1, parent, false);
            ViewHolder1 holder = new ViewHolder1(view);
            return holder;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apartment_layout_line2, parent, false);
            ViewHolder2 holder = new ViewHolder2(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).departName.setText(mNameList.get(position).getBmmz().toString());
            ((ViewHolder1) holder).departName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String bmmz = holder.departName.getText().toString();
                    String bmmz = mNameList.get(position).getBmmz();
                    String tmp_bmbh = null;
                    for (Apartment apart : mNameList) {
                        if (apart.getBmmz().equals(bmmz)) {
                            tmp_bmbh = String.valueOf(apart.getBmbh());
                            break;
                        }
                    }
                    Intent intent = new Intent(mContext, DetailView.class);
                    //将获取到的干部对象传递给detailView
                    intent.putExtra("intent_type", "search_by_apartment");
                    intent.putExtra("bmbh", tmp_bmbh);
                    intent.putExtra("bmmz", bmmz);
                    mContext.startActivity(intent);
                }
            });
            for (TextView tv : ((ViewHolder1) holder).tvList) {
                tv.setText("");
            }
            cadrelist.clear();
            if (CadreType.COUNTY.getCode() == mNameList.get(position).getBmbh()) {
           /*     String[] titles = new String[]{"书记", "镇长", "人大主席", "副书记", "纪委书记", "组织",
                        "宣传统战", "文教", "经济", "城建", "农业", "人武", "派出所长"
                        , "副主席", "副主席", "不定"};*/

                String[] titles = mContext.getResources().getStringArray(R.array.countyTitleList);
                for (int i = 0; i < titles.length; ++i) {
                    Cadre tmpCadre = new Cadre(titles[i], "1");
                    cadrelist.add(tmpCadre);
                }

                for (TextView tv : ((ViewHolder1) holder).tvList) {
                    tv.setVisibility(View.GONE);
                }
                for (int i = 0; i < (cadrelist.size()); ++i) {
                    ((ViewHolder1) holder).tvList.get(i).setText(cadrelist.get(i).getXm());

                    GradientDrawable gd = new GradientDrawable();//创建drawable
                    gd.setColor(Color.parseColor("#520e6a"));
                    gd.setStroke(2, Color.parseColor("#292f3a"));
                    gd.setCornerRadius(5);
                    ((ViewHolder1) holder).tvList.get(i).setBackgroundDrawable(gd);
                    ((ViewHolder1) holder).tvList.get(i).setVisibility(View.VISIBLE);
                    gd = null;
                }
            } else {
                cadrelist = CadreUtils.getCadreListFromMap(prescentNum, cadreMatrix, cadreMap, String.valueOf(mNameList.get(position).getBmbh()));
/*                if (cadrelist.size() > 0) {
                    if (22 > Integer.valueOf(cadrelist.get(0).getBmbh())) {
                        cadrelist = reSortList(cadrelist);
                    }
                }*/

                for (TextView tv : ((ViewHolder1) holder).tvList) {
                    tv.setVisibility(View.GONE);
                }
                for (int i = 0; i < (cadrelist.size()); ++i) {
                    final String nm = cadrelist.get(i).getXm();
                    final Cadre tmpc = cadrelist.get(i);
                    ((ViewHolder1) holder).tvList.get(i).setText(cadrelist.get(i).getXm());
                    ((ViewHolder1) holder).tvList.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!nm.equals("")) {
                                String str = nm;
                                Intent intent = new Intent(mContext, DetailView.class);
                                //将获取到的干部对象传递给detailView
                                intent.putExtra("intent_type", "single_cadre");
                                intent.putExtra("cadre_object", tmpc);
                                mContext.startActivity(intent);
                            }
                        }
                    });

                    GradientDrawable gd = new GradientDrawable();//创建drawable
                    gd.setColor(Color.parseColor("#292f3a"));
                    gd.setStroke(2, Color.parseColor("#000000"));
                    gd.setCornerRadius(5);
                    ((ViewHolder1) holder).tvList.get(i).setBackgroundDrawable(gd);
                    ((ViewHolder1) holder).tvList.get(i).setVisibility(View.VISIBLE);
                }
            }
        } else {
            ((ViewHolder2) holder).departName.setText(mNameList.get(position).getBmmz().toString());
            ((ViewHolder2) holder).departName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String bmmz = holder.departName.getText().toString();
                    String bmmz = mNameList.get(position).getBmmz();
                    String tmp_bmbh = null;
                    for (Apartment apart : mNameList) {
                        if (apart.getBmmz().equals(bmmz)) {
                            tmp_bmbh = String.valueOf(apart.getBmbh());
                            break;
                        }
                    }
                    Intent intent = new Intent(mContext, DetailView.class);
                    //将获取到的干部对象传递给detailView
                    intent.putExtra("intent_type", "search_by_apartment");
                    intent.putExtra("bmbh", tmp_bmbh);
                    intent.putExtra("bmmz", bmmz);
                    mContext.startActivity(intent);
                }
            });
            cadrelist.clear();
            for (TextView tv : ((ViewHolder2) holder).tvList) {
                tv.setText("");
            }
            cadrelist = CadreUtils.getCadreListFromMap(prescentNum, cadreMatrix, cadreMap, String.valueOf(mNameList.get(position).getBmbh()));
            for (TextView tv : ((ViewHolder2) holder).tvList) {
                tv.setVisibility(View.GONE);
            }
            for (int i = 0; i < (cadrelist.size()); ++i) {
                final String nm = cadrelist.get(i).getXm();
                final Cadre tmpc = cadrelist.get(i);
                ((ViewHolder2) holder).tvList.get(i).setText(cadrelist.get(i).getXm());
                ((ViewHolder2) holder).tvList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!nm.equals("")) {
                            String str = nm;
                            Intent intent = new Intent(mContext, DetailView.class);
                            //将获取到的干部对象传递给detailView
                            intent.putExtra("intent_type", "single_cadre");
                            intent.putExtra("cadre_object", tmpc);
                            mContext.startActivity(intent);
                        }
                    }
                });
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
        @BindView(R.id.depart_nm)
        TextView departName;
        List<TextView> tvList = new ArrayList<TextView>();

        public ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
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

            for (int i = 0; i < 32; ++i) {
                TextView tv = (TextView) view.findViewById(idList.get(i));
                tvList.add(tv);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        cadrelist.clear();
        num = String.valueOf(mNameList.get(position).getBmbh());
        cadrelist = CadreUtils.getCadreListFromMap(prescentNum, cadreMatrix, cadreMap, num);
        if (cadrelist.size() > 16) {
            return 2;
        }
        return 1;
    }

/*    public List<Cadre> reSortList(List<Cadre> cadres) {
        List<Cadre> sortedList = new ArrayList<Cadre>();
        sortedList.addAll(cadres);
        // 从web导出的源数据并未修改，需求强行要求变更列的顺序。
        sortedList.set(7, cadres.get(11));
        sortedList.set(10, cadres.get(12));
        sortedList.set(11, cadres.get(13));
        sortedList.set(12, cadres.get(14));
        sortedList.set(13, cadres.get(10));
        sortedList.set(14, cadres.get(7));
        return sortedList;
    }*/
}

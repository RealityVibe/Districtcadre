package com.yze.manageonpad.districtcadre.core.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yze.manageonpad.districtcadre.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yze
 *
 * 2019/2/27.
 */
@Deprecated
public class TitleAdapter extends BaseAdapter {
    private Context mContext;
    //    public static List<String> mThumbIds = new ArrayList<String>();
    public List<String> titleList = new ArrayList<String>();
    public static int cadre_num;
    private LinearLayout title_parent;
    private LayoutInflater mInflater;
    private int mWidthPix;
    public TitleAdapter(Context c, int widthPix) {

        mInflater = LayoutInflater.from(c);
        mContext = c;
        mWidthPix = widthPix;
    }

    public int getCount() {
        return titleList.size();
    }

    public static int getCadre_num() {
        return cadre_num;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            convertView = mInflater.inflate(R.layout.gridview_title_item, null);
            title_parent = convertView.findViewById(R.id.cadre_title_parent);
            title_parent.getLayoutParams().width = mWidthPix;
            textView = convertView.findViewById(R.id.title_item);
            textView.setGravity(Gravity.CENTER);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(titleList.get(position));
        return textView;
    }
}
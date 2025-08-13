package com.solution.brothergroup.DMRNEW;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.solution.brothergroup.DMRNEW.dto.DialogItem;
import com.solution.brothergroup.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<DialogItem> {

    private int resourceLayout;
    private Context mContext;

    public ListAdapter(Context context, int resource, List<DialogItem> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        DialogItem p = getItem(position);
        if (p != null) {
            TextView tv = (TextView) v.findViewById(R.id.tv);
            tv.setText("");
            tv.setTextSize(15.0f);
            tv.setCompoundDrawablesWithIntrinsicBounds(p.icon, null, null, null);
            int dp5 = (int) (5 * mContext.getResources().getDisplayMetrics().density + 0.5f);
            tv.setCompoundDrawablePadding(dp5);

        }

        return v;
    }

}
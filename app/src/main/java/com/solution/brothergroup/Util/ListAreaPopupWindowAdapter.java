package com.solution.brothergroup.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.solution.brothergroup.DTH.dto.PincodeArea;
import com.solution.brothergroup.R;

import java.util.ArrayList;

public class ListAreaPopupWindowAdapter extends BaseAdapter {
    private ArrayList<PincodeArea> items;
    private Context context;
    int layout;
    ClickView mClickView;

    public ListAreaPopupWindowAdapter(ArrayList<PincodeArea> items, Context context, int layout, ClickView mClickView) {
        this.items = items;
        this.context = context;

        this.layout = layout;
        this.mClickView = mClickView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PincodeArea getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(items.get(position).getArea() + "");

        if(items.get(position).getReachInHour()==0){
            holder.tvValue.setVisibility(View.GONE);
        }else{
            holder.tvValue.setVisibility(View.VISIBLE);
            holder.tvValue.setText("Reach Time - " + UtilMethods.INSTANCE.formatedAmount(items.get(position).getReachInHour() + "") + " Hr");

        }

        /* holder.ivImage.setImageResource(getItem(position).getImageRes());*/
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mClickView != null) {
                    mClickView.onClickView(items.get(position).getArea() + "", items.get(position).getId(), items.get(position).getReachInHour());
                }
            }

        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvName, tvValue;


        ViewHolder(View view) {
            tvName = view.findViewById(R.id.name);
            tvValue = view.findViewById(R.id.value);
        }
    }


    public interface ClickView {
        void onClickView(String area, int id, double time);
    }
}

package com.solution.brothergroup.Util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.MoveToWallet.ui.MoveToWalletNew;
import com.solution.brothergroup.R;

import java.util.ArrayList;

public class ListPopupWindowAdapter extends BaseAdapter {
    private ArrayList<BalanceType> items;
    private Context context;
    private boolean isBankActive;
    int layout;
    ClickView mClickView;
    public ListPopupWindowAdapter(ArrayList<BalanceType> items, Context context, boolean isBankActive,int layout,ClickView mClickView) {
        this.items = items;
        this.context = context;
        this.isBankActive = isBankActive;
        this.layout=layout;
        this.mClickView=mClickView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public BalanceType getItem(int i) {
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

        holder.tvName.setText(items.get(position).getName() + "");
        holder.tvValue.setText("\u20B9 " + items.get(position).getAmount());
        /* holder.ivImage.setImageResource(getItem(position).getImageRes());*/
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBankActive) {
                    Intent intent = new Intent(context, MoveToWalletNew.class);
                    Gson gson = new Gson();
                    intent.putExtra("items", "" + gson.toJson(items, new TypeToken<ArrayList<BalanceType>>() {
                    }.getType()));
                    context.startActivity(intent);
                }else{
                    if(mClickView!=null){
                        mClickView.onClickView(items.get(position).getName()+"","\u20B9 " + items.get(position).getAmount());
                    }
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


    public interface ClickView{
        void onClickView(String walletName, String amount);
    }
}

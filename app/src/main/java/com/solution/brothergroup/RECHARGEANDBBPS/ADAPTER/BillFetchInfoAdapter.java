package com.solution.brothergroup.RECHARGEANDBBPS.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.Api.Object.BillAdditionalInfo;
import com.solution.brothergroup.R;

import java.util.List;

public class BillFetchInfoAdapter extends RecyclerView.Adapter<BillFetchInfoAdapter.MyViewHolder> {


    private List<BillAdditionalInfo> mList;
    private Context mContext;
    private boolean isAdditionalInfo;

    public BillFetchInfoAdapter(List<BillAdditionalInfo> mList, Context mContext, boolean isAdditionalInfo) {
        this.mList = mList;
        this.mContext = mContext;
        this.isAdditionalInfo = isAdditionalInfo;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bbps_info_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final BillAdditionalInfo item = mList.get(position);
        if(isAdditionalInfo)
        {
            holder.infoNameTv.setText(item.getInfoName());
            holder.infoValueTv.setText(item.getInfoValue());
        }else {
            holder.infoNameTv.setText(item.getAmountName());
            holder.infoValueTv.setText(mContext.getString(R.string.rupiya) + item.getAmountValue()+"");
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView infoValueTv, infoNameTv;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            infoValueTv = view.findViewById(R.id.infoValueTv);
            infoNameTv = view.findViewById(R.id.infoNameTv);
        }
    }

}

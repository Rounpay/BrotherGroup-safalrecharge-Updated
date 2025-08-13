package com.solution.brothergroup.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.R;

import java.util.List;

public class WalletTypeAdapter extends RecyclerView.Adapter<WalletTypeAdapter.MyViewHolder> {

    int cliclPosition = -1;
    onClick mOnClick;
    private List<WalletType> transactionsList;
    private Context mContext;

    public WalletTypeAdapter(Context mContext, List<WalletType> transactionsList, onClick mOnClick) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.mOnClick = mOnClick;
    }

    @Override
    public WalletTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallet_type_adapter, parent, false);

        return new WalletTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletTypeAdapter.MyViewHolder holder, final int position) {
        final WalletType operator = transactionsList.get(position);
        holder.walletTv.setText(operator.getName());
        if (position == 0 && cliclPosition == -1 || cliclPosition == position) {
            holder.walletTv.setBackgroundResource(R.drawable.rounded_light_green);
            holder.walletTv.setTextColor(Color.WHITE);
        } else {
            holder.walletTv.setBackgroundResource(0);
            holder.walletTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
        }
        holder.walletTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliclPosition = position;
                notifyDataSetChanged();
                if (mOnClick != null) {
                    mOnClick.onClick(operator.getId());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public interface onClick {
        void onClick(int id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView walletTv;


        public MyViewHolder(View view) {
            super(view);
            walletTv = view.findViewById(R.id.walletTv);

        }
    }
}

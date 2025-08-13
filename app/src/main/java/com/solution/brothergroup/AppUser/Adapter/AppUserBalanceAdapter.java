package com.solution.brothergroup.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AppUserBalanceAdapter extends RecyclerView.Adapter<AppUserBalanceAdapter.MyViewHolder> {

    private List<BalanceType> transactionsList;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv,balanceTv;


        public MyViewHolder(View view) {
            super(view);
            nameTv =  view.findViewById(R.id.name);
            balanceTv =  view.findViewById(R.id.balance);

        }
    }

    public AppUserBalanceAdapter(Context mContext, List<BalanceType> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public AppUserBalanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_user_balance_adapter, parent, false);

        return new AppUserBalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppUserBalanceAdapter.MyViewHolder holder, final int position) {
        final BalanceType operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getName());
        holder.balanceTv.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(operator.getBalance()+""));



    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public interface onClick {
        void onClick(int id);
    }
}

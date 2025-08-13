package com.solution.brothergroup.Fragments.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;


public class WalletBalanceAdapter extends RecyclerView.Adapter<WalletBalanceAdapter.MyViewHolder> {


    private ArrayList<BalanceType> mList;
    private Context mContext;

    public WalletBalanceAdapter(Context mContext, ArrayList<BalanceType> mList) {
        this.mList = mList;
        this.mContext = mContext;

    }

    @Override
    public WalletBalanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_balance, parent, false);

        return new WalletBalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletBalanceAdapter.MyViewHolder holder, int position) {
        final BalanceType operator = mList.get(position);

        holder.name.setText(operator.getName());
        holder.amount.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getAmount()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView name, amount;
        ImageView iv_wallet;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);
            // iv_wallet = view.findViewById(R.id.iv_wallet);

        }
    }
}

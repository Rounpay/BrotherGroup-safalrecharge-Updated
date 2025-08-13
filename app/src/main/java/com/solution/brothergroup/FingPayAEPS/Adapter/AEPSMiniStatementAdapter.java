package com.solution.brothergroup.FingPayAEPS.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.FingPayAEPS.dto.MiniStatements;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Nisha on 12-11-2020.
 */

public class AEPSMiniStatementAdapter extends RecyclerView.Adapter<AEPSMiniStatementAdapter.MyViewHolder> {


    private ArrayList<MiniStatements> operatorList;
    private Context mContext;

    public AEPSMiniStatementAdapter(ArrayList<MiniStatements> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mini_statement, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MiniStatements operator = operatorList.get(position);
        holder.date.setText(operator.getTransactionDate());
        holder.narration.setText(operator.getNarration());
        holder.amt.setText("\u20B9 "+ UtilMethods.INSTANCE.formatedAmount(operator.getAmount() + ""));

        if (operator.getTransactionType().toLowerCase().equalsIgnoreCase("cr")) {
            holder.amt.setTextColor(mContext.getResources().getColor(R.color.darkGreen));
        } else {
            holder.amt.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }
    }


    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, amt, narration;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            date = view.findViewById(R.id.date);
            amt = view.findViewById(R.id.amt);
            narration = view.findViewById(R.id.narration);

        }
    }

}




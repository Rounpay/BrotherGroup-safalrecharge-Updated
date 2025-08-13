package com.solution.brothergroup.MoveToWallet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solution.brothergroup.Api.Object.SlabRangeDetail;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class SettlementChargeDetailAdapter extends RecyclerView.Adapter<SettlementChargeDetailAdapter.MyViewHolder> {

    private ArrayList<SlabRangeDetail> transactionsList;
    private Context mContext;

    public SettlementChargeDetailAdapter(ArrayList<SlabRangeDetail> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_settlement_charge_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SlabRangeDetail operator = transactionsList.get(position);
        holder.range.setText(operator.getMinRange() + " - " + operator.getMaxRange());
        String comType, amtType;
        if (operator.isCommType()) {
            comType = "(SUR.)";

        } else {
            comType = "(COMM.)";
        }

        if (operator.isAmtType()) {
            amtType = mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getComm() + "");


        } else {
            amtType = UtilMethods.INSTANCE.formatedAmount(operator.getComm() + "") + " %";
        }

        holder.operator.setText(operator.getOperator() + "");
        holder.comSur.setText(amtType + " " + comType);
        //holder.fixed.setText(UtilMethods.INSTANCE.formatedAmount(operator.getFixedCharge() + ""));
        //holder.maxCom.setText(UtilMethods.INSTANCE.formatedAmount(operator.getMaxComm() + ""));
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView comSur, operator, maxCom, range, fixed;


        public MyViewHolder(View view) {
            super(view);
            operator = (AppCompatTextView) view.findViewById(R.id.operator);
            fixed = (AppCompatTextView) view.findViewById(R.id.fixed);
            comSur = (AppCompatTextView) view.findViewById(R.id.comSur);
            maxCom = (AppCompatTextView) view.findViewById(R.id.maxCom);
            range = (AppCompatTextView) view.findViewById(R.id.range);
        }
    }

}
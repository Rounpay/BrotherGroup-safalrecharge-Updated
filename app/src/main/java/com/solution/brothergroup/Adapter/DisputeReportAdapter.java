package com.solution.brothergroup.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solution.brothergroup.Api.Object.RefundLog;
import com.solution.brothergroup.R;
import com.solution.brothergroup.W2RRequest.report.W2RHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class DisputeReportAdapter extends RecyclerView.Adapter<DisputeReportAdapter.MyViewHolder> {
    String charText = "";
    int resourceId = 0;
    private List<RefundLog> rechargeStatus;
    private List<RefundLog> transactionsList;
    private Context mContext;

    public DisputeReportAdapter(List<RefundLog> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
    }

    @Override
    public DisputeReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dispute_report_adapter, parent, false);

        return new DisputeReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DisputeReportAdapter.MyViewHolder holder, int position) {
        final RefundLog operator = transactionsList.get(position);
        holder.txn.setText("" + operator.getTransactionID());
        holder.operator.setText("" + operator.getOperator());
        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRequestedAmount());
        holder.number.setText("" + operator.getAccountNo());
        holder.acceptRejectDate.setText("" + operator.getRefundActionDate());
        holder.status.setText("" + operator.getrStatus());
        holder.requestDate.setText("" + operator.getRefundRequestDate());
        holder.rechargeDate.setText("" + operator.getEntryDate());
        holder.liveId.setText("" + operator.getLiveID());
        holder.refundStatus.setText("" + operator.getRefundType());
        holder.refundRemark.setText("" + operator.getRefundRemark());
        if(mContext instanceof W2RHistory){
            holder.refundRemarkView.setVisibility(View.VISIBLE);
        }else{
            holder.refundRemarkView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (RefundLog wp : rechargeStatus) {
                if (wp.getAccountNo().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getOutletMobile().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOutletName().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getTransactionID().toLowerCase(Locale.getDefault()).contains(charText) || wp.getrStatus().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View refundRemarkView;
        private AppCompatTextView status;
        private AppCompatTextView amount;
        private AppCompatTextView operator;
        private AppCompatTextView txnLabel;
        private AppCompatTextView txn;
        private AppCompatTextView liveIdLabel;
        private AppCompatTextView liveId;
        private AppCompatTextView numberLabel;
        private AppCompatTextView number;
        private AppCompatTextView requestDateLabel;
        private AppCompatTextView requestDate;
        private AppCompatTextView acceptRejectDateLabel;
        private AppCompatTextView acceptRejectDate;
        private AppCompatTextView rechargeDateLabel;
        private AppCompatTextView rechargeDate, refundRemark;
        private AppCompatTextView refundStatusLabel;
        private AppCompatTextView refundStatus;


        public MyViewHolder(View view) {
            super(view);
            refundRemarkView = view.findViewById(R.id.refundRemarkView);
            status = (AppCompatTextView) view.findViewById(R.id.status);
            amount = (AppCompatTextView) view.findViewById(R.id.amount);
            operator = (AppCompatTextView) view.findViewById(R.id.operator);
            txnLabel = (AppCompatTextView) view.findViewById(R.id.txnLabel);
            txn = (AppCompatTextView) view.findViewById(R.id.txn);
            liveIdLabel = (AppCompatTextView) view.findViewById(R.id.liveIdLabel);
            liveId = (AppCompatTextView) view.findViewById(R.id.liveId);
            numberLabel = (AppCompatTextView) view.findViewById(R.id.numberLabel);
            number = (AppCompatTextView) view.findViewById(R.id.number);
            requestDateLabel = (AppCompatTextView) view.findViewById(R.id.requestDateLabel);
            requestDate = (AppCompatTextView) view.findViewById(R.id.requestDate);
            acceptRejectDateLabel = (AppCompatTextView) view.findViewById(R.id.acceptRejectDateLabel);
            acceptRejectDate = (AppCompatTextView) view.findViewById(R.id.acceptRejectDate);
            rechargeDateLabel = (AppCompatTextView) view.findViewById(R.id.rechargeDateLabel);
            rechargeDate = (AppCompatTextView) view.findViewById(R.id.rechargeDate);
            refundStatusLabel = (AppCompatTextView) view.findViewById(R.id.refundStatusLabel);
            refundStatus = (AppCompatTextView) view.findViewById(R.id.refundStatus);
            refundRemark = (AppCompatTextView) view.findViewById(R.id.refundRemark);
        }
    }
}
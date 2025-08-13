package com.solution.brothergroup.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.Api.Object.LedgerObject;
import com.solution.brothergroup.R;

import java.util.ArrayList;
import java.util.Locale;

public class LedgerReportAdapter extends RecyclerView.Adapter<LedgerReportAdapter.MyViewHolder> {
    String charText = "";
    int resourceId = 0;
    private ArrayList<LedgerObject> rechargeStatus;
    private ArrayList<LedgerObject> transactionsList;
    private Context mContext;

    public LedgerReportAdapter(ArrayList<LedgerObject> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
    }

    @Override
    public LedgerReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ledger_report_adapter, parent, false);

        return new LedgerReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LedgerReportAdapter.MyViewHolder holder, int position) {
        final LedgerObject operator = transactionsList.get(position);
        if (operator.getDebit().equals("0.00")) {
            holder.txnLabel.setText("Credit" + " : ");
            holder.txn.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getCredit());
            holder.txnLabel.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getCredit().equals("0.00")) {
            holder.txnLabel.setText("Debit" + " : ");
            holder.txn.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getDebit());
            holder.txnLabel.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        holder.tid.setText("" + operator.getTid());
        holder.debit.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getDebit());
        holder.old.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getLastAmount());
        holder.balance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getCurentBalance());
        holder.date.setText(operator.getEntryDate());
        holder.statusLabel.setText("Description : ");
        holder.status.setText(operator.getDescription());
        String faqsearchDescription = transactionsList.get(position).getDescription().toLowerCase(Locale.getDefault());
        if (faqsearchDescription.contains(charText)) {
            int startPos = faqsearchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(transactionsList.get(position).getDescription()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.status.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            holder.status.setText(transactionsList.get(position).getDescription());
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
            for (LedgerObject wp : rechargeStatus) {
                if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView txn, txnLabel, balance, date, statusLabel, status, old, debit, tid;


        public MyViewHolder(View view) {
            super(view);
            txn = (AppCompatTextView) view.findViewById(R.id.txn);
            txnLabel = (AppCompatTextView) view.findViewById(R.id.txnLabel);
            balance = (AppCompatTextView) view.findViewById(R.id.balance);
            old = (AppCompatTextView) view.findViewById(R.id.old);
            debit = (AppCompatTextView) view.findViewById(R.id.debit);
            date = (AppCompatTextView) view.findViewById(R.id.date);
            statusLabel = (AppCompatTextView) view.findViewById(R.id.statusLabel);
            status = (AppCompatTextView) view.findViewById(R.id.status);
            tid = (AppCompatTextView) view.findViewById(R.id.tid);
        }
    }
}

package com.solution.brothergroup.AppUser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.solution.brothergroup.Api.Object.LedgerObject;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class AccountStatementReportAdapter extends RecyclerView.Adapter<AccountStatementReportAdapter.MyViewHolder> implements Filterable {



    private ArrayList<LedgerObject> filterListItem = new ArrayList<>();
    private ArrayList<LedgerObject> listItem = new ArrayList<>();
    private Activity mContext;




    private String charText="";

    public AccountStatementReportAdapter(ArrayList<LedgerObject> transactionsList,
                                         Context mContext) {
        this.mContext = (Activity) mContext;
        this.filterListItem = transactionsList;
        this.listItem = transactionsList;

    }


    @Override
    public AccountStatementReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_account_statement_report, parent, false);

        return new AccountStatementReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AccountStatementReportAdapter.MyViewHolder holder, int position) {
        final LedgerObject operator = filterListItem.get(position);
        if (operator.getDebit().equals("0.00")) {
            holder.txnLabel.setText("Credit" + " : ");
            holder.txn.setText(UtilMethods.INSTANCE.formatedAmountWithRupees( operator.getCredit()));
            holder.txnLabel.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getCredit().equals("0.00")) {
            holder.txnLabel.setText("Debit" + " : ");
            holder.txn.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getDebit()));
            holder.txnLabel.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        holder.tid.setText(""+operator.getTid());
        holder.debit.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getDebit()));
        holder.old.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getLastAmount()));
        holder.balance.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getCurentBalance()));
        holder.date.setText(operator.getEntryDate());
        holder.statusLabel.setText("Description : ");
        holder.status.setText(operator.getDescription());
        String faqsearchDescription = operator.getDescription().toLowerCase(Locale.getDefault());
        if (faqsearchDescription.contains(charText)) {
            int startPos = faqsearchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getDescription()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.status.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            holder.status.setText(operator.getDescription());
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                charText= charSequence.toString();
                if (charText.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<LedgerObject> filteredList = new ArrayList<>();
                    for (LedgerObject row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getMobileNo() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getUser() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getCurentBalance() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getTid() + "").toLowerCase().contains(charText.toLowerCase() )||
                                (row.getDescription() + "").toLowerCase().contains(charText.toLowerCase())
                        ) {
                            filteredList.add(row);
                        }
                    }

                    filterListItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterListItem = (ArrayList<LedgerObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }





    @Override
    public int getItemCount() {
        return filterListItem.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView txn, txnLabel, balance, date, statusLabel, status, old, debit,tid;


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
            tid= (AppCompatTextView) view.findViewById(R.id.tid);
        }
    }
}

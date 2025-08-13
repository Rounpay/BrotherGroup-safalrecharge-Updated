package com.solution.brothergroup.Adapter;

import android.app.Activity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.solution.brothergroup.Api.Object.UserDaybookReport;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;
import java.util.List;


public class UserDayBookListAdapter extends RecyclerView.Adapter<UserDayBookListAdapter.MyViewHolder> implements Filterable {

    private List<UserDaybookReport> listItem;
    private List<UserDaybookReport> filterListItem;
    private Activity mContext;
    String rollId;
    public UserDayBookListAdapter(Activity mContext, List<UserDaybookReport> mUserLists, String rollId) {
        this.listItem = mUserLists;
        this.filterListItem = mUserLists;
        this.mContext = mContext;
        this.rollId=rollId;
    }

    @Override
    public UserDayBookListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_day_book, parent, false);

        return new UserDayBookListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserDayBookListAdapter.MyViewHolder holder, final int position) {
        final UserDaybookReport mItem = filterListItem.get(position);

        if (rollId.equalsIgnoreCase("3") || rollId.equalsIgnoreCase("2")) {
            holder.extraCommisionView.setVisibility(View.GONE);
        } else {
            holder.extraCommisionView.setVisibility(View.VISIBLE);
            holder.selfComm.setText("Self Commission\n\u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getSelfCommission())));
            holder.teamComm.setText("Team Commission\n\u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getTeamCommission())));
        }
        holder.operator.setText(mItem.getOperator());
        holder.commisiion.setText("Commission : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getCommission())));
        holder.totalHits.setText("Total Hits : " + mItem.getTotalHits());
        holder.totalAmt.setText("Total Amount : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getTotalAmount())));
        holder.successHits.setText("Success Hits : " + mItem.getSuccessHits());
        holder.successAmt.setText("Success Amount : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getSuccessAmount())));
        holder.failedHits.setText("Failed Hits : " + mItem.getFailedHits());
        holder.failedAmt.setText("Failed Amount : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getFailedAmount())));
        holder.pendingHits.setText("Pending Hits : " + mItem.getPendingHits());
        holder.pendingAmt.setText("Pending Amount : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getPendingAmount())));

    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<UserDaybookReport> filteredList = new ArrayList<>();
                    for (UserDaybookReport row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getOperator().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<UserDaybookReport>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView operator;
        private AppCompatTextView commisiion;
        private AppCompatTextView totalHits;
        private AppCompatTextView totalAmt;
        private AppCompatTextView successHits;
        private AppCompatTextView successAmt;
        private AppCompatTextView failedHits;
        private AppCompatTextView failedAmt;
        private AppCompatTextView pendingHits;
        private AppCompatTextView pendingAmt;
        View extraCommisionView;
        AppCompatTextView teamComm, selfComm;

        public MyViewHolder(View view) {
            super(view);
            operator = view.findViewById(R.id.operator);
            commisiion = view.findViewById(R.id.commisiion);
            totalHits = view.findViewById(R.id.totalHits);
            totalAmt = view.findViewById(R.id.totalAmt);
            successHits = view.findViewById(R.id.successHits);
            successAmt = view.findViewById(R.id.successAmt);
            failedHits = view.findViewById(R.id.failedHits);
            failedAmt = view.findViewById(R.id.failedAmt);
            pendingHits = view.findViewById(R.id.pendingHits);
            pendingAmt = view.findViewById(R.id.pendingAmt);
            extraCommisionView = view.findViewById(R.id.extraCommisionData);
            teamComm = view.findViewById(R.id.teamCommision);
            selfComm = view.findViewById(R.id.selfCommision);
        }
    }
}

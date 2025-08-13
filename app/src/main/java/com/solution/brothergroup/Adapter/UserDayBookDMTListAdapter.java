package com.solution.brothergroup.Adapter;

import android.app.Activity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solution.brothergroup.Api.Object.UserDaybookDMRReport;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.List;


public class UserDayBookDMTListAdapter extends RecyclerView.Adapter<UserDayBookDMTListAdapter.MyViewHolder> {

    String rollId;
    private List<UserDaybookDMRReport> listItem;
    private Activity mContext;

    public UserDayBookDMTListAdapter(Activity mContext, List<UserDaybookDMRReport> mUserLists, String rollId) {
        this.listItem = mUserLists;
        this.mContext = mContext;
        this.rollId = rollId;
    }

    @Override
    public UserDayBookDMTListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_day_book_dmt, parent, false);

        return new UserDayBookDMTListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserDayBookDMTListAdapter.MyViewHolder holder, final int position) {
        final UserDaybookDMRReport mItem = listItem.get(position);

        if (rollId.equalsIgnoreCase("3") || rollId.equalsIgnoreCase("2")) {
            holder.extraCommisionView.setVisibility(View.GONE);
        } else {
            holder.extraCommisionView.setVisibility(View.VISIBLE);
            holder.selfComm.setText("Self Commission\n\u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getSelfCommission())));
            holder.teamComm.setText("Team Commission\n\u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getTeamCommission())));
        }

        holder.creaditedAmount.setText("Credited Amount : \u20B9 " +UtilMethods.INSTANCE.formatedAmount( String.format("%.2f", mItem.getCreditedAmount())));
        holder.successHits.setText("Success Hits : " + mItem.getTotalHits());
        holder.successAmt.setText("Success Amount : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getTotalAmount())));
        holder.failedHits.setText("CCF : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getCcf())));
        holder.failedAmt.setText("Base Amount : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getBaseAmount())));
        holder.pendingHits.setText("Surcharge : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getSurcharge())));
        holder.pendingAmt.setText("GST On Surcharge : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getGstOnSurcharge())));
        holder.amtWithTds.setText("With TDS : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getAmountWithTDS())));
        holder.tds.setText("TDS : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getTds())));
        holder.amtAfterSrchrg.setText("After Surcharge : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getAmountAfterSurcharge())));
        holder.refundGst.setText("Refund GST : \u20B9 " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", mItem.getRefundGST())));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View extraCommisionView;
        AppCompatTextView teamComm, selfComm;
        private AppCompatTextView creaditedAmount;
        private AppCompatTextView successHits;
        private AppCompatTextView successAmt;
        private AppCompatTextView failedHits;
        private AppCompatTextView failedAmt;
        private AppCompatTextView pendingHits;
        private AppCompatTextView pendingAmt, amtWithTds, tds, amtAfterSrchrg, refundGst;

        public MyViewHolder(View view) {
            super(view);
            creaditedAmount = view.findViewById(R.id.creaditedAmount);
            successHits = view.findViewById(R.id.successHits);
            successAmt = view.findViewById(R.id.successAmt);
            failedHits = view.findViewById(R.id.failedHits);
            failedAmt = view.findViewById(R.id.failedAmt);
            pendingHits = view.findViewById(R.id.pendingHits);
            pendingAmt = view.findViewById(R.id.pendingAmt);
            extraCommisionView = view.findViewById(R.id.extraCommisionData);
            teamComm = view.findViewById(R.id.teamCommision);
            selfComm = view.findViewById(R.id.selfCommision);
            amtWithTds = view.findViewById(R.id.amtWithTds);
            tds = view.findViewById(R.id.tds);
            amtAfterSrchrg = view.findViewById(R.id.amtAfterSrchrg);
            refundGst = view.findViewById(R.id.refundGst);
        }
    }


}

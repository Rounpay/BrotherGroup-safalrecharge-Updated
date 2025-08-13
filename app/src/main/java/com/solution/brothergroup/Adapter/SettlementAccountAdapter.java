package com.solution.brothergroup.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.Activities.SettlementBankListActivity;
import com.solution.brothergroup.Api.Object.SettlementAccountData;
import com.solution.brothergroup.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 29-12-2017.
 */

public class SettlementAccountAdapter extends RecyclerView.Adapter<SettlementAccountAdapter.MyViewHolder> implements Filterable {

    int resourceId = 0;

    Dialog dialog = null;
    Activity activity;
    private ArrayList<SettlementAccountData> listItem, filterListItem;


    boolean isSattlemntAccountVerify;

    public SettlementAccountAdapter(boolean isSattlemntAccountVerify, ArrayList<SettlementAccountData> listItem, Activity activity) {
        this.listItem = listItem;
        this.filterListItem = listItem;
        this.activity = activity;
        this.isSattlemntAccountVerify = isSattlemntAccountVerify;
    }

    @Override
    public SettlementAccountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_settlement_account, parent, false);


        return new SettlementAccountAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SettlementAccountAdapter.MyViewHolder holder, final int position) {
        final SettlementAccountData operator = filterListItem.get(position);
        holder.beneName.setText(operator.getAccountHolder());
        holder.beneAccountNumber.setText(operator.getAccountNumber());
        holder.beneBank.setText(operator.getBankName());
        holder.beneIFSC.setText(operator.getIfsc());
        holder.verifyStatusTv.setText(operator.getVerificationText() + "");
        holder.approvalStatusTv.setText(operator.getApprovalText() + "");

        if (operator.getApprovalStatus() == 2) {
            holder.switchView.setVisibility(View.VISIBLE);
        } else {
            holder.switchView.setVisibility(View.GONE);
        }
        if (isSattlemntAccountVerify) {
            if (operator.getVerificationStatus() == 0) {
                holder.verifyBtn.setText("Verify");
                holder.verifyBtn.setVisibility(View.VISIBLE);
            } else if (operator.getVerificationStatus() == 1) {
                holder.verifyBtn.setText("Update UTR");
                holder.verifyBtn.setVisibility(View.VISIBLE);
            } else {
                holder.verifyBtn.setVisibility(View.GONE);
            }
        } else {
            holder.verifyBtn.setVisibility(View.GONE);
        }
        if (operator.isDefault()) {
            holder.activeSwitch.setChecked(true);
        } else {
            holder.activeSwitch.setChecked(false);
        }
        /*holder.beneName.setText(operator.getNAME());
        holder.beneAccountNumber.setText(operator.getACCOUNT());
        holder.beneBank.setText(operator.getBANK());
        holder.beneIFSC.setText(operator.getIFSC());*/

        holder.verifyBtn.setOnClickListener(v -> {
            if (activity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) activity).VerifyOrUtr(operator);
            }

        });
        holder.updateBtn.setOnClickListener(v -> {
            if (activity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) activity).update(operator);
            }

        });
        holder.switchView.setOnClickListener(v -> {
            if (activity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) activity).setDefault(operator);
            }

        });

        holder.deleteBtn.setOnClickListener(v -> {
            if (activity instanceof SettlementBankListActivity) {
                ((SettlementBankListActivity) activity).confirmationDialog(operator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }


    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
        }
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
                    ArrayList<SettlementAccountData> filteredList = new ArrayList<>();
                    for (SettlementAccountData row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getMobileNo() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getAccountNumber() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBankName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getIfsc() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getAccountHolder() + "").toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<SettlementAccountData>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public void setFlag(boolean isSattlemntAccountVerify) {
        this.isSattlemntAccountVerify = isSattlemntAccountVerify;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName;
        public TextView beneAccountNumber;
        public TextView beneBank;
        public TextView beneIFSC, approvalStatusTv, verifyStatusTv;
        public SwitchCompat activeSwitch;
        public Button deleteBtn, verifyBtn, updateBtn;
        public View switchView, approvalStatusView, verificationStatusView;


        public MyViewHolder(View view) {
            super(view);
            beneName = (TextView) view.findViewById(R.id.beneName);
            beneAccountNumber = (TextView) view.findViewById(R.id.beneAccountNumber);
            beneBank = (TextView) view.findViewById(R.id.beneBank);
            beneIFSC = (TextView) view.findViewById(R.id.beneIFSC);
            deleteBtn = view.findViewById(R.id.deleteBtn);
            verifyBtn = view.findViewById(R.id.verifyBtn);
            updateBtn = view.findViewById(R.id.updateBtn);
            approvalStatusTv = view.findViewById(R.id.approvalStatusTv);

            verifyStatusTv = view.findViewById(R.id.verifyStatusTv);
            activeSwitch = view.findViewById(R.id.activeSwitch);
            switchView = view.findViewById(R.id.switchView);
            approvalStatusView = view.findViewById(R.id.approvalStatusView);
            verificationStatusView = view.findViewById(R.id.verificationStatusView);


            if (!isSattlemntAccountVerify) {
                approvalStatusView.setVisibility(View.GONE);
                verificationStatusView.setVisibility(View.GONE);
                verifyBtn.setVisibility(View.GONE);
            }
        }
    }


}

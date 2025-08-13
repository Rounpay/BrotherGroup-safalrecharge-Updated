package com.solution.brothergroup.DMRNEW.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.solution.brothergroup.Api.Response.BenisObject;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.MyViewHolder> {

    int resourceId = 0;
    CustomLoader loader;
    Dialog dialog = null;
    Activity activity;
    private ArrayList<BenisObject> operatorList;

    public BeneficiaryAdapter(ArrayList<BenisObject> operatorList, Activity activity) {
        this.operatorList = operatorList;

        this.activity = activity;
    }

    @Override
    public BeneficiaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beneficiary_list_adapter, parent, false);

        loader = new CustomLoader(activity, android.R.style.Theme_Translucent_NoTitleBar);

        return new BeneficiaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryAdapter.MyViewHolder holder, final int position) {
        final BenisObject operator = operatorList.get(position);
        holder.beneName.setText(operator.getBeneName());
        holder.beneAccountNumber.setText(operator.getAccountNo());
        holder.beneBank.setText(operator.getBankName());
        holder.beneIFSC.setText(operator.getIfsc());

        /*holder.beneName.setText(operator.getNAME());
        holder.beneAccountNumber.setText(operator.getACCOUNT());
        holder.beneBank.setText(operator.getBANK());
        holder.beneIFSC.setText(operator.getIFSC());*/

        holder.transferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transferIntent = new Intent(activity, MoneyTransfer.class);
                transferIntent.putExtra("name", operatorList.get(position).getBeneName());
                transferIntent.putExtra("bankAccount", operatorList.get(position).getAccountNo());
                transferIntent.putExtra("bank", operatorList.get(position).getBankName());
                transferIntent.putExtra("beneficiaryCode", operatorList.get(position).getBeneID());
                transferIntent.putExtra("ifsc", operatorList.get(position).getIfsc());
                activity.startActivity(transferIntent);
                activity.finish();


            }
        });

        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog(operatorList.get(position).getBeneID(), operatorList.get(position).getBeneName(), ""/* operatorList.get(position).getMOBILENO()*/);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void confirmationDialog(final String id, final String beneName, final String beneNumber) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.confirm_dialog, null);

        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView number = (TextView) view.findViewById(R.id.number);
        final AppCompatButton okButton = (AppCompatButton) view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = (AppCompatButton) view.findViewById(R.id.cancelButton);
        final LinearLayout llNumber = (LinearLayout) view.findViewById(R.id.ll_number);

        okButton.setBackgroundColor(okButton.getContext().getResources().getColor(R.color.colorPrimary));
        cancelButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        okButton.setTextColor(activity.getResources().getColor(R.color.white));
        cancelButton.setTextColor(activity.getResources().getColor(R.color.white));
        SharedPreferences prefs = activity.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);
        name.setText("" + beneName);
        number.setText("" + beneNumber);
        dialog = new Dialog(activity, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        if (beneNumber != null && beneNumber.length() > 0) {
            llNumber.setVisibility(View.VISIBLE);
        } else {
            llNumber.setVisibility(View.GONE);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = activity.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);
                if (UtilMethods.INSTANCE.isNetworkAvialable(activity)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.Deletebeneficiary(activity, senderNumber, id, loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(activity, activity.getResources().getString(R.string.network_error_title),
                            activity.getResources().getString(R.string.network_error_message));
                }
            }
        });
        dialog.show();
    }

    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName;
        public TextView beneAccountNumber;
        public TextView beneBank;
        public TextView beneIFSC;

        public RelativeLayout transferLayout;
        public RelativeLayout deleteLayout;


        public MyViewHolder(View view) {
            super(view);
            beneName = (TextView) view.findViewById(R.id.beneName);
            beneAccountNumber = (TextView) view.findViewById(R.id.beneAccountNumber);
            beneBank = (TextView) view.findViewById(R.id.beneBank);
            beneIFSC = (TextView) view.findViewById(R.id.beneIFSC);
            transferLayout = (RelativeLayout) view.findViewById(R.id.transferLayout);
            deleteLayout = (RelativeLayout) view.findViewById(R.id.deleteLayout);

        }
    }


}

package com.solution.brothergroup.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.brothergroup.Api.Response.DmtReportObject;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class DMRReportAdapter extends RecyclerView.Adapter<DMRReportAdapter.MyViewHolder> {

    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;
    ProgressDialog mProgressDialog = null;
    CustomLoader loader;
    private ArrayList<DmtReportObject> transactionsList;
    private Activity mContext;
    private String status;

    public DMRReportAdapter(ArrayList<DmtReportObject> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public DMRReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dmr_report_adapter, parent, false);

        mProgressDialog = new ProgressDialog(mContext);

        return new DMRReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DMRReportAdapter.MyViewHolder holder, int position) {
        final DmtReportObject operator = transactionsList.get(position);

        // holder.status.setText("" + operator.getType_());
        holder.opening.setText("" + operator.getOpening());
        holder.amount.setText("" + operator.getRequestedAmount());
        holder.debit.setText("" + operator.getAmount());
        holder.balance.setText("" + operator.getBalance());
        holder.bankName.setText("" + operator.getOptional1());
        holder.outletName.setText("" + operator.getOutlet());
        holder.accountNumber.setText("" + operator.getAccount());
        holder.operatorName.setText("" + operator.getOperator() + "( " + operator.getOptional4() + " }");
        holder.operatorId.setText("TxID : " + operator.getTransactionID());
        holder.operatorliveId.setText("LiveID : " + operator.getLiveID());
        holder.createdDate.setText("" + operator.getEntryDate());
        holder.responseDate.setText("" + operator.getModifyDate());
        holder.senderNo.setText("SenderNo.: " + operator.getOptional2());
        holder.source.setText("Source : " + operator.getRequestMode());
        //holder.action.setText("Dispute");

        if (operator.getType_().equalsIgnoreCase("SUCCESS")) {
            status = "Success";
            holder.share.setVisibility(View.VISIBLE);
            holder.status.setText(status);
            holder.action.setVisibility(View.VISIBLE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.green));
        } else if (operator.getType_().equalsIgnoreCase("FAILED")) {
            status = "Failed";
            holder.status.setText(status);
            holder.action.setVisibility(View.GONE);
            holder.share.setVisibility(View.GONE);
            /*holder.print.setVisibility(View.GONE);*/
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.red));

        } else if (operator.getType_().equalsIgnoreCase("REFUNDED")) {
            status = "Refund";
            holder.status.setText(status);
            holder.action.setVisibility(View.GONE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.color_cyan));

        } else if (operator.getType_().equalsIgnoreCase("PENDING") || operator.getType_().equalsIgnoreCase("REQUEST SEND") || operator.getType_().equalsIgnoreCase("REQUEST SENT")) {
            status = "Pending";
            holder.status.setText(status);
            holder.action.setVisibility(View.GONE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.yellow_dark));
        } else {
            status = "other";
            holder.share.setVisibility(View.GONE);
        }

        if (!operator.getType_().equalsIgnoreCase("FAILED") && !operator.getType_().equalsIgnoreCase("PENDING")) {
            if (operator.getRefundStatus().equalsIgnoreCase("1")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("DISPUTE")) {
                    holder.action.setVisibility(View.VISIBLE);
                    holder.action.setClickable(true);
                } else {
                    holder.action.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus().equalsIgnoreCase("3")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("REFUNDED")) {
                    holder.status.setText("REFUNDED");
                    holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
                    ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.color_cyan));
                    holder.action.setVisibility(View.GONE);
                } else {
                    holder.action.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus().equalsIgnoreCase("4")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("REJECTED")) {
                    holder.status.setText("REJECTED");
                    holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
                    ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.grey));
                    holder.action.setVisibility(View.GONE);
                } else {
                    holder.action.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus().equalsIgnoreCase("2")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("UNDER REVIEW")) {
                    holder.action.setVisibility(View.VISIBLE);
                    holder.action.setText("UNDER REVIEW");
                    holder.action.setEnabled(false);
                    holder.action.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
                    ViewCompat.setBackgroundTintList(holder.action, ContextCompat.getColorStateList(mContext, R.color.grey));
                    /*holder.dispute.setVisibility(View.GONE);*/
                } else {
                    holder.action.setVisibility(View.GONE);
                }
            }
        }




       /* if (operator.getRefundStatus() !=null && operator.getRefundStatus().equalsIgnoreCase("1") && operator.get_Type().equals("2")) {
            holder.action.setVisibility(View.VISIBLE);
            holder.share.setVisibility(View.INVISIBLE);
            //Type=2 and RefundStatus=1
        } else {
            holder.action.setVisibility(View.GONE);
        }*/
        /*if (operator.get_Type().equals("2")) {
            holder.action.setVisibility(View.GONE);
            holder.share.setVisibility(View.VISIBLE);
        } else {
            holder.action.setVisibility(View.GONE);
            holder.share.setVisibility(View.GONE);
        }*/

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                    loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.RefundRequest(mContext, operator.getTid(), operator.getTransactionID(), "", loader, holder.action, "", false);
                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.err_msg_network_title), mContext.getResources().getString(R.string.err_msg_network));
                }


               /* LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dispute_popup, null);
                tilRemark = (TextInputLayout) view.findViewById(R.id.til_remark);
                etRemark = (EditText) view.findViewById(R.id.remark);
                okButton = (Button) view.findViewById(R.id.okButton);
                cancelButton = (Button) view.findViewById(R.id.cancelButton);
                final Dialog dialog = new Dialog(mContext, R.style.alert_dialog_light);
                dialog.setCancelable(false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                etRemark.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        *//*if (!validateText()) {
                            return;
                        }*//*
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String remarkText;
                        if (etRemark.getText() != null && etRemark.getText().toString().trim().length() > 0) {
                            remarkText = etRemark.getText().toString().trim();
                        } else {
                            remarkText = "";
                        }
                        if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                            loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            UtilMethods.INSTANCE.RefundRequest(mContext, operator.getTid(),operator.getTransactionID(), "",loader, holder.action,"",false);
                            dialog.dismiss();
                        } else {
                            UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.err_msg_network_title), mContext.getResources().getString(R.string.err_msg_network));
                        }

                    }
                });
                dialog.show();*/
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    CustomLoader loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    loader.show();
                    UtilMethods.INSTANCE.GetDMTReceipt(mContext, operator.getGroupID(), "Specific", loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getString(R.string.err_msg_network_title),
                            mContext.getString(R.string.err_msg_network));
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView remark, amount, bankName, status, outletName, accountNumber, source, senderNo, operatorName, operatorId, createdDate, responseDate, action, opening, debit, balance, operatorliveId;
        ImageView share;

        public MyViewHolder(View view) {
            super(view);
            amount = (TextView) view.findViewById(R.id.amount);
            opening = (TextView) view.findViewById(R.id.opening);
            debit = (TextView) view.findViewById(R.id.debit);
            balance = (TextView) view.findViewById(R.id.balance);
            status = (TextView) view.findViewById(R.id.status);
            outletName = (TextView) view.findViewById(R.id.outletName);
            accountNumber = (TextView) view.findViewById(R.id.accountNumber);
            operatorName = (TextView) view.findViewById(R.id.operatorName);
            operatorliveId = (TextView) view.findViewById(R.id.operatorliveId);
            source = (TextView) view.findViewById(R.id.source);
            senderNo = (TextView) view.findViewById(R.id.senderNo);
            operatorId = (TextView) view.findViewById(R.id.operatorId);
            createdDate = (TextView) view.findViewById(R.id.createdDate);
            responseDate = (TextView) view.findViewById(R.id.responseDate);
            action = (TextView) view.findViewById(R.id.action);
            share = (ImageView) view.findViewById(R.id.share);
            bankName = view.findViewById(R.id.bankName);
        }
    }

}

package com.solution.brothergroup.Aeps.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.SlipActivityRechargeReport;
import com.solution.brothergroup.Aeps.dto.AePsDetail;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.MyPrintDocumentAdapter;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;


public class AEPSReportAdapter extends RecyclerView.Adapter<AEPSReportAdapter.MyViewHolder> {
    String charText = "";
    private List<AePsDetail> rechargeStatus;
    private List<AePsDetail> aePsDetailList;
    private Activity mContext;
    int resourceId = 0;
    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView balance;
        public AppCompatTextView w2r;
        public AppCompatTextView lastbalance;
        public AppCompatTextView txn;
        public AppCompatTextView amount;
        public AppCompatTextView opid;
        public AppCompatTextView date, credit, comm;
        public AppCompatImageView operatorImage;
        public AppCompatTextView accountNo;
        public AppCompatTextView outlet;
        public AppCompatTextView status;
        public AppCompatTextView liveID;
        TextView dispute, source;
        ImageView Share, print;

        public MyViewHolder(View view) {
            super(view);
            txn = (AppCompatTextView) view.findViewById(R.id.txn);
            w2r= view.findViewById(R.id.w2r);
            balance = (AppCompatTextView) view.findViewById(R.id.balance);
            amount = (AppCompatTextView) view.findViewById(R.id.amount);
            source = view.findViewById(R.id.source);
            date = (AppCompatTextView) view.findViewById(R.id.date);
            accountNo = (AppCompatTextView) view.findViewById(R.id.mobile);
            outlet = (AppCompatTextView) view.findViewById(R.id.outlet);
            liveID = (AppCompatTextView) view.findViewById(R.id.liveID);
            status = (AppCompatTextView) view.findViewById(R.id.status);
            dispute = (TextView) view.findViewById(R.id.dispute);
            Share = (ImageView) view.findViewById(R.id.Share);
            print = (ImageView) view.findViewById(R.id.print);
            operatorImage = (AppCompatImageView) view.findViewById(R.id.operatorImage);
            credit = (AppCompatTextView) view.findViewById(R.id.credit);
            comm = (AppCompatTextView) view.findViewById(R.id.comm);
            /*lastbalance = (AppCompatTextView) view.findViewById(R.id.lastbalance);*/
        }
    }

    public AEPSReportAdapter(List<AePsDetail> aePsDetailList, Activity mContext) {
        this.aePsDetailList = aePsDetailList;
        this.mContext = mContext;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(aePsDetailList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aeps_report_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AePsDetail operator = aePsDetailList.get(position);
        holder.operatorImage.setVisibility(View.GONE);
        holder.w2r.setVisibility(View.GONE);
        holder.txn.setText("Tx ID :\n" + operator.getTransactionID());
        holder.balance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getBalance());
        /*holder.lastbalance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getLastBalance());*/
        holder.amount.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getRequestedAmount());
        holder.credit.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getAmount());
        holder.comm.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getCommission());
        holder.source.setText(operator.getRequestMode());
        if (operator.getCommType().equals("false")) {
            holder.comm.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.comm.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        //  holder.opid.setText(" "  + operator.getOpID());
        holder.date.setText(operator.getEntryDate());
        holder.accountNo.setText("" + operator.getAccount());
        holder.outlet.setText("" + operator.getOutlet());
        holder.liveID.setText("Live ID :" + operator.getLiveID());
        String faqsearchDescription = aePsDetailList.get(position).getType().toLowerCase(Locale.getDefault());
        String faqsearchDescription2 = aePsDetailList.get(position).getAccount().toLowerCase(Locale.getDefault());
        String faqsearchDescription3 = aePsDetailList.get(position).getOperator().toLowerCase(Locale.getDefault());
        if (faqsearchDescription.contains(charText)) {
            int startPos = faqsearchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(aePsDetailList.get(position).getType()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.status.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.status.setText(aePsDetailList.get(position).getOperator());
        }
        if (faqsearchDescription3.contains(charText)) {
            int startPos = faqsearchDescription3.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(aePsDetailList.get(position).getOperator()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.status.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.status.setText(aePsDetailList.get(position).getOperator());
        }
        if (faqsearchDescription2.contains(charText)) {
            int startPos = faqsearchDescription2.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(aePsDetailList.get(position).getAccount()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.accountNo.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.accountNo.setText(aePsDetailList.get(position).getAccount());
        }
        String status = "";
        if (operator.getType().equalsIgnoreCase("SUCCESS")) {
            status = "Success";
            holder.status.setText(status);
            holder.dispute.setVisibility(View.VISIBLE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));


            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.green));
        } else if (operator.getType().equalsIgnoreCase("FAILED")) {
            status = "Failed";
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.red));
            holder.Share.setVisibility(View.GONE);
            holder.print.setVisibility(View.GONE);


        } else if (operator.getType().equalsIgnoreCase("REFUNDED")) {
            status = "Refund";
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.color_cyan));

        } else if (operator.getType().equalsIgnoreCase("PENDING") || operator.getType().equalsIgnoreCase("REQUEST SEND")
                || operator.getType().equalsIgnoreCase("REQUEST SENT")) {
            status = "Pending";
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.grey));
        } else {
            status = "other";

        }

        if (!operator.getType().equalsIgnoreCase("FAILED") && !operator.getType().equalsIgnoreCase("PENDING")) {
            if (operator.getRefundStatus()==1) {
                if (operator.getRefundStatus_().equalsIgnoreCase("DISPUTE")) {
                    holder.dispute.setVisibility(View.VISIBLE);
                    holder.dispute.setClickable(true);
                } else {
                    holder.dispute.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus()==3) {
                if (operator.getRefundStatus_().equalsIgnoreCase("REFUNDED")) {
                    holder.status.setText("REFUNDED");
                    holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
                    ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.color_cyan));
                    holder.dispute.setVisibility(View.GONE);
                } else {
                    holder.dispute.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus()==4) {
                if (operator.getRefundStatus_().equalsIgnoreCase("REJECTED")) {
                    holder.status.setText("REJECTED");
                    holder.status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
                    ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.grey));
                    holder.dispute.setVisibility(View.GONE);
                } else {
                    holder.dispute.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus()==2) {
                if (operator.getRefundStatus_().equalsIgnoreCase("UNDER REVIEW")) {
                    holder.dispute.setText("UNDER REVIEW");
                    holder.dispute.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rect_bal_dis));
                    ViewCompat.setBackgroundTintList(holder.dispute, ContextCompat.getColorStateList(mContext, R.color.grey));
                    holder.dispute.setVisibility(View.GONE);
                } else {
                    holder.dispute.setVisibility(View.GONE);
                }
            }
        }
        /*if (operator.getOperator() != null && operator.getOperator().length() > 0) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png").
                    apply(requestOptions).into(holder.operatorImage);
        } else {
            holder.operatorImage.setImageResource(R.drawable.ic_operator_default_icon);
        }*/

        holder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, SlipActivityRechargeReport.class);

                i.putExtra("amount", "" + operator.getRequestedAmount());
                i.putExtra("RechargeMobileNo", "" + operator.getAccount());
                i.putExtra("liveId", "" + operator.getLiveID());
                i.putExtra("OID", "" + operator.getOid());
                i.putExtra("operatorname", "" + operator.getOperator());
                i.putExtra("pdate", "" + operator.getEntryDate());
                i.putExtra("ptime", "" + operator.getModifyDate());
                i.putExtra("txid", "" + operator.getTransactionID());
                i.putExtra("txStatus", "" + operator.getType());
                i.putExtra("typerecharge", "" + operator.getType());
                i.putExtra("imageurl", "" + ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png");
                // i.putExtra("transaction_id",""+operator.getTransaction_ID());
                mContext.startActivity(i);
              /*  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Mobile Number : " + operator.getAccount() + "\n" +
                        "Operator Name : " + operator.getOperator() + "\n" +
                        "Amount : " + operator.getRequestedAmount() + "\n" +
                        "Date : " + operator.getEntryDate() + "\n" +
                        "Tx.ID : " + operator.getTransactionID() + "\n" +
                        "Live.ID : " + operator.getLiveID() + "\n" +
                        "Recharge Status :" + operator.getType_() + "\n" +
                        "Source : " + operator.getRequestMode();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));*/
            }
        });

        holder.print.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mContext, SlipActivityRechargeReport.class);

                i.putExtra("amount", "" + operator.getRequestedAmount());
                i.putExtra("RechargeMobileNo", "" + operator.getAccount());
                i.putExtra("liveId", "" + operator.getLiveID());
                i.putExtra("OID", "" + operator.getOid());
                i.putExtra("operatorname", "" + operator.getOperator());
                i.putExtra("pdate", "" + operator.getEntryDate());
                i.putExtra("ptime", "" + operator.getModifyDate());
                i.putExtra("txid", "" + operator.getTransactionID());
                i.putExtra("txStatus", "" + operator.getType());
                i.putExtra("typerecharge", "" + operator.getType());
                i.putExtra("imageurl", "" + ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png");
                // i.putExtra("transaction_id",""+operator.getTransaction_ID());

                mContext.startActivity(i);

            }
        });
        holder.dispute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dispute_popup, null);
                tilRemark = (TextInputLayout) view.findViewById(R.id.til_remark);
                etRemark = (EditText) view.findViewById(R.id.remark);
                okButton = (Button) view.findViewById(R.id.okButton);
                cancelButton = (Button) view.findViewById(R.id.cancelButton);
                final Dialog dialog = new Dialog(mContext);
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
                        /*if (!validateText()) {
                            return;
                        }*/
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

                            CustomLoader loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            UtilMethods.INSTANCE.RefundRequest(mContext, String.valueOf(operator.getTid()),operator.getTransactionID(), remarkText,loader,
                                    holder.dispute,"",false);

                            dialog.dismiss();
                        } else {
                            UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.err_msg_network_title),
                                    mContext.getResources().getString(R.string.err_msg_network));
                        }

                    }
                });
                dialog.show();
            }
        });

       /* if (operator.getIsWTR()!=null && operator.getIsWTR().equalsIgnoreCase("true")){
            if (operator.get_Type().equalsIgnoreCase("1")
                    || operator.get_Type().equalsIgnoreCase("2")
                    || operator.get_Type().equalsIgnoreCase("5")){
                holder.w2r.setVisibility(View.VISIBLE);
            } else {
                holder.w2r.setVisibility(View.GONE);
            }
        }else {
            holder.w2r.setVisibility(View.GONE);
        }*/

        holder.w2r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vi = inflater.inflate(R.layout.dialog_tv, null);

                final Toolbar toolbar= (Toolbar) vi.findViewById(R.id.toolbar);
                final TextInputEditText RightAccount = (TextInputEditText) vi.findViewById(R.id.RightAccount);
                RightAccount.setInputType(InputType.TYPE_CLASS_TEXT);
                final Button submitButton = (Button) vi.findViewById(R.id.submitButton);
                final Button cancelButton = (Button) vi.findViewById(R.id.cancelButton);
                final Dialog dialog = new Dialog(mContext);
                dialog.setCancelable(false);
                dialog.setContentView(vi);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                toolbar.setTitle("Make W2R Request");
                toolbar.setTitleTextColor(Color.WHITE);

                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!RightAccount.getText().toString().isEmpty()){
                            CustomLoader loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            UtilMethods.INSTANCE.MakeW2RRequest(mContext, String.valueOf(operator.getTid()),operator.getTransactionID(),RightAccount.getText().toString() ,loader, holder.w2r);
                            dialog.dismiss();
                        } else {
                            RightAccount.setError("Enter Password");
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    /*private boolean validateText() {
        if (etRemark.getText().toString().trim().isEmpty()) {
            tilRemark.setError(mContext.getString(R.string.err_msg_text));
            okButton.setEnabled(false);
            return false;
        } else {
            okButton.setEnabled(true);
        }
        return true;
    }*/

    @Override
    public int getItemCount() {
        return aePsDetailList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveandprint(String string) {
        MyPrintDocumentAdapter pd = new MyPrintDocumentAdapter(mContext, string);
        pd.printDocument(string);
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        aePsDetailList.clear();
        if (charText.length() == 0) {
            aePsDetailList.addAll(rechargeStatus);
        } else {
            for (AePsDetail wp : rechargeStatus) {
                if (wp.getType().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getAccount().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText)) {
                    aePsDetailList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

package com.solution.brothergroup.Adapter;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.solution.brothergroup.Activities.FundOrderPendingActivity;
import com.solution.brothergroup.Api.Object.FundRequestForApproval;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;
import java.util.List;


public class FundOrderPendingListAdapter extends RecyclerView.Adapter<FundOrderPendingListAdapter.MyViewHolder> implements Filterable {

    private List<FundRequestForApproval> listItem;
    private List<FundRequestForApproval> filterListItem;

    private Activity mContext;
    private AlertDialog alertDialogFundTransfer;


    public FundOrderPendingListAdapter(Activity mContext, List<FundRequestForApproval> mFundRequestForApprovalLists) {
        this.listItem = mFundRequestForApprovalLists;
        filterListItem = mFundRequestForApprovalLists;
        this.mContext = mContext;
    }

    @Override
    public FundOrderPendingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fund_order_pending_adapter, parent, false);

        return new FundOrderPendingListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundOrderPendingListAdapter.MyViewHolder holder, final int position) {
        final FundRequestForApproval mItem = filterListItem.get(position);
        holder.balanceTv.setText("\u20B9 " + mItem.getAmount());
        holder.dateTv.setText(mItem.getEntryDate());
        holder.acHolderNameTv.setText(mItem.getAccountHolder());
        holder.acNoTv.setText("[ " + mItem.getAccountNo() + " ]");

        holder.bankTv.setText(mItem.getBank());
        holder.mobileTv.setText(mItem.getMobileNo());
        holder.userNameTv.setText(mItem.getUserName());
        holder.userMobileTv.setText("[ " + mItem.getUserMobile() + " ]");
        holder.modeTv.setText(mItem.getMode());

        holder.transIdTv.setText(mItem.getTransactionId());
        holder.statusTv.setText(mItem.getStatus());
        if (mItem.getCardNumber() != null && !mItem.getCardNumber().isEmpty()) {
            holder.cardNoView.setVisibility(View.VISIBLE);
            holder.cardNoTv.setText(mItem.getCardNumber());
        } else {
            holder.cardNoView.setVisibility(View.GONE);
        }
        if (mItem.getChequeNo() != null && !mItem.getChequeNo().isEmpty()) {
            holder.chequeView.setVisibility(View.VISIBLE);
            holder.chequeNoTv.setText(mItem.getChequeNo());
        } else {
            holder.chequeView.setVisibility(View.GONE);
        }


        GradientDrawable bgShape = (GradientDrawable) holder.statusTv.getBackground();

        if (mItem.getStatusCode() == 0) {
            bgShape.setColor(mContext.getResources().getColor(R.color.red));
        } else {
            bgShape.setColor(mContext.getResources().getColor(R.color.darkGreen));
        }

        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRejectDialog(mItem.getUserId(), holder.userNameTv.getText().toString(), mItem.getMobileNo(), mItem.getAmount(), mItem.getPaymentId());
            }
        });

        holder.fundTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFundTransferDialog(mItem.getUserId(), holder.userNameTv.getText().toString(), mItem.getMobileNo(), mItem.getCommRate(), mItem.getAmount(), mItem.getPaymentId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    private void showFundTransferDialog(final int uId, final String name, String mobile, final double commission, String amount, final int paymentId) {
        try {
            if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogFundTransfer = dialogBuilder.create();
            alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
            alertDialogFundTransfer.setView(dialogView);


            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            LinearLayout changeTypeView = dialogView.findViewById(R.id.changetTypeView);
            changeTypeView.setVisibility(View.GONE);
            final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
            amountTv.setVisibility(View.VISIBLE);
            amountEt.setVisibility(View.GONE);

            AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
            final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
            final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
            if (UtilMethods.INSTANCE.getDoubleFactorPref(mContext)) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
            nameTv.setText(name);
            mobileTv.setText(mobile);
            commisionTv.setText(commission + "");


            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(mContext);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            SwitchCompat actCreditSwitch= dialogView.findViewById(R.id.actCreditSwitch);
            if(LoginDataResponse.isAccountStatement()){
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            }else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            if (LoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
            }

            try {
                double amountVal = Double.parseDouble(amount);

                double calculatedVal = amountVal + ((amountVal * commission) / 100);
                amountTxtTv.setText("Transferable Amount is \u20B9 " + calculatedVal);
            } catch (NumberFormatException nfe) {

            }
            amountTv.setText(amount);


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                        pinPassEt.setError("Please Enter Pin Password");
                        pinPassEt.requestFocus();
                        return;
                    }
                    ((FundOrderPendingActivity) mContext).fundTransferApi(pinPassEt.getText().toString(),actCreditSwitch.isChecked(), paymentId, uId, remarksEt.getText().toString(), amountTv.getText().toString(), name, alertDialogFundTransfer);
                }
            });


            alertDialogFundTransfer.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    private void showRejectDialog(final int uId, final String name, String mobile, String amount, final int paymentId) {
        try {
            if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogFundTransfer = dialogBuilder.create();
            alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
            alertDialogFundTransfer.setView(dialogView);


            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(mContext);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            SwitchCompat actCreditSwitch= dialogView.findViewById(R.id.actCreditSwitch);
            if(LoginDataResponse.isAccountStatement()){
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            }else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            if (LoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
            }
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            LinearLayout changeTypeView = dialogView.findViewById(R.id.changetTypeView);
            changeTypeView.setVisibility(View.GONE);

            final AppCompatTextView titleTv = dialogView.findViewById(R.id.titleTv);
            titleTv.setText("Fund Reject Form");
            final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
            final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
            if (UtilMethods.INSTANCE.getDoubleFactorPref(mContext)) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }
            amountTv.setVisibility(View.VISIBLE);
            amountEt.setVisibility(View.GONE);

            AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
            commisionTv.setVisibility(View.GONE);
            AppCompatTextView commisionTitleTv = dialogView.findViewById(R.id.commisionTitleTv);
            commisionTitleTv.setVisibility(View.GONE);
            final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
            amountTxtTv.setVisibility(View.GONE);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
            nameTv.setText(name);
            mobileTv.setText(mobile);

            amountTv.setText(amount);


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });
            fundTransferBtn.setText("Reject");
            fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                        pinPassEt.setError("Please Enter Pin Password");
                        pinPassEt.requestFocus();
                        return;
                    }
                    ((FundOrderPendingActivity) mContext).rejectApi(pinPassEt.getText().toString(),actCreditSwitch.isChecked(),
                            paymentId, uId, remarksEt.getText().toString(), amountTv.getText().toString(), name, alertDialogFundTransfer);
                }
            });


            alertDialogFundTransfer.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

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
                    ArrayList<FundRequestForApproval> filteredList = new ArrayList<>();
                    for (FundRequestForApproval row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMobileNo().toLowerCase().contains(charString.toLowerCase()) || row.getUserMobile().toLowerCase().contains(charString.toLowerCase()) || row.getUserName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getAmount().toLowerCase().contains(charString.toLowerCase()) || row.getBank().toLowerCase().contains(charString.toLowerCase()) || row.getAccountHolder().toLowerCase().contains(charString.toLowerCase())
                                || row.getMode().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<FundRequestForApproval>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mainView;
        private LinearLayout outletNameView;
        private AppCompatTextView userNameTv;
        private LinearLayout mobileView;
        private AppCompatTextView mobileTv;
        private LinearLayout bankView;
        private AppCompatTextView bankTv;
        private LinearLayout transIdView;
        private AppCompatTextView transIdTv;
        private LinearLayout acHolderView;
        private AppCompatTextView acHolderNameTv, acNoTv, userMobileTv;
        private LinearLayout chequeView;
        private AppCompatTextView chequeNoTv;
        private LinearLayout cardNoView;
        private AppCompatTextView cardNoTv;
        private AppCompatImageView calanderIcon;
        private AppCompatTextView dateTv;
        private FrameLayout rightView;
        private AppCompatTextView statusTv;
        private AppCompatTextView balanceTv;
        private RelativeLayout bottomView;
        private AppCompatButton rejectBtn;
        private AppCompatButton fundTransferBtn;
        private AppCompatTextView modeTv;


        public MyViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.mainView);
            outletNameView = view.findViewById(R.id.outletNameView);
            userNameTv = view.findViewById(R.id.userNameTv);
            mobileView = view.findViewById(R.id.mobileView);
            mobileTv = view.findViewById(R.id.mobileTv);
            bankView = view.findViewById(R.id.bankView);
            bankTv = view.findViewById(R.id.bankTv);
            userMobileTv = view.findViewById(R.id.userMobileTv);
            transIdView = view.findViewById(R.id.transIdView);
            transIdTv = view.findViewById(R.id.transIdTv);
            acHolderView = view.findViewById(R.id.acHolderView);
            acHolderNameTv = view.findViewById(R.id.acHolderNameTv);
            acNoTv = view.findViewById(R.id.acNoTv);
            chequeView = view.findViewById(R.id.chequeView);
            chequeNoTv = view.findViewById(R.id.chequeNoTv);
            cardNoView = view.findViewById(R.id.cardNoView);
            cardNoTv = view.findViewById(R.id.cardNoTv);
            calanderIcon = view.findViewById(R.id.calanderIcon);
            dateTv = view.findViewById(R.id.dateTv);
            rightView = view.findViewById(R.id.rightView);
            statusTv = view.findViewById(R.id.statusTv);
            balanceTv = view.findViewById(R.id.balanceTv);
            bottomView = view.findViewById(R.id.bottomView);
            rejectBtn = view.findViewById(R.id.rejectBtn);
            fundTransferBtn = view.findViewById(R.id.fundTransferBtn);
            modeTv = view.findViewById(R.id.modeTv);
        }
    }

}

package com.solution.brothergroup.AppUser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.WalletTypeAdapter;
import com.solution.brothergroup.Api.Object.AscReport;
import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.AppUser.Activity.FOSAccStmtAndCollActivity;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FOSAccSmtReportAdapter extends RecyclerView.Adapter<FOSAccSmtReportAdapter.MyViewHolder> {


    private ArrayList<AscReport> mFilterList = new ArrayList<>();
    private Activity mContext;

    CustomLoader loader;

    FundTransferCallBAck mFundTransferCallBAck;
    private boolean oType;

    LoginResponse mLoginDataResponse;
    private AlertDialog alertDialogFundTransfer;
    private int walletType;


    public FOSAccSmtReportAdapter(ArrayList<AscReport> transactionsList, Context mContext, CustomLoader loader, LoginResponse mLoginDataResponse,
                                  FundTransferCallBAck mFundTransferCallBAck) {
        this.mContext = (Activity) mContext;
        this.mFilterList = transactionsList;
        this.mFundTransferCallBAck = mFundTransferCallBAck;

        this.mLoginDataResponse = mLoginDataResponse;
        this.loader = loader;
    }

    public interface FundTransferCallBAck {
        void onSucessFund();


    }

    @Override
    public FOSAccSmtReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_fosaccledger_report, parent, false);

        return new FOSAccSmtReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FOSAccSmtReportAdapter.MyViewHolder holder, int position) {
        final AscReport operator = mFilterList.get(position);


        holder.openingValue.setText(operator.getOpening() + "");
        holder.purchaseValue.setText(operator.getPurchase() + "");
        holder.salesValue.setText(operator.getSales() + "");
        holder.cCollectionValue.setText(operator.getcCollection() + "");
        holder.closingValue.setText(operator.getClosing() + "");
        holder.outletNameValue.setText(operator.getOutletName() + "");
        holder.mobileValue.setText(operator.getMobile() + "");
        holder.lsaleValue.setText(operator.getLsale() + "");
        holder.lCollectionValue.setText(operator.getlCollection() + "");
        holder.lsDateValue.setText(operator.getLsDate() + "");
        holder.lcDateValue.setText(operator.getLcDate() + "");
        holder.balanceValue.setText(operator.getBalance() + "");
        //    holder.uBalanceValue.setText(operator.getuBalance()+ "");


        //   Log.d(getClass().getSimpleName(),"USER_ID : "+userList.get(position).getId());

        holder.fundTransferView.setOnClickListener(v -> {

            if (UtilMethods.INSTANCE.getIsFlatCommission(mContext)) {

                UtilMethods.INSTANCE.GeUserCommissionRate((Activity) mContext, mFilterList.get(position).getUserID(), loader, mLoginDataResponse, object -> {
                    BasicResponse mBasicResponse = (BasicResponse) object;
                    showFundTransferDialog(mFilterList.get(position).getRoleID(), mFilterList.get(position).getUserID(),
                            holder.outletNameValue.getText().toString(), mFilterList.get(position).getMobile(), mBasicResponse.getCommRate());


                });
            } else {

                showFundTransferDialog(mFilterList.get(position).getRoleID(), mFilterList.get(position).getUserID(), holder.outletNameValue.getText().toString(), mFilterList.get(position).getMobile(), mFilterList.get(position).getCommission());

            }
        });


        holder.collectionView.setOnClickListener(v -> {


            if (mContext instanceof FOSAccStmtAndCollActivity) {
                ((FOSAccStmtAndCollActivity) mContext).FosCollectionActivity(operator);
            }


        });
    }


    private void showFundTransferDialog(int roleId, final int uId, final String name, String mobile,
                                        final double commission) {
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
            LinearLayout changetTypeView = dialogView.findViewById(R.id.changetTypeView);
            final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
            amountTv.setVisibility(View.GONE);
            amountEt.setVisibility(View.VISIBLE);
            //  AppCompatTextView commisionTv = dialogView.findViewById(R.id.commisionTv);
            AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);

            final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);

            final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
            if (UtilMethods.INSTANCE.getDoubleFactorPref(mContext)) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }

            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);
            SwitchCompat actCreditSwitch = dialogView.findViewById(R.id.actCreditSwitch);
            if (mLoginDataResponse.isAccountStatement()) {
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            } else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            if (mLoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
                oType = false;
            }


            View walletTypeView = dialogView.findViewById(R.id.walletTypeView);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            WalletTypeResponse mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(mContext), WalletTypeResponse.class);

            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                ArrayList<WalletType> mWalletTypesList = new ArrayList<>();
                for (WalletType mWalletType : mWalletTypeResponse.getWalletTypes()) {
                    if (mWalletType.getInFundProcess()) {
                        if (roleId == 3) {
                            if (mWalletType.getId() == 6) {
                                if (mWalletType.isPackageDedectionForRetailor()) {
                                    mWalletTypesList.add(mWalletType);
                                }
                            } else {
                                mWalletTypesList.add(mWalletType);
                            }
                        } else {
                            mWalletTypesList.add(mWalletType);
                        }


                    }
                }
                if (mWalletTypesList.size() <= 2 && creditDebitView.getVisibility() == View.VISIBLE) {
                    changetTypeView.setOrientation(LinearLayout.HORIZONTAL);
                } else {
                    changetTypeView.setOrientation(LinearLayout.VERTICAL);
                }

                walletTypeView.setVisibility(View.GONE);
                recyclerView.setAdapter(new WalletTypeAdapter(mContext, mWalletTypesList, new WalletTypeAdapter.onClick() {
                    @Override
                    public void onClick(int id) {
                        walletType = id;
                    }
                }));
            } else {
                walletTypeView.setVisibility(View.VISIBLE);
            }


            nameTv.setText(name);
            mobileTv.setText(mobile);
            commisionTv.setText(commission + "");
            oType = false;
            walletType = 1;

            prepaidTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prepaidTv.setBackgroundResource(R.drawable.rounded_light_green);
                    prepaidTv.setTextColor(Color.WHITE);
                    utilityTv.setBackgroundResource(0);
                    utilityTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
                    walletType = 1;
                }
            });

            utilityTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utilityTv.setBackgroundResource(R.drawable.rounded_light_green);
                    utilityTv.setTextColor(Color.WHITE);
                    prepaidTv.setBackgroundResource(0);
                    prepaidTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
                    walletType = 2;
                }
            });

            creditTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creditTv.setBackgroundResource(R.drawable.rounded_light_red);
                    creditTv.setTextColor(Color.WHITE);
                    debitTv.setBackgroundResource(0);
                    debitTv.setTextColor(mContext.getResources().getColor(R.color.red));
                    oType = false;
                }
            });

            debitTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    debitTv.setBackgroundResource(R.drawable.rounded_light_red);
                    debitTv.setTextColor(Color.WHITE);
                    creditTv.setBackgroundResource(0);
                    creditTv.setTextColor(mContext.getResources().getColor(R.color.red));
                    oType = true;
                }
            });


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
                    if (amountEt.getText().toString().isEmpty()) {
                        amountEt.setError(mContext.getResources().getString(R.string.err_empty_field));
                        amountEt.requestFocus();
                        return;
                    } else if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                        pinPassEt.setError("Please Enter Pin Password");
                        pinPassEt.requestFocus();
                        return;
                    }
                    loader.show();
                    UtilMethods.INSTANCE.fundTransferApi(mContext, actCreditSwitch.isChecked(), pinPassEt.getText().toString(), oType,
                            uId, remarksEt.getText().toString(), walletType, amountEt.getText().toString(), name, alertDialogFundTransfer,
                            loader, new UtilMethods.FundTransferCallBAck() {
                                @Override
                                public void onSucessFund() {
                                    if (mFundTransferCallBAck != null) {
                                        mFundTransferCallBAck.onSucessFund();
                                    }
                                }

                                @Override
                                public void onSucessEdit() {

                                }


                            });
                }
            });

            amountEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        try {
                            long amountVal = Long.parseLong(s.toString());

                            double calculatedVal = amountVal + ((amountVal * commission) / 100);
                            amountTxtTv.setText("Transferable Amount is \u20B9 " + calculatedVal);
                        } catch (NumberFormatException nfe) {

                        }
                    }
                }
            });

            alertDialogFundTransfer.show();
            alertDialogFundTransfer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    // Filter Class
    public void filter(String str) {
        ArrayList<AscReport> newList = new ArrayList<>();
        mFilterList = newList;
        mFilterList.addAll(newList);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView openingTv, openingValue, purchaseTv, purchaseValue, salesTv, salesValue, cCollectionTv, cCollectionValue,
                closingTv, closingValue, outletNameTv, outletNameValue, mobileTv, mobileValue, lsaleTv, lsaleValue, lCollectionTv, lCollectionValue, lsDateTv,
                lsDateValue, lcDateTv, lcDateValue, balanceTv, balanceValue, uBalanceTv, uBalanceValue;
        View remarkView;
        private View fundTransferView, collectionView;


        public MyViewHolder(View view) {
            super(view);

            openingTv = view.findViewById(R.id.openingTv);
            openingValue = view.findViewById(R.id.openingValue);
            purchaseTv = view.findViewById(R.id.purchaseTv);
            purchaseValue = view.findViewById(R.id.purchaseValue);
            salesTv = view.findViewById(R.id.salesTv);
            salesValue = view.findViewById(R.id.salesValue);
            cCollectionTv = view.findViewById(R.id.cCollectionTv);
            cCollectionValue = view.findViewById(R.id.cCollectionValue);
            closingTv = view.findViewById(R.id.closingTv);
            closingValue = view.findViewById(R.id.closingValue);
            outletNameTv = view.findViewById(R.id.outletNameTv);
            outletNameValue = view.findViewById(R.id.outletNameValue);
            mobileTv = view.findViewById(R.id.mobileTv);
            mobileValue = view.findViewById(R.id.mobileValue);
            lsaleTv = view.findViewById(R.id.lsaleTv);
            lsaleValue = view.findViewById(R.id.lsaleValue);
            lCollectionTv = view.findViewById(R.id.lCollectionTv);
            lCollectionValue = view.findViewById(R.id.lCollectionValue);
            lsDateTv = view.findViewById(R.id.lsDateTv);
            lsDateValue = view.findViewById(R.id.lsDateValue);
            lcDateTv = view.findViewById(R.id.lcDateTv);
            lcDateValue = view.findViewById(R.id.lcDateValue);
            balanceTv = view.findViewById(R.id.balanceTv);
            balanceValue = view.findViewById(R.id.balanceValue);
            uBalanceTv = view.findViewById(R.id.uBalanceTv);
            uBalanceValue = view.findViewById(R.id.uBalanceValue);
            remarkView = view.findViewById(R.id.remarkView);
            fundTransferView = view.findViewById(R.id.fundTransferView);
            collectionView = view.findViewById(R.id.collectionView);

        }
    }
}

package com.solution.brothergroup.AppUser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.WalletTypeAdapter;
import com.solution.brothergroup.Api.Object.AscReport;
import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.AppUser.Activity.AccountStatementServiceWiseActivity;
import com.solution.brothergroup.AppUser.Activity.FosCollectionActivity;
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

public class FOSChannelReportAdapter extends RecyclerView.Adapter<FOSChannelReportAdapter.MyViewHolder> implements Filterable {


    private int walletType = 1;
    double calculatedTransferAmtVal = 0;
    private ArrayList<AscReport> mFilterList = new ArrayList<>();
    private ArrayList<AscReport> mList = new ArrayList<>();
    private Activity mContext;
    LoginResponse loginPrefResponse;
    CustomLoader loader;
    private boolean oType;
    private AlertDialog alertDialogFundTransfer;
    FundTransferCallBAck mFundTransferCallBAck;
    String filterFromDate;
    String filterToDate;
    public FOSChannelReportAdapter(String filterFromDate, String filterToDate,ArrayList<AscReport> transactionsList, CustomLoader loader, LoginResponse loginPrefResponse, Context mContext, FundTransferCallBAck mFundTransferCallBAck) {
        this.mContext = (Activity) mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;
        this.mFundTransferCallBAck = mFundTransferCallBAck;
        this.loader = loader;
        this.loginPrefResponse = loginPrefResponse;
        this.filterFromDate = filterFromDate;
        this.filterToDate = filterToDate;
    }

    public void setDate(String filterFromDate, String filterToDate) {
        this.filterFromDate = filterFromDate;
        this.filterToDate = filterToDate;
    }
    @Override
    public FOSChannelReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_fos_channel_report, parent, false);

        return new FOSChannelReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FOSChannelReportAdapter.MyViewHolder holder, int position) {
        final AscReport operator = mFilterList.get(position);


        holder.openingValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getOpening() + ""));
        // holder.purchaseValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPurchase() + ""));
        holder.salesValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getSales() + ""));
        holder.cCollectionValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getcCollection() + ""));
        holder.closingValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getClosing() + ""));
        holder.outletNameValue.setText(operator.getOutletName() + "");
        holder.mobileValue.setText(operator.getMobile() + "");
        holder.lsaleValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getLsale() + ""));
        holder.lCollectionValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getlCollection() + ""));
        holder.lsDateValue.setText(operator.getLsDate() + "");
        holder.lcDateValue.setText(operator.getLcDate() + "");
        if (operator.isPrepaid()) {
            holder.balanceValue.setVisibility(View.VISIBLE);
        } else {
            holder.balanceValue.setVisibility(View.GONE);
        }
        if (operator.isUtility()) {
            holder.uBalanceValue.setVisibility(View.VISIBLE);
        } else {
            holder.uBalanceValue.setVisibility(View.GONE);
        }
        holder.balanceValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));
        holder.uBalanceValue.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getuBalance() + ""));



        //   Log.d(getClass().getSimpleName(),"USER_ID : "+userList.get(position).getId());

        holder.fundTransferView.setOnClickListener(v -> {

            if (UtilMethods.INSTANCE.getIsFlatCommission(mContext)) {

                UtilMethods.INSTANCE.GeUserCommissionRate((Activity) mContext, operator.getUserID(), loader, loginPrefResponse, object -> {
                    BasicResponse mBasicResponse = (BasicResponse) object;
                    showFundTransferDialog(operator.getRoleID(), operator.getUserID(),
                            holder.outletNameValue.getText().toString(), operator.getMobile(), mBasicResponse.getCommRate());


                });
            } else {

                showFundTransferDialog(operator.getRoleID(), operator.getUserID(), holder.outletNameValue.getText().toString(), operator.getMobile(), operator.getCommission());

            }
        });
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AccountStatementServiceWiseActivity.class);
                intent.putExtra("Mobile", operator.getMobile() + "");
                intent.putExtra("ServiceId", 0);
                intent.putExtra("FilterFromDate", filterFromDate);
                intent.putExtra("FilterToDate", filterToDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
        holder.infoSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AccountStatementServiceWiseActivity.class);
                intent.putExtra("Mobile", operator.getMobile() + "");
                intent.putExtra("ServiceId", 4);
                intent.putExtra("FilterFromDate", filterFromDate);
                intent.putExtra("FilterToDate", filterToDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
        holder.infoCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AccountStatementServiceWiseActivity.class);
                intent.putExtra("Mobile", operator.getMobile() + "");
                intent.putExtra("ServiceId", 44);
                intent.putExtra("FilterFromDate", filterFromDate);
                intent.putExtra("FilterToDate", filterToDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });

        holder.collectionView.setOnClickListener(v -> {

            if (mFilterList != null) {

                Intent i = new Intent(mContext, FosCollectionActivity.class);
                i.putExtra("userID", operator.getUserID() + "");
                i.putExtra("outletName", operator.getOutletName() + "");
                i.putExtra("mobile", operator.getMobile() + "");
                mContext.startActivity(i);
            }
        });
    }

    public interface FundTransferCallBAck {
        void onSucessFund();

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

                    UtilMethods.INSTANCE.fundTransferApi(mContext,actCreditSwitch.isChecked(),pinPassEt.getText().toString(), oType, uId,
                            remarksEt.getText().toString(), walletType, amountEt.getText().toString(), name,
                            alertDialogFundTransfer, loader,new UtilMethods.FundTransferCallBAck() {
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
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterList = mList;
                } else {
                    ArrayList<AscReport> filteredList = new ArrayList<>();
                    for (AscReport row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getMobile() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getOutletName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBalance() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getArea() + "").toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row);
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<AscReport>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView /*openingTv,*/ openingValue, /*purchaseTv,*/ /*purchaseValue,*/ /*salesTv, */
                salesValue, /*cCollectionTv,*/
                cCollectionValue,
        /*closingTv,*/ closingValue, /*outletNameTv,*/
                outletNameValue,/* mobileTv,*/
                mobileValue, /*lsaleTv, */
                lsaleValue,
        /*lCollectionTv,*/ lCollectionValue,/* lsDateTv,*/
                lsDateValue, /*lcDateTv,*/
                lcDateValue, /*balanceTv,*/
                balanceValue, /*uBalanceTv,*/
                uBalanceValue;
        View remarkView, info, infoCol, infoSale;
        private View fundTransferView, collectionView;


        public MyViewHolder(View view) {
            super(view);

            /*openingTv = view.findViewById(R.id.openingTv);*/
            openingValue = view.findViewById(R.id.openingValue);
            /*purchaseTv = view.findViewById(R.id.purchaseTv);*/
            /*purchaseValue = view.findViewById(R.id.purchaseValue);*/
            /*salesTv = view.findViewById(R.id.salesTv);*/
            salesValue = view.findViewById(R.id.salesValue);
            /*cCollectionTv = view.findViewById(R.id.cCollectionTv);*/
            cCollectionValue = view.findViewById(R.id.cCollectionValue);
            /*closingTv = view.findViewById(R.id.closingTv);*/
            closingValue = view.findViewById(R.id.closingValue);
            /*outletNameTv = view.findViewById(R.id.outletNameTv);*/
            outletNameValue = view.findViewById(R.id.outletNameValue);
            /*mobileTv = view.findViewById(R.id.mobileTv);*/
            mobileValue = view.findViewById(R.id.mobileValue);
            /*lsaleTv = view.findViewById(R.id.lsaleTv);*/
            lsaleValue = view.findViewById(R.id.lsaleValue);
            /*lCollectionTv = view.findViewById(R.id.lCollectionTv);*/
            lCollectionValue = view.findViewById(R.id.lCollectionValue);
            /*lsDateTv = view.findViewById(R.id.lsDateTv);*/
            lsDateValue = view.findViewById(R.id.lsDateValue);
            /*lcDateTv = view.findViewById(R.id.lcDateTv);*/
            lcDateValue = view.findViewById(R.id.lcDateValue);
            /*balanceTv = view.findViewById(R.id.balanceTv);*/
            balanceValue = view.findViewById(R.id.balanceValue);
            /*uBalanceTv = view.findViewById(R.id.uBalanceTv);*/
            uBalanceValue = view.findViewById(R.id.uBalanceValue);
            remarkView = view.findViewById(R.id.remarkView);
            fundTransferView = view.findViewById(R.id.fundTransferView);
            collectionView = view.findViewById(R.id.collectionView);
            info = view.findViewById(R.id.info);
            infoCol = view.findViewById(R.id.infoCol);
            infoSale = view.findViewById(R.id.infoSale);

        }
    }
}

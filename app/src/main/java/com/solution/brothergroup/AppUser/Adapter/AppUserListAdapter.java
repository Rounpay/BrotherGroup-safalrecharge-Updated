package com.solution.brothergroup.AppUser.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.WalletTypeAdapter;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.AppUser.Activity.AppUserListActivity;
import com.solution.brothergroup.Api.Object.BalanceData;
import com.solution.brothergroup.Api.Object.UserList;
import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.Api.Request.FundTransferRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.AppUser.AppUserListFragment;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.UpgradePacakge.ui.UpgradePackage;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.AutoBillingUpdateAppRequest;
import com.solution.brothergroup.Util.AutoBillingUpdateAppResponse;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppUserListAdapter extends RecyclerView.Adapter<AppUserListAdapter.MyViewHolder> implements Filterable {

    FundTransferCallBAck mFundTransferCallBAck;
    AutoBillingCallBAck autoBillingCallBAck;
    private ArrayList<UserList> listItem;
    private ArrayList<UserList> filterListItem;
    private Activity mContext;
    private AlertDialog alertDialogFundTransfer, alertDialogAutoBilling;
    private boolean oType;
    private int walletType = 1;
    AppUserListFragment appUserListFragment;
    BalanceData mBalanceData;
    boolean isAccountStatement;
    CustomLoader loader;
    LoginResponse mLoginResponse;

    public AppUserListAdapter(Activity mContext, ArrayList<UserList> mUserLists, FundTransferCallBAck mFundTransferCallBAck,
                              AutoBillingCallBAck autoBillingCallBAck, BalanceData mBalanceData, boolean isAccountStatement,
                              AppUserListFragment appUserListFragment, CustomLoader loader, LoginResponse mLoginResponse) {
        this.listItem = mUserLists;
        this.filterListItem = mUserLists;
        this.mFundTransferCallBAck = mFundTransferCallBAck;
        this.autoBillingCallBAck = autoBillingCallBAck;
        this.isAccountStatement = isAccountStatement;
        this.mContext = mContext;
        this.loader = loader;
        this.mLoginResponse = mLoginResponse;
        this.mBalanceData = mBalanceData;
        this.appUserListFragment = appUserListFragment;
    }

    @Override
    public AppUserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_app_user, parent, false);

        return new AppUserListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppUserListAdapter.MyViewHolder holder, final int position) {
        final UserList mItem = filterListItem.get(position);
        if (mItem.getBalanceTypes() != null && mItem.getBalanceTypes().size() > 0) {
            holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mItem.getBalanceTypes()));
        } else {
            ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
            if (mBalanceData.isBalance()) {
                mBalanceTypes.add(new BalanceType("Prepaid Balance", mItem.getBalance()));
            }
            if (mBalanceData.isUBalance()) {
                mBalanceTypes.add(new BalanceType("Utiity Balance", mItem.getuBalance()));
            }
            if (mBalanceData.isBBalance()) {
                mBalanceTypes.add(new BalanceType("Bank Balance", mItem.getbBalance()));
            }
            if (mBalanceData.isCBalance()) {
                mBalanceTypes.add(new BalanceType("Card Balance", mItem.getcBalance()));
            }
            if (mBalanceData.isIDBalance()) {
                mBalanceTypes.add(new BalanceType("ID Balance", mItem.getIdBalnace()));
            }
            if (mBalanceData.isPacakgeBalance() &&
                    (mItem.getRoleID() != 3 ||
                            !mBalanceData.isPackageDeducionForRetailor() &&
                                    mItem.getRoleID() == 3)) {
                mBalanceTypes.add(new BalanceType("Package Balance", mItem.getPacakgeBalance()));
            }

            if (isAccountStatement) {
                mBalanceTypes.add(new BalanceType("Outstanding Balance", mItem.getOsBalance()));
            }
            mItem.setBalanceTypes(mBalanceTypes);
            holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mBalanceTypes));
        }


        if (mItem.getRoleID() == 3) {
            holder.upgradeBtn.setVisibility(View.VISIBLE);
        } else {
            holder.upgradeBtn.setVisibility(View.GONE);
        }
        if (mItem.getRoleID() == 8 && isAccountStatement) {
            holder.collectionBtn.setVisibility(View.VISIBLE);
        } else {
            holder.collectionBtn.setVisibility(View.GONE);
        }

        holder.balanceTv.setText("\u20B9 " + mItem.getBalance());
        holder.dateTv.setText(mItem.getJoinDate());
        holder.joinByTv.setText(mItem.getJoinBy());

        if (mItem.getSlab() != null) {
            if (mItem.getSlab().contains("Level")) {
                holder.slabView.setVisibility(View.GONE);
            } else {
                holder.slabView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.slabView.setVisibility(View.GONE);
        }

        holder.slabTv.setText(mItem.getSlab() + "");
        holder.mobileTv.setText(mItem.getMobileNo());
        holder.ouyletNameTv.setText(mItem.getOutletName() + "[ " + mItem.getPrefix() + mItem.getId() + " ]");

        holder.kycStatusTv.setText("KYC\n" + mItem.getKycStatusStr());
        GradientDrawable bgShape = (GradientDrawable) holder.kycStatusTv.getBackground();

        if (mItem.getKycStatus() == 1) {
            bgShape.setColor(mContext.getResources().getColor(R.color.grey));
        } else if (mItem.getKycStatus() == 2) {
            bgShape.setColor(mContext.getResources().getColor(R.color.colorlink));
        } else if (mItem.getKycStatus() == 3) {
            bgShape.setColor(mContext.getResources().getColor(R.color.darkGreen));
        } else if (mItem.getKycStatus() == 4) {
            bgShape.setColor(mContext.getResources().getColor(R.color.color_amber));
        } else {
            bgShape.setColor(mContext.getResources().getColor(R.color.red));
        }
        if (mItem.isOTP()) {
            holder.otpStatusTv.setBackgroundResource(R.drawable.rounded_green);
            holder.otpStatusTv.setText("OTP Enable");
        } else {
            holder.otpStatusTv.setBackgroundResource(R.drawable.rounded_red);
            holder.otpStatusTv.setText("OTP Disable");
        }
        if (mItem.isStatus()) {
            holder.activeSwitch.setChecked(true);
            holder.activeTv.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.activeTv.setText("Active");
        } else {
            holder.activeSwitch.setChecked(false);
            holder.activeTv.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.activeTv.setText("Inactive");
        }


        //////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////

        if (UtilMethods.INSTANCE.getIsAutoBilling(mContext)) {
            holder.autoBillingLL.setVisibility(View.VISIBLE);
            holder.autobillingsettingIV.setVisibility(View.VISIBLE);

            if (mItem.isAutoBilling) {
                holder.autoBillingSwitch.setChecked(true);
                holder.autoBillingTv.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.autoBillingTv.setText("Active AB");
                holder.autobillingsettingIV.setVisibility(View.VISIBLE);
            } else {
                holder.autoBillingSwitch.setChecked(false);
                holder.autoBillingTv.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.autoBillingTv.setText("Inactive AB");
                holder.autobillingsettingIV.setVisibility(View.GONE);
            }

        } else {
            holder.autoBillingLL.setVisibility(View.GONE);
            holder.autobillingsettingIV.setVisibility(View.GONE);
        }


        holder.autoBillingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           /*     int balanceforABint = Integer.valueOf(mItem.getBalanceForAB());
                //   int maxbillingcountint= Integer.valueOf(mItem.getMaxBillingCountAB());
                //  int maxcreditamtint= Integer.valueOf(mItem.getMaxCreditLimitAB());
                int maxtranferamtint = Integer.valueOf(mItem.getMaxTransferLimitAB());*/


                if (holder.autoBillingSwitch.isChecked()) {
                    holder.autoBillingSwitch.setChecked(false);
                    holder.autoBillingTv.setText("Inactive AB");
                    holder.autoBillingTv.setTextColor(mContext.getResources().getColor(R.color.red));

                    autobillingsubmitAPi(
                            1,
                            null,
                            null,
                            false,
                            mItem.getId(),
                            mItem.getMaxBillingCountAB() + "",
                            mItem.getBalanceForAB() + "",
                            mItem.getMaxCreditLimitAB() + "",
                            mItem.getMaxTransferLimitAB() + "",
                            alertDialogAutoBilling

                    );
                    ((AppUserListActivity) mContext).appUserChildRolesApi();


                } else {

                    /*holder.autoBillingSwitch.setChecked(true);
                    holder.autoBillingTv.setTextColor(mContext.getResources().getColor(R.color.green));
                    holder.autoBillingTv.setText("Active AB");*/


                    int balanceforABint = Integer.valueOf(mItem.getBalanceForAB());
                    //   int maxbillingcountint= Integer.valueOf(mItem.getMaxBillingCountAB());
                    int maxcreditamtint = Integer.valueOf(mItem.getMaxCreditLimitAB());
                    //  int maxtranferamtint = Integer.valueOf(mItem.getMaxTransferLimitAB());

                    // if (balanceforABint >= 1000 && maxcreditamtint >= 5000){

                    if ((mItem.getBalanceForAB() != 0) && mItem.getMaxTransferLimitAB() != 0) {
                        autobillingsubmitAPi(
                                1,
                                null,
                                null,
                                true,
                                mItem.getId(),
                                mItem.getMaxBillingCountAB() + "",
                                mItem.getBalanceForAB() + "",
                                mItem.getMaxCreditLimitAB() + "",
                                mItem.getMaxTransferLimitAB() + "",
                                alertDialogAutoBilling

                        );
                        ((AppUserListActivity) mContext).appUserChildRolesApi();

                        //    Toast.makeText(mContext, "66", Toast.LENGTH_SHORT).show();

                    } else {
                        showAutoBillingDialog(
                                holder.autoBillingSwitch, holder.autoBillingTv,
                                mItem.isAutoBilling(),
                                mItem.getId(),
                                mItem.getMaxBillingCountAB() + "",
                                mItem.getBalanceForAB() + "",
                                mItem.getMaxCreditLimitAB() + "",
                                mItem.getMaxTransferLimitAB() + "");


                        //  Toast.makeText(mContext, "99", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

        holder.autobillingsettingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAutoBillingDialogsetting(
                     /*   holder.autoBillingSwitch, holder.autoBillingTv,
                        mItem.getAutoBilling(),*/
                        mItem.isAutoBilling(),
                        mItem.getId(),
                        mItem.getMaxBillingCountAB() + "",
                        mItem.getBalanceForAB() + "",
                        mItem.getMaxCreditLimitAB() + "",
                        mItem.getMaxTransferLimitAB() + "");

            }
        });


        holder.collectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUserListFragment.DistributorCollectionFromFosActivity(mItem);

            }
        });
        holder.upgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpgradePackage.class);
                mContext.startActivity(intent);
            }
        });
        holder.fundTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.getIsFlatCommission(mContext)) {

                    UtilMethods.INSTANCE.GeUserCommissionRate((Activity) mContext, mItem.getId(), loader, mLoginResponse, object -> {
                        BasicResponse mBasicResponse = (BasicResponse) object;
                        showFundTransferDialog(mItem.getRoleID(), mItem.getId(), holder.ouyletNameTv.getText().toString(), mItem.getMobileNo(), mBasicResponse.getCommRate());

                    });
                } else {

                    showFundTransferDialog(mItem.getRoleID(), mItem.getId(), holder.ouyletNameTv.getText().toString(), mItem.getMobileNo(), mItem.getCommRate());
                }

            }
        });


        holder.switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusApi("", mItem.getId(), position, holder.activeSwitch, holder.activeTv, holder.ouyletNameTv.getText().toString());
            }
        });

    }


    private void showAutoBillingDialogsetting(/*final SwitchCompat abSwitchCompat, final TextView activeText, Boolean autoBilling,*/Boolean autoBilling, int uid, String gtmaxBillingContAB, String gtbalanceforAB, String gtmaxCreditLimitAB, String gtmaxttranferLimitAB) {

        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogAutoBilling = dialogBuilder.create();
        alertDialogAutoBilling.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = mContext.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_auto_billing, null);
        alertDialogAutoBilling.setView(dialogView);

        AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
        AppCompatTextView autobillingsummitBtn = dialogView.findViewById(R.id.autobillingsummitBtn);
        AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);

        AppCompatEditText maxBillingCountAB = dialogView.findViewById(R.id.amountAutoBillingEt);
        AppCompatEditText alertbalanceForAB = dialogView.findViewById(R.id.alertAmountEt);
        AppCompatEditText maxCreditLimitAB = dialogView.findViewById(R.id.maxCreditAmountEt);
        AppCompatEditText maxTransferLimitAB = dialogView.findViewById(R.id.maxTransferAmountEt);

        if (gtmaxBillingContAB != null || !gtmaxBillingContAB.isEmpty()) {
            maxBillingCountAB.setText(gtmaxBillingContAB);
        }

        if (gtbalanceforAB != null || !gtbalanceforAB.isEmpty()) {//alertAmount
            alertbalanceForAB.setText(gtbalanceforAB);
        }

        if (gtmaxCreditLimitAB != null || !gtmaxCreditLimitAB.isEmpty()) {
            maxCreditLimitAB.setText(gtmaxCreditLimitAB);
        }

        if (gtmaxttranferLimitAB != null || !gtmaxttranferLimitAB.isEmpty()) {//maxtranferAmount
            maxTransferLimitAB.setText(gtmaxttranferLimitAB);
        }


        autobillingsummitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String et1 = maxBillingCountAB.getText().toString();
                String et2 = alertbalanceForAB.getText().toString();
                String et3 = maxCreditLimitAB.getText().toString();
                String et4 = maxTransferLimitAB.getText().toString();

                if ((!TextUtils.isEmpty(et1)) && (!TextUtils.isEmpty(et2)) && (!TextUtils.isEmpty(et3)) && (!TextUtils.isEmpty(et4))) {

                    autobillingsubmitAPi(
                            1,
                            null,
                            null,
                            autoBilling,
                            uid,
                            maxBillingCountAB.getText().toString(),
                            alertbalanceForAB.getText().toString(),
                            maxCreditLimitAB.getText().toString(),
                            maxTransferLimitAB.getText().toString(),
                            alertDialogAutoBilling
                    );
                } else {
                    TextUtils.isEmpty(et1);
                    TextUtils.isEmpty(et2);
                    TextUtils.isEmpty(et3);
                    TextUtils.isEmpty(et4);
                }


                //hereall
                // if(mContext instanceof AppUserListFragment){
                ((AppUserListActivity) mContext).appUserChildRolesApi();

                alertDialogAutoBilling.dismiss();

            }
        });


        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAutoBilling.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAutoBilling.dismiss();
            }
        });

        alertDialogAutoBilling.show();
        alertDialogAutoBilling.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    private void showAutoBillingDialog(final SwitchCompat abSwitchCompat, final TextView activeText, Boolean autoBilling, int uid, String gtmaxBillingContAB, String gtbalanceforAB, String gtmaxCreditLimitAB, String gtmaxttranferLimitAB) {

        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogAutoBilling = dialogBuilder.create();
        alertDialogAutoBilling.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = mContext.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_auto_billing, null);
        alertDialogAutoBilling.setView(dialogView);

        AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
        AppCompatTextView autobillingsummitBtn = dialogView.findViewById(R.id.autobillingsummitBtn);
        AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);

        AppCompatEditText maxBillingCountAB = dialogView.findViewById(R.id.amountAutoBillingEt);
        AppCompatEditText alertbalanceForAB = dialogView.findViewById(R.id.alertAmountEt);
        AppCompatEditText maxCreditLimitAB = dialogView.findViewById(R.id.maxCreditAmountEt);
        AppCompatEditText maxTransferLimitAB = dialogView.findViewById(R.id.maxTransferAmountEt);

        if (gtmaxBillingContAB != null || !gtmaxBillingContAB.isEmpty()) {
            maxBillingCountAB.setText(gtmaxBillingContAB);
        }

        if (gtbalanceforAB != null || !gtbalanceforAB.isEmpty()) {//alertAmount
            alertbalanceForAB.setText(gtbalanceforAB);
        }

        if (gtmaxCreditLimitAB != null || !gtmaxCreditLimitAB.isEmpty()) {
            maxCreditLimitAB.setText(gtmaxCreditLimitAB);
        }

        if (gtmaxttranferLimitAB != null || !gtmaxttranferLimitAB.isEmpty()) {//maxtranferAmount
            maxTransferLimitAB.setText(gtmaxttranferLimitAB);
        }


        autobillingsummitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            /*    int balanceforABint = Integer.valueOf(gtbalanceforAB);
                //   int maxbillingcountint= Integer.valueOf(mItem.getMaxBillingCountAB());
                //  int maxcreditamtint= Integer.valueOf(mItem.getMaxCreditLimitAB());
                int maxtranferamtint = Integer.valueOf(gtmaxttranferLimitAB);



                if(balanceforABint>= 1000 &&
                        maxtranferamtint >=5000){


*/

                if (abSwitchCompat.isChecked()) {
                    abSwitchCompat.setChecked(false);
                    activeText.setText("Inactive AB");
                    activeText.setTextColor(mContext.getResources().getColor(R.color.red));

                    autobillingsubmitAPi(
                            1,
                            abSwitchCompat,
                            activeText,
                            false,
                            uid,
                            maxBillingCountAB.getText().toString(),
                            alertbalanceForAB.getText().toString(),
                            maxCreditLimitAB.getText().toString(),
                            maxTransferLimitAB.getText().toString(),
                            alertDialogAutoBilling
                    );


                } else {
                    abSwitchCompat.setChecked(true);
                    activeText.setTextColor(mContext.getResources().getColor(R.color.green));
                    activeText.setText("Active AB");

                    autobillingsubmitAPi(
                            1,
                            abSwitchCompat,
                            activeText,
                            true,
                            uid,
                            maxBillingCountAB.getText().toString(),
                            alertbalanceForAB.getText().toString(),
                            maxCreditLimitAB.getText().toString(),
                            maxTransferLimitAB.getText().toString(),
                            alertDialogAutoBilling
                    );


                }

                ((AppUserListActivity) mContext).appUserChildRolesApi();



              /*  ((AppUserListActivity) mContext).autobillingsubmitAPi(
                        autoBilling,
                        uid,
                        maxBillingCountAB.getText().toString(),
                        alertbalanceForAB.getText().toString(),
                        maxCreditLimitAB.getText().toString(),
                        maxTransferLimitAB.getText().toString()
                        , alertDialogAutoBilling, new AppUserListActivity.autobillingsubmitAPiCallBAck() {
                            @Override
                            public void onSucess() {
                                if (autoBillingCallBAck != null) {
                                    autoBillingCallBAck.onSucessFund();
                                 //   ((AppUserListFragment)mContext).appUserListApi();

                                }
                            }
                        });*/

              /*  }else {
                    Toast.makeText(mContext, "Values are lesser", Toast.LENGTH_SHORT).show();
                }*/

                alertDialogAutoBilling.dismiss();

            }
        });


        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAutoBilling.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAutoBilling.dismiss();
            }
        });

        alertDialogAutoBilling.show();
        alertDialogAutoBilling.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    @Override
    public int getItemCount() {
        return filterListItem.size();
    }


    public void autobillingsubmitAPi(final int flag, final SwitchCompat abSwitchCompat, final TextView activeText, Boolean autoBilling, int uid, String maxBillingCountAB, String balanceForAB, String maxCreditLimitAB, String maxTransferLimitAB,
                                     final AlertDialog alertDialogAutoBilling/*, final AppUserListActivity.autobillingsubmitAPiCallBAck mautobillingsubmitAPiCallBAck*/) {
        try {
            // loader.show();

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AutoBillingUpdateAppResponse> call = git.AutoBillingUpdateApp(new AutoBillingUpdateAppRequest(
                    uid
                    , (flag == 1) ? autoBilling : null
                    /*autoBilling*/
                    , maxBillingCountAB
                    , balanceForAB
                    , false
                    , maxCreditLimitAB
                    , maxTransferLimitAB
                    , mLoginResponse.getData().getUserID()
                    , mLoginResponse.getData().getSessionID()
                    , mLoginResponse.getData().getSession()
                    , ApplicationConstant.INSTANCE.APP_ID
                    , UtilMethods.INSTANCE.getIMEI(mContext)
                    , UtilMethods.getFCMRegKey(mContext)
                    , BuildConfig.VERSION_NAME
                    , UtilMethods.INSTANCE.getSerialNo(mContext)
                    , mLoginResponse.getData().getLoginTypeID()));


            call.enqueue(new Callback<AutoBillingUpdateAppResponse>() {
                @Override
                public void onResponse(Call<AutoBillingUpdateAppResponse> call, Response<AutoBillingUpdateAppResponse> response) {
                    if (response.body() != null) {

                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {

                            UtilMethods.INSTANCE.Successful(mContext, response.body().getMsg());
                        } else if (response.body().getStatuscode() == "-1") {

                            UtilMethods.INSTANCE.Error(mContext, response.body().getMsg());
                        } else if (response.body().getStatuscode() == "0") {

                            UtilMethods.INSTANCE.Error(mContext, response.body().getMsg());
                        } else {
                            //  Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();
                            UtilMethods.INSTANCE.Error(mContext, response.body().getMsg());
                        }


                    } else {
                        // Toast.makeText(mContext, "Failuree", Toast.LENGTH_SHORT).show();
                        UtilMethods.INSTANCE.Error(mContext, response.body().getMsg());
                    }


                }

                @Override
                public void onFailure(Call<AutoBillingUpdateAppResponse> call, Throwable t) {
                    //  Toast.makeText(mContext, "Failuree", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            // Toast.makeText(mContext, "4444444", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            //UtilMethods.INSTANCE.Error(mContext, R.string.getString(R.string.some_thing_error));
        }

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

            SwitchCompat actCreditSwitch= dialogView.findViewById(R.id.actCreditSwitch);
            if(mLoginResponse.isAccountStatement()){
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            }else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            if (mLoginResponse.getData().isCanDebit()) {
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

                    ((AppUserListActivity) mContext).fundTransferApi(actCreditSwitch.isChecked(),pinPassEt.getText().toString(), oType, uId, remarksEt.getText().toString(), walletType, amountEt.getText().toString(), name, alertDialogFundTransfer, new AppUserListActivity.FundTransferCallBAck() {
                        @Override
                        public void onSucess() {
                            if (mFundTransferCallBAck != null) {
                                mFundTransferCallBAck.onSucessFund();
                            }
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

    public void changeStatusApi(String securityKey, int uid, final int position,
                                final SwitchCompat switchView, final TextView activeText, final String name) {
        try {


            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.ChangeUserStatus(new FundTransferRequest(false,securityKey,
                    false, uid, "", 0, 0, "", mLoginResponse.getData().getUserID(), mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(mContext),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(mContext), mLoginResponse.getData().getSessionID(),
                    mLoginResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(mContext, data.getMsg().replace("{User}", name));
                            if (switchView.isChecked()) {
                                switchView.setChecked(false);
                                activeText.setText("Inactive");
                                activeText.setTextColor(mContext.getResources().getColor(R.color.red));
                                filterListItem.get(position).setStatus(false);
                                for (int i = 0; i < listItem.size(); i++) {
                                    if (listItem.get(i).getId() == filterListItem.get(position).getId()) {
                                        listItem.get(i).setStatus(false);
                                        break;
                                    }
                                }
                            } else {
                                switchView.setChecked(true);
                                activeText.setTextColor(mContext.getResources().getColor(R.color.green));
                                activeText.setText("Active");
                                filterListItem.get(position).setStatus(true);
                                for (int i = 0; i < listItem.size(); i++) {
                                    if (listItem.get(i).getId() == filterListItem.get(position).getId()) {
                                        listItem.get(i).setStatus(true);
                                        break;
                                    }
                                }
                            }
                            notifyDataSetChanged();
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                        } else {

                            UtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title), mContext.getResources().getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(mContext, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {

                        UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
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
                    ArrayList<UserList> filteredList = new ArrayList<>();
                    for (UserList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMobileNo().toLowerCase().contains(charString.toLowerCase()) || row.getOutletName().toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBalance() + "").toLowerCase().contains(charString.toLowerCase()) || row.getSlab().toLowerCase().contains(charString.toLowerCase()) || row.getJoinBy().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<UserList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface FundTransferCallBAck {
        void onSucessFund();

        void onSucessEdit();
    }

    public interface AutoBillingCallBAck {
        void onSucessFund();


        void onSucessEdit();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainView;
        private LinearLayout outletNameView;
        private AppCompatTextView ouyletNameTv;
        private LinearLayout mobileView;
        private AppCompatTextView mobileTv;
        private LinearLayout slabView;
        private AppCompatTextView slabTv;
        private LinearLayout joinByView, switchView;
        private AppCompatTextView joinByTv;
        private AppCompatImageView calanderIcon;
        private AppCompatTextView dateTv;
        private LinearLayout rightView;
        private SwitchCompat activeSwitch;
        private AppCompatTextView activeTv;
        private AppCompatTextView otpStatusTv;
        private AppCompatTextView kycStatusTv;
        private AppCompatTextView balanceTv;
        private AppCompatButton editBtn;
        private AppCompatButton fundTransferBtn, collectionBtn, upgradeBtn;
        private SwitchCompat autoBillingSwitch;
        private AppCompatTextView autoBillingTv;
        private LinearLayout autoBillingLL;
        private AppCompatImageView autobillingsettingIV;
        RecyclerView balRecycerView;

        public MyViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.mainView);
            outletNameView = view.findViewById(R.id.outletNameView);
            ouyletNameTv = view.findViewById(R.id.ouyletNameTv);
            mobileView = view.findViewById(R.id.mobileView);
            mobileTv = view.findViewById(R.id.mobileTv);
            slabView = view.findViewById(R.id.slabView);
            slabTv = view.findViewById(R.id.slabTv);
            joinByView = view.findViewById(R.id.joinByView);
            joinByTv = view.findViewById(R.id.joinByTv);
            calanderIcon = view.findViewById(R.id.calanderIcon);
            dateTv = view.findViewById(R.id.dateTv);
            rightView = view.findViewById(R.id.rightView);
            activeSwitch = view.findViewById(R.id.activeSwitch);
            activeTv = view.findViewById(R.id.activeTv);
            otpStatusTv = view.findViewById(R.id.otpStatusTv);
            kycStatusTv = view.findViewById(R.id.kycStatusTv);
            balanceTv = view.findViewById(R.id.balanceTv);
            editBtn = view.findViewById(R.id.editBtn);
            switchView = view.findViewById(R.id.switchView);
            fundTransferBtn = view.findViewById(R.id.fundTransferBtn);
            collectionBtn = view.findViewById(R.id.collectionBtn);
            upgradeBtn = view.findViewById(R.id.upgradeBtn);
            autoBillingSwitch = view.findViewById(R.id.autoBillingSwitch);
            autoBillingTv = view.findViewById(R.id.autoBillingTv);
            autoBillingLL = view.findViewById(R.id.autoBillingLL);
            autobillingsettingIV = view.findViewById(R.id.autobillingsettingIV);
            balRecycerView = view.findViewById(R.id.balRecycerView);
            balRecycerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
    }
}

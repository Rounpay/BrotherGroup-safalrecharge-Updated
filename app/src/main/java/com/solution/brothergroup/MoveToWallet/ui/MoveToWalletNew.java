package com.solution.brothergroup.MoveToWallet.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Object.SlabRangeDetail;
import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.Api.Request.SlabRangeDetailRequest;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.SlabRangeDetailResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.Adapter.WalletBalanceAdapter;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.MoveToWallet.Adapter.MoveToWalletAdapter;
import com.solution.brothergroup.MoveToWallet.Adapter.SettlementChargeDetailAdapter;
import com.solution.brothergroup.MoveToWallet.dto.MoveToWalletRequest;
import com.solution.brothergroup.MoveToWallet.dto.TransactionMode;
import com.solution.brothergroup.MoveToWallet.dto.TransactionModeResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoveToWalletNew extends AppCompatActivity {
    Spinner spinMoveTo, spinTransactionMode, spinSource, spinDestination;
    RecyclerView walletBalance;
    public LinearLayout ll_TransactionMode;
    public TextView chargesTv;
    CustomLoader loader;
    EditText amount;
    Button submit;
    String actiontype = "1";
    String TransMode = "NEFT";
    Map<String, Integer> hmForTransactionMode = new HashMap<>();
    int selectedOid = 0;
    ArrayList<TransactionMode> transactionModesList;
    ArrayList<OperatorList> transactionModesOPList = new ArrayList<>();
    ArrayList<BalanceType> balanceTypes = new ArrayList<>();
    ArrayList<String> moveToArrayList = new ArrayList<>();
    private WalletTypeResponse mWalletTypeResponse;
    HashMap<String, Integer> walletIdMap = new HashMap<>();
    HashMap<Integer, ArrayList<WalletType>> mMapDestObject = new HashMap<>();

    ArrayList<WalletType> moveToArrayListSelectSource = new ArrayList<>();
    private List<WalletType> walletTypesList = new ArrayList<>();
    private int mtwid;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_to_wallet_new);
        setToolbar();
        getIds();
        balanceTypes = (ArrayList<BalanceType>) getIntent().getSerializableExtra("items");
        gettingMoveToDataselectsource(getIntent().getExtras().getString("items", ""));


        //gettingMoveToData();
        adaptDataInRecyclerView(walletBalance);
        mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(this), WalletTypeResponse.class);
        setWalletIds();
        setListeners();
    }

    void setWalletIds() {

        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {

            for (WalletType object : mWalletTypeResponse.getWalletTypes()) {
                for (String objectWallet : moveToArrayList) {
                    if (objectWallet.contains(object.getName())) {
                        walletIdMap.put(objectWallet, object.getId());
                    }
                }
            }

        } else {
            UtilMethods.INSTANCE.WalletType(MoveToWalletNew.this, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    mWalletTypeResponse = (WalletTypeResponse) object;
                    setWalletIds();
                }
            });
        }
    }




    private void gettingMoveToDataselectsource(String items) {

        String responseWalletType = UtilMethods.INSTANCE.getWalletType(this);
        if (responseWalletType != null) {
            mWalletTypeResponse = new Gson().fromJson(responseWalletType, WalletTypeResponse.class);
            if (mWalletTypeResponse != null) {
                walletTypesList = mWalletTypeResponse.getMoveToWalletMappings();
                if (walletTypesList != null && walletTypesList.size() > 0) {
                    ArrayList<WalletType> moveToSelectDestinationStrList = new ArrayList<>();
                    ArrayList<Integer> addedFromId = new ArrayList<>();
                    for (WalletType item : walletTypesList) {
                        if (!addedFromId.contains(item.getFromWalletID())) {
                            moveToArrayListSelectSource.add(item);
                            addedFromId.add(item.getFromWalletID());
                            moveToSelectDestinationStrList = new ArrayList<>();
                        }

                        moveToSelectDestinationStrList.add(item);
                        mMapDestObject.put(item.getFromWalletID(), moveToSelectDestinationStrList);

                    }
                    adaptDataInSpinner(spinSource, moveToArrayListSelectSource, true);

                }
            }
        }


    }


    private void setListeners() {

        spinSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WalletType clickedItem = (WalletType) parent.getItemAtPosition(position);
                int fromWalletId = clickedItem.getFromWalletID();
                if (mMapDestObject != null && mMapDestObject.containsKey(fromWalletId)) {
                    adaptDataInSpinner(spinDestination, mMapDestObject.get(fromWalletId), false);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WalletType clickedItem = (WalletType) parent.getItemAtPosition(position);
                mtwid = clickedItem.getId();

                if (clickedItem.getToWalletID() == 3) {
                    //GetTransactionMode();
                    getOperator(42);
                } else {
                    ll_TransactionMode.setVisibility(View.GONE);
                    chargesTv.setVisibility(View.GONE);
                    selectedOid = 0;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinTransactionMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransMode = spinTransactionMode.getSelectedItem().toString();
                if (TransMode != null && TransMode.length() > 0 && hmForTransactionMode != null) {
                    selectedOid = hmForTransactionMode.get(TransMode);
                    chargesTv.setVisibility(View.VISIBLE);

                } else {
                    selectedOid = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (amount.getText().toString().isEmpty()) {
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UtilMethods.INSTANCE.isNetworkAvialable(MoveToWalletNew.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    MoveToWallet(MoveToWalletNew.this, actiontype, TransMode, selectedOid+"", amount.getText().toString());
                } else {
                    UtilMethods.INSTANCE.NetworkError(MoveToWalletNew.this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }

            }
        });
        chargesTv.setOnClickListener(view -> HitChargeDetail(this));
    }

    public void HitChargeDetail(Activity context) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            //loader.setCancelable(false);
            //loader.setCanceledOnTouchOutside(false);


            try {
                String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
                LoginResponse mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                Call<SlabRangeDetailResponse> call = git.RealTimeCommission(new SlabRangeDetailRequest(selectedOid,
                        mLoginDataResponse.getData().getUserID() + "", mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this), "", BuildConfig.VERSION_NAME,
                        UtilMethods.INSTANCE.getSerialNo(this), mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
                call.enqueue(new Callback<SlabRangeDetailResponse>() {

                    @Override
                    public void onResponse(Call<SlabRangeDetailResponse> call, Response<SlabRangeDetailResponse> response) {

                        try {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            if (response.isSuccessful()) {

                                if (response.body() != null) {
                                    if (response.body().getStatuscode() == 1) {
                                        if (response.body().getData() != null && response.body().getData().size() > 0) {

                                            chargesDialog(response.body().getData());
                                        } else {
                                            UtilMethods.INSTANCE.Error(context, "Slab Range Data not found.");
                                        }

                                    } else {
                                        if (response.body().getVersionValid() == false) {
                                            UtilMethods.INSTANCE.versionDialog(context);
                                        } else {
                                            if (response.body().getMsg() != null && !response.body().getMsg().isEmpty()) {
                                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                            } else {
                                                UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                                            }
                                        }
                                    }

                                } else {
                                    UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                                }
                            } else {
                                UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            UtilMethods.INSTANCE.Error(context, e.getMessage() + "");
                        }

                    }

                    @Override
                    public void onFailure(Call<SlabRangeDetailResponse> call, Throwable t) {

                        try {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            UtilMethods.INSTANCE.apiFailureError(context, t);
                            /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                        } catch (IllegalStateException ise) {
                            UtilMethods.INSTANCE.Error(context, ise.getMessage());
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader != null) {
                    if (loader.isShowing()) {
                        loader.dismiss();
                    }
                }
                UtilMethods.INSTANCE.Error(context, e.getMessage() + "");
            }


        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void chargesDialog(ArrayList<SlabRangeDetail> mSlabRangeDetail) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_settlement_charge_details, null);
            alertDialog.setView(dialogView);
            View maxCommView, fixedCommView;
            ImageView closeView = dialogView.findViewById(R.id.iv_cancleView);
            ImageView opImage = dialogView.findViewById(R.id.iv_opImage);
            TextView opName = dialogView.findViewById(R.id.tv_opName);
            TextView opRange = dialogView.findViewById(R.id.tv_opRange);
            maxCommView = dialogView.findViewById(R.id.maxCommView);
            fixedCommView = dialogView.findViewById(R.id.fixedCommView);
                            /*maxCommView.setVisibility(View.GONE);
                            fixedCommView.setVisibility(View.GONE);*/

            RecyclerView slabRangeRecyclerView = dialogView.findViewById(R.id.rv_slabRange);
            slabRangeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            SettlementChargeDetailAdapter commissionSlabAdapter = new SettlementChargeDetailAdapter(mSlabRangeDetail, this);
            slabRangeRecyclerView.setAdapter(commissionSlabAdapter);

            /*opName.setText(operatorValue + "");
            opRange.setText("Range : " + min + " - " + max);*/

            closeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

           /* RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.mipmap.ic_launcher);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                    .apply(requestOptions)
                    .into(opImage);
*/



            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    private void adaptDataInRecyclerView(RecyclerView recyclerView) {

        if (balanceTypes != null && balanceTypes.size() > 0) {
            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(MoveToWalletNew.this, balanceTypes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }



    private void getOperator(int op_Type) {

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        NumberListResponse numberListResponse = new Gson().fromJson(response, NumberListResponse.class);
        ArrayList<String> arrayTranMode = new ArrayList<>();
        for (OperatorList op : numberListResponse.getData().getOperators()) {
            if (op.getOpType() == op_Type && op.isActive()) {
                transactionModesOPList.add(op);
                arrayTranMode.add(op.getName());
                hmForTransactionMode.put(op.getName(), op.getOid() );

            }

        }

        if (arrayTranMode.size() > 0) {
            aadaptDataInSpinner(spinTransactionMode, arrayTranMode);
            ll_TransactionMode.setVisibility(View.VISIBLE);
        }
    }

    public void adaptDataInSpinner(Spinner spinner, ArrayList<WalletType> mWalletType, boolean isSource) {

        MoveToWalletAdapter arrayAdapter = new MoveToWalletAdapter(this, mWalletType, isSource);
        spinner.setAdapter(arrayAdapter);
    }

    public void aadaptDataInSpinner(Spinner spinner, ArrayList<String> stringArray) {
        String[] stringA = new String[stringArray.size()];
        stringArray.toArray(stringA);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stringA);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void MoveToWallet(final Activity context, String actiontype, String transMode, String oid, String amount) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String mLoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
            Call<TransactionModeResponse> call = git.MoveToWallet(new MoveToWalletRequest(ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    LoginDataResponse.getData().getLoginTypeID() + "", "",
                    UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getUserID() + "",
                    BuildConfig.VERSION_NAME, actiontype, transMode, oid, amount, mtwid));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    if (response.body() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), (MoveToWalletNew) context);
                        } else {
                            if (response.body().getMsg() != null) {
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<TransactionModeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    if ((t.getMessage() + "").contains("No address associated with hostname")) {
                        UtilMethods.INSTANCE.NetworkError(MoveToWalletNew.this, "Network Error", getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(MoveToWalletNew.this, t.getMessage() + "");
                    }
                }
            });

        } catch (Exception ex) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            UtilMethods.INSTANCE.Error(MoveToWalletNew.this, ex.getMessage() + "");
        }
    }





    private void getIds() {
        // spinMoveTo = (Spinner) findViewById(R.id.spin_MoveTo);
        spinSource = (Spinner) findViewById(R.id.spin_source);
        spinDestination = (Spinner) findViewById(R.id.spin_destination);

        spinTransactionMode = (Spinner) findViewById(R.id.spin_TransactionMode);
        walletBalance = (RecyclerView) findViewById(R.id.walletBalance);
        ll_TransactionMode = (LinearLayout) findViewById(R.id.ll_TransactionMode);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        amount = (EditText) findViewById(R.id.amount);
        submit = (Button) findViewById(R.id.submit);
        chargesTv = findViewById(R.id.chargesTv);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Move To Wallet");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

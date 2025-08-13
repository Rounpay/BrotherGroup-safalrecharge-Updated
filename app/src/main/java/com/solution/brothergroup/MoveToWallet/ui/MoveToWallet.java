package com.solution.brothergroup.MoveToWallet.ui;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.Api.Request.BalanceRequest;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.Adapter.WalletBalanceAdapter;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.MoveToWallet.Adapter.MoveToWalletAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoveToWallet extends AppCompatActivity {
    Spinner spinMoveTo, spinTransactionMode ,spinSource, spinDestination;
    RecyclerView walletBalance;
    public LinearLayout ll_TransactionMode;
    public AppCompatTextView tv_maxMinCharge;
    CustomLoader loader;
    EditText amount;
    Button submit;
    String actiontype = "1";
    String TransMode = "NEFT";
    Map<String, String> hmForTransactionMode = new HashMap<>();
    String transactionCode = "";
    ArrayList<TransactionMode> transactionModesList;
    private String max, min, charge;
    ArrayList<BalanceType> balanceTypes = new ArrayList<>();
    private String[] moveToArray;
    ArrayList<String> moveToArrayList = new ArrayList<>();
    private WalletTypeResponse mWalletTypeResponse;
    HashMap<String, Integer> walletIdMap = new HashMap<>();
    HashMap<Integer, ArrayList<WalletType>> mMapDestObject = new HashMap<>();

    ArrayList<WalletType> moveToArrayListSelectSource = new ArrayList<>();
    private List<WalletType> walletTypesList = new ArrayList<>();
    private int mtwid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_move_to_wallet);
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
            UtilMethods.INSTANCE.WalletType(MoveToWallet.this,  new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    mWalletTypeResponse = (WalletTypeResponse) object;
                    setWalletIds();
                }
            });
        }
    }

    private void gettingMoveToData() {


        if (balanceTypes != null && balanceTypes.size() > 0) {
            for (int i = 0; i < balanceTypes.size(); i++) {
                if (!balanceTypes.get(i).getName().equalsIgnoreCase("Bank Wallet")) {
                    moveToArrayList.add("Move To " + balanceTypes.get(i).getName());
                }
            }
            moveToArrayList.add("Move To Bank");
        }
        aadaptDataInSpinner(spinMoveTo, moveToArrayList);
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

                if (clickedItem.getToWalletID()==3){
                    GetTransactionMode();
                }else {
                    ll_TransactionMode.setVisibility(View.GONE);
                    tv_maxMinCharge.setVisibility(View.GONE);
                    transactionCode = "";
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
                    transactionCode = hmForTransactionMode.get(TransMode);
                    max = transactionModesList.get(position).getMax();
                    min = transactionModesList.get(position).getMin();
                    charge = transactionModesList.get(position).getCharge();

                    tv_maxMinCharge.setVisibility(View.VISIBLE);
                    tv_maxMinCharge.setText("Min Amount : " + min + " Rs." + "\n" + "Max Amount : " + max + " Rs." + "\n" + "Transaction Charges for " + "'" + TransMode + "'" + " is " + charge + " Rs.");

                } else {
                    transactionCode = "";
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

                if (UtilMethods.INSTANCE.isNetworkAvialable(MoveToWallet.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    MoveToWallet(MoveToWallet.this, actiontype, transactionCode, amount.getText().toString());
                } else {
                    UtilMethods.INSTANCE.NetworkError(MoveToWallet.this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }

            }
        });
    }

    private void adaptDataInRecyclerView(RecyclerView recyclerView) {

        if (balanceTypes != null && balanceTypes.size() > 0) {
            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(MoveToWallet.this, balanceTypes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void gettingTransactionModeData(ArrayList<TransactionMode> list) {
        transactionModesList = list;
        if (transactionModesList != null && transactionModesList.size() > 0) {
            ArrayList<String> arrayTranMode = new ArrayList<>();
            for (int i = 0; i < transactionModesList.size(); i++) {
                arrayTranMode.add(transactionModesList.get(i).getTransMode());
                hmForTransactionMode.put(transactionModesList.get(i).getTransMode(), transactionModesList.get(i).getCode());
            }
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

    public void MoveToWallet(final Activity context, String actiontype, String transMode, String amount) {
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
                    BuildConfig.VERSION_NAME, actiontype, transMode, amount,mtwid));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    if (response.body() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), (MoveToWallet) context);
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
                        UtilMethods.INSTANCE.NetworkError(MoveToWallet.this, "Network Error", getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(MoveToWallet.this, t.getMessage() + "");
                    }
                }
            });

        } catch (Exception ex) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            UtilMethods.INSTANCE.Error(MoveToWallet.this, ex.getMessage() + "");
        }
    }


    public void GetTransactionMode() {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String mResponse = UtilMethods.INSTANCE.getLoginPref(MoveToWallet.this);
            LoginResponse LoginDataResponse = new Gson().fromJson(mResponse, LoginResponse.class);
            Call<TransactionModeResponse> call = git.GetTransactionMode(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(MoveToWallet.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(MoveToWallet.this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    if (response.body() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {

                            if (response.body().getTransactionModes() != null && response.body().getTransactionModes().size() > 0) {
                                gettingTransactionModeData(response.body().getTransactionModes());
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(MoveToWallet.this, response.body().getMsg() + "");
                        }
                    }
                }

                @Override
                public void onFailure(Call<TransactionModeResponse> call, Throwable t) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();

                    if ((t.getMessage() + "").contains("No address associated with hostname")) {
                        UtilMethods.INSTANCE.NetworkError(MoveToWallet.this, "Network Error", getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(MoveToWallet.this, t.getMessage() + "");
                    }

                }
            });

        } catch (Exception ex) {

            if (loader != null)
                if (loader.isShowing())
                    loader.dismiss();
            UtilMethods.INSTANCE.Error(MoveToWallet.this, ex.getMessage() + "");
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
        tv_maxMinCharge = findViewById(R.id.tv_maxMinCharge);
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

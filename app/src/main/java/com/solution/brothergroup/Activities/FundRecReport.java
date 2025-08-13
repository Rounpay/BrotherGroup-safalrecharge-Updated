package com.solution.brothergroup.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.FundRecAdapter;
import com.solution.brothergroup.Api.Response.FundDCObject;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FundRecReport extends AppCompatActivity implements View.OnClickListener {
    boolean visibleFlag = false;
    ProgressDialog mProgressDialog = null;
    RecyclerView recycler_view;
    FundRecAdapter mAdapter;
    String response = "";
    LinearLayoutManager mLayoutManager;
    ArrayList<FundDCObject> transactionsObjects = new ArrayList<>();
    RechargeReportResponse transactions = new RechargeReportResponse();
    // Declare CustomLoader.....
    CustomLoader loader;
    EditText search_all;
    int flag = 1;
    int report = 0;
    CustomFilterDialog mCustomFilterDialog;
    private Toolbar toolbar;
    private String fromDateStr, toDateStr;
    private String serviceTypeTitleStr = "Received By";
    private String filterMobieNum;
    private boolean isSelfFilter = true;
    private String filterOtherMobile;
    private String filterWalletType, filterService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.fund_dc_report);
        mCustomFilterDialog = new CustomFilterDialog(this);
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });


        //In which you need put here
        SimpleDateFormat sdfToday = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        fromDateStr = sdfToday.format(new Date());
        toDateStr = fromDateStr;


        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recycler_view = findViewById(R.id.recycler_view);
        mProgressDialog = new ProgressDialog(FundRecReport.this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Fund Credit Report");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Calendar myCalendar2 = Calendar.getInstance();
        String myFormat = "dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        report = 0;
        filterMobieNum = UtilMethods.INSTANCE.getUserMobile(this);
        filterOtherMobile = "";

        HitApi(1, 4);
    }

    private void HitApi(int WalletTypeId, int ServiceId) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            //UtilMethods.INSTANCE.FundDCReport(this, ServiceID, "0", "",mobileNo, fromdate, todate, "", "", "false", loader);
            UtilMethods.INSTANCE.FundDCReport(this, isSelfFilter, WalletTypeId, ServiceId, filterOtherMobile, fromDateStr
                    , toDateStr,
                    filterMobieNum,
                    loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(String response) {
        try {
            Gson gson = new Gson();
            transactions = gson.fromJson(response, RechargeReportResponse.class);
            transactionsObjects = transactions.getFundDCReport();
            if (transactionsObjects.size() > 0 && transactionsObjects != null) {
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter = new FundRecAdapter(transactionsObjects, FundRecReport.this, serviceTypeTitleStr);
                mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);
                search_all.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mAdapter.filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            } else {

                UtilMethods.INSTANCE.Error(this, "No Record Found ! between \n" + fromDateStr + " to " + toDateStr);
                recycler_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
       /* if (v == searchLayout) {
            report = 1;
            if (fromDate.getText().toString().isEmpty() || toDate.getText().toString().isEmpty()) {
                UtilMethods.INSTANCE.Error(this, "Please enter both Date");
                return;
            }
            if (to_Mill < from_mill) {
                UtilMethods.INSTANCE.Error(this, "From Date is not valid Date");
                return;
            }
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.FundDCReport(this, true, 1, ServiceID, "0", "0", numberSearch.getText().toString(), fromDate.getText().toString()
                        , toDate.getText().toString(),
                        "",
                        "",
                        "false", loader);
            } else {
                UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
           /* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/

            mCustomFilterDialog.openFundDCFilter(fromDateStr, toDateStr, filterMobieNum, isSelfFilter, filterOtherMobile, filterService, filterWalletType, serviceTypeTitleStr, new CustomFilterDialog.FundDebitCreditCallBack() {

                @Override
                public void onSubmitClick(String fromDate, String toDate, String mobileNo, boolean isSelf, int walletTypeId, String walletType, int serviceTypeId, String serviceType, String otherMobile) {
                    fromDateStr = fromDate;
                    toDateStr = toDate;
                    filterMobieNum = mobileNo;
                    isSelfFilter = isSelf;
                    filterOtherMobile = otherMobile;
                    filterService = serviceType;
                    filterWalletType = walletType;
                    serviceTypeTitleStr = serviceTypeId == 4 ? "Received By" : "Fund Transfered To";
                    if (serviceTypeId == 4) {
                        toolbar.setTitle("Fund Credit Report");
                    } else {
                        toolbar.setTitle("Fund Debit Report");
                    }
                    HitApi(walletTypeId, serviceTypeId);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("fund_receive")) {

            response = activityFragmentMessage.getFrom();
            dataParse(response);


        }
    }


}
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
import com.solution.brothergroup.Adapter.FundReqAdapter;
import com.solution.brothergroup.Api.Response.FundOrderReportObject;
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
import java.util.Locale;


public class FundReqReport extends AppCompatActivity {
    boolean visibleFlag = false;
    ProgressDialog mProgressDialog = null;
    RecyclerView recycler_view;
    FundReqAdapter mAdapter;
    String response = "";
    LinearLayoutManager mLayoutManager;
    ArrayList<FundOrderReportObject> transactionsObjects = new ArrayList<>();
    RechargeReportResponse transactions = new RechargeReportResponse();

    // Declare CustomLoader.....
    CustomLoader loader;
    EditText search_all;

    private Toolbar toolbar;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "", filterTransactionId = "";
    private int filterTopValue = 50;
    private String filterModeValue = "";
    private String filterStatus = "";
    private int filterStatusId, filteModeId;
    private String filterChildMobileNo = "", filterAccountNo = "";
    private String filterDateType = "";
    private String filteWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filteDateTypeId;
    private int filterChooseCriteriaId;

    private int filteWalletTypeId = 1;
    private int filterModeTypeId = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.fund_rq_report);
        mCustomFilterDialog = new CustomFilterDialog(this);
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });


        /////////////////////////////////////////////////////////////////////


        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recycler_view = findViewById(R.id.recycler_view);
        mProgressDialog = new ProgressDialog(FundReqReport.this);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Fund Order Report");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Calendar myCalendar2 = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        filterFromDate = sdf.format(myCalendar2.getTime());
        filterToDate = sdf.format(myCalendar2.getTime());
        HitApi();
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.FundOrderReport(this, filterTopValue + "",
                    filterStatusId + "",
                    filterFromDate
                    , filterToDate,
                    filterTransactionId,
                    filterAccountNo,
                    "false", filteModeId + "", "true", "", loader);
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(String response) {
        try {
            Gson gson = new Gson();
            transactions = gson.fromJson(response, RechargeReportResponse.class);
            transactionsObjects = transactions.getFundOrderReport();
            if (transactionsObjects.size() > 0 && transactionsObjects != null) {
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter = new FundReqAdapter(transactionsObjects, FundReqReport.this);
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

                UtilMethods.INSTANCE.Error(this, "No Record Found ! between \n" + filterFromDate + " to " + filterToDate);
                recycler_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

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

            mCustomFilterDialog.openDisputeFilter("FundOrder", "",
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId, filterChooseCriteria, filterEnterCriteria, filteWalletType, new CustomFilterDialog.LedgerFilterCallBack() {
                        @Override
                        public void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo, String accountNo, int topValue, int statusId, String statusValue, int dateTypeId, String dateTypeValue, int modeTypeId, String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue, int walletTypeId, String walletType) {
                            filterFromDate = fromDate;
                            filterToDate = toDate;
                            filterMobileNo = mobileNo;
                            filterTransactionId = transactionId;
                            filterChildMobileNo = childMobileNo;
                            filterAccountNo = accountNo;
                            filterTopValue = topValue;
                            filterStatusId = statusId;
                            filterStatus = statusValue;
                            filteDateTypeId = dateTypeId;
                            filterDateType = dateTypeValue;
                            filterChooseCriteriaId = chooseCriteriaId;
                            filterChooseCriteria = chooseCriteriaValue;
                            filterEnterCriteria = enterCriteriaValue;
                            filteWalletTypeId = walletTypeId;
                            filteWalletType = walletType;
                            filterModeValue = modeValue;
                            filterModeTypeId = modeTypeId;

                            HitApi();
                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
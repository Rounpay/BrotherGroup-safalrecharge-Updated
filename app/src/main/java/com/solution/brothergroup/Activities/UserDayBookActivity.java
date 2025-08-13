package com.solution.brothergroup.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.UserDayBookDMTListAdapter;
import com.solution.brothergroup.Adapter.UserDayBookListAdapter;
import com.solution.brothergroup.Api.Object.UserDaybookDMRReport;
import com.solution.brothergroup.Api.Object.UserDaybookReport;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDayBookActivity extends AppCompatActivity implements View.OnClickListener {
    boolean visibleFlag = false;
    ProgressDialog mProgressDialog = null;
    RecyclerView recycler_view;
    UserDayBookListAdapter mAdapter;
    String response = "";
    // Declare CustomLoader.....
    CustomLoader loader;
    String ServiceID = "4";
    TextView fromDate,toDate;
    EditText numberSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer, numberSearchLayout;
    LoginResponse mLoginPrefResponse;
    private Toolbar toolbar;
    private String type;
    View totalCommView;
    TextView totalSelfComm, totalTeamComm;
CustomFilterDialog mCustomFilterDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_day_book);
        type = getIntent().getStringExtra("Type");
        mCustomFilterDialog=new CustomFilterDialog(this);
        searchContainer = (RelativeLayout) findViewById(R.id.searchContainer);
        searchContainer.setVisibility(View.VISIBLE);
        totalCommView = findViewById(R.id.totalCommView);
        totalSelfComm = findViewById(R.id.totalSelfComm);
        totalTeamComm = findViewById(R.id.totalTeamComm);
        numberSearchLayout = (RelativeLayout) findViewById(R.id.numberSearchLayout);
        numberSearch = (EditText) findViewById(R.id.numberSearch);
        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);
        //In which you need put here
        SimpleDateFormat sdfToday = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        fromDate.setText(sdfToday.format(new Date()));
        toDate.setText(sdfToday.format(new Date()));
        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        mLoginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);

        if (mLoginPrefResponse.getData().getRoleID().equalsIgnoreCase("3") || mLoginPrefResponse.getData().getRoleID().equalsIgnoreCase("2")) {
            numberSearchLayout.setVisibility(View.GONE);
            totalCommView.setVisibility(View.GONE);
        } else {
            numberSearchLayout.setVisibility(View.VISIBLE);
            totalCommView.setVisibility(View.VISIBLE);
        }


        searchLayout = (RelativeLayout) findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(this);


        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCFromDate(fromDate,toDate);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCToDate(fromDate,toDate);
            }
        });
        /////////////////////////////////////////////////////////////////////


        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressDialog = new ProgressDialog(UserDayBookActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if (type.equalsIgnoreCase("UserDayBook")) {
            toolbar.setTitle("User Day Book");
        } else {
            toolbar.setTitle("User Day Book DMT");
        }
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


        HitApi();
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            String childNumber;
            if (mLoginPrefResponse.getData().getRoleID().equalsIgnoreCase("3") || mLoginPrefResponse.getData().getRoleID().equalsIgnoreCase("2")) {
                childNumber = "";
            } else {
                if (numberSearch.getText().toString().isEmpty()) {
                    childNumber = mLoginPrefResponse.getData().getMobileNo();
                } else {
                    childNumber = numberSearch.getText().toString();
                }
            }
            //UtilMethods.INSTANCE.FundDCReport(this, ServiceID, "0", "",mobileNo, fromdate, todate, "", "", "false", loader);
            if (type.equalsIgnoreCase("UserDayBook")) {
                UtilMethods.INSTANCE.UserDayBook(this, childNumber, fromDate.getText().toString(),toDate.getText().toString(), loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {

                        dataParse((AppUserListResponse) object);
                    }
                });
            } else {
                UtilMethods.INSTANCE.UserDayBookDmt(this, childNumber, fromDate.getText().toString(),toDate.getText().toString(), loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {

                        dataParseDMT((AppUserListResponse) object);
                    }
                });
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(AppUserListResponse response) {
        try {
            if (response != null) {
                List<UserDaybookReport> transactionsObjects = response.getUserDaybookReport();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {
                    if(totalCommView.getVisibility()==View.VISIBLE){
                        double selfComm = 0,teamComm = 0;
                        for(UserDaybookReport item:transactionsObjects){
                            selfComm=selfComm+ item.getSelfCommission();
                            teamComm=teamComm+ item.getTeamCommission();
                        }

                        totalSelfComm.setText("\u20B9 " + selfComm);
                        totalTeamComm.setText("\u20B9 " + teamComm);
                    }
                    recycler_view.setVisibility(View.VISIBLE);
                    mAdapter = new UserDayBookListAdapter(UserDayBookActivity.this, transactionsObjects,mLoginPrefResponse.getData().getRoleID());

                    recycler_view.setLayoutManager(new LinearLayoutManager(this));

                    recycler_view.setAdapter(mAdapter);

                } else {

                    UtilMethods.INSTANCE.Error(this, "Record not Found!");
                    recycler_view.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dataParseDMT(AppUserListResponse response) {
        try {
            if (response != null) {
                List<UserDaybookDMRReport> transactionsObjects = response.getUserDaybookDMTReport();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {
                    if(totalCommView.getVisibility()==View.VISIBLE){
                        double selfComm = 0,teamComm = 0;
                        for(UserDaybookDMRReport item:transactionsObjects){
                            selfComm=selfComm+ item.getSelfCommission();
                            teamComm=teamComm+ item.getTeamCommission();
                        }

                        totalSelfComm.setText("\u20B9 " + selfComm);
                        totalTeamComm.setText("\u20B9 " + teamComm);
                    }
                    recycler_view.setVisibility(View.VISIBLE);
                    UserDayBookDMTListAdapter mAdapter = new UserDayBookDMTListAdapter(UserDayBookActivity.this, transactionsObjects, mLoginPrefResponse.getData().getRoleID());

                    recycler_view.setLayoutManager(new LinearLayoutManager(this));

                    recycler_view.setAdapter(mAdapter);

                } else {

                    UtilMethods.INSTANCE.Error(this, "Record not Found!");
                    recycler_view.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == searchLayout) {
            if (fromDate.getText().toString().isEmpty()) {
                UtilMethods.INSTANCE.Error(this, "Please enter both Date");
                return;
            }

            HitApi();
        }
    }

    @Override
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
    }

    @Override
    public void onStart() {
        super.onStart();

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

        super.onDestroy();
    }


}

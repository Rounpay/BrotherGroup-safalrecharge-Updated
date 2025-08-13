package com.solution.brothergroup.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.LedgerReportAdapter;
import com.solution.brothergroup.Api.Object.LedgerObject;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
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

/**
 * Created by Vishnu on 10-04-2017.
 */

public class LedgerReport extends AppCompatActivity /*implements View.OnClickListener*/ {

    RecyclerView recycler_view;
    LedgerReportAdapter mAdapter;
    String response = "";
    LinearLayoutManager mLayoutManager;
    ArrayList<LedgerObject> transactionsObjects = new ArrayList<>();
    RechargeReportResponse transactions = new RechargeReportResponse();
    TextView fromDate, toDate;
    EditText numberSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer, numberSearchLayout, childSearchLayout;
    int flag = 0;
    boolean visibleFlag = false;
    EditText search_all;
    // Declare CustomLoader.....
    CustomLoader loader;
    String todatay;
    long to_Mill;
    long from_mill;
    private Toolbar toolbar;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterStatusId, filteDateTypeId;
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    int filteWalletTypeId = 1,filterModeTypeId=1;
    private String filterModeValue="";
    String filteWalletType;
    private int filterChooseCriteriaId;
    private LoginResponse loginPrefResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.report);
        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        loginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        filterChildMobileNo = loginPrefResponse.getData().getMobileNo() + "";
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        SimpleDateFormat sdfToday = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        filterFromDate = sdfToday.format(new Date());
        filterToDate = filterFromDate;
        filterMobileNo = UtilMethods.INSTANCE.getUserMobile(this);
        recycler_view = findViewById(R.id.recycler_view);
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });
        searchContainer = findViewById(R.id.searchContainer);
        childSearchLayout = findViewById(R.id.childSearchLayout);
        numberSearchLayout = findViewById(R.id.numberSearchLayout);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Ledger Report");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // OpenRechargeDialog();

        numberSearch = findViewById(R.id.numberSearch);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        searchLayout = findViewById(R.id.searchLayout);
        /* searchLayout.setOnClickListener(this);*/
        numberSearchLayout.setVisibility(View.GONE);
        childSearchLayout.setVisibility(View.GONE);


        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        todatay = sdf.format(myCalendar.getTime());
        flag = 0;
        HitApi();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromDate.setText(sdf.format(myCalendar.getTime()));
                Date date1 = myCalendar.getTime();
                from_mill = date1.getTime();
            }

        };
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LedgerReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        /////////////////////////////////////////////////////////////////////

        final Calendar myCalendarTo = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                toDate.setText(sdf.format(myCalendarTo.getTime()));
                Date date1 = myCalendarTo.getTime();
                to_Mill = date1.getTime();
            }

        };
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LedgerReport.this, dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.LedgerReport(this, "0", filterTopValue+"","0", filterFromDate, filterToDate, filteWalletTypeId, filterTransactionId, filterChildMobileNo, "false", loader);


        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(String response) {
        Gson gson = new Gson();
        transactions = gson.fromJson(response, RechargeReportResponse.class);
        transactionsObjects = transactions.getLedgerReport();
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter = new LedgerReportAdapter(transactionsObjects, LedgerReport.this);
            mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    mAdapter.filter(newText.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {

            UtilMethods.INSTANCE.Error(this, "No Record Found ! between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
        }
    }

    public void specificReportList(String response) {
        recycler_view.setVisibility(View.VISIBLE);
        dataParse(response);
    }


    /*@Override
    public void onClick(View v) {
        if (v == searchLayout) {
            flag = 1;
            if (fromDate.getText().toString().isEmpty() || toDate.getText().toString().isEmpty()) {
                UtilMethods.INSTANCE.Error(this, "Please enter both Date");
                return;
            }
            if (to_Mill<from_mill) {
                UtilMethods.INSTANCE.Error(this, "From Date is not valid Date");
                return;
            }
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);


                        UtilMethods.INSTANCE.LedgerReport(this, "0", "0", fromDate.getText().toString()
                                , toDate.getText().toString(),
                                "",
                                numberSearch.getText().toString(),
                                "false", loader);
                    } else {
                        UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                                getResources().getString(R.string.err_msg_network));
                    }

            }


    }*/

   /* @Override
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

            mCustomFilterDialog.openDisputeFilter("Ledger", loginPrefResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId, filterChooseCriteria, filterEnterCriteria, filteWalletType, new CustomFilterDialog.LedgerFilterCallBack() {
                        @Override
                        public void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo, String accountNo, int topValue, int statusId, String statusValue, int dateTypeId, String dateTypeValue,int modeTypeId,String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue, int walletTypeId, String walletType) {
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
                            filterModeValue= modeValue;
                            filterModeTypeId=modeTypeId;
                            HitApi();
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
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("ledger_respo")) {
            response = activityFragmentMessage.getFrom();
            dataParse(response);


        }
    }

    public void OpenRechargeDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewTemp = inflater.inflate(R.layout.recharge_dateselection, null);
        final TextView et_fromdate = viewTemp.findViewById(R.id.et_fromdate);
        et_fromdate.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_calendar_icon), null, null, null);
        final TextInputLayout til_fromdate = viewTemp.findViewById(R.id.til_fromdate);
        final TextView et_todate = viewTemp.findViewById(R.id.et_todate);
        et_todate.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_calendar_icon), null, null, null);
        final TextInputLayout til_todate = viewTemp.findViewById(R.id.til_todate);
        final Button okButton = viewTemp.findViewById(R.id.okButton);
        final Button cancelButton = viewTemp.findViewById(R.id.cancelButton);
        final CustomLoader loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(viewTemp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_fromdate.setText(dateFormatter.format(newDate.getTime()));
                if (et_fromdate.getText().toString().trim().isEmpty()) {
                    til_fromdate.setError(getString(R.string.err_msg_text));
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        final DatePickerDialog toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_todate.setText(dateFormatter.format(newDate.getTime()));
                if (et_todate.getText().toString().trim().isEmpty()) {
                    til_todate.setError(getString(R.string.err_msg_text));
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, Context.MODE_PRIVATE);


        et_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        et_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
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
                if (et_fromdate.getText() != null && et_todate != null && et_todate.getText().toString().trim().length() > 0
                        && et_fromdate.getText().toString().trim().length() > 0) {

                    // HitApi(et_fromdate.getText().toString(), et_todate.getText().toString());


                    dialog.dismiss();

                } else {
                    fromDate.setError("Please select From date !!");
                    fromDate.requestFocus();
                }
            }
        });
        dialog.show();
    }

}

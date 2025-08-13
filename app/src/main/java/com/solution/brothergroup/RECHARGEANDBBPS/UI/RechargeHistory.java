package com.solution.brothergroup.RECHARGEANDBBPS.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.RechargeReportAdapter;
import com.solution.brothergroup.Api.Object.RechargeStatus;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RechargeHistory extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    RelativeLayout childSearchLayout;
    String response = "";
    RechargeReportAdapter mAdapter;
    ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
    RechargeReportResponse transactions = new RechargeReportResponse();
    ImageView noData;
    TextView fromDate, toDate;
    EditText numberSearch, childSearch;
    RelativeLayout searchLayout;
    RelativeLayout searchContainer;
    EditText search_all;
    boolean visibleFlag = false;
    // Declare CustomLoader.....
    CustomLoader loader;
    String todatay;
    int flag = 0;
    long to_Mill;
    long from_mill;
    boolean isPSA;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "", filteWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterStatusId, filteDateTypeId;
    private int filterChooseCriteriaId;
    ;
    private int filteWalletTypeId = 1, filterModeTypeId = 1;
    private String filterModeValue = "";
    private Toolbar toolbar;
    private LoginResponse mLoginDataResponse;
    private boolean isRecent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        // Start loading the ad in the background.
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(isPSA ? "PSA Token History" : "Recharge Report");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        mLoginDataResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);

        isPSA = getIntent().getBooleanExtra("PSA", false);
        recycler_view = findViewById(R.id.recycler_view);
        childSearchLayout = findViewById(R.id.childSearchLayout);
        searchContainer = findViewById(R.id.searchContainer);
        search_all = findViewById(R.id.search_all);

        noData = findViewById(R.id.noData);
        // OpenRechargeDialog();
        numberSearch = findViewById(R.id.numberSearch);
        childSearch = findViewById(R.id.childSearch);
        mAdapter = new RechargeReportAdapter(transactionsObjects, this, isPSA, mLoginDataResponse.getData().getRoleID());
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
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

        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });

        if (mLoginDataResponse.getData().getRoleID().equals("3")) {
            childSearch.setVisibility(View.GONE);
        } else {
            childSearch.setVisibility(View.VISIBLE);
        }
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(this);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        flag = 0;
        todatay = sdf.format(myCalendar.getTime());
        filterFromDate = sdf.format(myCalendar.getTime());
        filterToDate = sdf.format(myCalendar.getTime());
        isRecent = true;
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
                new DatePickerDialog(RechargeHistory.this, date, myCalendar
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
                new DatePickerDialog(RechargeHistory.this, dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.RechargeReport(this, isPSA ? UtilMethods.INSTANCE.getPsaId(RechargeHistory.this) : "0", filterTopValue + "", filterStatusId,
                    filterFromDate, filterToDate, filterTransactionId, filterAccountNo, filterChildMobileNo, "false",
                    isRecent, loader, new UtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
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
                    filterFromDate = et_fromdate.getText().toString();
                    filterToDate = et_todate.getText().toString();
                    isRecent = false;
                    HitApi();


                    dialog.dismiss();

                } else {
                    fromDate.setError("Please select From date !!");
                    fromDate.requestFocus();
                }
            }
        });
        dialog.show();
    }

    public void dataParse(RechargeReportResponse mRechargeReportResponse) {

        transactions = mRechargeReportResponse;
        transactionsObjects.clear();
        transactionsObjects.addAll(transactions.getRechargeReport());

        if (transactionsObjects.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();


        } else {
            noData.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
            UtilMethods.INSTANCE.Error(this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
        }
    }

    @Override
    public void onClick(View v) {

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
            mCustomFilterDialog.openDisputeFilter("Recharge", mLoginDataResponse.getData().getRoleID(),
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
                            isRecent = false;
                            HitApi();
                        }
                    });
           /* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

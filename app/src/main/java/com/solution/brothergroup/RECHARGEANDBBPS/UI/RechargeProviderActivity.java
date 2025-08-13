package com.solution.brothergroup.RECHARGEANDBBPS.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.SelectProviderAdapter;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.DTH.Activity.DTHSubscriptionActivity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

public class RechargeProviderActivity extends AppCompatActivity {

    private static String LOG_TAG = "EXAMPLE";
    RecyclerView rcSelectProvider;
    SelectProviderAdapter mAdapter;
    NumberListResponse NumberList = new NumberListResponse();
    ArrayList<OperatorList> operatorArray = new ArrayList<>();
    String from = "dth";
    CustomLoader loader;
    LinearLayout llComingSoon;
    private String forWhat = "BBPS";
    private int fromId;
    private boolean fromPhoneRecharge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_provider);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        getId();
    }

    private void getId() {
        rcSelectProvider = (RecyclerView) findViewById(R.id.rc_select_provider);
        llComingSoon = (LinearLayout) findViewById(R.id.ll_coming_soon);

        // Set toolbar icon in ...
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle("Select Provider");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fromPhoneRecharge = getIntent().getBooleanExtra("fromPhoneRecharge", false);
        from = getIntent().getExtras().getString("from");
        fromId = getIntent().getExtras().getInt("fromId", 0);

        getOperator(fromId + "");
        // getOperatorList();

        if (operatorArray != null && operatorArray.size() > 0) {
            llComingSoon.setVisibility(View.GONE);
            mAdapter = new SelectProviderAdapter(operatorArray, this);
            rcSelectProvider.setLayoutManager(new GridLayoutManager(this, 2));
            rcSelectProvider.setItemAnimator(new DefaultItemAnimator());
            rcSelectProvider.setAdapter(mAdapter);
        } else {
            llComingSoon.setVisibility(View.VISIBLE);
        }

    }

    public void getOperatorList() {

        if (from.equalsIgnoreCase("Postpaid")) {
            getOperator("2");
        } else if (from.equalsIgnoreCase("dth")) {
            getOperator("3");
        } else if (from.equalsIgnoreCase("landline")) {
            getOperator("4");
        } else if (from.equalsIgnoreCase("electricity")) {
            getOperator("5");
        } else if (from.equalsIgnoreCase("insurance")) {
            //  getOperator("2");
        } else if (from.equalsIgnoreCase("gas")) {
            getOperator("6");
        } else if (from.equalsIgnoreCase("water")) {
            getOperator("17");
        } else if (from.equalsIgnoreCase("Broadband")) {
            getOperator("16");
        }

    }

    private void getOperator(String op_Type) {
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberList = gson.fromJson(response, NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if ((op.getOpType()+"").equals(op_Type)) {
                operatorArray.add(op);
            }

        }
    }


/*    public void ItemClick(final String name, final int id, final int Max, final int Min, final int Length,
                          final int MaxLength, final boolean IsAccountNumeric,
                          final boolean IsPartial, final boolean BBPS, final String AccountName,
                          final String AccountRemark, final String Icon, final boolean isBilling, final String StartWith) {*/
        public void ItemClick(OperatorList operator) {




            openRechargeScreen(operator);


    }


    void openRechargeScreen(OperatorList operator) {
        if (fromPhoneRecharge) {
            Intent clickIntent = new Intent();
            clickIntent.putExtra("SelectedOperator", operator);
            clickIntent.putExtra("from", from);
            clickIntent.putExtra("fromId", fromId);
            setResult(RESULT_OK, clickIntent);
            finish();
        } else {
            if(fromId==35||fromId==36)
            {
                Intent i = new Intent(this, DTHSubscriptionActivity.class);
                i.putExtra("from", from);
                i.putExtra("fromId", fromId);
                i.putExtra("SelectedOperator", operator);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }

            else if (fromId == 34 ) {
                Intent i = new Intent(this, DTHSubscriptionActivity.class);
                i.putExtra("from", from);
                i.putExtra("fromId", fromId);
                i.putExtra("SelectedOperator", operator);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }else {
                Intent clickIntent = new Intent(this, from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") ? RechargeActivity.class : SecondRechargeActivity.class);
                clickIntent.putExtra("SelectedOperator", operator);
                clickIntent.putExtra("from", from);
                clickIntent.putExtra("fromId", fromId);
                startActivity(clickIntent);
            }
        }
    }

    /*public void ItemClick(final String name, final int id, final String Max, final String Min, final String Length,
                          final Boolean IsPartial, final Boolean BBPS, final String AccountName, final String AccountRemark, final String Icon, final Boolean isBilling, final String StartWith, final String lengthMax, final boolean isAccountNumeric) {


        if (BBPS && !IsPartial) {
            *//*--------------------Calling Onboarding service----------------------------*//*

            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                UtilMethods.INSTANCE.CallOnboarding(this, id, "", true, false, loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        if (object != null) {
                            OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                            if (mOnboardingResponse != null) {
                                Intent clickIntent = new Intent(RechargeProviderActivity.this, SecondRechargeActivity.class);
                                clickIntent.putExtra("selected", name);
                                clickIntent.putExtra("selectedId", id);
                                clickIntent.putExtra("Max", Max);
                                clickIntent.putExtra("Min", Min);
                                clickIntent.putExtra("from", from);
                                clickIntent.putExtra("fromId", fromId);
                                clickIntent.putExtra("Length", Length);
                                clickIntent.putExtra("IsPartial", IsPartial);
                                clickIntent.putExtra("BBPS", BBPS);
                                clickIntent.putExtra("AccountName", AccountName);
                                clickIntent.putExtra("AccountRemark", AccountRemark);
                                clickIntent.putExtra("Icon", Icon);
                                clickIntent.putExtra("isBilling", isBilling);
                                clickIntent.putExtra("lengthMax", lengthMax);
                                clickIntent.putExtra("isAccountNumeric", isAccountNumeric);
                                clickIntent.putExtra("StartWith", StartWith);
                                startActivity(clickIntent);
                            }
                        }
                    }
                });
            } else {
                UtilMethods.INSTANCE.NetworkError(this, "Network Error!", getString(R.string.network_error));
            }

        } else {
            Intent clickIntent = new Intent(RechargeProviderActivity.this, SecondRechargeActivity.class);
            clickIntent.putExtra("selected", name);
            clickIntent.putExtra("selectedId", id);
            clickIntent.putExtra("Max", Max);
            clickIntent.putExtra("Min", Min);
            clickIntent.putExtra("from", from);
            clickIntent.putExtra("fromId", fromId);
            clickIntent.putExtra("Length", Length);
            clickIntent.putExtra("IsPartial", IsPartial);
            clickIntent.putExtra("BBPS", BBPS);
            clickIntent.putExtra("AccountName", AccountName);
            clickIntent.putExtra("AccountRemark", AccountRemark);
            clickIntent.putExtra("Icon", Icon);
            clickIntent.putExtra("isBilling", isBilling);
            clickIntent.putExtra("lengthMax", lengthMax);
            clickIntent.putExtra("isAccountNumeric", isAccountNumeric);
            clickIntent.putExtra("StartWith", StartWith);
            startActivity(clickIntent);
        }


    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        if (searchMenuItem == null) {
            return true;
        }

        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search");
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //  mAdapter.filter(newText);
                newText = newText.toLowerCase();
                ArrayList<OperatorList> newlist = new ArrayList<>();
                for (OperatorList op : operatorArray) {
                    String getName = op.getName().toLowerCase();
                    if (getName.contains(newText)) {
                        newlist.add(op);

                    }
                }
                mAdapter.filter(newlist);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}

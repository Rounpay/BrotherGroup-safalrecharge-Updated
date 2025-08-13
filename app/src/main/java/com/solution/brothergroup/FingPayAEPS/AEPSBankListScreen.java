package com.solution.brothergroup.FingPayAEPS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.BankListScreenAdapter;
import com.solution.brothergroup.Api.Object.BankListObject;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AEPSBankListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> operator=new ArrayList<>();
    BankListResponse operatorList = new BankListResponse();

    private CustomLoader loader;
EditText search_all;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_aeps_bank_screen);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank List");
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        search_all=findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });


        recycler_view = findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        getOperatorList();


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().toLowerCase();
                ArrayList<BankListObject> newlist = new ArrayList<>();
                for (BankListObject op : operator) {
                    String getName = op.getBankName().toLowerCase();
                    if (getName.contains(newText)) {
                        newlist.add(op);
                    }
                }
                mAdapter.filter(newlist);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getOperatorList() {

        operatorList = (BankListResponse) getIntent().getSerializableExtra("BankList");
        if (operatorList != null && operatorList.getBankMasters() != null && operatorList.getBankMasters().size() > 0) {
            operator = operatorList.getBankMasters();
            setData();
        }else {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String response = prefs.getString(ApplicationConstant.INSTANCE.bankAEPSListPref, null);
            operatorList = new Gson().fromJson(response, BankListResponse.class);
            if (operatorList != null && operatorList.getBankMasters() != null && operatorList.getBankMasters().size() > 0) {
                operator = operatorList.getBankMasters();
                setData();
            }else{
                AEPSWebConnectivity.INSTANCE.GetAEPSBanklist(this,loader,null, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        operatorList = (BankListResponse) object;
                        if (operatorList != null && operatorList.getBankMasters() != null && operatorList.getBankMasters().size() > 0) {
                            operator = operatorList.getBankMasters();
                            setData();
                        }
                    }
                });

            }

        }

    }

    void setData(){
        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new BankListScreenAdapter(operator, AEPSBankListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public void ItemClick(BankListObject operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("Bank", operator);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //  mAdapter.filter(newText);
                newText = newText.toLowerCase();
                ArrayList<BankListObject> newlist = new ArrayList<>();
                for (BankListObject op : operator) {
                    String getName = op.getBankName().toLowerCase();
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


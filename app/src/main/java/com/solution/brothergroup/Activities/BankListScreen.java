package com.solution.brothergroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
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


/**
 * Created by Vishnu on 14-04-2017.
 */


public class BankListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> operator = new ArrayList<>();
    BankListResponse operatorList = new BankListResponse();
    TextView title;
    EditText search_all;
    private CustomLoader loader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_list_screen);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        search_all = findViewById(R.id.search_all);
        title = findViewById(R.id.title);
        findViewById(R.id.searchViewLayout).setVisibility(View.VISIBLE);
        title.setText("Bank List");
        findViewById(R.id.backIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });


        recycler_view = findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        //getOperatorList();
        if (UtilMethods.INSTANCE.getBankList(this) != null && UtilMethods.INSTANCE.getBankList(this).length() > 0) {
            String response=UtilMethods.INSTANCE.getBankList(this);
            operatorList = new Gson().fromJson(response, BankListResponse.class);
            operator = operatorList.getBankMasters();
        } else {
            HitApi();
        }

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new BankListScreenAdapter(operator, BankListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }

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

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.bankListPref, null);

        Gson gson = new Gson();
        operatorList = gson.fromJson(response, BankListResponse.class);
        operator = operatorList.getBankMasters();

    }

    public void ItemClick(BankListObject operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", operator.getBankName());
        clickIntent.putExtra("bankId", operator.getId());
        clickIntent.putExtra("accVerification", operator.getIsACVerification());
        clickIntent.putExtra("shortCode", operator.getCode());
        clickIntent.putExtra("ifsc", operator.getIfsc());
        clickIntent.putExtra("neft", operator.getIsNEFT());
        clickIntent.putExtra("imps", operator.getIsIMPS());
        clickIntent.putExtra("accLmt", operator.getAccountLimit());
        clickIntent.putExtra("ekO_BankID", operator.getEkO_BankID());
        setResult(4, clickIntent);
        finish();
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetBanklist(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {

                    BankListResponse bankListResponse=(BankListResponse) object;
                    operator = bankListResponse.getBankMasters();
                }
            });
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
        }
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

/*
public class BankListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> operator = new ArrayList<>();
    BankListResponse operatorList = new BankListResponse();
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank List");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        getOperatorList();

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new BankListScreenAdapter(operator, BankListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public void getOperatorList() {

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.bankListPref, null);

        Gson gson = new Gson();
        operatorList = gson.fromJson(response, BankListResponse.class);
        operator = operatorList.getBanks();
    }

    public void ItemClick(String id, String name, String accVerification, String shortCode) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", name);
        clickIntent.putExtra("bankId", id);
        clickIntent.putExtra("accVerification", accVerification);
        clickIntent.putExtra("shortCode", shortCode);
        setResult(1, clickIntent);
        finish();
    }

}
*/

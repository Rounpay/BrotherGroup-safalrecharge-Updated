package com.solution.brothergroup.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.AccountOpenListAdapter;
import com.solution.brothergroup.Api.Object.AccountOpData;
import com.solution.brothergroup.Api.Response.AccountOpenListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Vishnu on 14-04-2017.
 */


public class AccountOpenActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    AccountOpenListAdapter mAdapter;
    ArrayList<AccountOpData> operator=new ArrayList<>();

    private CustomLoader loader;
    EditText search_all;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_account_open);




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
        mAdapter = new AccountOpenListAdapter(operator, this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);
        noData = findViewById(R.id.noData);

        getOperatorList();


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().toLowerCase();
                ArrayList<AccountOpData> newlist = new ArrayList<>();
                for (AccountOpData op : operator) {

                    if ((op.getName()+"").toLowerCase().contains(newText)||(op.getContent()+"").toLowerCase().contains(newText)) {
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




        AccountOpenListResponse mAccountOpenListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getAccountOpenList(this), AccountOpenListResponse.class);
            if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null &&
                    mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {
                operator.clear();
                operator.addAll(mAccountOpenListResponse.getAccountOpeningDeatils());
                if (operator != null && operator.size() > 0) {
                    noData.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();

                } else {
                    noData.setVisibility(View.VISIBLE);
                }
            }
        UtilMethods.INSTANCE.GetAccountOpenlist(this,getIntent().getIntExtra("SERVICE_ID",0),loader,
                 new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        AccountOpenListResponse mAccountOpenListResponse = (AccountOpenListResponse) object;
                        if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null &&
                                mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {
                            operator.clear();
                            operator.addAll(mAccountOpenListResponse.getAccountOpeningDeatils());
                            if (operator != null && operator.size() > 0) {
                                noData.setVisibility(View.GONE);
                                mAdapter.notifyDataSetChanged();

                            } else {
                                noData.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
        );
        }






}


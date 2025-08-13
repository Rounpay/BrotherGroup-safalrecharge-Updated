package com.solution.brothergroup.ROffer.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.solution.brothergroup.R;
import com.solution.brothergroup.ROffer.dto.ROfferObject;
import com.solution.brothergroup.ROffer.dto.RofferResponse;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;

public class ROfferActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;


    RelativeLayout searchViewLayout;
    LinearLayoutManager mLayoutManager;
    ArrayList<ROfferObject> transactionsObjects = new ArrayList<>();
    RofferResponse mRofferResponse = new RofferResponse();
    EditText search_all;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        searchViewLayout = findViewById(R.id.searchViewLayout);
        searchViewLayout.setVisibility(View.GONE);
        mRofferResponse = (RofferResponse) getIntent().getSerializableExtra("response");

        recycler_view = findViewById(R.id.recycler_view);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("R OFFER");
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        parseData();
    }

    public void parseData() {
        try {

            if (mRofferResponse != null && mRofferResponse.getData() != null && mRofferResponse.getData().getRecords() != null && mRofferResponse.getData().getRecords().size() > 0) {
                transactionsObjects = mRofferResponse.getData().getRecords();

            } else if (mRofferResponse != null && mRofferResponse.getRofferData() != null && mRofferResponse.getRofferData().size() > 0) {
                transactionsObjects = mRofferResponse.getRofferData();

            } else if (mRofferResponse != null && mRofferResponse.getDataPA() != null && mRofferResponse.getDataPA().getRecords() != null && mRofferResponse.getDataPA().getRecords().size() > 0) {
                transactionsObjects = mRofferResponse.getDataPA().getRecords();

            } else if (mRofferResponse != null && mRofferResponse.getDataPA() != null && mRofferResponse.getDataPA().getError() != 0) {
                UtilMethods.INSTANCE.Error(this, mRofferResponse.getDataPA().getMessage() + "");

            } else {
                UtilMethods.INSTANCE.Error(this, "No Offer Found");
            }
            ROfferAdapter mAdapter = new ROfferAdapter(transactionsObjects, ROfferActivity.this);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
        } catch (Exception e) {
            UtilMethods.INSTANCE.Error(this, "No Offer Found");
        }


    }

    @Override
    public void onClick(View v) {

    }

    public void ItemClick(String amount, String desc) {


        ActivityActivityMessage activityActivityMessage =
                new ActivityActivityMessage("rOffer_Amount", "" + amount + "_" + desc);
        GlobalBus.getBus().post(activityActivityMessage);
        finish();
    }
}

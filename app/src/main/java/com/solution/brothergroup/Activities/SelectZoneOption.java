package com.solution.brothergroup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.SelectZoneOptionAdapter;
import com.solution.brothergroup.Api.Object.NumberList;
import com.solution.brothergroup.Api.Response.CircleList;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.util.ArrayList;

public class SelectZoneOption extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    SelectZoneOptionAdapter mAdapter;
    ArrayList<CircleList> operator = new ArrayList<>();
    ArrayList<NumberList> operatorSelection = new ArrayList<>();
    ArrayList<String> rechargeType = new ArrayList<>();

    String from = "";
    String operatorId = "";
    String opListName = "";
    String opListNameCode = "";
    int opList = 0;
    TextView title;
    ImageView backIcon;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_option_layout);

        from = getIntent().getExtras().getString("from");
        operatorId = getIntent().getExtras().getString("operatorId");
        opList = getIntent().getExtras().getInt("opList");
        if (opList == 1)
            opListName = getIntent().getExtras().getString("opListName");

        title = findViewById(R.id.title);
        backIcon = findViewById(R.id.backIcon);

        title.setText("Select Operator");

        if (from.equalsIgnoreCase("operator"))
            title.setText("Operator's");
        else
            title.setText("Zone");


        recycler_view = findViewById(R.id.recycler_view);
        noData = findViewById(R.id.noData);

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        NumberListResponse numberListResponse = new Gson().fromJson(response, NumberListResponse.class);
        operator = numberListResponse.getData().getCirlces();

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new SelectZoneOptionAdapter(operator, SelectZoneOption.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ItemClick(String name, String id, String circle) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("selectedCircleName", circle);
        clickIntent.putExtra("selectedCircleId", id);
        setResult(3, clickIntent);
        finish();
    }

}


package com.solution.brothergroup.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.SupportAdapter;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Object.SupportListItem;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.util.ArrayList;

public class DthSupportActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SupportAdapter mSupportAdapter;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dth_support);
        from = getIntent().getStringExtra("From");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(from.equalsIgnoreCase("DTH") ? "DTH Support" : "Prepaid Support");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSupportAdapter = new SupportAdapter(this, from.equalsIgnoreCase("DTH") ? getDthListItem(3) : getMobileListItem(1));
        mRecyclerView.setAdapter(mSupportAdapter);
    }


    ArrayList<SupportListItem> getDthListItem(int op_Type) {
        ArrayList<SupportListItem> mSupportListItems = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberListResponse NumberList = gson.fromJson(response, NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if (op.getOpType()==op_Type && !op.getTollFree().isEmpty()) {
                mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), op.getTollFree().replaceAll(",", "\n"), ""));
                /*if (op.getName().contains("Airtel")) {

                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "12150 (For Airtel number)\n18605006500", "Other then Airtel number Carriers will be charge"));
                }
                if (op.getName().contains("Reliance")) {
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "18002009001\n08030332249", ""));
                }
                if (op.getName().contains("Dish")) {
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "18602583474\n18002583474", ""));
                }
                if (op.getName().contains("Videocon")) {
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "180013704444\n09115691156", "Only registered number"));
                }
                if (op.getName().contains("Sun")) {
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "18001037575\n08213940757", ""));
                }
                if (op.getName().contains("Tata")) {
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "18002086633\n18601206633", ""));
                }*/
            }

        }

        return mSupportListItems;
    }

    ArrayList<SupportListItem> getMobileListItem(int op_Type) {
        ArrayList<SupportListItem> mSupportListItems = new ArrayList<>();
        ArrayList<String> duplicateCheckArray = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberListResponse NumberList = gson.fromJson(response, NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if (op.getOpType()==op_Type && !op.getTollFree().isEmpty()) {
                mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), op.getTollFree().replaceAll(",", "\n"), ""));
               /* if (!duplicateCheckArray.contains("Airtel") && op.getName().contains("Airtel")) {
                    duplicateCheckArray.add("Airtel");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "198 (For Airtel number)\n09810198101", "Other then Airtel number Carriers will be charge"));
                }
                if (!duplicateCheckArray.contains("Bsnl") && op.getName().contains("Bsnl")) {
                    duplicateCheckArray.add("Bsnl");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "1503 (For BSNL number)\n18001801503", ""));
                }
                if (!duplicateCheckArray.contains("Docomo") && op.getName().contains("Docomo")) {
                    duplicateCheckArray.add("Docomo");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "121 (For Tata Docomo number)\n18602665555\n1800266121", ""));
                }
                if (!duplicateCheckArray.contains("Idea") && op.getName().contains("Idea")) {
                    duplicateCheckArray.add("Idea");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "12345 (For Idea number)\n198 (For Idea number)", ""));
                }
                if (!duplicateCheckArray.contains("Jio") && op.getName().contains("Jio")) {
                    duplicateCheckArray.add("Jio");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "1991(For Jio number)\n198 (For Jio number)\n18008899999", ""));
                }
                if (!duplicateCheckArray.contains("Vodafone") && op.getName().contains("Vodafone")) {
                    duplicateCheckArray.add("Vodafone");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "198 (For Vodafone number)\n09820098200", "Other then Vodafone number Carriers will be charge"));
                }
                if (!duplicateCheckArray.contains("Mtnl") && op.getName().contains("Mtnl")) {
                    duplicateCheckArray.add("Mtnl");
                    mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), "198 (For MTNL number)\n1500 (For MTNL number)", ""));
                }*/
            }

        }

        return mSupportListItems;
    }


}

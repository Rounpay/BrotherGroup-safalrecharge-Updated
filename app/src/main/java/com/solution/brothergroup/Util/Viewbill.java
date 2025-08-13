package com.solution.brothergroup.Util;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.R;

public class Viewbill extends AppCompatActivity {
    TextView MOBILE, CustomerName, BillDate, BillPeriod, DueDate, MSG, DueAmt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbill);
        String respose = getIntent().getExtras().getString("respose");
        Gson gson = new Gson();
        ViewbillResponse viewbillResponse = gson.fromJson(respose, ViewbillResponse.class);
        MOBILE = findViewById(R.id.MOBILE);
        MOBILE.setText(viewbillResponse.getMOBILE());
        CustomerName = findViewById(R.id.CustomerName);
        CustomerName.setText(viewbillResponse.getCustomerName());
        BillDate = findViewById(R.id.BillDate);
        BillDate.setText(viewbillResponse.getBillDate());
        BillPeriod = findViewById(R.id.BillPeriod);
        BillPeriod.setText(viewbillResponse.getBillPeriod());
        DueDate =  findViewById(R.id.DueDate);
        DueDate.setText(viewbillResponse.getDueDate());
        MSG = findViewById(R.id.MSG);
        MSG.setText(viewbillResponse.getMSG());
        DueAmt = findViewById(R.id.DueAmt);
        DueAmt.setText(viewbillResponse.getDueAmt());
        DueAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityActivityMessage activityActivityMessage =
                        new ActivityActivityMessage("viewbill", DueAmt.getText().toString());
                GlobalBus.getBus().post(activityActivityMessage);
                Viewbill.this.finish();
            }
        });

    }
}

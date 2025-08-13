package com.solution.brothergroup.RECHARGEANDBBPS.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.Api.Object.BillAdditionalInfo;
import com.solution.brothergroup.R;
import com.solution.brothergroup.RECHARGEANDBBPS.ADAPTER.BillFetchInfoAdapter;

import java.util.List;

public class BillFetchInfoScreen extends AppCompatActivity
{
    private String number, operator;
    private RecyclerView infoListRv;
    private TextView opDetailsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_b_b_p_s_screen);
        infoListRv = findViewById(R.id.infoListRv);
        opDetailsTv = findViewById(R.id.opDetailsTv);
        infoListRv.setLayoutManager(new LinearLayoutManager(this));
        number = getIntent().getExtras().getString("number");
        operator = getIntent().getExtras().getString("operator");
        List<BillAdditionalInfo> billAdditionalInfoList = (List<BillAdditionalInfo>) getIntent().getSerializableExtra("AdditionalInfo");
        opDetailsTv.setText(operator + "\n" + number);
        if (billAdditionalInfoList != null && billAdditionalInfoList.size() > 0) {
            infoListRv.setAdapter(new BillFetchInfoAdapter(billAdditionalInfoList, this, true));
        }

        findViewById(R.id.closeViewIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}

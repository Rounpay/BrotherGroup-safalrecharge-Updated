package com.solution.brothergroup.DthCustomerInfo.ui;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.DthCustomerInfo.dto.DTHInfoData;
import com.solution.brothergroup.DthCustomerInfo.dto.DTHInfoRecords;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.GlobalBus;

import java.util.ArrayList;
import java.util.List;

public class DthConsumerInfoScreen extends AppCompatActivity {//consumer_info_screen


    // Declare CustomLoader.....
    ImageView operatorIcon;
    DTHInfoData responsePlan = new DTHInfoData();
    ArrayList<String> rechargeType = new ArrayList<>();
    List<DTHInfoRecords> operatorDetails = new ArrayList<>();
    String response = "";
    String operatorName;
    String OpRefOp = "", CoustomerNumber = "";
    String OpRefCircle = "";
    TextView tel, operator, customerName, planname, NextRechargeDate, Balance;
    TextView MonthlyRecharge;
    LinearLayout ll_RechargeDate, ll_bal_amount, ll_customer_layout, ll_plan_name;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumer_info_screen);


        responsePlan = (DTHInfoData) getIntent().getSerializableExtra("response");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DTH Consumer Info");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tel = findViewById(R.id.tel);

        operatorIcon = findViewById(R.id.operatorIcon);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(getIntent().getSerializableExtra("icon")).
                apply(requestOptions).into(operatorIcon);
        operator = findViewById(R.id.operator);
        customerName = findViewById(R.id.customerName);
        Balance = findViewById(R.id.Balance);
        planname = findViewById(R.id.planname);
        NextRechargeDate = findViewById(R.id.NextRechargeDate);
        MonthlyRecharge = findViewById(R.id.RechargeAmount);
        ll_RechargeDate = findViewById(R.id.ll_RechargeDate);
        ll_bal_amount = findViewById(R.id.ll_bal_amount);
        ll_customer_layout = findViewById(R.id.ll_customer_layout);
        ll_plan_name = findViewById(R.id.ll_plan_name);


        // dthPlan(OpRefOp, CoustomerNumber);
        parsedata(response);
    }

    private void parsedata(String response) {
       /* Gson gson = new Gson();
        responsePlan = gson.fromJson(response, DTHInfoData.class);*/
        operatorDetails = responsePlan.getRecords();
        tel.setText(getIntent().getStringExtra("title").trim() + " : " + responsePlan.getTel());
        operator.setText(responsePlan.getOperator());
        try {
            if (!operatorDetails.get(0).getCustomerName().equalsIgnoreCase(""))
                customerName.setText(operatorDetails.get(0).getCustomerName());
            else
                ll_customer_layout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            ll_customer_layout.setVisibility(View.GONE);
        }
        try {
            MonthlyRecharge.setText(getResources().getString(R.string.rupiya) + " " + operatorDetails.get(0).getMonthlyRecharge());
        } catch (Exception e) {
            e.printStackTrace();
            ll_bal_amount.setVisibility(View.GONE);
        }
        try {
            planname.setText(operatorDetails.get(0).getPlanname());
        } catch (Exception e) {
            e.printStackTrace();
            ll_plan_name.setVisibility(View.GONE);
        }
        try {
            NextRechargeDate.setText(operatorDetails.get(0).getNextRechargeDate());
        } catch (Exception e) {
            e.printStackTrace();
            ll_RechargeDate.setVisibility(View.GONE);
        }
        try {
            Balance.setText(getResources().getString(R.string.rupiya) + "   " + operatorDetails.get(0).getBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MonthlyRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Amount", operatorDetails.get(0).getMonthlyRecharge());
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }


 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browseplan_menu, menu);

        if (operatorName != null && operatorName.toString().length() > 0) {

            String imgName = operatorName;
            String imgTemp = imgName.toLowerCase().replace(" ", "");
            String imgTemp1 = imgTemp.toLowerCase().replace("-", "");
            String imgTemp2 = imgTemp1.toLowerCase().replace("&", "");
            String imgTemp3 = imgTemp2.toLowerCase().replace("_", "");
            String imgTemp4 = imgTemp3.substring(0, imgTemp3.length());

            int resourceId = getResources().getIdentifier(
                    imgTemp4, "drawable", getPackageName());

            if (resourceId != 0)
                menu.getItem(0).setIcon(getResources().getDrawable(resourceId));
            else
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_operator_default_icon));
        } else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_operator_default_icon));
        }


        this.invalidateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }*/

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            GlobalBus.getBus().register(this);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }


}






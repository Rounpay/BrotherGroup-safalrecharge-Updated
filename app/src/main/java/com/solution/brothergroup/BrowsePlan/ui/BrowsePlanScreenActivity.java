package com.solution.brothergroup.BrowsePlan.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.solution.brothergroup.BrowsePlan.dto.PlanData;
import com.solution.brothergroup.BrowsePlan.dto.PlanDataDetails;
import com.solution.brothergroup.BrowsePlan.dto.PlanTypes;
import com.solution.brothergroup.BrowsePlan.dto.ResponsePlan;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowsePlanScreenActivity extends AppCompatActivity {

    ResponsePlan responsePlan = new ResponsePlan();
    /*ArrayList<String> rechargeType = new ArrayList<>();*/
    PlanData records;
    private PlanData recordsUpdated;
    PlanData recordsRP;
    PlanData recordsPA;

    Map<String, ArrayList<PlanDataDetails>> mapPlanData = new HashMap<>();
    String response = "";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    public BrowsePlanScreenActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.browse_plan_layout);
        response = getIntent().getExtras().getString("response") + "";
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plans");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);
        viewPager = findViewById(R.id.viewpager);
        Gson gson = new Gson();
        responsePlan = gson.fromJson(response, ResponsePlan.class);
        records = responsePlan.getData();
        recordsUpdated = responsePlan.getData();
        recordsRP = responsePlan.getDataRP();
        recordsPA = responsePlan.getDataPA();

        if (records != null && records.getRecords() != null && records.getStatus() != 0) {
            dataParse(records);
        } else if (recordsUpdated != null && recordsUpdated.getTypes() != null && recordsUpdated.getTypes().size() > 0) {
            dataParseUpdated(recordsUpdated.getTypes());
        } else if (recordsRP != null && recordsRP.getRecords() != null && recordsRP.getStatus() != 0) {
            dataParse(recordsRP);
        } else if (recordsPA != null && recordsPA.getRecords() != null && recordsPA.getError() == 0) {
            dataParse(recordsPA);
        } else if (recordsPA != null && recordsPA.getError() != 0) {
            UtilMethods.INSTANCE.Error(this, recordsPA.getMessage() + "");
        } else {
            UtilMethods.INSTANCE.Error(this, "No Record Found ! ");
        }


    }

    private void dataParse(PlanData records) {


        if (records.getRecords().getFulltt() != null && records.getRecords().getFulltt().size() > 0) {
            mapPlanData.put("FULLTT", records.getRecords().getFulltt());
        }
        if (records.getRecords().get_3g4gG() != null && records.getRecords().get_3g4gG().size() > 0) {
            mapPlanData.put("3G/4G", records.getRecords().get_3g4gG());
        }
        if (records.getRecords().getRatecutter() != null && records.getRecords().getRatecutter().size() > 0) {
            mapPlanData.put("Rate Cutter", records.getRecords().getRatecutter());
        }
        if (records.getRecords().get_2g() != null && records.getRecords().get_2g().size() > 0) {
            mapPlanData.put("2G", records.getRecords().get_2g());
        }

        if (records.getRecords().getRomaing() != null && records.getRecords().getRomaing().size() > 0) {
            mapPlanData.put("Romaing", records.getRecords().getRomaing());
        }

        if (records.getRecords().getFrc() != null && records.getRecords().getFrc().size() > 0) {
            mapPlanData.put("FRC", records.getRecords().getFrc());
        }
        if (records.getRecords().getCombo() != null && records.getRecords().getCombo().size() > 0) {
            mapPlanData.put("Combo", records.getRecords().getCombo());
        }
        if (records.getRecords().get_2g3gData() != null && records.getRecords().get_2g3gData().size() > 0) {
            mapPlanData.put("2G/3G", records.getRecords().get_2g3gData());
        }
        if (records.getRecords().getComboVouchers() != null && records.getRecords().getComboVouchers().size() > 0) {
            mapPlanData.put("Combo Vouchers", records.getRecords().getComboVouchers());
        }
        if (records.getRecords().getVas() != null && records.getRecords().getVas().size() > 0) {
            mapPlanData.put("vas", records.getRecords().getVas());
        }

        if (records.getRecords().getAllRounder() != null && records.getRecords().getAllRounder().size() > 0) {
            mapPlanData.put("All Rounder", records.getRecords().getAllRounder());
        }

        if (records.getRecords().getHotstar() != null && records.getRecords().getHotstar().size() > 0) {
            mapPlanData.put("Hotstar", records.getRecords().getHotstar());
        }

        if (records.getRecords().getValidity() != null && records.getRecords().getValidity().size() > 0) {
            mapPlanData.put("Validity", records.getRecords().getValidity());
        }
        if (records.getRecords().getData() != null && records.getRecords().getData().size() > 0) {
            mapPlanData.put("Data", records.getRecords().getData());
        }
        if (records.getRecords().getDataPlans() != null && records.getRecords().getDataPlans().size() > 0) {
            mapPlanData.put("Data Plans", records.getRecords().getDataPlans());
        }
        if (records.getRecords().getIsd() != null && records.getRecords().getIsd().size() > 0) {
            mapPlanData.put("ISD", records.getRecords().getIsd());
        }
        if (records.getRecords().getMblazeUltra() != null && records.getRecords().getMblazeUltra().size() > 0) {
            mapPlanData.put("Mblaze Ultra", records.getRecords().getMblazeUltra());
        }
        if (records.getRecords().getStv() != null && records.getRecords().getStv().size() > 0) {
            mapPlanData.put("STV", records.getRecords().getStv());
        }
        if (records.getRecords().getSms() != null && records.getRecords().getSms().size() > 0) {
            mapPlanData.put("SMS", records.getRecords().getSms());
        }
        if (records.getRecords().getTalktime() != null && records.getRecords().getTalktime().size() > 0) {
            mapPlanData.put("Talk Time", records.getRecords().getTalktime());
        }
        if (records.getRecords().getInternationalRoaming() != null && records.getRecords().getInternationalRoaming().size() > 0) {
            mapPlanData.put("International Roaming", records.getRecords().getInternationalRoaming());
        }
        if (records.getRecords().getUnlimitedPlans() != null && records.getRecords().getUnlimitedPlans().size() > 0) {
            mapPlanData.put("Unlimited Plans", records.getRecords().getUnlimitedPlans());
        }
        if (records.getRecords().getValidityExtension() != null && records.getRecords().getValidityExtension().size() > 0) {
            mapPlanData.put("Validity Extension", records.getRecords().getValidityExtension());
        }
        if (records.getRecords().getWifiUltraRecharges() != null && records.getRecords().getWifiUltraRecharges().size() > 0) {
            mapPlanData.put("Wifi Ultra Recharges", records.getRecords().getWifiUltraRecharges());
        }
        if (records.getRecords().getTopup() != null && records.getRecords().getTopup().size() > 0) {
            mapPlanData.put("Topup", records.getRecords().getTopup());
        }
        if (records.getRecords().getIUCTopup() != null && records.getRecords().getIUCTopup().size() > 0) {
            mapPlanData.put("IUC Topup", records.getRecords().getIUCTopup());
        }
        if (records.getRecords().getNewAllInOne() != null && records.getRecords().getNewAllInOne().size() > 0) {
            mapPlanData.put("New ALl-In-One", records.getRecords().getNewAllInOne());
        }
        if (records.getRecords().getAllInOne() != null && records.getRecords().getAllInOne().size() > 0) {
            mapPlanData.put("ALl-In-One", records.getRecords().getAllInOne());
        }
        if (records.getRecords().getJioPhone() != null && records.getRecords().getJioPhone().size() > 0) {
            mapPlanData.put("Jio Phone", records.getRecords().getJioPhone());
        }
        if (records.getRecords().getJioPrime() != null && records.getRecords().getJioPrime().size() > 0) {
            mapPlanData.put("Jio Prime", records.getRecords().getJioPrime());
        }
        if (records.getRecords().getCricketPack() != null && records.getRecords().getCricketPack().size() > 0) {
            mapPlanData.put("Cricket Pack", records.getRecords().getCricketPack());
        }
        if (records.getRecords().getFRCNonPrime() != null && records.getRecords().getFRCNonPrime().size() > 0) {
            mapPlanData.put("FRC Non-Prime", records.getRecords().getFRCNonPrime());
        }
        if (records.getRecords().getAll() != null && records.getRecords().getAll().size() > 0) {
            mapPlanData.put("All", records.getRecords().getAll());
        }
        if (records.getRecords().getUnlimited() != null && records.getRecords().getUnlimited().size() > 0) {
            mapPlanData.put("Unlimited", records.getRecords().getUnlimited());
        }
        if (records.getRecords().getFrcsrc() != null && records.getRecords().getFrcsrc().size() > 0) {
            mapPlanData.put("FRC SRC", records.getRecords().getFrcsrc());
        }
        if (records.getRecords().getSmartRecharge() != null && records.getRecords().getSmartRecharge().size() > 0) {
            mapPlanData.put("Smart Recharge", records.getRecords().getSmartRecharge());
        }
        if (records.getRecords().getDataPack() != null && records.getRecords().getDataPack().size() > 0) {
            mapPlanData.put("Data Pack", records.getRecords().getDataPack());
        }

        setupViewPager(viewPager);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void dataParseUpdated(ArrayList<PlanTypes> mPlanTypes) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (PlanTypes item : mPlanTypes) {

            if(item.getpDetails()!=null && item.getpDetails().size()>0) {
                RechargeTypeFragment rechargeTypeFragment = new RechargeTypeFragment();
                Bundle arg = new Bundle();
                arg.putString("type", item.getpType() + "");
                arg.putSerializable("response", item.getpDetails());
                rechargeTypeFragment.setArguments(arg);
                adapter.addFragment(rechargeTypeFragment, "" + item.getpType());

            }

        }

        if (adapter.getCount() > 0) {
            viewPager.setAdapter(adapter);
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(viewPager);
        } else {

            UtilMethods.INSTANCE.Error(this, "No Record Found ! ");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (Map.Entry<String, ArrayList<PlanDataDetails>> entry : mapPlanData.entrySet()) {
            String key = entry.getKey();
            ArrayList<PlanDataDetails> arrayList = entry.getValue();
            RechargeTypeFragment rechargeTypeFragment = new RechargeTypeFragment();
            Bundle arg = new Bundle();
            arg.putString("type", key);
            arg.putSerializable("response", arrayList);
            rechargeTypeFragment.setArguments(arg);
            adapter.addFragment(rechargeTypeFragment, "" + key);
        }

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browseplan_menu, menu);
        final MenuItem settingsItem = menu.findItem(R.id.operatorIcon);
        this.invalidateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("browseplan")) {
            response = activityFragmentMessage.getFrom();
        }
    }

    public void ItemClick(String amount, String desc) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("amount", amount);
        clickIntent.putExtra("desc", desc);
        setResult(RESULT_OK, clickIntent);
       /* ActivityActivityMessage activityActivityMessage =
                new ActivityActivityMessage("planSelected", "" + amount + "_" + desc);
        GlobalBus.getBus().post(activityActivityMessage);*/
        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

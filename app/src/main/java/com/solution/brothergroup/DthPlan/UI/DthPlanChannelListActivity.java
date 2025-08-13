package com.solution.brothergroup.DthPlan.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.DTH.Adapter.DthChannelAdapter;
import com.solution.brothergroup.DthPlan.dto.DTHChannelPlanInfoRequest;
import com.solution.brothergroup.DthPlan.dto.DthPlanChannelAllResponse;
import com.solution.brothergroup.DthPlan.dto.DthPlanChannels;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoResponse;
import com.solution.brothergroup.DthPlan.dto.PlanInfoPlan;
import com.solution.brothergroup.DthPlan.dto.PlanRPResponse;
import com.solution.brothergroup.DthPlan.dto.PlanValidity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.RecyclerViewStickyHeader.HeaderItemDecoration;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class DthPlanChannelListActivity extends AppCompatActivity {
    CustomLoader loader;
    View languageView, amountView, validityView;
    public TextView validity;
    public TextView amount, planName, channel, language;
    public TextView description;
    RecyclerView recycler_view, rsGrid;
    private EditText searchView;
    View channelCountView, topView, descriptionLayout;


    private DthPlanChannelListAdapter mAdapter;

    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;
    private RequestOptions requestOptionsAdapter;
    private PlanRPResponse mPlanRPResponse;
    private PlanInfoPlan mPlanInfoPlan;
    int packageId;
    private boolean isPlanServiceUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dth_plan_channel_list);


        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        deviceId = UtilMethods.INSTANCE.getIMEI(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        requestOptionsAdapter = new RequestOptions();
        requestOptionsAdapter.placeholder(R.drawable.rnd_logo);
        requestOptionsAdapter.error(R.drawable.rnd_logo);
        requestOptionsAdapter.diskCacheStrategy(DiskCacheStrategy.ALL);
        isPlanServiceUpdated = getIntent().getBooleanExtra("IsPlanServiceUpdated", false);
        mPlanRPResponse = (PlanRPResponse) getIntent().getSerializableExtra("Data");
        mPlanInfoPlan = (PlanInfoPlan) getIntent().getSerializableExtra("DataAll");


        findViews();

        findViewById(R.id.clearIcon).setOnClickListener(v -> searchView.setText(""));


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAdapter != null) {
                    mAdapter.filter(s.toString());
                }
            }
        });
        setUiData();
        GetDthChannel();
    }

    void findViews() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle("DTH Channel");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
        searchView = findViewById(R.id.search_all);
        topView = findViewById(R.id.topView);
        planName = findViewById(R.id.planName);
        amount = findViewById(R.id.amount);
        language = findViewById(R.id.language);
        validityView = findViewById(R.id.validityView);
        languageView = findViewById(R.id.languageView);
        amountView = findViewById(R.id.amountView);
        channel = findViewById(R.id.channel);
        description = findViewById(R.id.description);
        channelCountView = findViewById(R.id.channelCountView);
        validity = findViewById(R.id.validity);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        rsGrid = findViewById(R.id.rsGrid);
        recycler_view = findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler_view.setLayoutManager(gridLayoutManager);

        rsGrid.setLayoutManager(new GridLayoutManager(this, 5));

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {


                if (mAdapter != null && mAdapter.getItemViewType(position) == DthChannelAdapter.Header) {
                    return 3;
                }

                return 1;

            }
        });
    }


    void setUiData() {
        if (mPlanRPResponse != null) {
            topView.setVisibility(View.VISIBLE);
            packageId = mPlanRPResponse.getPackageId();
            planName.setVisibility(View.GONE);
            description.setText(mPlanRPResponse.getDetails() + "");
            channel.setText(mPlanRPResponse.getChannelcount() + "");
            validity.setText(mPlanRPResponse.getRechargeValidity() + "");
            amount.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(mPlanRPResponse.getRechargeAmount() + ""));

            if (mPlanRPResponse.getPackagelanguage() != null && !mPlanRPResponse.getPackagelanguage().isEmpty()) {
                languageView.setVisibility(View.VISIBLE);
                language.setText(mPlanRPResponse.getPackagelanguage() + "");
            } else {
                languageView.setVisibility(View.GONE);
            }
        } else if (mPlanInfoPlan != null) {
            topView.setVisibility(View.VISIBLE);
            packageId = mPlanInfoPlan.getPackageId();

            if (mPlanInfoPlan.getPlanName() != null && !mPlanInfoPlan.getPlanName().isEmpty()) {
                planName.setVisibility(View.VISIBLE);
                planName.setText(mPlanInfoPlan.getPlanName() + "");
            } else {
                planName.setVisibility(View.GONE);
            }
            if (mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty()) {
                descriptionLayout.setVisibility(View.VISIBLE);
                description.setText(mPlanInfoPlan.getDesc() + "");
            } else {
                descriptionLayout.setVisibility(View.GONE);
            }
            if (mPlanInfoPlan.getpChannelCount() != 0) {
                channelCountView.setVisibility(View.VISIBLE);
                channel.setText(mPlanInfoPlan.getpChannelCount() + "");
            } else {
                channelCountView.setVisibility(View.GONE);
            }


            if (mPlanInfoPlan.getpLangauge() != null && !mPlanInfoPlan.getpLangauge().isEmpty()) {
                languageView.setVisibility(View.VISIBLE);
                language.setText(mPlanInfoPlan.getpLangauge() + "");
            } else {
                languageView.setVisibility(View.GONE);
            }

            List<PlanValidity> mPlanValidities = new ArrayList<>();

            if (mPlanInfoPlan.getRs() != null) {
                if (mPlanInfoPlan.getRs().get1MONTHS() != null && !mPlanInfoPlan.getRs().get1MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get1MONTHS(), "1 Month",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get3MONTHS() != null && !mPlanInfoPlan.getRs().get3MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get3MONTHS(), "3 Months",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get6MONTHS() != null && !mPlanInfoPlan.getRs().get6MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get6MONTHS(), "6 Months",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get9MONTHS() != null && !mPlanInfoPlan.getRs().get9MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get9MONTHS(), "9 Months",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get1YEAR() != null && !mPlanInfoPlan.getRs().get1YEAR().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get1YEAR(), "1 Year",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get5YEAR() != null && !mPlanInfoPlan.getRs().get5YEAR().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get5YEAR(), "5 Years",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
            }

            if (mPlanValidities != null && mPlanValidities.size() > 0) {
                if (mPlanValidities.size() > 1) {
                    amountView.setVisibility(View.GONE);
                    validityView.setVisibility(View.GONE);
                    rsGrid.setVisibility(View.VISIBLE);
                    if (mPlanValidities.size() <= 5) {
                        rsGrid.setLayoutManager(new GridLayoutManager(this, mPlanValidities.size()));
                    }
                    DthPlanValidityGridNewAdapter mDthPlanValidityGridAdapter = new DthPlanValidityGridNewAdapter(R.layout.adapter_plan_validity_single, this, mPlanValidities);
                    rsGrid.setAdapter(mDthPlanValidityGridAdapter);
                } else {
                    amount.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(mPlanValidities.get(0).getAmount() + ""));
                    validity.setText(mPlanValidities.get(0).getValidity() + "");
                    amountView.setVisibility(View.VISIBLE);
                    validityView.setVisibility(View.VISIBLE);
                    rsGrid.setVisibility(View.GONE);
                }
            } else {
                amountView.setVisibility(View.GONE);
                validityView.setVisibility(View.GONE);
                rsGrid.setVisibility(View.GONE);
            }
        } else {
            topView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(this, "Channel is not available");
        }
    }

    public void GetDthChannel() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            DTHChannelPlanInfoRequest mDthChannelPlanInfoRequest = new DTHChannelPlanInfoRequest(packageId + "",
                    getIntent().getStringExtra("OperatorId"),
                    mLoginDataResponse.getData().getUserID() + "", mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession());

            if (isPlanServiceUpdated) {
                Call<DthPlanChannelAllResponse> call = git.GetDTHChannelList(mDthChannelPlanInfoRequest);
                call.enqueue(new Callback<DthPlanChannelAllResponse>() {

                    @Override
                    public void onResponse(Call<DthPlanChannelAllResponse> call, retrofit2.Response<DthPlanChannelAllResponse> response) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getStatuscode() == 1) {
                                    if (response.body().getData() != null &&
                                            response.body().getData().size() > 0) {
                                        channel.setText(response.body().getData().size() + "");
                                        dataParse(response.body().getData());
                                    } else {
                                        UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, "Channel Not Found.");
                                    }

                                } else {
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, response.body().getMsg() + "");
                                    } else {
                                        UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error) + "");
                                    }
                                }
                            } else {
                                UtilMethods.INSTANCE.apiErrorHandle(DthPlanChannelListActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, e.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<DthPlanChannelAllResponse> call, Throwable t) {

                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                UtilMethods.INSTANCE.NetworkError(DthPlanChannelListActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                UtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error));
                                }
                            }
                        } catch (IllegalStateException ise) {
                            UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, ise.getMessage() + "");
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                });
            } else {

                Call<DthPlanInfoResponse> call = git.DTHChannelPlanInfo(mDthChannelPlanInfoRequest);
                call.enqueue(new Callback<DthPlanInfoResponse>() {

                    @Override
                    public void onResponse(Call<DthPlanInfoResponse> call, retrofit2.Response<DthPlanInfoResponse> response) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getStatuscode() == 1) {
                                    if (response.body().getDataRPDTHChannelList() != null &&
                                            response.body().getDataRPDTHChannelList().getChannels() != null &&
                                            response.body().getDataRPDTHChannelList().getChannels().size() > 0) {
                                        channel.setText(response.body().getDataRPDTHChannelList().getChannels().size() + "");
                                        dataParse(response.body().getDataRPDTHChannelList().getChannels());
                                    } else {
                                        UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, "Channel Not Found.");
                                    }

                                } else {
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, response.body().getMsg() + "");
                                    } else {
                                        UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error) + "");
                                    }
                                }
                            } else {
                                UtilMethods.INSTANCE.apiErrorHandle(DthPlanChannelListActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, e.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {

                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                UtilMethods.INSTANCE.NetworkError(DthPlanChannelListActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                UtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error));
                                }
                            }
                        } catch (IllegalStateException ise) {
                            UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, ise.getMessage() + "");
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
            loader.dismiss();
            UtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, e.getMessage() + "");
        }

    }


    public void dataParse(ArrayList<DthPlanChannels> channelsArrayList) {


        if (channelsArrayList != null && channelsArrayList.size() > 0) {
            Collections.sort(channelsArrayList, (o1, o2) -> o1.getGenre().compareToIgnoreCase(o2.getGenre()));

            if (channelsArrayList != null && channelsArrayList.size() > 0) {
                String category = "";
                ArrayList<DthPlanChannels> mModifiedList = new ArrayList<>();
                for (DthPlanChannels mItem : channelsArrayList) {
                    if (!category.equalsIgnoreCase(mItem.getGenre())) {
                        mModifiedList.add(new DthPlanChannels(mItem.getGenre(), "", "", ""));
                        category = mItem.getGenre();
                    }
                    mModifiedList.add(mItem);
                }
                mAdapter = new DthPlanChannelListAdapter(mModifiedList, this, requestOptionsAdapter);
                recycler_view.setAdapter(mAdapter);
                new HeaderItemDecoration(mAdapter);
                recycler_view.setVisibility(View.VISIBLE);


            } else {
                UtilMethods.INSTANCE.Error(this, "Channel is not available");
                recycler_view.setVisibility(View.GONE);
            }
        } else {
            UtilMethods.INSTANCE.Error(this, "Channel is not available");
            recycler_view.setVisibility(View.GONE);
        }


    }
}


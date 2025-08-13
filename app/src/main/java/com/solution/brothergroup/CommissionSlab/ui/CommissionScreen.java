package com.solution.brothergroup.CommissionSlab.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.SlabDetailDisplayLvl;
import com.solution.brothergroup.Api.Object.SlabRangeDetail;
import com.solution.brothergroup.Api.Response.SlabCommissionResponse;
import com.solution.brothergroup.Api.Response.SlabCommtObject;
import com.solution.brothergroup.Api.Response.SlabRangeDetailResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.RecyclerViewStickyHeader.HeaderItemDecoration;
import com.solution.brothergroup.Util.RecyclerViewStickyHeader.HeaderRecyclerView;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class CommissionScreen extends AppCompatActivity implements View.OnClickListener {

    CustomLoader loader;
    HeaderRecyclerView recycler_view;
    CommissionAdapter mAdapter;
    String response = "";

    LinearLayoutManager mLayoutManager;
    ArrayList<SlabDetailDisplayLvl> transactionsObjects = new ArrayList<>();
    ArrayList<SlabCommtObject> transactionsNewObjects = new ArrayList<>();

    SlabCommissionResponse transactions = new SlabCommissionResponse();
    EditText searchView;
    RelativeLayout searchLayout;
    private AlertDialog alertDialog;
    private RequestOptions requestOptions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_screen);
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        loader = new CustomLoader(com.solution.brothergroup.CommissionSlab.ui.CommissionScreen.this, android.R.style.Theme_Translucent_NoTitleBar);
        recycler_view = findViewById(R.id.recycler_view);
        searchView = (EditText) findViewById(R.id.search_all);
        // searchView.setIconified(false);
        searchView.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Commission Slab");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dataParse(new Gson().fromJson(UtilMethods.INSTANCE.getCommList(this), SlabCommissionResponse.class));
        findViewById(R.id.clearIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText("");
            }
        });
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
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        HitApi();
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.MyCommission(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    dataParse((SlabCommissionResponse) object);
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void HitApiSlabDetail(final int oid, final String operatorValue, final String min,String max) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.MyCommissionDetail(this, oid, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    SlabRangeDetailResponse mSlabRangeDetailResponse = (SlabRangeDetailResponse) object;
                    slabDetailDialog(mSlabRangeDetailResponse.getSlabRangeDetail(), operatorValue, min,max, oid);
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void dataParse(SlabCommissionResponse transactions) {


        if (transactions != null) {
            transactionsObjects = transactions.getSlabDetailDisplayLvl();
            if (transactionsObjects != null && transactionsObjects.size() > 0) {

                String HeaderName = "";
                for (int i = 0; i < transactionsObjects.size(); i++) {
                    if (!HeaderName.equalsIgnoreCase(transactionsObjects.get(i).getOpType())) {
                        HeaderName = transactionsObjects.get(i).getOpType();
                        transactionsObjects.add(i, new SlabDetailDisplayLvl(transactionsObjects.get(i).getOpType(), 0,
                                "", "", 0,0, 0, 0, null));
                    }
                }


                mAdapter = new CommissionAdapter(transactionsObjects, this);
                mLayoutManager = new LinearLayoutManager(this);
                recycler_view.setLayoutManager(mLayoutManager);
                new HeaderItemDecoration(mAdapter);
                recycler_view.setAdapter(mAdapter);
                recycler_view.setVisibility(View.VISIBLE);


            }
        } else {
            recycler_view.setVisibility(View.GONE);
        }

    }


    public void slabDetailDialog(ArrayList<SlabRangeDetail> mSlabRangeDetail, String operatorValue, String min,String max, int oid) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_slab_range_details, null);
            alertDialog.setView(dialogView);
 View maxCommView, fixedCommView;
                            ImageView closeView = dialogView.findViewById(R.id.iv_cancleView);
                            ImageView opImage = dialogView.findViewById(R.id.iv_opImage);
                            TextView opName = dialogView.findViewById(R.id.tv_opName);
                            TextView opRange = dialogView.findViewById(R.id.tv_opRange);
                            maxCommView = dialogView.findViewById(R.id.maxCommView);
                            fixedCommView = dialogView.findViewById(R.id.fixedCommView);
                            /*maxCommView.setVisibility(View.GONE);
                            fixedCommView.setVisibility(View.GONE);*/

                            RecyclerView slabRangeRecyclerView = dialogView.findViewById(R.id.rv_slabRange);
                            slabRangeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

                            CommissionSlabDetailAdapter commissionSlabAdapter = new CommissionSlabDetailAdapter(mSlabRangeDetail, this);
                            slabRangeRecyclerView.setAdapter(commissionSlabAdapter);

                            opName.setText(operatorValue + "");
                            opRange.setText("Range : " + min + " - " + max);

                            closeView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.mipmap.ic_launcher);
                            requestOptions.error(R.mipmap.ic_launcher);
                            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(this)
                                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                                    .apply(requestOptions)
                                    .into(opImage);

           /* AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView operator = dialogView.findViewById(R.id.operator);
            TextView maxMin = dialogView.findViewById(R.id.maxMin);
            ImageView iconIv = dialogView.findViewById(R.id.icon);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CommissionSlabDetailAdapter(mSlabRangeDetail, this));
            operator.setText(operatorValue);
            maxMin.setText("Range : "+maxMinValue);

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                    .apply(requestOptions)
                    .into(iconIv);
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });*/


            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


   /* private String getname(String Opid) {
        String operatorName = "";
        Gson gson = new Gson();
        NumberListResponse NumberList = gson.fromJson(UtilMethods.INSTANCE.getOperatorList(this), NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if ((op.getOid()+"").equals(Opid)) {
                operatorName = op.getName();
            }

        }
        return operatorName;
    }*/
}

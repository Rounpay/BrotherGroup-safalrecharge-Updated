package com.solution.brothergroup.Fragments.Adapter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.solution.brothergroup.R;
import com.solution.brothergroup.UpgradePacakge.dto.PDetail;
import com.solution.brothergroup.UpgradePacakge.dto.ServicesObj;
import com.solution.brothergroup.UpgradePacakge.dto.UpgradePackageResponse;
import com.solution.brothergroup.UpgradePacakge.ui.UpgradePackageAdapter;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

public class UpgradePackage extends AppCompatActivity {
    CustomLoader loader;
    private Toolbar toolbar;
    RecyclerView recycler_view;
    ImageView noData;
    String response;

    ArrayList<PDetail> packageDetails=new ArrayList<>();
    ArrayList<ServicesObj>services=new ArrayList<>();
    UpgradePackageAdapter upgradePackageAdapter;
    //PackageData packageData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_package);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        // response = getIntent().getExtras().getString("response") + "";

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Upgrade package");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        noData = (ImageView) findViewById(R.id.noData);
        HitApi();
        //getDetail();
    }

    private void dataParse(UpgradePackageResponse response) {
        //Gson gson = new Gson();
        // responsedata = gson.fromJson(response, AppUserListResponse.class);
        //packageData=response.getpDetail();
        packageDetails=response.getpDetail();
/*
        services=response.getpDetail().get(0).getServices();

        CustomPackage customPackage;
        ArrayList<CustomPackage> CustomServicesList=new ArrayList<>();
        ArrayList<String> servicePackageName =null;

        if(packageDetails!=null && packageDetails.size()>0)
        {
            for(int i=0;i<packageDetails.size();i++)
            {
                customPackage=new CustomPackage();
                customPackage.setPackageName(packageDetails.get(i).getPackageName());
                customPackage.setPackageAmount(packageDetails.get(i).getPackageCost());
                customPackage.setDefault(packageDetails.get(i).getDefault());
                customPackage.setPackageId(packageDetails.get(i).getPackageId());
                servicePackageName=new ArrayList<>();
                if(services!=null&&services.size()>0)
                {
                    for(ServicesObj servicesObj:services)
                    {

                        if(servicesObj.getPackageId().equalsIgnoreCase(packageDetails.get(i).getPackageId()))
                        {
                            String serviceName=servicesObj.getServiceName();
                            servicePackageName.add(serviceName);
                            customPackage.setServiceNameList(servicePackageName);
                        }


                    }
                    CustomServicesList.add(customPackage);
                }

            }
        }
*/

        // upgradePackageAdapter = new UpgradePackageAdapter(UpgradePackage.this,CustomServicesList);
        upgradePackageAdapter = new UpgradePackageAdapter(UpgradePackage.this,packageDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(upgradePackageAdapter);
    }


    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetAppPackageAvailable(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    UpgradePackageResponse data=(UpgradePackageResponse)object;
                    dataParse(data);

                }
            });
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }
}
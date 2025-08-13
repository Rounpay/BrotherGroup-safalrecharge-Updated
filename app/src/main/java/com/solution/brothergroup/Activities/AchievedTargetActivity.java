package com.solution.brothergroup.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import java.util.List;
import com.solution.brothergroup.Adapter.AchievedTargetAdapter;
import com.solution.brothergroup.Api.Object.TargetAchieved;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;



public class AchievedTargetActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_achieved_target);
        setTitle("Target Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hitApi();
    }


    void hitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.UserAchieveTarget(this, false, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                    if (mAppUserListResponse != null) {
                        List<TargetAchieved> transactionsObjects = mAppUserListResponse.getTargetAchieveds();
                        if (transactionsObjects != null && transactionsObjects.size() > 0) {
                            AchievedTargetAdapter mAdapter = new AchievedTargetAdapter(transactionsObjects, AchievedTargetActivity.this);
                            recyclerView.setAdapter(mAdapter);

                        } else {
                            UtilMethods.INSTANCE.Error(AchievedTargetActivity.this, "Data not found.");
                        }
                    }
                }
            });


        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

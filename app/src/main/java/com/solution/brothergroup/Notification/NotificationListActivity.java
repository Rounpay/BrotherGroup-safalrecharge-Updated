package com.solution.brothergroup.Notification;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Notification.Adapter.NotificationListAdapter;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

public class NotificationListActivity extends AppCompatActivity {

    AppUserListResponse mNotificationResponse;
    RecyclerView recycler_view;
    TextView noData;
    NotificationListAdapter mNotificationListAdapter;
    private int readCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_notification_list);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle("Notification & Event");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        noData = findViewById(R.id.noData);
        mNotificationResponse = new Gson().fromJson(UtilMethods.INSTANCE.getNotificationList(this), AppUserListResponse.class);

        if (mNotificationResponse != null && mNotificationResponse.getNotifications() != null && mNotificationResponse.getNotifications().size() > 0) {
            mNotificationListAdapter = new NotificationListAdapter(this, mNotificationResponse.getNotifications());
            recycler_view.setAdapter(mNotificationListAdapter);
        } else {
            // noData.setVisibility(View.VISIBLE);
        }


    }

    public void setReadNotification(int position) {
        mNotificationResponse.getNotifications().get(position).setSeen(true);
        mNotificationListAdapter.notifyDataSetChanged();
        UtilMethods.INSTANCE.setNotificationList(this, new Gson().toJson(mNotificationResponse));
        readCount++;

        setResult(RESULT_OK, new Intent().putExtra("Notification_Count", readCount));
    }

}

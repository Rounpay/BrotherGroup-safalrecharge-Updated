package com.solution.brothergroup.Notification.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.NotificationData;
import com.solution.brothergroup.Notification.NotificationActivity;
import com.solution.brothergroup.Notification.NotificationListActivity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    int resourceId = 0;
    private List<NotificationData> mList;
    private Context mContext;
RequestOptions requestOptions;
    public NotificationListAdapter(Context mContext, List<NotificationData> mList) {
        this.mList = mList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.notification);
        requestOptions.error(R.drawable.notification);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification, parent, false);

        return new NotificationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationListAdapter.MyViewHolder holder, final int position) {
        final NotificationData operator = mList.get(position);
        holder.title.setText(operator.getTitle());
        holder.msg.setText(operator.getMessage().replace("\n\n\n", "").replaceAll("\n\n", "\n").replaceAll("\r\n\r\n", "\n"));
        holder.date.setText(operator.getEntryDate());

        if (!operator.isSeen()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.aboutpagecolour));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }


        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseUrl + "/Image/Notification/" + operator.getImageUrl())
                .apply(requestOptions)
                .into(holder.logo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Title", operator.getTitle());
                intent.putExtra("Message", operator.getMessage());
                intent.putExtra("Image", (operator.getImageUrl() != null && !operator.getImageUrl().isEmpty()) ? ApplicationConstant.INSTANCE.baseUrl + "/Image/Notification/" + operator.getImageUrl() : "");
                intent.putExtra("Url", operator.getUrl());
                intent.putExtra("Time", operator.getEntryDate());
                mContext.startActivity(intent);
                if (!operator.isSeen()) {
                    ((NotificationListActivity) mContext).setReadNotification(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void filter(ArrayList<NotificationData> newList) {
        mList = new ArrayList<>();
        mList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, msg, date;
        public ImageView logo;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            date = (TextView) view.findViewById(R.id.date);
            logo = (ImageView) view.findViewById(R.id.logo);

        }
    }

}



/*
public class BankListScreenAdapter extends RecyclerView.Adapter<BankListScreenAdapter.MyViewHolder> {

    private ArrayList<BankListObject> operatorList;
    private Context mContext;
    int resourceId = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            opImage = (ImageView) view.findViewById(R.id.opImage);

        }
    }

    public BankListScreenAdapter(ArrayList<BankListObject> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public BankListScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_list_adapter, parent, false);

        return new BankListScreenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankListScreenAdapter.MyViewHolder holder, int position) {
        final BankListObject operator = operatorList.get(position);
        holder.title.setText(operator.getBankName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BankListScreen) mContext).ItemClick(operator.getBankId(), operator.getBankName(), operator.getAccVeri(), operator.getShortCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

}
*/

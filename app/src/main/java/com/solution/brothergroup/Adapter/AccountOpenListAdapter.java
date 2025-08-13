package com.solution.brothergroup.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.AccountOpData;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class AccountOpenListAdapter extends RecyclerView.Adapter<AccountOpenListAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<AccountOpData> operatorList;
    private Context mContext;
    RequestOptions requestOptions;
    public AccountOpenListAdapter(ArrayList<AccountOpData> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
         requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.circle_logo);
        requestOptions.error(R.drawable.circle_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public AccountOpenListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_account_open_list, parent, false);

        return new AccountOpenListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AccountOpenListAdapter.MyViewHolder holder, int position) {
        final AccountOpData operator = operatorList.get(position);
        holder.title.setText(operator.getName()+"");
        holder.content.setText(operator.getContent()+"");

        Glide.with(mContext).load(ApplicationConstant.INSTANCE.basebankBannerUrl + operator.getOid()+".png").
                apply(requestOptions).into(holder.banklogo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(operator.getRedirectURL())));
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void filter(ArrayList<AccountOpData> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,content;
        public ImageView  banklogo;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView=view;
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            banklogo = view.findViewById(R.id.banklogo);

        }
    }

}



/*
public class BankListScreenAdapter extends RecyclerView.Adapter<BankListScreenAdapter.MyViewHolder> {

    private ArrayList<AccountOpData> operatorList;
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

    public BankListScreenAdapter(ArrayList<AccountOpData> operatorList, Context mContext) {
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
        final AccountOpData operator = operatorList.get(position);
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

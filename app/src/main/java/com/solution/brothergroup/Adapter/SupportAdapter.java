package com.solution.brothergroup.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.SupportListItem;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.util.ArrayList;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.MyViewHolder> {
    String charText = "";
    private ArrayList<SupportListItem> mList;

    private Context mContext;
    RequestOptions requestOptions;
    public SupportAdapter(Context mContext, ArrayList<SupportListItem> mList) {
        this.mList = mList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.circle_logo);
        requestOptions.error(R.drawable.circle_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public SupportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_support, parent, false);

        return new SupportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SupportAdapter.MyViewHolder holder, int position) {
        final SupportListItem operator = mList.get(position);
        holder.name.setText(operator.getName());

        holder.numbers.setText(operator.getNumber());
        if (operator.getMsg() != null && !operator.getMsg().isEmpty()) {
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(operator.getMsg());
        } else {
            holder.msg.setVisibility(View.GONE);
        }
        if (operator.getImage() != null && operator.getImage().length() > 0) {

            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage())
                    .apply(requestOptions)
                    .into(holder.image);

        } else {
            holder.image.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView numbers, msg, name;
        ImageView image;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            numbers = view.findViewById(R.id.numbers);
            msg = view.findViewById(R.id.msg);
            image = view.findViewById(R.id.image);
        }
    }


}

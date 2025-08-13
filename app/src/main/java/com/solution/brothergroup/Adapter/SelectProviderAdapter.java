package com.solution.brothergroup.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.R;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.RechargeProviderActivity;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class SelectProviderAdapter extends RecyclerView.Adapter<SelectProviderAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<OperatorList> operatorList;
    private Context mContext;
    RequestOptions requestOptions;

    public SelectProviderAdapter(ArrayList<OperatorList> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.circle_logo);
        requestOptions.error(R.drawable.circle_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_provider_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);
        holder.title.setText(operator.getName());
        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RechargeProviderActivity) mContext).ItemClick( operator);
                /*((RechargeProviderActivity) mContext).ItemClick(operator.getName(), operator.getOid(),
                        operator.getMax(),
                        operator.getMin(),
                        operator.getLength(),
                        operator.getLengthMax(),
                        operator.getIsAccountNumeric(),
                        operator.getIsPartial(),
                        operator.getBBPS(),
                        operator.getAccountName(),
                        operator.getAccountRemak(),
                        operator.getImage(),
                        operator.getIsBilling(),
                        operator.getStartWith());*/
            }
        });


        if (operator.getImage() != null && operator.getImage().length() > 0) {
            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage())
                    .apply(requestOptions)
                    .into(holder.opImage);
        } else {
            holder.opImage.setImageResource(R.drawable.ic_tower);
        }

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        // operatorList.clear();
        if (charText.length() == 0) {
            operatorList.addAll(operatorList);
        } else {
            for (OperatorList op : operatorList) {
                if (op.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    operatorList.add(op);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter(ArrayList<OperatorList> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;
        LinearLayout ll_top;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            opImage = (ImageView) view.findViewById(R.id.opImage);
            ll_top = view.findViewById(R.id.ll_top);

        }
    }

}

package com.solution.brothergroup.AddMoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.RoleCommission;
import com.solution.brothergroup.Api.Object.SlabDetailDisplayLvl;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.AddMoney.AddMoneyActivity;


import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AddMoneyTypeAdapter extends RecyclerView.Adapter<AddMoneyTypeAdapter.MyViewHolder> {

    private ArrayList<SlabDetailDisplayLvl> operatorList;
    private Context mContext;
    RequestOptions requestOptions;
    String userRoleId;
    private AlertDialog alertDialogSlabRange;
    public AddMoneyTypeAdapter(ArrayList<SlabDetailDisplayLvl> operatorList, Context mContext, String userRoleId) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.userRoleId = userRoleId;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_money_type_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SlabDetailDisplayLvl operator = operatorList.get(position);
        holder.title.setText(operator.getOperator()+"");

        if(operator.getRoleCommission()!=null && operator.getRoleCommission().size()>0)
        {
            for (RoleCommission item : operator.getRoleCommission()){
                if(item.getRoleID()==Integer.parseInt(userRoleId))
                {
                    int amtType=item.getAmtType();
                    if(amtType==1)
                        holder.comm.setText("Charges : " + UtilMethods.INSTANCE.formatedAmount(item.getComm() + "") + " \u20B9" );
                    else
                        holder.comm.setText("Charges : " + UtilMethods.INSTANCE.formatedAmount(item.getComm() + "") +  " %");

                }
            }

        }

        if (operator.getCommSettingType() == 2) {
            holder.comm.setVisibility(View.GONE);
            holder.viewCharge.setVisibility(View.VISIBLE);
        } else {
            holder.comm.setVisibility(View.VISIBLE);
            holder.viewCharge.setVisibility(View.GONE);
        }

        holder.minMaxRange.setText("Min : " + operator.getMin() + " \u20B9" +" - " + " Max : " + operator.getMax() + " \u20B9");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof AddMoneyActivity) {
                    ((AddMoneyActivity) mContext).paymentTypeClick(operator);
                }/*if (mContext instanceof AddMoneyActivity) {
                    ((AddMoneyActivity) mContext).paymentTypeClick(operator);
                }*/
            }
        });

        holder.viewCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof AddMoneyActivity) {
                    ((AddMoneyActivity) mContext).viewRangeClick(operator);
                } /*if (mContext instanceof AddMoneyActivity) {
                    ((AddMoneyActivity) mContext).viewRangeClick(operator);
                }*/
            }
        });


        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                .apply(requestOptions)
                .into(holder.opImage);

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
            for (SlabDetailDisplayLvl op : operatorList) {
                if (op.getOperator().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    operatorList.add(op);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter(ArrayList<SlabDetailDisplayLvl> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, comm,minMaxRange,viewCharge;
        public ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            comm = (TextView) view.findViewById(R.id.comm);
            viewCharge = (TextView) view.findViewById(R.id.tv_viewCharges);
            minMaxRange = (TextView) view.findViewById(R.id.tv_minMaxRange);
            opImage = (ImageView) view.findViewById(R.id.opImage);

        }
    }

}

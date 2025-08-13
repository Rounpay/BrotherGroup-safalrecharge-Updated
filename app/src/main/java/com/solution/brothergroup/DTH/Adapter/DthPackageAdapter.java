package com.solution.brothergroup.DTH.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.DTH.Activity.DthPackageActivity;
import com.solution.brothergroup.DTH.dto.DthPackage;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class DthPackageAdapter extends RecyclerView.Adapter<DthPackageAdapter.MyViewHolder> {

    private List<DthPackage> operatorList;
    private Context mContext;

    public DthPackageAdapter(List<DthPackage> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_package, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DthPackage operator = operatorList.get(position);
        holder.description.setText(operator.getDescription() + "");
        holder.name.setText(operator.getPackageName() + "");
        holder.validity.setText(operator.getValidity() + " Days");
        holder.bookingAmt.setText("Booking Amount : " + mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getBookingAmount() + ""));

        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(operator.getPackageMRP() + ""));
        holder.viewChannel.setText("View Channels\n("+operator.getChannelCount()+" Channels)");

        holder.viewChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DthPackageActivity) mContext).ViewChannel(operator);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DthPackageActivity) mContext).SelectedPackage(operator);
            }
        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView validity;
        public TextView amount, name, viewChannel;
        public TextView description, bookingAmt;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);
            validity = view.findViewById(R.id.validity);
            name = view.findViewById(R.id.name);
            bookingAmt = view.findViewById(R.id.bookngAmt);
            viewChannel = view.findViewById(R.id.viewChannel);


        }
    }

}

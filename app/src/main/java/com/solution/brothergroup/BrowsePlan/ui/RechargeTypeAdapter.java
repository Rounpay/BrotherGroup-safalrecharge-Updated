package com.solution.brothergroup.BrowsePlan.ui;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.BrowsePlan.dto.PlanDataDetails;
import com.solution.brothergroup.R;

import java.util.List;

public class RechargeTypeAdapter extends RecyclerView.Adapter<RechargeTypeAdapter.MyViewHolder> {

    private List<PlanDataDetails> operatorList;
    private Context mContext;

    public RechargeTypeAdapter(List<PlanDataDetails> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public RechargeTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recharge_plan, parent, false);

        return new RechargeTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RechargeTypeAdapter.MyViewHolder holder, final int position) {
        final PlanDataDetails operator = operatorList.get(position);
        //     holder.talktime.setText("N/A");
        if (operator.getValidity() != null && operator.getValidity().length() > 0)
            holder.validity.setText(operator.getValidity());
        else
            holder.validity.setText("N/A");
        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BrowsePlanScreenActivity) mContext).ItemClick("" + operator.getRs(), "" + operator.getDesc());
            }
        });
        if (operator.getDesc() != null && operator.getDesc().length() > 0)
            holder.description.setText(operator.getDesc());
        else
            holder.description.setText("N/A");

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView talktime;
        public TextView validity;
        public TextView amount;
        public TextView description;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            //  talktime = view.findViewById(R.id.talktime);
            validity = view.findViewById(R.id.validity);
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);

        }
    }

}

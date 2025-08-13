package com.solution.brothergroup.DthPlan.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.DthPlan.dto.PlanRPResponse;
import com.solution.brothergroup.R;

import java.util.List;


public class DthPlanListRPAdapter extends RecyclerView.Adapter<DthPlanListRPAdapter.MyViewHolder> {

    private List<PlanRPResponse> operatorList;
    private Context mContext;

    public DthPlanListRPAdapter(List<PlanRPResponse> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DthPlanListRPAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_plan, parent, false);

        return new DthPlanListRPAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListRPAdapter.MyViewHolder holder, final int position) {
        final PlanRPResponse operator = operatorList.get(position);

        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRechargeAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRechargeAmount(), "" + operator.getDetails());
            }
        });

        holder.countView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.planName.setText(operator.getRechargeValidity());
        if (operator.getDetails() != null && operator.getDetails().length() > 0) {
            holder.description.setText(operator.getDetails());
        } else {
            holder.description.setText("N/A");
        }
        if (operator.getPackagelanguage() != null && operator.getPackagelanguage().length() > 0) {
            holder.language.setText(operator.getPackagelanguage());
            holder.languageView.setVisibility(View.VISIBLE);
        } else {
            holder.languageView.setVisibility(View.GONE);
            holder.language.setText("N/A");
        }
        if (operator.getChannelcount() != 0) {
            holder.count.setText(operator.getChannelcount() + "");
            holder.countView.setVisibility(View.VISIBLE);
        } else {
            holder.countView.setVisibility(View.GONE);
            holder.count.setText("N/A");
        }

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView planName;
        public TextView amount, validity;
        public TextView description, count, language;
        View itemView, countLangView, countView, languageView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            planName = view.findViewById(R.id.planName);
            amount = view.findViewById(R.id.amount);
            validity = view.findViewById(R.id.validity);
            language = view.findViewById(R.id.language);
            description = view.findViewById(R.id.description);
            countLangView = view.findViewById(R.id.countLangView);
            languageView = view.findViewById(R.id.languageView);
            countView = view.findViewById(R.id.countView);
            count = view.findViewById(R.id.count);
            validity.setVisibility(View.GONE);
            countLangView.setVisibility(View.VISIBLE);
        }
    }

}

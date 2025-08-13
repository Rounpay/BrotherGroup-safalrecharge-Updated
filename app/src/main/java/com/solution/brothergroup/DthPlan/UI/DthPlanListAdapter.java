package com.solution.brothergroup.DthPlan.UI;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.DthPlan.dto.PlanInfoPlan;
import com.solution.brothergroup.DthPlan.dto.PlanValidity;
import com.solution.brothergroup.R;

import java.util.ArrayList;
import java.util.List;


public class DthPlanListAdapter extends RecyclerView.Adapter<DthPlanListAdapter.MyViewHolder> {

    private List<PlanInfoPlan> operatorList;
    private Context mContext;

    public DthPlanListAdapter(List<PlanInfoPlan> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DthPlanListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_plan, parent, false);

        return new DthPlanListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListAdapter.MyViewHolder holder, final int position) {
        final PlanInfoPlan operator = operatorList.get(position);
        List<PlanValidity> mPlanValidities = new ArrayList<>();

        if (operator.getRs().get1MONTHS() != null && !operator.getRs().get1MONTHS().isEmpty()) {
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get1MONTHS().trim());
            holder.validity.setText("1 Month");
            mPlanValidities.add(new PlanValidity(operator.getRs().get1MONTHS(), "1 Month", operator.getDesc()));
        }
        if (operator.getRs().get3MONTHS() != null && !operator.getRs().get3MONTHS().isEmpty()) {
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get3MONTHS().trim());
            holder.validity.setText("3 Months");
            mPlanValidities.add(new PlanValidity(operator.getRs().get3MONTHS(), "3 Months", operator.getDesc()));
        }
        if (operator.getRs().get6MONTHS() != null && !operator.getRs().get6MONTHS().isEmpty()) {
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get6MONTHS().trim());
            holder.validity.setText("6 Months");
            mPlanValidities.add(new PlanValidity(operator.getRs().get6MONTHS(), "6 Months", operator.getDesc()));
        }
        if (operator.getRs().get9MONTHS() != null && !operator.getRs().get9MONTHS().isEmpty()) {
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get9MONTHS().trim());
            holder.validity.setText("9 Months");
            mPlanValidities.add(new PlanValidity(operator.getRs().get9MONTHS(), "9 Months", operator.getDesc()));
        }
        if (operator.getRs().get1YEAR() != null && !operator.getRs().get1YEAR().isEmpty()) {
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get1YEAR().trim());
            holder.validity.setText("1 Year");
            mPlanValidities.add(new PlanValidity(operator.getRs().get1YEAR(), "1 Year", operator.getDesc()));
        }
        if (operator.getRs().get5YEAR() != null && !operator.getRs().get5YEAR().isEmpty()) {
            holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs().get5YEAR().trim());
            holder.validity.setText("5 Years");
            mPlanValidities.add(new PlanValidity(operator.getRs().get5YEAR(), "5 Years", operator.getDesc()));
        }

        if (mPlanValidities != null && mPlanValidities.size() > 1) {
            holder.rsGrid.setVisibility(View.VISIBLE);
            holder.amtView.setVisibility(View.GONE);
            DthPlanValidityGridAdapter mDthPlanValidityGridAdapter = new DthPlanValidityGridAdapter(mContext, mPlanValidities);
            holder.rsGrid.setLayoutManager(new LinearLayoutManager(mContext));
            holder.rsGrid.setAdapter(mDthPlanValidityGridAdapter);
        } else {

            holder.amtView.setVisibility(View.VISIBLE);
            holder.rsGrid.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator.getRs() != null && operator.getRs().get1MONTHS() != null && !operator.getRs().get1MONTHS().isEmpty()) {
                    ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get1MONTHS(),
                            operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
                } else if (operator.getRs() != null && operator.getRs().get3MONTHS() != null && !operator.getRs().get3MONTHS().isEmpty()) {
                    ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get3MONTHS(),
                            operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
                } else if (operator.getRs() != null && operator.getRs().get6MONTHS() != null && !operator.getRs().get6MONTHS().isEmpty()) {
                    ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get6MONTHS(),
                            operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
                } else if (operator.getRs() != null && operator.getRs().get9MONTHS() != null && !operator.getRs().get9MONTHS().isEmpty()) {
                    ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get9MONTHS(),
                            operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
                } else if (operator.getRs() != null && operator.getRs().get1YEAR() != null && !operator.getRs().get1YEAR().isEmpty()) {
                    ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get1YEAR(),
                            operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
                } else if (operator.getRs() != null && operator.getRs().get5YEAR() != null && !operator.getRs().get5YEAR().isEmpty()) {
                    ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get5YEAR(),
                            operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
                }
            }
        });
        if (operator.getDesc() != null && operator.getDesc().length() > 0) {
            Spanned html = Html.fromHtml(operator.getDesc());
            holder.description.setText(HtmlCompat.fromHtml(String.valueOf(html), HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else
            holder.description.setText("N/A");

        if (operator.getPlanName() != null && operator.getPlanName().length() > 0) {
            Spanned html = Html.fromHtml(operator.getPlanName());
            holder.planName.setText(HtmlCompat.fromHtml(String.valueOf(html), HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else
            holder.planName.setText("N/A");

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView planName;
        public TextView amount;
        public TextView description, validity;
        RecyclerView rsGrid;
        View itemView, amtView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            planName = view.findViewById(R.id.planName);
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);
            validity = view.findViewById(R.id.validity);
            amtView = view.findViewById(R.id.amtView);
            rsGrid = view.findViewById(R.id.rsGrid);
            rsGrid.setLayoutManager(new GridLayoutManager(mContext, 3));

        }
    }

}

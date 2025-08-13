package com.solution.brothergroup.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.brothergroup.Activities.SelectZoneOption;
import com.solution.brothergroup.Api.Response.CircleList;
import com.solution.brothergroup.R;

import java.util.ArrayList;

public class SelectZoneOptionAdapter extends RecyclerView.Adapter<SelectZoneOptionAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<CircleList> operatorList;
    private Context mContext;

    public SelectZoneOptionAdapter(ArrayList<CircleList> operatorList, Context mContext) {
        this.operatorList = operatorList;

        this.mContext = mContext;

    }

    @Override
    public SelectZoneOptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_option_adapter, parent, false);

        return new SelectZoneOptionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectZoneOptionAdapter.MyViewHolder holder, int position) {
        final CircleList operator = operatorList.get(position);
        holder.title.setPadding(20, 0, 0, 0);
        holder.title.setText(operator.getCircle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SelectZoneOption) mContext).ItemClick(operator.getCircle(), operator.getId(), operator.getCircle());
            }
        });
        holder.opImage.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            opImage = (ImageView) view.findViewById(R.id.opImage);
        }
    }

}

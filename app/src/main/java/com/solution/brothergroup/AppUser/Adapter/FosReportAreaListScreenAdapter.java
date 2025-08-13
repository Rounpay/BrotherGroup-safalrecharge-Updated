package com.solution.brothergroup.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.AreaMaster;
import com.solution.brothergroup.AppUser.Activity.FosAreaReportActivity;
import com.solution.brothergroup.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class FosReportAreaListScreenAdapter extends RecyclerView.Adapter<FosReportAreaListScreenAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<AreaMaster> operatorList = new ArrayList<>();
    private Context mContext;
    RequestOptions requestOptions;
    String qwerty;


    public FosReportAreaListScreenAdapter(ArrayList<AreaMaster> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }


    @Override
    public FosReportAreaListScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_area_list, parent, false);

        return new FosReportAreaListScreenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FosReportAreaListScreenAdapter.MyViewHolder holder, int position) {
        final AreaMaster operator = operatorList.get(position);

        holder.title.setText(operator.getArea());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof FosAreaReportActivity) {
                    ((FosAreaReportActivity) mContext).setArea(operator);
                }

            }
        });


//            holder.title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    Toast.makeText(mContext, ""+operator.getAreaID(), Toast.LENGTH_SHORT).show();
//                }
//            });

    }


    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void filter(ArrayList<AreaMaster> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView areakarrow;


        public MyViewHolder(View view) {
            super(view);


            title = view.findViewById(R.id.title);
            areakarrow = view.findViewById(R.id.areakarrow);

        }
    }

}

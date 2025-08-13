package com.solution.brothergroup.UpgradePacakge.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.solution.brothergroup.R;
import com.solution.brothergroup.UpgradePacakge.dto.ServicesObj;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<ServicesObj> services ;

    public ServicesAdapter(Context mContext, ArrayList<ServicesObj> services ) {
        this.mContext = mContext;
        this.services = services;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_adpater, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ServicesObj servic=services.get(position);

        holder.tv_services.setText(servic.getServiceName());


    }

    @Override
    public int getItemCount() {
        return services.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_services;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_services=itemView.findViewById(R.id.tv_services);
        }
    }
}


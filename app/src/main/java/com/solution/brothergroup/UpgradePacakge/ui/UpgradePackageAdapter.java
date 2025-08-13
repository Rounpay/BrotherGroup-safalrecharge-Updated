package com.solution.brothergroup.UpgradePacakge.ui;

import android.app.Activity;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.UpgradePacakge.dto.PDetail;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

public class UpgradePackageAdapter extends RecyclerView.Adapter<UpgradePackageAdapter.MyViewHolder> {

    Activity mContext;
    CustomLoader loader;


  //  ArrayList<CustomPackage> CustomServicesList=new ArrayList<>();
  ArrayList<PDetail> packageDetails;

    ServicesAdapter servicesadapetr;




    public UpgradePackageAdapter(Activity mContext, ArrayList<PDetail> packageDetails) {
        this.mContext = mContext;
        this.packageDetails = packageDetails;
        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upgrade_package_adpater, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        final PDetail operator=packageDetails.get(position);

        holder.tv_package_name.setText("" + operator.getPackageName());
        holder.tv_package_cost.setText("" + operator.getPackageCost());

        //  // Log.e("PackageId",operator.getPackageId());


        holder.rec_services.setLayoutManager(new GridLayoutManager(mContext, 2));
        servicesadapetr = new ServicesAdapter(mContext,operator.getServices());

        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.rec_services.setLayoutManager(mLayoutManager);
        holder.rec_services.setLayoutManager(new LinearLayoutManager(mContext
                , LinearLayoutManager.HORIZONTAL, false));*/
        holder.rec_services.setItemAnimator(new DefaultItemAnimator());
        holder.rec_services.setAdapter(servicesadapetr);




        if (operator.getDefault()) {
            holder.btn_upgarde_pacakge.setText("Current");
            holder.btn_upgarde_pacakge.setEnabled(false);
           // holder.btn_upgarde_pacakge.setBackground(mContext.getDrawable(R.drawable.rect));
            //holder.btn_upgarde_pacakge.setBackgroundColor(mContext.getResources().getColor(R.color.darkGreen));
        } else {
            holder.btn_upgarde_pacakge.setText("Upgrade");
           // holder.btn_upgarde_pacakge.setBackground(mContext.getDrawable(R.drawable.rect));
          //  holder.btn_upgarde_pacakge.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            holder.btn_upgarde_pacakge.setEnabled(true);
            holder.btn_upgarde_pacakge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String LoginResponse = UtilMethods.INSTANCE.getLoginPref(mContext);
                   LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.GetAppPackageUpgrade(mContext,LoginDataResponse.getData().getUserID()+"",operator.getPackageId(),loader,holder.btn_upgarde_pacakge);
                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.err_msg_network_title),
                                mContext.getResources().getString(R.string.err_msg_network));
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return packageDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_package_name, tv_package_cost;
        RecyclerView rec_services;
        Button btn_upgarde_pacakge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_package_name = itemView.findViewById(R.id.tv_package_name);
            tv_package_cost = itemView.findViewById(R.id.tv_package_cost);
            rec_services = itemView.findViewById(R.id.rec_services);
            btn_upgarde_pacakge = itemView.findViewById(R.id.btn_upgarde_pacakge);


        }
    }

}

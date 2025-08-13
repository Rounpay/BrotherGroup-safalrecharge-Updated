package com.solution.brothergroup.DMTWithPipe.Adapter;

import android.app.Activity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.solution.brothergroup.Api.Object.DataOpType;

import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.CustomAlertDialog;


import java.util.List;


public class DMTOptionListAdapter extends RecyclerView.Adapter<DMTOptionListAdapter.MyViewHolder> {

    ClickView mClickView;
    int layout;
    private List<OperatorList> operatorList;
    private Activity mContext;
    private DataOpType data;
    CustomAlertDialog mCustomAlertDialog;

    public DMTOptionListAdapter(List<OperatorList> operatorList, Activity mContext, ClickView mClickView, int layout) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
        mCustomAlertDialog = new CustomAlertDialog(mContext, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);


            holder.name.setText(layout == R.layout.adapter_dashboard_option_grid ? operator.getName().replaceAll("\n", " ") : operator.getName());
            //holder.icon.setImageResource(ServiceIcon.INSTANCE.serviceIcon(operator.getOpType()));
               setIcon(operator.getOpType(), holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mClickView != null) {

                    mClickView.onClick(operator);
                }
            }
        });
       /* if (operator.getName() != null && operator.getName().toString().length() > 0) {
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl+operator.getImage()).
                    apply(requestOptions).into(holder.opImage);

        } else {
            holder.opImage.setImageResource(R.drawable.ic_operator_default_icon);
        }*/
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    void setIcon(int id, MyViewHolder holder) {
        /*1	Prepaid
2	Postpaid
3	DTH
4	Landline
5	Electricity
6	Gas
7	Domestic Hotel
8	International Hotel
9	Domestic Flight
10	International Flight
11	Bus
12	Connection
13	MPOS
14	DMT
15	DMR Charge
16	Broadband
17	Water
18	Train
19	Shopping
20	PAN Card
22	AEPS
110 Fund Request*/
        if (id == 1) {
            holder.icon.setImageResource(R.drawable.ic_prepaid);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.red));
        }
        if (id == 2) {
            holder.icon.setImageResource(R.drawable.ic_postpaid);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.colorPrimaryLight));
        }
        if (id == 3) {
            holder.icon.setImageResource(R.drawable.ic_satellite_dish);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_purple));
        }
        if (id == 4) {
            holder.icon.setImageResource(R.drawable.ic_landline);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.gre));
        }
        if (id == 5) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_blue_grey));
            holder.icon.setImageResource(R.drawable.ic_bulb);
        }
        if (id == 6) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_mark));
            holder.icon.setImageResource(R.drawable.ic_gas_pipe);
        }
        if (id == 7) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 8) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 9) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 10) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 11) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 12) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 13) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 14) {
            holder.icon.setImageResource(R.drawable.ic_bank);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
        }
        if (id == 15) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 16) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.pressed));
            holder.icon.setImageResource(R.drawable.ic_broadband);
        }
        if (id == 17) {
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.gre));
            holder.icon.setImageResource(R.drawable.ic_water);
        }
        if (id == 18) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 19) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 20) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 21) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 22) { //For AEPS
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
            holder.icon.setImageResource(R.drawable.ic_eaadharcard);
        }
        if (id == 23) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 24) {
            //For PSA
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.black));
            holder.icon.setImageResource(R.drawable.ic_pan);
        }
        if (id == 25) {
            holder.icon.setImageResource(R.drawable.ic_loan);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_cyan));

        }
        if (id == 26) {
            holder.icon.setImageResource(R.drawable.ic_gas);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_orange));

        }
        if (id == 27) {
            holder.icon.setImageResource(R.drawable.ic_insurance);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_brown));

        }
        if (id == 28) {
            //Bike Insurance
            holder.icon.setImageResource(R.drawable.ic_bike_insurance);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_teal));

        }
        if (id == 29) {
            //Car Insurance
            holder.icon.setImageResource(R.drawable.ic_car_insurance);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_purple));

        }
        if (id == 30) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 31) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        if (id == 32) {
            holder.icon.setImageResource(R.drawable.ic_prepaid);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.red));
        }
        if (id == 33) {
            holder.icon.setImageResource(R.drawable.ic_satellite_dish);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_purple));
        }
        if (id == 37) {
            holder.icon.setImageResource(R.drawable.ic_add_wallet);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_light_green));
        }

        if (id == 100) {
            //Fund Request
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.dark_blue));
            holder.icon.setImageResource(R.drawable.ic_fund_request);
        }
        if (id == 101) {
            holder.icon.setImageResource(R.drawable.ic_recharge_report);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.style_color_accent));
        }
        if (id == 102) {
            holder.icon.setImageResource(R.drawable.ic_ledger_report);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.style_color_primary));
        }
        if (id == 103) {
            holder.icon.setImageResource(R.drawable.ic_fund_order_report);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_light_blue));
        }
        if (id == 104) {
            holder.icon.setImageResource(R.drawable.ic_dispute);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.centerOrange1));
        }
        if (id == 105) {
            //DMT Report
            holder.icon.setImageResource(R.drawable.ic_dmt_transaction);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.iciciColor2));

        }
        if (id == 106) {
            holder.icon.setImageResource(R.drawable.ic_exchnage_fund_24dp);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.onselect));
        }
        if (id == 107) {
            //Debit Credit Report
            holder.icon.setImageResource(R.drawable.ic_fund_receive);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.snackBarColor1));
        }
        if (id == 108) {
            holder.icon.setImageResource(R.drawable.ic_user_daybook);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.yellow_dark));
        }
        if (id == 109) {
            holder.icon.setImageResource(R.drawable.ic_report_pending);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.colorlink));
        }
        if (id == 110) {
            holder.icon.setImageResource(R.drawable.ic_fund_request);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.reddishBrown));
        }
        if (id == 111) {
            holder.icon.setImageResource(R.drawable.ic_create_user);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.green));
        }
        if (id == 112) {
            holder.icon.setImageResource(R.drawable.ic_multi_user);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_brown));
        }
        if (id == 113) {
            //Commission Slab
            holder.icon.setImageResource(R.drawable.ic_commission_slab);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_delete));
        }
        if (id == 114) {
            holder.icon.setImageResource(R.drawable.ic_wrong_right);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_deep_purple));
        }
        if (id == 115) {
            holder.icon.setImageResource(R.drawable.ic_daybook_dmt);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_light_blue));
        }
        if (id == 116) {
            holder.icon.setImageResource(R.drawable.ic_call_request);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_orange));
        }
        if (id == 117) {
            holder.icon.setImageResource(R.drawable.ic_scan_pay);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_blue_grey));
        }
        if (id == 118) {
            holder.icon.setImageResource(R.drawable.ic_specific_report);
            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_amber));
        }
//        if (id == 119) {
//            holder.icon.setImageResource(R.drawable.ic_add_wallet);
//            ViewCompat.setBackgroundTintList(holder.bgView, ContextCompat.getColorStateList(mContext, R.color.color_light_green));
//        }
    }

    public interface ClickView {
        void onClick(OperatorList mOperatorList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, comingsoonTv;
        public ImageView icon, bgView;
        RelativeLayout imageContainer;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            /*comingsoonTv = view.findViewById(R.id.comingsoonTv);*/
            icon = view.findViewById(R.id.icon);
            imageContainer = view.findViewById(R.id.imageContainer);
            bgView = view.findViewById(R.id.bgView);
            itemView = view;

        }
    }
}
